package org.cg.repository;

import org.cg.Model.MasterRef;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.cg.Model.User;
import java.util.List;
import java.lang.Long;
import java.lang.String;

@Repository
public interface MasterRefRepository extends CrudRepository<MasterRef,Long>{
MasterRef findByMasterRefId(Long masterrefid);
MasterRef findByUserId(Long userid);
}
