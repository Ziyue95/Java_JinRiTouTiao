package com.nowcoder.service;

import com.nowcoder.dao.NewsDAO;
import com.nowcoder.model.News;
import com.nowcoder.util.ToutiaoUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.velocity.texen.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;

    /** function defined in NewsDAO file */
    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }

    /*
    public int addNews(News news){
        newsDAO.addNews(news);
        return news.getId();
    }
    */
    public Map<String, Object> addNews(String image, String title, String link, int userId){
        Map<String, Object> map = new HashMap<String, Object>();
        if(StringUtils.isBlank(image)){
            map.put("msg","Image url can not be blank;");
            return map;
        }
        if(StringUtils.isBlank(title)){
            map.put("msg","title can not be blank;");
            return map;
        }
        if(StringUtils.isBlank(link)){
            map.put("msg","link can not be blank;");
            return map;
        }
        if(userId < 0){
            map.put("msg","Invalid userId;");
            return map;
        }
        News news = new News();
        news.setCreatedDate(new Date());
        news.setTitle(title);
        news.setImage(image);
        news.setLink(link);
        news.setUserId(userId);
        newsDAO.addNews(news);
        map.put("newsId",news.getId());
        return map;
    }

    /*
    public News getById(int newsId) {
        return newsDAO.getById(newsId);
    }
    */

    public String saveImage(MultipartFile file) throws IOException{
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        /** can't find "." in original filename */
        if(dotPos < 0){
            return null;
        }
        /** get the suffix of original filename */
        String fileExt = file.getOriginalFilename().substring(dotPos+1).toLowerCase();
        if(!ToutiaoUtil.isFileAllowed(fileExt)){
            return null;
        }

        String fileName = UUID.randomUUID().toString().replaceAll("-","") + "." + fileExt;
        /** Files.copy(InputStream in, Path target, CopyOption… options): copy bytes from a file to I/O streams or from I/O streams to a file;
         * MultipartFile.getInputStream: Return an InputStream to read the contents of the file from;
         * File.topath(): obtain a Path that uses the abstract path represented by a File object to locate a file;
         * CopyOption: StandardCopyOption.REPLACE_EXISTING(if image already exists, replace the old image); */
        Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR + fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        /** return the url of upload image for front end usage */
        return ToutiaoUtil.TOUTIAO_DOMAIN + "image?name=" + fileName;

    }

    /*
    public int updateCommentCount(int id, int count) {
        return newsDAO.updateCommentCount(id, count);
    }
    */
}
