package net.deniro.land.module.mobile.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author deniro
 *         2015/11/5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-dataset-type.xml",
        "classpath:spring/spring-context-ftp.xml"})
@TransactionConfiguration
@Transactional
public class BackRolePrivilegeDaoTest {

    @Autowired
    private BackRolePrivilegeDao backRolePrivilegeDao;

    @Test
    public void batchInsert() {
        List<Integer> moduleIds = new ArrayList<Integer>();
        moduleIds.add(1001);
        moduleIds.add(1002);

        System.out.println("$$$$$$$$" + backRolePrivilegeDao.batchInsert(moduleIds, 1000));
    }

    @Test
    public void deleteAllByRoleId() {
        System.out.println("$$$$$$$$$" + backRolePrivilegeDao.deleteAllByRoleId(1000));
    }

    @Test
    public void testFindByRoleId() throws Exception {
        System.out.println(backRolePrivilegeDao.findByRoleId(1));
    }
}