package com.lening.controller;

import com.lening.entity.DeptBean;
import com.lening.mapper.DeptMapper;
import com.lening.service.DeptService;
import com.lening.utils.Page;
import com.lening.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private DeptService deptService;

    @RequestMapping("/getDeptListConn")
    public Page<DeptBean> getDeptListConn(@RequestBody DeptBean deptBean, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "3")Integer pageSize){
        return deptService.getDeptListConn(deptBean,pageNum,pageSize);
    }

    @RequestMapping("/toAddpost")
    public DeptBean toAddpost(Long id){
        return deptService.toAddpost(id);
    }

    @RequestMapping("/savePost")
    public ResultInfo savePost(Long id, @RequestBody Long[] postids){

        System.out.println(postids.length+"DeptController");
        try {
            deptService.savePost(id,postids);
            return new ResultInfo(true,"保存成功");
        }catch (Exception e){
            return new ResultInfo(false,"保存失败");
        }
    }

}
