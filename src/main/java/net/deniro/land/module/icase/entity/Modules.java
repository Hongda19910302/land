package net.deniro.land.module.icase.entity;

import net.deniro.land.common.spring.mvc.ResourcePathExposer;
import net.deniro.land.module.system.dao.MenuDao;
import net.deniro.land.module.system.entity.MenuItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 模块
 *
 * @author deniro
 *         2016/1/13
 */
@Service
public class Modules {

    @Autowired
    private MenuDao menuDao;

    static Logger logger = Logger.getLogger(Modules.class);


    /**
     * 关系；key：父节点ID；value：子节点列表
     */
    private LinkedHashMap<Integer, List<Integer>> relation = new LinkedHashMap<Integer,
            List<Integer>>();

    /**
     * ID与名称映射关系
     */
    private Map<Integer, String> moduleNames = new HashMap<Integer, String>();

    /**
     * 顶级模块ID列表
     */
    private List<Integer> topModuleIds = new ArrayList<Integer>();

    /**
     * 模块列表
     */
    private List<ModuleTreeNode> root = new ArrayList<ModuleTreeNode>();

    public List<ModuleTreeNode> getRoot() {
        return root;
    }

    /**
     * 初始化
     */
    public void init() {

        List<MenuItem> modules = menuDao.findAll();
        if (modules == null || modules.isEmpty()) {
            return;
        }

        //寻找父节点，初始化关系
        for (MenuItem module : modules) {
            if (module.getParentId() == null) {
                relation.put(module.getBackPrivilegeId(), new ArrayList<Integer>());
            }
        }

        //寻找子节点，加入list
        for (MenuItem module : modules) {
            if (module.getParentId() != null) {
                List<Integer> list = relation.get(module.getParentId());
                if (list == null) continue;

                list.add(module.getBackPrivilegeId());
            }
        }

        //ID与名称映射关系、顶级节点
        for (MenuItem module : modules) {
            moduleNames.put(module.getBackPrivilegeId(), module.getName());
            if (module.getParentId() == null) {
                topModuleIds.add(module.getBackPrivilegeId());
            }
        }

        if (!moduleNames.isEmpty()) {
            logger.info("初始化模块：" + moduleNames.size() + "个");
        }


        //加入顶级模块
        Map<Integer, Integer> idIndexMap = new HashMap<Integer, Integer>();
        //key：id；value：所处索引
        int index = 0;
        for (Integer key : relation.keySet()) {
            root.add(createNode(key));
            idIndexMap.put(key, index++);
        }

        //加入子级模块
        for (Integer key : relation.keySet()) {
            List<Integer> childs = relation.get(key);

            ModuleTreeNode rootNode = root.get(idIndexMap.get(key));
            List<ModuleTreeNode> children = new ArrayList<ModuleTreeNode>();
            if (rootNode.getChildren() != null) {
                children = rootNode.getChildren();
            }

            for (Integer childId : childs) {
                children.add(createNode(childId));
            }
            rootNode.setChildren(children);

            logger.info("root:" + root);
        }

    }

    /**
     * 创建节点
     *
     * @param id
     * @return
     */
    private ModuleTreeNode createNode(Integer id) {

        ModuleTreeNode node = new ModuleTreeNode();
        node.setBackPrivilegeId(id);
        node.setName(moduleNames.get(id));

        String ICON_URL_PREFIX = ResourcePathExposer.getResourceRoot()
                + "/dwz/themes/default/images/dialog/";

        if (topModuleIds.contains(id)) {//是父节点
            node.setIsParent("true");
            node.setIconOpen(ICON_URL_PREFIX + "folder.png");
            node.setIconClose(ICON_URL_PREFIX + "folder_go.png");
        } else {
            node.setIsParent("false");
            node.setIcon(ICON_URL_PREFIX + "application_view_detail.png");
        }

        return node;
    }
}
