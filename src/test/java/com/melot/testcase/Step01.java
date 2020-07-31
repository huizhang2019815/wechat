package com.melot.testcase;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author zhanghui
 * @Date 2020/7/28 19:57
 * @Version 1.0
 * @description
 */
public  class Step01 {
      static String  token;
    @BeforeAll
    //BeforeAll 修饰静态方法，Test注解不能修饰静态方法
    static void getAccessToken(){
        token = given()
                .log().all()
                .when()
                .param("corpid","wwcfd3df3789ee03ba")
                .param("corpsecret","WjvHCtYwuA6e1-L2H989cm5BQIwllDcu9cAc-4KD0rI")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .log().body()
                .extract()
                .response()
                .path("access_token");

    }

    @Test
    @Description("创建部门")
    void createDepartment(){
        String createBody = "{\n" +
                "   \"name\": \"广州研发中心1\",\n" +
                "   \"name_en\": \"RDGZ1\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 2,\n" +
                "   \"id\": 3\n" +
                "}";
        Response createResponse = given()
                .log().all()
                .when()
                .queryParam("access_token",token)
                .contentType("application/json")
                .body(createBody)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then()
                .log().all()
                .extract()
                .response();
        assertEquals("0",createResponse.path("errcode").toString());



    }



}
