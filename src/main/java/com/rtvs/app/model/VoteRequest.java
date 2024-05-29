package com.rtvs.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {
    private String electionId;
    private List<PositionVote> positionVotes;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PositionVote {
        private String positionLabel;
        private String candidateFirstName;
        private String candidateLastName;
        private Boolean yesNoVote; // null if not applicable, true for yes, false for no
    }
}
