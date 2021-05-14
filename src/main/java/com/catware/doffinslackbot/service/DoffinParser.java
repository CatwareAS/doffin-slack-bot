package com.catware.doffinslackbot.service;

import com.catware.doffinslackbot.TenderDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoffinParser {

    private static final String TENDERS_LIST_SELECTOR = "div.notice-search-item";
    private static final String TENDER_LEFT_COL_SELECTOR = "div.notice-search-item-header>a";
    private static final String TENDER_RIGHT_COL_SELECTOR = "div.right-col>div";

    Logger log = LoggerFactory.getLogger(DoffinParser.class);

    public List<TenderDto> parseUrl(URL url) throws IOException {
        Document doc = Jsoup.parse(url, 1000 * 30);
        var tendersElements = doc.select(TENDERS_LIST_SELECTOR);
        return parseTenders(tendersElements);
    }

    private List<TenderDto> parseTenders(Elements tendersElements) {
        return tendersElements
                .stream()
                .map(this::parseTenderElement)
                .collect(Collectors.toList());
    }

    private TenderDto parseTenderElement(Element te) {
        TenderDto tenderDto = null;
        try {
            tenderDto = new TenderDto(
                    parseName(te),
                    parseUrl(te),
                    parsePublishedBy(te),
                    parsePublishedDate(te),
                    parseExpiresDate(te),
                    parseDoffinReference(te)
            );
        } catch (Exception e) {
            log.error("Error while parsing tender. ", e);
        }
        return tenderDto;
    }

    private String parseName(Element te) {
        String tenderName = te.select(TENDER_LEFT_COL_SELECTOR).last().text();
        log.info("Parsing tender: {}", tenderName);
        return tenderName;
    }

    private String parseUrl(Element te) {
        return "https://doffin.no" + te.select(TENDER_LEFT_COL_SELECTOR).last().attr("href");
    }

    private String parsePublishedBy(Element te) {
        return te.select("div.left-col>div").first().text().substring("Published by: ".length());
    }

    private String parsePublishedDate(Element te) {
        List<TextNode> textNodes = te.select(TENDER_RIGHT_COL_SELECTOR).textNodes();
        return textNodes.get(textNodes.size() - 1).text().substring("Publication date: ".length());
    }

    private String parseExpiresDate(Element te) {
        List<TextNode> textNodes = te.select(TENDER_RIGHT_COL_SELECTOR).textNodes();
        if (textNodes.size() == 3) {
            return te.select("div.right-col>div:eq(1)").first().text().substring("Expire date: ".length());
        }
        return null;
    }

    private String parseDoffinReference(Element te) {
        List<TextNode> textNodes = te.select(TENDER_RIGHT_COL_SELECTOR).textNodes();
        return textNodes.get(0).text().substring("Doffin reference: ".length());
    }

}
