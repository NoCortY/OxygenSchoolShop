# Oxygen校园商铺

##### 喜欢就给个star吧！

## 项目介绍

名称：Oxygen校园商铺

定位：校园内的商铺经营自然人（学生 or 店主）

功能：注册店铺、上架/下架商品、支持自定义分类等等。

Oxygen校园商铺是一个基于SSM框架所完成的开源项目，依托互联网+生态，对校园内的店铺和商品进行从**完全线下采购**到**线上 to 线下**模式的转变。对于SSM框架学习者来着这是个很好的练手项目，对于需要进行生产环境项目开发的coder来说，这也是一个很好的案例参考。

## 项目技术（SSM）

1. **maven**
2. **Spring（IOC DI AOP 声明式事务处理）**
3. **SpringMVC（支持Restful风格）**
4. **MyBatis（持久层）**
5. **Redis（缓存层）**
6. **前端：JQuery+SUI Mobile（Aplibaba）+EasyUI**
7. **数据库：MySQL**
8. **百度地图 and 微信支付接入**(脚手架)
9. **日志：LogBack**
10. **单元测试：JUnit**

## 项目架构

项目采用基本的单服务器架构，当然也可以布置成集群模式。如果是学习，则单机模式足矣。后续会持续输出基于SpringBoot和基于SpringCloud的项目，并且项目会运用在自己的网站上，如果感兴趣的小伙伴可以给个Star或者Follow，感激不尽。

个人博客：[Object's Blog](http://blog.objectspace.cn)

![项目架构](http://image.objectspace.cn/1578547911026.png)

## 快速启动

- ### 环境

  项目启动需要基于Tomcat、Redis、MySQL，所以在启动本项目以前，确保计算机中存在这一套环境。

- ### 构建数据库

  ```sql
  数据库脚本已保存在项目文件中:shopsql.sql
  ```

- ### 修改配置文件

  jdbc.properties:

  ```properties
  jdbc.driver=com.mysql.jdbc.Driver
  jdbc.url=数据库url
  jdbc.username=数据库用户名
  jdbc.password=数据库密码
  ```

  **注意，数据库用户名和密码需要进行加密，在cn.objectspace.schoolshop.util.DESUtils生成加密后的用户名和密码，再填入配置文件，否则无法正确连接数据库。**

  redis.properties:

  ```properties
  redis.hostname=redisurl
  redis.password=redis密码
  redis.port=6379
  redis.database=0
  redis.pool.maxActive=600
  redis.pool.maxIdle=300
  redis.pool.maxWait=3000
  redis.pool.testOnBorrow=true
  ```

  weixin.properties(非必须):

  ```properties
  weixinappid=
  weixinappsecret=
  ```
  
- ### Server.xml

  这个配置文件关联图片的显示。

  ```xml
  <Context docBase="E:\projectdev\image\upload(改成存储图片路径)" path="/upload"/>
  ```

  

## 启动

- ### 前端展示

  - #### 首页：

    ![首页](http://image.objectspace.cn/1578549166546.png)

  - #### 商店列表：

    ![商店列表](http://image.objectspace.cn/1578549211136.png)

  - #### 店铺详情：

    ![店铺详情](http://image.objectspace.cn/1578549252911.png)

  - #### 商品详情：

    ![商品详情](http://image.objectspace.cn/1578549270815.png)

    ![商品详情图](http://image.objectspace.cn/1578549291873.png)

- ### 店主管理页

  - #### 注册登录

    ![注册登录](http://image.objectspace.cn/1578549486283.png)

    ![登录](http://image.objectspace.cn/1578549540279.png)

  - #### 商铺管理

    ![商铺管理](http://image.objectspace.cn/1578549569637.png)

  - #### 注册商铺

    ![注册商铺](http://image.objectspace.cn/1578549594284.png)

  - #### 商铺管理

    ![商铺管理](http://image.objectspace.cn/1578549594284.png)

  - #### 商品管理

    ![商品管理](http://image.objectspace.cn/1578549783161.png)

    ![商品编辑](http://image.objectspace.cn/1578549804296.png)

    ![预览](http://image.objectspace.cn/1578549804296.png)

    上/下架：

    ![1578549950271](http://image.objectspace.cn/1578549950271.png)

    新增商品:

    ![新增商品](http://image.objectspace.cn/1578549975592.png)

- ### 超级管理员

  - #### 首页

    ![首页](http://image.objectspace.cn/1578550288656.png)

  - #### 头条（轮播图）管理

    ![轮播图管理](http://image.objectspace.cn/1578550416638.png)

    ![1578551001492](http://image.objectspace.cn/1578551001492.png)

    ![删除](http://image.objectspace.cn/1578551026595.png)

  - #### 账号管理

    

    ![账号管理](http://image.objectspace.cn/1578550517197.png)

  - #### 区域管理

    ![区域管理](http://image.objectspace.cn/1578550610687.png)

  - #### 商铺管理

    ![1578550694303](http://image.objectspace.cn/1578550694303.png)

  - #### 类别管理

    ![1578550741744](http://image.objectspace.cn/1578550741744.png)

    ![类别管理2](http://image.objectspace.cn/1578550788623.png)

## 测试

**超级管理员：**

>**测试账号**:admin
>
>**密码**：123456

如有bug欢迎提交Issue,喜欢的小伙伴可以加个Star哦！

欢迎访问个人博客：[Object's Blog](http://blog.objectspace.cn)