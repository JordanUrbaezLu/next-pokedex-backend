package com.authapi.repository;

import com.authapi.entity.Friendship;
import com.authapi.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

  // For checking if a request already exists
  Optional<Friendship> findByRequesterAndReceiver(User requester, User receiver);

  // For accepting incoming requests
  List<Friendship> findByReceiverAndAcceptedFalse(User receiver);

  // For listing all accepted friends for a user
  List<Friendship> findByRequesterAndAcceptedTrue(User requester);

  List<Friendship> findByReceiverAndAcceptedTrue(User receiver);

  // For bidirectional friendship cleanup (used in delete)
  List<Friendship> findByRequesterAndReceiverOrReceiverAndRequester(
      User requester1, User receiver1, User requester2, User receiver2);
}
