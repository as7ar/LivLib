plugins {
    id("java")
    id("com.gradleup.shadow")
}

group = "kr.as7ar"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jsoup:jsoup:1.15.3")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("me.friwi:jcefmaven:143.0.14")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

/*tasks.shadowJar {
    archiveClassifier.set("")
    mergeServiceFiles()

    archiveFileName.set("wfLiv-$version.jar")
}*/
