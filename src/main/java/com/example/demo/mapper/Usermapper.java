package com.example.demo.mapper;

import com.example.demo.map.columns;
import com.example.demo.map.dfdsf;
import com.example.demo.map.tb_user;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper

public interface Usermapper {
    @Select("select * from dfdsf")
    public List<dfdsf> get();
    @Insert("INSERT INTO dfdsf VALUES (#{username},#{password},null,null,null)")
    public int insert(String username,String password);

    @Select("SELECT * FROM dfdsf WHERE username=#{username}")
    public dfdsf select(String username);
    @Update("UPDATE dfdsf SET 头像=#{url},头像名称=#{filename} WHERE username=#{username};")
    public int updata(String url, String username,String filename);
    @Select("select * from columns limit 6 offset #{pageNum}")
    public List<columns> getshopsee(Integer pageNum);

    @Insert("INSERT INTO columns VALUES (#{coverurl},null,#{wzname},#{wzlex},#{wzuser},0)")
    public int insertarticle(String coverurl,String wzname,String wzlex,String wzuser);
    @Select("SELECT * FROM columns WHERE id=#{id} ")
    public columns selectbyarticle(String id);
    @Update("UPDATE columns SET liked=#{liked} WHERE id=#{id};")
    public int updatabylike(int liked, int id);
    @Insert("INSERT INTO tb_user VALUES (null,#{wzuser},#{username})")
    public int updateattention(String wzuser,String username);
    @Delete("DELETE FROM tb_user WHERE username=#{wzuser} AND followusername=#{username}")
    public int delectattention(String wzuser,String username);
    @Select("SELECT * FROM tb_user WHERE username=#{user_id} AND followusername=#{followuser_id}")
    public tb_user selectbyattention(String user_id,String followuser_id);

    @Select("SELECT * FROM  tb_user WHERE username=#{user_id}")
    public List<tb_user> getbyfriend(String user_id);



}
