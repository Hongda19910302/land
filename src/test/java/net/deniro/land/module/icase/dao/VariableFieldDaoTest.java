package net.deniro.land.module.icase.dao;

import net.deniro.land.module.icase.entity.TVariableField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author deniro
 *         2015/11/6
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-dataset-type.xml",
        "classpath:spring/spring-context-ftp.xml"})
@TransactionConfiguration
@Transactional
public class VariableFieldDaoTest {

    @Autowired
    private VariableFieldDao variableFieldDao;

    @Test
    public void findVariableSelects(){
        System.out.println("$$$$$$$$$$$$"+variableFieldDao.findVariableSelects());
    }

    @Test
    public void find() {
        System.out.println("$$$$$$$$$$$$$$$"+variableFieldDao.find(1,"surveyResult"));
    }

    @Test
    public void testFindByCompanyId() throws Exception {
        System.out.println(variableFieldDao.findByCompanyId(1));
    }
}