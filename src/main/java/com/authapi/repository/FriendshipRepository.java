package com.authapi.repository;

import com.authapi.entity.Friendship;
import com.authapi.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

  boolean existsByRequesterAndRecipient(User requester, User recipient);

  Optional<Friendship> findByRequesterAndRecipient(User requester, User recipient);

  List<Friendship> findByRequesterOrRecipientAndAccepted(User u1, User u2, boolean accepted);

  List<Friendship> findByRecipientAndAccepted(User recipient, boolean accepted);

  List<Friendship> findByRequesterAndAccepted(User requester, boolean accepted);
}
