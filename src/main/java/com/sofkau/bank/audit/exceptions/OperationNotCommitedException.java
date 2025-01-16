package com.sofkau.bank.audit.exceptions;

public class OperationNotCommitedException extends RuntimeException {
  public OperationNotCommitedException(String message) {
    super(message);
  }
}
