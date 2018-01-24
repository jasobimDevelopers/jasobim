package com.my.spring.controller;

public class AccountController {
	  /// <summary>
    /// 力软登录用户key
    /// 要与<seealso cref="OperatorProvider"/>中的LoginUserKey一致
    /// </summary>
    private static String LoginUserKey = "Learun_LoginUserKey_2016_V6.1";
    /// <summary>
    /// 力软主题风格cookie名
    /// </summary>
    private static String UiThemeKey = "learn_UItheme";
    /// <summary>
    /// IdentityServer认证接口
    /// </summary>
    private static String AuthorizationEndpoint = "http://192.168.2.53:8020/connect/authorize";
    /// <summary>
    /// IdentityServer用户信息接口
    /// </summary>
    private static String UserinfoEndpoint = "http://192.168.2.53:8020/connect/userinfo";
    /// <summary>
    /// 客户端Id
    /// </summary>
    private static String ClientId = "mvc";
    /// <summary>
    /// 授权后的回调地址，要与IdentityServer端定义的客户端的回调地址一致
    /// </summary>
    private static String  CallbackUrl = "http://localhost:4066/account/callback";
    
    /// <summary>
    /// 登录
    /// </summary>
    /// <returns></returns>
   
}
