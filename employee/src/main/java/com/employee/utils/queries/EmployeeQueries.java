package com.employee.utils.queries;

public class EmployeeQueries {

    public static final String addEmpQuery = "insert into employee (emp_id,emp_name,emp_mobile,desgination_id) values (?,?,?,?)";

    public static final String getEmployeeById = "select emp_id,emp_name,emp_mobile,desgination_id from employee where emp_id = ?;";

    public static final String getEmployeeDetails = "select emp_id,emp_name,emp_mobile,desgination_id from employee ";
}
