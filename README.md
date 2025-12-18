# 📰 News Aggregator - Backend (Java/Spring Boot)

A sophisticated Java-based backend system for news aggregation, processing, and intelligent analysis. Implements all 10 required features using advanced algorithms and data structures.

## 🎯 Project Overview

This backend service automatically collects, processes, and analyzes news articles from multiple sources (BBC, Guardian, NY Times, CBC) using Java and Spring Boot. It provides RESTful APIs for the frontend application and implements advanced computing concepts including web crawling, natural language processing, and machine learning.

---

## ✨ Core Features (All 10 Implemented)

### 1. **Web Crawler** 🌐
- **Technology**: Selenium WebDriver 4.11.0
- **Implementation**: 5 dedicated crawler classes
- **Sources**: BBC, Guardian, NY Times, CBC
- **Features**:
  - Multi-threaded crawling
  - Polite crawling with delays
  - Scheduled automated updates
  - CSV export functionality
  - Respects robots.txt

**Files**:
- `BBCCrawler.java`
- `GuardianCrawler.java`
- `NYTimesCrawler.java`
- `CBCCrawler.java`
- `GlobalCrawler.java`

### 2. **HTML Parser** 📄
- **Technology**: Selenium WebDriver + JSoup
- **Implementation**: Integrated within crawlers
- **Features**:
  - DOM parsing
  - Content extraction (title, description, date, author)
  - Advertisement removal
  - Data normalization
  - Structured data conversion

**File**: `DataCleaner.java`

### 3. **Spell Checking System** ✍️
- **Algorithm**: Levenshtein Distance (Edit Distance)
- **Technology**: LanguageTool 6.4
- **Implementation**: `SpellCheckService.java`
- **Features**:
  - Dictionary built from articles
  - "Did you mean?" suggestions
  - Frequency-based word ranking
  - Multi-word suggestions
  - Continuous vocabulary updates

**Algorithm Complexity**: O(m × n) where m, n are string lengths

**Key Methods**:
```java
public Map<String, Object> checkSpelling(String inputText)
public int levenshteinDistance(String a, String b)
public List<String> getSuggestions(String word, int count)
```

### 4. **Word Completion** 🔤
- **Data Structure**: Trie-like implementation with HashMap
- **Implementation**: `SearchAutoCompleteService.java`
- **Features**:
  - Real-time autocomplete
  - Prefix matching
  - Frequency-based ranking
  - Historical search integration
  - Trending topics prioritization

**Key Methods**:
```java
public List<Map<String, Object>> getSuggestions(String term, int limit)
public void incrementSearchFrequency(String term)
```

### 5. **Frequency Count Analysis** 📊
- **Data Structure**: HashMap
- **Implementation**: Multiple services
- **Features**:
  - Word occurrence tracking
  - Trending topic identification
  - Statistical insights
  - Time-based analysis
  - MongoDB persistence

**Files**:
- `SearchFrequencyService.java`
- `SearchAutoCompleteService.java`
- `RankedArticlesService.java`

### 6. **Search Frequency Tracking** 🔍
- **Database**: MongoDB collection `search_frequency`
- **Implementation**: `SearchFrequencyService.java`
- **Features**:
  - User search pattern monitoring
  - Popular query tracking
  - Time-series analysis
  - Trend identification
  - Real-time updates

**Key Methods**:
```java
public void incrementSearchFrequency(String term)
public List<Map<String, Object>> getTopSearches(int limit)
```

### 7. **Page Ranking System** ⭐
- **Algorithm**: TF-IDF (Term Frequency-Inverse Document Frequency)
- **Implementation**: `RankedArticlesService.java`
- **Features**:
  - TF-IDF scoring
  - Stop words filtering
  - Title weighting (3x importance)
  - Parallel processing
  - Pagination support

**Algorithm**:
- **TF**: `tf = count(term) / total_terms`
- **IDF**: `idf = log(total_docs / docs_with_term)`
- **Score**: `Σ(TF × IDF)` for all terms

