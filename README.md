# zjh
3D

项目封板于2018年5月
个人仅完成项目3d扫描呈现部分

框架搭建说明：
1、本次开发框架使用Springmvc+Mybatis框架，与之前项目配置的框架相比，有略微差别，配置了校验器(主要在注册的时候作为校验的一种方式)、增强(AOP)；
2、除了Mapper曾采用批量包扫描的方式，框架中其他层采用全“注解”方式开发，包括Controller、Service；
3、关于框架中的“事务控制”部分，是在Service层上完成的，与以往不同的是，本次开发为了体现事务控制的作用，规定每个完整的业务均在Service层中完成，Controller层只负责为Service层提供参数以及接受处理Service层返回的结果；


开发规范说明：
1、所有类、方法必须有注释，注释方式参看已有的User、UserMapper、UserService、UserController类；
2、所有静态页面均存放在WEB-INF目录下(css、js、images资源文件可以放在WebRoot下)，同时使用文件夹对页面进行分类；
3、每个数据表必须对应一个mapper.xml/mapper.java，单表操作的sql语句必须写在其对应的mapper.xml中，涉及到多表操作的时候，自行判断本次查询的主表，将sql语句仔仔主表对应的mapper.xml中；
4、所有有关保存的操作，一定要注意封装保存对象，并且确保对象参数的完整性，比如日期和状态值，这些很可能都需要后台代码设置，并不是从前台传值过来的。

框架修改说明：
1、SqlMapConfig.xml中添加对com.zttx.vo包的扫描；
