package com.manhnv.model.request;

import java.io.Serializable;

public class AuthorRateRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7952953347119996449L;
	private Long userId;
	private Double rate;
	private String comment;

	public AuthorRateRequest() {
		super();
	}

	public final Long getUserId() {
		return userId;
	}

	public final void setUserId(Long userId) {
		this.userId = userId;
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

	public boolean isValid() {
		return userId != null && userId > 0;
	}
}
