package sn.zeitune.oliveinsurancesettings.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UsernameNotFoundException e) {
        return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(
            ExceptionResponse.builder()
                .businessErrorCode(BusinessErrorCodes.USER_NOT_FOUND.getCode())
                .businessErrorDescription(BusinessErrorCodes.USER_NOT_FOUND.getDescription())
                .error(e.getMessage())
            .build()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleException(DataIntegrityViolationException e) {

        log.error(e.getMessage());
        boolean isDuplicateEmail = e.getMessage().contains("email");
        boolean isDuplicateUsername = e.getMessage().contains("username");
        if (isDuplicateEmail) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorCode(BusinessErrorCodes.DUPLICATE_EMAIL.getCode())
                                    .businessErrorDescription(BusinessErrorCodes.DUPLICATE_EMAIL.getDescription())
                                    .error(e.getMessage())
                                    .build()
                    );
        } else if (isDuplicateUsername) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorCode(BusinessErrorCodes.DUPLICATE_USERNAME.getCode())
                                    .businessErrorDescription(BusinessErrorCodes.DUPLICATE_USERNAME.getDescription())
                                    .error(e.getMessage())
                                    .build()
                    );
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ExceptionResponse.builder()
                                    .error(e.getMessage())
                                    .build()
                    );
        }
    }


    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ACCOUNT_LOCKED.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ACCOUNT_LOCKED.getDescription())
                                .error(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ACCOUNT_DISABLED.getCode())
                                .businessErrorDescription(BusinessErrorCodes.ACCOUNT_DISABLED.getDescription())
                                .error(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.BAD_CREDENTIALS.getCode())
                                .businessErrorDescription(BusinessErrorCodes.BAD_CREDENTIALS.getDescription())
                                .error(e.getMessage())
                                .build()
                );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException e) {
        Set<String> errors = new HashSet<>();
        e.getBindingResult().getFieldErrors().forEach(
                error -> errors.add(error.getField() + ": " + error.getDefaultMessage())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder().validationErrors(errors).build());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder().businessErrorCode(BusinessErrorCodes.NO_CODE.getCode())
                                .businessErrorDescription("Internal server error, please try again later")
                                .error(e.getMessage())
                                .build()
                );
    }
}