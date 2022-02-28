plugins {
    `java-library-maven-publish`
}

group ="com.wavemaker.commons"

dependencies {
    implementation(enforcedPlatform(appDependencies.boms.springFramework.get()))
    api(enforcedPlatform(appDependencies.boms.jackson.get()))
    implementation(projects.wavemakerToolsApidocsCore)
    implementation(appDependencies.slf4j.api)
    implementation(appDependencies.commons.io)
    implementation(appDependencies.commons.codec)
    implementation(appDependencies.commons.lang3)
    implementation(appDependencies.commons.configuration2)
    implementation(appDependencies.commons.text) {
        because("This is an optional dependency for commons-configuration2 which is needed for PropertiesConfiguration class usage by us")
    }
    implementation(appDependencies.commons.collections4)
    implementation(appDependencies.guava)
    implementation(appDependencies.spring.webmvc)
    api(appDependencies.jackson.core)
    api(appDependencies.jackson.databind)
    implementation(appDependencies.gson)
    implementation(appDependencies.javax.jaxb.api)
    compileOnly(appDependencies.javax.servlet.api)
    testImplementation(appDependencies.test.testng)
    testImplementation(appDependencies.test.mockito.all)
    testImplementation(appDependencies.test.hamcrest.all)
    testImplementation(appDependencies.test.junit4)
    testImplementation(appDependencies.javax.servlet.api)
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