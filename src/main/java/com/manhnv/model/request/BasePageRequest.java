package com.manhnv.model.request;

import java.io.Serializable;

import com.manhnv.common.Const;

public class BasePageRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 948662816040173420L;
	private int pageNo;
	private int pageSize = Const.DEFAULT_PAGE_SIZE;

	public BasePageRequest() {
		super();
	}

	public BasePageRequest(int pageNo, int pageSize) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public final int getPageNo() {
		return pageNo;
	}

	public final void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public final int getPageSize() {
		return pageSize <= 0 ? Const.DEFAULT_PAGE_SIZE : pageSize;
	}

	public final void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
