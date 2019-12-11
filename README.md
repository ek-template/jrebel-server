# jrebel-server

#### 介绍
jrebel 提供服务

# Jrebel & Jet Brains License Server for Java


## Docker
Build image
```
mvn package 
docker build -t jrebel-server .
```

start container
```
docker run -d --name jrebel-server --restart always -e PORT=9001 -p 9001:9001 qierkang/jrebel-server
```
default port is 9001,you can modify it
## Support
JetBrains Products

## Feedback

+ issue: https://gitee.com/ek-plugs/jrebel-server/issues
+ Builder:qierkang E-mail:xyqierkang@163.com Wechat:qekang