package com.example.demo.config;

import com.example.demo.map.dfdsf;
import com.example.demo.map.tb_user;
import com.example.demo.mapper.Usermapper;
import com.example.demo.serivce.service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class friend {
    @Autowired
    private Usermapper usermapper;

@GetMapping("/getbyfriend")
    public Result getbyfriend(HttpServletRequest request){
    String username=service.tokentouser(request);
    List<tb_user> friends=usermapper.getbyfriend(username);
//    System.out.println(friends);
    List<dfdsf> usernames = new ArrayList<>();
    for(tb_user followusername:friends){
//        System.out.println(followusername);
//        System.out.println(followusername.getFollowusername());
        String follusername = followusername.getFollowusername();
        dfdsf dfdsf=usermapper.select(follusername);
        usernames.add(dfdsf);

    }
    System.out.println(usernames);
    return Result.success(usernames);



    }
}
