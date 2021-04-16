package com.lening.mapper;

import com.lening.entity.DeptBean;
import com.lening.entity.MeunBean;
import com.lening.entity.UserBean;
import com.lening.entity.UserBeanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserMapper {

    int deleteUserPostById(@Param("id") Long id);

    int deleteUserDeptById(@Param("id") Long id);

    int insertUserDept(@Param("userid")Long userid,@Param("deptid")Long deptid);

    Long[] getUserDeptidsById(@Param("id") Long id);

    long countByExample(UserBeanExample example);

    int deleteByExample(UserBeanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserBean record);

    int insertSelective(UserBean record);

    List<UserBean> selectByExample(UserBeanExample example);

    UserBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByExample(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByPrimaryKeySelective(UserBean record);

    int updateByPrimaryKey(UserBean record);

    List<DeptBean> getUserDeptById(Long id);

    void saveUserPost(@Param("userid") Long userid,@Param("postid") Long postid);

    List<UserBean> getlong(UserBean ub);

    Set<String> getUserMeunUrlsById(@Param("userid") Long userid);

    List<MeunBean> getUserMeunListById(@Param("userid") Long userid);
}