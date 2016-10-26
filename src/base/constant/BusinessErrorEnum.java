package base.constant;


/**
 * 业务逻辑错误，错误码范围：12000-12999
 */
public enum BusinessErrorEnum implements ErrorCode {

    NUll_DATA_EMPTY(12999, "数据错误"),
    
    USER_EXIST(12008, "用户已存在");

    int errorCode;

    String errorMsg;

    BusinessErrorEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
