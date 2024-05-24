package com.gbl.app;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "HelloServlet", urlPatterns = {"/hello"}) // Para Jetty
//@WebServlet(name = "HelloServlet", urlPatterns = {"/JavaServlet/hello"}) // Para Tomcat -- Não confirmado essa necessidade ainda !

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/plain;charset=UTF-8");
        var out = response.getOutputStream();

        out.print("Hello there from Servlet");
    }
}
