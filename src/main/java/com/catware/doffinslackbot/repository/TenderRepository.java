package com.catware.doffinslackbot.repository;

import com.catware.doffinslackbot.dto.TenderDto;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenderRepository extends FirestoreReactiveRepository<TenderDto> {
}
