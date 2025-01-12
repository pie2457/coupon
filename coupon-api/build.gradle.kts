dependencies {
    implementation(project(":coupon-core"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.1.0")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

/** QueryDSL 설정 **/

// QueryDSL QClass 파일 생성 위치를 지정
val generated = file("build/generated/querydsl")

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(generated)
}

// java source set에 querydsl QClass 위치 추가
sourceSets["main"].java {
    srcDir(generated)
}

// gradle clean 시에 QClass 디렉토리 삭제
tasks.clean {
    delete(generated)
}

/** QueryDSL 설정 종료 **/
