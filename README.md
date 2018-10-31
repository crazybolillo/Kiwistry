# AtomicModels
Free software designed to serve as a learning resource for chemistry related themes like atoms and molecules. The app provides diagrams and data on the subjects mentioned before. It also provides trivia based games to test current knowledge and promote improvement. The source code has been designed to make it as easy as possible for users to make modifications in order to fit their needs. 

## Features
* Multi-language support (English and Spanish at the moment. Adding more languages is easy to do)
* Information about all atoms (1-118) with respective 2D models of their structure. 
* (Upcoming) Information on common molecules. 
* (Upcoming) Lewis structures
* Trivia based games to test your knowledge.
* Customizable GUI. 

## Development
The source code is well documented so its easy to modify the program to better fit your needs. The underlying components/program logic were separated from the GUI as much as possible to allow easy modification and reuse. Adding support for different languages just requires adding the new information to the internal database and adding a new Enum to the LanguageLoader class found inside the ResourceLoader package.
