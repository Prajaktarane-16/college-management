package com.designation.utils.queries;

public class DesignationQueries {

    public static final String addDesignationQuery = "insert into desgination (desg_id ,desg_name) values (?,?)";

    public static final String getDesignationById = "select desg_id,desg_name from desgination where desg_id = ?";
}
