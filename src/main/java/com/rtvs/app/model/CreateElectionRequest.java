package com.rtvs.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateElectionRequest {
    private String title;
    private List<Position> positions;
    private Date expiringDate;
    private String creatorEmail;
}
