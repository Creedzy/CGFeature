package org.cg.repository;

import org.cg.Model.Message;
import org.cg.Model.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.Long;
import java.util.List;
import org.joda.time.DateTime;
import java.lang.String;

@Repository
public interface RequestRepository extends CrudRepository<Request,Long> {
Request findByRequestId(Long requestid);
List<Request> findByResponseDate(DateTime responsedate);
List<Request> findBySubmittionDate(DateTime submittiondate);
List<Request> findByName(String name);

@Query(value = "SELECT d FROM Request d where d.owner.userId = :userId")
List<Request> findRequestByUserId(@Param("userId") Long userId);
}
