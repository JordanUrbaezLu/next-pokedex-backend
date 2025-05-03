package com.authapi.controller;

import com.authapi.dto.FriendActionRequest;
import com.authapi.dto.FriendDTO;
import com.authapi.dto.PendingFriendDTO;
import com.authapi.entity.Friendship;
import com.authapi.entity.User;
import com.authapi.repository.FriendshipRepository;
import com.authapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

  @Autowired private UserRepository userRepository;

  @Autowired private FriendshipRepository friendshipRepository;

  @PostMapping("/request")
  @Transactional
  public ResponseEntity<?> sendFriendRequest(
      @RequestBody FriendActionRequest request, Principal principal) {
    Optional<User> requesterOpt = userRepository.findByEmail(principal.getName());
    Optional<User> receiverOpt = userRepository.findById(request.getTargetUserId());

    if (requesterOpt.isEmpty() || receiverOpt.isEmpty()) {
      return ResponseEntity.badRequest().body("Invalid users");
    }

    User requester = requesterOpt.get();
    User receiver = receiverOpt.get();

    // 🚫 Prevent self-friend request
    if (requester.getId().equals(receiver.getId())) {
      return ResponseEntity.badRequest().body("Cannot send a friend request to yourself");
    }

    // Don't allow duplicate or reverse duplicate friendships
    boolean friendshipExists =
        friendshipRepository.findByRequesterAndReceiver(requester, receiver).isPresent()
            || friendshipRepository.findByRequesterAndReceiver(receiver, requester).isPresent();

    if (friendshipExists) {
      return ResponseEntity.badRequest().body("Friendship already exists or pending");
    }

    Friendship friendship = new Friendship();
    friendship.setRequester(requester);
    friendship.setReceiver(receiver);
    friendship.setRequestedAt(Instant.now());
    friendship.setAccepted(false);

    friendshipRepository.save(friendship);

    return ResponseEntity.ok("Friend request sent");
  }

  @PostMapping("/accept")
  @Transactional
  public ResponseEntity<?> acceptFriendRequest(
      @RequestBody FriendActionRequest request, Principal principal) {
    Optional<User> receiverOpt = userRepository.findByEmail(principal.getName());
    Optional<User> requesterOpt = userRepository.findById(request.getTargetUserId());

    if (receiverOpt.isEmpty() || requesterOpt.isEmpty()) {
      return ResponseEntity.badRequest().body("Invalid users");
    }

    User receiver = receiverOpt.get();
    User requester = requesterOpt.get();

    Optional<Friendship> friendshipOpt =
        friendshipRepository.findByRequesterAndReceiver(requester, receiver);

    if (friendshipOpt.isEmpty()) {
      return ResponseEntity.badRequest().body("No pending request found");
    }

    Friendship friendship = friendshipOpt.get();
    friendship.setAccepted(true);
    friendship.setAcceptedAt(Instant.now());

    friendshipRepository.save(friendship);

    return ResponseEntity.ok("Friend request accepted");
  }

  @DeleteMapping("/remove")
  @Transactional
  public ResponseEntity<?> removeFriend(
      @RequestBody FriendActionRequest request, Principal principal) {
    Optional<User> currentUserOpt = userRepository.findByEmail(principal.getName());
    Optional<User> targetUserOpt = userRepository.findById(request.getTargetUserId());

    if (currentUserOpt.isEmpty() || targetUserOpt.isEmpty()) {
      return ResponseEntity.badRequest().body("Invalid users");
    }

    User currentUser = currentUserOpt.get();
    User target = targetUserOpt.get();

    friendshipRepository
        .findByRequesterAndReceiver(currentUser, target)
        .ifPresent(friendshipRepository::delete);

    friendshipRepository
        .findByRequesterAndReceiver(target, currentUser)
        .ifPresent(friendshipRepository::delete);

    return ResponseEntity.ok("Friend removed");
  }

  @GetMapping("/list")
  public ResponseEntity<?> getAcceptedFriends(Principal principal) {
    Optional<User> currentUserOpt = userRepository.findByEmail(principal.getName());

    if (currentUserOpt.isEmpty()) {
      return ResponseEntity.badRequest().body("Invalid user");
    }

    User currentUser = currentUserOpt.get();

    var sent = friendshipRepository.findByRequesterAndAcceptedTrue(currentUser);
    var received = friendshipRepository.findByReceiverAndAcceptedTrue(currentUser);

    List<FriendDTO> friendDTOs =
        new ArrayList<>(
            sent.stream()
                .map(
                    f ->
                        new FriendDTO(
                            f.getReceiver().getId(),
                            f.getReceiver().getEmail(),
                            f.getReceiver().getName(),
                            f.getAcceptedAt()))
                .toList());

    friendDTOs.addAll(
        received.stream()
            .map(
                f ->
                    new FriendDTO(
                        f.getRequester().getId(),
                        f.getRequester().getEmail(),
                        f.getRequester().getName(),
                        f.getAcceptedAt()))
            .toList());

    return ResponseEntity.ok(friendDTOs);
  }

  @GetMapping("/pending")
  public ResponseEntity<?> getPendingFriendRequests(Principal principal) {
    Optional<User> currentUserOpt = userRepository.findByEmail(principal.getName());

    if (currentUserOpt.isEmpty()) {
      return ResponseEntity.badRequest().body("Invalid user");
    }

    User currentUser = currentUserOpt.get();

    var pendingRequests = friendshipRepository.findByReceiverAndAcceptedFalse(currentUser);

    return ResponseEntity.ok(
        pendingRequests.stream()
            .map(
                f ->
                    new PendingFriendDTO(
                        f.getRequester().getId(),
                        f.getRequester().getEmail(),
                        f.getRequester().getName(),
                        f.getRequestedAt()))
            .toList());
  }
}
