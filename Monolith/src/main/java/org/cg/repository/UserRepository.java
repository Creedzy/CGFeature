package org.cg.repository;

import org.cg.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.lang.String;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
	User findByUserId(Long userId);
	User findByUsername(String username);
	User findByEmail(String email);
}
