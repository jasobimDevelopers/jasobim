package base.exception;

import org.apache.poi.ss.formula.functions.T;

import test.XaResult;


/**
 * 异常处理基础类
 * @author zj
 *
 */
public abstract class BaseException extends Exception {

    private static final long serialVersionUID = 1L;

    protected int errorCode;

    protected XaResult<T> dataResult;

    protected BaseException() {
        super();
    }

    protected BaseException(String errorMsg) {
        super(errorMsg);
    }

    protected BaseException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    protected BaseException(int errorCode, String errorMsg, XaResult<T> dataResult) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.dataResult = dataResult;
    }

    protected BaseException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }

    protected BaseException(int errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
    }

    protected BaseException(int errorCode, String errorMsg, XaResult<T> dataResult, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.dataResult = dataResult;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public XaResult<T> getDataResult() {
        return dataResult;
    }

    public void setDataResult(XaResult<T> dataResult) {
        this.dataResult = dataResult;
    }
}
