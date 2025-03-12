package org.example.spring_homework001.controller;

import org.example.spring_homework001.entity.RequestTicket;
import org.example.spring_homework001.entity.Ticket;
import org.example.spring_homework001.entity.TicketStatus;
import org.example.spring_homework001.response.ApiResponse;
import org.example.spring_homework001.response.FoundResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    List<Ticket> tickets=new ArrayList<>();
    public TicketController() {
        tickets.add(
                new Ticket("Kok dara", "20-02-2022", "PP", "TK", 200d, true, "BOOKED", "100B")
        );
        tickets.add(
                new Ticket("Kok Bona", "20-02-2023", "PP", "PVH", 200d, false, "BOOKED", "120B")
        );
    }

    @PostMapping("")
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

    @GetMapping("/{ticket-id}")
    public ResponseEntity<?> getTicketByID(@PathVariable("ticket-id") int idTicket) {
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
        FoundResponse notFountResponse=new FoundResponse(false,"No Ticket found with Id:"+idTicket,HttpStatus.NOT_FOUND, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFountResponse);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> searchTicketsByType(@RequestParam TicketStatus ticketStatus, @RequestParam String travelDate) {
        for (Ticket ticket : tickets) {
            if (ticket.getTravelDate().equals(travelDate) && ticket.getTicketStatus().equals(ticketStatus.toString())) {
                ApiResponse<Ticket> apiResponse = new ApiResponse<>(true, "string",HttpStatus.CONTINUE, ticket, LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
            }

        }
        ApiResponse<Ticket> notfound=new ApiResponse<>(true,"Ticket filtered successfully",HttpStatus.OK,null,LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notfound);
    }
    @DeleteMapping("/{ticket-id}")
    public ResponseEntity<?> deleteTicket(@PathVariable("ticket-id") int idTicket) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == idTicket) {
                tickets.remove(ticket);
                FoundResponse foundResponse=new FoundResponse(true,"Ticket deleted successfully", HttpStatus.OK,LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(foundResponse);
            }
        }
        FoundResponse notfound=new FoundResponse(false,"No ticket found with ID:"+idTicket, HttpStatus.NOT_FOUND, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notfound);
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchTickets(@RequestParam String passengerName) {
        List<Ticket> storeTicket = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getPassengerName().equals(passengerName)) {
                storeTicket.add(ticket);
            }
        }

        if (!storeTicket.isEmpty()) {
            ApiResponse<List<Ticket>> apiResponse = new ApiResponse<>(
                    true, "Ticket search successfully", HttpStatus.OK, storeTicket, LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } else {
            FoundResponse notFoundResponse = new FoundResponse(
                    false, "No ticket found with name: " + passengerName, HttpStatus.NOT_FOUND, LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllTickets(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "5") int limit) {

        // Handle invalid offset values
        if (offset < 0 || limit <= 0) {
            return ResponseEntity.badRequest().body("Offset must be >= 0 and limit must be > 0");
        }

        int start = Math.min(offset, tickets.size());
        int end = Math.min(start + limit, tickets.size());

        List<Ticket> paginatedTickets = tickets.subList(start, end);

        ApiResponse<List<Ticket>> response = new ApiResponse<>(
                true, "Tickets retrieved successfully", HttpStatus.OK, paginatedTickets, LocalDateTime.now());

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{ticket-id}")
    public ResponseEntity<?> updateTicket(@PathVariable("ticket-id") Integer id, @RequestBody RequestTicket requestTicket) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == id) {
                ticket.setPassengerName(requestTicket.getPassengerName());
                ticket.setTicketStatus(requestTicket.getTicketStatus());
                ticket.setSeatNumber(requestTicket.getSeatNumber());
                ticket.setTravelDate(requestTicket.getTravelDate());
                ticket.setSourceStation(requestTicket.getSourceStation());
                ticket.setDestinationStation(requestTicket.getDestinationStation());
                ticket.setPrice(requestTicket.getPrice());
                ticket.setPaymentStatus(requestTicket.getPaymentStatus());
                ticket.setTicketStatus(requestTicket.getTicketStatus());
                ticket.setSeatNumber(requestTicket.getSeatNumber());
                ApiResponse<Ticket> apiResponse=new ApiResponse<>(true,"Successfully ",HttpStatus.OK,ticket,LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);


            }
        }
        return null;
    }






}
