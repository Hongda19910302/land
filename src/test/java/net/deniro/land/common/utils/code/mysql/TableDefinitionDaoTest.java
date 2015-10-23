package net.deniro.land.common.utils.code.mysql;

import net.deniro.land.common.utils.code.mysql.entity.FieldDefinition;
import net.deniro.land.common.utils.code.mysql.entity.TableDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author deniro
 *         2015/10/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-module.xml"})
@TransactionConfiguration
@Transactional
public class TableDefinitionDaoTest {

    @Autowired
    private TableDefinitionDao tableDefinitionDao;

    @Test
    public void testFindTable() throws Exception {
        System.out.println(tableDefinitionDao.findTable("comp_page_search"));
    }

    @Test
    public void testFindFields() throws Exception {
        List<FieldDefinition> fieldDefinitions = tableDefinitionDao.findFields
                ("comp_page_search");
        System.out.println(fieldDefinitions);
    }
}