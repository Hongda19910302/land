package net.deniro.land.common.utils.code.mysql;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author deniro
 *         2015/10/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-module.xml"})
@TransactionConfiguration
@Transactional
public class AnnotationHibernateCodeMarkerTest {

    @Autowired
    private AnnotationHibernateCodeMarker codeMarker;

    @Test
    public void testMark() throws Exception {
        Map<String, String> names = new HashMap<String, String>();
        names.put("comp_page_search", "CompPageSearch");
        names.put("comp_page_search_form", "comp_page_search_form");
        names.put("comp_page_search_table", "comp_page_search_table");

        String packagePath="net.deniro.land.module.system.entity";

        codeMarker.mark(packagePath,names);
    }
}