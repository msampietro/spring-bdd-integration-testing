## Spring Integration Testing with jBehave and TestContainers

### A Films API Example (JWT Authentication)

#### Dependencies:

- Spring Boot 2.5.2
- Java 11
- jBehave 4.8.3 (JUnit4 still required)
- PostgreSQL TestContainers 1.15.3

#### Stories: 
- src/test/resources/stories/*.story

#### Reusable Steps:
- src/test/java/com.../AbstractStory.java

#### Impl Steps ->

-   src/test/java/com.../module/actor/integration/ActorSteps.java
-   src/test/java/com.../module/film/integration/FilmCrewMemberSteps.java
-   src/test/java/com.../module/film/integration/FilmProductionCompanySteps.java
-   src/test/java/com.../module/film/integration/FilmSteps.java