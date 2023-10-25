plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.fylora.tracker_presentation"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.trackerDomain))

    implementation(Coil.coilCompose)
}