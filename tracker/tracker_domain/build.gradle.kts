plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.fylora.tracker_domain"
}

dependencies {
    implementation(project(Modules.core))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}