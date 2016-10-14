Feature: 登录,验证账号密码正确则登录成功
  """
  登录,验证账号密码正确则登录成功
  """
  Scenario: 账号密码都正确登录成功
   Given 存在用户名为test密码为123456的用户
    When 输入用户名test
    And 输入密码123456
    And 点击登录按钮
    Then 登录成功

  Scenario: 账号不存在登录失败
    Given 存在用户名为test密码为123456的用户
    When 输入用户名notExist
    And 输入密码123456
    And 点击登录按钮
    Then 登录失败

  Scenario: 账号存在密码为空登录失败
    Given 存在用户名为test密码为123456的用户
    When 输入用户名test
    And 点击登录按钮
    Then 登录失败

  Scenario: 账号存在密码错误登录失败
    Given 存在用户名为test密码为123456的用户
    When 输入用户名test
    And 输入密码65321
    And 点击登录按钮
    Then 登录失败