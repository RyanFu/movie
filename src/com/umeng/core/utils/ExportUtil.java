package com.umeng.core.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
/**
 * ����CSV�ļ�������
 * @author hyeang
 *
 */
public class ExportUtil {
    private static enum DataField{
        name("����"){
            public String getValue(Map<String, Object> values){
                String value=(String)values.get(this.name());
                if(value!=null && value.contains(",")){
                    value=value.replaceAll(",", "��");
                }
                return value;
            };
        },
        status("״̬"){
            public String getValue(Map<String, Object> values){
                Object value=values.get(this.name());
                if("false".equals(String.valueOf(value)) || "0".equals(String.valueOf(value))){
                    return "��Ч";
                }else{
                    return "��ͣ";
                }         
            };
        },
        default_price("����"){
            public String getValue(Map<String, Object> values){
                Object value=values.get(this.name());
                if(value!=null){
                    return "��"+nf.format(((Long)value)/1000000.0);
                }else{
                    return "-";
                }        
            };
        },        
        price("����"){
            public String getValue(Map<String, Object> values){
                Object value=values.get(this.name());
                if(value!=null){
                    return "��"+nf.format(((Long)value)/1000000.0);
                }else{
                    return "-";
                }        
            };
        }, 
        daily_budget("Ԥ��"){
            public String getValue(Map<String, Object> values){
                Object value=values.get(this.name());
                if(value!=null){
                    return "��"+nf.format(((Long)value)/1000000.0);
                }else{
                    return "-";
                }        
            };
        },
        ppc("ƽ������۸�"){
            public String getValue(Map<String, Object> values){
                Double value=(Double)values.get(this.name());;
                if(value==null){
                    Long money=(Long)values.get("consume");
                    if(money==null){money=(Long)values.get("income");}
                    Long click=(Long)values.get("click");
                    if(money!=null && click!=null && click>0){
                        value=(money/1000000.0)/click;
                    }
                }
                if(value!=null){
                    return "��"+nf.format(value);
                }else{
                    return "-";
                }        
            };
        },
        cpm("CPM"){
            public String getValue(Map<String, Object> values){
                Double value=(Double)values.get(this.name());;
                if(value==null){
                    Long money=(Long)values.get("consume");
                    if(money==null){money=(Long)values.get("income");}
                    Long click=(Long)values.get("click");
                    if(money!=null && click!=null && click>0){
                        value=(money/1000.0)/click;
                    }
                }
                if(value!=null){
                    return "��"+nf.format(value);
                }else{
                    return "-";
                }        
            };
        },        
        show("չʾ����"),
        click("�������"),      
        ctr("�����"){
            public String getValue(Map<String, Object> values){
                Double value=(Double)values.get(this.name());;
                if(value==null){
                    Long show=(Long)values.get("show");
                    Long click=(Long)values.get("click");
                    if(consume!=null && click!=null && show>0){
                        value=((double)click*100)/show;
                    }
                }
                if(value!=null){
                    return nf.format(value)+"%";
                }else{
                    return "-";
                }        
            };
        },
        consume("����"){
            public String getValue(Map<String, Object> values){
                Object value=values.get(this.name());
                if(value!=null){
                    return "��"+nf.format(((Long)value)/1000000.0);
                }else{
                    return "-";
                }        
            };
        },
        income("����"){
            public String getValue(Map<String, Object> values){
                Object value=values.get(this.name());
                if(value!=null){
                    return "��"+nf.format(((Long)value)/1000000.0);
                }else{
                    return "-";
                }        
            };
        },
        date("����"){
            public String getValue(Map<String, Object> values){
                Object value=values.get(this.name());
                return df.format((Date)value);
            };
        },       
        startDate("��ʼ����"){
            public String getValue(Map<String, Object> values){
                Object value=values.get(this.name());
                return df.format((Date)value);
            };
        },       
        endDate("��������"){
            public String getValue(Map<String, Object> values){
                Object value=values.get(this.name());
                return df.format((Date)value);
            };
        },
        create_time("����"){
            public String getValue(Map<String, Object> values){
                Object value=values.get(this.name());
                return df.format((Date)value);
            };
        },    
        charge_in("����"){
            public String getValue(Map<String, Object> values){
                Object amount=values.get("charge_amount");
                Integer type=(Integer)values.get("type");
                if(amount==null || type==null){
                    return "-";
                }else{
                    if(type.intValue()==100 || type.intValue()==101){
                        return "��"+nf.format(((Long)amount)/1000000.0);
                    }else{
                        return "-";
                    }
                }
            };
        },       
        charge_out("����"){
            public String getValue(Map<String, Object> values){
                Object amount=values.get("charge_amount");
                Integer type=(Integer)values.get("type");
                if(amount==null || type==null){
                    return "-";
                }else{
                    if(type.intValue()==100 || type.intValue()==101){
                        return "-";
                    }else{
                        return "��"+nf.format(((Long)amount)/1000000.0);
                    }
                }      
            };
        },  
        balance("���"){
            public String getValue(Map<String, Object> values){
                Object value=values.get(this.name());
                if(value!=null){
                    return "��"+nf.format(((Long)value)/1000000.0);
                }else{
                    return "-";
                }        
            };
        },        
        type("����"){
            public String getValue(Map<String, Object> values){
                Integer type=(Integer)values.get("type");
                if(type.intValue()==100 || type.intValue()==101){
                    return "����";
                }else{
                    return "����";
                }      
            };
        },
        platform("Platform"){
            public String getValue(Map<String, Object> values){
            	String value=(String)values.get(this.name());
                if(value!=null && value.contains(",")){
                    value=value.replaceAll(",", "��");
                }
                return value;
            };
        },
        priority("���ȼ�"){
            public String getValue(Map<String, Object> values){
                Integer type=(Integer)values.get("type");
                if(type.intValue()==100 || type.intValue()==101){
                    return "����";
                }else{
                    return "����";
                }      
            };
        };  
        private String zhName;
        protected static NumberFormat nf=NumberFormat.getInstance();
        protected static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        static{
            nf.setMaximumFractionDigits(2);
            nf.setGroupingUsed(false);
        }
        DataField(String zhName){
            this.zhName=zhName;
        }
        public String getZhName(){
            return zhName;
        }
        public String getValue(Map<String, Object> values){
            Object value=values.get(this.name());
            value=value==null ? "-" : value;
            return String.valueOf(value);           
        };
    }
	/**
	 * ��������CSV��ʽд��response��OutputStream
	 * @param response
	 * @param data
	 * @param columnNames
	 * @param fileName
	 * @throws IOException
	 */
	public static void writeCsvOutput(HttpServletResponse response, List<Map<String, Object>> data, List<String> columnNames, String fileName) throws IOException{
		// ��Ӧ��������
		response.setContentType("text/csv;charset=gbk");
		// ���������ļ���
//		fileName = new String(fileName.getBytes(), "ISO8859-1");
		response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
		// ���CSV��������
		if (data != null && data.size() > 0) {
		    PrintWriter writer=response.getWriter();
//			out.write(new   byte []{( byte ) 0xEF ,( byte ) 0xBB ,( byte ) 0xBF });  
            for (int i = 0; i < columnNames.size(); i++) {
                String fieldName=columnNames.get(i);
                try {
                    DataField fild=DataField.valueOf(fieldName);
                    writer.print(fild==null ? fieldName : fild.getZhName());
				} catch (Exception e) {
	                writer.print(fieldName);
				}
//                writer.print(fild==null ? new String(fieldName.getBytes("gbk"),"utf-8") : new String(fild.getZhName().getBytes("gbk"),"utf-8"));
                writer.print(",");
            }
            writer.print("\r\n");
			for (Map<String, Object> record : data) {
				for (int i = 0; i < columnNames.size(); i++) {
	                String fieldName=columnNames.get(i);
	                DataField fild=DataField.valueOf(fieldName);
					writer.print(fild.getValue(record));
					if (i != columnNames.size() - 1) {
						writer.print(",");
					} else {
						writer.print("\r\n");
					}
				}
			}
			writer.flush();
			writer.close();
		}
	}
	
