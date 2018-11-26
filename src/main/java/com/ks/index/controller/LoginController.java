package com.ks.index.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.index.model.UserVO;
import com.ks.index.service.IndexService;

/** 
 * 登录认证控制器 
 */ 
@Controller
@RequestMapping("/LoginController")
public class LoginController {
	
	@Autowired
	private IndexService indexService;
	
	/** 
     * 登录 
     * @param session 
     *          HttpSession 
     * @param username 
     *          用户名 
     * @param password 
     *          密码 
     * @return 
     */  
    @RequestMapping(value="/login")  
	@ResponseBody
    public String login(HttpSession session,String username,String password){
        //在Session里保存信息  
        session.setAttribute("username", username);  
        //重定向
        return "redirect:hello.action";   
    }
    
    /** 
     * 退出系统 
     * @param session 
     *          Session 
     * @return 
     * @throws Exception 
     */  
    @RequestMapping(value="/logout")  
	@ResponseBody
    public String logout(HttpSession session){  
        //清除Session  
        session.invalidate();  
          
        return "redirect:hello.action";  
    }  
}
