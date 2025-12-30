# Blockchain-Based Voting System

[![Java 21](https://img.shields.io/badge/Java-21-brightgreen)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-Central-blue)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

## 🚀 Features

- ✅ Immutable Blockchain - Proof-of-Work mining prevents tampering
- ✅ ECDSA Signatures - Cryptographic voter authentication
- ✅ Voter Registry - Prevents double-voting per public key
- ✅ JSON Persistence - Automatic blockchain save/load
- ✅ REST API - Full CRUD operations
- ✅ Live Web UI - Real-time results + vote casting
- ✅ Production Ready - Spring Boot, Java 21, Maven

## 🏗️ Architecture

```
Voters ──(Sign)──> REST API ──(Verify)──> Voter Registry
                                    ↓
                               Blockchain (PoW)
                                    ↓
                              JSON Persistence
```

### Core Components:

- `Vote` - Signed voter transaction (voterId, candidate, timestamp, signature)
- `Block` - Contains votes + PoW nonce + cryptographic hash chain
- `Blockchain` - Validates chain integrity + mines new blocks
- `Wallet` - ECDSA keypair generation + signing/verification
- `VoterRegistry` - Tracks registered voters + prevents double-voting

## 📦 Quick Start

```
# Clone & Build
mvn clean package

# Run API + Web UI
mvn spring-boot:run

# Open browser
http://localhost:8080
```

## 🧪 Test the System

1. Visit `http://localhost:8080`
2. Voter ID: `alice`, Candidate: `CandidateA`
3. Generate Vote JSON → Green JSON appears
4. Cast Vote → Success! Results update live
5. Try `alice` again → "Voter already voted"
6. Try `bob` + `CandidateB` → Works perfectly!

## 🔧 API Endpoints

| Method | Endpoint                                          | Description          |
|--------|---------------------------------------------------|----------------------|
| `GET`  | `/api/voting/chain`                               | Full blockchain      |
| `GET`  | `/api/voting/valid`                               | Chain validation     |
| `GET`  | `/api/voting/results`                             | Vote tallies         |
| `GET`  | `/api/voting/generate-vote/{voterId}/{candidate}` | Generate signed vote |
| `POST` | `/api/voting/vote`                                | Cast verified vote   |

## 🛠️ Tech Stack

```
Backend: Java 21 + Spring Boot 3.2.0 + Maven
Crypto: ECDSA (secp256r1) + SHA-256
Database: JSON file persistence (blockchain.json)
Frontend: Vanilla HTML/JS + Fetch API
Consensus: Proof-of-Work (2 leading zero difficulty)
```

## 📁 Project Structure

```
src/main/java/com/example/
├── Main.java              # Spring Boot entrypoint
├── Vote.java              # Vote record + payload()
├── Block.java             # Block + hashing + PoW
├── Blockchain.java        # Chain management + validation
├── Wallet.java            # ECDSA keypair + signing
├── VoterRegistry.java     # Double-vote prevention
├── VotingApi.java         # REST controllers
└── JsonPersistence.java   # blockchain.json save/load

src/main/resources/static/
└── index.html             # Web UI
```

## 🔒 Security Features

- Digital Signatures - Each vote cryptographically tied to voter
- Voter Registry - Public key → voterId mapping + one-vote-per-key
- Chain Validation - Immutable hash chain prevents tampering
- Proof-of-Work - Mining cost prevents spam blocks
- Timestamping - Prevents replay attacks

## 🚀 Production Deployment

```
# Build executable JAR
mvn clean package -DskipTests

# Run with custom port
java -jar target/blockchain-voting-1.0.jar --server.port=3000

# Docker (add Dockerfile)
docker build -t blockchain-voting .
docker run -p 8080:8080 blockchain-voting
```

## 📈 Scaling Options

| Feature         | Status    | Effort |
|-----------------|-----------|--------|
| Multi-node P2P  | 🔁 Planned | Medium |
| PostgreSQL      | 🔁 Planned | Low    |
| Redis Cache     | 🔁 Planned | Low    |
| Zero-Knowledge  | 🔁 Planned | High   |
| Admin Dashboard | 🔁 Planned | Medium |

## 🐛 Troubleshooting

| Issue                     | Solution                            |
|---------------------------|-------------------------------------|
| `mvn clean package` fails | `java -version` (must be 21+)       |
| Port 8080 busy            | `--server.port=3001`                |
| JSON errors               | `@JsonIgnore` on `PublicKey` fields |
| Signature fails           | Check `secp256r1` curve support     |

## 📄 License

MIT License - see [LICENSE](LICENSE) file for details

## 👤 Author

**Daniel Pierre Fachini**
- GitHub: [@akaPierre](https://github.com/akaPierre)
- Website: [danielpierre.tech](https://www.danielpierre.tech/)
- Twitter: [@PierreDani_](https://twitter.com/PierreDani_)

## 🤝 Contributing

1. Fork repository
2. `mvn clean package`
3. Add feature/tests
4. Submit PR

## 🎯 Next Features

- [ ] Multi-Node Network - P2P consensus
- [ ] Admin Dashboard - Voter management
- [ ] Zero-Knowledge Privacy - Anonymous voting proofs
- [ ] Database Backend - PostgreSQL scaling
- [ ] Mobile App - React Native client

---

⭐ **Star this repository if you find it helpful!**