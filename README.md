# JAtomicModels
Application that displays information about atoms and generates 2D models of them. Based on JavaFX. The application provides a simple user interface to access atoms, their properties and their models. It also provides a simple guessing game so you can practice your knowledge about atoms.

![Alt text](images/Krypton.png?raw=true "Atomic model")
<p align="center">
  <img src="images/MainGUI.png?raw=true" alt="Main GUI"/>
</p>

The source code can be helpful for anyone trying to develop a Java application which deals with atoms, their attributes and their models. This application provides a simple embeeded database (sqlite3) that stores all the information about the atoms. It provides a very simple to use Java object which is initialized by simply typing out the atom´s name or atomic number. This object makes all the necessary queries to gather the atom´s attributes. A resizable Canvas can be created based on the atom. 
