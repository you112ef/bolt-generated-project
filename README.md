# AIDE — Android IDE with AI and Build Export (MVP)

This repository contains an Android app (Kotlin + Jetpack Compose + Room) that provides:
- Project templates (Android/Flutter/Console/Node/Python + sample Notes/Expenses)
- Basic editor sample (Notes & Expenses) with local Room storage
- Build screen (local build placeholder)
- AI Settings screen with secure storage (EncryptedSharedPreferences)
- AI provider/model registry derived from `Bbolt.diy` (as a Git submodule)

## Getting Started

1) Clone and initialize submodules

```bash
git clone https://github.com/you112ef/bolt-generated-project.git
cd bolt-generated-project
git submodule update --init --recursive
```

2) Open in Android Studio (JDK 17, Android SDK 34)
- Open the project root
- Let Gradle sync
- Run the app on an emulator or device (minSdk 26)

3) Gradle CLI (optional)
If you prefer CLI build, generate wrapper then build:
```bash
gradle wrapper --gradle-version 8.7
./gradlew :app:assembleDebug
```

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