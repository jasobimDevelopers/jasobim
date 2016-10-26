package base.exception;

import org.apache.poi.ss.formula.functions.T;

import test.XaResult;

/**
 * 数据库异常类
 * @author zj
 *
 */
public class DatabaseException extends BaseException {
    
    private static final long serialVersionUID = 1L;

    public DatabaseException() {
        super();
    }

    public DatabaseException(String errorMsg) {
        super(errorMsg);
    }

    public DatabaseException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public DatabaseException(int errorCode, String errorMsg, XaResult<T> dataResult) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.dataResult = dataResult;
    }

    public DatabaseException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }

    public DatabaseException(int errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
    }

    public DatabaseException(int errorCode, String errorMsg, XaResult<T> dataResult, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.dataResult = dataResult;
    }
}