**Key Methods**:
```java
public List<News> getRankedNews()
public List<News> getTopRankedNews(int page, int limit)
private double calculateTFIDFScore(String content, Map<String, Integer> df, int totalDocs)
```

### 8. **Inverted Indexing** 🗂️
- **Data Structure**: HashMap + MongoDB
- **Implementation**: `SearchAutoCompleteService.java`
- **Features**:
  - Word-to-article mapping
  - Instant search retrieval
  - No full article scanning
  - Efficient updates
  - Persistent storage

**Complexity**: O(1) average lookup time

### 9. **Data Validation** ✅
- **Technology**: Regular Expressions (RegEx)
- **Implementation**: `DataCleaner.java`
- **Features**:
  - URL validation
  - Date format validation
  - Email validation
  - Text sanitization
  - HTML tag removal
  - Invalid data filtering

**RegEx Patterns**:
- URL: `^https?://.*`
- Email: `^[A-Za-z0-9+_.-]+@(.+)$`
- HTML tags: `<[^>]+>`

### 10. **Pattern Recognition** 🔎
- **Technology**: Regular Expressions (RegEx)
- **Implementation**: `PatternDetectionService.java`
- **Features**:
  - Trending topic identification
  - Recurring theme detection
  - Named entity extraction
  - Date/location pattern matching
  - Citation pattern recognition
  - Spam/low-quality filtering

---

## 🚀 Additional Features

### AI/ML Integration
- **Article Summarization** (`SummarizationService.java`)
  - Deep Java Library (DJL) 0.24.0
  - PyTorch engine
  - HuggingFace tokenizers
  
- **Text-to-Speech** (`TextToSpeechService.java`)
  - Audio conversion for accessibility

- **Personalized Recommendations** (`RecommendationService.java`)
  - User interaction tracking
  - Behavior-based recommendations
  - Search history integration

### User Management
- **Authentication** (`AuthService.java`)
  - User registration and login
  - BCrypt password encryption
  - Session management

- **User Interactions** (`UserInteractionService.java`)
  - Click tracking
  - Reading history
  - Preference learning

---

## 🛠️ Technology Stack

### Core Technologies
- **Java**: 23
- **Spring Boot**: 3.4.0
- **Database**: MongoDB (NoSQL)
- **Build Tool**: Maven

### Libraries & Frameworks
- **Selenium WebDriver**: 4.11.0 (Web crawling)
- **LanguageTool**: 6.4 (Spell checking)
- **Deep Java Library (DJL)**: 0.24.0 (AI/ML)
- **PyTorch Engine**: Model inference
- **HuggingFace Tokenizers**: NLP
- **Spring Security Crypto**: BCrypt password hashing
- **Dotenv Java**: Environment variables

### Dependencies (pom.xml)
```xml
<dependencies>
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- MongoDB -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
    
    <!-- Selenium -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.11.0</version>
    </dependency>
    
    <!-- Deep Java Library -->
    <dependency>
        <groupId>ai.djl</groupId>
        <artifactId>api</artifactId>
        <version>0.24.0</version>
    </dependency>
    
    <!-- LanguageTool -->
    <dependency>
        <groupId>org.languagetool</groupId>
        <artifactId>languagetool-core</artifactId>
        <version>6.4</version>
    </dependency>
</dependencies>
```

---

## 📁 Project Structure

