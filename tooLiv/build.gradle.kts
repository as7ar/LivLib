plugins {
    id("java")
    id("com.gradleup.shadow")
}

group = "kr.astar"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jsoup:jsoup:1.15.3")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("io.reactivex.rxjava3:rxjava:3.1.10")

    implementation("org.seleniumhq.selenium:selenium-java:4.34.0")
    implementation("io.github.bonigarcia:webdrivermanager:6.2.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    archiveClassifier.set("")
    mergeServiceFiles()

    archiveFileName.set("tooLiv-$version.jar")
}