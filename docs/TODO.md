# AIDE — TODO Plan (Fixes and Improvements)

This checklist targets a stable, reproducible build (local and CI) and a clean, error-free app. Priorities: P0 (blocker), P1 (near-term), P2 (nice-to-have).

## P0 — CI/CD Stabilization (Blockers)
- [ ] Commit Gradle Wrapper (required for validation)
  - Generate wrapper locally with Gradle 8.7 and commit the files: `gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.properties`, `gradle/wrapper/gradle-wrapper.jar`.
  - Add `distributionSha256Sum` to `gradle-wrapper.properties`.
  - Acceptance: `gradle/actions/setup-gradle@v3` passes `validate-wrappers: true` without generating wrapper during CI.
- [ ] Remove apt-installed Gradle from CI
  - Use only `./gradlew` in workflow; delete the step that installs Gradle via apt and the runtime wrapper generation.
  - Acceptance: CI uses repo wrapper only; no deprecation warnings from `setup-gradle`.
- [ ] Make Android SDK license acceptance fully non-interactive
  - Keep `android-actions/setup-android@v3` with `accept-android-sdk-licenses: true`.
  - Add explicit non-interactive fallback for both paths: `${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager` and `${ANDROID_SDK_ROOT}/cmdline-tools/16.0/bin/sdkmanager`.
  - Acceptance: No “Accept? (y/N)” prompts; CI proceeds past SDK install consistently.
- [ ] Pin Android SDK components to match app config
  - Ensure CI installs: `platform-tools`, `platforms;android-34`, `build-tools;34.0.0`.
  - Align `compileSdk`, `targetSdk`, and `buildToolsVersion` where applicable.
  - Acceptance: AAPT2/resource tasks succeed; no missing platform/build-tools errors.
- [ ] Consistent Gradle flags for stability
  - Keep `--no-parallel`, `--stacktrace`, `--info` until green; re-enable parallel later.
  - Publish Build Scans for troubleshooting.
  - Acceptance: CI completes `:app:assembleDebug` successfully.

## P0 — Build/Config Hygiene
- [ ] Align Kotlin/Compose versions
  - Use Kotlin 1.9.24 + `org.jetbrains.kotlin.plugin.compose`.
  - Remove manual `composeOptions` if compose plugin is applied.
  - Use a stable Compose BOM (e.g., 2024.06.00) across all Compose libs.
  - Acceptance: No compose compiler/version mismatch errors.
- [ ] Room + KSP consistency and migrations
  - Use KSP plugin version matching Kotlin (1.9.24) and Room compiler version that supports it.
  - Add real migrations for DB version bump to 2 (for `Project`, `BuildJob`) to avoid destructive fallback.
  - Acceptance: App starts without Room migration crashes; DAO tests pass.
- [ ] WorkManager configuration correctness
  - Ensure `AndroidManifest.xml` sets `android:name=".AideApplication"`.
  - Acceptance: No WorkManager initialization warnings; background tasks schedule properly.
- [ ] Resource/themes cleanup
  - Standardize on Material3 theme; remove unused legacy styles; keep coherent colors.
  - Acceptance: No resource linking failures (`aapt2`) and theme renders correctly.

## P1 — Security and Settings
- [ ] Harden `EncryptedSharedPreferences` usage
  - Ensure `MasterKey` built with AES256_GCM; handle API level compatibility per `minSdk`.
  - Prevent logging of API keys; redact sensitive values in logs and BuildConfig.
  - Acceptance: Lint passes security checks; logs show redacted keys.
- [ ] Settings UI robustness
  - Validate API key input; show provider/model constraints; disable save if invalid.
  - Persist/restore state reliably; add simple ViewModel-based state holder.
  - Acceptance: Settings survive process death; values used by providers.

## P1 — AI Integration
- [ ] Implement `CloudAiProvider` minimal real call
  - Add OpenAI-compatible client abstraction with base URL + key.
  - Support model selection from `AiRegistry`.
  - Acceptance: Test call returns response in-app (mock in debug if needed).
- [ ] Extend `AiRegistry`
  - Sync models/providers with `vendor/Bbolt.diy` data; tag which require API keys.
  - Acceptance: Providers/models list matches source; appears in Settings.
- [ ] Local AI placeholder improvements
  - Implement cancellable stubs; add progress callbacks.
  - Acceptance: No ANRs; background work cancellable.

## P1 — Build/Export (Local build engine)
- [ ] Define export flow (placeholder → actionable)
  - Add UI to pick output type (APK/AAB), keystore config, and start build.
  - Save `BuildJob` with status logs; stream logs to UI.
  - Acceptance: Simulated build completes and produces a dummy artifact file.
- [ ] Keystore management
  - Securely store keystore alias/password via `EncryptedSharedPreferences`.
  - Acceptance: Values used only during signing step (later real implementation).

## P1 — Testing & Quality Gates
- [ ] Unit tests for DAOs/Repositories
  - In-memory Room; cover CRUD + simple queries.
  - Acceptance: >80% coverage for data layer.
- [ ] UI tests for Settings
  - Compose tests to verify provider/model/key flows.
  - Acceptance: Stable green tests on CI.
- [ ] Static analysis
  - Add ktlint + Detekt; wire into CI.
  - Acceptance: CI fails on violations; baseline checked in initially if needed.
- [ ] Android Lint
  - Enable `lintDebug` in CI; fix critical issues.
  - Acceptance: No fatal lint errors.

## P2 — Developer Experience
- [ ] Gradle build cache and configuration cache
  - Re-enable parallel and caching after stability; measure build time improvement.
  - Acceptance: <30% CI time reduction on subsequent runs.
- [ ] Better logs/diagnostics
  - Pipe build logs into artifacts; keep last N runs.
  - Acceptance: Easy post-mortem with a single artifact download.

## P2 — Product Polish
- [ ] Home/Editor UX
  - Better empty states; inline error banners; responsive layout on tablets.
  - Acceptance: No Compose warnings; accessibility checks pass basic thresholds.
- [ ] Plugins SDK (skeleton)
  - Define SPI for providers/tools; load from classpath with versioning metadata.
  - Acceptance: A demo plugin registers a fake tool and shows up in UI.

---

## Tracking and Acceptance
- Create issues per checkbox with labels: `P0`, `P1`, `P2`, `CI`, `Build`, `AI`, `Security`, `UX`, `Testing`.
- A change is “done” when: code merged, CI green, and acceptance criteria met.
- After P0 completes: switch CI to parallel builds and enable caches; then tackle P1.