package net.deniro.land.common.utils.code.mysql;

import com.sun.tools.javac.util.StringUtils;
import net.deniro.land.common.utils.StrUtils;
import net.deniro.land.common.utils.code.mysql.entity.FieldDefinition;
import net.deniro.land.common.utils.code.mysql.entity.TableDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 表定义
 *
 * @author deniro
 *         2015/10/23
 */
@Repository
public class TableDefinitionDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 查询表定义
     *
     * @param tableName 表名称
     * @return
     */
    public TableDefinition findTable(String tableName) {
        StringBuilder sql = new StringBuilder("SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.`TABLES` ");
        sql.append("WHERE TABLE_NAME=:tableName");

        MapSqlParameterSource mps = new MapSqlParameterSource().addValue("tableName", tableName);

        TableDefinition tableDefinition = namedParameterJdbcTemplate.queryForObject(sql
                .toString(), mps, new
                RowMapper<TableDefinition>() {
                    public TableDefinition mapRow(ResultSet resultSet, int i) throws SQLException {
                        TableDefinition entity = new TableDefinition();
                        entity.setName(resultSet.getString("TABLE_NAME"));
                        entity.setComment(resultSet.getString("TABLE_COMMENT"));
                        return entity;
                    }
                });

        tableDefinition.setFieldDefinitions(findFields(tableName));
        return tableDefinition;
    }

    /**
     * 查询表字段定义
     *
     * @param tableName 表名称
     * @return
     */
    public List<FieldDefinition> findFields(String tableName) {
        StringBuilder sql = new StringBuilder("SELECT");
        sql.append(" COLUMN_NAME,COLUMN_COMMENT,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH," +
                "NUMERIC_PRECISION,COLUMN_KEY,COLUMN_TYPE");
        sql.append(" FROM information_schema.`COLUMNS` WHERE TABLE_NAME =:tableName");

        MapSqlParameterSource mps = new MapSqlParameterSource().addValue("tableName", tableName);

        return namedParameterJdbcTemplate.query(sql.toString(), mps, new RowMapper<FieldDefinition>() {
            public FieldDefinition mapRow(ResultSet resultSet, int i) throws SQLException {
                FieldDefinition entity = new FieldDefinition();

                String columnName = StringUtils.toLowerCase(resultSet.getString
                        ("COLUMN_NAME"));//列名称小写化处理
                entity.setName(columnName);

                //将字段名称转换为字段在类中的名称
                entity.setClassName(StrUtils.replaceUnderLineAndFirstUpper(columnName, false));

                entity.setComment(resultSet.getString("COLUMN_COMMENT"));
                entity.setDataType(resultSet.getString("DATA_TYPE"));
                entity.setCharacterMaximumLength(resultSet.getInt("CHARACTER_MAXIMUM_LENGTH"));
                entity.setNumbericPrecision(resultSet.getInt("NUMERIC_PRECISION"));
                entity.setColumnKey(resultSet.getString("COLUMN_KEY"));
                entity.setColumnType(resultSet.getString("COLUMN_TYPE"));
                return entity;
            }
        });
    }


}
