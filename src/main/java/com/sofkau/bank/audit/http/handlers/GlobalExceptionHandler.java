package com.sofkau.bank.audit.http.handlers;

import com.sofkau.bank.audit.exceptions.OperationNotCommitedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ProblemDetail> handleBaseException(Exception e) {
        return Mono.just(ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ProblemDetail> handleRuntimeException(RuntimeException e) {
        return Mono.just(ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(OperationNotCommitedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ProblemDetail> handleOperationNotCommitedException(OperationNotCommitedException e) {
        return Mono.just(ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}
