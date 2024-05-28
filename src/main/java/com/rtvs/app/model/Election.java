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
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Candidate {
    private String firstName;
    private String lastName;
    private int numberOfVotes;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Position {
    private String label;
    private List<Candidate> candidates = new ArrayList<>();

    public boolean hasOneCandidate() {
        return candidates.size() > 1;
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public void removeCandidate(Candidate candidate) {
        candidates.remove(candidate);
    }
}
