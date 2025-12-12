# 📰 Intelligent News Aggregator Platform

A sophisticated full-stack news aggregation and personalization platform that combines AI-powered recommendations, real-time web crawling, and advanced search capabilities to deliver a personalized news reading experience.

## 🌟 Project Overview

This project is an intelligent news aggregation system that crawls multiple news sources (BBC, The Guardian, NY Times, CBC), processes articles using AI/ML techniques, and provides personalized recommendations based on user interactions. The platform features advanced search with autocomplete, spell-checking, text-to-speech, AI summarization, and pattern detection capabilities.

### Key Highlights

- **Multi-Source News Aggregation**: Automated crawlers for BBC, Guardian, NY Times, and CBC
- **AI-Powered Recommendations**: Personalized content based on user reading patterns and search history
- **Advanced Search**: Autocomplete suggestions, spell-checking, and search frequency tracking
- **AI Summarization**: Generate concise summaries of articles using deep learning models
- **Text-to-Speech**: Convert articles to audio for accessibility
- **Pattern Detection**: Identify trending topics and news patterns
- **User Authentication**: Secure registration and login with BCrypt password encryption
- **Modern UI**: Responsive React interface with Tailwind CSS and smooth animations

---

## 🏗️ Architecture

### Technology Stack

#### Backend (`newsaggregator`)
- **Framework**: Spring Boot 3.4.0
- **Language**: Java 23
- **Database**: MongoDB (NoSQL)
- **AI/ML**: 
  - Deep Java Library (DJL) 0.24.0
  - PyTorch Engine for model inference
  - HuggingFace Tokenizers
- **Web Crawling**: Selenium WebDriver 4.11.0
- **NLP**: LanguageTool 6.4 for spell-checking
- **Security**: Spring Security Crypto (BCrypt)
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose

#### Frontend (`newshub`)
- **Framework**: React 19.1.1
- **Build Tool**: Vite (Rolldown)
- **State Management**: Redux Toolkit 2.10.1
- **Routing**: React Router DOM 7.9.5
- **Styling**: Tailwind CSS 4.1.17
- **HTTP Client**: Axios 1.13.2
- **Icons**: Lucide React 0.554.0

### System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        Frontend (React)                      │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐   │
│  │   Home   │  │ All News │  │  Login   │  │ Profile  │   │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │         Redux Store (Auth, News, Recommendations)    │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                            ↕ REST API
┌─────────────────────────────────────────────────────────────┐
│                   Backend (Spring Boot)                      │
│  ┌──────────────────────────────────────────────────────┐   │
│  │                    Controllers                        │   │
│  │  News │ Auth │ Recommendation │ Search │ Summarize   │   │
│  └──────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │                      Services                         │   │
│  │  Crawler │ Recommendation │ SpellCheck │ TTS │ AI    │   │
│  └──────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │                    Repositories                       │   │
│  │           MongoDB Data Access Layer                   │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                            ↕
┌─────────────────────────────────────────────────────────────┐
│                    MongoDB Database                          │
│  Collections: news, users, userInteractions, searchTerms    │
└─────────────────────────────────────────────────────────────┘
                            ↕
