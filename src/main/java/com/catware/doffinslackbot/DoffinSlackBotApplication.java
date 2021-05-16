package com.catware.doffinslackbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DoffinSlackBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoffinSlackBotApplication.class, args);
    }

//    @Autowired
//    SlackService slackService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("HELLO");
//        TenderDto t = new TenderDto("name", "https://google.com",
//                "publishedBy", "2021-01-01", "2021-01-31", "doffin Reference");
//        slackService.publishTenders(Collections.singletonList(t));
//    }
}
