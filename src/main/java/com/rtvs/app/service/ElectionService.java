package com.rtvs.app.service;

import com.rtvs.app.model.Election;
import com.rtvs.app.model.CreateElectionRequest;
import com.rtvs.app.model.ElectionResponse;
import com.rtvs.app.model.VoteRequest;
import com.rtvs.app.repository.ElectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ElectionService {
    private final ElectionRepository electionRepository;
    public ElectionResponse createElection(CreateElectionRequest request){
        Election election = Election
                .builder()
                .title(request.getTitle())
                .positions(request.getPositions())
                .creatorEmail(request.getCreatorEmail()).expiringDate(request.getExpiringDate())
                .build();
        electionRepository.save(election);
        return ElectionResponse.builder().message("Election Created").build();

    }
    public ElectionResponse vote(VoteRequest voteRequest) {
        Optional<Election> electionOpt = electionRepository.findById(voteRequest.getElectionId());

        if (electionOpt.isPresent()) {
            Election election = electionOpt.get();
            boolean success = election.vote(voteRequest.getPositionVotes());

            if (success) {
                electionRepository.save(election);
                return ElectionResponse.builder().message("Vote cast successfully").build();
            } else {
                return ElectionResponse.builder().message("Candidate or Position not found").build();
            }
        } else {
            return ElectionResponse.builder().message("Election not found").build();
        }
    }

}
