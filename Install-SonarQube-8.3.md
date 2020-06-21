# 安装SonarQube 8.3版本

[官方文档](https://docs.sonarqube.org/latest/setup/install-server/)

[下载地址](https://www.sonarqube.org)

## 准备工作

* 准备一台CentOS 7服务器
* SonarQube 8.3版本只支持Java 11 ([下载Java 11](https://www.oracle.com/java/technologies/javase-downloads.html))
* [安装pgAdmin](https://www.pgadmin.org/download/)

## 安装PostgreSQL 12.0

```shell
yum install https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm

yum install postgresql12-server

/usr/pgsql-12/bin/postgresql-12-setup initdb
systemctl enable postgresql-12
systemctl start postgresql-12

# 安装后的数据库data目录
cd /var/lib/pgsql/12/data

# 修改配置
vi pg_hba.conf
host    all             all             0.0.0.0/0            md5

vi postgresql.conf
listen_addresses = '*'

systemctl restart postgresql-12

# 客户端程序目录
cd /usr/pgsql-12/bin

# 安装的时候会自动创建postgres用户密码为空
su postgres
bash-4.2$ psql
psql (12.3)
输入 "help" 来获取帮助信息.

# 修改管理员密码(默认是随机密码)
ALTER USER postgres WITH PASSWORD 'postgres';

# 退出
\q
```

## 安装服务端程序

```shell
# 上传SQ v8.3
scp /Users/sunweisheng/Downloads/sonarqube-8.3.1.34397.zip root@sq.bluersw.com:/opt/

# 上传Java 11
scp /Users/sunweisheng/Downloads/jdk-11.0.7_linux-x64_bin.tar root@sq.bluersw.com:/opt/

# 解压缩
yum install zip unzip

cd /opt
tar -xvf jdk-11.0.7_linux-x64_bin.tar
# 一定用ZIP解压缩原始文件，否则会产生很多._XXX的隐藏文件，使程序报错
unzip sonarqube-8.3.1.34397.zip

# 创建用户
groupadd sonar
useradd sonar -g sonar
passwd sonar

chown -R sonar.sonar /opt/jdk-11.0.7/
chown -R sonar.sonar /opt/sonarqube-8.3.1.34397/

```

## 创建数据库

```shell
su postgres

bash-4.2$ psql

# 创建用户
create user sonar with password 'sonar';

# 创建数据库指定所属者
create database sonarqube owner=sonar encoding='UTF8';

# 将dbtest所有权限赋值给sonar
grant all on database sonarqube to sonar;
```

## 配置SonarQube

```shell
# 修改sonar.properties配置文件($SONARQUBE-HOME/conf/sonar.properties)
cd /opt/sonarqube-8.3.1.34397/conf
vi sonar.properties

sonar.jdbc.url=jdbc:postgresql://localhost/sonarqube
sonar.jdbc.username=sonar
sonar.jdbc.password=sonar

# 系统安装的是Java 8，所以需要单独指定Java 11的路径
vi wrapper.conf

wrapper.java.command=/opt/jdk-11.0.7/bin/java

# elasticsearch需要改
vi /etc/sysctl.conf

vm.max_map_count=655360

sysctl -p
```

## 手工启动检查日志排除错误

```shell
su sonar

# 第一次启动会有各种初始化过程
/opt/sonarqube-8.3.1.34397/bin/linux-x86-64/sonar.sh start

# 查看logs文件夹下的日志文件，排查错误。千万用ZIP解压缩否则产生一堆隐藏文件和莫名错误
cat /opt/sonarqube-8.3.1.34397/logs/sonar.log
cat /opt/sonarqube-8.3.1.34397/logs/es.log
```

## 创建服务

```shell
vi /etc/systemd/system/sonarqube.service
```

ExecStart中的路径请根据版本不同重新设置

```conf
[Unit]
Description=SonarQube service
After=syslog.target network.target

[Service]
Type=simple
User=sonar
Group=sonar
PermissionsStartOnly=true
ExecStart=/bin/nohup /opt/jdk-11.0.7/bin/java -Xms32m -Xmx32m -Djava.net.preferIPv4Stack=true -jar /opt/sonarqube-8.3.1.34397/lib/sonar-application-8.3.1.34397.jar
StandardOutput=syslog
LimitNOFILE=65536
LimitNPROC=8192
TimeoutStartSec=5
Restart=always
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
```

```shell
systemctl daemon-reload
systemctl enable sonarqube.service
systemctl start sonarqube.service
```

## 访问安装SonarQube

访问 http://192.168.0.5:9000/

默认用户名和密码都是：admin
