package net.deniro.land.mobile.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author deniro
 *         2015/11/5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-dataset-type.xml"})
@TransactionConfiguration
@Transactional
public class ClientPrivilegeDaoTest {

    @Autowired
    private ClientPrivilegeDao clientPrivilegeDao;

    @Test
    public void testFindByName() throws Exception {
        System.out.println(clientPrivilegeDao.findByName("新建案件 privilegeName=新建案件, privilegeOrder=0, isExist=1)]\n" +
                "[2015-11-05 16:40:47.996] [DEBUG]: [org.springframework.jdbc.datasource.DataSourceUtils.doRelea"));
    }

    @Test
    public void testFindAll() throws Exception {
        System.out.println(clientPrivilegeDao.findAll());
    }
}