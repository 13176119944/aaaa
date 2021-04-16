package com.lening.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.*;
import com.lening.mapper.DeptMapper;
import com.lening.mapper.MeunMapper;
import com.lening.mapper.UserMapper;
import com.lening.redis.RedisUtil;
import com.lening.service.UserService;
import com.lening.utils.MD5key;
import com.lening.utils.Page;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MeunMapper meunMapper;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private RedisUtil redisUtil;

    //全查
    @Override
    public List<UserBean> getUserList() {
        return userMapper.selectByExample(null);
    }

    //分页模糊
    @Override
    public Page<UserBean> getUserListConn(UserBean userBean,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        UserBeanExample example = new UserBeanExample();
        UserBeanExample.Criteria criteria = example.createCriteria();
        if(userBean!=null){
            if(userBean.getUname()!=null&&userBean.getUname().length()>=1){
            criteria.andUnameLike("%"+userBean.getUname()+"%");
            }
            if(userBean.getAge()!=null){
                criteria.andAgeGreaterThanOrEqualTo(userBean.getAge());
            }
            if(userBean.getEage()!=null){
                criteria.andAgeLessThanOrEqualTo(userBean.getEage());
            }
        }
        List<UserBean> list1 = userMapper.selectByExample(example);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list1);
        Long total = pageInfo.getTotal();
        Page<UserBean> page = new Page<UserBean>(pageInfo.getPageNum()+"",total.intValue(),pageInfo.getPageSize()+"");
        page.setList(list1);
        return page;
    }

    @Override
    public List<MeunBean> getMeunList(UserBean ub) {
        if(ub!=null){
            List<MeunBean> list = null;
            //从缓存中获取用户列表
            Object userListCache = redisUtil.getObject(ub.getId());

            //判断缓存中是否存在
            if (userListCache != null) {//不空，则强转返回
                System.out.println("redis中存在，直接返回");
                list = (List<MeunBean>) userListCache;
                System.out.println(list);

            }else{
                System.out.println("redis缓存中不存在，从数据库中取出，并且放入缓存");
                //查询数据库，取出
                list = userMapper.getUserMeunListById(ub.getId());
                //放入redis缓存
                redisUtil.putObject(ub.getId(), list);
            }
            return list;
        }
            return null;
    }

    //删除
    @Override
    public String delUser(Long id) {
        int i = userMapper.deleteByPrimaryKey(id);
        if(i>0){
            return "ok";
        }else{
            return "no";
        }
    }

    @Override
    public UserBean getUserVoById(Long id) {
        System.out.println(id);
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        Long[] deptids = userMapper.getUserDeptidsById(id);
        userBean.setDeptids(deptids);
        List<DeptBean> deptBeans = deptMapper.selectByExample(null);
        userBean.setDlist(deptBeans);
        return userBean;
    }

    @Override
    public void saveUserDept(Long id, Long[] deptids) {
            userMapper.deleteUserPostById(id);
            userMapper.deleteUserDeptById(id);
        for (Long deptid : deptids) {
            userMapper.insertUserDept(id,deptid);
        }
    }

    @Override
    public UserBean getUserInfo(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        List<DeptBean> dlist = userMapper.getUserDeptById(id);
        if(dlist!=null&&dlist.size()>=1){
            for (DeptBean deptBean : dlist) {
               List<PostBean> plist =  deptMapper.getDeptPostList(deptBean.getId());
               Long[] postids = deptMapper.getUserPostByIdAndDeptid(id,deptBean.getId());
               deptBean.setPostids(postids);
               deptBean.setPostBeans(plist);
            }
        }
        userBean.setDlist(dlist);
        return userBean;
    }

    @Override
    public void saveUserPost(UserBean userBean) {
        if(userBean!=null){
            userMapper.deleteUserPostById(userBean.getId());
            if(userBean.getDlist()!=null&&userBean.getDlist().size()>=1){
                for (DeptBean deptBean : userBean.getDlist()) {
                    if(deptBean.getPostBeans()!=null&&deptBean.getPostBeans().size()>=1){
                        for (Long postid : deptBean.getPostids()) {
                            userMapper.saveUserPost(userBean.getId(),postid);
                        }
                    }
                }
            }
        }
    }

    @Override
    public UserBean longin(UserBean ub) {
        if(ub!=null){
            List<UserBean> ulist = userMapper.getlong(ub);
            if(ulist!=null&&ulist.size()==1){
                UserBean userBean = ulist.get(0);
                String pwd = ub.getPwd();
                pwd = userBean.getPwdsalt()+pwd+userBean.getPwdsalt();
                MD5key md5key = new MD5key();
                String newpwd = md5key.getkeyBeanofStr(pwd);
                if(newpwd.equals(userBean.getPwd())){
                    return userBean;
                }
            }
        }
        return null;
    }

    @Override
    public Set<String> getUserMeunUrlsById(UserBean ub) {
        if(ub!=null){
            Set<String> urls = userMapper.getUserMeunUrlsById(ub.getId());
            System.out.println(urls+"7777777777777777777");
            return  urls;
        }
        return null;
    }

    @Test
    public void test(){
        String pwd = "123456";
        pwd = "1234"+pwd+"1234";
        MD5key md5key = new MD5key();
        String s = md5key.getkeyBeanofStr(pwd);
        System.out.println(s);
    }

    //回显
    public List<MeunBean> getMeunList2() {
        Long [] ids = {1L,2L,3L};
        List<MeunBean> list = meunMapper.selectByExample(null);
        for (Long id : ids) {
            for (MeunBean meunBean : list) {
                if(id.equals(meunBean.getId())){
                    meunBean.setChecked(true);
                    break;
                }
            }
        }
        return list;
    }
}
