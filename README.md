# Jewel Chase
Recreation of the 1997 game 'Jewel Chase' for Swansea University module CS-230, 2022.

---
## Setup
In order to be able to edit this project, your IDE must be configured correctly.

The Java JDK version used is 19.0.1. The JavaFX version is 19. 

Using IntelliJ as an example, in order to configure:

- First, open the Project Structure menu, keyboard command `Ctrl+Alt+Shift+S`
- Then, navigate to 'Modules' and select the correct JDK
  - If it does not show up, you may navigate to SDKs, click create and navigate to your Java installation folder. Example: `C:\Program Files\Java\jdk-19.0.1`
- Next, navigate to 'Libraries' and create a new project library. This should link to the `lib` folder in your JavaFX folder. Example: `C:\Program Files\Java\javafx-sdk-19\lib`
- To build and run your code, navigate to 'Run > Edit Configurations...'
- Create a new configuration, using the Application template
- Select Java 19 as your JRE/JDK
- Add `--module-path "C:\your\javafx\install\lib" --add-modules javafx.controls,javafx.fxml` to the VM options field
  - If you cannot see the VM options field you may find it in the 'Modify options' submenu or by using the keyboard command `Alt+O`

---
## Contributing
When contributing you have an option to enter a title for your commit, along with a short description. Since this is the first time using GitHub for many of the group members, no format for writing commits is in place. However, it is recommended that you add a title to your commits, and a description of your changes. This helps us know what each commit is for, and is a nice way to organise everything. It's also a good routine to get in to, and helps a fair amount along the development process.
