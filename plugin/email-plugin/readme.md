# email-plguin

邮件报警插件，借助comons-email封装了一个简易的、支持html格式的邮件报警器

## I. 使用姿势

### 引入依赖

```xml
<dependency>
    <groupId>com.hust.hui.alarm</groupId>
    <artifactId>email-plugin</artifactId>
    <version>1.0</version>
</dependency>
```

### 设置配置信息

alarm.properties 文件，新增如下配置

```sh
emailHost=smtp.163.com  # 发送邮件帐号的服务器
emailPort=25 # 端口
emailUname=xhhuiblog # 发送帐号的用户名 
emailToken= # token 
emailFrom=xhhuiblog@163.com # 发送邮件的帐号
```

说明：如需正常使用，请确保上面的参数无误


### 使用

报警使用方式和其他的没有区别，报警规则key设置为 EMAIL 即可