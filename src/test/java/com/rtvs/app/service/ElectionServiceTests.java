package com.rtvs.app.service;

import com.rtvs.app.model.Election;
import com.rtvs.app.model.CreateElectionRequest;
import com.rtvs.app.model.ElectionResponse;
import com.rtvs.app.repository.ElectionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ElectionServiceTests {

    @Mock
    private ElectionRepository electionRepository;

    @InjectMocks
    private ElectionService electionService;

    public ElectionServiceTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatElection() {
        Election.Candidate candidate1 = Election.Candidate
                .builder()
                .firstName("yassine")
                .lastName("errakkas")
                .numberOfVotes(0)
                .build();

        Election.Candidate candidate2 = Election.Candidate
                .builder()
                .firstName("ali")
                .lastName("ahmad")
                .numberOfVotes(0)
                .build();

        Election.Position position = Election.Position
                .builder()
                .label("President")
                .candidates(List.of(candidate1,candidate2))
                .build();

        CreateElectionRequest request = CreateElectionRequest.builder()
                .title("Election 2024")
                .positions(List.of(position))
                .creatorEmail("admin@example.com")
                .expiringDate(new Date())
                .build();

        Election election = Election.builder()
                .title(request.getTitle())
                .positions(request.getPositions())
                .creatorEmail(request.getCreatorEmail())
                .expiringDate(request.getExpiringDate())
                .build();

        when(electionRepository.save(election)).thenReturn(election);

        ElectionResponse response = electionService.createElection(request);

        assertEquals("Election Created", response.getMessage());
        verify(electionRepository).save(election);
    }
}