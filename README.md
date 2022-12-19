<h3 align="center"><a href="http://jrebel.qekang.com" target="_blank">jrebel-server</a></h3>

[![coverage](https://img.shields.io/badge/文档创建&贡献者-尔康-blueviolet.svg)](https://www.qekang.com)
|[![coverage](https://img.shields.io/badge/blog-important.svg)](https://www.qekang.com)
|[![coverage](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)
|[![coverage](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)

<font face="微软雅黑" size=2 color=#A9A9A9 >版权声明：内容供内部学习使用，请勿外传！转载请附上作者信息</font>

### jrebel 提供服务

## [Jrebel & Jet Brains License Server for Java](http://jrebel.qekang.com)

## Docker

Build mavne

```
mvn clean install
```

Build image

```
docker rm -f jrebel-server
docker rmi -f qierkang/jrebel-server
docker build -t qierkang/jrebel-server .
```

start container

```
docker run -d --name jrebel-server --restart always -e PORT=9001 -p 9001:9001 qierkang/jrebel-server
```

**default port is 9001,you can modify it**

## Support

JetBrains Products

## Feedback

+ [github issue](https://github.com/ek-template/jrebel-server/issues)
+ [gitee issue](https://gitee.com/ek-plugs/jrebel-server/issues)
+ Builder:qierkang E-mail:xyqierkang@163.com Wechat:qekang