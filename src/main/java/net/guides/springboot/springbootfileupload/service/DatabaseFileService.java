package net.guides.springboot.springbootfileupload.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.guides.springboot.springbootfileupload.exception.FileNotFoundException;
import net.guides.springboot.springbootfileupload.exception.FileStorageException;
import net.guides.springboot.springbootfileupload.model.DatabaseFile;
import net.guides.springboot.springbootfileupload.repository.DatabaseFileRepository;

@Service
public class DatabaseFileService {
	
	@Autowired
	private DatabaseFileRepository databaseFileRepository;
	
	public DatabaseFile storeFile(MultipartFile file) {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException(String.format(
						"Filename contains invalid path sequence %s",
						fileName));
			}
			DatabaseFile dbFile = new DatabaseFile(fileName,
					file.getContentType(), file.getBytes());
			
			return databaseFileRepository.save(dbFile);
			
		} catch (IOException ex) {
			throw new FileStorageException(
					String.format("Could not store file %s. Please try again!",
							fileName),
					ex);
		}
	}
	
	public DatabaseFile getFile(String fileId) {
		return databaseFileRepository.findById(fileId)
				.orElseThrow(() -> new FileNotFoundException(
						String.format("File not found with id %s", fileId)));
	}
}
