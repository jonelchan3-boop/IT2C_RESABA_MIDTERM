package com.scc.model;

public record ScenarioEntity(
        int id,
        String patientName,
        String doctor,
        double consultFee,
        String status
) {}