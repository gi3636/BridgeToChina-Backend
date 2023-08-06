# 留华桥 [brige-to-china]

<p align=center>
  <a href="#">
    <img src="./doc/image/header-logo.png" alt="留华桥">
  </a>
</p>
<p  align=center>
   基于微服务架构的前后端分离的留华信息共享平台
</p>
<p align="center">
<a target="_blank" href="https://github.com/gi3636/BridgeToChina-Backend">
  <img src="https://img.shields.io/badge/license-CC%20BY--NC--ND%204.0-lightgrey" alt="知识共享许可协议"/>
  <img src="https://img.shields.io/github/stars/gi3636/BridgeToChina-Backend" alt=""/>
  <img src="https://img.shields.io/github/forks/gi3636/BridgeToChina-Backend" alt=""/>
<br>
  <img src="https://img.shields.io/badge/SpringBoot-2.7.0-green" alt=""/>
  <img src="https://img.shields.io/badge/SpringCloud-2021.0.2-green" alt=""/>
  <img src="https://img.shields.io/badge/SpringCloudAlibaba-2021.0.1-green" alt=""/>
  <img src="https://img.shields.io/badge/Nacos-2021.0.2-orange" alt=""/>
  <img src="https://img.shields.io/badge/Netty-4.1.77-orange" alt=""/>
  <img src="https://img.shields.io/badge/RabbitMq-2.4.5-orange" alt=""/>
  <img src="https://img.shields.io/badge/Minio-8.3.4-orange" alt=""/>
  <img src="https://img.shields.io/badge/Redis-6.1.8-orange" alt=""/>
  <img src="https://img.shields.io/badge/MySql-8.0.25-orange" alt=""/>
  <img src="https://img.shields.io/badge/Elasticsearch-7.17.3-orange" alt=""/>
</a></p>

## 系统架构图

![img-structure.png](doc%2Fimage%2Fimg-structure.png)

## 项目特点

- 微服务架构开发,前后端分离
- 采用 **Nacos** 作为服务注册中心，支持分布式集群架构，方便微服务配置管理
- 采用 **Getaway** 搭建网关服务，进行负载转发的同时对用户身份进行识别，并支持配置接口未认证拦截功能
- 采用 **JWT** 的方式进行用户鉴权，配合网关服务保证其他所有微服务都能准确识别用户身份
- 采用 **Minio** 搭建对象存储服务
- 采用 **RabbitMQ** 搭建消息队列服务
- 采用 **Elasticsearch** 搭建搜索引擎服务
- 采用 **Redis** 搭建缓存服务
- 使用**Netty** + **Websocket**实现即时通讯

## 项目目录

```
├─config：全局配置文件目录
├─doc：项目文档目录
├─db：数据库文件目录
├─common：公用模块
│  ├─common-amqp：公用AMQP模块
│  ├─common-core：核心模块
│  ├─common-redis：公用Redis模块
│  ├─common-swagger：公用Swagger模块
│  └─common-mybatis：公用Mybatis模块
├─gateway-service：网关服务
├─user-service：用户服务
├─content-service：内容服务
├─message-service: 消息服务
├─comment-service：评论服务
└─file-service：文件服务
```

## 技术选型

### 后端技术

|       技术       |     说明     |                        官网                         |
|:--------------:|:----------:|:-------------------------------------------------:|
|   SpringBoot   |   MVC框架    |      https://spring.io/projects/spring-boot       |
|  SpringCloud   |   微服务框架    |     https://spring.io/projects/spring-cloud/      |
|    Gateway     |   网关服务框架   |  https://spring.io/projects/spring-cloud-gateway  |
|     Fegin      |   RPC框架    | https://spring.io/projects/spring-cloud-openfeign |
|     Nacos      |  注册、配置中心   |              https://nacos.io/zh-cn/              |
|  MyBatis-Plus  |   数据库框架    |             https://mp.baomidou.com/              |
|     MySQL      |    数据库     |              https://www.mysql.com/               |
|     Redis      |   分布式缓存    |                 https://redis.io/                 |
|    RabbitMQ    |    消息队列    |             https://www.rabbitmq.com/             |
| Elasticsearch  |    搜索引擎    |     https://github.com/elastic/elasticsearch      |
|     Docker     |   容器化部署    |              https://www.docker.com/              |
|    Jenkins     |  自动化部署服务   |              https://www.jenkins.io/              |
|     Druid      |   数据库连接池   |         https://github.com/alibaba/druid          |
|     SLF4J      |    日志框架    |               http://www.slf4j.org/               |
|     Lombok     |  简化对象封装工具  |      https://github.com/rzwitserloot/lombok       |
|     Nginx      |   web服务器   |                 http://nginx.org/                 |
|     Hutool     | Java工具包类库  |             https://hutool.cn/docs/#/             |
|     Minio      |  本地对象存储服务  |                  https://min.io/                  |
| Docker Compose | Docker容器编排 |         https://docs.docker.com/compose/          |


### 项目启动
请查看项目的doc文件夹下的[README](./doc/README.md)

### 前端技术

前端使用的是React+Nextjs框架，使用Ant Design作为UI框架，使用TypeScript进行开发, 项目地址：[BridgeToChina-Frontend](https://github.com/gi3636/brige-to-china)

### 联系方式
微信: gi3636

Discord社区：[留华桥](https://discord.gg/bqqqv584)
