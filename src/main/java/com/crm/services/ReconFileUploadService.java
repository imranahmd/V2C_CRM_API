package com.crm.services;

import java.awt.print.Pageable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import com.crm.model.ReconRecord;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReconFileUploadService {
	private final Path fileLocation;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	/*
	 * @Autowired(required = true) ReconRecord reconDadta;
	 */
	public ReconFileUploadService(Environment env) {
		this.fileLocation = Path.of("file.upload.location","./home/reconUploads").toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.fileLocation);
		}catch (Exception ex) {
			throw new RuntimeException(
					"Could not create directory where the uploaded file will be stored.",ex);
		}
	}
	
	private String getFileExtension(String fileName) {
		if(fileName == null) {
			return null;
		}
		String[] fileNameParts = fileName.split("\\.");
		return fileNameParts[fileNameParts.length-1];
		
	}
	
	public String saveFile(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		
		ArrayList arrayList = new ArrayList();
		
		try {
			if(fileName.contains("..")) {
				throw new RuntimeException("Sorry! Filename contains invalid path sequence"+fileName);
			}
		
			Path uploadLocation = this.fileLocation.resolve(fileName);
			Files.copy(file.getInputStream(), uploadLocation, StandardCopyOption.REPLACE_EXISTING);
			
			
			arrayList.add(fileName);
			arrayList.add(uploadLocation.toString());
			Gson gson = new Gson();
			String jsonArray = gson.toJson(arrayList);
			return jsonArray.toString();
		}catch (IOException ex) {
			throw new RuntimeException("Could not save file "+fileName+". Please try again!",ex);
		}
	}
	
	public void deleteFile(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		Path uploadLocation = this.fileLocation.resolve(fileName);
		try {
			Files.delete(uploadLocation);
		}catch (NoSuchFileException ex) {
			throw new RuntimeException("No such file or directory",ex);
		}catch (IOException ex) {
			ex.printStackTrace();
		}
	}
		
	public List<ReconRecord> getReconFileList(String ReconDate) {
		final Query query = new Query();
		final List<Criteria> criteria = new ArrayList<>();
		if (!ReconDate.equals("")&& ReconDate!=null && !ReconDate.isEmpty()) {
			criteria.add(Criteria.where("UploadedOn").is(ReconDate));
		} 
		return mongoTemplate.find(query, ReconRecord.class);
	}	
}
