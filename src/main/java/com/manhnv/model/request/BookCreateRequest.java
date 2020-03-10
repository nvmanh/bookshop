package com.manhnv.model.request;

import java.io.Serializable;

import com.manhnv.model.Status;

public class BookCreateRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7386735173771212982L;
	private String name;
	private Double price;
	private Long authorId;
	private Long id;
	private String thump;

	private String cover;

	private String shortDescription;

	private String description;

	private String publishedDate;

	private Status status;

	public BookCreateRequest() {
		super();
	}

	public BookCreateRequest(String name, Double price, Long authorId) {
		super();
		this.name = name;
		this.price = price;
		this.authorId = authorId;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final Double getPrice() {
		return price;
	}

	public final void setPrice(Double price) {
		this.price = price;
	}

	public final Long getAuthorId() {
		return authorId;
	}

	public final void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public final Long getId() {
		return id == null ? 0 : id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getThump() {
		return thump;
	}

	public final void setThump(String thump) {
		this.thump = thump;
	}

	public final String getCover() {
		return cover;
	}

	public final void setCover(String cover) {
		this.cover = cover;
	}

	public final String getShortDescription() {
		return shortDescription;
	}

	public final void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final String getPublishedDate() {
		return publishedDate;
	}

	public final void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public final Status getStatus() {
		return status;
	}

	public final void setStatus(Status status) {
		this.status = status;
	}

}
