
create table um_group (
  id int(11) not null auto_increment,
  group_name varchar(512) not null,
  group_type varchar(100) not null,
  created_user varchar(100) default null,
  created_time timestamp,
  primary key (id)
) engine=innodb ;

create table group_user (
  id int(11) not null auto_increment,
  group_id int(11) not null,
  user_name varchar(512) not null,
  primary key (id),
  FOREIGN KEY (group_id) REFERENCES um_group(id)
) engine=innodb;

create table group_role (
  id int(11) not null auto_increment,
  group_id int(11) not null,
  role_name varchar(512) not null,
  primary key (id),
  FOREIGN KEY (group_id) REFERENCES um_group(id)
) engine=innodb ;