package com.manhnv.entity;

import java.math.BigDecimal;
import java.util.List;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.manhnv.dto.base.BaseCompareEntity;
import com.manhnv.model.Status;
import com.manhnv.model.request.BookCreateRequest;
import com.manhnv.utils.TextUtils;

@Entity(name = "tbl_book")
@Table(name = "tbl_book")
public class Book extends BaseCompareEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4877310639369992281L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Please provide a name")
	private String name;

	@NotNull(message = "Please provide a price")
	@DecimalMin("1.00")
	private BigDecimal price;

	private String thump;

	private String cover;

	@Column(name = "short_description")
	private String shortDescription;

	private String description;

	@Column(name = "published_date")
	private String publishedDate;

	@Enumerated(EnumType.STRING)
	private Status status = Status.INSTOCK;

	// @ManyToOne(targetEntity = Author.class)
	// @JsonUnwrapped
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_user_book_rates", joinColumns = {
			@JoinColumn(name = "book_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "book_rate_id", referencedColumnName = "id") })
	// @JsonUnwrapped
	// @RestResource(exported = false)
	// @JsonManagedReference
	private List<BookRate> bookRates;

	// avoid this "No default constructor for entity"
	public Book() {
		super();
	}

	public Book(Long id, String name, Author author, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.price = price;
	}

	public Book(String name, Author author, BigDecimal price) {
		this.name = name;
		this.author = author;
		this.price = price;
	}

	public Book(@NotEmpty(message = "Please provide a name") String name, Author author,
			@NotNull(message = "Please provide a price") @DecimalMin("1.00") BigDecimal price, Status status) {
		super();
		this.name = name;
		this.price = price;
		this.status = status;
		this.author = author;
	}

	public Book(BookCreateRequest request) {
		super();
		if (!TextUtils.isEmpty(request.getName())) {
			this.name = request.getName();
		}
		if (request.getPrice() != null && request.getPrice() > 0) {
			this.price = BigDecimal.valueOf(request.getPrice());
		}
		if (!TextUtils.isEmpty(request.getThump())) {
			this.thump = request.getThump();
		}
		if (!TextUtils.isEmpty(request.getDescription())) {
			this.description = request.getDescription();
		}
		if (!TextUtils.isEmpty(request.getCover())) {
			this.cover = request.getCover();
		}
		if (!TextUtils.isEmpty(request.getPublishedDate())) {
			this.publishedDate = request.getPublishedDate();
		}
		if (request.getStatus() != null) {
			this.status = request.getStatus();
		}
		if (request.getId() != null && request.getId() > 0) {
			this.id = request.getId();
		}
	}

	@Override
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

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getThump() {
		return thump;
	}

	public void setThump(String thump) {
		this.thump = thump;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
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

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
