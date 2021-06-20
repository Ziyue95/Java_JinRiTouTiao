package com.nowcoder.service;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.util.ToutiaoUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public Map<String, Object> register(String username, String password){
        /** create a Map object using HashMap to store login/register information */
        Map<String, Object> map = new HashMap<String, Object>();

        /** Blank username case: */
        if(StringUtils.isBlank(username)){
            map.put("msgname", "username can't be null!!!");
            return map;
        }

        /** Blank password case: */
        if(StringUtils.isBlank(password)){
            map.put("msgpwd", "password can't be null;");
            return map;
        }

        /** Username has been registered */
        User user = userDAO.selectByName(username);
        if(user != null){
            map.put("msgname", "username has been registered;");
            return map;
        }

        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        //Login
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;

    }

    private  String addLoginTicket(int userId){
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replace("-",""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }

    public Map<String, Object> login(String username, String password){
        Map<String, Object> map = new HashMap<String, Object>();

        /** Blank username case: */
        if(StringUtils.isBlank(username)){
            map.put("msgname", "username can't be null;");
            return map;
        }

        /** Blank password case: */
        if(StringUtils.isBlank(password)){
            map.put("msgpwd", "password can't be null;");
            return map;
        }

        /** Username has been registered */
        User user = userDAO.selectByName(username);
        if(user == null){
            map.put("msgname", "username does not exist;");
            return map;
        }

        /** Incorrect password case: */
        if(!ToutiaoUtil.MD5(password + user.getSalt()).equals(user.getPassword())){
            map.put("msgpwd","Incorrect password");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public void logout(String ticket){
        loginTicketDAO.updateStatus(ticket,1);
    }
}
