plugins {
    `java-library`
    `maven-publish`
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
    implementation("org.apache.commons:commons-collections4")
    implementation("org.springframework:spring-webmvc")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.google.code.gson:gson:2.8.5")
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

java {
    withSourcesJar()
}

publishing {
    configurePublicationToDist(this)
    publications {
        create<MavenPublication>("maven") {
            artifactId = project.extensions.extraProperties.get("basename") as String
            from(components["java"])
            withoutBuildIdentifier()
            pom {
                withXml {
                    updateGeneratedPom(asNode(), mapOf(
                        "compile" to configurations.implementation.get().dependencies + configurations.api.get().dependencies,
                        "provided" to configurations.compileOnly.get().dependencies
                    ))
                }
            }
        }
    }
}