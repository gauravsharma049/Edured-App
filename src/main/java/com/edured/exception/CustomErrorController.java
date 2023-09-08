package com.edured.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {
    
    @GetMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        model.addAttribute("title", "Something Went Wrong!");
        int statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Map<String, String> error = new HashMap<>();
        // Custom error handling based on the status code
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            // Handle 404 Not Found error
            error.put("statusCode", String.valueOf(statusCode));
            error.put("message", "The Page You Are Looking For Does Not Exists!");
        } else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            // Handle 503 Service Unavailable error
            error.put("statusCode", String.valueOf(statusCode));
            error.put("message", "Service Unavailable!");
        } 
        else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
            // Handle 400 bad request error
            error.put("statusCode", String.valueOf(statusCode));
            error.put("message", "Bad Request! your request cann't be processed!");
        } 
        else if (statusCode == HttpStatus.BAD_GATEWAY.value()) {
            // Handle 502 bad gateway error
            error.put("statusCode", String.valueOf(statusCode));
            error.put("message", "Bad Gateway!");
        } 
        else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            // Handle 403 forbidden error
            error.put("statusCode", String.valueOf(statusCode));
            error.put("message", "You don't have permission to make this request!");
        } 
        else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            // Handle 401 unauthorized error
            error.put("statusCode", String.valueOf(statusCode));
            error.put("message", "Beaware! You are not the authorized person to make this request.");
        } 
        else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            // Handle 500 internal server error
            error.put("statusCode", String.valueOf(statusCode));
            error.put("message", "Something Went Wrong on our side!");
        }
        else if (statusCode == 405) {
            // Handle 405 request not supported error
            error.put("statusCode", String.valueOf(statusCode));
            error.put("message", "Something Went Wrong!");
        }
        else{
            error.put("statusCode", String.valueOf(statusCode));
            error.put("message", "Something Went Wrong!");
        }
        model.addAttribute("error", error);
        return "error";
    }

    

    
}