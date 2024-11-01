# 部署步骤

## 安装环境
服务器的版本为 ubuntu18.04
### 安装docker
在所有的服务器都需要安装docker
<br>先更新ubuntu的安装源
<br>`apt-get update`
<br>再安装docker
<br>`apt-get install docker.io`

### 安装jdk17和maven
选择一台服务器作为编译用服务器。安装jdk17和maven
#### 安装JDK17
<br>用`apt-get install openjdk-17-jdk`安装jdk17


#### 安装maven
<br>用`wget https://dlcdn.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.zip` 下载maven 3.8.8
<br>用`unzip apache-maven-3.8.8-bin.zip` 将maven展开到本地目录
<br>在.profile文件最后增加环境变量
<br>`export PATH=MAVEN_HOME/bin:$PATH`其中MAVEN_HOME是maven展开的目录
<br>用`source .profile`让环境变量生效
<br>用`mvn -version`看maven的版本和jdk的版本是否正确

## docker swarm初始化
如果在docker swarm上部署，需要先安装docker swarm，参考以下网页
[https://docs.docker.com/engine/swarm/swarm-tutorial/create-swarm/
](https://docs.docker.com/engine/swarm/swarm-tutorial/create-swarm/)
<br>选择一台机器作为管理节点，运行
<br>`docker swarm init`
<br>将`docker swarm join --token ....`拷贝到其他的工作节点运行
<br>如果忘记上述命令可以用`docker swarm join-token worker`重新获得上述命令
<br>各节点加入后可以用`docker node ls`看到各节点的状况

### 创建docker swarm overlay network
用`docker network create --driver overlay my-net`在docker swarm内部建立一个虚拟网络，未来所有的服务都在这个虚拟网络里。

## 安装MySQL镜像和服务

### 更新目标服务器的label
为目标服务器定义label，方便docker swarm在创建Service时，将Service部署在目标服务器上，以下我们在node1上定义了一个label `server=mysql`<br>
`docker node update --label-add server=mysql node1`<br>

### 在docker swarm中创建服务
<br>MySQL的配置文件目录conf.d和数据库初始化脚本都在productdemo的mysql目录下，需要把这些文件拷贝到运行mysql的节点上，并映射到容器中。同时在root目录建立mysql/log和mysql/data分别用来存放mysql的日志和数据库文件
<br>用`chmod a+wr mysql/log`将日志目录赋予所有人读写的权限，mysql/data目录也做相同处理 
<br>用以下命令创建mysql
<br>`docker service create --name mysql --with-registry-auth --constraint node.labels.server==mysql --publish published=3306,target=3306 --mount type=bind,src=/root/JavaEEPlatform/7.SpringAOP/productdemoaop/mysql/sql,dst=/sql,readonly --mount type=bind,src=/root/JavaEEPlatform/7.SpringAOP/productdemoaop/mysql/conf.d,dst=/etc/mysql/conf.d,readonly --mount type=bind,src=/root/mysql/log,dst=/var/log/mysql --mount type=bind,src=/root/mysql/data,dst=/var/lib/mysql  --network my-net -e MYSQL_ROOT_PASSWORD=123456  -d swr.cn-north-4.myhuaweicloud.com/oomall-javaee/mysql:latest`
<br>其中 `--with-registry-auth`是带token访问私有镜像，如果访问官方公开镜像无需此参数
<br>`--mount type=bind,src=/root/7.SpringAOP/productdemoaop/mysql/sql,dst=/sql,readonly`是将OOMALL的数据SQL脚本mount到容器中
<br>`--mount type=bind,src=/root/7.SpringAOP/productdemoaop/mysql/conf.d,dst=/etc/mysql/conf.d,readonly`是将OOMALL中的MySQL设置mount到容器中
<br>`--mount type=bind,src=/root/mysql/log,dst=/var/log/mysql`是将操作系统的`/root/mysql/log`mount到容器中的MySQL日志目录，这样在操作系统里就能看到mysql的日志
<br>`--mount type=bind,src=/root/mysql/data,dst=/var/lib/mysql`是将操作系统的`/root/mysql/data`mount到容器中的MySQL数据目录，这样将数据库数据存储在操作系统的目录下
<br>`-e MYSQL_ROOT_PASSWORD=123456`是设定数据库root账户密码<br>
<br>如果需要将mysql的端口暴露出来 加上--publish published=3306,target=3306
<br>swr.cn-north-4.myhuaweicloud.com/oomall-javaee/mysql:latest 是mysql在华为云上的私有镜像，如果访问官网镜像请改成mysql:latest


### 在运行mysql服务的节点上运行sql脚本
看一下mysql的服务运行在哪台服务器<br>
`docker service ps mysql`<br>
切换到运行mysql服务的机器，看一下mysql容器在这台机器的container id，将容器的CONTAINER ID拷贝替换下述命令中[CONTAINER ID],用这个容器运行mysql的命令<br>
`docker exec -it [CONTAINER ID] mysql -uroot -p`<br>
用root账号登录mysql服务器，在运行起来的mysql命令行中用`source /sql/database.sql`建立productdemo数据库<br>

### 初始化数据
用`use oomall_demo`切换数据库<br>
用`source /sql/product.sql`插入初始数据

## 编译打包productdemoaop
在安装了maven和jdk的节点上编译打包productdemoaop

### 编译productdemoaop
将代码从服务器上克隆下来，`git clone https://codehub.devcloud.cn-north-4.huaweicloud.com/OOMALL00024/JavaEEPlatform.git`
<br>在JavaEEPlatform/7.SpringAOP/productdemoaop目录下运行
<br>`mvn clean pre-integration-test -Dmaven.test.skip=true`
<br>在编译完成后用`docker images`能看到镜像已经在本地存在

### 上传镜像到华为swr
将镜像名称改成华为swr所需要的名称，其中组织名称是在华为swr上建立的组织
<br>`docker tag {镜像名称}:{版本名称} swr.cn-north-4.myhuaweicloud.com/{组织名称}/{镜像名称}:{版本名称}`
<br>将镜像上传到华为swr`docker push swr.cn-north-4.myhuaweicloud.com/{组织名称}/{镜像名称}:{版本名称}`，上传前应该建立本节点和华为swr的临时或永久登录关系。

## 部署productdemoaop服务
在部署的节点（如node2）的/root/上建立logs目录。
<br>在管理机上创建服务
<br>`docker node update --label-add server=goods node2`
<br>`docker service create --name productdemoaop --with-registry-auth --network my-net --constraint node.labels.server==goods --publish published=8080,target=8080 --mount type=bind,source=/root/logs,destination=/app/logs -d swr.cn-north-4.myhuaweicloud.com/{组织名称}/{镜像名称}:{版本名称}`
<br>用`docker service ls`看所有服务运行状况
<br>`docker service ps productdemo`看productdemoaop服务的具体状况
<br>在部署的节点上的logs目录用`tail -f productdemoaop.log`看日志文件的内容

## 测试productdemoaop服务

### 安装配置Jmeter
安装Jmeter需要先安装jdk17
<br>从[https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.6.3.zip](https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.6.3.zip)下载Jmeter
<br>`wget https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.6.3.zip`
<br>`unzip apache-jmeter-5.6.3.zip`将压缩文件解压
<br>由于服务器内存只有1G，需要修改jmeter的配置，才能将jmeter运行起来。在jmeter的bin目录下新建setenv.sh配置文件，在文件中增加以下内容
<br>`export JVM_ARGS="-Xms256m -Xmx256m -XX:MaxMetaspaceSize=256m"`
<br>修改jmeter报告的采样间隔，从60秒修改为1秒，jmeter的bin目录下的user.properties文件中的`jmeter.reportgenerator.overall_granularity=60000`将其改为`jmeter.reportgenerator.overall_granularity=1000`
<br>修改root目录下的.profile文件，在文件最后增加
<br>`export PATH=/root/apache-jmeter-5.6.3/bin:$PATH`将jmeter增加到运行路径里

### 测试productdemoaop
修改jmx文件中的hostname，改为swarm集群的内部ip，运行
<br>`jmeter -n -t ReadProduct.jmx -l Read100-20.jtl -e -o Read/100-20`对productdemoaop进行测试



