package com.manhnv.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.manhnv.dto.base.BaseCompareEntity;
import com.manhnv.model.request.BookRateRequest;

@Entity(name = "tbl_book_rate")
@Table(name = "tbl_book_rate")
public class BookRate extends BaseCompareEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7066983727244426747L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double rate;
	private String comment;

	@Column(name = "created_date")
	private Timestamp createdDate = new Timestamp(System.currentTimeMillis());

	// @JsonBackReference
	@OneToOne
	@JoinColumn(name = "user_id")
	private UserDetail user;

	// @JsonBackReference
	@OneToOne
	@JoinColumn(name = "book_id")
	private Book book;

	public BookRate() {
		super();
	}

	public BookRate(Double rate, String comment, UserDetail user, Book book) {
		super();
		this.rate = rate;
		this.comment = comment;
		this.user = user;
		this.book = book;
	}

	public BookRate(BookRateRequest request) {
		super();
		this.rate = request.getRate();
		this.comment = request.getComment();
	}

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Double getRate() {
		return rate;
	}

	public final void setRate(Double rate) {
		this.rate = rate;
	}

	public final String getComment() {
		return comment;
	}

	public final void setComment(String comment) {
		this.comment = comment;
	}

	public final UserDetail getUser() {
		return user;
	}

	public final void setUser(UserDetail user) {
		this.user = user;
	}

	public final Book getBook() {
		return book;
	}

	public final void setBook(Book book) {
		this.book = book;
	}

	public final Timestamp getCreatedDate() {
		return createdDate;
	}

	public final void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

}
