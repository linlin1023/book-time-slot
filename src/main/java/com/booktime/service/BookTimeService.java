package com.booktime.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.booktime.model.Day;
import com.booktime.model.TimeSlot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class BookTimeService {
  HashMap<String, List<TimeSlot>> timeSlotsByUserId = new HashMap<>();
  HashMap<TimeSlot, List<String>> userIdsByTimeSlot = new HashMap<>();

  public boolean bookTimeSlot(String userId, Day day, int time) {
      TimeSlot timeSlot = new TimeSlot(day, time);

      //
      if(isOverbooked(timeSlot) ||isDuplicateBookedDay(userId, day)){
        return false;
      }

      List<TimeSlot> timeSlots = timeSlotsByUserId.get(userId);
      List<String> users = userIdsByTimeSlot.get(timeSlot);

      users.add(userId);
      timeSlots.add(timeSlot);
      return true;

  }

  public List<TimeSlot> getTimeSlotsByUser(String userId) {
    return timeSlotsByUserId.get(userId);
  }

  public TreeMap<Integer,Integer> getDayUtilization(Day day) {
    Set<TimeSlot> timeSlots = userIdsByTimeSlot.keySet();
    final TreeMap<Integer,Integer> countsPerHour = new TreeMap();

    timeSlots.stream().filter(timeSlot -> timeSlot.getDay().equals(day)).forEach(
        timeSlot -> {
            int time = timeSlot.getTime();
            int bookings = userIdsByTimeSlot.get(timeSlot).size();
            Integer count = countsPerHour.get(time);
            //accumulate bookings
            if(count == null ){
              countsPerHour.put(time, 0 + bookings);
            }else{
              countsPerHour.put(time, count + bookings);
            }
        }
    );
    return countsPerHour;
  }

  private boolean isOverbooked(TimeSlot timeSlot) {
    List<String> users = userIdsByTimeSlot.get(timeSlot);

    if(users != null && users.size() >= 8 ){
      return true;
    }
    if(users == null) {
      initializeUserList(timeSlot);
    }
    return false;
  }


  private void initializeUserList(TimeSlot timeSlot){
    userIdsByTimeSlot.put(timeSlot, new LinkedList<>());
  }


  private void initializeTimeSlotList(String userId){
    timeSlotsByUserId.put(userId,new LinkedList<>());
  }


  private boolean isDuplicateBookedDay(String userId, Day day) {
    List<TimeSlot> timeSlots = timeSlotsByUserId.get(userId);
    if(timeSlots == null){
      initializeTimeSlotList(userId);
      return false;
    }

    Set<Day> daysBooked = timeSlots.stream().map(timeSlot -> timeSlot.getDay()).collect(Collectors.toSet());
    if(daysBooked.contains(day)){
      return true;
    }
    return false;
  }

}
