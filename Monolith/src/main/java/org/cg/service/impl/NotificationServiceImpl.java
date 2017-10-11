package org.cg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cg.Model.Notification;
import org.cg.Model.User;
import org.cg.Model.dto.NotificationDTO;
import org.cg.Model.dto.UserDTO;
import org.cg.repository.NotificationRepository;
import org.cg.service.MessageService;
import org.dozer.DozerBeanMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.cg.service.*;

@Service
public class NotificationServiceImpl implements NotificationService {

	Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	DozerBeanMapper mapper = new DozerBeanMapper();

	@Autowired
	NotificationRepository notificationRepository;

	@Transactional
	@Override
	public NotificationDTO getNotification(Long notificationId) {
		NotificationDTO notification = convertEntityIntoDto(notificationRepository.findById(notificationId).get());

		logger.debug("Returning notification:{}",notification);

		return notification;
	}
	
	@Transactional
	@Override
	public List<NotificationDTO> getNotificationsForUser(Long userId) {
		List<Notification> notifications = notificationRepository.findNotificationsForUserId(userId);
		List<NotificationDTO> notificationList = new ArrayList<NotificationDTO>();
		for(Notification notif : notifications) {
			notificationList.add(convertEntityIntoDto(notif));
		}
		logger.debug("Returning notifications for user:{}",notificationList);
		return notificationList;
	}
	
	@Transactional
	@Override
	public List<NotificationDTO> getAllNotifications() {

		List<Notification> notifications = (List<Notification>) notificationRepository.findAll();
		List<NotificationDTO> notificationList = new ArrayList<NotificationDTO>();
		for(Notification notif : notifications) {
			notificationList.add(convertEntityIntoDto(notif));
		}
		logger.debug("Returning notification List:{}",notificationList);
		return notificationList;
	}
	
	@Transactional
	@Override
	public NotificationDTO saveNotification(NotificationDTO notificationDto) {

		Notification notificationEntity = convertDtoIntoEntity(notificationDto);
		logger.debug("Saving notification:{}",notificationEntity);
		notificationRepository.save(notificationEntity);
		NotificationDTO notifDTO = convertEntityIntoDto(notificationEntity);
		return notifDTO;
	}
	
	@Transactional
	@Override
	public void deleteNotification(Long id) {
		logger.debug("Deleting:{}",id );
		notificationRepository.delete(id);
	}
	
	@Transactional
	public NotificationDTO convertEntityIntoDto(Notification notification){
		NotificationDTO notificationDTO = mapper.map(notification,NotificationDTO.class);
		if (notificationDTO.getMessage() == null) {
			notificationDTO.setMessage(notification.getMessage());
		}
		return notificationDTO;
	}

	@Transactional
	public Notification convertDtoIntoEntity(NotificationDTO notificationDTO){

		Notification notif = mapper.map(notificationDTO,Notification.class);
		if (notif.getMessage()==null){
			notif.setMessage(notificationDTO.getMessage());
		}

		return notif;
	}


}
