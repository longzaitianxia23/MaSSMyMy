package com.ks.index.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ks.index.dao.IndexDao;
import com.ks.index.model.UserVO;

@Service("indexService")
public class IndexService{
	
	@Autowired
	private IndexDao indexDao;
    
    public int saveUser(UserVO vo){
    	return indexDao.insertUser(vo);
    } 
    
    public List<UserVO> queryUser(UserVO vo){
    	List<UserVO> UserList = indexDao.queryUser(vo);
    	return UserList;
    }

    public UserVO queryUserByAccount(UserVO vo){
    	UserVO user = indexDao.queryUserByAccount(vo);
    	return user;
    }
}
