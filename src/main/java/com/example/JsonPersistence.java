package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonPersistence {
    private static final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    private static final File BLOCKCHAIN_FILE = new File("blockchain.json");

    public static void save(Blockchain blockchain) {
        try {
            mapper.writeValue(BLOCKCHAIN_FILE, blockchain.getChain());
            System.out.println("✅ Blockchain saved to blockchain.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Blockchain load() {
        if (!BLOCKCHAIN_FILE.exists()) {
            System.out.println("📁 No blockchain.json found, creating new blockchain");
            return new Blockchain();
        }
        try {
            List<Block> chain = mapper.readValue(BLOCKCHAIN_FILE,
                    mapper.getTypeFactory().constructCollectionType(List.class, Block.class));
            Blockchain blockchain = new Blockchain();
            blockchain.setChain(chain);
            System.out.println("✅ Blockchain loaded from blockchain.json (" + chain.size() + " blocks)");
            return blockchain;
        } catch (IOException e) {
            System.out.println("⚠️ Failed to load blockchain, creating new one");
            return new Blockchain();
        }
    }
}