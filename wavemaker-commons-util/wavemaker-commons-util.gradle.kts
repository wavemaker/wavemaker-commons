plugins {
    `java-library-maven-publish`
}

group ="com.wavemaker.commons"

dependencies {
    implementation(enforcedPlatform(appDependenciesLibs.boms.slf4j.get().toString()))
    implementation(enforcedPlatform(appDependenciesLibs.boms.springFramework.get().toString()))
    api(enforcedPlatform(appDependenciesLibs.boms.jackson.get().toString()))
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
    implementation(appDependenciesLibs.jackson.dataformat.yaml)
    implementation(appDependenciesLibs.gson)
    implementation(appDependenciesLibs.jakarta.xml.bind.api)
    compileOnly(appDependenciesLibs.jakarta.servlet.api)
    testImplementation(appDependenciesLibs.test.testng)
    testImplementation(appDependenciesLibs.test.mockito.core)
    testImplementation(appDependenciesLibs.test.hamcrest)
    testImplementation(appDependenciesLibs.test.junit4) {
        exclude("org.hamcrest", "*")
    }
    testImplementation(appDependenciesLibs.jakarta.servlet.api)
    testImplementation(appDependenciesLibs.jaxb.impl)
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
}

javaLibraryMavenPublish {
    scmUrl="git:https://github.com/wavemaker/wavemaker-commons.git"
}