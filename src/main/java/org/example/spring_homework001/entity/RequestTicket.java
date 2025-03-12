package org.example.spring_homework001.entity;

import lombok.Data;

@Data
public class RequestTicket {
    private String passengerName;
    private String travelDate;
    private String sourceStation;
    private String destinationStation;
    private double price;
    private Boolean paymentStatus;
    private String ticketStatus;
    private String seatNumber;
}
