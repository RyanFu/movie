package com.umeng.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装.
 * 
 * @param <T>
 *            Page中记录的类型.
 * 
 */
public class Page<T> implements Iterable<T> {
	/**
	 * 公共常量 
	 */
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    /**
     * 默认值
     */
	public static final int defaultPageSize=5;
	
	/**
	 * 分页查询参数
	 */
    public int pageNo = 1;                //当前页
    public int pageSize = 0;             //一页内记录数
    protected String orderBy = null;      //排序列
    protected String order = null;        //正序OR逆序
    private String requestUrl;            //查询请求的url(不包含查询参数）
    private Map<String, Object> param = new HashMap<String, Object>(); //查询参数（不包括分页相关参数）
    
    /**
     * 查询结果数据 
     */
    public long totalItems = 0;                   //总记录数
    public List<T> result = new ArrayList<T>();    //当前页数据
    public long totalPages = 0;                    //总页数
    

    
    // -- 构造函数 --//
    public Page() {
    }

    public Page(int pageSize) {
        setPageSize(pageSize);
    }

    public Page(int pageNo, int pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    /** 默认为id倒序的构造方法 */
    public Page(int pageNo, int pageSize, Map<String, Object> param) {
        setPageNo(pageNo);
        setPageSize(pageSize);
        setParam(param);
        setOrderBy("id");
        setOrder(DESC);
    }

    /**
     * 简便的生成Page
     * 
     * @param pageNo
     *            当前页
     * @param pageSize
     *            每页显示多少条
     * @param param
     *            查询参数
     * @param orderBy
     *            根据哪列排序
     * @param order
     *            正序还是倒序
     */
    public Page(int pageNo, int pageSize, Map<String, Object> param, String orderBy, String order) {
        setPageNo(pageNo);
        setPageSize(pageSize);
        setParam(param);
        setOrderBy(orderBy);
        setOrder(order);
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始. 用于Mysql,Hibernate.
     */
    public int getOffset() {
        return ((pageNo - 1) * pageSize);
    }

    /**
     * 获得排序方向, 无默认值.
     */
    public String getOrder() {
        return order;
    }

    /**
     * 获得排序字段,无默认值. 多个排序字段时用','分隔.
     */
    public String getOrderBy() {
        return orderBy;
    }

    // -- 分页参数访问函数 --//
    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 获得每页的记录数量, 默认为-1.
     */
    public int getPageSize() {
        return pageSize;
    }

    public Map<String, Object> getParam() {
        return param;
    }
    public void addParam(String key, Object value){
    	param.put(key, value);
    }

    /**
     * 获得页内的记录列表.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * 获得总记录数, 默认值为-1.
     */
    public long getTotalItems() {
        return totalItems;
    }

    /**
     * 根据pageSize与totalItems计算总页数, 默认值为-1.
     */
    public long getTotalPages() {
        if (totalItems < 0) {
            return -1;
        }

        long count = totalItems / pageSize;
        if (totalItems % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否已设置排序字段,无默认值.
     */
    public boolean isOrderBySetted() {
        return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
    }

    /**
     * 实现Iterable接口,可以for(Object item : page)遍历使用
     */
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return result == null ? IteratorUtils.EMPTY_ITERATOR : result.iterator();
    }
    
    /**
     * 设置排序方式向.
     * 
     * @param order
     *            可选值为desc或asc,多个排序字段时用','分隔.
     */
    public void setOrder(final String order) {
        String lowcaseOrder = StringUtils.lowerCase(order);

        // 检查order字符串的合法值
        String[] orders = StringUtils.split(lowcaseOrder, ',');
        for (String orderStr : orders) {
            if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
                throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
            }
        }

        this.order = lowcaseOrder;
    }

    /**
     * 设置排序字段,多个排序字段时用','分隔.
     */
    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;

        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }

    /**
     * 设置每页的记录数量.
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }
    /**
     * 获取查询请求的URL（注意没有包含查询参数，如果需要包含查询参数，可以使用getCompleteRequestUrl()）
     * @return
     */
    public String getRequestUrl() {
		return requestUrl;
	}
    /**
     * 获取查询请求的URL,包含了查询参数
     * @return
     */
    public String getCompleteRequestUrl() {
        StringBuilder builder=new StringBuilder();
        builder.append(requestUrl);
        if(param!=null && param.size()>0){
            builder.append("?");
            for(String key : param.keySet()){
                Object value=param.get(key);
                String valueStr=null;
                if(value instanceof String[]){
                    valueStr=StringUtils.join((String[])value, ",");
                }else{
                    valueStr=String.valueOf(value);
                }
                if(valueStr!=null && !valueStr.isEmpty()){
                    builder.append("&");
                    builder.append(key);
                    builder.append("=");
                    builder.append(valueStr);
                }
            }
        }
        return builder.toString();
    }
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
     * 设置页内的记录列表.
     */
    public void setResult(final List<T> result) {
        this.result = result;
    }

    /**
     * 设置总记录数.
     */
    public void setTotalItems(final long totalItems) {
        this.totalItems = totalItems;
        if(pageNo>getTotalPages()){
            setPageNo((int)this.getTotalPages());
        }
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}