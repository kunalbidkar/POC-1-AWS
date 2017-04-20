package com.kunal.dao.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.kunal.dao.AWSDao;

@Repository
public class AWSDaoImpl implements AWSDao {
	AWSCredentials credentials = new BasicAWSCredentials("**Access key here**",
			"**Secret Key here**");

	// create a client connection based on credentials
	AmazonS3 s3client = new AmazonS3Client(credentials);

	public void addBucket1(String bucketName) {
		// TODO Auto-generated method stub
		System.out.println("Bucket name inside DAO " + bucketName);
		try {
			
			s3client.createBucket(bucketName);
			System.out.println("Bucket created");
		} catch (Exception e) {
			HttpServletRequest request = null;
			HttpServletResponse response = null;
			@SuppressWarnings("null")
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/403.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public List<String> listBuckets() {
		// TODO Auto-generated method stub
		// list buckets
		List<String> list = new ArrayList<String>();
		for (Bucket bucket : s3client.listBuckets()) {
			list.add(bucket.getName());
			// System.out.println(" - " + bucket.getName());
		}

		return list;
	}

	public void deleteObject() {
		// TODO Auto-generated method stub

	}

	public void downloadBucket(String bucketName) {
		// TODO Auto-generated method stub
		// code to download the object to your desktop.
		
		System.out.println("download bucket"+bucketName);
		
		ObjectListing listing = s3client.listObjects(bucketName);
		List<S3ObjectSummary> summaries = listing.getObjectSummaries();

		while (listing.isTruncated()) {
		   listing = s3client.listNextBatchOfObjects (listing);
		   summaries.addAll (listing.getObjectSummaries());
		}
		System.out.println(summaries.get(0));
		
		s3client.getObject(new GetObjectRequest(bucketName, "aws.xlsx"), new File("D:\\aws.xlsx"));

	}

	public void uploadObject() {
		// TODO Auto-generated method stub

	}

}
