FROM openjdk:8-jdk-alpine
# 使用一个基础的 OpenJDK 8 镜像
#FROM openjdk:8-jdk-alpine AS builder
# alpine linux apk 的安装源改为国内镜像
#RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories
# 解决图片验证码 解决字体问题
RUN #apk add --update ttf-dejavu fontconfig && rm -rf /var/cache/apk/*
# 设置容器内的临时目录
VOLUME /tmp
# 设置默认的 Git 提交信息为 0
ARG GIT_COMMIT=0
# 将 Git 提交信息设置为环境变量
ENV GIT_COMMIT=${GIT_COMMIT}
# 设置时区
ENV TZ=Asia/Shanghai
# 将 Git 提交信息添加到镜像的元数据中
LABEL GIT_COMMIT=${GIT_COMMIT}
LABEL description="https://blog.csdn.net/qierkang：JrebelBrains-在线注册服务"
LABEL description="http://www.qekang.com"
LABEL Blog="https://blog.csdn.net/qierkang"
# 复制构建好的 JAR 文件到容器中
COPY target/jrebel-server-1.1-SNAPSHOT.jar /jrebel-server.jar
# 设置容器的时区为亚洲/上海
RUN ln -sf /usr/share/zoneinfo/${TZ} /etc/localtime
RUN echo ${TZ} >/etc/timezone
# 设置默认的端口号为 8866
ENV PORT 9001
# 设置 Java 运行时的参数
ENV JAVA_OPTS="-XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m -Xms128m -Xmx512m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -XX:CompileCommand=exclude,org/hibernate/cfg/annotations/SimpleValueBinder,setType"
# 容器启动时运行的命令
CMD java -jar -Duser.timezone=GMT+08 ${JAVA_OPTS} /jrebel-server.jar -p ${PORT}

