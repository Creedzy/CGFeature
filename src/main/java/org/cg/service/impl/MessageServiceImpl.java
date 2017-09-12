package org.cg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cg.Model.Message;
import org.cg.Model.Role;
import org.cg.Model.User;
import org.cg.Model.dto.MessageDTO;
import org.cg.Model.dto.RoleDTO;
import org.cg.Model.dto.UserDTO;
import org.cg.repository.MessageRepository;
import org.cg.service.MessageService;
import org.dozer.DozerBeanMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageServiceImpl implements MessageService {
	
	Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Autowired
	MessageRepository messageRepository;
	
	@Transactional
	@Override
	public MessageDTO getMessage(Long messageId) {
		MessageDTO message = convertEntityIntoDto(messageRepository.findByMessageId(messageId));
		
		logger.debug("Returning message:{}",message);
		
		return message;
	}
	
	@Transactional
	@Override
	public List<MessageDTO> getMessagesForUser(Long userId) {
		List<Message> sentmessages = messageRepository.findReceivedMessagesByUserId(userId);
		List<Message> received = messageRepository.findSentMessagesByUserId(userId);
		List<MessageDTO> messageList = new ArrayList<MessageDTO>();
		for(Message message : sentmessages) {
			messageList.add(convertEntityIntoDto(message));
		}
		for(Message message : received) {
			for(MessageDTO existing: messageList) {
				if(message.getMessageId()!=existing.getMessageId()){
					messageList.add(convertEntityIntoDto(message));
				}
			}
			
		}
		logger.debug("Returning message:{}",messageList);
		return messageList;
	}
	
	@Transactional
	@Override
	public List<MessageDTO> getAllMessages() {

		List<Message> messages = (List<Message>) messageRepository.findAll();
		List<MessageDTO> messageList = new ArrayList<MessageDTO>();
		for(Message message : messages) {
			messageList.add(convertEntityIntoDto(message));
		}
		logger.debug("Returning message:{}",messageList);
		return messageList;
	}
	
	@Transactional
	@Override
	public MessageDTO saveMessage(MessageDTO message) {
		
		Message messageEntity = convertDtoIntoEntity(message);
		logger.debug("Saving message:{}",messageEntity);
		messageRepository.save(messageEntity);
		MessageDTO messageDTO = convertEntityIntoDto(messageEntity);
		return messageDTO;
	}
	
	@Transactional
	@Override
	public void deleteMessage(Long id) {
		logger.debug("Deleting:{}",id );
		messageRepository.delete(messageRepository.findByMessageId(id));
	}
	
	@Transactional
	@Override
	public MessageDTO convertEntityIntoDto(Message message){
		MessageDTO messageDTO = mapper.map(message,MessageDTO.class);
		if (messageDTO.getMessageId() == null) {
			messageDTO.setMessageId(message.getMessageId());
		}
		return messageDTO;
	}
	
	@Transactional
	@Override
	public Message convertDtoIntoEntity(MessageDTO messageDTO){
		
		Message message = mapper.map(messageDTO,Message.class);
		if (message.getMessageId()==null){
			message.setMessageId(messageDTO.getMessageId());
		}
		
		return message;
	}

	@Override
	public List<MessageDTO> getMessagesForUser(User userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MessageDTO> getReceivedMessagesForUser(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
