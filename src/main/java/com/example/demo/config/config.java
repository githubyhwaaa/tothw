package com.example.demo.config;

//import com.alibaba.fastjson2.JSON;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.map.dfdsf;
import com.example.demo.mapper.Usermapper;
import com.example.demo.serivce.service;
import com.example.demo.utiles.jwtutil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;



@RestController
@CrossOrigin
public class config {
    @Autowired
    private Usermapper usermapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping("/hello")
    public Result get() {
        String shop = stringRedisTemplate.opsForValue().get("账号密码");
        if (shop!=null){
            val jsonArray = JSON.parseArray(shop);
            return Result.success(jsonArray);
        }
        List<dfdsf> dfdsf=usermapper.get();
        stringRedisTemplate.opsForValue().set("账号密码",JSON.toJSONString(usermapper.get()));
        return Result.success((dfdsf));



//
    }
    @GetMapping("/first")
    public Result first(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Map<String, Object> map = jwtutil.parsetoken(token);
        String username = map.get("username").toString();
        System.out.println(username);
        String shop =stringRedisTemplate.opsForValue().get("username"+username);
        System.out.println("zhesshop"+shop);
//        val jsonArray = JSON.parseArray(shop);
        System.out.println(JSON.parseObject(shop));
        if (shop != null) {
            return Result.success(JSON.parseObject(shop));
        }
//        System.out.println(shop !=null);
        dfdsf dfdsf = usermapper.select(username);
        if (dfdsf == null) {
            return Result.err("用户名错误");
        }
        stringRedisTemplate.opsForValue().set("username"+username,JSON.toJSONString(dfdsf));
        return Result.success(usermapper.select(username));
//        return Result.success("wqe");
    }
    @PostMapping("/hello1")
    public Result insert(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        dfdsf user = usermapper.select(username);
        System.out.println(user);
        if (user == null) {
            usermapper.insert(username, password);
            return Result.success();
        } else {
            return Result.err("用户名占用");
        }
    }

//        return "aaa";


    @PostMapping("/login")
    public Result login(String username, String password) {
        dfdsf user = usermapper.select(username);
        System.out.println(user);
        System.out.println(Result.err("SDA"));

        if (user == null) {
            return Result.err("用户名错误");
        }
        if (password.equals(user.getPassword())) {
            Map<String,Object> claim= new HashMap<>();
            claim.put("username",username);
            claim.put("password",password);
            String token=jwtutil.grtToken(claim);
            System.out.println(token);
            stringRedisTemplate.opsForValue().set(token,token,1, TimeUnit.HOURS);
            Map<String,Object>aa=jwtutil.parsetoken(token);
            System.out.println(aa);
            return Result.success(token);
        } else {
            return Result.err("登录失败");
        }
    }
    @PostMapping("/upload")
    public Result upload(MultipartFile file, HttpServletRequest request) throws IOException {
//        拿取请求头中的token，解析到username
        String username = service.tokentouser(request);

//        给拿取到的图片进行加工,存入本地文件
        String aa = file.getOriginalFilename();
        System.out.println(aa+"12345678");
//        给图片给予唯一名字
        String filename= UUID.randomUUID().toString()+aa.substring(aa.lastIndexOf("."));
//         存入地址
        file.transferTo(new File("D:\\javakaifagongju\\图片\\"+filename));
//        获取老头像的名称，如果要更改新头像把老头像删除
        dfdsf dfdfs=usermapper.select(username);
        System.out.println(dfdfs);
//        得到老头像地址，然后删除
        String oldname=dfdfs.get头像名称();
        System.out.println(oldname);
        File file2=new File("D:\\javakaifagongju\\图片\\"+oldname);
        file2.delete();
//        url为老头像地址
        String url="http://localhost:8088/img/"+filename;
//        存入数据库
        usermapper.updata(url,username,filename);
        stringRedisTemplate.delete("username"+username);
        System.out.println(url);



        return Result.success("sd");


    }


}
