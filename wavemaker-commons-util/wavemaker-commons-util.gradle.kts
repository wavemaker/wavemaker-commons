plugins {
    `java-library-maven-publish`
}

group ="com.wavemaker.commons"

dependencies {
    implementation(platform(project(":wavemaker-commons")))
    implementation("com.wavemaker.tools.apidocs:wavemaker-tools-apidocs-core")
    implementation("org.slf4j:slf4j-api")
    implementation("commons-io:commons-io")
    implementation("commons-codec:commons-codec")
    implementation("org.apache.commons:commons-lang3")
    implementation("org.apache.commons:commons-configuration2")
    implementation("org.apache.commons:commons-text") {
        because("This is an optional dependency for commons-configuration2 which is needed for PropertiesConfiguration class usage by us")
    }
    implementation("org.apache.commons:commons-collections4")
    implementation("org.springframework:spring-webmvc")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.google.code.gson:gson")
    compileOnly("javax.servlet:javax.servlet-api")
    testImplementation("org.testng:testng")
    testImplementation("org.mockito:mockito-all")
    testImplementation("org.hamcrest:hamcrest-all")
    testImplementation("junit:junit")
    testImplementation("javax.servlet:javax.servlet-api")
}

configurations.register("testConfiguration") {
    extendsFrom(configurations.testCompile.get())
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