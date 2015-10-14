package net.deniro.land.common.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 分页对象
 * <p>
 * 包含当前页数据及分页信息
 *
 * @author deniro
 *         2015/4/24
 */
@Data
public class Page implements Serializable {

    private static final int DEFAULT_PAGE_SIZE = 20;
    /**
     * 每页记录数
     */
    private int pageSize = DEFAULT_PAGE_SIZE;
    /**
     * 当前页第一条数据在List中的位置，从0开始
     */
    private long start;
    /**
     * 当前页中存放的记录
     */
    private List data;
    /**
     * 总记录数
     */
    private long totalCount;

    /**
     * 默认构造方法
     *
     * @param pageSize   本页容量
     * @param start      本页数据在数据库中的起始位置
     * @param data       本页包含的数据
     * @param totalCount 数据库中总记录条数
     */
    public Page(int pageSize, long start, List data, long totalCount) {
        this.pageSize = pageSize;
        this.start = start;
        this.data = data;
        this.totalCount = totalCount;
    }

    /**
     * 构造空页
     */
    public Page() {
        this(DEFAULT_PAGE_SIZE, 0, new ArrayList(), 0);
    }

    /**
     * 取总记录数
     *
     * @return
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 取总页数
     *
     * @return
     */
    public long getTotalPageCount() {
        if (totalCount % pageSize == 0) {
            return totalCount / pageSize;
        } else
            return totalCount / pageSize + 1;
    }

    /**
     * 取每页数据容量
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 取当前页中的记录
     *
     * @return
     */
    public List getData() {
        return data;
    }

    /**
     * 取该页当前页码，页码从1开始
     *
     * @return
     */
    public long getCurrentPageNo() {
        return start / pageSize + 1;
    }

    /**
     * 该页是否有下一页
     *
     * @return
     */
    public boolean isHasNextPage() {
        return getCurrentPageNo() < getTotalPageCount();
    }

    /**
     * 该页是否有上一页
     *
     * @return
     */
    public boolean isHasPreviousPage() {
        return getCurrentPageNo() > 1;
    }

    /**
     * 获取任一页第一条数据在数据集的位置
     *
     * @param pageNo   从1开始的页号
     * @param pageSize 每页记录条数
     * @return 该页第一条数据在数据库中的起始位置
     */
    public static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }

    /**
     * 获取任一页第一条数据在数据集的位置，每页条数使用默认值
     *
     * @param pageNo 从1开始的页号
     * @return 该页第一条数据在数据库中的起始位置
     * @see #getStartOfPage(int, int)
     */
    protected static int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
    }
}
