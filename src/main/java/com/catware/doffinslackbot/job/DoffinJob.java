package com.catware.doffinslackbot.job;

import com.catware.doffinslackbot.TenderDto;
import com.catware.doffinslackbot.dao.TendersDao;
import com.catware.doffinslackbot.service.DoffinParser;
import com.catware.doffinslackbot.service.SlackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class DoffinJob {

    private final DoffinParser doffinParser;
    private final TendersDao tendersDao;
    private final SlackService slackService;

    @Value("${app.doffin.url}")
    private String doffinUrl;

    Logger log = LoggerFactory.getLogger(DoffinJob.class);

    public DoffinJob(DoffinParser doffinParser, TendersDao tendersDao, SlackService slackService) {
        this.doffinParser = doffinParser;
        this.tendersDao = tendersDao;
        this.slackService = slackService;
    }

    @Scheduled(cron = "${app.doffin.polling.interval}")
    public void fetchResultsAndPublishToSlack() throws IOException {
        List<TenderDto> tenderDtos = doffinParser.parseUrl(new URL(doffinUrl));
        List<TenderDto> newTendersSinceLastCheck = tendersDao.updateNewTenders(tenderDtos);
        slackService.publishTenders(newTendersSinceLastCheck);
    }
}
