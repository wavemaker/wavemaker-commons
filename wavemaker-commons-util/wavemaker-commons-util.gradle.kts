plugins {
    `java-library-maven-publish`
}

group ="com.wavemaker.commons"

dependencies {
    implementation(platform(project(":wavemaker-commons")))
    implementation("com.wavemaker.tools.apidocs:wavemaker-tools-apidocs-core")
    implementation("org.slf4j:slf4j-api")
    implementation("org.apache.commons:commons-configuration2")
    implementation("commons-io:commons-io")
    implementation("org.apache.commons:commons-lang3")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-web")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    compileOnly("javax.servlet:javax.servlet-api")
    testImplementation("junit:junit")
    testImplementation("org.testng:testng")
    testImplementation("org.hamcrest:hamcrest-all")
    testImplementation("org.mockito:mockito-all")
}

tasks.test {
    exclude("**/*")
}

javaLibraryMavenPublish {
    scmUrl="git:https://github.com/wavemaker/wavemaker-commons.git"
}