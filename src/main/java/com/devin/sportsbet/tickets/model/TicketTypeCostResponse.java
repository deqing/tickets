package com.devin.sportsbet.tickets.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class TicketTypeCostResponse {
  @Setter private long transactionId;

  private final List<TicketTypeCost> tickets = new ArrayList<>();

  private double totalCost;

  public void addCost(TicketType ticketType, int quantity, double cost) {
    if (quantity > 0) {
      tickets.add(new TicketTypeCost(ticketType, quantity, cost));
    }
  }

  public void calculateTotalCost() {
    tickets.forEach(ticket -> totalCost += ticket.totalCost());
  }
}
