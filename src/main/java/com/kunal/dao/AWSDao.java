package com.kunal.dao;

import java.util.List;

public interface AWSDao {
	public void addBucket1(String bucketName);

	public void downloadBucket(String bucketName);

	public List<String> listBuckets();

	public void deleteObject();

	public void uploadObject();
}
