package me.candybox.user.vo;


import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class UserDeptInfoTreeVO {
    private boolean defer=true;
    private String name;
    private String id;
    private String shortName;
    private JSONObject value;
}
