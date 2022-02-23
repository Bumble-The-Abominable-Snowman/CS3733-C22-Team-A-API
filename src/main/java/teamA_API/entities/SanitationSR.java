package teamA_API.entities;

import lombok.Data;

@Data
public class SanitationSR {

  private String sanitationType;
  private String toLocation;
  private String employeeAssigned;
  private String comments;
}
