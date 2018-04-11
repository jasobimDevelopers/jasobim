package com.my.spring.enums;
import java.io.Serializable;
public enum ErrorCodeEnum implements Serializable {
    No_Error("No Error!", 0),
    Error("Unknown Error!", 1),
    Empty_Inputs("Empty Inputs Error!", 2),
    User_Existed("User Name Existed!",3),
    User_Not_Existed("User Does not Existed!",4),
    Password_Error("Password Error",5),
    User_Not_Logined("User Does not login",6),
    AUTH_Error("Auth Error",7),
    //////项目错误
    Project_Not_Existed("Project Does not Existed!",8),
    Target_Not_Existed("Target Does not Existed!",9),
    Password_Not_Fit("密码错误!",10),
    Phone_Format_Error("手机格式错误!",11),
    Phone_Time_Error("验证码超时!",12),
    Phone_Code_Error("验证码不匹配!",13),
    Phone_Existed_Error("手机号码已注册！",14)
    ;
    private String label;
    private Integer code;
    ErrorCodeEnum() {
    }
    ErrorCodeEnum(String label, Integer code) {
        this.label = label;
        this.code = code;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public int getCode() {
        return code;
    }
    @Override
    public String toString() {
        return code.toString();
    }
    public static ErrorCodeEnum parse(int code) {
        for (ErrorCodeEnum theEnum : ErrorCodeEnum.values()) {
            if (theEnum.getCode() == code) {
                return theEnum;
            }
        }
        return No_Error;
    }
}

