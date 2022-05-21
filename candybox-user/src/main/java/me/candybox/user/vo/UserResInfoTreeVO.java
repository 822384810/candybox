package me.candybox.user.vo;


import java.util.List;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class UserResInfoTreeVO {
    private boolean defer=false;
    private String name;
    private String id;
    private String tag;
    private String descript;
    private List<UserResInfoTreeVO> children;
}