```
newsaggregator/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── NewsAggregatorApplication.java    # Main application
│   │   │   ├── config/                           # Configuration
│   │   │   │   ├── MongoConfig.java
│   │   │   │   ├── WebConfig.java
│   │   │   │   └── CorsConfig.java
│   │   │   ├── controller/                       # REST Controllers
│   │   │   │   ├── NewsController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── RecommendationController.java
│   │   │   │   ├── SearchAutoController.java
│   │   │   │   ├── SpellCheckController.java
│   │   │   │   ├── SummarizationController.java
│   │   │   │   ├── TextToSpeechController.java
│   │   │   │   ├── PatternDetectionController.java
│   │   │   │   └── RankedArticlesController.java
│   │   │   ├── service/                          # Business Logic
│   │   │   │   ├── NewsService.java
│   │   │   │   ├── CrawlerService.java
│   │   │   │   ├── SpellCheckService.java
│   │   │   │   ├── SearchAutoCompleteService.java
│   │   │   │   ├── SearchFrequencyService.java
│   │   │   │   ├── RankedArticlesService.java
│   │   │   │   ├── PatternDetectionService.java
│   │   │   │   ├── RecommendationService.java
│   │   │   │   ├── SummarizationService.java
│   │   │   │   ├── TextToSpeechService.java
│   │   │   │   ├── UserInteractionService.java
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── DataCleaner.java
│   │   │   │   └── CSVtoMongoUploader.java
│   │   │   ├── crawler/                          # Web Crawlers
│   │   │   │   ├── BBCCrawler.java
│   │   │   │   ├── GuardianCrawler.java
│   │   │   │   ├── NYTimesCrawler.java
│   │   │   │   ├── CBCCrawler.java
│   │   │   │   └── GlobalCrawler.java
│   │   │   ├── model/                            # Data Models
│   │   │   │   ├── News.java
│   │   │   │   ├── User.java
│   │   │   │   └── UserInteraction.java
│   │   │   ├── repository/                       # MongoDB Repositories
│   │   │   │   └── UserRepository.java
│   │   │   └── db/                               # Database Utilities
│   │   │       └── MongoDBConnection.java
│   │   └── resources/
│   │       └── application.properties            # Configuration
│   └── test/                                     # Unit Tests
├── pom.xml                                       # Maven Dependencies
├── Dockerfile                                    # Docker Configuration
├── docker-compose.yml                            # Docker Compose
└── README.md                                     # This file
```

---

## ⚙️ Configuration

### Environment Variables

Create a `.env` file in the root directory:

```env
MONGO_URI=mongodb+srv://<username>:<password>@<cluster>.mongodb.net/<database>?retryWrites=true&w=majority
MONGO_DATABASE=news_aggregator
```

### application.properties

```properties
# MongoDB Configuration
spring.data.mongodb.uri=${MONGO_URI}
spring.data.mongodb.database=${MONGO_DATABASE}

# Server Configuration
server.port=8080
server.servlet.context-path=/

# CORS Configuration
cors.allowed-origins=http://localhost:5173

# Crawler Configuration
crawler.schedule.interval=3600000  # 1 hour in milliseconds
crawler.max-articles=100

# AI Model Configuration
ai.model.path=/models/summarization
ai.model.max-length=150
```

---

## 🚀 Getting Started

### Prerequisites

- **Java JDK**: 23 or higher
- **Maven**: 3.6 or higher
- **MongoDB**: Atlas account or local instance
- **Docker** (optional): For containerized deployment

### Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd newsaggregator
```

2. **Configure environment variables**
```bash
# Create .env file
echo "MONGO_URI=your_mongodb_uri" > .env
echo "MONGO_DATABASE=news_aggregator" >> .env
```

3. **Install dependencies**
```bash
mvn clean install
```

4. **Run the application**
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

---

## 🐳 Docker Deployment

### Using Docker Compose

```bash
# Start services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

**Services**:
- Selenium Chrome (Port 4444)
- VNC Viewer (Port 7900)
- Java Crawler Service

---

## 🔌 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Authentication Endpoints

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "securePassword123"
}
```

### News Endpoints

#### Get All News
```http
GET /api/news?page=0&size=20
```

#### Get News by Category
```http
GET /api/news/category/{category}?page=0&size=20
```

#### Search News
```http
GET /api/news/search?query=technology&page=0&size=20
```

### Search & Autocomplete

#### Autocomplete Suggestions
```http
GET /api/search-suggest?query=tech&limit=5
```

#### Spell Check
```http
POST /api/spellcheck
Content-Type: application/json

{
  "text": "technolgy news"
}
```

#### Increment Search Frequency
```http
POST /api/search-increment
Content-Type: application/json