┌─────────────────────────────────────────────────────────────┐
│                   External Services                          │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐   │
│  │   BBC    │  │ Guardian │  │ NY Times │  │   CBC    │   │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘   │
│                    (Selenium Crawlers)                       │
└─────────────────────────────────────────────────────────────┘
```

---

## ✨ Features

### 1. **News Aggregation & Crawling**
- **Multi-Source Crawlers**: Automated Selenium-based crawlers for:
  - BBC News
  - The Guardian
  - New York Times
  - CBC News
- **Scheduled Updates**: Automatic crawling at regular intervals
- **Data Cleaning**: Automated data sanitization and normalization
- **CSV Export**: Backup news data to CSV files
- **MongoDB Integration**: Seamless upload to database

### 2. **Personalized Recommendations**
- **User Interaction Tracking**: Records article clicks and reading patterns
- **Search History Analysis**: Incorporates search queries into recommendation algorithm
- **Weighted Scoring**: Balances between clicked articles and search interests
- **Real-time Updates**: Recommendations update as users interact with content
- **Category-based Filtering**: Recommendations by news category

### 3. **Advanced Search**
- **Full-Text Search**: Search across article titles, content, and metadata
- **Autocomplete Suggestions**: Real-time search suggestions based on:
  - Historical search frequency
  - Popular search terms
  - Fuzzy matching
- **Spell Checking**: LanguageTool integration for:
  - "Did you mean?" suggestions
  - Automatic typo correction
  - Multi-language support
- **Search Frequency Tracking**: Analytics on popular search terms
- **Category Filtering**: Filter by news categories (World, Politics, Tech, Sports, etc.)

### 4. **AI-Powered Features**
- **Article Summarization**: 
  - Deep learning model for text summarization
  - Generates concise article summaries
  - Adjustable summary length
- **Text-to-Speech**: 
  - Convert articles to audio
  - Accessibility feature for visually impaired users
- **Pattern Detection**: 
  - Identify trending topics
  - Detect news patterns across sources
  - Temporal analysis of news coverage

### 5. **User Management**
- **Secure Authentication**: 
  - User registration and login
  - BCrypt password hashing
  - Session management
- **User Profiles**: 
  - Personalized user dashboard
  - Reading history
  - Saved preferences
- **Protected Routes**: Frontend route protection for authenticated users

### 6. **Modern User Interface**
- **Responsive Design**: Mobile-first approach with Tailwind CSS
- **Dark/Light Theme**: User preference support
- **Smooth Animations**: Enhanced UX with transitions
- **Infinite Scroll**: Seamless content loading
- **Pagination**: Page-based navigation with Previous/Next controls
- **Category Tabs**: Modern horizontal category navigation
- **Search Bar**: Integrated search with autocomplete dropdown
- **Toast Notifications**: User feedback for actions

---

## 📁 Project Structure

```
News crawler/
├── newsaggregator/              # Backend (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/
│   │   │   │   ├── config/              # Configuration classes
│   │   │   │   │   ├── MongoConfig.java
│   │   │   │   │   ├── WebConfig.java
│   │   │   │   │   └── CorsConfig.java
│   │   │   │   ├── controller/          # REST API Controllers
│   │   │   │   │   ├── NewsController.java
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── RecommendationController.java
│   │   │   │   │   ├── SearchAutoController.java
│   │   │   │   │   ├── SpellCheckController.java
│   │   │   │   │   ├── SummarizationController.java
│   │   │   │   │   ├── TextToSpeechController.java
│   │   │   │   │   ├── PatternDetectionController.java
│   │   │   │   │   └── ...
│   │   │   │   ├── service/             # Business Logic
│   │   │   │   │   ├── NewsService.java
│   │   │   │   │   ├── CrawlerService.java
│   │   │   │   │   ├── RecommendationService.java
│   │   │   │   │   ├── AutoCompleteService.java
│   │   │   │   │   ├── SpellCheckService.java
│   │   │   │   │   ├── SummarizationService.java
│   │   │   │   │   ├── TextAnalysisService.java
│   │   │   │   │   ├── UserInteractionService.java
│   │   │   │   │   └── ...
│   │   │   │   ├── crawler/             # Web Crawlers
│   │   │   │   │   ├── BBCCrawler.java
│   │   │   │   │   ├── GuardianCrawler.java
│   │   │   │   │   ├── NYTimesCrawler.java
│   │   │   │   │   ├── CBCCrawler.java
│   │   │   │   │   └── GlobalCrawler.java
│   │   │   │   ├── model/               # Data Models
│   │   │   │   │   ├── News.java
│   │   │   │   │   ├── User.java
│   │   │   │   │   └── UserInteraction.java
│   │   │   │   ├── repository/          # MongoDB Repositories
│   │   │   │   │   └── UserRepository.java
│   │   │   │   ├── db/                  # Database Utilities
│   │   │   │   │   └── MongoDBConnection.java
│   │   │   │   └── NewsAggregatorApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/                        # Unit Tests
│   ├── pom.xml                          # Maven Dependencies
│   ├── Dockerfile                       # Docker Configuration
│   └── docker-compose.yml               # Docker Compose Setup
│
└── newshub/                     # Frontend (React + Vite)
    ├── src/
    │   ├── components/                  # React Components
    │   │   ├── Home.jsx                 # Homepage with featured news
    │   │   ├── AllNews.jsx              # All news with search & filters
    │   │   ├── Navbar.jsx               # Navigation bar
    │   │   ├── Recommendations.jsx      # Personalized recommendations
    │   │   ├── SummarizeModal.jsx       # AI summarization modal
    │   │   ├── Loader.jsx               # Loading spinner
    │   │   ├── Toast.jsx                # Notification component
    │   │   └── ProtectedRoute.jsx       # Route protection
    │   ├── pages/                       # Page Components
    │   │   ├── Login.jsx                # Login page
    │   │   ├── Register.jsx             # Registration page
    │   │   └── Profile.jsx              # User profile
    │   ├── features/                    # Redux Slices
    │   │   ├── auth/                    # Authentication state
    │   │   ├── news/                    # News state
    │   │   ├── recommendation/          # Recommendation state
    │   │   └── summarization/           # Summarization state
    │   ├── app/                         # Redux Store
    │   ├── utils/                       # Utility functions
    │   ├── App.jsx                      # Main App component
    │   └── main.jsx                     # Entry point
    ├── public/                          # Static assets
    ├── package.json                     # NPM Dependencies
    ├── vite.config.js                   # Vite Configuration
    └── tailwind.config.js               # Tailwind Configuration
