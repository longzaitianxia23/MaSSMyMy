package com.ks.index.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



import com.ks.index.model.UserVO;
import com.ks.index.service.IndexService;


@Controller
@RequestMapping("/index")
public class IndexController{
	
	private static final Logger LOG = Logger.getLogger(IndexController.class);  

	@Autowired
    private IndexService indexService; 
	
	/**
	 * 登陆账号
	 * @param session
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/userLogin")
	@ResponseBody
	public Map<String,Object> HandleIndexSome(HttpSession session,HttpServletRequest req,HttpServletResponse res){
		Map<String,Object> ret = new HashMap<String,Object>();
//		if("".equals(req.getParameter("username"))){
//			ret.put("notUser", "notUser");
//			return ret;
//		}
		//用户名跟密码组成Token
		UsernamePasswordToken token = new UsernamePasswordToken(req.getParameter("username"),
				req.getParameter("pasword"));
		Subject subject = SecurityUtils.getSubject();
		token.setRememberMe(true);
		//验证角色和权限  
		try {
			subject.login(token);
			ret.put("success", "认证成功");
		} catch (AuthenticationException e) {
			e.printStackTrace();
			ret.put("error", "用户名或密码错误");
		}
		String userName = req.getParameter("username");
        session.setAttribute("userName", userName);
        session.setAttribute("userId", "1565256");
		return ret;
	}

	/**
	 * 注册账号
	 * @param vo:页面填写的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/registerUser")
	public int RegisterUser(@RequestBody UserVO vo){
		if("".equals(vo.getId()) || vo.getId() == null){
			//新建用户
			vo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			vo.setCreateDate(new Timestamp(System.currentTimeMillis()));
		}
		vo.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
		int i = indexService.saveUser(vo);
		return i;
	}
	
	
	@RequestMapping(value="/queryUserSession")
	@ResponseBody
	public Map<String,Object> QueryUserSession(HttpServletRequest request, HttpServletResponse response,
			Object arg2){
		Map<String,Object> ret = new HashMap<String,Object>();
		HttpSession session = request.getSession();
        String username = (String)session.getAttribute("userName");
        if(username != null){
        	ret.put("userName",username);
            return ret;
        }else{
        	ret.put("userName","off");
            return ret;
        }
        //不符合条件的，跳转到登录界面
		//request.getRequestDispatcher("com/Login.html").forward(request, response);
	}
	
}
