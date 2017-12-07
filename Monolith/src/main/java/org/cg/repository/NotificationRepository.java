package org.cg.repository;

import org.cg.Model.Message;
import org.cg.Model.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.String;
import java.util.List;
import java.util.Optional;

import org.cg.Model.User;
import java.lang.Long;

@Repository
public interface NotificationRepository extends CrudRepository<Notification,Long>{
List<Notification> findBySender(String sender);
List<Notification> findByReceiver(User receiver);
Optional<Notification> findById(Long id);

@Query(value = "SELECT d FROM Notification d where d.receiver.userId = :userId")
List<Notification> findNotificationsForUserId(@Param("userId") Long userId);

}
