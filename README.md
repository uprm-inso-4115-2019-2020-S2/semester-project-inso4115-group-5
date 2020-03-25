## Setup

Download Visual Studio Code: https://code.visualstudio.com/

Download sbt: https://www.scala-sbt.org/download.html

Download git: https://git-scm.com/downloads

Download Node.js: https://nodejs.org/en/download/

In Visual Studio Code, install the following extentions: GitLens, Scala (sbt), Scala Syntax (official), HTML CSS Support, markdownlint

Clone the project the following command in the terminal: git clone <URL from github link>

## Execution
To execute the project, everything must be done in a terminal. Visual Studio Code provides a terminal in the lower panel. The sbt commands are the following:

sbt: Start the sbt server

exit: Stop the sbt server

compile: Compiles the project

run: Start the local application server(This starts the localhost. To open it, put the following URL in a web browser: http://localhost:9000/)

"This is not a command, just press the enter button": Stop the local application server

test: Run every test file

testOnly <name of the test file>: Run only that test file
	
After opening the project, the terminal will automatically execute the sbt server and the terminal will look like this: [Play-Videos-Server] $, after that, use the sbt command you want.

## Coding
The files we will be working for now are in the server folder, which it's composed of the following:

app: Folder containing every class

    controllers: Connection between back-end and front-end
	
    models: Back-end
	
    views: Front-end
	
conf: Folder containing the configuration files

    routes: File containing all application routes
	
test: Folder containing the test classes
