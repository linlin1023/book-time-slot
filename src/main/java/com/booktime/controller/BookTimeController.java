package com.booktime.controller;

import java.util.List;
import java.util.TreeMap;

import javax.validation.Valid;

import com.booktime.model.BookTimeRequest;
import com.booktime.model.Day;
import com.booktime.model.DayUtilizationReport;
import com.booktime.model.TimeSlot;
import com.booktime.service.BookTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
public class BookTimeController {

  BookTimeService bookTimeService;

  @Autowired
  public BookTimeController(BookTimeService bookTimeService){
    this.bookTimeService = bookTimeService;
  }

  @PostMapping
  public boolean bookTimeSlot(@RequestBody @Valid BookTimeRequest requestBody){
      Day day = Day.fromString(requestBody.getDay());
      if(day == null){
        return false;
      }
      return bookTimeService.bookTimeSlot(requestBody.getUserId(), day , requestBody.getTime());
  }

  @GetMapping("/reportbyuser/{userId}")
  public List<TimeSlot>  getTimeSlotsByUser(@PathVariable String userId){
    return bookTimeService.getTimeSlotsByUser(userId);
  }

  @GetMapping("/reportbyday/{day}")
  public DayUtilizationReport getDayUtilization(@PathVariable String day){
    TreeMap countsPerHour =  bookTimeService.getDayUtilization(Day.fromString(day));
    return new DayUtilizationReport(Day.fromString(day), countsPerHour);
  }


}
