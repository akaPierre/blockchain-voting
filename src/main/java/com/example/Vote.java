package com.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.security.PublicKey;
import java.time.Instant;

public record Vote(
        String voterId,
        String candidateId,
        Instant timestamp,
        byte[] signature,
        @JsonIgnore PublicKey publicKey  // FIXED: Hide from JSON
) {
    public String payload() {
        return voterId + candidateId + timestamp.toString();
    }
}