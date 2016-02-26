package net.deniro.land.module.system.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.system.dao.CompanyDao;
import net.deniro.land.module.system.dao.RegionDao;
import net.deniro.land.module.system.dao.RegionRelationDao;
import net.deniro.land.module.system.entity.*;
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

    @Autowired
    private RegionRelationDao regionRelationDao;

    @Autowired
    private RegionDao regionDao;

    /**
     * 获取用于下拉选择框的企业数据
     *
     * @param currentUser 当前登录账号
     * @return key：企业ID；value：企业名称
     */
    public Map<String, String> findForSelectsCompanys(User currentUser) {
        Map<String, String> datas = new LinkedHashMap<String, String>();
        //如果不是超级管理员，就只加入当前账号所属企业
        if (currentUser != null && !currentUser.isSuperAdmin()) {
            datas.put(String.valueOf(currentUser.getCompany().getCompanyId()),
                    currentUser
                            .getCompany().getCompanyName());
        } else {
            datas = findAllInSelect();
        }
        return datas;
    }

    /**
     * 更新单位
     *
     * @param company
     * @return
     */
    public boolean update(Company company) {
        try {
            companyDao.update(company);

            if (company.getRegionId() != null) {
                regionRelationDao.deleteAllByCompanyId(company.getCompanyId());
                addRegionRelation(company);
            }

            return true;
        } catch (Exception e) {
            logger.error("更新单位", e);
            return false;
        }
    }

    /**
     * 新增区域映射关系
     *
     * @param company
     */
    private void addRegionRelation(Company company) {
        TRegionRelation relation = new TRegionRelation();
        relation.setRegionId(company.getRegionId());
        relation.setRelationId(company.getCompanyId());
        relation.setRelationType(TRegionRelation.RelationType.COMPANY.code());
        regionRelationDao.save(relation);
    }

    /**
     * 依据ID，获取单位对象
     *
     * @param companyId
     * @return
     */
    public Company findById(Integer companyId) {

        try {
            Company company = companyDao.get(companyId);

            //获取关联的区域信息
            List<TRegionRelation> relations = regionRelationDao.findByCompanyId(companyId);
            if (relations != null && !relations.isEmpty()) {
                TRegionRelation relation = relations.get(0);
                company.setRegionId(relation.getRegionId());

                TRegion region = regionDao.get(relation.getRegionId());
                if (region != null) {
                    company.setRegionName(region.getName());
                }
            }

            return company;
        } catch (Exception e) {
            logger.error(" 依据ID，获取单位对象", e);
            return new Company();
        }
    }

    /**
     * 新增单位
     *
     * @param company
     * @return
     */
    public boolean add(Company company) {
        try {
            companyDao.save(company);

            if (company.getRegionId() != null) {//创建关联区域关系
                addRegionRelation(company);
            }

            return true;
        } catch (Exception e) {
            logger.error("新增单位", e);
            return false;
        }
    }

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
