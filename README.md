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
