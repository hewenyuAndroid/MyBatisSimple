package com.example.pojo;

public class Emp2 {

    private Integer empId;
    private String empUsername;
    private String empPassword;
    private Integer age;
    private String gender;
    private Dept dept;

    public Emp2() {
    }

    public Emp2(Integer empId, String empUsername, String empPassword, Integer age, String gender, Dept dept) {
        this.empId = empId;
        this.empUsername = empUsername;
        this.empPassword = empPassword;
        this.age = age;
        this.gender = gender;
        this.dept = dept;
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

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp2{" +
                "empId=" + empId +
                ", empUsername='" + empUsername + '\'' +
                ", empPassword='" + empPassword + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", dept=" + dept +
                '}';
    }
}
