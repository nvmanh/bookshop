package com.manhnv.model.request;

public class BookRatePageRequest extends BasePageRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4987430477773115311L;
	private Long userId;

	public BookRatePageRequest() {
		super();
	}

	public final Long getUserId() {
		return userId;
	}

	public final void setUserId(Long userId) {
		this.userId = userId;
	}

}
