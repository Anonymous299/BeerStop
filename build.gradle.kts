buildscript {
    dependencies {
        classpath(libs.hiltClasspath)
    }
}
plugins{
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlin) apply false
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}