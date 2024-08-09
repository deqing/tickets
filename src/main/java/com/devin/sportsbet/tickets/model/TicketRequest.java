package com.devin.sportsbet.tickets.model;

public record TicketRequest(long transactionId, Customer[] customers) {}
