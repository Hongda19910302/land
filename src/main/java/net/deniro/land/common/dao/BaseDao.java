package net.deniro.land.common.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.util.Assert;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DAO基类，其他DAO可以直接继承这个DAO，不但可以复用共用的方法，还可以获得泛型的好处
 * <p/>
 * 基于Hibernate模板类的 DAO
 *
 * @author deniro
 *         2015/4/24
 */
public class BaseDao<T> {

    public static final Logger log = Logger.getLogger(BaseDao.class);

    /**
     * 注入Hibernate模板类
     * <p/>
     * 子类只要打上@Repository的注解就自然拥有了HibernateTemplate成员变量
     */
    @Autowired
    private HibernateTemplate hibernateTemplate;

    /**
     * DAO的泛型类，即子类所指定的T所对应的类型
     */
    private Class entityClass;

    /**
     * 通过反射方式获取子类Dao对应的泛型实体类
     */
    public BaseDao() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    /**
     * 通过id加载实体
     *
     * @param id
     * @return 返回相应的持久化实体
     */
    public T load(Serializable id) {
        return (T) getHibernateTemplate().load(entityClass, id);
    }

    /**
     * 通过id获取实体
     *
     * @param id
     * @return 返回相应的持久化实体
     */
    public T get(Serializable id) {
        return (T) getHibernateTemplate().get(entityClass, id);
    }

    /**
     * 获取所有实体
     *
     * @return
     */
    public List<T> loadAll() {
        return getHibernateTemplate().loadAll(entityClass);
    }

    /**
     * 保存实体
     *
     * @param entity
     */
    public void save(T entity) {
        getHibernateTemplate().save(entity);
    }

    /**
     * 删除实体
     *
     * @param entity
     */
    public void remove(T entity) {
        getHibernateTemplate().delete(entity);
    }

    /**
     * 更改实体
     *
     * @param entity
     */
    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    /**
     * 执行HQL查询
     *
     * @param hql
     * @return 查询结果
     */
    public List find(String hql) {
        return getHibernateTemplate().find(hql);
    }

    /**
     * 执行带参的HQL查询
     *
     * @param hql
     * @param params
     * @return
     */
    public List find(String hql, Object... params) {
        return getHibernateTemplate().find(hql, params);
    }

    /**
     * 按参数名称占位符映射表查询
     *
     *
     * @param queryString 查询字符串
     * @param params 参数名称占位符映射表
     * @return
     * @throws DataAccessException
     */
    public List findByNamedParam(final String queryString, final LinkedHashMap<String, Object>
            params)
            throws
            DataAccessException {

        if (params == null || params.isEmpty()) {
            throw new IllegalArgumentException("参数不能为null也不能为空");
        }

        //map转换为数组
        int size = params.size();
        String[] paramNames = new String[size];
        Object[] values = new Object[size];
        int i = 0;
        for (String key : params.keySet()) {
            paramNames[i] = key;
            values[i] = params.get(key);
            i++;
        }
        return getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
    }

    /**
     * 对延迟加载的实体执行初始化（强制加载）
     *
     * @param entity
     */
    public void initialize(Object entity) {
        getHibernateTemplate().initialize(entity);
    }

    /**
     * 创建Query对象。
     * 对于需要first,max,fetchsize,cache,cacheRegion等需要设置的函数，可以在返回Query后自行设置
     * 可以连续设置，如：
     * <pre>
     * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
     * </pre>
     * <p/>
     * 调用方式：
     * <pre>
     *     dao.createQuery(hql);
     *     dao.createQuery(hql,arg0);
     *     dao.createQuery(hql,arg0,arg1);
     *     dao.createQuery(hql,new Object[arg0,arg1,arg2])
     * </pre>
     *
     * @param hql
     * @param values 可变参数
     * @return
     */
    public Query createQuery(String hql, Object... values) {
        Assert.hasText(hql);
        Query query = getSession().createQuery(hql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query;
    }

    /**
     * 分页查询
     *
     * @param hql
     * @param pageNo   页号，从1开始
     * @param pageSize 每页记录条数
     * @param values   查询参数
     * @return 分页对象
     */
    public Page pagedQuery(String hql, int pageNo, int pageSize, Object... values) {
        Assert.hasText(hql);
        Assert.isTrue(pageNo >= 1, "页号必须从1开始");

        String countQueryString = "select count (*) " + removeSelect(removeOrders(hql));
        List countList = getHibernateTemplate().find(countQueryString, values);
        long totalCount = (Long) countList.get(0);

        if (totalCount < 1) {
            return new Page();
        }

        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        Query query = createQuery(hql, values);
        List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
        return new Page(pageSize, startIndex, list, totalCount);
    }

    /**
     * 去除hql的select子句，未考虑union情况
     *
     * @param hql
     * @return
     * @see #pagedQuery(String, int, int, Object...)
     */
    private static String removeSelect(String hql) {
        Assert.hasText(hql);
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, "hql：" + hql + " 语句必须包含【from】关键字");
        return hql.substring(beginPos);
    }

    /**
     * 去除hql中的Orderby子句
     *
     * @param hql
     * @return
     * @see #pagedQuery(String, int, int, Object...)
     */
    private static String removeOrders(String hql) {
        Assert.hasText(hql);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 获取 Hibernate模板类
     *
     * @return
     */
    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public Session getSession() {
        return SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
    }
}
