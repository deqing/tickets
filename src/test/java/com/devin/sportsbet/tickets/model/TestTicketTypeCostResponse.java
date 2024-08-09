package com.devin.sportsbet.tickets.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestTicketTypeCostResponse {

  @Test
  void shouldNotAddTicketWhenQuantityIsZero() {
    var costResponse = new TicketTypeCostResponse();
    costResponse.addCost(TicketType.SENIOR, 0, 2.0);
    assertTrue(costResponse.getTickets().isEmpty());
  }

  @Test
  void shouldAddTickets() {
    var costResponse = new TicketTypeCostResponse();

    costResponse.addCost(TicketType.CHILDREN, 5, 7);
    costResponse.addCost(TicketType.TEEN, 2, 23);
    costResponse.calculateTotalCost();

    assertThat(costResponse.getTickets().size(), is(2));
    assertThat(costResponse.getTickets().getFirst().ticketType(), is(TicketType.CHILDREN));
    assertThat(costResponse.getTickets().getFirst().quantity(), is(5));
    assertThat(costResponse.getTickets().getFirst().totalCost(), is(7.0));
    assertThat(costResponse.getTickets().get(1).ticketType(), is(TicketType.TEEN));
    assertThat(costResponse.getTickets().get(1).quantity(), is(2));
    assertThat(costResponse.getTickets().get(1).totalCost(), is(23.0));

    assertThat(costResponse.getTotalCost(), is(30.0));
  }
}
