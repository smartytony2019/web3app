

```sql

create table agent(
  id int primary key auto_increment,
  pid int comment '上级用户id',
  pusername varchar(100) comment '上级用户名',
  uid int comment '用户id',
  username varchar(100) comment '用户名'
  level int comment '层级'
) comment '代理'

```



https://www.cnblogs.com/cjsblog/p/14904861.html