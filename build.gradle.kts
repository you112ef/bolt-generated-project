buildscript {}

plugins {
    // Versions are controlled in app/build.gradle.kts via BOMs and constants
}

allprojects {
    tasks.withType<org.gradle.api.tasks.testing.Test> {
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}