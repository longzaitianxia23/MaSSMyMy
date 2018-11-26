package com.ks.top.db.dao;


import java.util.Map;

import org.springframework.stereotype.Repository;



@Repository
public interface IXuDBBace {

	//通过ID查询
	public Map queryVO(Object id);
	
	//插入数据
	public void insert(Object t);
	
	//更新数据，通过传入的对象的ID字段来查找需要更新的对象
	public void updateById(Object t);
	
	//通过ID删除
	public void deleteById(Object id);
}
