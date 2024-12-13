package com.student.utils.queries;

public class StudentQueries {

    public static final String addStudentQuery = "insert into students (stud_id, stud_name,gender,dep_id) values (?,?,?,?)";
    public static final String getStudentById = "select stud_id,stud_name,gender,dep_id from students where stud_id = ?";
    public static final String getStudentDetails = "select stud_id,stud_name,gender,dep_id from students";
    public static final String getStudDeptData = "SELECT stud_id, stud_name, gender, dep_id FROM students WHERE dep_id = ?";
}
