package com.my.spring.utils;
import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
public class DataWrapperDiy<T>  {
    private CallStatusEnum callStatus;
    private ErrorCodeEnum errorCode;
    private String errorInfo;
    private Integer notRead;
    private T data;
    private String token;

    // 用于分页结果
    private int numberPerPage;
    private int currentPage;
    private int totalNumber;
    private int totalPage;

    public DataWrapperDiy() {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getNumberPerPage() {
        return numberPerPage;
    }

    public void setNumberPerPage(int numberPerPage) {
        this.numberPerPage = numberPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return	"Code:" + this.callStatus + "\n" +
                "Error Code:" + this.errorCode+ "\n" +
                "Page :" + this.currentPage + "\n" +
                "Total Page :" + this.totalPage + "\n" +
                "Num per Page:" + this.numberPerPage + "\n" +
                "Total Num:" + this.totalNumber + "\n" ;
    }

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public Integer getNotRead() {
		return notRead;
	}

	public void setNotRead(Integer notRead) {
		this.notRead = notRead;
	}
}
