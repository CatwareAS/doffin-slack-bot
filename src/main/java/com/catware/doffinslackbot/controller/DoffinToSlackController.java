package com.catware.doffinslackbot.controller;

import com.catware.doffinslackbot.dto.TenderDto;
import com.catware.doffinslackbot.service.InputService;
import com.catware.doffinslackbot.service.OutputService;
import com.catware.doffinslackbot.service.PersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
@RestController
@RequestMapping("main")
public class DoffinToSlackController {

    Logger log = LoggerFactory.getLogger(DoffinToSlackController.class);

    private final InputService inputService;
    private final PersistenceService persistenceService;
    private final OutputService outputService;

    @Value("${app.doffin.url}")
    private String doffinUrl;

    public DoffinToSlackController(InputService inputService, PersistenceService persistenceService, OutputService outputService) {
        this.inputService = inputService;
        this.persistenceService = persistenceService;
        this.outputService = outputService;
    }

    @GetMapping("synchronize")
    public ResponseEntity<String> fetchResultsAndPublishToSlack() throws IOException {
        log.info("Started fetchResultsAndPublishToSlack");
        log.info("Fetching data from Doffin, url: {}", doffinUrl);
        List<TenderDto> tendersFromDoffin = inputService.fetchLastTenders(new URL(doffinUrl));
        log.info("Finding new tenders since last time and storing them in Firestore");
        List<TenderDto> newTendersSinceLastCheck = persistenceService.filterNewTendersUpdateExisting(tendersFromDoffin);
        if (newTendersSinceLastCheck.isEmpty()) {
            log.info("No changes since last run, nothing to publish in Slack");
        } else {
            log.info("Publishing new tenders in Slack");
            outputService.publishTenders(newTendersSinceLastCheck);
        }
        log.info("Finished fetchResultsAndPublishToSlack");
        return ResponseEntity.ok().build();
    }
}
