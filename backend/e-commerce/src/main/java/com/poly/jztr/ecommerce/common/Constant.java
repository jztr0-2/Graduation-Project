package com.poly.jztr.ecommerce.common;

public class Constant {
    public static String JWT_USERNAME = "Username";
    public static Integer USER_STATUS_NOT_ACTIVATED = 0;
    public static Integer USER_STATUS_ACTIVATED = 1;

    public static Integer ORDER_STATUS_PENDING = 0;

    public static Integer ORDER_STATUS_SUCCESS = 1;

    public static Integer ORDER_STATUS_CANCEL = 2;
    public static String RESPONSE_STATUS_SUCCESS = "OK";

    public static String RESPONSE_STATUS_NOTFOUND = "NOT_FOUND";

    public static String RESPONSE_STATUS_UNPROCESSABLE_ENTITY = "UNPROCESSABLE_ENTITY";

    public static Integer SORT_ASC = 1;

    public static Integer SORT_DESC = 2;

    public static Integer IMAGE_TYPE_USER = 1;

    public static Integer IMAGE_TYPE_PRODUCT_AVT = 2;

    public static Integer IMAGE_TYPE_PRODUCT_MULTIPLE = 3;

    public static Integer PROMOTION_TYPE_MULTIPLE_USER = 1;

    public static Integer PROMOTION_TYPE_SINGLE_USER = 2;

    public static String BASE_API_URL = "http://localhost:8080/api/v1/";
}
