package com.my.spring.jpush;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MKPushServlet
 */
@WebServlet("/MKPushServlet")
public class MKPushServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String appKey = "4bb64dc069ab2e6d9f705a93";
    private static final String masterSecret = "a7595a5d81ff4de00d3690f4";
    public static void main(String[] arg){
    	JiguangPush.testSendPush(appKey,masterSecret);  
        System.out.println("sucess"); 
    }
    /**
     * Default constructor. 
     */
    public MKPushServlet() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    	
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}