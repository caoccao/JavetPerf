/*
 * Copyright (c) 2023. caoccao.com Sam Cao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.gradle.internal.os.OperatingSystem

object Config {
    object Projects {
        // https://mvnrepository.com/artifact/org.apache.commons/commons-collections4
        const val COMMONS_COLLECTIONS_4 = "org.apache.commons:commons-collections4:${Versions.COMMONS_COLLECTIONS_4}"
        // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
        const val COMMONS_LANG_3 = "org.apache.commons:commons-lang3:${Versions.COMMONS_LANG_3}"
        const val JAVET = "com.caoccao.javet:javet:${Versions.JAVET}"
        const val JAVET_MACOS = "com.caoccao.javet:javet-macos:${Versions.JAVET}"
        const val JUNIT_BOM = "org.junit:junit-bom:${Versions.JUNIT}"
        const val JUNIT_JUPITER = "org.junit.jupiter:junit-jupiter"
        const val SELF4J_API = "org.slf4j:slf4j-api:${Versions.SELF4J}"
        const val SELF4J_LOG4J_12 = "org.slf4j:slf4j-log4j12:${Versions.SELF4J}"
    }

    object Versions {
        const val COMMONS_COLLECTIONS_4 = "4.4"
        const val COMMONS_LANG_3 = "3.12.0"
        const val JAVET = "2.1.2"
        const val JUNIT = "5.9.1"
        const val SELF4J = "2.0.7"
    }
}

plugins {
    id("java")
}

group = "com.caoccao.javet.perf"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(Config.Projects.COMMONS_LANG_3)
    implementation(Config.Projects.COMMONS_COLLECTIONS_4)
    if (OperatingSystem.current().isMacOsX()) {
        implementation(Config.Projects.JAVET_MACOS)
    } else {
        implementation(Config.Projects.JAVET)
    }
    implementation(Config.Projects.SELF4J_API)
    implementation(Config.Projects.SELF4J_LOG4J_12)
    testImplementation(platform(Config.Projects.JUNIT_BOM))
    testImplementation(Config.Projects.JUNIT_JUPITER)
}

tasks.test {
    useJUnitPlatform()
}

afterEvaluate {
    tasks.withType(JavaCompile::class) {
        options.compilerArgs.add("-Xlint:unchecked")
        options.compilerArgs.add("-Xlint:deprecation")
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    systemProperty("file.encoding", "UTF-8")
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}
