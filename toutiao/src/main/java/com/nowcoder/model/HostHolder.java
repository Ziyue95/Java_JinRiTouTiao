package com.nowcoder.model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    /** Use ThreadLocal construct to store User information in local thread */
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }

}
