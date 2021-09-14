package com.entity;

import com.entity.empl.Employee;
import com.model.Model;
import com.model.PayForOnePerson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class Department<T extends Employee> {

    protected String name;
    protected SalariesFund fund;

    public Department(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    public abstract void addEmployee(T employee);

    public abstract List<T> getEmployeeList();

    public String getName() {
        return name;
    }


    public SalariesFund getFund() {
        return fund;
    }

    public void setFund(SalariesFund fund) {
        Objects.requireNonNull(fund);
        this.fund = fund;
    }
    public BigDecimal getRate(){
        BigDecimal result = new BigDecimal("0");
        for (Employee employeeTmp: getEmployeeList()){
            result = result.add(employeeTmp.getRate());
        }
        return result;
    }

    public BigDecimal getMinSalary(Date date){
        BigDecimal result = new BigDecimal("0");
        for (Employee employeeTmp: getEmployeeList()){
            result = result.add(employeeTmp.getMinSalary(date));
        }
        return result;
    }

    public List<PayForOnePerson> calculateSalary() {
        List<PayForOnePerson> personList = new ArrayList<>();

        if (this.getFund().getType().equals(SalariesFund.FundType.BALANCED)){
            personList.addAll(calculateBalancedSalary());
        }else if (this.getFund().getType().equals(SalariesFund.FundType.UNBALANCED)){
            personList.addAll(calculateUnbalancedSalary());
        }

        return personList;
    }

    private PayForOnePerson initPayAccount(Employee employee, BigDecimal empPremium){
        PayForOnePerson result = new PayForOnePerson(employee,
                empPremium.add(employee.getRate()));
        result.setDepartment(this);
        result.setPremium(empPremium);
        return result;
    }

    private List<PayForOnePerson> calculateBalancedSalary(){
        List<PayForOnePerson> result = new ArrayList<>();
        BigDecimal empPremium = calculateBalancedPremiumValue();
        for (Employee employee: this.getEmployeeList()){
            result.add(initPayAccount(employee,empPremium));
        }
        return result;
    }

    private BigDecimal calculateBalancedPremiumValue(){
        BigDecimal amount = this.getFund().getAmount();
        int amountParts = this.getEmployeeList().size();
        BigDecimal premium = amount.divide(new BigDecimal(amountParts), Model.mc);
        return premium;
    }

    private List<PayForOnePerson> calculateUnbalancedSalary(){
        List<PayForOnePerson> result = new ArrayList<>();
        for (Employee employee: this.getEmployeeList()){
            BigDecimal empPremium = calculateUnbalancedPremiumValueForEmployee(employee);
            result.add(initPayAccount(employee,empPremium));
        }
        return result;
    }

    private BigDecimal calculateUnbalancedPremiumValueForEmployee(Employee employee){
        BigDecimal manPart = employee.getRate().divide(this.getRate(),Model.mc);
        BigDecimal premium = manPart.multiply(this.getFund().getAmount());
        return premium;
    }



}
