package com.employee.repositories;

import com.employee.entities.EmployeeEntity;
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

import static com.employee.utils.queries.EmployeeQueries.*;

@Slf4j
@Repository
public class EmployeeRepository {

    @Autowired
    @Qualifier("mysqlJdbcTemplateCollege")
    JdbcTemplate jdbcCollege;



    public int addEmployeeDetails(int empId,String empName,String empMobile,int depId) {
        KeyHolder holder = new GeneratedKeyHolder();
        int id;
        try {
            jdbcCollege.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(addEmpQuery, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, empId);
                    ps.setString(2, empName);
                    ps.setString(3, empMobile);
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

    public Optional<EmployeeEntity> getMappingDetailsById(int empId){
        Optional<EmployeeEntity> mappingDetails = null;
        try{
            //lamba is used here
            RowMapper<EmployeeEntity> rowMapper = (rs, rowNum) -> {
                EmployeeEntity mappingDetailsModel = new EmployeeEntity();
                mappingDetailsModel.setEmpId(rs.getInt("emp_id"));
                mappingDetailsModel.setEmpName(rs.getString("emp_name"));
                mappingDetailsModel.setEmpMobile(rs.getString("emp_mobile"));
                mappingDetailsModel.setDesginationId(rs.getInt("desgination_id"));

                return  mappingDetailsModel;
            };
            mappingDetails = jdbcCollege.query(getEmployeeById,new Object[]{empId},rowMapper).stream().findFirst();
        } catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return mappingDetails;
    }

    public List<EmployeeEntity> getEmployeeDetails(){
        List<EmployeeEntity> mappingList = null;
        try{
            RowMapper<EmployeeEntity> rowMapper = (rs, rowNum) -> {
                EmployeeEntity mapping = new EmployeeEntity();
                mapping.setEmpId(rs.getInt("emp_id"));
                mapping.setEmpName(rs.getString("emp_name"));
                mapping.setEmpMobile(rs.getString("emp_mobile"));
                mapping.setDesginationId(rs.getInt("desgination_id"));

                return mapping;
            };
            mappingList = jdbcCollege.query(getEmployeeDetails,rowMapper);
        }catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return mappingList;
    }
}
