package com.rtvs.app.service;

import com.rtvs.app.model.Election;
import com.rtvs.app.model.CreateElectionRequest;
import com.rtvs.app.model.ElectionResponse;
import com.rtvs.app.repository.ElectionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;

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
    public void testCreateVote() {
        // Arrange
        CreateElectionRequest request = CreateElectionRequest.builder()
                .title("Election 2024")
                .positions(Arrays.asList())
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

        // Act
        ElectionResponse response = electionService.createVote(request);

        // Assert
        assertEquals("vote created", response.getVoteMsg());
        verify(electionRepository).save(election);
    }
}
