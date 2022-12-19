FROM java:8-jre-alpine
MAINTAINER https://blog.csdn.net/qierkang
LABEL description="https://blog.csdn.net/qierkang：JrebelBrains-在线注册服务"
ADD target/jrebel-server-1.1-SNAPSHOT-jar-with-dependencies.jar /jrebel-server.jar
ENV PORT 9001
CMD java -jar /jrebel-server.jar -p $PORT