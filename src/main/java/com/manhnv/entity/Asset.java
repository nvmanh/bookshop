package com.manhnv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.manhnv.model.response.UploadFileResponse;

@Entity(name = "tbl_file")
public class Asset implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1896059285615451805L;
	@Id
	@GeneratedValue
	private Long id;
	@NotEmpty(message = "File name is not empty")
	@Column(name = "file_name")
	private String fileName;
	@Column(name = "real_path")
	private String realPath;
	@Column(name = "file_uri")
	private String fileUri;
	@Column(name = "content_type")
	private String contentType;
	@Column(name = "size")
	private Long size;

	public Asset() {
		super();
	}

	public Asset(String fileName, String realPath, String fileUri, String contentType, Long size) {
		super();
		this.fileName = fileName;
		this.realPath = realPath;
		this.fileUri = fileUri;
		this.contentType = contentType;
		this.size = size;
	}

	public Asset(UploadFileResponse res, String realPath) {
		super();
		this.fileName = res.getFileName();
		this.contentType = res.getFileType();
		this.size = res.getSize();
		this.fileUri = res.getFileDownloadUri();
		this.realPath = realPath;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
