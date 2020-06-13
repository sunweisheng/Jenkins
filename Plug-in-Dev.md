# Jenkins插件开发

## 插件功能

在Jenkins构建之前选择Slave Server进行构建。
[Slave Server搭建](https://github.com/sunweisheng/Jenkins/blob/master/Jenkins-Slave.md)

## 准备工作

[安装Java](https://github.com/sunweisheng/Kvm/blob/master/Install-Java-18.md)

[安装Maven](https://github.com/sunweisheng/Jenkins/blob/master/Install-Maven.md)

## 命名规约

artifactId：

- 使用小写 ID ，并根据需要使用连字符分隔术语
- 除非名称有任何意义，否则不要在 ID 中包含 jenkins 或 plugin

插件名称：

- 插件的名称在 Jenkins UI 和其它地方（如：插件站点）展示给用户
- 建议使用简短的描述性名称，如 Subversion
- 本示例的插件名称叫：Select Slave Server

groupId：

- 推荐使用 io.jenkins.plugins 或 org.jenkins-ci.plugins 作为 groupId
- 但是不禁止其他组织 ID ，除非它们是恶意的
- 本示例的GroupId是：com.bluersw

Java 源代码：

- 一般遵循Oracle Java 代码规约
- 本示例的IDE使用IntelliJ IDEA (Community Edition)，并安装了Alibaba Java Code Guidelines插件规范代码规约

## 创建项目

```shell
# 在项目文件夹下执行
mvn -U archetype:generate -Dfilter=io.jenkins.archetypes:
```

PS：在Generating project in Interactive mode会等一会儿。

选择创建一个空项目

```shell
Choose archetype:
1: remote -> io.jenkins.archetypes:empty-plugin (-)
2: remote -> io.jenkins.archetypes:global-configuration-plugin (Skeleton of a Jenkins plugin with a POM and an example piece of global configuration.)
3: remote -> io.jenkins.archetypes:global-shared-library (Uses the Jenkins Pipeline Unit mock library to test the usage of a Global Shared Library)
4: remote -> io.jenkins.archetypes:hello-world-plugin (Skeleton of a Jenkins plugin with a POM and an example build step.)
5: remote -> io.jenkins.archetypes:scripted-pipeline (Uses the Jenkins Pipeline Unit mock library to test the logic inside a Pipeline script.)
Choose a number or apply filter (format: [groupId:]artifactId, case sensitive contains): : 1
Choose io.jenkins.archetypes:empty-plugin version:
1: 1.0
2: 1.1
3: 1.2
4: 1.3
5: 1.4
6: 1.5
7: 1.6
Choose a number: 7: 7
Downloading from repo.bluersw.com: http://repo.bluersw.com:8081/repository/maven-public/io/jenkins/archetypes/empty-plugin/1.6/empty-plugin-1.6.pom
Downloaded from repo.bluersw.com: http://repo.bluersw.com:8081/repository/maven-public/io/jenkins/archetypes/empty-plugin/1.6/empty-plugin-1.6.pom (717 B at 991 B/s)
Downloading from repo.bluersw.com: http://repo.bluersw.com:8081/repository/maven-public/io/jenkins/archetypes/empty-plugin/1.6/empty-plugin-1.6.jar
Downloaded from repo.bluersw.com: http://repo.bluersw.com:8081/repository/maven-public/io/jenkins/archetypes/empty-plugin/1.6/empty-plugin-1.6.jar (1.5 kB at 3.7 kB/s)
[INFO] Using property: groupId = unused
Define value for property 'artifactId': select-slave-server-parameter
Define value for property 'version' 1.0-SNAPSHOT: :
[INFO] Using property: package = unused
Confirm properties configuration:
groupId: unused
artifactId: select-slave-server-parameter
version: 1.0-SNAPSHOT
package: unused
 Y: : y
[INFO] ----------------------------------------------------------------------------
[INFO] Using following parameters for creating project from Archetype: empty-plugin:1.6
[INFO] ----------------------------------------------------------------------------
[INFO] Parameter: groupId, Value: unused
[INFO] Parameter: artifactId, Value: select-slave-server-parameter
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] Parameter: package, Value: unused
[INFO] Parameter: packageInPathFormat, Value: unused
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] Parameter: package, Value: unused
[INFO] Parameter: groupId, Value: unused
[INFO] Parameter: artifactId, Value: select-slave-server-parameter
[INFO] Project created from Archetype in dir: /Users/sunweisheng/Documents/Test-Jenkins-Plugin/select-slave-server-parameter
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  05:05 min
[INFO] Finished at: 2020-06-13T08:42:26+08:00
[INFO] ------------------------------------------------------------------------
sunweisheng@localhost Test-Jenkins-Plugin %
```

PS:将项目目录下的文件拷贝到GitHub仓库目录下面，并用IDEA打开项目。

![Alt text](http://static.bluersw.com/images/Jenkins/sssp-01.png)

