package base.exception;

import org.apache.poi.ss.formula.functions.T;

import test.XaResult;


/**
 * 数据检测异常类
 * @author zj
 *
 */
public class ValidationException extends BaseException {
    
    private static final long serialVersionUID = 1L;

    public ValidationException() {
        super();
    }

    public ValidationException(String errorMsg) {
        super(errorMsg);
    }

    public ValidationException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public ValidationException(int errorCode, String errorMsg, XaResult<T> dataResult) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.dataResult = dataResult;
    }

    public ValidationException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }

    public ValidationException(int errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
    }

    public ValidationException(int errorCode, String errorMsg, XaResult<T> dataResult, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.dataResult = dataResult;
    }
}
