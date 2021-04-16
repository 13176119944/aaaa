package com.lening.service;

import com.lening.entity.DeptBean;
import com.lening.utils.Page;

import java.util.List;

public interface DeptService {
    Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize);

    DeptBean toAddpost(Long id);

    void savePost(Long id, Long[] postids);
}
