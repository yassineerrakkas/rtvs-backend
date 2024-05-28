package com.rtvs.app.controller;

import com.rtvs.app.model.CreateElectionRequest;
import com.rtvs.app.model.ElectionResponse;
import com.rtvs.app.service.ElectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/elections")
public class ElectionController {
    private final ElectionService electionService;
    @PostMapping("/create")
    public ResponseEntity<ElectionResponse> createVote(
            @RequestBody CreateElectionRequest request
    ){
        System.out.println(request.getCreatorEmail() + " creating a new vote");
        return ResponseEntity.ok(electionService.createElection(request));
    }
}
