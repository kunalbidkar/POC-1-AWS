package com.kunal.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kunal.dao.impl.AWSDaoImpl;
import com.kunal.service.AWSService;

@Service
public class AWSServiceImpl implements AWSService {

	
	AWSDaoImpl aws = new AWSDaoImpl();


	public void addBucket(String bucketName) {
		// TODO Auto-generated method stub
		System.out.println("Bucket value inside Service"+bucketName);
		aws.addBucket1(bucketName);
	}

	
	public void downloadBucket(String bucketName) {
		// TODO Auto-generated method stub
		this.aws.downloadBucket(bucketName);
	}


	public List<String> listBuckets() {
		// TODO Auto-generated method stub
		return this.aws.listBuckets();
	}

	
	public void deleteObject() {
		// TODO Auto-generated method stub
		this.aws.deleteObject();
	}

	public void uploadObject() {
		// TODO Auto-generated method stub
		this.aws.uploadObject();

	}

}
