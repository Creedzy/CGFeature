package org.cg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cg.Model.MasterRef;
import org.cg.Model.Message;
import org.cg.Model.Notification;
import org.cg.Model.Request;
import org.cg.Model.Role;
import org.cg.Model.User;
import org.cg.Model.dto.MasterRefDTO;
import org.cg.Model.dto.MessageDTO;
import org.cg.Model.dto.NotificationDTO;
import org.cg.Model.dto.RequestDTO;
import org.cg.Model.dto.RoleDTO;
import org.cg.Model.dto.UserDTO;
import org.cg.repository.MasterRefRepository;
import org.cg.repository.MessageRepository;
import org.cg.repository.NotificationRepository;
import org.cg.repository.RequestRepository;
import org.cg.repository.UserRepository;
import org.cg.service.MasterRefService;
import org.cg.service.MessageService;
import org.cg.service.NotificationService;
import org.cg.service.RequestService;
import org.cg.service.UserService;
import org.dozer.DozerBeanMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MasterRefServiceImpl implements MasterRefService {
	

Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
DozerBeanMapper mapper = new DozerBeanMapper();


@Autowired
MasterRefRepository masterRefRepository;

@Autowired
NotificationRepository notificationRepository;

@Autowired
MessageRepository messageRepository;

@Autowired
RequestRepository requestRepository;

@Autowired
UserRepository userRepository;

@Autowired
NotificationService notificationService;

@Autowired
MessageService messageService;

@Autowired
RequestService requestService;

@Autowired
UserService userService;

@Transactional
public MasterRefDTO addMasterRef(MasterRefDTO masterRefDto) {
		logger.debug("Create-masterRef:{}",masterRefDto);
		MasterRef masterRef = convertDtoIntoEntity(masterRefDto);
		
	
		logger.debug("Creating user:{}",masterRef.toString());
		masterRefRepository.save(masterRef);
		return convertEntityIntoDto(masterRef);
	}
	
	

	@Transactional
	@Override
	public MasterRefDTO updateMasterRef(Long userId, MasterRefDTO user) {
		MasterRef masterRef = masterRefRepository.findByUserId(userId);
		
		return null;
	}
	@Transactional
	@Override
	public MasterRefDTO getMasterRef(Long userId) {
		MasterRefDTO masterRefDTO = convertEntityIntoDto(masterRefRepository.findByUserId(userId));
		return masterRefDTO;
	}
	@Transactional
	@Override
	public void deleteMasterRef(Long userId) {
		MasterRef deleteMasterRef = masterRefRepository.findByUserId(userId);
		if(deleteMasterRef.getNotifications() != null) {
			for(Notification notif : deleteMasterRef.getNotifications()) {
				notificationRepository.delete(notif.getId());
			}
		}
		
		if(deleteMasterRef.getSentMessages()!= null) {
			for(Message message : deleteMasterRef.getSentMessages()) {
				messageRepository.delete(message.getMessageId());
			}
		}
		
		if(deleteMasterRef.getReceivedMessages()!= null) {
			for(Message message : deleteMasterRef.getReceivedMessages()) {
				messageRepository.delete(message.getMessageId());
			}
		}
		
		if(deleteMasterRef.getRequests()!=null) {
			for(Request request : deleteMasterRef.getRequests()) {
				requestRepository.delete(request.getRequestId());
			}
		}
		masterRefRepository.delete(deleteMasterRef.getMasterRefId());
		
	}
	
	
	
	@Transactional
	public MasterRefDTO convertEntityIntoDto(MasterRef masterRefEntity){
		MasterRefDTO masterRef = mapper.map(masterRefEntity,MasterRefDTO.class);
		if (masterRef.getUserId() == null) {
			masterRef.setUserId(masterRefEntity.getUserId());
		}
		if(masterRefEntity.getNotifications()!=null) {
			List<Notification> notificationsEntity = masterRefEntity.getNotifications();
			
		}
		
		return masterRef;
	}
	@Transactional
	public MasterRef convertDtoIntoEntity(MasterRefDTO masterRefDTO){
		
		MasterRef masterRef = mapper.map(masterRefDTO,MasterRef.class);
		if (masterRefDTO.getUserId()==null){
			masterRef.setUserId(masterRefDTO.getUserId());
		}

		
		return masterRef;
	}


	@Transactional
	@Override
	public List<MessageDTO> getSentMessagesForUserID(Long userId) {
		List<Message> messages =  messageRepository.findSentMessagesByUserId(userId);
		List<MessageDTO> result = new ArrayList<MessageDTO>();
		for(Message message : messages) {
			result.add(messageService.convertEntityIntoDto(message));
		}
		return result;
	}


	@Transactional
	@Override
	public List<MessageDTO> getReceivedMessagesForUserID(Long userId) {
		List<Message> messages =  messageRepository.findReceivedMessagesByUserId(userId);
		List<MessageDTO> result = new ArrayList<MessageDTO>();
		for(Message message : messages) {
			result.add(messageService.convertEntityIntoDto(message));
		}
		return result;
	}


	@Transactional
	@Override
	public List<RequestDTO> getRequestsForUserID(Long userId) {
		logger.debug("In masterRef service. GetRequests for User.");
		return requestService.getRequestForUserId(userId);
	}


	@Transactional
	@Override
	public List<NotificationDTO> getNotificationsForUserID(Long userId) {
		logger.debug("In masterRef service. Get notifications for user:{}",userId);
		return notificationService.getNotificationsForUser(userId);
	}

}
