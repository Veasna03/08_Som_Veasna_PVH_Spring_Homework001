package org.example.spring_homework001.controller;

import org.example.spring_homework001.entity.RequestTicket;
import org.example.spring_homework001.entity.Ticket;
import org.example.spring_homework001.entity.TicketStatus;
import org.example.spring_homework001.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TicketController {

    List<Ticket> tickets=new ArrayList<>();

    @PostMapping("/tickets")
    public ResponseEntity<?> addTicket(@RequestBody RequestTicket requestTicket) {
        Ticket newTicket = new Ticket(
                requestTicket.getPassengerName(),
                requestTicket.getTravelDate(),
                requestTicket.getSourceStation(),
                requestTicket.getDestinationStation(),
                requestTicket.getPrice(),
                requestTicket.getPaymentStatus(),
                requestTicket.getTicketStatus(),
                requestTicket.getSeatNumber()
        );

        tickets.add(newTicket);

        ApiResponse<Ticket> apiResponse = new ApiResponse<>(true, "Successfully", HttpStatus.OK, newTicket, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
  //get by id

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketByID(@PathVariable("id") int idTicket) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == idTicket) {
                ApiResponse<Ticket> apiResponse = new ApiResponse<>(
                        true,
                        "Successfully retrieved ticket",
                        HttpStatus.OK,
                        ticket,
                        LocalDateTime.now()
                );
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(
                        false,
                        "Ticket not found",
                        HttpStatus.NOT_FOUND,
                        null,
                        LocalDateTime.now()
                ));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> searchTicketsByType(@RequestParam TicketStatus ticketStatus, @RequestParam String name) {
        for (Ticket ticket : tickets) {
            if (ticket.getPassengerName().equals(name) && ticket.getTicketStatus().equals(ticketStatus.toString())) {
                ApiResponse<Ticket> apiResponse = new ApiResponse<>(true, "Successfully retrieved ticket", HttpStatus.OK, ticket, LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
            }
        }
        return null;
    }
    @DeleteMapping("/tickets/{ticket-id}")
    public ResponseEntity<?> deleteTicket(@PathVariable("ticket-id") int idTicket) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == idTicket) {
                tickets.remove(ticket);
                ApiResponse<Ticket> apiResponse = new ApiResponse<>(true,"Successfully deleted ticket", HttpStatus.OK, ticket, LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
            }
        }
        return null;
    }




}