```

---

## 🚀 Getting Started

### Prerequisites

- **Java**: JDK 23 or higher
- **Node.js**: v18 or higher
- **MongoDB**: MongoDB Atlas account or local MongoDB instance
- **Maven**: 3.6 or higher
- **Docker** (optional): For containerized deployment

### Environment Setup

#### 1. Backend Configuration

Create a `.env` file in the `newsaggregator` directory:

```env
MONGO_URI=mongodb+srv://<username>:<password>@<cluster>.mongodb.net/<database>?retryWrites=true&w=majority
MONGO_DATABASE=news_aggregator
```

Or configure `application.properties`:

```properties
spring.data.mongodb.uri=${MONGO_URI}
spring.data.mongodb.database=${MONGO_DATABASE}
server.port=8080
```

#### 2. Frontend Configuration

Create a `.env` file in the `newshub` directory:

```env
VITE_API_URL=http://localhost:8080
```

Update `vite.config.js` for API proxy:

```javascript
export default defineConfig({
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
});
```

---

## 📦 Installation

### Backend Setup

```bash
# Navigate to backend directory
cd newsaggregator

# Install dependencies (Maven will download automatically)
mvn clean install

# Run the application
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Frontend Setup

```bash
# Navigate to frontend directory
cd newshub

# Install dependencies
npm install

# Run development server
npm run dev
```

The frontend will start on `http://localhost:5173`

---

## 🐳 Docker Deployment

### Using Docker Compose

The project includes a Docker Compose configuration for Selenium-based crawlers:

```bash
# Navigate to backend directory
cd newsaggregator

# Start services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

**Services:**
- **Selenium Chrome**: Standalone Chrome browser for web crawling (Port 4444)
- **VNC Viewer**: Debug browser sessions (Port 7900)
- **Java Crawler**: Automated news crawling service

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

#### Get Single Article
```http
GET /api/news/{id}
```

### Recommendation Endpoints

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

#### Track Search Query
```http
POST /api/search-increment
Content-Type: application/json

{
  "userId": "user123",
  "query": "climate change"
}
```

### Search Endpoints

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

#### Get Crawler Status
```http
GET /api/admin/crawler-status
```

---

## 🧪 Testing

### Backend Tests

```bash
cd newsaggregator
mvn test
```

### Frontend Tests

```bash
cd newshub
npm run test
```

---

## 📊 Database Schema

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

#### `searchTerms` Collection
```javascript
{
  "_id": ObjectId,
  "term": String,
  "frequency": Number,
  "lastSearched": Date,
  "userId": String
}
```

---

## 🎨 Frontend Features

### Components Overview

#### **Home Component**
- Featured news carousel
- Category-wise news sections
- Trending articles
- Quick search access

#### **AllNews Component**
- Comprehensive news listing
- Advanced search bar with autocomplete
- Category filter tabs
- Pagination controls
- Infinite scroll option
- Article cards with hover effects

#### **Recommendations Component**
- Personalized news feed
- Based on user reading history
- Search query integration
- Category-specific recommendations

#### **Navbar Component**
- Responsive navigation
- User authentication status
- Quick links to all sections
- Search integration

### Redux State Management

#### **Auth Slice**
- User authentication state
- Login/logout actions
- Token management
- Protected route handling

#### **News Slice**
- All news articles
- Pagination state
- Search results
- Category filters
- Loading states

#### **Recommendation Slice**
- Personalized recommendations
- User interaction tracking
- Recommendation algorithm state

#### **Summarization Slice**
- Article summaries
- AI model state
- Summary cache

---

## 🔧 Configuration

### Backend Configuration

#### `application.properties`
```properties
# MongoDB Configuration
spring.data.mongodb.uri=${MONGO_URI}
spring.data.mongodb.database=news_aggregator

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

