package net.deniro.land.module.system.dao;

import net.deniro.land.module.system.entity.MenuItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author deniro
 *         2015/10/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml","classpath:spring/spring-context-tx.xml"})
@TransactionConfiguration
@Transactional
public class MenuDaoTest {

    @Autowired
    private MenuDao menuDao;



    @Test
    public void testFindChildrenByRoleId() throws Exception {
        List<MenuItem> datas = menuDao.findChildrenByRoleId(1);
        System.out.println("datas:" + datas);
    }

    @Test
    public void testFindTopByRoleId() throws Exception {
        List<MenuItem> datas = menuDao.findTopByRoleId(1);
        System.out.println("datas:" + datas);
    }
}