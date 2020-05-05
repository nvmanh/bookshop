package com.manhnv.common;

public class PathConsts {
	// start basic authentication user
	public static final String BASIC_USER = "user";
	public static final String BASIC_USER_PASS = "password";
	public static final String BASIC_ADMIN = "admin";
	public static final String BASIC_ADMIN_PASS = "password";
	// end basic authentication user

	// start role
	//private static final String BASIC_USER_ROLE = "USER";
	//private static final String BASIC_ADMIN_ROLE = "ADMIN";
	// end role

	// start authenticate
	private static final String AUTH = "/auth";
	// end authenticate

	// start user API
	private static final String LOGOUT = "/logout";
	private static final String USER = "/user";
	private static final String USER_DETAIL = "/me";
	private static final String USER_REGIST = "/user/regist";
	// view all rated event
	private static final String USER_RATED = "/user/{id}/rate";
	private static final String USER_UPDATE = "/user/{id}";
	private static final String USER_FOLLOWING = "/user/{id}/following";
	// end user API

	// start file API
	private static final String FILE = "/file";
	private static final String UPLOAD = "/file/upload";
	private static final String DOWNLOAD = "/file/download/{fileName:.+}";
	private static final String DOWNLOAD_FILE = "/file/download/{id}";
	private static final String UPLOAD_MULTI = "/file/uploadMultiple";
	private static final String DOWNLOAD_PATH = "/file/download/";
	// end file API

	// start book API
	private static final String BOOK = "/book";
	private static final String BOOK_DETAIL = "/book/{id}";
	private static final String BOOK_RATE = "/book/{id}/rate";
	private static final String BOOK_ALL_RATE = "/book/{id}/all-rate";
	private static final String BOOK_WITH_AUTHOR = "/book/with-author";
	// end book API

	// start author API
	private static final String AUTHOR = "/author";
	private static final String AUTHOR_DETAIL = "/author/{id}";
	private static final String AUTHOR_VOTE = "/author/{id}/vote";
	private static final String AUTHOR_RATE = "/author/{id}/rate";
	private static final String AUTHOR_FOLLOWER = "/author/{id}/followers";
	// end author API

	// start vote API
	private static final String VOTE = "/vote";
	// end vote API

	private static final String ERROR = "/error";
	private static final String API_ERROR = "/error";
	private static final String CREATE_SAMPLE = "/create-data";
	private static final String CURRENT_LANGUAGE = "/lang";

	public class v1 {
		// start authenticate
		private static final String V1 = "/api/v1";
		public static final String AUTH = V1 + PathConsts.AUTH;
		// end authenticate

		// start user API
		public static final String LOGOUT = V1 + PathConsts.LOGOUT;
		public static final String USER = V1 + PathConsts.USER;
		public static final String USER_PROFILE = V1 + PathConsts.USER_DETAIL;
		public static final String USER_REGIST = V1 + PathConsts.USER_REGIST;
		// view all rated event
		public static final String USER_RATED = V1 + PathConsts.USER_RATED;
		public static final String USER_UPDATE = V1 + PathConsts.USER_UPDATE;
		public static final String USER_FOLLOWING = V1 + PathConsts.USER_FOLLOWING;
		// end user API

		// start file API
		public static final String FILE = V1 + PathConsts.FILE;
		public static final String UPLOAD = V1 + PathConsts.UPLOAD;
		public static final String DOWNLOAD = V1 + PathConsts.DOWNLOAD;
		public static final String DOWNLOAD_FILE = V1 + PathConsts.DOWNLOAD_FILE;
		public static final String UPLOAD_MULTI = V1 + PathConsts.UPLOAD_MULTI;
		public static final String DOWNLOAD_PATH = V1 + PathConsts.DOWNLOAD_PATH;
		// end file API

		// start book API
		public static final String BOOK = V1 + PathConsts.BOOK;
		public static final String BOOK_DETAIL = V1 + PathConsts.BOOK_DETAIL;
		public static final String BOOK_RATE = V1 + PathConsts.BOOK_RATE;
		public static final String BOOK_ALL_RATE = V1 + PathConsts.BOOK_ALL_RATE;
		public static final String BOOK_WITH_AUTHOR = V1 + PathConsts.BOOK_WITH_AUTHOR;
		// end book API

		// start author API
		public static final String AUTHOR = V1 + PathConsts.AUTHOR;
		public static final String AUTHOR_DETAIL = V1 + PathConsts.AUTHOR_DETAIL;
		public static final String AUTHOR_VOTE = V1 + PathConsts.AUTHOR_VOTE;
		public static final String AUTHOR_RATE = V1 + PathConsts.AUTHOR_RATE;
		public static final String AUTHOR_FOLLOWER = V1 + PathConsts.AUTHOR_FOLLOWER;
		// end author API

		// start vote API
		public static final String VOTE = V1 + PathConsts.VOTE;
		// end vote API

		public static final String ERROR = PathConsts.ERROR;
		public static final String API_ERROR = V1 + PathConsts.API_ERROR;

		public static final String CLEAR_CACHE = V1 + "/clear-cache";
		
		public static final String CREATE_SAMPLE = V1 + PathConsts.CREATE_SAMPLE;
		
		public static final String CURRENT_LANGUAGE = V1 + PathConsts.CURRENT_LANGUAGE;
	}
}
