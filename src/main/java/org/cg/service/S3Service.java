package org.cg.service;

import java.util.List;

import com.amazonaws.services.s3.model.Bucket;



public interface S3Service  {

    public List<Bucket> listBuckets();
   
}
