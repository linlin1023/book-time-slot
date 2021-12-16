package com.booktime.model;


public enum Day {
  MONDAY, TUESDAY, WEDNESDAY,THURSDAY,FRIDAY, SATURDAY,SUNDAY;

  public static  Day fromString(String dayStr){
    return Day.valueOf(dayStr.toUpperCase());
  }
}
