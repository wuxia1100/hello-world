package org.cv.sf.framework.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.cv.sf.framework.shiro.MyAuthenticationToken;
import org.cv.sf.framework.shiro.MyShiroRealm;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * shiro 配置类
 * 使用shiro权限时 按照角色 和  许可 permission 进行注解
 *
 * W https://shiro.apache.org/spring-boot.html
 * <p>
 * <p>
 * Shiro 注解  用法
 * 可以在Action或控制器加入注解
 * '@RequiresGuest‘ 只有游客可以访问*
 * ’@RequiresAuthentication‘ 需要登录才能访问*
 * '@RequiresUser' 已登录的用户或“记住我”的用户能访问
 * '@RequiresRoles('rolename')' 已登录的用户需具有指定的角色才能访问*
 * '@RequiresPermissions('user:create')' 已登录的用户需具有指定的权限才能访问
 */
@Configuration
public class ShiroConfig {

    @Bean
    MyShiroRealm myShiroRealm(){
        return new MyShiroRealm();
    }

    @Bean
    MyAuthenticationToken myAuthenticationToken(){
        return new MyAuthenticationToken();
    }

    /**
     * SecurityManager是Shiro框架的核心，典型的Facade模式，
     * Shiro通过SecurityManager来管理内部组件实例，并通过它来提供安全管理的各种服务
     * SecurityUtils.getSubject()来进行门面获取
     */
    @Bean
    DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager  manager = new DefaultWebSecurityManager();
        manager.setRealm(myShiroRealm());
        return manager;
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(true)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，
         * 导致返回404。加入这项配置能解决这个bug
         */
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }
    /**
     * 配置缩写       	        对应的过滤器	                        功能
     * anon	             AnonymousFilter	                指定url可以匿名访问
     * authc	             FormticationFilter	        指定url需要form表单登录，默认会从请求中获取username、password,rememberMe等参数并尝试登录，如果登录不了就会跳转到loginUrl配置的路径。我们也可以用这个过滤器做默认的登录逻辑，但是一般都是我们自己在控制器写登录逻辑的，自己写的话出错返回的信息都可以定制嘛。
     * authcBasic	         BasicHttpAuthenticationFilter	    指定url需要basic登录
     * logout	             LogoutFilter	                    登出过滤器，配置指定url就可以实现退出功能，非常方便
     * noSessionCreation	 NoSessionCreationFilter	        禁止创建会话
     * perms                 PermissionsAuthorizationFilter     需要指定权限才能访问
     * port	             PortFilter	                        需要指定端口才能访问
     * rest	             HttpMethodPermissionFilter	        将http请求方法转化成相应的动词来构造一个权限字符串，这个感觉意义不大，有兴趣自己看源码的注释
     * roles	             RolesAuthorizationFilter	        需要指定角色才能访问
     * ssl	                 SslFilter	                        需要https请求才能访问
     * user	             UserFilter	                        需要已登录或“记住我”的用户才能访问
     * <p>
     * 但经过实际测试，过滤器的过滤路径，是context-path下的路径，无需加上"/platform"前缀
     * chain.addPathDefinition("/my/mvnBuild", "authc,perms[mvn:install]");//需要mvn:build权限
     * chain.addPathDefinition("/my/npmClean", "authc,perms[npm:clean]");//需要npm:clean权限
     * chain.addPathDefinition("/my/docker", "authc,roles[docker]");//需要js角色
     * chain.addPathDefinition("/my/python", "authc,roles[python]");//需要python角色
     */
/*
    @Bean
    ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();

        definition.addPathDefinition("doLogin","anon");//放行此路径
        definition.addPathDefinition("/**","authc");//拦截所有路径
        return definition;
    }*/

    /**
     * 自定义sessionManager
     *
     * @return
     */
/*    @Bean
    public SessionManager sessionManager() {
        CustomSessionManager customSessionManager = new CustomSessionManager();
        //这里可以不设置。Shiro有默认的session管理。如果缓存为Redis则需改用Redis的管理
        //customSessionManager.setSessionDAO(redisSessionDAO());
        return customSessionManager;
    }*/



    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/index");

        Map<String,String> map = new HashMap<>();
        map.put("/**","anon");
        map.put("/doLogin","anon");
        bean.setFilterChainDefinitionMap(map);

        return bean;
    }

}
