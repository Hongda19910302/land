package net.deniro.land.module.icase.dao;

import net.deniro.land.module.icase.entity.InstructionQueryParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author deniro
 *         2015/11/16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-dataset-type.xml"})
@TransactionConfiguration
@Transactional
public class InstructionDaoTest {

    @Autowired
    private InstructionDao instructionDao;

    @Test
    public void count(){
        InstructionQueryParam param = new InstructionQueryParam();
        param.setCaseId("336");
        param.setStatus("0");

        System.out.println("$$$$$$$$$$$$$$$" + instructionDao.count(param));
    }

    @Test
    public void testFindPage() throws Exception {
        InstructionQueryParam param = new InstructionQueryParam();
        param.setCaseId("336");
        param.setStatus("0");
        param.setPageNum(1);
        param.setNumPerPage(10);

        System.out.println("$$$$$$$$$$$$$$$" + instructionDao.findPage(param));
    }
}