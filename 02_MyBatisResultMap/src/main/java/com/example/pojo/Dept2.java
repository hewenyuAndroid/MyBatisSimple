package com.example.pojo;

import java.util.List;

public class Dept2 {

    private Integer deptId;
    private String deptName;
    private List<Emp> list;

    public Dept2() {
    }

    public Dept2(Integer deptId, String deptName, List<Emp> list) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.list = list;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<Emp> getList() {
        return list;
    }

    public void setList(List<Emp> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Dept2{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", list=" + list +
                '}';
    }
}
