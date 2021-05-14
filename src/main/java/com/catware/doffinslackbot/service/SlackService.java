package com.catware.doffinslackbot.service;

import com.catware.doffinslackbot.TenderDto;
import com.slack.api.Slack;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.divider;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.webhook.WebhookPayloads.payload;

@Service
public class SlackService {

    @Value("${app.slack.channel.url}")
    private String channelUrl;

    public void publishTenders(List<TenderDto> tenders) throws IOException {
        for (TenderDto t : tenders) {
            Slack.getInstance().send(channelUrl, payload(p -> p
                    .blocks(asBlocks(
                            section(section -> section.text(markdownText("*" + t.getName() + "*"))),
                            divider(),
                            section(section -> section.text(markdownText(t.getDoffinReference())))
                    ))
            ));
        }
    }
}
