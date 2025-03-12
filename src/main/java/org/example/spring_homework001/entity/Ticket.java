package org.example.spring_homework001.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Ticket {

    private int ticketId;
    private String passengerName;
    private String travelDate;
    private String sourceStation;
    private String destinationStation;
    private double price;
    private Boolean paymentStatus;
    private String ticketStatus;
    private int seatNumber;
    private static int countId = 0;

    public Ticket(String passengerName, String travelDate, String sourceStation, String destinationStation, double price, Boolean paymentStatus, String ticketStatus, int seatNumber) {
        this.ticketId = ++countId;
        this.passengerName = passengerName;
        this.travelDate = travelDate;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.ticketStatus = ticketStatus;
        this.seatNumber = seatNumber;
    }

}
