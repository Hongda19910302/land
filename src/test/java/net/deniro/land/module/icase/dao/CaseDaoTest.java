package net.deniro.land.module.icase.dao;

import net.deniro.land.module.icase.entity.CaseQueryParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static net.deniro.land.module.icase.entity.TCase.CaseStatus.PREPARE;

/**
 * @author deniro
 *         2015/11/10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-dataset-type.xml"})
@TransactionConfiguration
@Transactional
public class CaseDaoTest {

    @Autowired
    private CaseDao caseDao;

    @Test
    public void findVariablesById(){
        System.out.println("CaseDaoTest1:"+caseDao.findVariablesById(129));
    }

    @Test
    public void findById() {
        System.out.println("findById:"+caseDao.findById(129));
    }

    @Test
    public void testComplexFindPage() throws Exception {
        CaseQueryParam caseQueryParam = new CaseQueryParam();
        caseQueryParam.setUserId("37");
        caseQueryParam.setNumPerPage(10);
        caseQueryParam.setPageNum(1);
        caseQueryParam.setMoblieStatus(String.valueOf(PREPARE.mobileCode()));
        caseQueryParam.setDepartmentId("26");
        caseQueryParam.setRegionId(102);
        caseQueryParam.setBeginDate("2014-10-23");
        caseQueryParam.setCreatorName("18905910011");

        System.out.println(caseDao.findPage(caseQueryParam));
    }

    @Test
    public void testFindPage() throws Exception {
        CaseQueryParam caseQueryParam = new CaseQueryParam();
        caseQueryParam.setUserId("37");
        caseQueryParam.setNumPerPage(10);
        caseQueryParam.setPageNum(1);
        caseQueryParam.setMoblieStatus(String.valueOf(PREPARE.mobileCode()));
        caseQueryParam.setDepartmentId("26");

        System.out.println(caseDao.findPage(caseQueryParam));
    }
}