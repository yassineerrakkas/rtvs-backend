package com.rtvs.app.service;

import com.rtvs.app.model.Election;
import com.rtvs.app.model.CreateElectionRequest;
import com.rtvs.app.model.ElectionResponse;
import com.rtvs.app.repository.ElectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElectionService {
    private final ElectionRepository electionRepository;
    public ElectionResponse createElection(CreateElectionRequest request){
        Election election = Election
                .builder()
                .title(request.getTitle())
                .positions(request.getPositions())
                .creatorEmail(request.getCreatorEmail())
                .expiringDate(request.getExpiringDate())
                .build();
        electionRepository.save(election);
        return ElectionResponse.builder().message("Election Created").build();

    }

}
