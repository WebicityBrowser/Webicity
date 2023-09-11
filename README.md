# Webicity

[![codebeat badge](https://codebeat.co/badges/eb37c4a6-06fa-4033-bc9a-f5a76528c0d7)](https://codebeat.co/projects/github-com-webicitybrowser-webicity-thready-iterative)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c5bf06b8cb6847dda11264294ba7c643)](https://www.codacy.com/gh/WebicityBrowser/Webicity/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=WebicityBrowser/Webicity&amp;utm_campaign=Badge_Grade)
[![CodeClimate Badge](https://api.codeclimate.com/v1/badges/89430c7f39e139c7772d/maintainability)](https://codeclimate.com/github/WebicityBrowser/Webicity/maintainability)

Webicity is a simple web browser that I am working on (it is unfinished).

Webicity consists of multiple components:
*   Webicity browser: A graphical UI for Webicity, so that users can actually use Webicity.
*   Webicity: Responsible for rendering content.
*   Thready: Provides a GUI component system, and interfaces with the preferred drawing system.
*   Thready web extensions: Part of Webicity. Allows for rendering web pages with Thready.
*   EcmaSpiral Engine: An experimental Javascript runtime. Not yet attached to the main codebase.

Thanks to [@Minecraftian14](https://github.com/Minecraftian14) and [@shabman](https://github.com/shabman) for helping!

## Join our Discord server!

Want to talk about Webicity? Consider joining our [Discord server](https://discord.gg/HxPHHk6N8w).

## Requirements

Webicity is meant to be compatible with Windows, Linux distributions with Java support, and MacOS.
Other oparating systems are not supported. The recommended JDK/JRE version is Zulu JDK 17. Other versions of
the JDK or JRE are not guaranteed to work.

## Checking out the code
```bash
git clone https://github.com/WebicityBrowser/Webicity.git
cd Webicity
```

## Running the code (via Gradle)
```bash
./gradlew run
```

## Creating a Jarfile
After checking out the code, run this:

```bash
./gradlew shadowJar
```
Your Jarfile should appear at `./build/libs/Webicity-all.jar`.

## Running a built jar (Windows, Linux)
You can run the application with this command:
```bash
java -XX:MaxHeapFreeRatio=10 -XX:MinHeapFreeRatio=10 --XX:+UseG1GC -jar Webicity-all.jar
```

## Running a built jar (MacOS)
MacOS users must instead this command:
```bash
java -XstartOnFirstThread -XX:MaxHeapFreeRatio=10 -XX:MinHeapFreeRatio=10 --XX:+UseG1GC -jar Webicity-all.jar
```

## Screenshot

![image](https://i.imgur.com/2f7iygD.png)

## Disclaimer
Webicity is not suitable for use in medical applications, banking applications, or other applications in which security, accuracy, and/or performance is critical.
As stated in this software's license, the maintainers of this software are not responsible for any damages caused by use of this software.
