# 🚀 Selenium WebDriver Automation Testing Project

Welcome to my **Selenium WebDriver Automation Testing** practice project! This is a collection of automation tests I created while learning Selenium by practicing on [The Internet](https://the-internet.herokuapp.com/) - a website designed for testing automation scenarios.

## 📖 About This Project

This is a **beginner-friendly** learning project! I created these tests while practicing Selenium WebDriver automation. Each test solves a specific challenge from The Internet website, and I've added detailed comments to help other beginners understand what's happening.

### 🎯 What I Did

- **Practiced Selenium**: Worked through various automation challenges from The Internet website
- **Added Educational Comments**: Tried to make the code beginner-friendly with clear explanations
- **Learned by Doing**: Each test helped me understand different Selenium concepts
- **Simple Approach**: Just straightforward Java classes with main methods - no complex frameworks!

## ✨ Features

- ✅ **30+ Test Scenarios** - Various automation challenges from The Internet website
- ✅ **Educational Comments** - Detailed explanations to help beginners understand
- ✅ **Simple Structure** - Each test is a standalone Java class
- ✅ **Real Practice** - Tests actual scenarios you'll encounter in automation

## 🛠️ Technologies Used

- **Java** - Programming language
- **Selenium WebDriver** - Browser automation framework
- **Maven** - Dependency management (for Selenium libraries)
- **ChromeDriver** - Chrome browser automation

## 📋 Test Scenarios Covered

### Basic Interactions
- ✅ Add/Remove Elements
- ✅ Checkboxes
- ✅ Dropdowns
- ✅ Input Fields
- ✅ Key Presses

### Advanced Interactions
- ✅ Drag and Drop
- ✅ Hovers
- ✅ Context Menu (Right-click)
- ✅ JavaScript Alerts
- ✅ File Upload & Download
- ✅ Secure File Download

### Complex Scenarios
- ✅ Dynamic Content
- ✅ Dynamic Controls
- ✅ Dynamic Loading
- ✅ Infinite Scroll
- ✅ Floating Menu
- ✅ JQuery UI Menus

### Browser Features
- ✅ Multiple Windows/Tabs
- ✅ Frames & Nested Frames
- ✅ iFrames
- ✅ Shadow DOM
- ✅ JavaScript Errors

### Page Elements
- ✅ Large & Deep DOM
- ✅ Sortable Data Tables
- ✅ Broken Images
- ✅ Challenging DOM
- ✅ Disappearing Elements
- ✅ Shifting Content

### Authentication & Security
- ✅ Basic Authentication
- ✅ Form Authentication
- ✅ Secure File Download

### Other Scenarios
- ✅ Status Codes
- ✅ Redirects
- ✅ Notification Messages
- ✅ Slow Resources
- ✅ Typos
- ✅ WYSIWYG Editor
- ✅ Geolocation
- ✅ Exit Intent
- ✅ Entry Ad

## 🚀 Getting Started

### Prerequisites

Before you begin, make sure you have the following installed:

- **Java JDK** (version 8 or higher) - [Download Java](https://www.oracle.com/java/technologies/downloads/)
- **Maven** - [Download Maven](https://maven.apache.org/download.cgi)
- **Chrome Browser** - [Download Chrome](https://www.google.com/chrome/)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code) - Recommended: IntelliJ IDEA

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/shroukheshmat1/the-internet-herokuapp.git
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Run a test**
   - Open any test file in `src/main/java/MyTasks/`
   - Run the `main` method directly from your IDE
   - Or compile and run from command line:
     ```bash
     javac -cp "target/classes:path/to/selenium-jar" src/main/java/MyTasks/YourTest.java
     ```

### Running Tests

Each test is a standalone Java class with a `main` method. You can run them individually:

**From IDE:**
- Right-click on any test file → Run 'main()'

**From Command Line:**
```bash
# Navigate to project directory
cd TheInternetTask

# Compile (Maven will handle dependencies)
mvn compile

# Run a specific test
java -cp "target/classes:target/dependency/*" MyTasks.YourTestName
```

## 📁 Project Structure

```
TheInternetTask/
│
├── src/
│   └── main/
│       └── java/
│           └── MyTasks/
│               ├── AddRemoveElements.java
│               ├── BasicAuth.java
│               ├── FileDownload.java
│               ├── FileUpload.java
│               ├── FormAuthentication.java
│               ├── InfiniteScroll.java
│               ├── JqueryUI.java
│               └── ... (30+ test files)
│
├── downloads/              # Downloaded files (created automatically)
├── pom.xml                 # Maven configuration
└── README.md              # This file
```

## 💡 Learning Resources

### For Beginners

Each test file includes:
- **Detailed comments** explaining what the code does
- **Step-by-step explanations** of complex concepts
- **Why and How** sections for better understanding
- **Real-world examples** and best practices

### Key Concepts Covered

- **WebDriver Basics**: Finding elements, clicking, typing
- **Synchronization**: WebDriverWait, ExpectedConditions
- **Advanced Interactions**: Actions class, JavaScript execution
- **File Handling**: Upload, download, verification
- **Browser Management**: Windows, tabs, frames
- **Error Handling**: Try-catch, assertions

## 🎓 Educational Approach

I tried to make this project **beginner-friendly**:

1. **Clear Comments**: Added explanations for complex operations
2. **Simple Structure**: Each test is straightforward and easy to follow
3. **Real Practice**: Tests actual scenarios from The Internet website
4. **Learning Focus**: Comments explain the "why" and "how" to help others learn

## 🤝 Contributing

Contributions are welcome! If you'd like to improve this project:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 Notes

- All tests use **ChromeDriver** by default
- Tests are designed to work with [The Internet](https://the-internet.herokuapp.com/)
- Some tests create a `downloads` folder in the project directory
- Make sure Chrome browser is installed and up-to-date
- This is a **learning/practice project** - not production-ready code
- Tests follow the challenges exactly as described on The Internet website

## 🐛 Troubleshooting

### Common Issues

**Issue**: `ChromeDriver` not found
- **Solution**: Make sure Chrome browser is installed. Selenium will automatically download the matching ChromeDriver.

**Issue**: Tests timeout
- **Solution**: Check your internet connection. Tests require access to the-internet.herokuapp.com

**Issue**: Download tests fail
- **Solution**: Check that the `downloads` folder has write permissions

## 📚 Additional Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [The Internet - Test Website](https://the-internet.herokuapp.com/)
- [Selenium WebDriver Tutorial](https://www.selenium.dev/documentation/webdriver/)

## 📄 License

This project is open source and available for educational purposes.

## 🙏 Acknowledgments

- [The Internet](https://the-internet.herokuapp.com/) - For providing an excellent testing playground to practice on
- [Selenium](https://www.selenium.dev/) - For the amazing automation framework
- The Selenium community - For all the helpful resources and tutorials
- GPT-5.1 Codex (OpenAI) - For reviewing scripts, filling gaps, and documenting scenarios with fresh comments

## 📧 Contact

If you have questions or suggestions, feel free to open an issue or reach out!

---

**Happy Testing! 🎉**

*This is a learning project I created while practicing Selenium. Feel free to use it as a reference, but remember - I'm still learning too! 😊*

