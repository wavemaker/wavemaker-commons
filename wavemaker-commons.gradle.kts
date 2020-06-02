buildscript {
    extra.apply {
        set("wavemakerApiDocsToolsVersion", "2.20")
        set("commonsIoVersion", "2.6")
        set("commonsCodecVersion", "1.11")
        set("commonsConfigurationVersion", "2.4")
        set("commonsLang3Version", "3.9")
        set("slf4jVersion", "1.7.29")
        set("springVersion", "4.3.14.RELEASE")
        set("jacksonVersion", "2.9.8")
        set("mockitoVersion", "1.8.5")
        set("hamcrestVersion", "1.3")
        set("junitVersion", "4.11")
        set("testngVersion", "6.9.6")
        set("servletVersion", "3.1.0")
    }
}

plugins {
    `java-platform`
    `maven-publish`
}

group ="com.wavemaker.commons"

javaPlatform {
    allowDependencies()
}

dependencies {
    api(enforcedPlatform("org.springframework:spring-framework-bom:${project.extra["springVersion"]}"))
    api(enforcedPlatform("com.fasterxml.jackson:jackson-bom:${project.extra["jacksonVersion"]}"))
    constraints {
        api("com.wavemaker.tools.apidocs:wavemaker-tools-apidocs-core:${project.extra["wavemakerApiDocsToolsVersion"]}")
        api("org.slf4j:slf4j-api:${project.extra["slf4jVersion"]}")
        api("commons-codec:commons-codec:${project.extra["commonsCodecVersion"]}")
        api("commons-io:commons-io:${project.extra["commonsIoVersion"]}")
        api("org.apache.commons:commons-configuration2:${project.extra["commonsConfigurationVersion"]}")
        api("org.apache.commons:commons-lang3:${project.extra["commonsLang3Version"]}")
        api("junit:junit:${project.extra["junitVersion"]}")
        api("org.testng:testng:${project.extra["testngVersion"]}")
        api("org.hamcrest:hamcrest-all:${project.extra["hamcrestVersion"]}")
        api("org.mockito:mockito-all:${project.extra["mockitoVersion"]}")
        api("javax.servlet:javax.servlet-api:${project.extra["servletVersion"]}")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = project.extensions.extraProperties.get("basename") as String
            from(components["javaPlatform"])
        }
    }
}