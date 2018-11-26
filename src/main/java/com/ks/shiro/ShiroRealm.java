package com.ks.shiro;

import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.ks.index.model.UserVO;
import com.ks.index.service.IndexService;
import com.ks.shiro.model.Role;

public class ShiroRealm extends AuthorizingRealm{
	
	private static final Logger LOG = Logger.getLogger(AuthorizingRealm.class);  

	@Autowired
    private IndexService indexService;
	
	//登陆第二步,通过用户信息将其权限和角色加入作用域中,达到验证的功能
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 根据用户配置用户与权限
		if (principals == null) {  
            throw new AuthorizationException(  
                    "PrincipalCollection method argument cannot be null.");  
        }
		String name = (String) getAvailablePrincipal(principals);
		List<String> roles = new ArrayList<String>();  
        List<String> permissions = new ArrayList<String>();
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        UserVO userVo = (UserVO) session.getAttribute("user");
        if(userVo != null){
            // 装配用户的角色和权限 delete  
            List<Role> roleList = new ArrayList<Role>();
            roleList.add(new Role("role1"));
            roleList.add(new Role("role2"));
            //String userRole = userVo.getTheRole();
            for(Role role : roleList){
                roles.add(role.getName());
            }
            //permissions.add("delete");  
        } else {  
            throw new AuthorizationException();  
        }  
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();  
        // 为用户设置角色和权限  
        info.addRoles(roles);  
        info.addStringPermissions(permissions);  
        return info;
	}

	/**  
     * 验证当前登录的Subject  
     * @see 经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时  
     */  
    @SuppressWarnings("unused")
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        //下面通过读取token中的数据重新封装了一个user
        UserVO userVo = new UserVO();
        userVo.setUserAccountNumber(token.getUsername());
        userVo = indexService.queryUserByAccount(userVo);
        String pwd = new String(token.getPassword());  
        if (userVo == null || !pwd.equals(userVo.getUserPassword())){
        	throw new AuthorizationException("用户名或密码错误");  
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userVo.getUserAccountNumber(),  
        		pwd, getName());  
        //将该userVo村放入session作用域中  
        this.setSession("user", userVo);
        return info;
	}
    
    /**  
     * 将一些数据放到ShiroSession中,以便于其它地方使用  
     * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到  
     */  
    @SuppressWarnings("unused")  
    private void setSession(Object key, Object value) {  
        Subject currentUser = SecurityUtils.getSubject();  
        if (null != currentUser) {  
            Session session = currentUser.getSession();  
            System.out  
                    .println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");  
            if (null != session) {  
                session.setAttribute(key, value);  
            }  
        }  
    }

}
