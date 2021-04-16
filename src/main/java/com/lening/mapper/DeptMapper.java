package com.lening.mapper;

import com.lening.entity.DeptBean;
import com.lening.entity.DeptBeanExample;
import com.lening.entity.PostBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptMapper {
    int depeteDeptPost(@Param("deptid") Long deptid);

    int saveDeptPost(@Param("deptid")Long deptid,@Param("postid")Long postid);

    Long[] selectPostidsByDeptid(@Param("id")Long id);

    long countByExample(DeptBeanExample example);

    int deleteByExample(DeptBeanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeptBean record);

    int insertSelective(DeptBean record);

    List<DeptBean> selectByExample(DeptBeanExample example);

    DeptBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeptBean record, @Param("example") DeptBeanExample example);

    int updateByExample(@Param("record") DeptBean record, @Param("example") DeptBeanExample example);

    int updateByPrimaryKeySelective(DeptBean record);

    int updateByPrimaryKey(DeptBean record);

    List<PostBean> getDeptPostList(@Param("deptid") Long deptid);

    Long[] getUserPostByIdAndDeptid(@Param("userid") Long userid,@Param("deptid") Long deptid);
}