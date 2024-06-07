package com.example.demo.config;

import ch.qos.logback.core.util.FileUtil;
import com.example.demo.mapper.Usermapper;
import com.example.demo.utiles.jwtutil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.serivce.service;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.Map;

@RestController
@CrossOrigin
public class neirong {
    @Autowired
    private Usermapper usermapper;

    @GetMapping("/shopsee")
    public Result shopsee(){
        System.out.println(usermapper.getshopsee(0));
        return Result.success(usermapper.getshopsee(0));
    }
    @PostMapping("/cover")
    public  Result coveer(MultipartFile file) throws IOException {
        String filename = service.SetName(file);
        file.transferTo(new File("D:\\javakaifagongju\\封面\\"+filename));

        String path="D:\\javakaifagongju\\封面\\"+filename;
        service.clear();//清除因添加图片但没有文章上传的图片
        return Result.success(path);
    }
    @PostMapping("/addarticle")
    public  Result addarticle(String biaoti, String neirong, String fengmian, HttpServletRequest request) throws IOException {

        String username = service.tokentouser(request);

        File file1 =new File(fengmian);
        System.out.println("名字是"+file1.getName());
        File file2 = new File("D:\\javakaifagongju\\封面地址");
        FileUtils.moveFileToDirectory(file1,file2,false);
        String url = "http://localhost:8088/cover/"+file1.getName();
        usermapper.insertarticle(url,biaoti,neirong,username);


//        file.transferTo(new File("D:\\javakaifagongju\\封面\\"+neirong));
//        System.out.println(file);
        return Result.success();
        }

    @PostMapping("/pagingby")
    public Result pagingby(Integer page){
        Integer pageNum=6*(page-1);
        return Result.success(usermapper.getshopsee(pageNum));

    }


}
