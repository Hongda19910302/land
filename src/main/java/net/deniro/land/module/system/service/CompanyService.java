package net.deniro.land.module.system.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.system.dao.CompanyDao;
import net.deniro.land.module.system.entity.Company;
import net.deniro.land.module.system.entity.CompanyQueryParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 单位
 *
 * @author deniro
 *         2015/10/16
 */
@Service
public class CompanyService {

    static Logger logger = Logger.getLogger(CompanyService.class);

    @Autowired
    private CompanyDao companyDao;

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CompanyQueryParam queryParam) {
        try {
            return companyDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }

    /**
     * 查询所有单位（用于下拉选择）,key：单位ID；value：单位名称
     *
     * @return
     */
    public Map<String, String> findAllInSelect() {
        try {
            List<Company> companies = companyDao.findAll();
            Map<String, String> maps = new LinkedHashMap<String, String>();
            for (Company company : companies) {
                maps.put(String.valueOf(company.getCompanyId()), company.getCompanyName());
            }
            return maps;
        } catch (Exception e) {
            logger.error("查询所有单位", e);
            return new HashMap<String, String>();
        }
    }

    /**
     * 查询所有单位
     *
     * @return
     */
    public List<Company> findAll() {
        try {
            return companyDao.findAll();
        } catch (Exception e) {
            logger.error("查询所有单位", e);
            return new ArrayList<Company>();
        }
    }
}
