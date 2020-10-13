buildscript {
    extra.apply {
        set("commonsIoVersion", "2.8.0")
        set("commonsCodecVersion", "1.14")
        set("commonsCollections4Version", "4.4")
        set("commonsConfigurationVersion", "2.7")
        set("commonsTextVersion", "1.9")
        set("commonsLang3Version", "3.11")
        set("slf4jVersion", "1.7.30")
        set("guavaVersion", "29.0-jre")
        set("gsonVersion", "2.8.6")
        set("springVersion", "5.2.9.RELEASE")
        set("jacksonVersion", "2.11.3")
        set("mockitoVersion", "1.10.19")
        set("hamcrestVersion", "1.3")
        set("junitVersion", "4.13")
        set("testngVersion", "7.3.0")
        set("servletVersion", "3.1.0")
    }
}

plugins {
    `java-platform-maven-publish`
}

group ="com.wavemaker.commons"

javaPlatform {
    allowDependencies()
}

dependencies {
    api(enforcedPlatform("org.springframework:spring-framework-bom:${project.extra["springVersion"]}"))
    api(enforcedPlatform("com.fasterxml.jackson:jackson-bom:${project.extra["jacksonVersion"]}"))
    constraints {
        api("org.slf4j:slf4j-api:${project.extra["slf4jVersion"]}")
        api("org.apache.commons:commons-collections4:${project.extra["commonsCollections4Version"]}")
        api("commons-codec:commons-codec:${project.extra["commonsCodecVersion"]}")
        api("commons-io:commons-io:${project.extra["commonsIoVersion"]}")
        api("org.apache.commons:commons-configuration2:${project.extra["commonsConfigurationVersion"]}")
        api("org.apache.commons:commons-text:${project.extra["commonsTextVersion"]}")
        api("org.apache.commons:commons-lang3:${project.extra["commonsLang3Version"]}")
        api("com.google.guava:guava:${project.extra["guavaVersion"]}")
        api("com.google.code.gson:gson:${project.extra["gsonVersion"]}")
        api("junit:junit:${project.extra["junitVersion"]}")
        api("org.testng:testng:${project.extra["testngVersion"]}")
        api("org.hamcrest:hamcrest-all:${project.extra["hamcrestVersion"]}")
        api("org.mockito:mockito-all:${project.extra["mockitoVersion"]}")
        api("javax.servlet:javax.servlet-api:${project.extra["servletVersion"]}")
    }
}

javaPlatformMavenPublish {
    scmUrl="git:https://github.com/wavemaker/wavemaker-commons.git"
}