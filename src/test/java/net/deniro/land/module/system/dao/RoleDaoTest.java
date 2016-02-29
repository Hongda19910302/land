package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.system.entity.Role;
import net.deniro.land.module.system.entity.RoleQueryParam;
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
 *         2015/10/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-dataset-type.xml",
        "classpath:spring/spring-context-ftp.xml"})
@TransactionConfiguration
@Transactional
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    public void findByCompanyId() {
        System.out.println(roleDao.findByCompanyId(21));
    }

    @Test
    public void testFindPage() throws Exception {
        RoleQueryParam queryParam = new RoleQueryParam();


        Page page = roleDao.findPage(queryParam);

        List<Role> roles = page.getData();
        for (Role role : roles) {
            System.out.println(role);
        }
    }
}