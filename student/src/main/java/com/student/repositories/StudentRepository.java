package com.student.repositories;

import com.student.entities.StudentEntity;
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

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static com.student.utils.queries.StudentQueries.*;

@Repository
@Slf4j
public class StudentRepository {

    @Autowired
    @Qualifier("mysqlJdbcTemplateStudent")
    JdbcTemplate jdbcStudent;

    public int addStudentDetails(int studId,String studName,String gender,int depId) {
        KeyHolder holder = new GeneratedKeyHolder();
        int id;
        try {
            jdbcStudent.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(addStudentQuery, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, studId);
                    ps.setString(2, studName);
                    ps.setString(3, gender);
                    ps.setInt(4, depId);
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

    public List<StudentEntity> getStudentDetails(){
        List<StudentEntity> mappingList = null;
        try{
            RowMapper<StudentEntity> rowMapper = (rs, rowNum) -> {
                StudentEntity mapping = new StudentEntity();
                mapping.setStudId(rs.getInt("stud_id"));
                mapping.setStudName(rs.getString("stud_name"));
                mapping.setGender(rs.getString("gender"));
                mapping.setDepId(rs.getInt("dep_id"));

                return mapping;
            };
            mappingList = jdbcStudent.query(getStudentDetails,rowMapper);
        }catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return mappingList;
    }

    public Optional<StudentEntity> getMappingDetailsById(int studId){
        Optional<StudentEntity> mappingDetails = null;
        try{
            //lamba is used here
            RowMapper<StudentEntity> rowMapper = (rs, rowNum) -> {
                StudentEntity mappingDetailsModel = new StudentEntity();
                mappingDetailsModel.setStudId(rs.getInt("stud_id"));
                mappingDetailsModel.setStudName(rs.getString("stud_name"));
                mappingDetailsModel.setGender(rs.getString("gender"));
                mappingDetailsModel.setDepId(rs.getInt("dep_id"));

                return  mappingDetailsModel;
            };
            mappingDetails = jdbcStudent.query(getStudentById,new Object[]{studId},rowMapper).stream().findFirst();
        } catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return mappingDetails;
    }


    public List<StudentEntity> getStudentDetailsByDepId(int depId){
        List<StudentEntity> mappingList = null;
        try{
            RowMapper<StudentEntity> rowMapper = (rs, rowNum) -> {
                StudentEntity mapping = new StudentEntity();
                mapping.setStudId(rs.getInt("stud_id"));
                mapping.setStudName(rs.getString("stud_name"));
                mapping.setGender(rs.getString("gender"));
                mapping.setDepId(rs.getInt("dep_id"));

                return mapping;
            };
            mappingList = jdbcStudent.query(getStudDeptData,rowMapper,depId);
        }catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return mappingList;
    }

}
