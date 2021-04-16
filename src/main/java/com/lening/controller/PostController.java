package com.lening.controller;

import com.lening.entity.MeunBean;
import com.lening.entity.PostBean;
import com.lening.service.PostService;
import com.lening.utils.Page;
import com.lening.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Resource
    private PostService posetService;

    @RequestMapping("/getPostListConn")
    public Page<PostBean> getPostListConn(@RequestBody PostBean postBean, @RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "3")Integer pageSize){
        Page<PostBean> postListConn = posetService.getPostListConn(postBean, pageNum, pageSize);
        return postListConn;
    }

    @RequestMapping("/getMeunListById")
    public List<MeunBean> getMeunListById(Long id){
        return posetService.getMeunListById(id);
    }

    @RequestMapping("/savePostMeun")
    public ResultInfo savePostMeun(Long postid,@RequestBody Long[] ids){
        try {
            posetService.savePostMeun(postid,ids);
            return new ResultInfo(true,"保存成功");
        }catch (Exception e ){
           return  new ResultInfo(false,"保存失败");
        }
    }
}
