package org.cv.sf.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * 使用jdbc封装的工具包
 */
@Repository
public class LogDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Map findById(Integer id) {
        String sql = "select * from lin_log where id=?";
        final Object[] args = { id };
        final int[] argTypes = { 4 };

        return (Map) jdbcTemplate.queryForList(sql,args,argTypes).get(0);
        /*return jdbcTemplate.queryForMap(sql, (PreparedStatementSetter) new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setObject(1,id,4);
            }
        });*/
    }

    public void insert() {
        String sql = "insert into lin_log (message,user_id) values (?,?)";
        jdbcTemplate.update(sql, (PreparedStatementSetter) new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, "jdbc信息系");
                ps.setLong(2,1);
            }
        });
    }

    public Map findByIdN() {
        String sql = "select * from lin_log where id =:id";
        Map<String,String> params = new HashMap<>();
        params.put("id","1");
        return namedParameterJdbcTemplate.queryForMap(sql,params);
    }

    public void insertByNameTemplate(Map<String,Object> params){
        String sql = "insert into lin_log (" +
                "message,user_id,username,status_code,method,path,permission) values " +
                "(:message,:userId,:username,:statusCode,:method,:path,:permission)";
        namedParameterJdbcTemplate.update(sql,params);
    }

}