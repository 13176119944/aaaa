package com.lening.controller;

import com.lening.entity.MeunBean;
import com.lening.service.MeunService;
import com.lening.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/meun")
public class MeunController {
    @Resource
    private MeunService meunService;

    @RequestMapping("/getMeunListByPid")
    public List<MeunBean> getMeunListByPid(Long pid){
        List<MeunBean> meunListByPid = meunService.getMeunListByPid(pid);
        System.out.println(meunListByPid);
        return meunListByPid;
    }

    @RequestMapping("/saveMeun")
    public ResultInfo ResultInfo(@RequestBody MeunBean meunBean){
        try {
            meunService.saveMeun(meunBean);
            return new ResultInfo(true,"编辑成功");
        }catch (Exception e){
            return new ResultInfo(false,"编辑失败");
        }
    }

    @RequestMapping("/deleteMeunByPid")
    public ResultInfo deleteMeunByPid(Long id) {
        System.out.println(id+"---------------------");
        try {
            meunService.deleteMeunByPid(id);
            return new ResultInfo(true, "删除成功");
        } catch (Exception e) {
            return new ResultInfo(false, "删除失败");
        }
    }

}
