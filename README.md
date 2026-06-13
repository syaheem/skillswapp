# 🔄 SkillSwapp

> A peer-to-peer skill and course tutoring exchange platform for IIUM students — built with JavaFX.

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue?style=flat-square)
![Maven](https://img.shields.io/badge/Maven-3.x-red?style=flat-square&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

---

## 📖 About

**SkillSwapp** is a desktop application that allows IIUM (International Islamic University Malaysia) students to **exchange course tutoring** with each other. Instead of paying for tutoring, students can trade skills — *"I'll teach you INFO 3305 Web Development if you teach me MATH 1301 Calculus."*

The platform connects students across different kulliyyahs (faculties) and degree programs, making peer learning more accessible and collaborative.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔍 **Course Search** | Search any course by code, name, or kulliyyah |
| 🔄 **Swap Requests** | Send and receive swap requests with other students |
| 📊 **Dashboard** | View top courses, communities, and top-rated swappers |
| 👤 **Profile Management** | Edit bio, profile picture, matric number, kulliyyah |
| 🏛️ **Communities** | Browse degree-based communities (BCS, BBA, BIT, etc.) |
| 📜 **Trade History** | View all completed swaps and shared materials |
| 📂 **Content Sharing** | Share notes, Drive links, and WhatsApp groups after a successful swap |
| ⭐ **Rating System** | Rate swappers after completing a trade |
| 💾 **Persistent Data** | All data saved to a local JSON file — no resets on restart |

---

## 🏗️ Project Structure

```
skillswapp/
├── src/
│   └── main/
│       ├── java/com/skillswapp/
│       │   ├── Launcher.java               # Entry point
│       │   ├── SkillSwappApp.java          # JavaFX Application
│       │   ├── core/
│       │   │   ├── DataStore.java          # Singleton data store (JSON persistence)
│       │   │   ├── SceneManager.java       # View/scene navigation manager
│       │   │   ├── MainLayout.java         # Sidebar + main content wrapper
│       │   │   └── ModalUtil.java          # Popup/modal utility
│       │   ├── components/
│       │   │   ├── SkillCard.java          # Horizontal course card component
│       │   │   ├── CommunityCard.java      # Community list item component
│       │   │   └── SwapperRow.java         # Top swapper row component
│       │   └── views/
│       │       ├── dashboard/
│       │       │   └── HomeDashboardView.java
│       │       ├── auth/
│       │       │   └── LoginView.java
│       │       ├── profile/
│       │       │   ├── ManageProfileView.java
│       │       │   └── OtherUserProfileView.java
│       │       ├── search/
│       │       │   └── SearchResultsView.java
│       │       ├── skills/
│       │       │   └── MyCoursesView.java
│       │       └── trades/
│       │           ├── IncomingRequestsView.java
│       │           └── TradeHistoryView.java
│       └── resources/
│           ├── styles.css                  # App-wide stylesheet
│           └── images/
│               └── course.png
└── pom.xml                                 # Maven build configuration
```

---

## 🚀 How to Run

### Prerequisites

Make sure you have the following installed:

- **Java 21** (JDK) — [Download here](https://adoptium.net/)
- **Apache Maven** (or use NetBeans bundled Maven)
- **Apache NetBeans 20+** (recommended IDE)

### Option 1 — Run from NetBeans (Easiest)

1. Open **Apache NetBeans**
2. Go to **File → Open Project**
3. Navigate to the `skillswapp` folder and open it
4. Click the **Run** button (▶) or press `F6`

### Option 2 — Run from Command Line (Maven)

```bash
# Clone the repository
git clone https://github.com/syaheem/skillswapp.git
cd skillswapp

# Compile and run
mvn clean compile
mvn javafx:run
```

> ⚠️ If you get a `clean` error, the app may already be running. Stop it first, then retry.

### Option 3 — Run the JAR directly

```bash
# Build the JAR
mvn package

# Run
java -jar target/SkillSwapp-1.0-SNAPSHOT.jar
```

---

## 🖥️ Screenshots

### Dashboard — Top Courses for Swap
> Clean horizontal course cards with owner name, rating, and swap button.

### Community Panel
> Degree-based communities (BCS, BBA, BIT, B.ARCH, B.ENG) with member counts.

### Top Skill Swappers
> Ranked list of top-rated swappers with direct profile access.

### Trade History & Shared Content
> View completed swaps and access shared Google Drive folders and WhatsApp groups.

---

## 🗂️ Data Persistence

All user data is saved to a local **`datastore.json`** file in the project root. This includes:

- Your profile (bio, email, matric, kulliyyah, avatar)
- Your courses
- Incoming and outgoing swap requests
- Trade history and shared content links

The file is automatically created on first run and updated whenever you make changes. **Your data will not reset when you close and reopen the app.**

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| **Java 21** | Core programming language |
| **JavaFX 21** | UI framework for desktop app |
| **Maven** | Build and dependency management |
| **Gson** | JSON serialization for data persistence |
| **OpenJFX** | Cross-platform JavaFX library |

---

## 👨‍💻 Developer

**Syaheem**  
Bachelor of Computer Science (BCS)  
Kulliyyah of Information and Communication Technology (KICT)  
International Islamic University Malaysia (IIUM)

---

## 📜 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

> *"Swap Knowledge, Grow Together"* 🌱
