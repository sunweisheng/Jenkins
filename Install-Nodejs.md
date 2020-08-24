# 安装 Nodejs

下载合适的版本（[下载地址](https://nodejs.org/en/download/)）

```shell
# 解压缩
tar xvf node-v12.18.3-linux-x64.tar.xz

#配置环境变量
vi /etc/profile
```

```conf
export NODE_HOME=/opt/node-v12.18.3-linux-x64
export PATH=$PATH:$NODE_HOME/bin:$NODE_HOME/lib/node_modules
```

```shell
source /etc/profile

# 检查结果
node -v
```

可以设置npm的代理，如果有私有仓库代理地址可以设置成：https://registry.npm.taobao.org

```shell
# 在nodejs构建服务器上设置代理地址
npm config set registry http://私有仓库地址/repository/npm-proxy/
```


