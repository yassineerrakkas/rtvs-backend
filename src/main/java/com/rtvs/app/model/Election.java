package com.rtvs.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("Elections")
public class Election {
    @Id
    private String id;
    private String title;
    private List<Position> positions;
    private Date expiringDate;
    private String creatorEmail;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Candidate {
        private String firstName;
        private String lastName;
        private int numberOfVotes;
        private int yesVotes;
        private int noVotes;

        public void incrementVote() {
            this.numberOfVotes++;
        }

        public void incrementYesVote() {
            this.yesVotes++;
        }

        public void incrementNoVote() {
            this.noVotes++;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Position {
        private String label;
        private List<Candidate> candidates = new ArrayList<>();

        public boolean hasOneCandidate() {
            return candidates.size() == 1;
        }

        public Optional<Candidate> findCandidateByName(String firstName, String lastName) {
            return candidates.stream()
                    .filter(candidate -> candidate.getFirstName().equals(firstName) &&
                            candidate.getLastName().equals(lastName))
                    .findFirst();
        }
    }

    public boolean vote(List<VoteRequest.PositionVote> positionVotes) {
        for (VoteRequest.PositionVote positionVote : positionVotes) {
            Optional<Position> positionOpt = positions.stream()
                    .filter(position -> position.getLabel().equals(positionVote.getPositionLabel()))
                    .findFirst();

            if (positionOpt.isPresent()) {
                Position position = positionOpt.get();

                if (position.hasOneCandidate() && positionVote.getYesNoVote() != null) {
                    // Handle yes/no vote for a single candidate
                    Candidate candidate = position.getCandidates().get(0);
                    if (positionVote.getYesNoVote()) {
                        candidate.incrementYesVote();
                    } else {
                        candidate.incrementNoVote();
                    }
                } else if (!position.hasOneCandidate()) {
                    // Handle regular vote
                    Optional<Candidate> candidateOpt = position.findCandidateByName(positionVote.getCandidateFirstName(), positionVote.getCandidateLastName());

                    if (candidateOpt.isPresent()) {
                        Candidate candidate = candidateOpt.get();
                        candidate.incrementVote();
                    } else {
                        return false; // Candidate not found
                    }
                }
            } else {
                return false; // Position not found
            }
        }
        return true; // All votes successfully cast
    }
}
