# AtomicModels
Free software designed to serve as a learning resource for chemistry related themes like atoms and molecules. The app provides diagrams and data on the subjects mentioned before. It also provides trivia based games to test current knowledge and promote improvement. The source code has been designed to make it as easy as possible for users to make modifications in order to fit their needs. 

![Alt text](/images/Uranium.png?raw=true "Atom diagram")

## Features
* Multi-language support (English and Spanish at the moment. Adding more languages is easy to do)
* Information about all atoms (1-118) with respective 2D models of their structure. 
* (Upcoming) Information on common molecules. 
* (Upcoming) Lewis structures
* Trivia based games to test your knowledge.
* Customizable GUI. 

## Current state
A Lite version which provides spanish/english language support, atoms' basic information, their diagrams and a based trivia game. Is available. Right now work is being done to provide a better, more powerful application. Here is a preview of the main window for the new GUI (far from finished):
![Alt text](/images/KiwiGUI.png?raw=true "App snapshot")


## How to use it
Tested .jar files can be found under the release section. Latest .jar that matches the uploaded source code can be found on the root directory. All jar files need Java 8 or higher installed on the system to properly function.No setup needs to be done. Just run the program once downloaded.

## Development
All the source code is available and has been documented as much as possible to give anyone that wants to modify the app an easier time. The project is divided in six main packages. The "basicGUI" package consists of all the GUI resources that make up the Lite version of the app.The "kiwiGUI" package consists of all the GUI resources that makeup the "real" version of the app. The "rendering" package renders the atomic models while the "game package" stores all the trivia-game logic and the "sql" package manages the interaction with the embedded sqlite3 database that contains all relevant information. Finally a very imporant package consits of the LanguageLoader which manages the GUI translation to the selected language (currently supports English and Spanish).
