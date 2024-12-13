# Compose Multiplatform Project

This repository contains a **Kotlin Multiplatform Project (KMP)** developed as part of the [Compose Multiplatform iOS & Android Development with Kotlin](https://www.udemy.com/course/compose-multiplatform-iosandroid-development-with-kotlin/) course on Udemy. The project showcases the capabilities of Kotlin Multiplatform to create native applications for **Android** and **iOS** using shared business logic and a single codebase for UI with Jetpack Compose.

---

## Features
- **Shared Codebase**: The project uses Kotlin Multiplatform for shared business logic.
- **Jetpack Compose UI**: Compose Multiplatform for shared UI components across Android and iOS.
- **Platform-Specific Integrations**: Custom implementations for platform-specific requirements.
- **Dependency Injection**: Utilizes Koin for dependency injection.
- **Modern Development Practices**: Includes coroutines for asynchronous programming and Compose navigation for routing.

---

## Technologies Used
### Core Frameworks and Libraries
- **Kotlin Multiplatform (KMP)**: Enables writing shared logic for multiple platforms.
- **Jetpack Compose Multiplatform**: Allows shared UI development across Android and iOS.
- **Koin**: Lightweight dependency injection library.
- **Coroutines**: For asynchronous programming.

### Build Tools
- **Gradle**: Multi-platform project configuration and dependency management.

### Testing
- **Kotlin Test**: For unit tests.

---

## Getting Started

### Prerequisites
- **Android Studio**: Latest version with KMP plugin installed.
- **Xcode**: To build and run the iOS application.
- **Kotlin Multiplatform Plugin**: Installed in your IDE.

### Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/mehrankasebvatan/CurrencyAppKmp.git
   ```
2. Open the project in Android Studio.
3. Sync the Gradle files.
4. Run the Android app or configure the iOS build via Xcode.

---

## Project Structure
- `commonMain/`: Contains shared business logic and Compose UI components.
- `androidMain/`: Android-specific implementation.
- `iosMain/`: iOS-specific implementation.

---

## Screenshots
| Main Page | Currency Dialog |
|-----------------|-------------|
| ![Main Page](https://github.com/mehrankasebvatan/CurrencyAppKmp/blob/main/screenshots/image1.png) | ![Main Dialog](https://github.com/mehrankasebvatan/CurrencyAppKmp/blob/main/screenshots/image2.png) |

---

## Resources
- [Compose Multiplatform iOS & Android Development with Kotlin](https://www.udemy.com/course/compose-multiplatform-iosandroid-development-with-kotlin/)

---

## License
This project is licensed under the MIT License. Feel free to use and adapt the code for your own projects.

---

## Contributing
Contributions are welcome! Please feel free to submit issues or pull requests.

---

## Contact
For any questions, feel free to reach out via email at [contact@kasebvatan.ir].
