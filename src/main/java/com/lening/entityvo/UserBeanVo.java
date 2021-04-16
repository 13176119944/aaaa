package com.lening.entityvo;

import com.lening.entity.DeptBean;
import com.lening.entity.UserBean;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserBeanVo extends UserBean implements Serializable {
    private Long[] deptids;

    private List<DeptBean> dlist;
}
