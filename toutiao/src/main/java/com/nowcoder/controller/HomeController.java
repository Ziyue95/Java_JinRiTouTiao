package com.nowcoder.controller;

import com.nowcoder.model.HostHolder;
import com.nowcoder.model.News;
import com.nowcoder.model.User;
import com.nowcoder.model.ViewObject;
import com.nowcoder.service.NewsService;
import com.nowcoder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/** Controller use service to interact with DAO files */
@Controller
public class HomeController {
    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        /** Get a list of News object using functions in newsService */
        List<News> newsList = newsService.getLatestNews(userId, offset, limit);
        /** Store the each news and it corresponding user to vo
         * vos contains all collected ViewObject object vo */
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            /** Use useId to select user and use "user" String key to map it */
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    /** vos is created from back end and sent to home.html for further use */

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    /** Model is used to store all data fetched from back end to front end */
    public String index(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop) {
        /** model.addAttribute(variable_name , content);
         * add variable "vos" to the storage model with value vos <- created by calling getNews function */
        model.addAttribute("vos", getNews(0, 0, 10));

        if (hostHolder.getUser() != null) {
            pop = 0;
        }
        model.addAttribute("pop", pop);

        return "home";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId,
                            @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.addAttribute("vos", getNews(userId, 0, 10));
        /** receive the parameter send from the LoginRequireInterceptor
         * and stored it into the model for further front end usage */
        model.addAttribute("pop",pop);
        return "home";
    }


}