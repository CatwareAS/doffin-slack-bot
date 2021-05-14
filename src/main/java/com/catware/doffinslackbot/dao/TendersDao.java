package com.catware.doffinslackbot.dao;

import com.catware.doffinslackbot.dto.TenderDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TendersDao {
    @Value("${app.doffin.tenders.allocation}")
    private int tendersBufferSize;

    private List<TenderDto> tenders = new ArrayList<>();

    public List<TenderDto> updateNewTenders(List<TenderDto> newTenders) {
        List<TenderDto> delta = newTenders.stream().filter(t -> !tenders.contains(t)).collect(Collectors.toList());
        tenders = newTenders;
        return delta;
    }
}
