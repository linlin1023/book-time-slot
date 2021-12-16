package com.booktime.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class BookTimeRequest {

  @NotBlank(message = "The user id is required")
  private String userId;


  @NotBlank(message = "The day is required")
  private String day;


  @Max(value = 23, message = "The time is in range 0~23")
  @Min(value = 0, message = "The time is in range 0~23")
  private int time;

}
