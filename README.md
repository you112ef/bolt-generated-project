# AIDE — Android IDE with AI and Build Export (MVP)

[![Build Status](https://github.com/you112ef/bolt-generated-project/actions/workflows/android-ci.yml/badge.svg?branch=main)](https://github.com/you112ef/bolt-generated-project/actions/workflows/android-ci.yml)
[![License](https://img.shields.io/github/license/you112ef/bolt-generated-project)](LICENSE)
[![Stars](https://img.shields.io/github/stars/you112ef/bolt-generated-project?style=social)](https://github.com/you112ef/bolt-generated-project/stargazers)
[![Issues](https://img.shields.io/github/issues/you112ef/bolt-generated-project)](https://github.com/you112ef/bolt-generated-project/issues)
[![Last Commit](https://img.shields.io/github/last-commit/you112ef/bolt-generated-project)](https://github.com/you112ef/bolt-generated-project/commits)

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.24-7F52FF?logo=kotlin&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-8.7-02303A?logo=gradle&logoColor=white)
![Android](https://img.shields.io/badge/Android-API%2026%2B-3DDC84?logo=android&logoColor=white)
![CI/CD](https://img.shields.io/badge/GitHub%20Actions-Ready-2088FF?logo=github-actions&logoColor=white)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5.14-4285F4?logo=jetpackcompose&logoColor=white)

> **Android IDE with AI integration - Build APKs using GitHub Actions without Android Studio**

This repository contains an Android app (Kotlin + Jetpack Compose + Room) that provides:
- Project templates (Android/Flutter/Console/Node/Python + sample Notes/Expenses)
- Basic editor sample (Notes & Expenses) with local Room storage
- Build screen (local build placeholder)
- AI Settings screen with secure storage (EncryptedSharedPreferences)
- AI provider/model registry derived from `Bbolt.diy` (as a Git submodule)

## Prerequisites

### Android SDK Setup
Before building the project, you need to set up the Android SDK:

1. **Install Android Studio** or **Android SDK Command-line Tools**
   - Download from [developer.android.com](https://developer.android.com/studio)
   - Install Android SDK API level 34 and build-tools 34.0.0

2. **Configure SDK Location**
   - Copy `local.properties.example` to `local.properties`
   - Edit `local.properties` and set your Android SDK path:
     ```properties
     sdk.dir=/path/to/your/android/sdk
     ```
   - Common SDK locations:
     - **macOS**: `/Users/[username]/Library/Android/sdk`
     - **Linux**: `/home/[username]/Android/Sdk`
     - **Windows**: `C:\Users\[username]\AppData\Local\Android\Sdk`

3. **Environment Variable (Optional)**
   - Alternatively, set the `ANDROID_HOME` environment variable:
     ```bash
     export ANDROID_HOME=/path/to/your/android/sdk
     ```

### Java Development Kit
- **JDK 17** is required (configured in `build.gradle.kts`)

## Getting Started

1) **Clone and initialize submodules**

```bash
git clone https://github.com/you112ef/bolt-generated-project.git
cd bolt-generated-project
git submodule update --init --recursive
```

2) **Configure Android SDK** (see Prerequisites above)

3) **Open in Android Studio** or **Build with Gradle**

### Android Studio
- Open the project root directory
- Let Gradle sync (it will download dependencies automatically)
- Run the app on an emulator or device (minSdk 26, targetSdk 34)

### Gradle CLI
```bash
# Build debug APK
./gradlew :app:assembleDebug

# Build and run tests
./gradlew clean build

# Install on connected device/emulator
./gradlew :app:installDebug
```

### 🚀 GitHub Actions Build (No Android Studio Required!)
This project supports building APKs using GitHub Actions without needing Android Studio locally:

1. **Fork this repository**
2. **Push changes** to trigger automatic builds
3. **Download APKs** from the Actions artifacts
4. **Manual trigger**: Go to Actions → Android CI → "Run workflow"

The CI automatically:
- ✅ Sets up Android SDK and accepts all licenses
- ✅ Builds debug APK using Gradle
- ✅ Runs unit tests
- ✅ Uploads APK as downloadable artifact
- ✅ Generates build reports

## Troubleshooting

### "SDK location not found" Error
If you see this error:
```
SDK location not found. Define a valid SDK location with an ANDROID_HOME environment variable or by setting the sdk.dir path in your project's local properties file
```

**Solution:**
1. Ensure `local.properties` exists in the project root
2. Set the correct path to your Android SDK in `local.properties`:
   ```properties
   sdk.dir=/path/to/your/android/sdk
   ```
3. Or set the `ANDROID_HOME` environment variable

### CI/CD Setup
The project includes GitHub Actions workflow (`.github/workflows/android-ci.yml`) that:
- Sets up JDK 17
- Installs Android SDK automatically
- Accepts all Android SDK licenses
- Builds the project

For other CI systems, ensure:
- JDK 17 is available
- Android SDK is installed with API level 34 and build-tools 34.0.0
- All Android SDK licenses are accepted

## Submodule: Bbolt.diy
The directory `vendor/Bbolt.diy` is a Git submodule referencing:
- Repo: `https://github.com/you112ef/Bbolt.diy.git`

To update:
```bash
git submodule update --remote --merge
```

## AI Providers and Models
The app exposes a registry sourced from the submodule with the following providers:
- OpenAI, Anthropic, Google, Groq, Deepseek, Mistral, Cohere, AmazonBedrock,
  HuggingFace, Hyperbolic, OpenRouter, Github, Perplexity, Together, Ollama, LMStudio, xAI

In-app: open Home → AI Settings → pick Provider/Model → paste API Key.
- Keys are stored securely via EncryptedSharedPreferences.
- Some providers (Ollama/LMStudio/Together) may require Base URL; to be added in a later iteration.

Files of interest:
- `app/src/main/kotlin/com/aide/app/ai/registry/AiRegistry.kt` (providers/models catalog)
- `app/src/main/kotlin/com/aide/app/ui/SettingsScreen.kt` (UI to select and store provider/model/api key)
- `app/src/main/kotlin/com/aide/app/data/SecureSettings.kt` (Encrypted storage)

## Project Templates
Use Home screen buttons to create starter templates in app-private storage. A `README.md` is included per template folder.

## Build and Export
The Build screen currently simulates a local build. Cloud/local build engines can be plugged via `buildsystem/` interfaces.

## License
Third-party components are used under their respective licenses. See submodule and dependencies for details.