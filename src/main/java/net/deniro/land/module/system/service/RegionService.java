package net.deniro.land.module.system.service;

import net.deniro.land.common.service.Constants;
import net.deniro.land.common.spring.mvc.ResourcePathExposer;
import net.deniro.land.module.system.dao.RegionDao;
import net.deniro.land.module.system.entity.TRegion;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 行政区域
 *
 * @author deniro
 *         2015/11/6
 */
@Service
public class RegionService {

    static Logger logger = Logger.getLogger(RegionService.class);

    @Autowired
    private RegionDao regionDao;

    /**
     * 依据单位ID，获取行政区域（树型）
     *
     * @param companyId
     * @return
     */
    public List<TRegion> findByCompanyIdForTree(Integer companyId) {
        try {
            List<TRegion> regions = regionDao.findByCompanyId(companyId);
            setAttribute(regions);
            return regions;
        } catch (Exception e) {
            logger.error(" 依据单位ID，获取行政区域（树型）", e);
            return new ArrayList<TRegion>();
        }
    }

    /**
     * 依据区域ID，获取子区域列表（树型）
     *
     * @param regionId
     * @return
     */
    public List<TRegion> findChildrenByRegionIdForTree(Integer regionId) {
        try {
            List<TRegion> regions = regionDao.findChildrenByRegionId(regionId);
            setAttribute(regions);
            return regions;
        } catch (Exception e) {
            logger.error(" 依据区域ID，获取子区域列表（树型）", e);
            return new ArrayList<TRegion>();
        }
    }

    /**
     * 设置属性（是否为父区域、个性化图标）
     *
     * @param data
     */
    private void setAttribute(List<TRegion> data) {
        String ICON_URL_PREFIX = ResourcePathExposer.getResourceRoot()
                + Constants.TREE_ICON_PATH_PREFIX;

        List<Integer> parentIds = regionDao.findParentIds();
        for (TRegion entity : data) {
            if (parentIds.contains(entity.getRegionId())) {//是父节点
                entity.setIsParent("true");
                entity.setIconOpen(ICON_URL_PREFIX + "house.png");
                entity.setIconClose(ICON_URL_PREFIX + "house_go.png");
            } else {
                entity.setIsParent("false");
                entity.setIcon(ICON_URL_PREFIX + "group.png");
            }
            entity.setParentRegion(null);
        }
    }

    /**
     * 依据区域ID，获取子区域列表
     *
     * @param regionId
     * @return
     */
    public List<TRegion> findChildrenByRegionId(Integer regionId) {
        try {
            return regionDao.findChildrenByRegionId(regionId);
        } catch (Exception e) {
            logger.error(" 依据区域ID，获取子区域列表", e);
            return new ArrayList<TRegion>();
        }
    }

    /**
     * 依据单位ID，获取行政区域
     *
     * @param companyId
     * @return
     */
    public List<TRegion> findByCompanyId(Integer companyId) {
        try {
            return regionDao.findByCompanyId(companyId);
        } catch (Exception e) {
            logger.error("依据单位ID，获取行政区域", e);
            return new ArrayList<TRegion>();
        }
    }
}
