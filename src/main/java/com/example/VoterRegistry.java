package com.example;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VoterRegistry {
    private static final Map<String, PublicKey> voters = new HashMap<>();
    private static final Map<PublicKey, Boolean> voted = new HashMap<>();

    public static void registerVoter(String voterId, PublicKey publicKey) {
        voters.put(voterId, publicKey);
        voted.put(publicKey, false);
        System.out.println("✅ Voter registered: " + voterId);
    }

    public static boolean isRegistered(String voterId) {
        return voters.containsKey(voterId);
    }

    public static PublicKey getPublicKey(String voterId) {
        return voters.get(voterId);
    }

    public static boolean hasVoted(PublicKey publicKey) {
        return voted.getOrDefault(publicKey, true);
    }

    public static boolean castVote(PublicKey publicKey) {
        if (hasVoted(publicKey)) return false;
        voted.put(publicKey, true);
        return true;
    }

    public static Set<String> getVoters() {
        return voters.keySet();
    }
}