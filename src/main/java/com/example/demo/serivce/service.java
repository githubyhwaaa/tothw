package com.example.demo.serivce;

import com.example.demo.utiles.jwtutil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class service {
    public static String  SetName(MultipartFile file){
        //        给拿取到的图片进行加工,存入本地文件
        String aa = file.getOriginalFilename();
        System.out.println(aa+"12345678");
        String filename= UUID.randomUUID().toString()+aa.substring(aa.lastIndexOf("."));
        return filename;

    }
    public static void  clear(){
        File file1=new File("D:\\javakaifagongju\\封面\\");//获取目录

        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE,-1);
        Date date2 = calendar.getTime();//这是获取现在时间的前一天的时间

        File[] files = file1.listFiles();//获取这个文件下面的列表
//        System.out.println(files);
        long long2=date2.getTime();//将data数据转换为long
        for (File file11:files){
            long l = file11.lastModified();//获取这张图面最后被修改的时间
//如果l比long2短，也就是说图片创立的时间大于一天就删除
            if(l<long2){
                file11.delete();

            }
        }
    }
    public static String tokentouser(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        Map<String, Object> map = jwtutil.parsetoken(token);
        String username = map.get("username").toString();
        return username;
    }
}
