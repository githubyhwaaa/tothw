package com.example.demo;

import com.example.demo.mapper.Usermapper;
import lombok.val;
import org.apache.catalina.User;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import java.io.File;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    Usermapper usermapper;



    @Test
    void  yihongwei(){
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


}
