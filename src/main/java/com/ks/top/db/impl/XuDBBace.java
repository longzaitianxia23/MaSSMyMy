package com.ks.top.db.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ks.tool.BeanMutualMap;
import com.ks.top.db.DBVO;
import com.ks.top.db.dao.IXuDBBace;
import com.ks.top.xuannotation.Column;
import com.ks.top.xuannotation.Table;


@Service
public class XuDBBace{

	@Autowired
	private IXuDBBace iXuDBBace;

	public Object queryVO(Object t) {
		Map<String,String> parram = new HashMap<String,String>();
		parram = GetQuerySql(t);
		String sql = "select * from " + parram.get("table") + " where 1=1 " + parram.get("field");
		DBVO db = DBVO.getDBVO();
		db.setSql(sql);
		System.out.println(sql);
		Map ret = iXuDBBace.queryVO(db);
		try {
			System.out.println(t.getClass());
			return BeanMutualMap.convertMap(t.getClass(), ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String insert(Object t) {
		Map<String,String> ret = new HashMap<String,String>();
		ret = GetInsertSql(t);
		String sql = "insert into " + ret.get("table") + ret.get("field")
				+ret.get("value");
		DBVO db = DBVO.getDBVO();
		db.setSql(sql);
		iXuDBBace.insert(db);
		return null;
	}

	public void updateById(Object t) {
		Map<String,String> ret = new HashMap<String,String>();
		ret = GetUpdateSql(t);
		String sql = "update " + ret.get("table") + " set " + ret.get("field");
		DBVO db = DBVO.getDBVO();
		db.setSql(sql);
		iXuDBBace.updateById(db);
	}

	public void deleteById(Object t) {
		Map<String,String> ret = new HashMap<String,String>();
		ret = GetDeleteSql(t);
		String sql = "delete from " + ret.get("table") + ret.get("field");
		DBVO db = DBVO.getDBVO();
		db.setSql(sql);
		iXuDBBace.deleteById(db);
	}
	
	private Map<String,String> GetQuerySql(Object obj){
        Map<String,String> map = new HashMap<String,String>();
        if(null == obj) return map;
        Annotation[] classAnnotation = obj.getClass().getAnnotations();
        //Table t = obj.getClass().getAnnotation(Table.class);
        for(Annotation cAnnotation : classAnnotation){    
        	Class annotationType =  cAnnotation.annotationType();  
        	Table t = (Table) cAnnotation;
            map.put("table",t.table());
        } 

        StringBuilder filedSql = new StringBuilder("");

        Field[] fields = obj.getClass().getDeclaredFields();
        
        for(Field field : fields){
            //判断该成员变量上是不是存在Column类型的注解
        	if(!field.isAnnotationPresent(Column.class)){
                continue;
        	}
        	Column c = field.getAnnotation(Column.class);// 获取实例
        	try {
        		if(field.getModifiers() == 2){//private
					field.setAccessible(true);
        		}
				Object fieldObject= field.get(obj);
				if(fieldObject!=null){
		        	//获取元素值
		        	String columnName = c.name();
		        	//如果未指定列名，默认列名使用成员变量名
		        	if("".equals(columnName.trim())){
		        		columnName = field.getName();
		        	}
		        	filedSql.append(" and "+columnName + "='" + fieldObject.toString() + "'");
				}  
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
        map.put("field",filedSql.toString());
        return map;
    }
	
	private Map<String,String> GetDeleteSql(Object obj){
        Map<String,String> map = new HashMap<String,String>();
        if(null == obj) return map;
        Annotation[] classAnnotation = obj.getClass().getAnnotations();
        //Table t = obj.getClass().getAnnotation(Table.class);
        for(Annotation cAnnotation : classAnnotation){    
        	Class annotationType =  cAnnotation.annotationType();  
        	Table t = (Table) cAnnotation;
            map.put("table",t.table());
        } 

        StringBuilder filedSql = new StringBuilder("");

        Field[] fields = obj.getClass().getDeclaredFields();
        String idVal = null;
        for(Field field : fields){
            //判断该成员变量上是不是存在Column类型的注解
        	if(!field.isAnnotationPresent(Column.class)){
                continue;
        	}
        	try {
        		if(field.getModifiers() == 2){//private
					field.setAccessible(true);
        		}
    			Object fieldObject= field.get(obj);
				if(fieldObject!=null){
		        	//获取元素值
	            	if("id".equals(field.getName())){
	            		idVal = fieldObject.toString();
	                    break;
	            	}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
        filedSql.append(" where id='"+idVal+"'");
        map.put("field",filedSql.toString());
        return map;
    }

	private Map<String,String> GetInsertSql(Object obj){
        Map<String,String> map = new HashMap<String,String>();
        if(null == obj) return map;
        Annotation[] classAnnotation = obj.getClass().getAnnotations();
        //Table t = obj.getClass().getAnnotation(Table.class);
        for(Annotation cAnnotation : classAnnotation){    
        	Class annotationType =  cAnnotation.annotationType();  
        	Table t = (Table) cAnnotation;
            map.put("table",t.table());
        } 

        StringBuilder filedSql = new StringBuilder("(");
        StringBuilder valueSql = new StringBuilder("value (");

        Field[] fields = obj.getClass().getDeclaredFields();
        
        for(Field field : fields){
            //判断该成员变量上是不是存在Column类型的注解
        	if(!field.isAnnotationPresent(Column.class)){
                continue;
        	}
        	Column c = field.getAnnotation(Column.class);// 获取实例
        	try {
        		if(field.getModifiers() == 2){//private
					field.setAccessible(true);
        		}
				Object fieldObject= field.get(obj);
				if(fieldObject!=null){
					//System.out.println(field.getName()+"..."+fieldObject.toString());
		        	//获取元素值
		        	String columnName = c.name();
		        	//如果未指定列名，默认列名使用成员变量名
		        	if("".equals(columnName.trim())){
		        		columnName = field.getName();
		        	}
		        	filedSql.append(columnName + ",");
		        	//valueSql.append("#{" + objName + "." + field.getName() + "},");
		        	valueSql.append("'"+fieldObject.toString() + "',");
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
        valueSql.deleteCharAt(valueSql.length() - 1);
        filedSql.deleteCharAt(filedSql.length() - 1);
        valueSql.append(")");
        filedSql.append(")");
        map.put("field",filedSql.toString());
        map.put("value",valueSql.toString());
        return map;
    }
	

	private Map<String,String> GetUpdateSql(Object obj){
        Map<String,String> map = new HashMap<String,String>();
        if(null == obj) return map;
        Annotation[] classAnnotation = obj.getClass().getAnnotations();
        //Table t = obj.getClass().getAnnotation(Table.class);
        for(Annotation cAnnotation : classAnnotation){    
        	Class annotationType =  cAnnotation.annotationType();  
        	Table t = (Table) cAnnotation;
            map.put("table",t.table());
        } 

        StringBuilder filedSql = new StringBuilder("");

        Field[] fields = obj.getClass().getDeclaredFields();
        String idVal = null;
        for(Field field : fields){
            //判断该成员变量上是不是存在Column类型的注解
        	if(!field.isAnnotationPresent(Column.class)){
                continue;
        	}
        	Column c = field.getAnnotation(Column.class);// 获取实例
        	try {
        		if(field.getModifiers() == 2){//private
					field.setAccessible(true);
        		}
				Object fieldObject= field.get(obj);
            	if("id".equals(field.getName())){
            		idVal = fieldObject.toString();
                    continue;
            	}
				if(fieldObject!=null){
		        	//获取元素值
		        	String columnName = c.name();
		        	//如果未指定列名，默认列名使用成员变量名
		        	if("".equals(columnName.trim())){
		        		columnName = field.getName();
		        	}
		        	String value = null;
		        	if("".equals(c.format())){
		        		value=fieldObject.toString();
		        	}else{
		        		SimpleDateFormat df=new SimpleDateFormat(c.format());
		        		value=df.format(fieldObject);
		        	}
		        	filedSql.append(columnName + "='"+value + "',");
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
        filedSql.deleteCharAt(filedSql.length() - 1);
        filedSql.append(" where id = '" + idVal + "'");
        map.put("field",filedSql.toString());
        return map;
    }
}
