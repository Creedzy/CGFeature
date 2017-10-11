package org.cg.service;

import java.util.List;

import org.cg.Model.MotionCapture;
import org.cg.Model.dto.MotionCaptureDTO;

public interface MotionCaptureService {


	public MotionCaptureDTO getMotionCapture(Long mcId);
	public MotionCaptureDTO saveMotionCapture(MotionCaptureDTO mc);
	public void deleteMotionCapture(MotionCaptureDTO mcDto);
	public List<MotionCaptureDTO> getAllMC();
	public List<MotionCaptureDTO> getMCForUploader(String userId);
	public List<MotionCaptureDTO> searchMotionCapture(String... criteria);
	MotionCaptureDTO convertEntityIntoDTO(MotionCapture motionCapture);
	MotionCapture convertDTOintoEntity(MotionCaptureDTO motionCaptureDTO);
	
}
