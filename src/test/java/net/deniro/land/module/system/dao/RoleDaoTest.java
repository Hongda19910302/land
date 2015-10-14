package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.Page;
import net.deniro.land.common.entity.QueryParam;
import net.deniro.land.module.system.entity.Role;
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
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx.xml"})
@TransactionConfiguration
@Transactional
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    public void testFindPage() throws Exception {
        QueryParam queryParam = new QueryParam();


        Page page = roleDao.findPage(queryParam);

        List<Role> roles = page.getResult();
        for (Role role : roles) {
            System.out.println(role);
        }
    }
}