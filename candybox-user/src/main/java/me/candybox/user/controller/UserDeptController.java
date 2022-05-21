package me.candybox.user.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import me.candybox.core.model.BaseModel;
import me.candybox.core.service.CbDataService;
import me.candybox.core.vo.ResultVO;
import me.candybox.user.model.UserDeptInfo;
import me.candybox.user.vo.UserDeptInfoTreeVO;

@Slf4j
@RestController
@RequestMapping("/api/user/dept/")
public class UserDeptController {

    @Autowired
    private CbDataService cbDataService;

    
    @Operation(summary ="组织机构树查询")
    @GetMapping("/tree")
    public ResultVO userDeptInfoTree(@Parameter(description="父id",required = true) @RequestParam(defaultValue = "0") String parentId){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("parent_id", parentId);
        queryWrapper.orderByAsc("create_time");
        List<BaseModel> list = cbDataService.selectList(new UserDeptInfo(),queryWrapper);
        if(list==null){
            return new ResultVO();
        }
        List<UserDeptInfoTreeVO> userDeptInfoTreeVOs = new ArrayList<>();
        list.forEach(item->{
            UserDeptInfo userDeptInfo = (UserDeptInfo)item;
            UserDeptInfoTreeVO userDeptInfoTreeVO = new UserDeptInfoTreeVO();
            BeanUtils.copyProperties(userDeptInfo, userDeptInfoTreeVO);
            userDeptInfoTreeVO.setValue(JSON.parseObject("{'id':'"+userDeptInfoTreeVO.getId()+"','name':'"+userDeptInfoTreeVO.getName()+"'}"));
            userDeptInfoTreeVOs.add(userDeptInfoTreeVO);
        });
        return new ResultVO(userDeptInfoTreeVOs);
    }

    
}
