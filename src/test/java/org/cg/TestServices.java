package org.cg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.cg.Model.User;
import org.cg.Model.dto.MasterRefDTO;
import org.cg.Model.dto.MessageDTO;
import org.cg.Model.dto.NotificationDTO;
import org.cg.Model.dto.RequestDTO;
import org.cg.Model.dto.RoleDTO;
import org.cg.Model.dto.UserDTO;
import org.cg.config.ApplicationConfig;
import org.cg.config.DatabaseConfig;
import org.cg.config.SecurityConfig;
import org.cg.service.MasterRefService;
import org.cg.service.MessageService;
import org.cg.service.NotificationService;
import org.cg.service.RequestService;
import org.cg.service.UserService;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.jdbc.rds.AmazonRdsDataSourceFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes={DatabaseConfig.class,ApplicationConfig.class,SecurityConfig.class})
@ActiveProfiles("prod")
public class TestServices {
	
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	RequestService requestService;
	
	@Autowired
	MasterRefService masterRefService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	DataSource dataSource;
	
	@Before
	public void init() {
		
		
		
	}
	
	//@Test
	public void testUserService() {
		createUser("nickname");
		
		//Get list
		List<UserDTO> users = userService.getAllUsers();
		assertNotNull(users);
		System.out.println(users);
		//Get User
		UserDTO userGet = userService.getUser(2L);
		System.out.println(userGet);
		System.out.println(userGet.getRoles());
		assertEquals("ADMIN",userGet.getRoles().get(0).getRoleName());
		assertEquals("NAME",userGet.getName());
		assertEquals("EMAIL",userGet.getEmail());
		//Update user
		userGet.setUsername("nickname");
		
		userService.updateUser(2L, userGet);
		UserDTO updated = userService.getUser(2L);
		assertEquals("nickname",updated.getUsername());
		//Delete user
		userService.deleteUser(2L);
		
		try {
			userService.getUser(2L);
		} catch (Exception e){
			System.out.println("deleted");
			
		}
	}
	
	//@Test
	public void testNotificationService() {
		createUser("Subject");
		UserDTO userDTO = userService.getUserByUsername("Subject");
		NotificationDTO notificationDTO = new NotificationDTO();
		notificationDTO.setSender("System");
		notificationDTO.setReceiver(userDTO);
		notificationDTO.setMessage("message");
		notificationService.saveNotification(notificationDTO);
		
		List<NotificationDTO> notif = notificationService.getNotificationsForUser(userDTO.getUserId());
		assertNotNull(notif);
		assertEquals("message",notif.get(0).getMessage());
		
		NotificationDTO notificationDTO2 = new NotificationDTO();
		notificationDTO2.setSender("System");
		notificationDTO2.setReceiver(userDTO);
		notificationDTO2.setMessage("message2");
		notificationService.saveNotification(notificationDTO2);
	
		
		List<NotificationDTO> list = notificationService.getAllNotifications();
		assertNotNull(list);
		assertEquals(2,list.size());
		
		notificationService.deleteNotification(notif.get(0).getId());
		try {
			notificationService.getNotification(notif.get(0).getId());
		} catch (Exception e){
			System.out.println("deleted");
			
		}
		
	}
	
	//@Test
	public void testRequestService() {
		createUser("requests");
		UserDTO userDTO = userService.getUserByUsername("requests");
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setCompleted(false);
		requestDTO.setDescription("description");
		requestDTO.setName("name");
		requestDTO.setOwner(userDTO);
		requestService.addRequest(requestDTO);
		requestDTO.setOwner(null);
		requestService.addRequest(requestDTO);
		
		List<RequestDTO> requests=requestService.getAllRequests();
		assertNotNull(requests);
		assertEquals("name",requests.get(0).getName());
		assertEquals(2,requests.size());
		List<RequestDTO> userRequests = requestService.getRequestForUserId(userDTO.getUserId());
		assertNotNull(userRequests);
		assertEquals("name",requests.get(0).getName());
		assertEquals(1,userRequests.size());
		
		RequestDTO toUpdate = requests.get(0);
		toUpdate.setName("newname");
		RequestDTO updated = requestService.updateRequest(toUpdate);
		assertEquals("newname",updated.getName());
		
		requestService.deleteRequest(updated.getRequestId());
		
		try {
			requestService.getRequest(updated.getRequestId());
		} catch (Exception e){
			System.out.println("deleted request");
			
		}
		
		
	}
	
	@Test
	public void testMessageService() {
		createUser("sender");
		createUser("receiver");
		UserDTO sender = userService.getUserByUsername("sender");
		UserDTO receiver = userService.getUserByUsername("receiver");
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setMessage("Hello.");
		messageDTO.setReceiver(receiver);
		messageDTO.setSender(sender);
		//messageDTO.setDate(DateTime.now());
		messageService.saveMessage(messageDTO);
	}
	

	
	
	
	//@Test 
		public void testConnetion(){
			
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.getEnvironment().addActiveProfile("prod");
			ctx.register(ApplicationConfig.class,DatabaseConfig.class);
			ctx.refresh();
			try {
				System.out.println(dataSource.getConnection().isValid(30));
				System.out.println(dataSource.getClass().getSimpleName());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void createUser(String username) {
			UserDTO user = new UserDTO();
			RoleDTO role = new RoleDTO();
			
			role.setRoleName("ADMIN");
			List<RoleDTO> roles = new ArrayList<RoleDTO>();
			roles.add(role);
			
			user.setEmail("EMAIL");
			user.setName("NAME");
			user.setPassword("Password");
			user.setActivated(true);
			user.setEmailActive(true);
			user.setContactPreference("EMAIL");
			user.setRoles(roles);
			user.setUsername(username);
			userService.addUser(user);
		}
	
	
	
}
