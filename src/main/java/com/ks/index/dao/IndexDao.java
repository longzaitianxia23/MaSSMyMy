package com.ks.index.dao;

import java.util.List;




import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ks.index.model.UserVO;

@Transactional
@Repository
public interface IndexDao {

	public int insertUser(UserVO vo);
	
	public List<UserVO> queryUser(UserVO vo);

	public UserVO queryUserByAccount(UserVO vo);
}
