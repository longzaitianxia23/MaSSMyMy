package com.ks.shiro;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.ks.index.model.UserVO;
import com.ks.shiro.model.Role;
import com.ks.shiro.model.User;

public class ShiroFilter implements Filter{

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;  
        HttpServletResponse httpResponse = (HttpServletResponse) response;  
        Principal principal = httpRequest.getUserPrincipal();
        //条件为true说明用户有登陆信息
        if(principal != null){  
            Subject subjects = SecurityUtils.getSubject();  
            // 为了简单，这里初始化一个用户。实际项目项目中应该去数据库里通过名字取用户：  
            // 例如：User user = userService.getByAccount(principal.getName()); 
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            UserVO userVo = (UserVO) session.getAttribute("user");
            //userVo.setRole(new Role(userVo.getTheRole()));
            if(userVo != null){  
                UsernamePasswordToken token = new UsernamePasswordToken(  
                		userVo.getUserAccountNumber(), userVo.getUserPassword());  
//                subjects = SecurityUtils.getSubject();  
//                subjects.login(token);
//                subjects.getSession();  
            }else{  
                // 如果用户为空，则subjects信息登出  
//                if(subjects != null){  
//                    subjects.logout();  
//                }  
            }  
        }  
        chain.doFilter(httpRequest, httpResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
}
