
## [Jrebel 热部署插件服务 & Jet Brains License Server for Java](http://jrebel.qekang.com)

---
<font face="微软雅黑" size=2 color=#A9A9A9 >版权声明：内容供技术友人学习使用，请勿外传！转载请附上作者信息</font>

[![coverage](https://img.shields.io/badge/文档创建&贡献者-尔康-blueviolet.svg)](https://www.qekang.com)|[![coverage](https://img.shields.io/badge/blog-important.svg)](https://www.qekang.com)|[![coverage](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)|[![coverage](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)


#### JAVA-IDEA`Jrebel` 是一个Java虚拟机插件，能够帮助Java开发者在无需重启应用的情况下即时查看代码变更的效果，极大地提高了开发效率。它允许开发者快速热部署代码，减少了因频繁重启服务器而浪费的时间。


<div align="center">
  <b>特别赞助</b>
</div>
<table align="center" cellspacing="0" cellpadding="0" width="500">
  <tr>
    <td align="center" valign="middle" colspan="1">
      <a href="https://github.com/qierkang" target="_blank">
        <img height="150" src="http://jrebel-default.oss-cn-nanjing.aliyuncs.com/code.jpg" alt="薯条开源">
      </a>
    </td>
  </tr>
</table>

---

### 重要信息
1. [服务地址](http://jrebel.qekang.com)
2. [使用方法](https://blog.csdn.net/qierkang/article/details/95095954)


### Docker部署方式

<details>

<summary>点此展开查看</summary>

### 1.第一步打包 maven

```shell
mvn clean install
```

### 2.第二步打包 Dockerfile

```shell
# 删除本地 jrebel-server 容器
docker rm -f jrebel-server
# 删除本地 jrebel-server 镜像
docker rmi -f qierkang/jrebel-server
# 打包本地镜像根据Dockerfile配置进行打包
docker build -t qierkang/jrebel-server .
```

### 3.第三步启动镜像服务 start container

```shell
# 启动服务
docker run -d --name jrebel-server --restart always -e PORT=9001 -p 9001:9001 qierkang/jrebel-server
# 启动完成过后 默认端口 9001
docker ps -a
```

### 4.代码推送仓库

```shell
# 如果发布到  GitHub
git push -f git@github.com:ek-template/jrebel-server.git master

# 如果发布到  Gitee
git push -f git@gitee.com:ek-plugs/jrebel-server.git master

```
</details>

----------------------------------

### 贡献者列表
特别感谢参与贡献的所有同学，欢迎大家继续踊跃贡献代码！

<details>
<summary>点击此处展开查看贡献次数最多的几位小伙伴</summary>

+ [尔康 (薯条开源)](https://github.com/qierkang)


</details>

### 提问和交流
- [GitHub提问](https://github.com/ek-template/jrebel-server/issues)
- [Gitee提问](https://gitee.com/ek-plugs/jrebel-server/issues)

## 星标趋势
[![Stargazers over time](https://starchart.cc/ek-template/jrebel-server.svg?variant=adaptive)](https://starchart.cc/ek-template/jrebel-server)