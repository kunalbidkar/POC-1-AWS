package com.kunal.service;

import java.util.List;

public interface AWSService {
	public void addBucket(String bucketName);

	public void downloadBucket(String bucketName);

	public List<String> listBuckets();

	public void deleteObject();

	public void uploadObject();
}
