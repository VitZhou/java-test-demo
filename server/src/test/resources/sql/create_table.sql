CREATE TABLE IF NOT EXISTS test_user(
  name VARCHAR PRIMARY KEY,
  password VARCHAR(32) NOT NULL
);

insert into test_user(name,password)
SELECT 'jedi', 'abcmd5' FROM DUAL WHERE NOT EXISTS(SELECT * FROM test_user WHERE name = 'jedi')


