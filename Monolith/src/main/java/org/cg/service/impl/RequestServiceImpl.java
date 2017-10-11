package org.cg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cg.Model.Notification;
import org.cg.Model.Request;
import org.cg.Model.dto.RequestDTO;
import org.cg.Model.dto.UserDTO;
import org.cg.repository.RequestRepository;
import org.cg.service.MotionCaptureService;
import org.cg.service.RequestService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequestServiceImpl implements RequestService {
	
	Logger logger = LoggerFactory.getLogger(RequestServiceImpl.class);
	DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Autowired
	RequestRepository requestRepository;
	
	@Autowired
	MotionCaptureService motionCaptureService;
	
	@Transactional
	@Override
	public RequestDTO addRequest(RequestDTO requestDTO) {
		Request request = convertDTOIntoEntity(requestDTO);
		logger.debug("Saving:{}",request);
		
		return convertEntityIntoDTO(requestRepository.save(request));
	}
	
	@Transactional
	@Override
	public RequestDTO updateRequest(RequestDTO requestDTO) {
		logger.debug("Update request:{}",requestDTO);
		Request request = requestRepository.findByRequestId(requestDTO.getRequestId());
		logger.debug("Updating request:{}",request);
		if(requestDTO.getDate()!=null)
		request.setDate(requestDTO.getDate());
		if(requestDTO.getDescription()!=null){
			request.setDescription(requestDTO.getDescription());
		}

		if(requestDTO.getMotionCapture()!=null)
		request.setMotionCapture(motionCaptureService.convertDTOintoEntity(requestDTO.getMotionCapture()));
		if(requestDTO.getName()!=null)
		request.setName(requestDTO.getName());
		if(requestDTO.getShortDescription()!=null)
		request.setShortDescription(requestDTO.getShortDescription());
		if(requestDTO.getResponseDate()!=null)
		request.setResponseDate(requestDTO.getResponseDate());
		RequestDTO response = convertEntityIntoDTO(requestRepository.save(request));
		return response;
	}
	
	@Transactional
	@Override
	public RequestDTO getRequest(Long requestId) {
		return convertEntityIntoDTO(requestRepository.findByRequestId(requestId));
		
	}
	
	@Transactional
	@Override
	public List<RequestDTO> getAllRequests() {
		List<RequestDTO> result = new ArrayList<RequestDTO>();
		List<Request> searchResult = (List<Request>) requestRepository.findAll();
		for(Request req : searchResult) {
			result.add(convertEntityIntoDTO(req));
		}
		logger.debug("Final list:{}",result);
		return result;
	}
	
	@Transactional
	@Override
	public void deleteRequest(Long requestId) {
		logger.debug("Deleting request with id:{}",requestId);
		requestRepository.delete(requestId);
		
	}
	
	@Transactional
	@Override
	public List<RequestDTO> getRequestForUserId(Long userId){
		List<RequestDTO> result = new ArrayList<RequestDTO>();
		List<Request> request = requestRepository.findRequestByUserId(userId);
		logger.debug("Found requests:{}",request);
		for(Request req:request){
			result.add(convertEntityIntoDTO(req));
		}
		return result;
	}

	@Transactional
	@Override
	public RequestDTO convertEntityIntoDTO(Request requestEntity){
		RequestDTO requestDTO = mapper.map(requestEntity,RequestDTO.class);
		if (requestDTO.getRequestId() == null) {
			requestDTO.setRequestId(requestEntity.getRequestId());
		}
		
		
		return requestDTO;
	}
	@Transactional
	@Override
	public Request convertDTOIntoEntity(RequestDTO requestDTO){
		
		Request request = mapper.map(requestDTO,Request.class);
		if(requestDTO.getMotionCapture()!=null) {
			request.setMotionCapture(motionCaptureService.convertDTOintoEntity(requestDTO.getMotionCapture()));		
		}
		
		return request;
	}

}
