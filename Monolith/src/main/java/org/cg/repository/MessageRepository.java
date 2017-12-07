package org.cg.repository;

import org.cg.Model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.Long;
import java.util.List;
import org.cg.Model.User;

@Repository
public interface MessageRepository extends CrudRepository<Message,Long>{
 Message findByMessageId(Long messageid);
 List<Message> findByReceiver(User receiver);
 List<Message> findBySender(User sender);
 
 @Query(value = "SELECT d FROM Message d where d.sender.userId = :userId")
 List<Message> findSentMessagesByUserId(@Param("userId") Long userId);
 
 @Query(value = "SELECT d FROM Message d where d.receiver.userId = :userId")
 List<Message> findReceivedMessagesByUserId(@Param("userId") Long userId);
}
