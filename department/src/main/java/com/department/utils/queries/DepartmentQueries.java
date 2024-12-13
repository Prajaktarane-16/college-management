package com.department.utils.queries;

public class DepartmentQueries {

    public static final String addDepartmentQuery = "insert into department (dep_id , dep_name,description,hod_id) values (?,?,?,?)";

    public static final String getSpecificDepData = "select dep_id,dep_name,description,hod_id from department where dep_id = ?";

    public static final String getDepartmentData = "select dep_id,dep_name,description,hod_id from department";
}
