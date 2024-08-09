package com.devin.sportsbet.tickets.controller;

import com.devin.sportsbet.tickets.model.TicketRequest;
import com.devin.sportsbet.tickets.model.TicketTypeCostResponse;
import com.devin.sportsbet.tickets.service.TicketService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/costs")
public class TicketController {

  private TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @PostMapping
  public Mono<TicketTypeCostResponse> calculateCosts(@RequestBody TicketRequest request) {
    return Mono.just(ticketService.getCost(request));
  }
}
