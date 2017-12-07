package org.cg.rest;

import java.util.List;
import java.util.ArrayList;
import org.cg.Model.User;
import org.cg.Model.dto.UserDTO;
import org.cg.service.UserService;
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
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@RequestMapping(value="/user/{id}" , method=RequestMethod.GET)
	public UserDTO getUser(@PathVariable("id") Long userId) {
		logger.debug("Getting user with id:{}",userId);
		UserDTO user = userService.getUser(userId);
		return user;

	}
	@RequestMapping("/user")
	public List<UserDTO> getAllUsers() {
		logger.debug("Returning all users");
		List<UserDTO> users = userService.getAllUsers();
		return users;
	}

	@RequestMapping(value="/user", method= RequestMethod.POST) 
	public UserDTO addUser(@RequestBody UserDTO user) {
		logger.debug("Preparing to add user:{}",user);
		UserDTO newUser = userService.addUser(user);
		return newUser;
	}

	@RequestMapping(value="/user/{id}", method=RequestMethod.DELETE)
	public void deleteUser(@PathVariable("id") Long userId){
		logger.debug("Preparing to delete user with id:{}",userId.toString());
		userService.deleteUser(userId);

	}

	@RequestMapping(value="/user/{id}", method=RequestMethod.PUT)
	public UserDTO updateUser(@PathVariable Long id,@RequestBody UserDTO user){
		logger.debug("In update user controller, updating user:{}",user);
		UserDTO updatedUser = userService.updateUser(id,user);
		return updatedUser;
	}

}
