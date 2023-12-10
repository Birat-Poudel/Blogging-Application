package com.biratpoudel.blog.config;

public class AppConstants {
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "10";
    public static final String SORT_BY = "title";
    public static final String SORT_DIR = "asc";
    public static final String JWT_HEADER = "Authorization";
    public static final String SECRET_KEY = "9e7a281b7f4a22623389bc6538b0bcfb508c2609e07767d43d9f4cfd9b001c19";
}

/*
 * Code for Generating SECRET KEY
 * node -e "console.log(require('crypto').randomBytes(32).toString('hex'))"
 */
