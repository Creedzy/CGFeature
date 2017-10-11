package org.cg.rest;

import java.util.List;
import java.util.ArrayList;

import org.cg.Model.Message;
import org.cg.Model.User;
import org.cg.Model.dto.MessageDTO;
import org.cg.service.MessageService;
import org.cg.service.impl.MessageServiceImpl;
import org.cg.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController
{


	public static final Logger logger = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	MessageService messageService;

	@RequestMapping(value="/message/{id}" , method=RequestMethod.GET)
	public MessageDTO getUser(@PathVariable("id") Long messageId) {
		logger.debug("Getting message with id:{}",messageId);
		MessageDTO message = messageService.getMessage(messageId);
		return message;

	}
	@RequestMapping(value = "/message/users/{id}", method=RequestMethod.GET)
	public List<MessageDTO> getAllMessages(@PathVariable("id") String userId) {
		logger.debug("Returning all users");
		List<MessageDTO> messages = messageService.getMessagesForUser(Long.parseLong(userId));
		return messages;
	}

	@RequestMapping(value="/message", method= RequestMethod.POST) 
	public MessageDTO addMessage(@RequestBody MessageDTO message) {
		logger.debug("Preparing to add user");
		MessageDTO newMessage = messageService.saveMessage(message);
		return newMessage;
	}

	@RequestMapping(value="/message", method=RequestMethod.DELETE)
	public void deleteMessage(@RequestBody MessageDTO message){
		logger.debug("Preparing to delete user with id:{}",message);
		messageService.deleteMessage(message.getMessageId());

	}

	
}
