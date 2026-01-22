plugins {
    id("java")
    id("com.gradleup.shadow") version "9.2.2"
}

group = "kr.as7ar"
version = "1.0.0"

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("io.socket:socket.io-client:2.1.1")
        implementation("io.reactivex.rxjava3:rxjava:3.1.10")
        implementation("org.jsoup:jsoup:1.15.3")
        implementation("org.seleniumhq.selenium:selenium-java:4.34.0")
        implementation("io.github.bonigarcia:webdrivermanager:6.2.0")
        implementation("org.java-websocket:Java-WebSocket:1.5.5")

        implementation("com.google.code.gson:gson:2.13.1")

        implementation("com.squareup.retrofit2:retrofit:2.11.0")
        implementation("com.squareup.retrofit2:converter-gson:2.11.0")
        implementation("com.squareup.okhttp3:okhttp:4.12.0")

        compileOnly("org.projectlombok:lombok:1.18.32")
        annotationProcessor("org.projectlombok:lombok:1.18.32")

        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.build {
        dependsOn("shadowJar")
    }

}