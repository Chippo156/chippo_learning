package org.learning.dlearning_backend.exception;

import lombok.Getter;

@Getter

public class AppException extends RuntimeException{

        private final ErrorCode errorCode;

        public AppException(ErrorCode errorCode) {
            super(errorCode.getMessage());
            this.errorCode = errorCode;
        }
}