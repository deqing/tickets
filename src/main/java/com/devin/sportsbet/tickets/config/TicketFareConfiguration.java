package com.devin.sportsbet.tickets.config;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Setup Kie Container via DRL file which defined in application properties */
@Configuration
public class TicketFareConfiguration {

  @Value("${ticketFareRule}")
  private String drlFile;

  private static final KieServices kieServices = KieServices.Factory.get();

  @Bean
  public KieContainer kieContainer() {
    var kieFileSystem = kieServices.newKieFileSystem();
    kieFileSystem.write(ResourceFactory.newClassPathResource(drlFile));
    var kieBuilder = kieServices.newKieBuilder(kieFileSystem);
    kieBuilder.buildAll();
    var kieModule = kieBuilder.getKieModule();

    return kieServices.newKieContainer(kieModule.getReleaseId());
  }
}
