package net.guides.springboot.springbootfileupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.guides.springboot.springbootfileupload.model.DatabaseFile;

public interface DatabaseFileRepository
		extends JpaRepository<DatabaseFile, String> {
	
}
