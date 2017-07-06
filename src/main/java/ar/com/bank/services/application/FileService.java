package ar.com.bank.services.application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ar.com.bank.services.exceptions.StorageException;

@Service
//TODO write unit test
public class FileService {

	@Value("${files.upload.dir:#{null}}")
	private String filesUploadDir;

	@PostConstruct
	public void init() {
		if (StringUtils.isBlank(filesUploadDir)) {
			//If not configured, use java temp folder
			filesUploadDir = System.getProperty("java.io.tmpdir");
		}
		Path rootLocation = Paths.get(filesUploadDir);
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	/**
	 * Save a file under a subfolder with the given identifier
	 */
	public String saveFile(MultipartFile file, String identifier) {
		//The idea is to create a subfolder for each identifier to avoid a single folder with thousands of files 
		Path destinationFolder = Paths.get(filesUploadDir + "/" + identifier);
		try {
			Files.createDirectories(destinationFolder);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
		try {
			Files.copy(file.getInputStream(), destinationFolder.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new StorageException("Failed to store file", e);
		}
		return identifier + "/" + file.getOriginalFilename();
	}
	
	/**
	 * Load a file by its path
	 */
	public File loadFile(String path) {
		return new File(filesUploadDir + "/" + path);
	}
}
