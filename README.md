# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](ChessSequenceDiagramCS240new.png)](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqsyYOc4ACNgqlDA67N6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D6GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclKX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8cZdOUyABRAAyXLg9RgdOAoxgADNvMMhR1MIziSyTqDcSpymgfAgEDiRCo2XLmaSYCBIXIUNTKLSOndZi83hxZj9tgydPL1GyTOUAJIAOW54UFwtG1oMryW9s22xg3vqNWltDBOtOepJqnKRpQJoUPjAqQtQxFKCdRP1rNO7sjPq5ftjt3zs2AWdS9QgAGt0OXozB69nZc7i4rCvGUOUu42W+gtVQ8blToCLmUIlCkVBIqp1VhZ8D+0VqJdrpb85NyqsniOm620IepvsJ5Qt9lzOUAExOJzdPd50aX49TU9ji8wI9r3QDhNC8Xx-ACaB2HJGAeQgaIkgCNIMiyZBzAKI5LmqOomlaQx1ASNA3wGfdP0+J4bSWXY+gOTCoAKDddxIj97m-Z5g1taiAXOYEMOVIcEHgpA0FhOCENRdFYmxQcCiTFlSnJKkaQDAseyLZMCndbk+QFas7nFSVqwnUQAB5pzk0kbmMlRZN7ZNSjTDMG1zGtRkLJkNPyd1vV9f1SJQOsGzPNsoxjEdrJQMzcgslNf3PTBGKgKLB1KMThJXNcbyS6dEquZjXLGACry+OL0C-a97wwZLikuZ9X16fK7nKkqgr-ZqDmA0DvD8QIvBQNsxN8ZgkPSTJMEq5glR3MoKmkXkuXqLlmhafDVEI4jSovf4soYnimM2riEr2+j8hSwT7CG0T4KGiSMWk-jbPU+TFMzbNYU29yXVUTSOW0-lBU2gyIClcLByimLSgsdEIsejz5I4FBuEyV6cw+tS4fUH7SjmvlFs7VrzyBkGG3GtCqunOjSjqzBOs8bqIMhDhYOhGAAHF81ZEaUNJnJJoHGqZtZhblvsfMNoJsrtro3agX2iWttWbjZZOlLFPZ0ZVFE6F1bUW6pJh-IIZe5y0ZirG-t0wGJWB-HuzB8y7PkqGxBkw3HdJZBYh1zXPuLc35t062pVF0YIvB92U09sATJ1-IjuV6rtSHHltY5jKECwOiotynophDtRxkqfp849aRC4ARkfABmAAWJ5kMyM0CpWL5dAQUBmybpqJi+fOvXzHu9hgRoDgmxOSipl83zzjnC4qYv81Livq7rqYG9NfyW6eNuO67g8e6ePuB76Gjh46zguvAwJsB8KBsG4eBjUyNn8xSUbUN5tlKewhoRbF4J5bTz6EfMiisdr5BzjPUY-dQGTCVnOPiScHJPxQDrWEcAUE6z1liA2RsKQo3evLX2nktIBwBvLImttUhhwdk9UkztcER1KJsVsaD87EJZFjHylZBT53bDGfONDopMMEYlce4IMHpkyFgtQmUs45WOlcKBKAl7lErrXGAo8ybRwpgLSe9Vc7AMXmXNRK9NE0wvnTK+ARLCI0EskGAAApCAwkX6igCDvEAzYebmC-no6olJcItHzt0TaQD77AFsVAOAEBBJQBWAAdRYB6RaLQABCPIFBwAANK92McvDRtEBYyznEo547cokxLiYk5JqSMlZNyYffJpjCnxwQadfipQABWLi0BoOccJGRaI7qMLoSmY2b1TYR39jpch2ZgpJCDlQoREMGGuzwWANh+YOGYy8r9MhMA+FLMEfbYRYyFL5lGRjFMfhtDSPzC5K07D0ZfS4RWKsKlph8NCocy5Y9dHTX0RYkCVieoBC8JE+A3A8CdmwPfQg8REhv25hNEpwJSizXmotRoLQTBtN4h0pBIBoVQAUBqdBJLGT6EMNg+6SdYZfQciS2EOzvp7OxlirkMAABi4QagAFlqzdBxgtLlvKBVUOFZynlfLBWCP+bkSm1NOpAA)

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
