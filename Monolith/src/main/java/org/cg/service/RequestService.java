package org.cg.service;

import java.util.List;

import org.cg.Model.Request;
import org.cg.Model.dto.RequestDTO;
import org.cg.Model.dto.UserDTO;

public interface RequestService {
	
	
	public RequestDTO addRequest(RequestDTO request);
    public RequestDTO updateRequest(RequestDTO request);
	public RequestDTO getRequest(Long requestId);
	
	public List<RequestDTO> getAllRequests();
	public void deleteRequest(Long requestId);
	RequestDTO convertEntityIntoDTO(Request requestEntity);
	Request convertDTOIntoEntity(RequestDTO requestDTO);
	List<RequestDTO> getRequestForUserId(Long userId);
	
}
