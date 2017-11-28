# JavaFX Tutorial by markojakob
In this project an address app will be built using JavaFX
Link to tutorial: http://code.makery.ch/library/javafx-8-tutorial/

## Topics to be covered:
- Creating and starting a JavaFX project
- Using Scene Builder to design the user interface
- Structuring an application with the Model-View-Controller (MVC) pattern
- Using ObservableLists for automatically updating the user interface
- Using TableView and reacting to selection changes in the table
- Create a custom popup dialog to edit persons
- Validating user input
- Styling a JavaFX application with CSS
- Persisting data as XML
- Saving the last opened file path in user preferences
- Creating a JavaFX chart for statistics
- Deploying a JavaFX application as a native package

## Note
The original tutorial was written with Java 8. I started off using Java 9 and I was fine until Part 5 when I needed to use the javax.xml package.
This API is not made available by default in the class path of Java 9. You have to set the compile-time and run-time configurations to explicitly include the java.xml package.

You can do this in the following steps on Eclipse:

### Setup the compiler
1. Right-click on projectName
2. Select Build Path > Libraries > Modulepath 
3. Click on Java 9 JRE > Is Modular
4. Select java.xml.bind from the Available modules and move it to the Explicitly included modules
5. Click OK > Apply and Close 

### Setup the runtime environment to include the module as well
1. Right-click on projectName
2. Select Run As > Run configurations...
3. Click on (x)=Arguments tab
4. In the VM Arguments: area, add the following:
 `--add-modules java.xml.bind`
5. Apply and Close