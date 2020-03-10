package com.manhnv.model.request;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.manhnv.model.Status;

public class BookRequest extends BasePageRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3415130257663525821L;
	private boolean descending;
	private String sortBy;
	private String author;
	@Enumerated(EnumType.STRING)
	private Status status;

	public BookRequest() {
		super();
	}

	public BookRequest(int pageNo, int pageSize, boolean descending, String sortBy) {
		super(pageNo, pageSize);
		this.descending = descending;
		this.sortBy = sortBy;
	}

	public boolean isDescending() {
		return descending;
	}

	public void setDescending(boolean descending) {
		this.descending = descending;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public final Status getStatus() {
		return status;
	}

	public final void setStatus(Status status) {
		this.status = status;
	}

	public boolean isValidStatus() {
		return status != null;
	}

}
