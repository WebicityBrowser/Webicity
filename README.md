# Webicity

[![Codebeat Badge](https://codebeat.co/badges/ba54b0a4-273f-4c95-868e-401b5f6d6671)](https://codebeat.co/projects/github-com-webicitybrowser-webicity-ribbon)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c5bf06b8cb6847dda11264294ba7c643)](https://www.codacy.com/gh/WebicityBrowser/Webicity/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=WebicityBrowser/Webicity&amp;utm_campaign=Badge_Grade)
[![CodeClimate Badge](https://api.codeclimate.com/v1/badges/89430c7f39e139c7772d/maintainability)](https://codeclimate.com/github/WebicityBrowser/Webicity/maintainability)

Webicity is a simple web browser that I am working on (it is unfinished).

Webicity consists of multiple components:
* Webicity browser: Allows the user to directly launch Webicity, manages UI, disk, network, navigation and more
* Webicity: Responsible for rendering content
* Ribbon: Provides a GUI component system, and interfaces with the preferred drawing system
* Ribbon web: Part of Webicity. A web-oriented GUI component system based off of Ribbon.

Thanks to [@Minecraftian14](https://github.com/Minecraftian14) for helping!

## Checking out the code
```
git clone https://github.com/jasonthekitten/Webicity-Java.git
cd Webicity-Java
```

## Running the code
```bash
./gradlew run
```

## Creating a Jarfile
After checking out the code, run this:

```bash
./gradlew shadowJar
```
Your Jarfile should appear at `./build/libs/Webicity-all.jar`.

## Screenshot

![image](https://user-images.githubusercontent.com/15697938/128383315-43ae7aaf-8d5b-44a7-9a0e-ad5a4d4b5ae7.png)
