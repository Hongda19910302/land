package net.deniro.land.module.system.dao;

import net.deniro.land.module.system.entity.VersionItemQueryParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author deniro
 *         2015/11/25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-dataset-type.xml",
        "classpath:spring/spring-context-ftp.xml"})
@TransactionConfiguration
@Transactional
public class VersionItemDaoTest {

        @Autowired
        private VersionItemDao versionItemDao;

        @Test
        public void testFindPage() throws Exception {
                VersionItemQueryParam param=new VersionItemQueryParam();
                param.setType("NEW");
                param.setVersionId("1");

                System.out.println("$$$$$$$$$$$"+versionItemDao.findPage(param));
        }
}