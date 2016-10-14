CREATE TABLE IF NOT EXISTS test_user(
  name VARCHAR PRIMARY KEY,
  password VARCHAR(32) NOT NULL
);

insert into test_user(name,password)
values ('jedi','abcmd5')


