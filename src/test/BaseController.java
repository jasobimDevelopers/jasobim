package test;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import base.exception.BusinessException;
import base.exception.DatabaseException;
import base.exception.ValidationException;

/**
 * 基础控制器类
 * @author zj
 *
 */
public abstract class BaseController {

    private static final Logger LOGGER = Logger.getLogger(BaseController.class);

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    XaResult<T> handleUncaughtException(Exception ex) {			//系统异常
        LOGGER.error(ex.getMessage(), ex.getCause());
        return new XaResult<T>(ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public @ResponseBody
    XaResult<T> handleValidationException(ValidationException validationEx) {		//数据校验异常
        LOGGER.error(validationEx.getMessage(), validationEx.getCause());
        return new XaResult<T>(validationEx.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public @ResponseBody
    XaResult<T> handleBusinessException(BusinessException businessEx) {	//业务逻辑异常
        LOGGER.error(businessEx.getMessage(), businessEx.getCause());
        return new XaResult<T>(businessEx.getMessage());
    }

    @ExceptionHandler(DatabaseException.class)
    public @ResponseBody
    XaResult<T> handleValidationException(DatabaseException dbEx) {		//数据库操作异常
        LOGGER.error(dbEx.getMessage(), dbEx.getCause());
        return new XaResult<T>(dbEx.getMessage());
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public @ResponseBody
    XaResult<T> handleJSONConvertException(HttpMessageNotWritableException jsonEx) {	//JSON格式转换异常
        LOGGER.error(jsonEx.getMessage(), jsonEx.getCause());
        return new XaResult<T>("JSON格式转换异常");
    }
}
