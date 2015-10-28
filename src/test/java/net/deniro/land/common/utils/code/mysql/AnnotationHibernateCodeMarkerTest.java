package net.deniro.land.common.utils.code.mysql;

import net.deniro.land.common.utils.StrUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author deniro
 *         2015/10/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-dataset-type.xml"})
@TransactionConfiguration
@Transactional
public class AnnotationHibernateCodeMarkerTest {

    @Autowired
    private AnnotationHibernateCodeMarker codeMarker;

    @Test
    public void testMark() throws Exception {

        List<String> tableNames = new ArrayList<String>();
        tableNames.add("t_region");

        String packagePath = "net.deniro.land.module.system.entity";

        Map<String, String> names = new HashMap<String, String>();
        for (String tableName : tableNames) {
            //表名称转换生成类的名称
            names.put(tableName, StrUtils
                    .replaceUnderLineAndFirstUpper(tableName, true));
        }

        codeMarker.mark(packagePath, names);
    }
}