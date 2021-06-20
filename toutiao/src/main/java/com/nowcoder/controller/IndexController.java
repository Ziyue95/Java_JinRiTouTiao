package com.nowcoder.controller;

import com.nowcoder.aspect.LogAspect;
import com.nowcoder.model.User;
import com.nowcoder.service.ToutiaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by nowcoder on 2016/6/26.
 */
//@Controller
public class IndexController {
    /** Add a Logger object: record all log infos */
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    /** use @Autowired to automatically inject other classes(using the IoC idea)
     * web: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Autowired.html */
    @Autowired
    private ToutiaoService toutiaoService;

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    /** load message from session */
    public String index(HttpSession session) {
        /** Add info into logger */
        logger.info("Visit Index");

        /** session.getAttribute("msg") fetches data from session with attribute msg */
        return "Hello NowCoder," + session.getAttribute("msg")
                + "<br> Say:" + toutiaoService.say();
    }

    @RequestMapping(value = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    /** Get variables from path of url using @PathVairable;
     *  Get parameters from parameters of url(after query(?)) */
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "nowcoder") String key) {
        return String.format("GID{%s},UID{%d},TYPE{%d},KEY{%s}", groupId, userId, type, key);
    }
    /** .vm file is a Virtual Memory file */
    /** .vm file is used to render the response from the client into a string representation of the HTML and its associated Javascript, etc. */
    @RequestMapping(value = {"/vm"})
    /** Model is used to store all data fetched from back end to front end */
    public String news(Model model) {
        /** model.addAttribute(variable_name , content); */
        /** add variable value1 to the storage model with value "vv1" */
        model.addAttribute("value1", "vv1");
        /** define a List variable using Array class */
        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});
        /** define a Map variable using HashMap */
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < 4; ++i) {
            map.put(String.valueOf(i), String.valueOf(i * i));
        }
        /** add defined List and Map to storage model */
        model.addAttribute("colors", colors);
        model.addAttribute("map", map);

        model.addAttribute("user", new User("Jim"));

        /** it returns the .vm file with name news <--> news.vm */
        return "news";
    }

    @RequestMapping(value = {"/request"})
    @ResponseBody
    /** HttpServletRequest, HttpServletResponse, HttpSession are all standard class defined in Javax  */
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session) {

        StringBuilder sb = new StringBuilder();

        /** Get information from header of request */
        /** 	request.getHeaderNames() Returns an enumeration of all the header names this request contains. */
        /** Use java.util.Enumeration to turn a string into enumeratable object */
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }

        /** Get information from Cookie */
        for (Cookie cookie : request.getCookies()) {
            sb.append("Cookie:");
            sb.append(cookie.getName());
            sb.append(":");
            sb.append(cookie.getValue());
            sb.append("<br>");
        }

        /** Get other information from request */
        sb.append("getMethod:" + request.getMethod() + "<br>");
        sb.append("getPathInfo:" + request.getPathInfo() + "<br>");

        /** request.getQueryString() returns all the parameters after the query into a string */
        sb.append("getQueryString:" + request.getQueryString() + "<br>");
        sb.append("getRequestURI:" + request.getRequestURI() + "<br>");

        return sb.toString();

    }

    @RequestMapping(value = {"/response"})
    @ResponseBody
    public String response(@CookieValue(value = "nowcoderid", defaultValue = "a") String nowcoderId,
                           @RequestParam(value = "key", defaultValue = "key") String key,
                           @RequestParam(value = "value", defaultValue = "value") String value,
                           HttpServletResponse response) {
        /** search parameter key and value from url and add corresponding Cookie in response */
        response.addCookie(new Cookie(key, value));

        /** write a header with name key and value value into the Response header */
        response.addHeader(key, value);

        /** when the key of new Cookies is nowcoderid,
            the new cookies will be captured by @CookieValue
            and store its value into string nowcoderId */
        return "NowCoderId From Cookie:" + nowcoderId;
    }

    /*
    @RequestMapping(value = {"/redirect/{code}"})
    @ResponseBody
    public RedirectView redirect(@PathVariable("code") int code){
        // Create a new RedirectView with the relative given URL
        RedirectView red = new RedirectView("/",true);
        if(code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }
    */

    @RequestMapping("/redirect/{code}")
    public String redirect(@PathVariable("code") int code,
                           HttpSession session) {
        /** Set attribute into session */
        session.setAttribute("msg", " Jump from redirect.");
        /** this is a 302 redirect */
        return "redirect:/";
    }


    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key", required = false) String key) {
        /** String1.equals(String2) returns true when String1 has
         * the same contetn as String2 and false otherwise; */
        if ("admin".equals(key)) {
            return "hello admin";
        }
        /** throw an Exception */
        throw new IllegalArgumentException("Wrong Key");
    }
    /** Self-defined ExceptionHandler when an Exception happens */
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {
        return "error:" + e.getMessage();
    }
}