	/**
	 * ��������CSV��ʽд��response��OutputStream
	 * @param response
	 * @param data
	 * @param columnNames
	 * @param fileName
	 * @throws IOException
	 */
	public static void writeCsv(HttpServletResponse response, List<String> dateList, Map<String, List<Object>> contentMap, String fileName) throws IOException{
		// ��Ӧ��������
		response.setContentType("text/csv;charset=gbk");
		// ���������ļ���
//		fileName = new String(fileName.getBytes(), "ISO8859-1");
		response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
		// ���CSV��������
		if ((contentMap!= null && contentMap.size() > 0) && (dateList != null && dateList.size() > 0)) {
		    PrintWriter writer=response.getWriter();
//			out.write(new   byte []{( byte ) 0xEF ,( byte ) 0xBB ,( byte ) 0xBF });  
		    writer.print("");
		    writer.print(",");
            for (int i = 0; i < dateList.size(); i++) {
                String fieldName=dateList.get(dateList.size() - 1 - i);
	            writer.print(fieldName);
                writer.print(",");
            }
            writer.print("\r\n");
            for (String key: contentMap.keySet()) {
            	List<Object> contentList = contentMap.get(key);
            	writer.print(key+"");
            	writer.print(",");
            	for (int idx = 0; idx < contentList.size(); idx++){
            		writer.print(contentList.get(contentList.size() - 1 - idx));
            		if (idx != contentList.size() - 1){
            			writer.print(",");
            		} else {
            			writer.print("\r\n");
            		}
            	}
            	
            }
			writer.flush();
			writer.close();
		}
	}
	
	/**
	 * ��ȡ����CSV���ص�ȱʡurl(�������ѯ���ݵ�url��pathĩβ��"_CSV")
	 * @param requestUrl
	 * @return
	 */
	public static String getCsvRequestUrl(String requestUrl){
		int qi=requestUrl.indexOf('?');
		if(qi==-1){
			return requestUrl+"/export";
		}else{
			return requestUrl.substring(0,qi)+"/export"+requestUrl.substring(qi);
		}
	}
}
