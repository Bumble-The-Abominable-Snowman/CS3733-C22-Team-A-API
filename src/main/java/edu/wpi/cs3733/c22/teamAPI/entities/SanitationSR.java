package edu.wpi.cs3733.c22.teamAPI.entities;

import lombok.Data;

@Data
public class SanitationSR {

  private String sanitationType;
  private String toLocation;
  private String employeeAssigned;
  private String comments;
}
