package org.cg.repository;

import org.cg.Model.Message;
import org.cg.Model.MotionCapture;

import java.lang.Long;
import java.util.List;
import java.util.Optional;

import org.cg.Model.Request;
import org.cg.Model.User;
import java.lang.String;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MotionCaptureRepository extends CrudRepository<MotionCapture,Long> {
Optional<MotionCapture> findById(Long id);
MotionCapture findByRequest(Request request);
List<MotionCapture> findByUploader(User uploader);
List<MotionCapture> findByFormat(String format);
List<MotionCapture> findByPublished(DateTime published);
List<MotionCapture> findByDownloads(Long downloads);

@Query(value = "SELECT d FROM MotionCapture d where d.uploader.userId = :userId")
List<MotionCapture> findMCsByUploaderId(@Param("userId") Long userId);
}
