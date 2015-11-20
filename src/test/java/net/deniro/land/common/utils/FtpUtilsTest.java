package net.deniro.land.common.utils;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author deniro
 *         2015/11/20
 */
public class FtpUtilsTest {

        private static FtpUtils ftpUtils=null;

        @BeforeClass
        public static void init(){
                ftpUtils=new FtpUtils();
                ftpUtils.setAccount("gzty");
                ftpUtils.setPassword("gzty");
                ftpUtils.setIp("191.168.19.210");
                ftpUtils.setPort(21);
                ftpUtils.setPrefix("ftp://");


                ftpUtils.init();
        }

        @AfterClass
        public static void close(){
                ftpUtils.close();
        }

        @Test
        public void testIsExist() throws Exception {
                Assert.isTrue(ftpUtils.isExist("land"));
                Assert.isTrue(!ftpUtils.isExist("land1"));
                Assert.isTrue(ftpUtils.isExist("/land/temp"));

        }
}