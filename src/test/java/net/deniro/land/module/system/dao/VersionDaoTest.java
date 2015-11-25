package net.deniro.land.module.system.dao;

import net.deniro.land.module.system.entity.VersionQueryParam;
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
public class VersionDaoTest {

    @Autowired
    private VersionDao versionDao;

    @Test
    public void testFindPage() throws Exception {
        VersionQueryParam param = new VersionQueryParam();
        param.setNo("3");

        System.out.println("$$$$$$$$$" + versionDao.findPage(param));
    }
}