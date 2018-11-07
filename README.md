# AtomicModels
Free software designed to serve as a learning resource for chemistry related themes like atoms and molecules. The app provides diagrams and data on the subjects mentioned before. It also provides trivia based games to test current knowledge and promote improvement. The source code has been designed to make it as easy as possible for users to make modifications in order to fit their needs. 

![Alt text](/images/Uranium.png?raw=true "Atom diagram")

## Features
* Multi-language support (English and Spanish at the moment. Adding more languages is easy to do)
* Information about all atoms (1-118) with respective 2D models of their structure. 
* (Upcoming) Information on common molecules. 
* (Upcoming) Lewis structures
* Trivia based games to test your knowledge.
* Customizable GUI (different themes/color schemes to choose from). 

## Current state
The first two releases consisted of a Lite GUI which just tested the basic atom related functionalities. A new GUI "called Kiwi" is now available, it is a better looking more interactive GUI that will provide new functionality, right now it just has more information about atoms and difficulty levels for the games, but more will be added. Here is a snapshot of it:
![Alt text](/images/KiwiGUI.png?raw=true "App snapshot")


## How to use it
Tested .jar files can be found under the release section. All jars need Java 8 or higher and JavaFX installed on the system. Currently the app does not ship the JavaFX library so if your Java installation does not include it you will also need to install that library. Once the app is finished a version with the JavaFX library will be available. (Java 11 and OpenJDK do not include it. Oracle's Java 8 JRE and JDK do include it.) The app has been tested both on Windows and Linux machines, it should run on any operating system that has Java.

## Development
All the source code is available and has been documented as much as possible to give anyone that wants to modify the app an easier time. The project is divided in six main packages. The "basicGUI" package consists of all the GUI resources that make up the Lite version of the app.The "kiwiGUI" package consists of all the GUI resources that makeup the "real" version of the app. The "rendering" package renders the atomic models while the "game package" stores all the trivia-game logic and the "sql" package manages the interaction with the embedded sqlite3 database that contains all relevant information. Finally a very imporant package consits of the LanguageLoader which manages the GUI translation to the selected language (currently supports English and Spanish).
