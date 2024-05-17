# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclKX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdOUyABRAAyXLg9RgdOAoxgADNvMMhR1MIziSyTqDcSpymgfAgEDiRCo2XLmaSYCBIXIUNTKLSOndZi83hxZj9tgztPL1GzjOUAJIAOW54UFwtG1v0ryW9s22xg3vqNWltDBOtOepJqnKRpQJoUPjAqQtQxFKCdRP1rNO7sjPq5ftjt3zs2AWdS9QgAGt0OXozB69nZc7i4rCvGUOUu42W+gtVQ8blToCLmUIlCkVBIqp1VhZ8D+0VqJdrpb85NyqsniOm620IepvsJ5Qt9lzOUAExOJzdPd50aX49TU9ji8wI9r3QDhTC8Xx-ACaB2HJGAeQgaIkgCNIMiyZBzAKI5LmqOomlaAx1ASNA3wGfdP0+J4bSWXY+gOTCoAKDddxIj97m-Z5g1taiAXOYEMOVIcEHgpA0FhOCENRdFYmxQcCiTFlSnJKkaQDAseyLZMCndbk+QFas7nFSVqwnUQAB5pzk0k1Q1YyVFk3tk1KNMMwbXMa1GQsmQ0-J3W9X1-VIlA6wbM82yjGMRxslAzNyCyU1-c9MEYqBosHUoxOElc1xvZLpySq5mLcsYAKvL54vQL9r3vDAUuKS5n1fXoCruCrSuCv8WoOYDQO8PxAi8FA2zE3xmCQ9JMkwKrmCVHcygqaReS5eouWaFp8NUQjiLKi9-myhieKYrauMS-b6PyVLBPsYbRPg4aJIxaT+Ls9T5MUzNs1hLaPJdVRNI5bT+UFLaDIgKUIsHaLYtKCx0Uip7PPkjgUG4TI3pzT61Ph9RftKea+SWzs2vPYHQYbCa0Oq6c6NKeqzE4brwMCSEOFg6EYAAcXzVlRpQsmcimgdatmtnFpW+x802wnyp2ui9qBA7Je21ZuLl07UsUjnRlUUToQ1tQ7qk2H8kh16XPR2Lsf+3SgYlEGCe7cHzPs+TobEGSjad0lkFiXWta+4sLYW3SbalMXRkiiGPZTL2wBM3X8mOlWau1IceR1znMoQLA6OivKeimUO1HGSp+gLj1pCLgBGR8AGYABYnmQzIzUKlYvh0BBQGbZvmomL4C69fNe72GBGgOSak5KamXzffPOaLioS-zMvK5r+upkb00Atbp52877uD17p5+8HvoaJHzq6c8HqIOwHwoGwbh4GNTJ2fzFIxtQvm2Sp7CGlF8XggKxnn0Y+ZEla7XyLnWeowB5gMmMrOcfFk6OWfigXWsI4CoN1vrLEhtjYUlRh9BWfsvJaUDoDBWxM7apHDo7Z6pIXZ4MjgpCk6CC4kJZAHHSgoC5UILrQmKzCrKajdpDTYrY2H5g4VjbyHJfKVh4W-MKMB+EO0EfQlM-CkoT3BJg9MmRsFqCytnXKJ0rjQJQMvcoVc64wDHuTGOlNBZTwannEBS9y7WNXnY2mIEr4MwCJYJGglkgwAAFIQGEq-UUARd4gGbLzcw39nHVEpLhFoBduhbWAQ-YAQSoBwAgIJKAKwADqLAPRLRaAAIR5AoOAABpPuHiV62NooLWWc5zHPA7vkwpxSykVKqbU+pTSj4tK8W0hOiCzr8VKAAK0iWgdBEThKGLRPdJhGiWFgEIWbSOXCAbUJCkkYO1CBGQ0YaI5h6t8ywnYRjb6hzdK8LOaox67ttl+C0AYu5KlpE-VkaUeRVYVLthjPw8eTiZouN8fTXqAQvB5PgNwPAnZsAP0IPERI78eaTWSTCyouNFrLVaMYCBuc+iHR2klJBk4VQgFRVABQGoMFMsZHoAwOCHrJzht9HZeziGPP9kCy2FDswnKoWDfiEdtku26FszGKZGXpjhAC7GxL8YqWmJRd40wHQIBgAAMXCDUAAstWbomquTHL-Ma01FqRxWoWvjXhJrzUqPzIkimuQqY0y6kAA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
