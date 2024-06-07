package com.example.demo.map;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@Data
public class tb_user {
    private int id;
    private String username;
    private String followusername;
}
