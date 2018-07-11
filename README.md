# simple_user_manage_demo
SpringBoot+SpringSecurity+Thymeleaf整合实践，完成一个很简单的用户信息管理系统。
目的是为了联系一下SpringSecurity的权限控制功能。

### 技术选型
* SpringBoot
* Mybatis
* SpringSecurity
* BootStrap
* Thymeleaf

### 数据库表设计

* Users表

|username|password|knick_name|phone|address|status 
------ | ------ | ------ | ------ | ------ | ------ 
varchar | varchar| varchar| varchar|varchar|int

* Role表

|id|rolename|
----|----|
int|varchar

* users_role表（多对多关系中间表）

|username|role_id
--------|--------
|varchar|int

### 项目结构（未完全按照面向接口编程方法）

![项目结构图片](https://github.com/ScathonLin/img_repo/raw/master/spring_security/user_manage_demo/img01.png)

### 项目配置过程

* mybatis配置略
* SpringSecurity配置
 * Users类的编写（注！需要实现UserDetails接口），重点是getAuthorities()方法。
 ```
	package com.scathon.ssm.pojo;
	//省略import
	public class Users implements UserDetails {
	    private String username;
	    private String password;
	    private String knickName;
	    private String phone;
	    private String address;
	    private Integer status;
	    private List<Role> userRoleList;
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        List<GrantedAuthority> auths = new ArrayList<>();
	        this.getUserRoleList().forEach(role -> auths.add(new SimpleGrantedAuthority(role.getRoleName())));
	        return auths;
	    }
	    @Override
	    public String getPassword() {
	        return this.password;
	    }
	    @Override
	    public String getUsername() {
	        return this.username;
	    }
	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }
	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }
	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }
	    @Override
	    public boolean isEnabled() {
	        return true;
	    }
	
	    public List<Role> getUserRoleList() {
	        return userRoleList;
	    }
		//省略部分getter and setter
	}

 ```
 * 自定义UserDetailsService接口实现类，然后交由SpringSecurity使用获取用户相关信息（比如权限信息）

	
	@Component
	public class ScathonUserDetailService implements UserDetailsService {
	    @Autowired
	    private UserService userService;
	
	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Users user = userService.findByUsername(username);
	        if (user == null) {
	            throw new UsernameNotFoundException("用户名不存在！");
	        }
	        return user;
	    }
	}
 
	 
* 