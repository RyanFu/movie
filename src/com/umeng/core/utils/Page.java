package com.umeng.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;

/**
 * �����ORMʵ���޹صķ�ҳ��������ѯ�����װ.
 * 
 * @param <T>
 *            Page�м�¼������.
 * 
 */
public class Page<T> implements Iterable<T> {
	/**
	 * �������� 
	 */
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    /**
     * Ĭ��ֵ
     */
	public static final int defaultPageSize=5;
	
	/**
	 * ��ҳ��ѯ����
	 */
    public int pageNo = 1;                //��ǰҳ
    public int pageSize = 0;             //һҳ�ڼ�¼��
    protected String orderBy = null;      //������
    protected String order = null;        //����OR����
    private String requestUrl;            //��ѯ�����url(��������ѯ������
    private Map<String, Object> param = new HashMap<String, Object>(); //��ѯ��������������ҳ��ز�����
    
    /**
     * ��ѯ������� 
     */
    public long totalItems = 0;                   //�ܼ�¼��
    public List<T> result = new ArrayList<T>();    //��ǰҳ����
    public long totalPages = 0;                    //��ҳ��
    

    
    // -- ���캯�� --//
    public Page() {
    }

    public Page(int pageSize) {
        setPageSize(pageSize);
    }

    public Page(int pageNo, int pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    /** Ĭ��Ϊid����Ĺ��췽�� */
    public Page(int pageNo, int pageSize, Map<String, Object> param) {
        setPageNo(pageNo);
        setPageSize(pageSize);
        setParam(param);
        setOrderBy("id");
        setOrder(DESC);
    }

    /**
     * ��������Page
     * 
     * @param pageNo
     *            ��ǰҳ
     * @param pageSize
     *            ÿҳ��ʾ������
     * @param param
     *            ��ѯ����
     * @param orderBy
     *            ������������
     * @param order
     *            �����ǵ���
     */
    public Page(int pageNo, int pageSize, Map<String, Object> param, String orderBy, String order) {
        setPageNo(pageNo);
        setPageSize(pageSize);
        setParam(param);
        setOrderBy(orderBy);
        setOrder(order);
    }

    /**
     * ����pageNo��pageSize���㵱ǰҳ��һ����¼���ܽ�����е�λ��,��Ŵ�0��ʼ. ����Mysql,Hibernate.
     */
    public int getOffset() {
        return ((pageNo - 1) * pageSize);
    }

    /**
     * ���������, ��Ĭ��ֵ.
     */
    public String getOrder() {
        return order;
    }

    /**
     * ��������ֶ�,��Ĭ��ֵ. ��������ֶ�ʱ��','�ָ�.
     */
    public String getOrderBy() {
        return orderBy;
    }

    // -- ��ҳ�������ʺ��� --//
    /**
     * ��õ�ǰҳ��ҳ��,��Ŵ�1��ʼ,Ĭ��Ϊ1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * ���ÿҳ�ļ�¼����, Ĭ��Ϊ-1.
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
     * ���ҳ�ڵļ�¼�б�.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * ����ܼ�¼��, Ĭ��ֵΪ-1.
     */
    public long getTotalItems() {
        return totalItems;
    }

    /**
     * ����pageSize��totalItems������ҳ��, Ĭ��ֵΪ-1.
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
     * �Ƿ������������ֶ�,��Ĭ��ֵ.
     */
    public boolean isOrderBySetted() {
        return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
    }

    /**
     * ʵ��Iterable�ӿ�,����for(Object item : page)����ʹ��
     */
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return result == null ? IteratorUtils.EMPTY_ITERATOR : result.iterator();
    }
    
    /**
     * ��������ʽ��.
     * 
     * @param order
     *            ��ѡֵΪdesc��asc,��������ֶ�ʱ��','�ָ�.
     */
    public void setOrder(final String order) {
        String lowcaseOrder = StringUtils.lowerCase(order);

        // ���order�ַ����ĺϷ�ֵ
        String[] orders = StringUtils.split(lowcaseOrder, ',');
        for (String orderStr : orders) {
            if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
                throw new IllegalArgumentException("������" + orderStr + "���ǺϷ�ֵ");
            }
        }

        this.order = lowcaseOrder;
    }

    /**
     * ���������ֶ�,��������ֶ�ʱ��','�ָ�.
     */
    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * ���õ�ǰҳ��ҳ��,��Ŵ�1��ʼ,����1ʱ�Զ�����Ϊ1.
     */
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;

        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }

    /**
     * ����ÿҳ�ļ�¼����.
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }
    /**
     * ��ȡ��ѯ�����URL��ע��û�а�����ѯ�����������Ҫ������ѯ����������ʹ��getCompleteRequestUrl()��
     * @return
     */
    public String getRequestUrl() {
		return requestUrl;
	}
    /**
     * ��ȡ��ѯ�����URL,�����˲�ѯ����
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
     * ����ҳ�ڵļ�¼�б�.
     */
    public void setResult(final List<T> result) {
        this.result = result;
    }

    /**
     * �����ܼ�¼��.
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