package com.manhnv.model.request;

public class AuthorRequest extends BasePageRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2183080122913781002L;
	private String name;

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

}
