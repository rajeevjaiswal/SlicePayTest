package com.rajeevjaiswal.mvp.utils;

import com.rajeevjaiswal.mvp.BuildConfig;

/**
 * Created by Rajeev Jaiswal on 1/27/2018.
 */

public class FlickrConstants {

    private FlickrConstants() { }

    public static final String API_KEY = BuildConfig.FLICKR_API_KEY;
    public static final String PHOTOS_ENDPOINT = "flickr.interestingness.getList";

    public static final String QUERY_PARAM_PHOTO_ID = "photo_id";
    public static final String QUERY_PARAM_API_KEY = "api_key";
    public static final String QUERY_PARAM_METHOD = "method";
    public static final String QUERY_PARAM_NO_JSON_CALLBACK = "nojsoncallback";
    public static final String QUERY_PARAM_FORMAT = "format";
    public static final String QUERY_PARAM_VALUE_JSON = "json";
    public static final String QUERY_PARAM_VALUE_NO_JSON_CALLBACK = "1";
    public static final String QUERY_PARAM_PER_PAGE = "per_page";
    public static final String QUERY_PARAM_VALUE_PER_PAGE = "5";
    public static final String QUERY_PARAM_PAGE = "page";

    public static final String PHOTO_URL = "https://farm%s.staticflickr.com/%s/%s_%s_z.jpg";
}
