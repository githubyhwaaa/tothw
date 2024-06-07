package com.example.demo.config;
import com.example.demo.map.columns;
import com.example.demo.map.dfdsf;
import com.example.demo.map.tb_user;
import com.example.demo.mapper.Usermapper;
import com.example.demo.serivce.service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class artcledetiles {
    @Autowired
    Usermapper usermapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @GetMapping("/{id}")
    public Result getarticle(@PathVariable(value = "id") String id){
        System.out.println("zdnm"+id);
        columns columns=usermapper.selectbyarticle(id);
        return Result.success(columns);
    }
    @PostMapping("/{id}/like")
    public  Result likeblog(@PathVariable(value = "id") int id, HttpServletRequest request, int liked){

        String username = service.tokentouser(request);
        System.out.println("dsa"+username);

        //1.获取用户
//        dfdsf dfdsf=usermapper.select(username);
        //2.判断用户是否点赞
        Boolean member = stringRedisTemplate.opsForSet().isMember(String.valueOf(id),username);
        System.out.println(member);
        if(member==false){
            usermapper.updatabylike(liked,id);
            stringRedisTemplate.opsForSet().add(String.valueOf(id),username);

        }
        else{
            usermapper.updatabylike(liked,id);
            stringRedisTemplate.opsForSet().remove(String.valueOf(id),username);
        }

        //如果未点赞 点赞+1
        //数据库+1
        //如果点赞 点赞-1
        //数据库-1
        return Result.success();
    }
    @GetMapping("/{id}/back")
    public Result back(@PathVariable(value = "id") int id,HttpServletRequest request){
        String username = service.tokentouser(request);
        Boolean member =stringRedisTemplate.opsForSet().isMember(String.valueOf(id),username);
        if(member==true){
            return Result.success(true);
        }
        else{
            return  Result.success(false);
        }

    }
    @PostMapping("/userurl")
    public Result userurl(String username){
        System.out.println("sdsd");
        dfdsf dfdsf=usermapper.select(username);
        return  Result.success(usermapper.select(username));

    }
    @PutMapping("/attention")
    public Result attention(HttpServletRequest request,String wzuser,Boolean isfollow){
        String username=service.tokentouser(request);
        System.out.println(wzuser);

        //判断是否关注，如果关注取关,如果没关注关注
        if(isfollow==true){
            usermapper.updateattention(wzuser,username);
        }
        else {
            usermapper.delectattention(wzuser,username);
        }
        return Result.success();
    }
    @PostMapping("/isattention")
    public Result isattention(HttpServletRequest request,String wzuser){
        String username=service.tokentouser(request);
        tb_user tbUser=usermapper.selectbyattention(wzuser,username);
        System.out.println(tbUser==null);
        if(tbUser==null){
            return Result.success(false);
        }
        else {
            return Result.success(true);
        }


    }

}
