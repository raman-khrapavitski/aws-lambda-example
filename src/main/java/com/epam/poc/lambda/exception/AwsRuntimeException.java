package com.epam.poc.lambda.exception;

/**
 * Root runtime exception.
 * <p>
 * Date: 2018-02-15
 *
 * @author Raman_Khrapavitski
 */
public class AwsRuntimeException extends RuntimeException {

    /**
     * Constructs a new aws runtime exception.
     *
     * @param cause the cause
     */
    public AwsRuntimeException(Throwable cause) {
        super(cause);
    }
}
