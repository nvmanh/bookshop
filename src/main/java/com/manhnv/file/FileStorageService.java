package com.manhnv.file;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.manhnv.common.PathConsts;
import com.manhnv.entity.Asset;
import com.manhnv.model.response.UploadFileResponse;
import com.manhnv.property.FileStorageProperties;
import com.manhnv.utils.TextUtils;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Autowired
	private IFileRepository fileRepository;

	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}
	
	public Asset findAssetById(Long id) {
		return fileRepository.findById(id).get();
	}

	public void saveFileDb(UploadFileResponse res, String realPath) {
		Asset asset = new Asset(res, realPath);
		fileRepository.save(asset);
	}

	private Path createFilePath(String dir, String fileName) {
		Path target = fileStorageLocation.resolve(dir);
		File f = new File(target.toString());
		if (!f.exists()) {
			f.mkdir();
		}
		String newPath = dir + "/" + fileName;
		File asset = new File(newPath);
		if (!asset.exists()) {
			try {
				asset.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileStorageLocation.resolve(newPath);
	}

	private String getToday() {
		String format = "yyyy-MM-dd";
		DateFormat dateFormatter = new SimpleDateFormat(format);
		return dateFormatter.format(new Date(System.currentTimeMillis()));
	}

	private Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

	private String changeFileName(String name) {
		String ext = getExtensionByStringHandling(name).get();
		if (!TextUtils.isEmpty(ext)) {
			ext = "." + ext;
		}
		return String.valueOf(System.currentTimeMillis()) + ext;
	}

	public UploadFileResponse storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			fileName = changeFileName(fileName);
			// Copy file to the target location (Replacing existing file with the same name)
			// Path targetLocation = this.fileStorageLocation.resolve(fileName);
			String dir = getToday();
			Path targetLocation = createFilePath(dir, fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			String realPath = dir.concat("/").concat(fileName);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(PathConsts.v1.DOWNLOAD_PATH)
					.path(dir).path("/").path(fileName).toUriString();

			UploadFileResponse res = new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(),
					file.getSize());
			saveFileDb(res, realPath);
			return res;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}
}
