package com.lening.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.DeptBean;
import com.lening.entity.DeptBeanExample;
import com.lening.entity.PostBean;
import com.lening.mapper.DeptMapper;
import com.lening.mapper.PostMapper;
import com.lening.service.DeptService;
import com.lening.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private PostMapper postMapper;
    @Override
    public Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        DeptBeanExample example = new DeptBeanExample();
        DeptBeanExample.Criteria criteria = example.createCriteria();
        if(deptBean!=null){
            if(deptBean.getDeptname()!=null&&deptBean.getDeptname().length()>=1){
                criteria.andDeptnameLike("%"+deptBean.getDeptname()+"%");
            }
        }
        List<DeptBean> list = deptMapper.selectByExample(example);
        PageInfo<DeptBean> pageInfo = new PageInfo<>(list);
        Long total = pageInfo.getTotal();
        Page<DeptBean> page = new Page<>(pageInfo.getPageNum()+"",total.intValue(),pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }

    @Override
    public DeptBean toAddpost(Long id) {
        DeptBean deptBean = deptMapper.selectByPrimaryKey(id);
        List<PostBean> list = postMapper.selectByExample(null);
        Long[] postids = deptMapper.selectPostidsByDeptid(id);
        deptBean.setPostBeans(list);
        deptBean.setPostids(postids);
        return deptBean;
    }

    @Override
    public void savePost(Long id, Long[] postids) {
        if(postids !=null&&postids.length>=1) {
            for (Long postid : postids) {
                deptMapper.saveDeptPost(id, postid);
            }
        }
    }
}
