package com.ks.top.db;

public class DBVO {
	
	private static DBVO dbVO;
	
	private String id;
	
	private String sql;

	public static DBVO getDBVO(){
		if(dbVO == null){
			return new DBVO();
		}else{
			return dbVO;
		}
	}
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
