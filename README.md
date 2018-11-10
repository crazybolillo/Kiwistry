# AtomicModels
Free software designed to serve as a learning resource for chemistry related themes like atoms and molecules. The app provides diagrams and data on the subjects mentioned before. It also provides trivia based games to test current knowledge and promote improvement.

![Alt text](/images/KiwiGUI.png?raw=true "App snapshot")

## How to use it
You just need to download the latest .jar file from the release section and run the application. If your computer does not have JavaFX (OpenJDK and anything newer than JDK 8) you will need to download the JavaFX SDK and run the application trough the command line with the following command:

```java --module -path $YOUR_PATH_TO_FX_LIB --add-modules=javafx.controls -jar TheJarName.jar```

So far the application has been tested on Windows and Linux machines with Java 8 and no problems have been found. It has also been tested on a Linux machine with JDK 11 and the JavaFX SDK succesfully by using the command above.

## Features
* Multi-language support (English and Spanish at the moment. Adding more languages is easy to do)
* Information about all atoms (1-118) with respective 2D models of their structure. 
* (Upcoming) Information on common molecules. 
* (Upcoming) Lewis structures
* Trivia based games to test your knowledge.
* Customizable GUI (different themes/color schemes to choose from). 

## Current state
Currently the application supports 


## Development
All the source code is available and has been documented. A big part of the application depends on the embedded database found under the sql package. It provides all the information about atoms, moecules and all the different translations for GUI elements. The app is based on a message driven system where most GUI elements just send messages to a Visual Controller that processes them. Resources like .css stylesheets or translations for GUI elements are all acessed trough specific classes, this means that the whole app is synchronized whenever a different style or language is chosen. This also means that adding new .css or languages takes minutes.
