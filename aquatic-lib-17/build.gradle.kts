plugins {
    `maven-publish`
}

val maven_username: String by rootProject.extra
val maven_password: String by rootProject.extra

val nmsVersion = "1.0.31"

group = "gg.aquatic.aquaticseries"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.nekroplex.com/releases")
    }
}

dependencies {
    implementation(project(":aquatic-lib"))
    implementation(project(":core"))
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_17_1:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_18_2:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_19_4:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_20_1:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_20_4:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_20_6:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_21:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_21_1:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
}

kotlin {
    jvmToolchain(17)
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("AquaticLib-${project.version}.jar")
    archiveClassifier.set("all")
    dependencies {
        include(project(":spigot"))
        include(project(":paper"))
        include(project(":core"))
        include(project(":aquatic-lib"))
        include(dependency("com.jeff-media:custom-block-data:2.2.2"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_17_1"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_18_2"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_19_4"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_20_1"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_20_4"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_20_6"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_21"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_21_1"))
    }
}

publishing {
    repositories {
        maven {
            name = "aquaticRepository"
            url = uri("https://repo.nekroplex.com/releases")

            credentials {
                username = maven_username
                password = maven_password
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "gg.aquatic.aquaticseries"
            artifactId = "aquaticlib-17"
            version = "${project.version}"
            from(components["java"])
        }
    }
}