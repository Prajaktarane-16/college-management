package com.department.repositories;

import com.department.entities.DepartmentEntity;
import com.department.utils.queries.DepartmentQueries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import org.springframework.beans.factory.annotation.Qualifier;

import static com.department.utils.queries.DepartmentQueries.addDepartmentQuery;

@Slf4j
@Repository
public class DepartmentRepository {

    @Autowired
    @Qualifier("mysqlJdbcTemplateCollege")
    JdbcTemplate jdbcCollege;

    public int addDepartmentDetails(int depId,String depName,String description,int hodId) {
        KeyHolder holder = new GeneratedKeyHolder();
        int id;
        try {
            jdbcCollege.update(new PreparedStatementCreator() {

                // insert into department (dep_id , dep_name,description,hod_id) values (?,?,?,?)
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(addDepartmentQuery, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, depId);
                    ps.setString(2, depName);
                    ps.setString(3, description);
                    ps.setInt(4, hodId);

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

    public Optional<DepartmentEntity> getMappingDetailsById(int depid){
        Optional<DepartmentEntity> mappingDetails = null;
        try{
            //lamba is used here
            RowMapper<DepartmentEntity> rowMapper = (rs, rowNum) -> {
                DepartmentEntity mappingDetailsModel = new DepartmentEntity();
                mappingDetailsModel.setDepId(rs.getInt("dep_id"));
                mappingDetailsModel.setDepName(rs.getString("dep_name"));
                mappingDetailsModel.setDescription(rs.getString("description"));
                mappingDetailsModel.setHodId(rs.getInt("hod_id"));
                return  mappingDetailsModel;
            };
            mappingDetails = jdbcCollege.query(DepartmentQueries.getSpecificDepData,new Object[]{depid},rowMapper).stream().findFirst();
        } catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return mappingDetails;
    }

    public List<DepartmentEntity> getDepartmentDetails(){
        List<DepartmentEntity> mappingList = null;
        try{
            RowMapper<DepartmentEntity> rowMapper = (rs, rowNum) -> {
                DepartmentEntity mapping = new DepartmentEntity();
                mapping.setDepId(rs.getInt("dep_id"));
                mapping.setDepName(rs.getString("dep_name"));
                mapping.setDescription(rs.getString("description"));
                mapping.setHodId(rs.getInt("hod_id"));
                return mapping;
            };
            mappingList = jdbcCollege.query(DepartmentQueries.getDepartmentData,rowMapper);
        }catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return mappingList;
    }
}
