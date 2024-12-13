package com.courses.repositories;

import com.courses.entities.CoursesEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static com.courses.utils.queries.CourseQueries.*;

@Slf4j
@Repository
public class CourseRepository {

    @Autowired
    @Qualifier("mysqlJdbcTemplateCollege")
    JdbcTemplate jdbcCollege;


    public int addCourseDetails(int course_id,String course_name,String description,int dep_id,int professor_id) {
        KeyHolder holder = new GeneratedKeyHolder();
        int id;
        try {
            jdbcCollege.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(addCourseQuery, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, course_id);
                    ps.setString(2, course_name);
                    ps.setString(3, description);
                    ps.setInt(4, dep_id);
                    ps.setInt(5, professor_id);

                    return ps;

                }
            }, holder);
            id = holder.getKey().intValue();
        } catch (DataAccessException e) {
            e.printStackTrace();
            id = 0;
        }
        return id;
    }

    public Optional<CoursesEntity> getMappingDetailsById(int courseId){
        Optional<CoursesEntity> mappingDetails = null;
        try{
            //lamba is used here
            RowMapper<CoursesEntity> rowMapper = (rs, rowNum) -> {
                CoursesEntity mappingDetailsModel = new CoursesEntity();
                mappingDetailsModel.setCourse_id(rs.getInt("course_id"));
                mappingDetailsModel.setCourse_name(rs.getString("course_name"));
                mappingDetailsModel.setDescription(rs.getString("description"));
                mappingDetailsModel.setDep_id(rs.getInt("dep_id"));
                mappingDetailsModel.setProfessor_id(rs.getInt("professor_id"));
                return  mappingDetailsModel;
            };
            mappingDetails = jdbcCollege.query(getCoursesById,new Object[]{courseId},rowMapper).stream().findFirst();
        } catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return mappingDetails;
    }

    public List<CoursesEntity> getCourseDetails(){
        List<CoursesEntity> mappingList = null;
        try{
            RowMapper<CoursesEntity> rowMapper = (rs, rowNum) -> {
                CoursesEntity mapping = new CoursesEntity();
                mapping.setCourse_id(rs.getInt("course_id"));
                mapping.setCourse_name(rs.getString("course_name"));
                mapping.setDescription(rs.getString("description"));
                mapping.setDep_id(rs.getInt("dep_id"));
                mapping.setProfessor_id(rs.getInt("professor_id"));
                return mapping;
            };
            mappingList = jdbcCollege.query(getCourseDetails,rowMapper);
        }catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return mappingList;
    }
}
