package net.deniro.land.module.icase.dao;

import net.deniro.land.module.icase.entity.CaseParam;
import net.deniro.land.module.icase.entity.TCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void count() {
        CaseParam param = new CaseParam();
//        param.setCreateBeginDate(new Date(2014,9,30));
        param.setDepartmentId("26");
        param.setRecycleStatus("1");
        System.out.println("$$$$$$$$$$$$$$$" + caseDao.count(param));
    }

    @Test
    public void findPageInstruction() {
        CaseParam param = new CaseParam();
        param.setUserId("1");
        param.setPageNum(1);
        param.setNumPerPage(10);

        List<TCase.CaseStatus> includeStatus = new ArrayList<TCase.CaseStatus>();
        includeStatus.add(TCase.CaseStatus.PREPARE);
        includeStatus.add(TCase.CaseStatus.INSPECT);
        includeStatus.add(TCase.CaseStatus.APPLY);
        includeStatus.add(TCase.CaseStatus.FIRST_OVER);
        param.setIncludeStatus(includeStatus);

        System.out.println("$$$$$$$$$$$$$$" + caseDao.findPage(param));
    }

    @Test
    public void findVSDAll() {
        System.out.println("CaseDaoTest2:" + caseDao.findAllVSD());
    }

    @Test
    public void findVariablesById() {
        System.out.println("CaseDaoTest1:" + caseDao.findVariablesById(129));
    }

    @Test
    public void findById() {
        System.out.println("findById:" + caseDao.findById(129));
    }

    @Test
    public void testComplexFindPage() throws Exception {
        CaseParam caseParam = new CaseParam();
        caseParam.setUserId("37");
        caseParam.setNumPerPage(10);
        caseParam.setPageNum(1);
        caseParam.setMoblieStatus(String.valueOf(PREPARE.mobileCode()));
        caseParam.setDepartmentId("26");
        caseParam.setRegionId(102);
        caseParam.setBeginDate("2014-10-23");
        caseParam.setCreatorName("18905910011");

        System.out.println(caseDao.findPage(caseParam));
    }

    @Test
    public void testFindPage() throws Exception {
        CaseParam caseParam = new CaseParam();
        caseParam.setUserId("37");
        caseParam.setNumPerPage(10);
        caseParam.setPageNum(1);
        caseParam.setMoblieStatus(String.valueOf(PREPARE.mobileCode()));
        caseParam.setDepartmentId("26");

        System.out.println(caseDao.findPage(caseParam));
    }
}