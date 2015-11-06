package net.deniro.land.module.system.dao;

import net.deniro.land.module.system.entity.TRegionRelation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author deniro
 *         2015/10/28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-dataset-type.xml"})
@TransactionConfiguration
@Transactional
public class RegionDaoTest {

    @Autowired
    private RegionDao regionDao;

    @Test
    public void findChildrenByRegionId() {
        System.out.println(regionDao.findChildrenByRegionId(1));
    }

    @Test
    public void findByCompanyId() {
        System.out.println(regionDao.findByCompanyId(1));
    }

    @Test
    public void testFindById() throws Exception {
        System.out.println("COMPANY:" + regionDao.findById(37, TRegionRelation.RelationType
                .COMPANY));

        System.out.println("DEPARTMENT:" + regionDao.findById(1, TRegionRelation.RelationType
                .DEPARTMENT));
    }
}