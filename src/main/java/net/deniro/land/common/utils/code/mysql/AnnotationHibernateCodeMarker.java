package net.deniro.land.common.utils.code.mysql;


import freemarker.template.Configuration;
import freemarker.template.Template;
import net.deniro.land.common.utils.code.mysql.entity.TableDefinition;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 注解式hibernate代码生成器
 *
 * @author deniro
 *         2015/10/23
 */
@Service
public class AnnotationHibernateCodeMarker {

    static Logger logger = Logger.getLogger(AnnotationHibernateCodeMarker.class);

    /**
     * 模板路径
     */
    public static final String TEMPLATE_PATH
            = "W:/ideaProjects/deniro/deniro-land/src/main/java/net/deniro/land/common/utils/code/mysql/templates/";

    /**
     * 代码生成路径
     */
    public static final String CODE_PATH = "w:/code-marker/";

    public static final String ENCODING = "UTF-8";

    /**
     * 类文件后缀
     */
    public static final String CLASS_SUFFIX = ".java";

    @Autowired
    private TableDefinitionDao tableDefinitionDao;

    private Configuration cfg;

    public AnnotationHibernateCodeMarker() {

        /**
         * 创建配置实例
         */
        try {
            cfg = new Configuration();
            cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
            cfg.setDefaultEncoding(ENCODING);
        } catch (IOException e) {
            logger.error("创建配置实例", e);
        }
    }

    /**
     * 生成
     *
     * @param packagePath 包路径
     * @param names       key:表名；value：类名
     */
    public void mark(String packagePath, Map<String, String> names) {
        try {
            for (Map.Entry<String, String> name : names.entrySet()) {
                //数据模型
                Map<String, Object> root = new HashMap<String, Object>();
                TableDefinition tableDefinition = tableDefinitionDao.findTable(name
                        .getKey());
                root.put("tableDefinition", tableDefinition);
                root.put("packagePath", packagePath);
                root.put("markDate", new Date());
                root.put("tableName", name.getKey());
                root.put("className", name.getValue());

                //获取模板
                Template template = cfg.getTemplate("AnnotationHibernate.ftl");

                //生成代码
                FileOutputStream fos = new FileOutputStream(new File(CODE_PATH + name
                        .getValue()+CLASS_SUFFIX));
                template.process(root, new OutputStreamWriter(fos, ENCODING));

                //关闭文件
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            logger.error("生成", e);
        }
    }
}
