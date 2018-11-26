package com.ks.shiro.dao;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;

/**
 * 进行详细的session存储操作
 * @author xushoushan
 *
 */
public interface ShiroSessionRepository {
	void saveSession(Session session);  
	  
    void deleteSession(Serializable sessionId);  
  
    Session getSession(Serializable sessionId);  
  
    Collection<Session> getAllSessions();
}
