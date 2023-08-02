# 设置JAVA版本
FROM adoptopenjdk/openjdk11-openj9
WORKDIR /root
# 将当前目录下的jar包复制到docker容器的/目录下
ADD app.jar /root/app.jar
# 设置JVM运行参数，限定内存大小，并设置时区为东八区
ENV JAVA_OPTS="\
-server \
-Xms128m \
-Xmx128m \
-Xss256k \
-XX:MetaspaceSize=128m \
-XX:MaxMetaspaceSize=256m \
-Duser.timezone=GMT+08 "

EXPOSE 8100
# 入口点， 执行JAVA运行命令
ENTRYPOINT ["java","-jar","/root/app.jar","--spring.profiles.active=prod"]
