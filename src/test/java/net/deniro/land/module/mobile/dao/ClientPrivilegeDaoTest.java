package net.deniro.land.module.mobile.dao;

import net.deniro.land.module.mobile.dao.ClientPrivilegeDao;
import net.deniro.land.module.mobile.entity.TClientPrivilege;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static net.deniro.land.module.mobile.entity.TClientPrivilege.*;
import static net.deniro.land.module.mobile.entity.TClientPrivilege.PrivilegeType.*;

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
    public void updateAll() {
        System.out.println(clientPrivilegeDao.updateAll(YES));
    }

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