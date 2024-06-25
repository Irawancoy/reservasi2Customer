package com.example.reservasi.controller;

import org.springframework.web.bind.annotation.*;
import com.example.reservasi.model.ReservasiModel;
import com.example.reservasi.services.ReservasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.*;

@RestController
@RequestMapping("/reservasi")
public class ReservasiController {

      @Autowired
      private ReservasiService reservasiService;
   
      //post reservasi
      @PostMapping(value = "/add")
      public ResponseEntity<?> addReservasi(@RequestBody ReservasiModel reservasi) {
         LocalDate date = LocalDate.now();
         return reservasiService.addReservation(date, reservasi);

         
      }

      
      //get reservasi semiinggu
      @GetMapping("/getWeek")
      public ResponseEntity<?> getReservasiWeek() {
         return reservasiService.getReservationsForNextWeek();
      }

      


}
