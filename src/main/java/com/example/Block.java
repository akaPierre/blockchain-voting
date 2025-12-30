package com.example;

import java.security.MessageDigest;
import java.util.List;

public record Block(long index, String previousHash, long timestamp, List<Vote> votes, long nonce, String hash) {
    public static String calculateHash(long index, String previousHash, long timestamp, List<Vote> votes, long nonce) {
        // FIXED: Proper votes serialization
        String votesData = votes.stream()
                .map(v -> v.voterId() + v.candidateId() + v.timestamp().toString())
                .reduce((a, b) -> a + "|" + b)
                .orElse("");
        return sha256(index + previousHash + timestamp + votesData + nonce);
    }

    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) hex.append(String.format("%02X", b));
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}