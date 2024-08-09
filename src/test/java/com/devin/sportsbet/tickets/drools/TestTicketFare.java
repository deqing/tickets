package com.devin.sportsbet.tickets.drools;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.devin.sportsbet.tickets.model.Customer;
import com.devin.sportsbet.tickets.model.TicketRequest;
import com.devin.sportsbet.tickets.model.TicketType;
import com.devin.sportsbet.tickets.model.TicketTypeCostResponse;
import org.droolsassert.DroolsAssert;
import org.droolsassert.DroolsSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@DroolsSession("rules/TieredPricing.drl")
public class TestTicketFare {

  @RegisterExtension public DroolsAssert drools = new DroolsAssert();

  @Test
  void costCalculation() {
    var request =
        new TicketRequest(
            1,
            new Customer[] {
              new Customer("Senior1", 65),
              new Customer("Adult1", 64),
              new Customer("Adult2", 18),
              new Customer("Teen1", 17),
              new Customer("Teen2", 11)
            });

    var response = new TicketTypeCostResponse();
    drools.setGlobal("response", response);
    drools.insertAndFire(request);

    assertThat(response.getTransactionId(), is(1L));

    assertThat(response.getTickets().getFirst().ticketType(), is(TicketType.ADULT));
    assertThat(response.getTickets().getFirst().totalCost(), is(2 * 25.0));

    assertThat(response.getTickets().get(1).ticketType(), is(TicketType.SENIOR));
    assertThat(response.getTickets().get(1).totalCost(), is(25.0 * 0.7));

    assertThat(response.getTickets().get(2).ticketType(), is(TicketType.TEEN));
    assertThat(response.getTickets().get(2).totalCost(), is(2 * 12.0));

    assertThat(response.getTotalCost(), is(2 * 25 + 25 * 0.7 + 2 * 12));
  }

  @Test
  void costCalculationBigFamily() {
    var request =
        new TicketRequest(
            2,
            new Customer[] {
              new Customer("Senior", 90),
              new Customer("Child1", 2),
              new Customer("Child2", 2),
              new Customer("Child3", 2)
            });

    var response = new TicketTypeCostResponse();
    drools.setGlobal("response", response);
    drools.insertAndFire(request);

    assertThat(response.getTickets().getFirst().ticketType(), is(TicketType.CHILDREN));
    assertThat(response.getTickets().getFirst().totalCost(), is(3 * 5.0 * 0.75));
  }

  @Test
  void costCalculationSmallFamily() {
    var request =
        new TicketRequest(
            3,
            new Customer[] {
              new Customer("Senior", 90), new Customer("Child1", 2), new Customer("Child2", 2)
            });

    var response = new TicketTypeCostResponse();
    drools.setGlobal("response", response);
    drools.insertAndFire(request);

    assertThat(response.getTickets().getFirst().ticketType(), is(TicketType.CHILDREN));
    assertThat(response.getTickets().getFirst().totalCost(), is(2 * 5.0));
  }
}
