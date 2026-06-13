package com.skillswapp.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static DataStore instance;

    public static class Course {
        public String icon;
        public String code;
        public String fullName;
        public String desc;
        public Course() {}
        public Course(String icon, String code, String fullName, String desc) {
            this.icon = icon; this.code = code; this.fullName = fullName; this.desc = desc;
        }
    }

    public static class TradeRequest {
        public String user;
        public String offered;
        public String requested;
        public String timeAgo;
        public String status;
        public TradeRequest() {}
        public TradeRequest(String user, String offered, String requested, String timeAgo, String status) {
            this.user = user; this.offered = offered; this.requested = requested; this.timeAgo = timeAgo; this.status = status;
        }
    }

    public static class TradeHistory {
        public String user;
        public String gave;
        public String got;
        public String date;
        public boolean rated;
        public String driveLink = "";
        public String whatsappLink = "";
        public String notesText = "";
        public List<String> sharedFiles = new ArrayList<>();

        public TradeHistory() {}
        public TradeHistory(String user, String gave, String got, String date, boolean rated) {
            this.user = user; this.gave = gave; this.got = got; this.date = date; this.rated = rated;
            this.driveLink = "https://drive.google.com/drive/folders/mock-folder-id";
            this.whatsappLink = "https://chat.whatsapp.com/mock-chat-id";
            this.notesText = "Hey, let's share learning materials here. Happy swapping!";
            this.sharedFiles.add("Syllabus_" + gave.replace(" ", "") + ".pdf");
            this.sharedFiles.add("Lecture_Notes_" + got.replace(" ", "") + ".zip");
        }
    }

    public static class GlobalCourse {
        public String icon;
        public String code;
        public String title;
        public String desc;
        public int requests;
        public double rating;
        public String ownerName;
        public GlobalCourse() {}
        public GlobalCourse(String icon, String code, String title, String desc, int requests, double rating) {
            this.icon = icon; this.code = code; this.title = title; this.desc = desc; this.requests = requests; this.rating = rating;
            this.ownerName = getRandomOwnerForCourse(code);
        }
        private static String getRandomOwnerForCourse(String code) {
            String[] owners = {"Syaheem (BCS)", "Fatimah (BBA)", "Ahmad Rafiq (MECH)", "Nurul Ain (BIT)", "Haziq (BARCH)", "Ahmad (LAW)"};
            int hash = Math.abs(code.hashCode());
            return owners[hash % owners.length];
        }
    }

    public List<Course> myCourses = new ArrayList<>();
    public List<GlobalCourse> allAvailableCourses = new ArrayList<>();
    public List<TradeRequest> incomingRequests = new ArrayList<>();
    public List<TradeRequest> outgoingRequests = new ArrayList<>();
    public List<TradeHistory> tradeHistory = new ArrayList<>();
    
    public String currentViewingUser = "Syaheem";
    public String currentSearchQuery = "";
    
    public String myBio = "Computer Science major. Looking to trade tutoring for my courses.";
    public String myEmail = "john.doe@live.iium.edu.my";
    public String myMatric = "2110234";
    public String myKulliyyah = "KICT";
    public String myAvatarPath = "";
    public transient TradeHistory currentViewingSwap;

    private DataStore() {
        // Initialize with default mock data
        myCourses.add(new Course("💻", "INFO 3305", "Web Development", "Learn to build modern websites using HTML, CSS, and JS."));
        myCourses.add(new Course("🗄️", "INFO 2201", "Database Systems", "Introduction to Database Systems and SQL."));
        myCourses.add(new Course("📊", "MATH 1301", "Calculus I", "Calculus and Analytical Geometry."));

        // Initialize 50 diverse mock courses
        // KICT - University Required Courses
        allAvailableCourses.add(new GlobalCourse("🕌", "UNGS 1301", "Basic Philosophy and Islamic Worldview", "University required course.", 50, 4.5));
        allAvailableCourses.add(new GlobalCourse("🕌", "UNGS 2290", "Knowledge & Civilization in Islam", "University required course.", 40, 4.3));
        allAvailableCourses.add(new GlobalCourse("🕌", "UNGS 2380", "Ethics and Fiqh of Contemporary Issues", "University required course.", 45, 4.6));
        allAvailableCourses.add(new GlobalCourse("📖", "TQTD 1002", "Tilawah Al-Quran 1", "University required course.", 30, 4.8));
        allAvailableCourses.add(new GlobalCourse("📖", "TQTD 2002", "Tilawah Al-Quran 2", "University required course.", 25, 4.7));
        allAvailableCourses.add(new GlobalCourse("🕌", "LQAD 2003", "Arabic for Ibadah", "University required course.", 20, 4.4));
        allAvailableCourses.add(new GlobalCourse("🤝", "CCUB 1061", "Usrah 1", "University required course.", 60, 4.9));
        allAvailableCourses.add(new GlobalCourse("✍️", "LEED 1301", "English for Academic Writing", "University required course.", 35, 4.5));

        // KICT - Core Computing Courses
        allAvailableCourses.add(new GlobalCourse("💻", "BICS 1301", "Elements of Programming", "Core programming concepts.", 40, 4.6));
        allAvailableCourses.add(new GlobalCourse("🖥️", "BICS 1302", "Introduction to Computer Organization", "Computer hardware basics.", 38, 4.4));
        allAvailableCourses.add(new GlobalCourse("🌐", "BICS 1303", "Computer Networking", "Networking principles.", 35, 4.7));
        allAvailableCourses.add(new GlobalCourse("⚙️", "BICS 2305", "Operating Systems", "OS concepts and mechanics.", 30, 4.5));
        allAvailableCourses.add(new GlobalCourse("🗄️", "BIIT 1301", "Database Programming", "SQL and database management.", 45, 4.8));
        allAvailableCourses.add(new GlobalCourse("📊", "BIIT 1303", "System Analysis & Design", "Systems development life cycle.", 28, 4.3));
        allAvailableCourses.add(new GlobalCourse("🕌", "BIIT 3304", "ICT & Islam", "Islamic perspectives on technology.", 20, 4.9));

        // KICT - Discipline Core Courses
        allAvailableCourses.add(new GlobalCourse("☕", "BICS 1304", "Object-Oriented Programming", "OOP using Java or C++.", 50, 4.7));
        allAvailableCourses.add(new GlobalCourse("🔢", "BICS 1305", "Discrete Structures", "Math for computer science.", 32, 4.2));
        allAvailableCourses.add(new GlobalCourse("🔌", "BICS 1306", "Digital & Embedded Systems", "Digital logic and microcontrollers.", 25, 4.4));
        allAvailableCourses.add(new GlobalCourse("🏢", "BICS 2301", "Enterprise Networks", "Advanced networking.", 22, 4.5));
        allAvailableCourses.add(new GlobalCourse("📚", "BICS 2302", "Data Structures and Algorithms", "Algorithmic problem solving.", 55, 4.8));
        allAvailableCourses.add(new GlobalCourse("🧠", "BICS 2303", "Intelligent Systems", "Introduction to AI.", 40, 4.9));
        allAvailableCourses.add(new GlobalCourse("⚙️", "BICS 2304", "Computer Architecture & Assembly", "Assembly language programming.", 20, 4.1));
        allAvailableCourses.add(new GlobalCourse("🛠️", "BICS 2306", "Software Engineering", "Software development processes.", 35, 4.6));
        allAvailableCourses.add(new GlobalCourse("📱", "BICS 3301", "Cross-Platform Software Dev", "Mobile and desktop development.", 38, 4.8));
        allAvailableCourses.add(new GlobalCourse("⏳", "BICS 3302", "Computation and Complexity", "Theory of computation.", 15, 4.0));
        allAvailableCourses.add(new GlobalCourse("⚡", "BICS 3303", "Parallel & Distributed Systems", "Distributed computing concepts.", 18, 4.5));
        allAvailableCourses.add(new GlobalCourse("🎨", "BIIT 2301", "User Experience Design", "UI/UX principles.", 42, 4.9));

        // KICT - Field Electives (Samples)
        allAvailableCourses.add(new GlobalCourse("📈", "BICS 4340", "Machine Learning", "Artificial Intelligence elective.", 30, 4.8));
        allAvailableCourses.add(new GlobalCourse("🤖", "BICS 4345", "Generative AI Fundamentals", "Artificial Intelligence elective.", 45, 5.0));
        allAvailableCourses.add(new GlobalCourse("🛡️", "BICS 4352", "Penetration Testing", "Security elective.", 25, 4.9));
        allAvailableCourses.add(new GlobalCourse("📊", "BICS 4361", "Data Science", "Data Engineering elective.", 35, 4.7));

        // KOE (ENGR, MECH, ELEC)
        allAvailableCourses.add(new GlobalCourse("⚙️", "ENGR 1101", "Intro to Engineering", "Engineering principles.", 15, 4.2));
        allAvailableCourses.add(new GlobalCourse("⚙️", "ENGR 2201", "Statics and Dynamics", "Engineering Mechanics.", 20, 4.5));
        allAvailableCourses.add(new GlobalCourse("🔥", "MECH 2100", "Thermodynamics", "Thermodynamics laws.", 18, 4.1));
        allAvailableCourses.add(new GlobalCourse("💧", "MECH 3200", "Fluid Mechanics", "Fluid dynamics.", 22, 4.3));
        allAvailableCourses.add(new GlobalCourse("⚡", "ELEC 2101", "Circuit Analysis", "Electrical circuits.", 25, 4.4));
        allAvailableCourses.add(new GlobalCourse("🔌", "ELEC 3102", "Digital Logic Design", "Logic gates and circuits.", 16, 4.6));
        allAvailableCourses.add(new GlobalCourse("🏭", "MFG 3100", "Manufacturing Processes", "Manufacturing methods.", 12, 4.2));
        allAvailableCourses.add(new GlobalCourse("✈️", "AERO 4100", "Aerodynamics", "Flight dynamics.", 8, 4.9));
        allAvailableCourses.add(new GlobalCourse("🧱", "MATL 2100", "Materials Science", "Properties of materials.", 14, 4.4));
        allAvailableCourses.add(new GlobalCourse("🏗️", "CIVE 2100", "Structural Analysis", "Civil engineering structures.", 19, 4.5));

        // KENMS (ECON, ACC, MGT, FIN)
        allAvailableCourses.add(new GlobalCourse("📉", "ECON 1101", "Microeconomics", "Microeconomic theory.", 35, 4.6));
        allAvailableCourses.add(new GlobalCourse("📈", "ECON 1102", "Macroeconomics", "Macroeconomic theory.", 30, 4.5));
        allAvailableCourses.add(new GlobalCourse("📒", "ACC 1101", "Financial Accounting", "Accounting principles.", 40, 4.4));
        allAvailableCourses.add(new GlobalCourse("📊", "ACC 2201", "Management Accounting", "Managerial accounting.", 28, 4.5));
        allAvailableCourses.add(new GlobalCourse("👔", "MGT 2000", "Principles of Management", "Management concepts.", 22, 4.3));
        allAvailableCourses.add(new GlobalCourse("👥", "MGT 3100", "Organizational Behavior", "Behavior in orgs.", 15, 4.6));
        allAvailableCourses.add(new GlobalCourse("💰", "FIN 3000", "Corporate Finance", "Financial management.", 20, 4.5));
        allAvailableCourses.add(new GlobalCourse("🏦", "FIN 4100", "Islamic Banking", "Islamic financial system.", 25, 4.8));
        allAvailableCourses.add(new GlobalCourse("📢", "MKT 3000", "Principles of Marketing", "Marketing strategies.", 30, 4.6));
        allAvailableCourses.add(new GlobalCourse("🤝", "ISF 2100", "Islamic Finance", "Principles of Islamic Finance.", 18, 4.7));

        // KAED (ARCH, AAD, LARC)
        allAvailableCourses.add(new GlobalCourse("🏛️", "ARCH 1101", "Intro to Architecture", "History of architecture.", 22, 4.7));
        allAvailableCourses.add(new GlobalCourse("📐", "ARCH 2100", "Architecture Design Studio", "Design studio 1.", 15, 4.8));
        allAvailableCourses.add(new GlobalCourse("✏️", "AAD 1100", "Basic Drawing", "Technical drawing.", 20, 4.5));
        allAvailableCourses.add(new GlobalCourse("🛋️", "AAD 2200", "Interior Design", "Interior spaces.", 18, 4.6));
        allAvailableCourses.add(new GlobalCourse("🌲", "LARC 2100", "Landscape Architecture", "Landscape design.", 12, 4.7));
        allAvailableCourses.add(new GlobalCourse("🏙️", "URP 3100", "Urban Planning", "City and regional planning.", 16, 4.4));

        // KIRKHS (RK, PSYC, SOC, COMM, ENGL)
        allAvailableCourses.add(new GlobalCourse("🕌", "RK 1100", "Islamic Worldview", "Islamic perspectives.", 50, 4.5));
        allAvailableCourses.add(new GlobalCourse("📖", "RK 2200", "Usul al-Fiqh", "Islamic jurisprudence.", 35, 4.6));
        allAvailableCourses.add(new GlobalCourse("🧠", "PSYC 1000", "Intro to Psychology", "Basic psychology.", 45, 4.8));
        allAvailableCourses.add(new GlobalCourse("👥", "PSYC 2100", "Social Psychology", "Human social behavior.", 30, 4.7));
        allAvailableCourses.add(new GlobalCourse("🌍", "SOC 1100", "Intro to Sociology", "Study of society.", 25, 4.4));
        allAvailableCourses.add(new GlobalCourse("📱", "COMM 2100", "Digital Media", "Digital communication.", 28, 4.6));
        allAvailableCourses.add(new GlobalCourse("📰", "COMM 3100", "Mass Communication", "Media and society.", 20, 4.5));
        allAvailableCourses.add(new GlobalCourse("📚", "ENGL 1100", "English Literature", "Literary analysis.", 18, 4.7));

        // AIKOL (LAW)
        allAvailableCourses.add(new GlobalCourse("⚖️", "LAW 1100", "Malaysian Legal System", "Intro to Malaysian Law.", 35, 4.6));
        allAvailableCourses.add(new GlobalCourse("📝", "LAW 2100", "Contract Law", "Law of contracts.", 25, 4.5));
        allAvailableCourses.add(new GlobalCourse("🚨", "LAW 3100", "Criminal Law", "Criminal justice system.", 30, 4.8));
        allAvailableCourses.add(new GlobalCourse("💍", "LAW 4100", "Islamic Family Law", "Family law in Islam.", 20, 4.7));
        incomingRequests.add(new TradeRequest("Ahmad", "MATH 1301", "INFO 3305", "2 hours ago", "Pending"));
        incomingRequests.add(new TradeRequest("Fatimah", "COMM 2100", "INFO 2201", "5 hours ago", "Pending"));
        incomingRequests.add(new TradeRequest("Haziq", "ENGR 2201", "MATH 1301", "1 day ago", "Pending"));
        incomingRequests.add(new TradeRequest("Nurul Ain", "PSYC 1000", "INFO 3305", "2 days ago", "Pending"));

        outgoingRequests.add(new TradeRequest("Syaheem", "INFO 3305", "INFO 4402", "3 hours ago", "Pending"));
        outgoingRequests.add(new TradeRequest("Fatimah", "MATH 1301", "COMM 2100", "1 day ago", "Pending"));
        outgoingRequests.add(new TradeRequest("Haziq", "INFO 2201", "ENGR 2201", "3 days ago", "Accepted ✅"));

        tradeHistory.add(new TradeHistory("Haziq", "INFO 2201", "ENGR 2201", "Oct 12, 2025", true));
        tradeHistory.add(new TradeHistory("Syaheem", "INFO 3305", "INFO 4402", "Sep 28, 2025", false));
        tradeHistory.add(new TradeHistory("Fatimah", "MATH 1301", "COMM 2100", "Aug 15, 2025", false));
        tradeHistory.add(new TradeHistory("Ahmad", "CSCI 4301", "BIOL 1105", "Jul 3, 2025", true));
        tradeHistory.add(new TradeHistory("Nurul Ain", "PSYC 1000", "ECON 1101", "Jun 20, 2025", true));
    }

    private static final String FILE_PATH = "datastore.json";

    public void saveToFile() {
        try (java.io.Writer writer = new java.io.FileWriter(FILE_PATH)) {
            com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
            gson.toJson(this, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        java.io.File file = new java.io.File(FILE_PATH);
        if (file.exists()) {
            try (java.io.Reader reader = new java.io.FileReader(file)) {
                com.google.gson.Gson gson = new com.google.gson.Gson();
                DataStore loaded = gson.fromJson(reader, DataStore.class);
                if (loaded != null) {
                    if (loaded.myCourses != null) this.myCourses = loaded.myCourses;
                    if (loaded.allAvailableCourses != null && !loaded.allAvailableCourses.isEmpty()) this.allAvailableCourses = loaded.allAvailableCourses;
                    if (loaded.incomingRequests != null) this.incomingRequests = loaded.incomingRequests;
                    if (loaded.outgoingRequests != null) this.outgoingRequests = loaded.outgoingRequests;
                    if (loaded.tradeHistory != null) this.tradeHistory = loaded.tradeHistory;
                    if (loaded.myBio != null) this.myBio = loaded.myBio;
                    if (loaded.myEmail != null) this.myEmail = loaded.myEmail;
                    if (loaded.myMatric != null) this.myMatric = loaded.myMatric;
                    if (loaded.myKulliyyah != null) this.myKulliyyah = loaded.myKulliyyah;
                    if (loaded.myAvatarPath != null) this.myAvatarPath = loaded.myAvatarPath;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            saveToFile();
        }
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
            instance.loadFromFile();
        }
        return instance;
    }

    public String getCurrentDateFormatted() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        return date.format(formatter);
    }
}
