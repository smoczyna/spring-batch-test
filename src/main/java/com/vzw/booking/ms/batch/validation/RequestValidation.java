package com.vzw.booking.ms.batch.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import com.mysql.jdbc.StringUtils;
import com.vzw.vlf.lib.logger.VlfLogger;

/**
 * <h1>RequestValidation</h1> RequestValidation contains the validations for the
 * request object. There is one validation to assure that the operand is
 * supplied and non-blank.
 * <p>
 */
@Component
public class RequestValidation {

    /**
     * Inject the logger.
     */
    @Autowired
    private VlfLogger logger;

    private static final String MISSING_OPERAND = "Validation failed -- Missing operand";

//    public boolean validate(TemplateRequest request) {
//
//        boolean rc = true;
//        if (StringUtils.isNullOrEmpty(request.getOperand())) {
//            rc = false;
//            this.logger.write(Constants.VALIDATION, Constants.VALIDATION, Severity.ERROR,
//                    MISSING_OPERAND, LogLevel.ERROR);
//        }
//        return rc;
//    }

}
