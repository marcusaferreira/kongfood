FROM eclipse-temurin:17-jdk-alpine AS build
LABEL authors="marcusaferreira94"

WORKDIR /workspace/app

COPY .. /workspace/app
RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)


FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/
COPY --from=build ${DEPENDENCY}/libs /app/libs
COPY --from=build ${DEPENDENCY}/classes/kotlin/main/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/classes /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/libs/kongfood-0.0.1-SNAPSHOT.jar"]