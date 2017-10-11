package org.cg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cg.Model.MotionCapture;
import org.cg.Model.dto.MotionCaptureDTO;
import org.cg.repository.MotionCaptureRepository;
import org.cg.service.MotionCaptureService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MotionCaptureServiceImpl implements MotionCaptureService
{
	Logger logger = LoggerFactory.getLogger(MotionCaptureServiceImpl.class);
	DozerBeanMapper mapper = new DozerBeanMapper();
	@Autowired
	MotionCaptureRepository motionCaptureRepository;

	@Transactional
	@Override
	public MotionCaptureDTO getMotionCapture(Long mcId) {
		MotionCaptureDTO result = convertEntityIntoDTO(motionCaptureRepository.findById(mcId).get());
		logger.debug("Motion capture DTO:{}",result);
		return result;
	}
	@Transactional
	@Override
	public MotionCaptureDTO saveMotionCapture(MotionCaptureDTO mc) {
		MotionCapture motionCapture = motionCaptureRepository.save(convertDTOintoEntity(mc));
		logger.debug("MotionCapture:{}",motionCapture);
		return convertEntityIntoDTO(motionCapture);
	}
	@Transactional
	@Override
	public void deleteMotionCapture(MotionCaptureDTO mcDto) {
		logger.debug("Deleting Motion capture entity:{}",mcDto);
		motionCaptureRepository.delete(mcDto.getId());
		
	}
	@Transactional
	@Override
	public List<MotionCaptureDTO> getAllMC() {
		List<MotionCaptureDTO> result = new ArrayList<MotionCaptureDTO>();
		List<MotionCapture> motionCaptures = (List<MotionCapture>) motionCaptureRepository.findAll();
		for(MotionCapture mc : motionCaptures) {
			result.add(convertEntityIntoDTO(mc));
		}
		return result;
	}
	@Transactional
	@Override
	public List<MotionCaptureDTO> getMCForUploader(String userId) {
		List<MotionCaptureDTO> result = new ArrayList<MotionCaptureDTO>();
		List<MotionCapture> searchResult = motionCaptureRepository.findMCsByUploaderId(Long.parseLong(userId));
		for(MotionCapture mc : searchResult) {
			result.add(convertEntityIntoDTO(mc));
		}
		return result;
	}
	@Transactional
	@Override
	public List<MotionCaptureDTO> searchMotionCapture(String... criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	@Transactional
	@Override
	public MotionCaptureDTO convertEntityIntoDTO(MotionCapture motionCapture) {
		MotionCaptureDTO motionCaptureDTO = mapper.map(motionCapture, MotionCaptureDTO.class);
		return motionCaptureDTO;
	}
	@Transactional
	@Override
	public MotionCapture convertDTOintoEntity(MotionCaptureDTO motionCaptureDTO) {
		MotionCapture motionCapture = mapper.map(motionCaptureDTO, MotionCapture.class);
		
		return motionCapture;
	}
	
}