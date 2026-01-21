rootProject.name = "LivLib"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()

        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://jitpack.io")
    }
}
include("uLiv")
include("TooLiv")
include("wfLib")