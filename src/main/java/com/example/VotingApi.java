package com.example;

import org.springframework.web.bind.annotation.*;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/voting")
@CrossOrigin(origins = "*")
public class VotingApi {

    private final Blockchain blockchain = JsonPersistence.load();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/chain")
    public List<Block> getChain() { return blockchain.getChain(); }

    @GetMapping("/valid")
    public Map<String, Boolean> isValid() {
        return Map.of("valid", blockchain.isValidChain());
    }

    @GetMapping("/results")
    public Map<String, Long> getResults() {
        return blockchain.getChain().stream()
                .flatMap(b -> b.votes().stream())
                .collect(java.util.stream.Collectors.groupingBy(
                        Vote::candidateId, java.util.stream.Collectors.counting()));
    }

    @GetMapping("/generate-vote/{voterId}/{candidate}")
    public Map<String, Object> generateVote(
            @PathVariable("voterId") String voterId,
            @PathVariable("candidate") String candidate) {
        Wallet voter = new Wallet();
        VoterRegistry.registerVoter(voterId, voter.getPublicKey());

        Instant timestamp = Instant.now();
        String payload = voterId + candidate + timestamp.toString();
        byte[] signatureBytes = voter.signData(payload);

        String voteJson = String.format("""
            {
              "voterId": "%s",
              "candidateId": "%s",
              "timestamp": "%s",
              "signature": "%s",
              "publicKey": "%s"
            }
            """, voterId, candidate, timestamp,
                Base64.getEncoder().encodeToString(signatureBytes),
                voter.getPublicKeyBase64());

        return Map.of("success", true, "useThisJson", voteJson);
    }

    @PostMapping("/vote")
    public Map<String, Object> castVote(@RequestBody Map<String, Object> voteData) {
        try {
            String voterId = (String) voteData.get("voterId");
            String candidateId = (String) voteData.get("candidateId");
            String timestampStr = (String) voteData.get("timestamp");
            String signatureB64 = (String) voteData.get("signature");
            String publicKeyB64 = (String) voteData.get("publicKey");

            Instant timestamp = Instant.parse(timestampStr);
            byte[] signature = Base64.getDecoder().decode(signatureB64);
            PublicKey publicKey = deserializePublicKey(publicKeyB64);

            // FIXED: Exact payload match
            String expectedPayload = voterId + candidateId + timestamp.toString();

            // Verify signature FIRST
            Wallet verifier = new Wallet();
            if (!verifier.verifyData(expectedPayload, signature, publicKey)) {
                return Map.of("success", false, "error", "Invalid signature");
            }

            if (!VoterRegistry.castVote(publicKey)) {
                return Map.of("success", false, "error", "Voter already voted");
            }

            // Create vote AFTER verification
            Vote vote = new Vote(voterId, candidateId, timestamp, signature, publicKey);
            boolean added = blockchain.addBlock(List.of(vote));
            JsonPersistence.save(blockchain);

            return Map.of("success", true, "blockAdded", added, "chainLength", blockchain.getChain().size());
        } catch (Exception e) {
            return Map.of("success", false, "error", e.getMessage());
        }
    }

    private PublicKey deserializePublicKey(String publicKeyB64) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyB64);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            java.security.KeyFactory kf = java.security.KeyFactory.getInstance("EC");
            return kf.generatePublic(spec);
        } catch (Exception e) {
            return new Wallet().getPublicKey();
        }
    }
}