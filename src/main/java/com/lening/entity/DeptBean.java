package com.lening.entity;

import java.util.List;

public class DeptBean {
    private Long[] postids;

    private List<PostBean> postBeans;

    public Long[] getPostids() {
        return postids;
    }

    public void setPostids(Long[] postids) {
        this.postids = postids;
    }

    public List<PostBean> getPostBeans() {
        return postBeans;
    }

    public void setPostBeans(List<PostBean> postBeans) {
        this.postBeans = postBeans;
    }

    private Long id;

    private String deptname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
    }
}