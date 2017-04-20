package com.kunal.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.kunal.dao.impl.AWSDaoImpl;
import com.kunal.service.impl.AWSServiceImpl;

@Controller
public class CreateBucketController {
	private static final String SUFFIX = "/";
	AWSCredentials credentials = new BasicAWSCredentials("**Access Key here**",
			"**Secret Key here**");
	AmazonS3 s3client = new AmazonS3Client(credentials);

	@RequestMapping(value = "/CreateBucket")
	public ModelAndView redirect(HttpServletRequest request, HttpServletResponse response) {

		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/createBucketForm.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/ListBucket")
	public void listBucket(HttpServletRequest request, HttpServletResponse response) {
		AWSDaoImpl aw = new AWSDaoImpl();
		List<String> list = new ArrayList<String>();
		list = aw.listBuckets();
		System.out.println(list);
		request.setAttribute("list", list);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/listBuckets.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/UploadObjectToBucket")
	public void uploadToBucket(HttpServletRequest request, HttpServletResponse response) {
		AWSDaoImpl aw = new AWSDaoImpl();
		List<String> list = new ArrayList<String>();
		list = aw.listBuckets();
		System.out.println(list);
		request.setAttribute("list", list);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/addObject.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/DownloadObject")
	public void downloadObject(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AWSDaoImpl aw = new AWSDaoImpl();
		List<String> list = new ArrayList<String>();
		list = aw.listBuckets();
		System.out.println(list);
		request.setAttribute("list", list);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/downloadObjectForm.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/BucketForm")
	public void addBucket(HttpServletRequest request, HttpServletResponse response) {
		String bucketName = request.getParameter("bucketname");
		AWSServiceImpl aws = new AWSServiceImpl();
		aws.addBucket(bucketName);

	}

	@RequestMapping(value = "/Download")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		String decision = request.getParameter("dropdown");
		AWSServiceImpl aws = new AWSServiceImpl();
		aws.downloadBucket(decision);

	}

	@RequestMapping(value = "/CreateFolder")
	public void createFolder(HttpServletRequest request, HttpServletResponse response) {

		AWSDaoImpl aw = new AWSDaoImpl();
		List<String> list = new ArrayList<String>();
		list = aw.listBuckets();
		System.out.println(list);
		request.setAttribute("list", list);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/createFolder.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/Folder")
	public void folder(HttpServletRequest request, HttpServletResponse response) {
		String decision = request.getParameter("dropdown1");
		String folderName = request.getParameter("folder");
		String file = request.getParameter("file");
		System.out.println(file);
		createFolder(decision, folderName, s3client);

		// upload file to folder and set it to public
		String fileName = folderName + SUFFIX + file;
		s3client.putObject(new PutObjectRequest(decision, fileName, new File("D:\\" + file))
				.withCannedAcl(CannedAccessControlList.PublicRead));
	}

	public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + SUFFIX, emptyContent,
				metadata);
		// send request to S3 to create folder
		client.putObject(putObjectRequest);
	}

	@RequestMapping(value = "/Delete")
	public void deleteBucket(HttpServletRequest request, HttpServletResponse response) {
		String decision = request.getParameter("dropdown3");
		System.out.println(decision);
		s3client.deleteBucket(decision);
	}

	@RequestMapping(value = "/DeleteBucket")
	public void delete(HttpServletRequest request, HttpServletResponse response) {

		AWSDaoImpl aw = new AWSDaoImpl();
		List<String> list = new ArrayList<String>();
		list = aw.listBuckets();
		System.out.println(list);
		request.setAttribute("list", list);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/deleteBucket.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/AddObject")
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		String decision = request.getParameter("dropdown4");
		System.out.println(decision);
		String file = request.getParameter("addObject");
		System.out.println(file);
		s3client.putObject(new PutObjectRequest(decision, file, new File("D:\\" + file))
				.withCannedAcl(CannedAccessControlList.PublicRead));
	}

	@RequestMapping(value = "/DeleteFolder")
	public void deleteFolder(HttpServletRequest request, HttpServletResponse response) {

		AWSDaoImpl aw = new AWSDaoImpl();
		List<String> list = new ArrayList<String>();
		list = aw.listBuckets();
		System.out.println(list);
		request.setAttribute("list", list);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/deleteFolder.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/DeleteF")
	public void deleteF(HttpServletRequest request, HttpServletResponse response) {
		String decision = request.getParameter("dropdown5");
		String folder = request.getParameter("folder");
		System.out.println(folder);
		System.out.println(decision);
		deleteFolder(decision, folder, s3client);
	}

	public static void deleteFolder(String bucketName, String folderName, AmazonS3 client) {
		List<S3ObjectSummary> fileList = client.listObjects(bucketName, folderName).getObjectSummaries();
		for (S3ObjectSummary file : fileList) {
			client.deleteObject(bucketName, file.getKey());
		}
		client.deleteObject(bucketName, folderName);
	}

	@RequestMapping(value = "/ListContents")
	public void list(HttpServletRequest request, HttpServletResponse response) {

		AWSDaoImpl aw = new AWSDaoImpl();
		List<String> list = new ArrayList<String>();
		list = aw.listBuckets();
		System.out.println(list);
		request.setAttribute("list", list);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/bucketContents.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/ListContent")
	public void listBucketContent(HttpServletRequest request, HttpServletResponse response) {
		String decision = request.getParameter("dropdown10");
		ObjectListing listing = s3client.listObjects(new ListObjectsRequest().withBucketName(decision));
		List<String> list1 = new ArrayList<String>();
		for (S3ObjectSummary objectSummary : listing.getObjectSummaries()) {
			list1.add(objectSummary.getKey() + "---------------------- \t It's size = \t " + objectSummary.getSize()+"\t KB");
		}
		request.setAttribute("list1", list1);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/bucketContents1.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@RequestMapping(value = "/Download10")
	public void download1(HttpServletRequest request, HttpServletResponse response) {
		String decision = request.getParameter("download");
		
		s3client.getObject(
		        new GetObjectRequest("kunalanuj84542", decision),
		        new File("D:\\"+decision)
		);
		
		
	}

	

}
