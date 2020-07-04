# 创建Global Shared Library项目

Maven设置中设置仓库地址

```conf
    <repositories>
        <repository>
        <id>jenkins-ci-releases</id>
        <url>https://repo.jenkins-ci.org/releases/</url>
        </repository>
        ...
    </repositories>
```

```shell
# 在工作目录下执行
mvn -U archetype:generate -Dfilter=io.jenkins.archetypes:

Choose archetype:
1: remote -> io.jenkins.archetypes:empty-plugin (-)
2: remote -> io.jenkins.archetypes:global-configuration-plugin (Skeleton of a Jenkins plugin with a POM and an example piece of global configuration.)
3: remote -> io.jenkins.archetypes:global-shared-library (Uses the Jenkins Pipeline Unit mock library to test the usage of a Global Shared Library)
4: remote -> io.jenkins.archetypes:hello-world-plugin (Skeleton of a Jenkins plugin with a POM and an example build step.)
5: remote -> io.jenkins.archetypes:scripted-pipeline (Uses the Jenkins Pipeline Unit mock library to test the logic inside a Pipeline script.)
# 选择3 带测试的Global Shared Library项目
Choose a number or apply filter (format: [groupId:]artifactId, case sensitive contains): : 3
Choose io.jenkins.archetypes:global-shared-library version:
1: 1.4
2: 1.5
3: 1.6
# 选择3
Choose a number: 3: 3
[INFO] Using property: groupId = org.sample
# 输入项目目录名称
Define value for property 'artifactId': global-shared-library-frame  
# 版本号默认即可
Define value for property 'version' 1.0-SNAPSHOT: :
[INFO] Using property: package = io.jenkins.pipeline.sample
Confirm properties configuration:
groupId: org.sample
artifactId: global-shared-library-frame
version: 1.0-SNAPSHOT
package: io.jenkins.pipeline.sample
# 同意 y
 Y: : y
[INFO] ----------------------------------------------------------------------------
[INFO] Using following parameters for creating project from Archetype: global-shared-library:1.6
[INFO] ----------------------------------------------------------------------------
[INFO] Parameter: groupId, Value: org.sample
[INFO] Parameter: artifactId, Value: global-shared-library-frame
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] Parameter: package, Value: io.jenkins.pipeline.sample
[INFO] Parameter: packageInPathFormat, Value: io/jenkins/pipeline/sample
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] Parameter: package, Value: io.jenkins.pipeline.sample
[INFO] Parameter: groupId, Value: org.sample
[INFO] Parameter: artifactId, Value: global-shared-library-frame
[INFO] Project created from Archetype in dir: /Users/sunweisheng/Documents/Test-Jenkins-Plugin/global-shared-library-frame
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  02:49 min
[INFO] Finished at: 2020-07-04T21:04:27+08:00
[INFO] ------------------------------------------------------------------------
```

完成后用IDEA打开项目即可。

![Alt text](http://static.bluersw.com/images/Jenkins/global-shared-library-01.png)

打开项目后将pipelineUsingSharedLib.groovy改名为pipelineUsingSharedLib.Jenkinsfile，TestSharedLibrary测试类里runScript()中的地址也要改一下，红色的错误提示就没了，之后把各个文件夹中的包名（目录名）改成你自己的包名。

有两个POM文件，分别需求修改一下，主要是各种改名字和增加一些需要的引用：

```conf
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.bluersw</groupId>
    <artifactId>global-shared-library-frame-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    <name>Global Shared Library - Parent</name>
    <description>Global Shared Library Frame</description>
    <modules>
        <module>unit-tests</module>
    </modules>
</project>
```

```conf
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.bluersw</groupId>
    <artifactId>global-shared-library-frame</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>Global Shared Library Frame</name>
    <description>Global Shared Library Frame</description>
    <url>https://github.com/sunweisheng/global-shared-library-frame</url>
    <licenses>
        <license>
            <name>MIT License</name>
            <url>https://opensource.org/licenses/MIT</url>
        </license>
    </licenses>
    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <!-- Dependency versions -->
        <groovy.version>2.4.17</groovy.version>
        <junit.version>4.12</junit.version>
        <jenkins-pipeline-unit.version>1.1</jenkins-pipeline-unit.version>
        <groovy-eclipse-compiler.version>3.5.0-01</groovy-eclipse-compiler.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <!-- Pick up common dependencies for 2.164.x: https://github.com/jenkinsci/bom#usage -->
                <groupId>io.jenkins.tools.bom</groupId>
                <artifactId>bom-2.164.x</artifactId>
                <version>3</version>
                <scope>import</scope>
                <type>pom</type>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.codehaus.groovy</groupId>
            <artifactId>groovy-all</artifactId>
            <version>${groovy.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.lesfurets</groupId>
            <artifactId>jenkins-pipeline-unit</artifactId>
            <version>${jenkins-pipeline-unit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jenkins-ci.plugins.workflow</groupId>
            <artifactId>workflow-job</artifactId>
            <version>2.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jenkins-ci.plugins.workflow</groupId>
            <artifactId>workflow-cps</artifactId>
            <version>2.23</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <testSourceDirectory>src/test/groovy</testSourceDirectory>
        <resources>
            <resource>
                <directory>src/main/jenkins</directory>
            </resource>
        </resources>
        <testResources>
            <testResource>
                <directory>../shared-library</directory>
                <targetPath>libs/shared-library@master</targetPath>
            </testResource>
        </testResources>
        <plugins>
            <plugin>
                <groupId>org.codehaus.groovy</groupId>
                <artifactId>groovy-eclipse-compiler</artifactId>
                <version>${groovy-eclipse-compiler.version}</version>
                <extensions>true</extensions>
            </plugin>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <compilerId>groovy-eclipse-compiler</compilerId>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>org.codehaus.groovy</groupId>
                        <artifactId>groovy-eclipse-compiler</artifactId>
                        <version>${groovy-eclipse-compiler.version}</version>
                    </dependency>
                    <!-- for 2.8.0-01 and later you must have an explicit dependency on groovy-eclipse-batch -->
                    <dependency>
                        <groupId>org.codehaus.groovy</groupId>
                        <artifactId>groovy-eclipse-batch</artifactId>
                        <version>2.5.8-02</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
</project>
```

[示例项目 Global Shared Library Frame](https://github.com/sunweisheng/global-shared-library-frame)
