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
import com.manhnv.model.request.AuthorRateRequest;

@Entity(name = "tbl_author_rate")
@Table(name = "tbl_author_rate")
public class AuthorRate extends BaseCompareEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6810443406425230259L;
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
	@JoinColumn(name = "author_id")
	private Author author;

	public AuthorRate() {
		super();
	}

	public AuthorRate(Long id, Double rate, String comment, UserDetail user, Author author) {
		super();
		this.id = id;
		this.rate = rate;
		this.comment = comment;
		this.user = user;
		this.author = author;
	}

	public AuthorRate(AuthorRateRequest request) {
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
		return rate <= 0 ? 0 : rate;
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

	public final Author getAuthor() {
		return author;
	}

	public final void setAuthor(Author author) {
		this.author = author;
	}

	public final Timestamp getCreatedDate() {
		return createdDate;
	}

	public final void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

}
