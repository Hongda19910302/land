package net.deniro.land.module.icase.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author deniro
 *         2015/11/11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-dataset-type.xml"})
@TransactionConfiguration
@Transactional
public class CaseServiceTest {

    @Autowired
    private CaseService caseService;

    @Test
    public void testFindVariablesById() throws Exception {
        System.out.println("caseService1:" + caseService.findVariablesById(129));
    }
}