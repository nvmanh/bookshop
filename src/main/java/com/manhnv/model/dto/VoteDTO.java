package com.manhnv.model.dto;

public class VoteDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2071003110255247730L;
	private Long id;
	private Boolean voted;
	private AuthorDTO author;
	private UserDetailDTO user;

	public VoteDTO() {
		super();
	}

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Boolean getVoted() {
		return voted;
	}

	public final void setVoted(Boolean voted) {
		this.voted = voted;
	}

	public final AuthorDTO getAuthor() {
		return author;
	}

	public final void setAuthor(AuthorDTO author) {
		this.author = author;
	}

	public final UserDetailDTO getUser() {
		return user;
	}

	public final void setUser(UserDetailDTO user) {
		this.user = user;
	}

}