#### `pom.xml` Key Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data MongoDB
- Deep Java Library (DJL) API & PyTorch
- Selenium WebDriver
- LanguageTool
- Spring Security Crypto
- Dotenv Java

### Frontend Configuration

#### `vite.config.js`
```javascript
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
});
```

#### `package.json` Key Dependencies
- React 19.1.1
- Redux Toolkit 2.10.1
- React Router DOM 7.9.5
- Axios 1.13.2
- Tailwind CSS 4.1.17
- Lucide React (Icons)

---

## 🚦 Running the Full Stack

### Development Mode

**Terminal 1 - Backend:**
```bash
cd newsaggregator
mvn spring-boot:run
```

**Terminal 2 - Frontend:**
```bash
cd newshub
npm run dev
```

**Access the application:**
- Frontend: `http://localhost:5173`
- Backend API: `http://localhost:8080/api`

### Production Build

**Backend:**
```bash
cd newsaggregator
mvn clean package
java -jar target/news-aggregator-1.0-SNAPSHOT.jar
```

**Frontend:**
```bash
cd newshub
npm run build
npm run preview
```

---

## 🔐 Security Features

- **Password Encryption**: BCrypt hashing with salt
- **CORS Protection**: Configured allowed origins
- **Input Validation**: Server-side validation for all inputs
- **SQL Injection Prevention**: MongoDB parameterized queries
- **XSS Protection**: React's built-in XSS prevention
- **Environment Variables**: Sensitive data in `.env` files

---

## 🐛 Troubleshooting

### Common Issues

#### Backend won't start
```bash
# Check Java version
java -version

# Clean Maven cache
mvn clean install -U

# Check MongoDB connection
# Verify MONGO_URI in .env file
```

#### Frontend build errors
```bash
# Clear node_modules and reinstall
rm -rf node_modules package-lock.json
npm install

# Clear Vite cache
rm -rf node_modules/.vite
```

#### MongoDB connection timeout
- Verify MongoDB Atlas IP whitelist
- Check network connectivity
- Validate connection string format
- Ensure database user has proper permissions

#### Selenium crawler not working
```bash
# Restart Docker services
docker-compose down
docker-compose up -d

# Check Selenium status
curl http://localhost:4444/status
```

---

## 📈 Performance Optimization

- **Backend Caching**: Redis integration for frequently accessed data
- **Database Indexing**: MongoDB indexes on frequently queried fields
- **Lazy Loading**: Frontend components loaded on demand
- **Image Optimization**: Compressed images with lazy loading
- **API Rate Limiting**: Prevent abuse and ensure fair usage
- **CDN Integration**: Static assets served via CDN

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👥 Authors

- **Dhriti Patel** - *Initial work and development*

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- React and Vite teams for modern frontend tooling
- MongoDB for flexible NoSQL database
- Deep Java Library (DJL) for AI/ML capabilities
- Selenium for web automation
- All open-source contributors

---

## 📞 Support

For support, email your-email@example.com or open an issue in the repository.

---

## 🗺️ Roadmap

### Planned Features
- [ ] Multi-language support
- [ ] Mobile app (React Native)
- [ ] Real-time notifications (WebSocket)
- [ ] Social sharing integration
- [ ] Bookmark and save articles
- [ ] Email newsletter subscriptions
- [ ] Advanced analytics dashboard
- [ ] Sentiment analysis on articles
- [ ] Video news integration
- [ ] Podcast recommendations

---

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev)
- [MongoDB Documentation](https://docs.mongodb.com)
- [Deep Java Library](https://djl.ai)
- [Tailwind CSS](https://tailwindcss.com)

---

**Built with ❤️ using Spring Boot, React, and MongoDB**
