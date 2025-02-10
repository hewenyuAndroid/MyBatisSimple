package com.example.pojo;

public class Emp {

    private Integer empId;
    private String empUsername;
    private String empPassword;
    private Integer age;
    private String gender;
    private Integer deptId;

    public Emp() {
    }

    public Emp(Integer empId, String empUsername, String empPassword, Integer age, String gender, Integer deptId) {
        this.empId = empId;
        this.empUsername = empUsername;
        this.empPassword = empPassword;
        this.age = age;
        this.gender = gender;
        this.deptId = deptId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpUsername() {
        return empUsername;
    }

    public void setEmpUsername(String empUsername) {
        this.empUsername = empUsername;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "empId=" + empId +
                ", empUsername='" + empUsername + '\'' +
                ", empPassword='" + empPassword + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", deptId=" + deptId +
                '}';
    }
}
