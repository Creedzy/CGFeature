package org.cg.service;

import java.util.List;

import org.cg.Model.User;
import org.cg.Model.dto.*;




public interface NotificationService {


	public NotificationDTO getNotification(Long messageId);
	public NotificationDTO saveNotification(NotificationDTO message);
	
	public List<NotificationDTO> getAllNotifications();
	
	void deleteNotification(Long id);
	List<NotificationDTO> getNotificationsForUser(Long userId);
}
