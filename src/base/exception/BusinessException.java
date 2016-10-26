package base.exception;

import org.apache.poi.ss.formula.functions.T;

import base.constant.BusinessErrorEnum;
import test.XaResult;
/**
 * 业务异常类
 * @author zj
 *
 */
public class BusinessException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    public BusinessException() {
        super();
    }

    public BusinessException(String errorMsg) {
        super(errorMsg);
    }

    public BusinessException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public BusinessException(int errorCode, String errorMsg, XaResult<T> dataResult) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.dataResult = dataResult;
    }

    public BusinessException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }

    public BusinessException(int errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
    }

    public BusinessException(int errorCode, String errorMsg, XaResult<T> dataResult, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.dataResult = dataResult;
    }

    public  static BusinessException getBusinessException(BusinessErrorEnum businessErrorEnum)
    {
       return new BusinessException(businessErrorEnum.getErrorCode(),businessErrorEnum.getErrorMsg());
    }


}
