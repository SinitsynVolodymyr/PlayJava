package com.model;

import com.entity.empl.Employee;

import java.math.BigDecimal;

public class PayForOnePerson {

    private Employee employee;
    private String departmentName;
    private BigDecimal salary;
    private BigDecimal premium;

    public PayForOnePerson(Employee employee, BigDecimal salary) {
        this.employee = employee;
        this.salary = salary;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public Employee getEmployee() {
        return employee;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}
