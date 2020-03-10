package com.manhnv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "tbl_vote")
@Table(name = "tbl_vote")
public class Vote implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 835189609566867767L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	@JoinColumn(name = "author_id")
	private Author author;
	@OneToOne
	@JoinColumn(name = "user_id")
	private UserDetail user;
	@Column(columnDefinition = "boolean default false")
	private Boolean voted;

	public Vote() {
		super();
	}

	public Vote(Author author, UserDetail user, Boolean voted) {
		super();
		this.author = author;
		this.user = user;
		this.voted = voted;
	}

	public final Long getId() {
		return id == null ? 0 : id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Author getAuthor() {
		return author;
	}

	public final void setAuthor(Author author) {
		this.author = author;
	}

	public final UserDetail getUser() {
		return user;
	}

	public final void setUser(UserDetail user) {
		this.user = user;
	}

	public final Boolean getVoted() {
		return voted;
	}

	public final void setVoted(Boolean voted) {
		this.voted = voted;
	}

}
