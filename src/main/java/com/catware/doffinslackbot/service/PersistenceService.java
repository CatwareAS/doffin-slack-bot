package com.catware.doffinslackbot.service;

import com.catware.doffinslackbot.dto.TenderDto;
import com.catware.doffinslackbot.repository.TenderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PersistenceService {

    private final TenderRepository tenderRepository;

    public PersistenceService(TenderRepository tenderRepository) {
        this.tenderRepository = tenderRepository;
    }

    public List<TenderDto> filterNewTendersUpdateExisting(List<TenderDto> newTenders) {
        List<TenderDto> tenders = tenderRepository.findAll().collectList().block();
        List<TenderDto> delta = newTenders.stream().filter(t -> !Objects.requireNonNull(tenders).contains(t)).collect(Collectors.toList());
        tenderRepository.saveAll(newTenders).blockLast();
        return delta;
    }
}
