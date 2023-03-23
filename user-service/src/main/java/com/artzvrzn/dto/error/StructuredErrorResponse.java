package com.artzvrzn.dto.error;

import com.artzvrzn.domain.Violation;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StructuredErrorResponse {
  private final String logref;
  private final List<Violation> violations;

  public StructuredErrorResponse(List<Violation> violations) {
    this.logref = "structured_error";
    this.violations = violations;
  }
}
