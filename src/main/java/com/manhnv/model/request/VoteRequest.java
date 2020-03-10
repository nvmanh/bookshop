package com.manhnv.model.request;

public class VoteRequest extends BasePageRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1123604656939581001L;
	private Long authorId;
	private Long userId;
	private Boolean voted;

	public VoteRequest() {
		super();
	}

	public VoteRequest(Long authorId, Long userId, Boolean voted) {
		super();
		this.authorId = authorId;
		this.userId = userId;
		this.voted = voted;
	}

	public final Long getAuthorId() {
		return authorId;
	}

	public final void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public final Long getUserId() {
		return userId;
	}

	public final void setUserId(Long userId) {
		this.userId = userId;
	}

	public final Boolean getVoted() {
		return voted;
	}

	public final void setVoted(Boolean voted) {
		this.voted = voted;
	}

	public boolean inValid() {
		return (authorId == null || authorId <= 0) && (userId == null || userId <= 0);
	}

	public boolean validUserAndAuthor() {
		return authorId != null && authorId > 0 && userId != null && userId > 0;
	}
}
