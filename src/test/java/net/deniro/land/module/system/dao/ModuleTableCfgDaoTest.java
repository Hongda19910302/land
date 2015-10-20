package net.deniro.land.module.system.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author deniro
 *         2015/10/20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx.xml"
        , "classpath:spring/spring-context-module.xml"})
@TransactionConfiguration
@Transactional
public class ModuleTableCfgDaoTest {

    @Autowired
    private ModuleTableCfgDao moduleTableCfgDao;

    @Test
    public void testFindByModuleId() throws Exception {
        System.out.println(moduleTableCfgDao.findByModuleId(23));
    }
}