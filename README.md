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

### 项目配置过程，主要就是SpringSecurity的配置

* mybatis配置略
* SpringSecurity配置
 	1. Users类的编写（注！需要实现UserDetails接口），重点是getAuthorities()方法。
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
 	2. 自定义UserDetailsService接口实现类，然后交由SpringSecurity使用获取用户相关信息（比如权限信息），其中userService中将用户的权限信息也set到了Users类的userRoleList中

	```
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
	```
	3.继承WebSecurityConfigurerAdapter定制具体要求的权限控制功能，其中配置了具体请求路由对应的所需权限，例如下面代码，/admin/**请求将会需要ADMIN权限

	```
	@Configuration
	//@EnableWebSecurity
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Bean
		public UserDetailsService userDetailService() {
			return new ScathonUserDetailService();
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			//使用我们自己定义的方式进行用户权限相关的信息的获取
			auth.userDetailsService(userDetailService());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.authorizeRequests()
					.antMatchers("/admin/**").hasRole("ADMIN")
					.antMatchers("/dev/**").hasAnyRole("ADMIN", "DEV")
					.antMatchers("/user/**").hasAnyRole("ADMIN", "DEV", "USER")
					.anyRequest().authenticated()
					.and()
					//定义登录界面的路径
					.formLogin().loginPage("/login")
					//定义登录成功的跳转路径
					.defaultSuccessUrl("/index")
					//定义登录失败的跳转路径
					.failureUrl("/login?error=true")
					.permitAll()
					.and()
					//登出，默认路径就是/logout
					.logout()
					.permitAll();
		}
	}
	```
	4. 为登录请求/login添加对应的request映射,这个继承方式在springboot2.x中被遗弃了
	```
	@Configuration
	public class WebMvcConfig extends WebMvcConfigurerAdapter {
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/login").setViewName("login");
		}
	}
	```
* 