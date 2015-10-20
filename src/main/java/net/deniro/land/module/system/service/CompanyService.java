package net.deniro.land.module.system.service;

import net.deniro.land.module.system.dao.CompanyDao;
import net.deniro.land.module.system.entity.Company;
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
     * 查询所有单位（用于下拉选择）
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
