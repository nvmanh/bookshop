package com.manhnv.model.dto;

public class UserDTO extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6074374302224547652L;
	private Long id;
	private String name;

	public UserDTO() {
		super();
	}

	public UserDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

}
