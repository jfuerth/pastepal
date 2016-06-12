package net.bluecow.pastepal.service;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class PastePalMain {

  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder(PastePalMain.class).headless(false).run(args);
  }
}