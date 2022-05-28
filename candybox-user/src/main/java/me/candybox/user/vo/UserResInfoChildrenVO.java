package me.candybox.user.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class UserResInfoChildrenVO {
    private String label;
    private String icon;
    private String url;
    private String link;
    private String schemaApi;
    private Object schema;
    private String isDefaultPage;
    private List<UserResInfoChildrenVO> children;
}
