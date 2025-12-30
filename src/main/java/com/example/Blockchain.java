package com.example;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> chain = new ArrayList<>();
    private final int difficulty = 2;

    public Blockchain() {
        chain.add(genesisBlock());
    }

    private Block genesisBlock() {
        return new Block(0, "0", System.currentTimeMillis(), List.of(), 0L,
                Block.calculateHash(0, "0", System.currentTimeMillis(), List.of(), 0L));
    }

    public boolean addBlock(List<Vote> votes) {
        Block last = chain.get(chain.size() - 1);
        Block newBlock = mineBlock(last.index() + 1, last.hash(), votes);
        chain.add(newBlock);
        return isValidNewBlock(newBlock, last);
    }

    private Block mineBlock(long index, String previousHash, List<Vote> votes) {
        long timestamp = System.currentTimeMillis();
        long nonce = 0;
        String hash;
        do {
            hash = Block.calculateHash(index, previousHash, timestamp, votes, nonce++);
        } while (!hash.startsWith("0".repeat(difficulty)));
        return new Block(index, previousHash, timestamp, votes, nonce - 1, hash);
    }

    public boolean isValidChain() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);
            if (!isValidNewBlock(current, previous)) return false;
        }
        return true;
    }

    private boolean isValidNewBlock(Block newBlock, Block previous) {
        return newBlock.hash().equals(Block.calculateHash(
                newBlock.index(), newBlock.previousHash(), newBlock.timestamp(),
                newBlock.votes(), newBlock.nonce()))
                && newBlock.previousHash().equals(previous.hash());
    }

    public List<Block> getChain() { return new ArrayList<>(chain); }

    // FIXED: For JSON persistence
    public void setChain(List<Block> chain) {
        this.chain.clear();
        this.chain.addAll(chain);
    }
}