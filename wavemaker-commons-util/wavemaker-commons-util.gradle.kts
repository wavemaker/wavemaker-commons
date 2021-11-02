plugins {
    `java-library-maven-publish`
}

group ="com.wavemaker.commons"

dependencies {
    implementation(enforcedPlatform(libs.boms.springFramework.get()))
    implementation(enforcedPlatform(libs.boms.jackson.get()))
    implementation(projects.wavemakerToolsApidocsCore)
    implementation(libs.slf4j.api)
    implementation(libs.commons.io)
    implementation(libs.commons.codec)
    implementation(libs.commons.lang3)
    implementation(libs.commons.configuration2)
    implementation(libs.commons.text) {
        because("This is an optional dependency for commons-configuration2 which is needed for PropertiesConfiguration class usage by us")
    }
    implementation(libs.commons.collections4)
    implementation(libs.guava)
    implementation(libs.spring.webmvc)
    implementation(libs.jackson.core)
    implementation(libs.jackson.databind)
    implementation(libs.gson)
    implementation(libs.javax.jaxb.api)
    compileOnly(libs.javax.servlet.api)
    testImplementation(libs.test.testng)
    testImplementation(libs.test.mockito.all)
    testImplementation(libs.test.hamcrest.all)
    testImplementation(libs.test.junit4)
    testImplementation(libs.javax.servlet.api)
}

configurations.register("testConfiguration") {
    extendsFrom(configurations.testImplementation.get())
}
tasks.register<Jar>(name = "testsJar") {
    from(project.sourceSets.test.get().output)
    description = "create a jar from the test source set"
    archiveClassifier.set("test")
}

artifacts {
    add("testConfiguration", tasks.getByName("testsJar"))
}

tasks.test {
    exclude("**/*")
}

javaLibraryMavenPublish {
    scmUrl="git:https://github.com/wavemaker/wavemaker-commons.git"
}