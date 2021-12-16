package com.booktime.model;


import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DayUtilizationReport {
  private Day day;
  private TreeMap<Integer,Integer> count;
}
