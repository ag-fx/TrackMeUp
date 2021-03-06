# TrackMeUp

## About

Side project about time tracking and task management.

The overall idea is to have fun creating a tool that allows people to keep track
of TODO lists, manage and organize tasks.
Much of the functionality is based on what the 'org-mode' of eMacs already offers.
The added value of this project is to provide a user interface, and to keep the data formats used
open and portable.

The project currently consists of three modules:

* Trambu-core: The core library containing data models and logic
* Trambu-app: Display package for stand-alone desktop application

Note: The *Trambu-web* package is currently no longer under active development. It was removed from the repository 
due to build complications. It can still be found in the prerelease 0.7.0.


### Version 0.2.0

Refactored the project structure to diferentiate between core-components, and
the frontend(s).

## Technical requirements

If you want to get hacking at my code, or contribute to the effort, 
you will need the following

* JDK 8
* Maven
* a git client

## Running the code from source

If you just want to run a local version of the web application,
follow these steps:

* Download the source code
* In the root directory (which contains the pom) execute 'mvn clean install'
* For each module, a 'target' directory will be created


### Run the standalone application
* Open a terminal window and navigate to the 'trambu-app/target' directory
* Execute 'java -jar XXXX' , where XXXX is the name of the TraMBU jar
* The application will now open in a desktop window
* Set up the application to use your own 'todo.txt' file

## Code quality

In order to keep the code maintainable and of reasonably high quality,
I use SonarCloud to point out any issues that exist.
The report can be found at:
https://sonarcloud.io/dashboard?id=be.doji.productivity%3Atrambu


## Acknowledgements 

Big shoutout to the awesome people that created the libraries I used to create this Application:

* https://bitbucket.org/Jerady/fontawesomefx
* https://github.com/TestFX/TestFX
* https://github.com/edvin/tornadofx

Thanks to:

* Suzanne VDW for guidance and collaboration on the project ideas and vision
* all friends and colleagues testing and using TraMBU