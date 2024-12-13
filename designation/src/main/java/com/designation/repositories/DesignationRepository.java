package com.designation.repositories;

import com.designation.entities.DesignationEntity;
import com.designation.entities.GenericResponse;
import com.designation.service.DesignationService;
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
import java.util.Optional;

import static com.designation.utils.queries.DesignationQueries.addDesignationQuery;
import static com.designation.utils.queries.DesignationQueries.getDesignationById;

@Slf4j
@Repository
public class DesignationRepository  {
    @Autowired
    @Qualifier("mysqlJdbcTemplateCollege")
    JdbcTemplate jdbcCollege;

    public int addDesignationDetails(int designationId,String designationName) {
        KeyHolder holder = new GeneratedKeyHolder();
        int id;
        try {
            jdbcCollege.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(addDesignationQuery, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, designationId);
                    ps.setString(2, designationName);
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

    public Optional<DesignationEntity> getMappingDetailsById(int designId){
        Optional<DesignationEntity> mappingDetails = null;
        try{
            //lamba is used here
            RowMapper<DesignationEntity> rowMapper = (rs, rowNum) -> {
                DesignationEntity mappingDetailsModel = new DesignationEntity();
                mappingDetailsModel.setDesgId(rs.getInt("desg_id"));
                mappingDetailsModel.setDesgName(rs.getString("desg_name"));
                return  mappingDetailsModel;
            };
            mappingDetails = jdbcCollege.query(getDesignationById,new Object[]{designId},rowMapper).stream().findFirst();
        } catch (DataAccessException dataAccessException) {
            log.error("Unable to access data " + dataAccessException);
        } catch (Exception exc) {
            log.error("Unexpected error occurred " + exc);
        }
        return mappingDetails;
    }

}
