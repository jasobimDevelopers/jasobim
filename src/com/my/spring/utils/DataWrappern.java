package com.my.spring.utils;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
public class DataWrappern<S,T,O>  {
    private CallStatusEnum callStatus;
    private ErrorCodeEnum errorCode;
    private T data;
    private S page;
    private O others;
    private String token;


    public DataWrappern() {
        callStatus = CallStatusEnum.SUCCEED;
        errorCode = ErrorCodeEnum.No_Error;
    }

    public CallStatusEnum getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(CallStatusEnum callStatus) {
        this.callStatus = callStatus;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode;
        if (!errorCode.equals(ErrorCodeEnum.No_Error)) {
            this.callStatus = CallStatusEnum.FAILED;
        }
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
    public S getPage() {
        return page;
    }

    public void setPage(S page) {
        this.page = page;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

   

    @Override
    public String toString() {
        return	"Code:" + this.callStatus + "\n" +
                "Error Code:" + this.errorCode+ "\n";
    }

	public O getOthers() {
		return others;
	}

	public void setOthers(O others) {
		this.others = others;
	}
}
