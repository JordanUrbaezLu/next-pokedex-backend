package com.authapi.service;

import com.authapi.entity.Friendship;
import com.authapi.entity.User;
import com.authapi.repository.FriendshipRepository;
import com.authapi.repository.UserRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FriendService {

  private final FriendshipRepository friendshipRepo;
  private final UserRepository userRepo;

  public FriendService(FriendshipRepository friendshipRepo, UserRepository userRepo) {
    this.friendshipRepo = friendshipRepo;
    this.userRepo = userRepo;
  }

  public void sendRequest(String fromEmail, String toEmail) {
    User from = userRepo.findByEmail(fromEmail).orElseThrow();
    User to = userRepo.findByEmail(toEmail).orElseThrow();

    if (friendshipRepo.existsByRequesterAndRecipient(from, to)) {
      throw new IllegalStateException("Request already sent or already friends.");
    }

    Friendship f = new Friendship();
    f.setRequester(from);
    f.setRecipient(to);
    f.setAccepted(false);
    f.setCreatedAt(Instant.now());

    friendshipRepo.save(f);
  }

  public void acceptRequest(String fromEmail, String toEmail) {
    User from = userRepo.findByEmail(fromEmail).orElseThrow();
    User to = userRepo.findByEmail(toEmail).orElseThrow();

    Friendship request =
        friendshipRepo
            .findByRequesterAndRecipient(from, to)
            .orElseThrow(() -> new IllegalStateException("Request not found"));

    request.setAccepted(true);
    friendshipRepo.save(request);
  }

  public void removeFriend(String email1, String email2) {
    User u1 = userRepo.findByEmail(email1).orElseThrow();
    User u2 = userRepo.findByEmail(email2).orElseThrow();

    friendshipRepo.findByRequesterAndRecipient(u1, u2).ifPresent(friendshipRepo::delete);

    friendshipRepo.findByRequesterAndRecipient(u2, u1).ifPresent(friendshipRepo::delete);
  }

  public List<Friendship> getFriends(User user) {
    return friendshipRepo.findByRequesterOrRecipientAndAccepted(user, user, true);
  }

  public List<Friendship> getPendingRequests(User user) {
    return friendshipRepo.findByRecipientAndAccepted(user, false);
  }
}
