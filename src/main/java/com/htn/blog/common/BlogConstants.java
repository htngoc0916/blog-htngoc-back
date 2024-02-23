package com.htn.blog.common;


import org.springframework.data.domain.Sort;

public class BlogConstants {
    //api code
    public final static String SUCCESS = "SUCCESS";
    public final static String FAILED = "FAILED";
    public final static String FORBIDDEN = "FORBIDDEN";

    //page setting
    public final static String DEFAULT_PAGE_NUMBER = "1";
    public final static String DEFAULT_PAGE_SIZE = "10";
    public final static String DEFAULT_SORT_BY = "id";
    public final static String DEFAULT_SORT_DIRECTION = "asc";
    public static final int MAX_PAGE_SIZE = 30;

    //upload
    public final static int MAXIMUM_IMAGES_PER_PRODUCT = 5;

    //token
    public final static int MAX_AUTH_TOKEN = 3;



    public final static int DEFAULT_PAGE_NUMBER_TEST = 1;
    public final static int DEFAULT_PAGE_SIZE_TEST = 1;
}
