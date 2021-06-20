package com.nowcoder.controller;

import com.nowcoder.model.*;
//import com.nowcoder.service.CommentService;
import com.nowcoder.service.NewsService;
import com.nowcoder.service.QiniuService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.*;

@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    NewsService newsService;

    @Autowired
    QiniuService qiniuService;

    @Autowired
    HostHolder hostHolder;

    //@Autowired
    //CommentService commentService;

    @Autowired
    UserService userService;


    @RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
    @ResponseBody
    /** copy loaded image into response */
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response){
        try{
            /** response.setContentType: Sets the content type of the response being sent to the client;
             * StreamUtiles.copy(InputStream in, OutputStream out): Copy the contents of the given InputStream to the given OutputStream. */
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new File(ToutiaoUtil.IMAGE_DIR + imageName)),
                    response.getOutputStream());
        }catch (Exception e){
            logger.error("fail to load image" + imageName + e.getMessage());
        }

    }

    @RequestMapping(path = {"/uploadImage/"}, method = {RequestMethod.POST})
    @ResponseBody
    /** MultipartFile: A representation of an uploaded file received in a multipart request. */
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try{
            //String fileUrl = newsService.saveImage(file);
            String fileUrl = qiniuService.saveImage(file);
            if(fileUrl == null){
                return ToutiaoUtil.getJSONString(1, "Fail to upload imagexxxxx;");
            }
            return ToutiaoUtil.getJSONString(0,fileUrl);
        }
        catch (Exception e){
            logger.error("Fail to upload image;" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "upload failed");
        }
    }

    @RequestMapping(path = {"/user/addNews/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(
            @RequestParam("image") String image,
            @RequestParam("title") String title,
            @RequestParam("link") String link
    ){
        try{
            //News news = new News();
            //news.setCreatedDate(new Date());
            //news.setTitle(title);
            //news.setImage(image);
            //news.setLink(link);
            int userId = -1;
            if(hostHolder.getUser() != null){
                userId = hostHolder.getUser().getId();
                //news.setUserId(hostHolder.getUser().getId());
            } else{
                // set an anonymous user
                userId = 3;
                //news.setUserId(3);
            }
            //newsService.addNews(news);
            Map<String, Object> map = newsService.addNews(image,title,link,userId);
            if(map.containsKey("newsId")){
                return ToutiaoUtil.getJSONString(0);
            }else{
                return ToutiaoUtil.getJSONString(1,map.get("msg").toString());
            }
            //return ToutiaoUtil.getJSONString(0);
        }catch(Exception e){
            logger.error("Fail to add news" + e.getMessage());
            return ToutiaoUtil.getJSONString(1,"Fail to publish;");
        }
    }

}
