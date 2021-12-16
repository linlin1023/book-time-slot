package com.booktime.controller;

import java.util.Arrays;
import java.util.TreeMap;

import com.booktime.model.BookTimeRequest;
import com.booktime.model.Day;
import com.booktime.model.DayUtilizationReport;
import com.booktime.model.TimeSlot;
import com.booktime.service.BookTimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TestBookTimeController {

  BookTimeController bookTimeController;

  @Mock
  BookTimeService bookTimeService;

  //test data
  private Day day = Day.FRIDAY;

  private Integer time = 18;

  private String userId = "user001";

  private TreeMap utilization = new TreeMap<>();

  private TimeSlot timeSlot = new TimeSlot(day, time);

  @BeforeEach
  void setUp() {
    bookTimeController = new BookTimeController(bookTimeService);
  }

  @Test
  @DisplayName("test book a time slot")
  public void testBookTimeSlot() {
    Mockito.when(bookTimeService.bookTimeSlot(userId, day, 18)).thenReturn(true);
    boolean result = bookTimeController.bookTimeSlot(new BookTimeRequest(userId, day.toString(), time));
    assertEquals(result, true);
  }

  @Test
  @DisplayName("Test get report by user")
  public void testGetTimeSlotsByUser() {
    Mockito.when(bookTimeService.getTimeSlotsByUser(userId)).thenReturn(Arrays.asList(timeSlot));
    TimeSlot timeSlotGet = bookTimeController.getTimeSlotsByUser(userId).get(0);
    assertEquals(timeSlot, timeSlotGet);
  }

  @Test
  @DisplayName("Test get day utilization")
  public void testGetReportByDay() {
    utilization.put(18, 1);
    Mockito.when(bookTimeService.getDayUtilization(day)).thenReturn(utilization);
    DayUtilizationReport report = bookTimeController.getDayUtilization(day.toString());
    assertEquals(report.getCount(), utilization);
  }
}