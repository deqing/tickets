package com.devin.sportsbet.tickets.service;

import com.devin.sportsbet.tickets.model.TicketRequest;
import com.devin.sportsbet.tickets.model.TicketTypeCostResponse;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketService {

  private KieContainer kieContainer;

  public TicketService(KieContainer kieContainer) {
    this.kieContainer = kieContainer;
  }

  /**
   * @param request contains a transaction that includes customers' information
   * @return movie ticket types and total costs
   */
  public TicketTypeCostResponse getCost(TicketRequest request) {
    log.info("Received getCost request, txn id: " + request.transactionId());

    var response = new TicketTypeCostResponse();
    var kieSession = kieContainer.newKieSession();
    kieSession.setGlobal("response", response);
    kieSession.insert(request);
    kieSession.fireAllRules();
    kieSession.dispose();

    log.info(
        String.format(
            "Generated %d tickets for txn %d",
            response.getTickets().size(), response.getTransactionId()));
    return response;
  }
}
