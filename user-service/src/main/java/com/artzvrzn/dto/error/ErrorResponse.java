package com.artzvrzn.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
  private final String logref;
  private final String message;

  public ErrorResponse(String message) {
    this.logref = "error";
    this.message = message;
  }
}
