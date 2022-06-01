package me.candybox.user.vo;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

/**
 * 单位信息
 */
@Data
public class UserDeptInfoTreeVO {
    private boolean defer=true;
    private String name;
    private String id;
    private String shortName;
    private JSONObject value;
}
