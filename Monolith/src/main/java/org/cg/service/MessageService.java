package org.cg.service;

import java.util.List;

import org.cg.Model.Message;
import org.cg.Model.User;
import org.cg.Model.dto.MessageDTO;

public interface MessageService {
	
	
public MessageDTO getMessage(Long messageId);
public MessageDTO saveMessage(MessageDTO message);

public List<MessageDTO> getAllMessages();

void deleteMessage(Long id);
List<MessageDTO> getMessagesForUser(User userId);
MessageDTO convertEntityIntoDto(Message message);
Message convertDtoIntoEntity(MessageDTO messageDTO);

List<MessageDTO> getReceivedMessagesForUser(Long userId);
List<MessageDTO> getMessagesForUser(Long userId);
}
