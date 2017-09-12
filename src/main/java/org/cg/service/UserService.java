package org.cg.service;

import org.cg.Model.User;
import org.cg.Model.dto.UserDTO;

import java.util.List;
public interface UserService  {

	public UserDTO addUser(UserDTO user);
    public UserDTO updateUser(Long userId, UserDTO user);
	public UserDTO getUser(Long userId);
	public UserDTO getUserByUsername(String username);
	public List<UserDTO> getAllUsers();
	public void deleteUser(Long UserId);
	public UserDTO getUserByEmail(String email);
	
}
