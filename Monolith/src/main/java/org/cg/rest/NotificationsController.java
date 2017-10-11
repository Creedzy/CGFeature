package org.cg.rest;

import java.util.List;
import java.util.ArrayList;

import org.cg.Model.Notification;
import org.cg.Model.User;
import org.cg.Model.dto.NotificationDTO;
import org.cg.service.NotificationService;
import org.cg.service.impl.NotificationServiceImpl;
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
public class NotificationsController
{


	public static final Logger logger = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	NotificationService notificationsService;

	@RequestMapping(value="/notification/{id}" , method=RequestMethod.GET)
	public NotificationDTO getNotification(@PathVariable("id") Long notificationId) {
		logger.debug("Getting message with id:{}",notificationId);
		NotificationDTO notificationDto = notificationsService.getNotification(notificationId);
		return notificationDto;

	}
	@RequestMapping(value = "/notification/users/{id}", method=RequestMethod.GET)
	public List<NotificationDTO> getAllNotifications(@PathVariable("id") String userId) {
		logger.debug("Returning all users");
		List<NotificationDTO> notifications = notificationsService.getNotificationsForUser(Long.parseLong(userId));
		return notifications;
	}

	@RequestMapping(value="/notification", method= RequestMethod.POST) 
	public NotificationDTO addUser(@RequestBody NotificationDTO notification) {
		logger.debug("Preparing to add notification;{}");
		NotificationDTO notificationDto = notificationsService.saveNotification(notification);
		return notificationDto;
	}

	@RequestMapping(value="/notification", method=RequestMethod.DELETE)
	public void deleteUser(@RequestBody NotificationDTO notification){
		logger.debug("Preparing to delete user with id:{}",notification);
		notificationsService.deleteNotification(notification.getId());

	}


}
