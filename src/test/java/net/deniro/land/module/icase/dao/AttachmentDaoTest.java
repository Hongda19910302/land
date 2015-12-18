package net.deniro.land.module.icase.dao;

import net.deniro.land.module.icase.entity.TAttachmentRelation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static net.deniro.land.module.icase.entity.TAttachmentRelation.RelationType.CASE;

/**
 * @author deniro
 *         2015/11/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-dataset-type.xml",
        "classpath:spring/spring-context-ftp.xml"})
@TransactionConfiguration
@Transactional
public class AttachmentDaoTest {

    @Autowired
    private AttachmentDao attachmentDao;

    @Test
    public void deleteAllByFilePath(){
        System.out.println("$$$$$$$$$$"+attachmentDao.deleteAllByFilePath("http://110.84.129.58:8070/res//gt/real/37/img/20140930210901-1.png"));
    }

    @Test
    public void deleteAll() {
        System.out.printf("$$$$$$$$$$$$$$" + attachmentDao.deleteAll(558,
                TAttachmentRelation.RelationType.CASE));
    }

    @Test
    public void testFindByAuditIdAndType() throws Exception {
        System.out.println("$$$$$$$$$$$$$$$" + attachmentDao.findByAuditIdAndType(129,
                CASE));
    }
}