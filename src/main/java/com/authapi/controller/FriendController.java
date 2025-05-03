package com.authapi.controller;

import com.authapi.entity.User;
import com.authapi.repository.UserRepository;
import com.authapi.service.FriendService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

  private final FriendService friendService;
  private final UserRepository userRepo;

  public FriendController(FriendService friendService, UserRepository userRepo) {
    this.friendService = friendService;
    this.userRepo = userRepo;
  }

  @PostMapping("/request")
  public void sendFriendRequest(@RequestParam String from, @RequestParam String to) {
    friendService.sendRequest(from, to);
  }

  @PostMapping("/accept")
  public void acceptRequest(@RequestParam String from, @RequestParam String to) {
    friendService.acceptRequest(from, to);
  }

  @DeleteMapping("/remove")
  public void removeFriend(@RequestParam String a, @RequestParam String b) {
    friendService.removeFriend(a, b);
  }

  @GetMapping("/list")
  public List<String> getFriends(@RequestParam String email) {
    User user = userRepo.findByEmail(email).orElseThrow();
    return friendService.getFriends(user).stream()
        .map(
            f -> {
              User other = f.getRequester().equals(user) ? f.getRecipient() : f.getRequester();
              return other.getEmail();
            })
        .collect(Collectors.toList());
  }

  @GetMapping("/pending")
  public List<String> getPending(@RequestParam String email) {
    User user = userRepo.findByEmail(email).orElseThrow();
    return friendService.getPendingRequests(user).stream()
        .map(f -> f.getRequester().getEmail())
        .collect(Collectors.toList());
  }
}
