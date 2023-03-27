package net.guides.springboot.springbootfileupload.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import net.guides.springboot.springbootfileupload.model.DatabaseFile;
import net.guides.springboot.springbootfileupload.service.DatabaseFileService;

@RestController
public class FileDownloadController {
	
	@Autowired
	private DatabaseFileService databaseFileService;
	
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,
			HttpServletRequest request) {
		DatabaseFile databaseFile = databaseFileService.getFile(fileName);
		
		return ResponseEntity.ok()
				.contentType(
						MediaType.parseMediaType(databaseFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + databaseFile.getFileName()
								+ "\"")
				.body(new ByteArrayResource(databaseFile.getData()));
	}
	
}
