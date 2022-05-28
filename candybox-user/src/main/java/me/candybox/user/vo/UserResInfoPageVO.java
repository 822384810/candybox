package me.candybox.user.vo;

import java.util.List;
import lombok.Data;

@Data
public class UserResInfoPageVO {
    private List<UserResInfoChildrenVO> children;
}
