# 安装 Maven

## 下载地址

[Downloading Apache Maven](http://maven.apache.org/download.cgi)

## 配置环境变量

```shell
# 解压缩后创建连接便于以后更新版本
sudo ln -s ~/apache-maven-3.6.3/ /usr/local/maven

# 配置环境变量
sudo vi /etc/profile
```

```conf
# 添加
export M2_HOME=/usr/local/maven/
export PATH=$M2_HOME/bin:$PATH
```

```shell
#重新加载
source /etc/profile
```

## 测试

```shell
mvn -v
```

## Mac系统

```shell
# 赋写权
sudo chmod a+rwx /etc/profile

# 编辑
vi  /etc/profile
source /etc/profile

# 把权限变更回来
sudo chmod a-wx /etc/profile

# 查看一下应该都是读权限
ls -l /etc/profile
```

```shell
# zsh加载优先级最高的配置文件
vi ~/.zshenv
```

```conf
# 添加如下命令
source /etc/profile
```
