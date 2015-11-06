package net.deniro.land.module.system.service;

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
