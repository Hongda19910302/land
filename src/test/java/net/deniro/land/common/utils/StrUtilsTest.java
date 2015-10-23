package net.deniro.land.common.utils;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author deniro
 *         2015/10/23
 */

public class StrUtilsTest {

        @Test
        public void testReplaceUnderLineAndFirstUpper() throws Exception {
                Assert.assertEquals("CompPageSearchTable",StrUtils
                        .replaceUnderLineAndFirstUpper
                                ("comp_page_search_table",true));

                Assert.assertEquals("compPageSearchTable",StrUtils
                        .replaceUnderLineAndFirstUpper
                                ("comp_page_search_table",false));
        }
}