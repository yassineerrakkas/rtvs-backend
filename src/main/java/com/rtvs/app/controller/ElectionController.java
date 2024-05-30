package com.rtvs.app.controller;

import com.rtvs.app.model.CreateElectionRequest;
import com.rtvs.app.model.ElectionResponse;
import com.rtvs.app.model.VoteRequest;
import com.rtvs.app.service.ElectionService;
import com.rtvs.app.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/elections")
public class ElectionController {
    @Autowired
    private ElectionService electionService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/create")
    public ElectionResponse createElection(@RequestBody CreateElectionRequest request) {
        return electionService.createElection(request);
    }

    @PostMapping("/vote")
    public ElectionResponse castVote(@RequestBody VoteRequest voteRequest,@NonNull HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ElectionResponse.builder().message("Unauthorized").build();
        }
        String authToken = authHeader.substring(7);
        String voterEmail = jwtService.extractUserEmail(authToken);
        return electionService.vote(voteRequest,voterEmail);
    }
}
