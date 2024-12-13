package com.courses.utils.queries;

public class CourseQueries {

    public static final String addCourseQuery = "insert into courses (course_id , course_name,description,dep_id,professor_id) values (?,?,?,?,?)";

    public static final String getCoursesById = "select course_id,course_name,description,dep_id,professor_id from courses where course_id = ?";

    public static final String getCourseDetails = "select course_id,course_name,description,dep_id,professor_id from courses";
}
