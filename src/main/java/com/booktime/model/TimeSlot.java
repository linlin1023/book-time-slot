package com.booktime.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TimeSlot {
  private Day day;
  private int time; //0 ~ 23



  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    TimeSlot timeSlot = (TimeSlot) o;
    return time == timeSlot.time && day == timeSlot.day;
  }

  @Override
  public int hashCode() {
    return Objects.hash(day, time);
  }
}
