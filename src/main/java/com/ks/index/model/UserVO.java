package com.ks.index.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.ks.shiro.model.Role;
import com.ks.top.xuannotation.Column;
import com.ks.top.xuannotation.Table;

@Table(table="bt_user")
public class UserVO implements Serializable{
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Column(name="id")
	private String id;
	
	/**
	 * 登陆账号（用户名）
	 */
	@Column(name="user_Account_Number")
	private String userAccountNumber;
	
	/**
	 * 密码
	 */
	@Column(name="user_Password")
	private String userPassword;
	
	/**
	 * 性别
	 */
	@Column(name="sex")
	private String sex;
	
	/**
	 * 出生日期
	 */
	@Column(name="user_Birth")
	private Timestamp userBirth;
	
	/**
	 * 手机号码
	 */
	@Column(name="mobilePhone_Num")
	private String mobilePhoneNum;
	
	/**
	 * 邮箱
	 */
	@Column(name="email")
	private String email;
	
	/**
	 * 创建时间
	 */
	@Column(name="create_Date")
	private Timestamp createDate;
	
	/**
	 * 最后修改信息时间
	 */
	@Column(name="last_Update_Date")
	private Timestamp lastUpdateDate;
	
	/**
	 * 用户角色
	 */
	private List<Role> roleList;
	
	/**
	 * 用户状态。1:活跃.2:注销.3:过期
	 */
	private String state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserAccountNumber() {
		return userAccountNumber;
	}

	public void setUserAccountNumber(String userAccountNumber) {
		this.userAccountNumber = userAccountNumber;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Timestamp getUserBirth() {
		return userBirth;
	}

	public void setUserBirth(Timestamp userBirth) {
		this.userBirth = userBirth;
	}

	public String getMobilePhoneNum() {
		return mobilePhoneNum;
	}

	public void setMobilePhoneNum(String mobilePhoneNum) {
		this.mobilePhoneNum = mobilePhoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	
}
