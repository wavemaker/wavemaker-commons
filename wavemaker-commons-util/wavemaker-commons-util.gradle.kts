plugins {
    `java-library-maven-publish`
}

group ="com.wavemaker.commons"

dependencies {
    implementation(enforcedPlatform(appDependenciesLibs.boms.springFramework.get()))
    api(enforcedPlatform(appDependenciesLibs.boms.jackson.get()))
    implementation(projects.wavemakerToolsApidocsCore)
    implementation(appDependenciesLibs.slf4j.api)
    implementation(appDependenciesLibs.commons.io)
    implementation(appDependenciesLibs.commons.codec)
    implementation(appDependenciesLibs.commons.lang3)
    implementation(appDependenciesLibs.commons.configuration2)
    implementation(appDependenciesLibs.jakarta.validationApi)
    implementation(appDependenciesLibs.commons.text) {
        because("This is an optional dependency for commons-configuration2 which is needed for PropertiesConfiguration class usage by us")
    }
    implementation(appDependenciesLibs.commons.collections4)
    implementation(appDependenciesLibs.guava)
    implementation(appDependenciesLibs.spring.webmvc)
    api(appDependenciesLibs.jackson.core)
    api(appDependenciesLibs.jackson.databind)
    implementation(appDependenciesLibs.gson)
    implementation(appDependenciesLibs.javax.jaxb.api)
    implementation(libs.lucene.core)
    compileOnly(appDependenciesLibs.javax.servlet.api)
    testImplementation(appDependenciesLibs.test.testng)
    testImplementation(appDependenciesLibs.test.mockito.all)
    testImplementation(appDependenciesLibs.test.hamcrest.all)
    testImplementation(appDependenciesLibs.test.junit4)
    testImplementation(appDependenciesLibs.javax.servlet.api)
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
    //exclude("**/*")
}

javaLibraryMavenPublish {
    scmUrl="git:https://github.com/wavemaker/wavemaker-commons.git"
}