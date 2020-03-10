package com.manhnv.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.manhnv.dto.base.BaseCompareEntity;
import com.manhnv.model.request.AuthorChangeRequest;
import com.manhnv.utils.TextUtils;

@Entity(name = "tbl_author")
@Table(name = "tbl_author")
public class Author extends BaseCompareEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3121243714640194632L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Author's name is not empty")
	private String name;
	private String pseudonym;
	private Double rate;
	@Column(name = "short_description")
	private String shortDescription;
	private String description;
	private boolean hideProfile;
	private Date birthday;

	@JsonManagedReference
	@OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<Book> books;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_author_followers", joinColumns = {
			@JoinColumn(name = "author_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "follower_id", referencedColumnName = "id") })
	@JsonUnwrapped
	@RestResource(exported = false)
	@JsonManagedReference
	private List<UserDetail> followers;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_user_author_rates", joinColumns = {
			@JoinColumn(name = "author_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "author_rate_id", referencedColumnName = "id") })
	@JsonUnwrapped
	@RestResource(exported = false)
	@JsonManagedReference
	private List<AuthorRate> authorRates;
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_user_author_votes", joinColumns = {
			@JoinColumn(name = "author_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "author_vote_id", referencedColumnName = "id") })
	@JsonUnwrapped
	@RestResource(exported = false)
	@JsonManagedReference
	private List<Vote> votes;

	public Author() {
		super();
	}

	public Author(String name) {
		super();
		this.name = name;
	}

	public Author(String name, Double rate, String shortDescription, String description, boolean hideProfile,
			Date birthday, List<Book> books, List<UserDetail> followers) {
		super();
		this.name = name;
		this.rate = rate;
		this.shortDescription = shortDescription;
		this.description = description;
		this.hideProfile = hideProfile;
		this.birthday = birthday;
		this.books = books;
		this.followers = followers;
	}

	public Author update(AuthorChangeRequest request) {
		if (!TextUtils.isEmpty(request.getName())) {
			this.name = request.getName();
		}
		if (request.isHideProfile() != null) {
			this.hideProfile = request.isHideProfile().booleanValue();
		}

		if (!TextUtils.isEmpty(request.getShortDescription())) {
			this.shortDescription = request.getShortDescription();
		}
		if (!TextUtils.isEmpty(request.getDescription())) {
			this.description = request.getDescription();
		}
		if (this.getBirthday() == null && request.getBirthday() != null) {
			this.birthday = request.getBirthday();
		} else if (this.getBirthday() != null && request.getBirthday() != null
				&& !this.getBirthday().equals(request.getBirthday())) {
			this.birthday = request.getBirthday();
		}
		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<UserDetail> getFollowers() {
		return followers;
	}

	public void setFollowers(List<UserDetail> followers) {
		this.followers = followers;
	}

	public boolean isHideProfile() {
		return hideProfile;
	}

	public void setHideProfile(boolean hideProfile) {
		this.hideProfile = hideProfile;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void addFollowers(UserDetail userDetail) {
		if (followers == null)
			followers = new ArrayList<UserDetail>();
		if (userDetail == null)
			return;
		if (!followers.contains(userDetail))
			followers.add(userDetail);
	}

	public void hideFollowers() {
		followers = null;
	}

	public final List<AuthorRate> getAuthorRates() {
		return authorRates;
	}

	public final void setAuthorRates(List<AuthorRate> authorRates) {
		this.authorRates = authorRates;
	}

	public final String getPseudonym() {
		return pseudonym;
	}

	public final void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}

}
