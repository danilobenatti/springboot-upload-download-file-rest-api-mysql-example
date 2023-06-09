package net.guides.springboot.springbootfileupload.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import net.guides.springboot.springbootfileupload.model.DatabaseFile;
import net.guides.springboot.springbootfileupload.payload.Response;
import net.guides.springboot.springbootfileupload.service.DatabaseFileService;

@RestController
public class FileUploadController {
	
	@Autowired
	private DatabaseFileService databaseFileService;
	
	@PostMapping("/uploadFile")
	public Response uploadFile(
			@RequestParam(name = "file") MultipartFile file) {
		DatabaseFile fileName = databaseFileService.storeFile(file);
		
		String fileDownloadUri = ServletUriComponentsBuilder
				.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName.getFileName()).toUriString();
		
		return new Response(fileName.getFileName(), fileDownloadUri,
				file.getContentType(), file.getSize());
	}
	
	@PostMapping("/uploadMultipleFiles")
	public List<Response> uploadMultipleFiles(
			@RequestParam(name = "files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(this::uploadFile)
				.collect(Collectors.toList());
	}
	
}
