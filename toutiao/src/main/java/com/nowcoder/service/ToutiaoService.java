package com.nowcoder.service;

import org.springframework.stereotype.Service;

/**
 * Created by nowcoder on 2016/6/26.
 */

/** Use @Service annotation to define a service(ToutiaoService here) */
@Service
public class ToutiaoService {
    public String say() {
        return "This is from ToutiaoService";
    }
}
