package com.lening.service.impl;

import com.lening.entity.MeunBean;
import com.lening.entity.MeunBeanExample;
import com.lening.mapper.MeunMapper;
import com.lening.service.MeunService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MeunServiceImpl implements MeunService {
    @Resource
    private MeunMapper meunMapper;
    @Override
    public List<MeunBean> getMeunListByPid(Long pid) {
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        List<MeunBean> list = meunMapper.selectByExample(example);
        return list;
    }

    @Override
    public void saveMeun(MeunBean meunBean) {
        if(meunBean!=null){
            if(meunBean.getId()!=null){
                //修改
                meunMapper.updateByPrimaryKeySelective(meunBean);
            }else{
                //添加
                meunMapper.insert(meunBean);
            }
        }
    }

    Set<Long> ids = new HashSet<>();
    @Override
    public void deleteMeunByPid(Long id) {
        getMeunListByPidToDel(id);
        for (Long idd : ids) {
            meunMapper.deleteByPrimaryKey(idd);
        }
    }


    private void getMeunListByPidToDel(Long pid) {
        ids.add(pid);
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        List<MeunBean> list = meunMapper.selectByExample(example);
        if(list!=null&&list.size()>=1){
            for (MeunBean bean : list) {
                getMeunListByPidToDel(bean.getId());
            }
        }
    }
}
