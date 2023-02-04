# 设置JAVA版本
FROM openjdk:8
WORKDIR /root
# 将当前目录下的jar包复制到docker容器的/目录下
ADD jarfile/user/app.jar /root/app.jar
# 设置JVM运行参数，限定内存大小，并设置时区为东八区
ENV JAVA_OPTS="\
-server \
-Xms256m \
-Xmx512m \
-XX:MetaspaceSize=256m \
-XX:MaxMetaspaceSize=512m \
-Duser.timezone=GMT+08 "

EXPOSE 8080
# 入口点， 执行JAVA运行命令
ENTRYPOINT ["java","-jar","/root/app.jar"]
