package com.example.reservasi.services;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.reservasi.model.ReservasiModel;

import java.util.*;
import java.time.*;

@Service
public class ReservasiService {
   private Map<LocalDate, List<ReservasiModel>> reservations = new HashMap<>();

   public ResponseEntity<?> addReservation(LocalDate date, ReservasiModel reservation) {
       List<ReservasiModel> reservationsForDate = reservations.getOrDefault(date, new ArrayList<>());
       if (reservationsForDate.size() < 2) { // Batas maksimum 2 customer per hari
           reservationsForDate.add(reservation);
           reservations.put(date, reservationsForDate);
             return ResponseEntity.ok().body("Reservasi berhasil ditambahkan");
       } else {
         return ResponseEntity.badRequest().body("Reservasi penuh");
       }
   }

  
   public ResponseEntity<?> getReservationsForNextWeek() {
      LocalDate today = LocalDate.now();
      List<String> seminggu = new ArrayList<>();

      for (int i = 0; i <= 6; i++) {
          LocalDate date = today.plusDays(i);
          if (date.getDayOfWeek() == DayOfWeek.WEDNESDAY || date.getDayOfWeek() == DayOfWeek.FRIDAY) {
              seminggu.add(date.getDayOfWeek().toString() + ": Libur");
          } else {
              List<ReservasiModel> reservationsForDate = reservations.getOrDefault(date, new ArrayList<>());
              if (reservationsForDate.isEmpty()) {
                  seminggu.add(date.getDayOfWeek().toString() + ": Tidak ada reservasi");
              } else {
                  StringBuilder sb = new StringBuilder(date.getDayOfWeek().toString() + ": ");
                  for (ReservasiModel reservation : reservationsForDate) {
                      sb.append(reservation.getCustomerName()).append(", ");
                  }
                  sb.setLength(sb.length() - 2);
                  seminggu.add(sb.toString());
              }
          }
      }
      return ResponseEntity.ok().body(seminggu);
  }
     
}