{
  "userId": "user123",
  "query": "climate change"
}
```

### Ranking & Recommendations

#### Get Ranked Articles
```http
GET /api/ranked-articles?page=1&limit=20
```

#### Get Personalized Recommendations
```http
GET /api/recommendations/{userId}?limit=10
```

#### Track Article Click
```http
POST /api/recommendations/track
Content-Type: application/json

{
  "userId": "user123",
  "articleId": "article456",
  "interactionType": "click"
}
```

### AI Features

#### Summarize Article
```http
POST /api/summarize
Content-Type: application/json

{
  "text": "Long article content here...",
  "maxLength": 150
}
```

#### Text-to-Speech
```http
POST /api/text-to-speech
Content-Type: application/json

{
  "text": "Article content to convert to speech"
}
```

#### Pattern Detection
```http
GET /api/patterns?startDate=2024-01-01&endDate=2024-12-31
```

### Admin Endpoints

#### Trigger Manual Crawl
```http
POST /api/admin/crawl/{source}
```
Sources: `bbc`, `guardian`, `nytimes`, `cbc`, `all`

---

## 🗄️ Database Schema

### MongoDB Collections

#### `news` Collection
```javascript
{
  "_id": ObjectId,
  "title": String,
  "description": String,
  "content": String,
  "url": String,
  "imageUrl": String,
  "source": String,        // "BBC", "Guardian", "NYTimes", "CBC"
  "category": String,      // "World", "Politics", "Tech", "Sports", etc.
  "publishedAt": Date,
  "author": String,
  "tags": [String],
  "score": Double,         // TF-IDF score
  "createdAt": Date,
  "updatedAt": Date
}
```

#### `users` Collection
```javascript
{
  "_id": ObjectId,
  "username": String,
  "email": String,
  "password": String,      // BCrypt hashed
  "preferences": {
    "categories": [String],
    "sources": [String]
  },
  "createdAt": Date,
  "lastLogin": Date
}
```

#### `userInteractions` Collection
```javascript
{
  "_id": ObjectId,
  "userId": String,
  "articleId": String,
  "interactionType": String,  // "click", "read", "save"
  "timestamp": Date,
  "category": String
}
```

#### `search_frequency` Collection
```javascript
{
  "_id": ObjectId,
  "term": String,
  "count": Number,
  "lastSearched": Date,
  "userId": String
}
```

---

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Test Coverage
- Unit tests for services
- Integration tests for controllers
- Database tests for repositories

---

## 🔐 Security Features

- **Password Encryption**: BCrypt hashing with salt
- **CORS Protection**: Configured allowed origins
- **Input Validation**: Server-side validation for all inputs
- **SQL Injection Prevention**: MongoDB parameterized queries
- **Environment Variables**: Sensitive data in `.env` files

---

## 🐛 Troubleshooting

### Backend won't start
```bash
# Check Java version
java -version

# Clean Maven cache
mvn clean install -U

# Check MongoDB connection
# Verify MONGO_URI in .env file
```

### MongoDB connection timeout
- Verify MongoDB Atlas IP whitelist
- Check network connectivity
- Validate connection string format
- Ensure database user has proper permissions

### Selenium crawler not working
```bash
# Restart Docker services
docker-compose down
docker-compose up -d

# Check Selenium status
curl http://localhost:4444/status
```

---

## 📊 Performance

### Optimizations Implemented
- Parallel processing for TF-IDF calculations
- HashMap for O(1) lookups
- MongoDB indexing for fast queries
- Connection pooling
- Caching for frequently accessed data

---

## 👥 Team

**Group No. 11**
- Bhavik Thumbadiya (110204126)
- Shiv Patel (110191927)
- Dhritiben Patel (110196105)
- Dhruvi Patel (110202575)
- Nishtha Pandya (110195567)

**Course**: COMP 8547 - Advanced Computing Concept  
**Professor**: Prof. Olena Syrotkina  
**University**: University of Windsor  
**Semester**: Fall 2025

---

## 📝 License

This project is created for academic purposes.

---

## 🙏 Acknowledgments

- Spring Boot Framework
- MongoDB
- Selenium WebDriver
- Deep Java Library
- LanguageTool

---

**Backend Version**: 1.0  
**Last Updated**: December 2025
