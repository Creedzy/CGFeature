package org.cg.repository;

import org.cg.Model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.cg.Model.User;
import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long>{
List<Role> findByUser(User user);
}
