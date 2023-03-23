package com.artzvrzn.exception;

import com.artzvrzn.domain.Violation;
import java.util.List;
import lombok.Getter;

@Getter
public class ValidationException extends IllegalArgumentException {
  private final List<Violation> violations;

  public ValidationException(List<Violation> violations) {
    this.violations = violations;
  }
}
