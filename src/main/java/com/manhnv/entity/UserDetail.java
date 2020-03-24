package com.manhnv.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.manhnv.dto.base.BaseCompareEntity;
import com.manhnv.model.Gender;
import com.manhnv.model.UserStatus;
import com.manhnv.model.request.UserChangeRequest;
import com.manhnv.model.request.UserCreateRequest;

@Entity(name = "tbl_user_detail")
public class UserDetail extends BaseCompareEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7151592707460992527L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue
	private Long id;
	@NotEmpty(message = "Please provide your name")
	private String name;
	@Column(name = "full_name")
	private String fullName;
	@Column(name = "address_1")
	private String address1;
	@Column(name = "address_2")
	private String address2;
	private int age;
	// 1: male; 2: female; 0: unknown
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Column(name = "phone_1")
	private String phone1;
	@Column(name = "phone_2")
	private String phone2;
	@Column(name = "email_1")
	private String email1;
	@Column(name = "email_2")
	private String email2;

	// 1: enable; 2: disable
	private UserStatus status = UserStatus.ENABLE;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "tbl_user_roles", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	@JsonUnwrapped
	@RestResource(exported = false)
	@JsonManagedReference
	private Set<Role> roles;

	@JsonBackReference
	@OneToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToMany(mappedBy = "followers", fetch = FetchType.EAGER)
	@JsonUnwrapped
	@JsonBackReference
	private List<Author> authors;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_user_book_rates", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "book_rate_id", referencedColumnName = "id") })
	@JsonUnwrapped
	@RestResource(exported = false)
	@JsonManagedReference
	private List<BookRate> bookRates;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_user_author_rates", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "author_id", referencedColumnName = "id") })
	@JsonUnwrapped
	@RestResource(exported = false)
	@JsonManagedReference
	private List<AuthorRate> authorRates;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_user_author_votes", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "author_vote_id", referencedColumnName = "id") })
	@JsonUnwrapped
	@RestResource(exported = false)
	@JsonManagedReference
	private List<Vote> votes;

	public UserDetail() {
		super();
	}

	public UserDetail(String name, String fullName, String address1, String address2, int age, Gender gender,
			String phone1, String phone2, Set<Role> roles) {
		super();
		this.name = name;
		this.fullName = fullName;
		this.address1 = address1;
		this.address2 = address2;
		this.age = age;
		this.gender = gender;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.roles = roles;
	}

	public UserDetail(UserChangeRequest jwtUser) {
		super();
		this.name = jwtUser.getUsername();
		this.fullName = jwtUser.getFullName();
		this.address1 = jwtUser.getAddress1();
		this.address2 = jwtUser.getAddress2();
		this.age = jwtUser.getAge();
		this.gender = jwtUser.getGender();
		this.phone1 = jwtUser.getPhone1();
		this.phone2 = jwtUser.getPhone2();
		this.email1 = jwtUser.getEmail1();
		this.email2 = jwtUser.getEmail2();
	}

	public UserDetail(UserCreateRequest jwtUser) {
		super();
		this.name = jwtUser.getUsername();
		this.fullName = jwtUser.getFullName();
		this.address1 = jwtUser.getAddress1();
		this.address2 = jwtUser.getAddress2();
		this.age = jwtUser.getAge();
		this.gender = jwtUser.getGender();
		this.phone1 = jwtUser.getPhone1();
		this.phone2 = jwtUser.getPhone2();
		this.email1 = jwtUser.getEmail1();
		this.email2 = jwtUser.getEmail2();
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		if (role == null)
			return;
		if (roles == null || roles.contains(role))
			roles = new HashSet<Role>();
		this.roles.add(role);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public void toggle() {
		if (status == UserStatus.ENABLE) {
			status = UserStatus.DISABLE;
		} else {
			status = UserStatus.ENABLE;
		}
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public final List<BookRate> getBookRates() {
		return bookRates;
	}

	public final void setBookRates(List<BookRate> bookRates) {
		this.bookRates = bookRates;
	}

	public final List<AuthorRate> getAuthorRates() {
		return authorRates;
	}

	public final void setAuthorRates(List<AuthorRate> authorRates) {
		this.authorRates = authorRates;
	}

}
