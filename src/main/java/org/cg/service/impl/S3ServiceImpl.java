package org.cg.service.impl;

import java.util.List;

import org.cg.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;

@Service
public class S3ServiceImpl implements S3Service {
    
    @Autowired
    AmazonS3 s3;
    
    @Override
    public List<Bucket> listBuckets() {
        List<Bucket> buckets = s3.listBuckets();
        return buckets;
    }

}
