# TSvnPwd4java 本地SVN密码查看器_java版 

**查看本地登录过的SVN帐号密码**


项目由来：

公司的代码版本控制使用SVN，某天，换电脑（虚拟桌面），忘记了SVN密码，网上找到了TSvnPwd这个工具，该工具使用C#编写，依赖于低版本.NET环境。然而，由于不可知的原因，在win10系统中可能无法安装成功该程序所需的.NET。
于是基于对Java语言的偏爱，建立此项目，顺便学习一些新知识。本项目移植自[http://www.leapbeyond.com/ric/TSvnPD/] 



工作原理：  
默认情况下，TortoiseSVN将缓存的凭据存储在 ％APPDATA％\Subversion\auth\svn.simple目录中的文件中。密码使用Windows数据保护API加密，并具有与用户帐户绑定的密钥。该工具读取文件并使用API解密密码。
事实上该工具并非利用TortoiseSVN中的安全漏洞进行解密。仅当您使用最初对文件进行加密的Windows用户帐户登录并通过身份验证时，才可以访问密码。





局限性：
<ul>
  <li>此项目算法部分依赖于默认的svn环境设置,毫无疑问，不能保证所有情况都有效</li>
  <li>虽然不依赖.NET,但是依赖于Java环境</li>
  <li>仅适用于Windows</li>
</ul>


使用说明：

0.如果想自己编译执行可以按照步骤1、2、3操作，也可以从https://github.com/meiMingle/TSvnPwd4java/releases 直接下载TSvnPwd4java-1.0-SNAPSHOT-jar-with-dependencies.jar ，并跳过步骤1、2；

1.下载 maven 、jdk 并配置好环境变量，详细步骤 略

2.下载本项目，在T‘SvnPwd4java’目录执行

```bash
mvn assembly:assembly
```

3.在‘TSvnPwd4java/targe’t目录下执行

```bash
java -jar TSvnPwd4java-1.0-SNAPSHOT-jar-with-dependencies.jar
```

效果图：

![](https://ftp.bmp.ovh/imgs/2020/11/cc15072258fc823c.png)


**项目需要频繁维护，希望大家支持**

**欢迎点赞项目，提交问题**

部分代码参考  [http://www.leapbeyond.com/ric/TSvnPD] 提供的源码，表示感谢

部分代码参考JNA官方提供的测试代码[https://github.com/java-native-access/jna/blob/master/contrib/platform/test/com/sun/jna/platform/win32/Crypt32Test.java]，表示感谢
