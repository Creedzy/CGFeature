package org.cg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cg.Model.MasterRef;
import org.cg.Model.Role;
import org.cg.Model.User;
import org.cg.Model.dto.MasterRefDTO;
import org.cg.Model.dto.RoleDTO;
import org.cg.Model.dto.UserDTO;
import org.cg.repository.MasterRefRepository;
import org.cg.repository.UserRepository;
import org.cg.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.dozer.DozerBeanMapper;
import org.joda.time.DateTime;
@Service
public class UserServiceImpl  implements UserService{

Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
DozerBeanMapper mapper = new DozerBeanMapper();

@Autowired
UserRepository userRepository;

@Autowired
MasterRefRepository masterRefRepository;

@Transactional
	public UserDTO addUser(UserDTO addUser) {
		logger.debug("Create-userDTO:{}",addUser);
		User user = convertDtoIntoEntity(addUser);
		
		MasterRef masterRef = new MasterRef();
		masterRef.setUserId(user.getUserId());
		logger.debug("Creating user:{}",user.toString());
		masterRefRepository.save(masterRef);
		userRepository.save(user);
		return convertEntityIntoDto(user);
	}
	@Transactional
	@Override
	public UserDTO updateUser(Long userId, UserDTO updateUser) {
		
		
		User user = convertDtoIntoEntity(updateUser);
		if(  updateUser.getUserId() == null){
			user.setUserId(userId);
		}
		
		logger.debug("About to update user:{}, user role:{}",user,user.getRole().toString());
		userRepository.save(user);
		return convertEntityIntoDto(user);
	}

	@Override
	@Transactional
	public UserDTO getUser(Long userId) {
		UserDTO userDto = null;
		User user = userRepository.findByUserId(userId);
		if(user != null){
			userDto = convertEntityIntoDto(user);
		}
		logger.debug("returning user : {}",userDto.toString());
		return userDto;
	}

	@Transactional
	@Override
	public UserDTO getUserByUsername(String username) {
		UserDTO userDTO = null;
		User user = userRepository.findByUsername(username);
		logger.debug("Found user:{}",user);
		userDTO = convertEntityIntoDto(user);
		return userDTO;
	}
	
	@Transactional
	@Override
	public UserDTO getUserByEmail(String email) {
		UserDTO userDTO = null;
		User user = userRepository.findByEmail(email);
		logger.debug("Found user:{}",user);
		if(user != null) {
			userDTO = convertEntityIntoDto(user);
			return userDTO;
		}
		return null;
	}

	@Override
	@Transactional
	public List<UserDTO> getAllUsers() {
		List<UserDTO> userDto = new ArrayList<UserDTO>();
		List<User> userList = (List<User>) userRepository.findAll(); 
	
		logger.debug("Users returned:{}",userList);
		for(User user : userList ){
			userDto.add(convertEntityIntoDto(user));
		}
		logger.debug("Returning all users:{}",userDto);
		return userDto;
	}

	@Override
	@Transactional
	public void deleteUser(Long userId) {
		logger.debug("Deleting user with id:{}",userId);
		
		userRepository.delete(userRepository.findByUserId(userId));
		
	}

	
	
	
	public UserDTO convertEntityIntoDto(User userEntity){
		UserDTO user = mapper.map(userEntity,UserDTO.class);
		if (user.getUserId() == null) {
			user.setUserId(userEntity.getUserId());
		}
		
		if(userEntity.getRole() != null) {
			List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
			for(Role role : userEntity.getRole()){
				
				RoleDTO temp = mapper.map(role, RoleDTO.class);
				if(temp.getRoleName()==null) {
					temp.setRoleName(role.getRole());
				}
				roleDTOs.add(temp);
			}
			user.setRoles(roleDTOs);
		}
		
		return user;
	}
	
	public User convertDtoIntoEntity(UserDTO userDTO){
		
		User user = mapper.map(userDTO,User.class);
		if (user.getUserId()==null){
			user.setUserId(userDTO.getUserId());
		}
		
		if(userDTO.getRoles() != null) {
			List<Role> roles = new ArrayList<Role>();
			for(RoleDTO role : userDTO.getRoles()) {			
				Role toAdd = mapper.map(role, Role.class);
				toAdd.setUser(user);
				if(role.getRoleName() != null) {
					toAdd.setRole(role.getRoleName());
				}
				DateTime date = new DateTime();
				toAdd.setDate(date.toDate());
				logger.debug("Adding role to user:{}",toAdd.toString());
				roles.add(toAdd);
			}
			
			user.setRole(roles);
		}
		return user;
	}
	
}
