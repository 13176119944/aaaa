package com.lening.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.MeunBean;
import com.lening.entity.PostBean;
import com.lening.entity.PostBeanExample;
import com.lening.mapper.MeunMapper;
import com.lening.mapper.PostMapper;
import com.lening.service.PostService;
import com.lening.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Resource
    private PostMapper postMapper;
    @Resource
    private MeunMapper meunMapper;


    @Override
    public Page<PostBean> getPostListConn(PostBean postBean, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PostBeanExample example = new PostBeanExample();
        PostBeanExample.Criteria criteria = example.createCriteria();
        if(postBean!=null){
            if(postBean.getPostname()!=null&&postBean.getPostname().length()>=1){
                criteria.andPostnameLike("%"+postBean.getPostname()+"%");
            }
        }
        List<PostBean> postBeans = postMapper.selectByExample(example);
        PageInfo<PostBean>  pageInfo = new PageInfo<>(postBeans);
        Long total = pageInfo.getTotal();
        Page<PostBean> page = new Page<>(pageInfo.getPageNum()+"",total.intValue(),pageInfo.getPageSize()+"");
        page.setList(postBeans);
        return page;
    }

    @Override
    public List<MeunBean> getMeunListById(Long id) {
        List<MeunBean> list = meunMapper.selectByExample(null);
        List<Long> meunIds = postMapper.getMeunListById(id);
        if(meunIds!=null&&meunIds.size()>=1){
            for (Long meunId : meunIds) {
                for (MeunBean meunBean : list) {
                    if(meunId.equals(meunBean.getId())){
                        meunBean.setChecked(true);
                        break;
                    }
                }
            }
        }
        return list;
    }

    @Override
    public void savePostMeun(Long postid, Long[] ids) {
        postMapper.deletePostMeunByPostid(postid);
        if(ids!=null&ids.length>=1){
            for (Long meunid : ids) {
                postMapper.savePostMeun(postid,meunid);
            }
        }
    }
}
