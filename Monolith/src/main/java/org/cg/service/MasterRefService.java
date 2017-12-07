package org.cg.service;

import java.util.List;

import org.cg.Model.dto.MasterRefDTO;
import org.cg.Model.dto.MessageDTO;
import org.cg.Model.dto.NotificationDTO;
import org.cg.Model.dto.RequestDTO;
import org.cg.Model.dto.UserDTO;

public interface MasterRefService {
	
	public MasterRefDTO addMasterRef(MasterRefDTO user);
    public MasterRefDTO updateMasterRef(Long userId, MasterRefDTO user);
	public MasterRefDTO getMasterRef(Long userId);
	public void deleteMasterRef(Long UserId);
	public List<MessageDTO> getSentMessagesForUserID(Long userId);
	public List<MessageDTO> getReceivedMessagesForUserID(Long userId);
	public List<RequestDTO> getRequestsForUserID(Long userId);
	public List<NotificationDTO> getNotificationsForUserID(Long userId);
	
}
