# AppRelay
Service that integrates serverless software in a practical, simple and scalable way

## Why does this repository exist?
In systems development it is normal to be in a situation where one software depends on another. Nowadays, with several practical solutions to create RESTFul servers, WebSocket, among others, this task becomes easier and easier. The problem is when the software we need to integrate doesn't have a communication interface through the server, the so-called _serverless_, and that's what AppRelay comes to help.

When it is necessary to integrate with _serverless_ software we usually use system call features such as `Runtime.getRuntime().exec("c:\\program files\\test\\test.exe", null, new File(" c:\\program files\\test\\"));
` in Java.

The problem: The location of this file may vary. As much as we are in development context, where the executable can be in different folders or even HDs/SSDs for each developer working on the project, as in production it will be in another folder.

## How does AppRelay work?
The AppRelay is what we call a *runtime container*. A runtime container is a set of rules that determine how software should run regardless of where that software is. AppRelay will do the process of *mapping the execution environment* of an application that is in a directory. Mapping is done by a configuration file called *_apprelay.yml_*. Inside that file contains instructions on how the application runs. AppRelay maps these instructions, stores where the application is, and creates a RESTFul and CLI interface so that other applications can invoke the mapped application decentrally, without needing them to know where the application is.

## Installing AppRelay
AppRelay is written in Java, specifically using Java 11.

To run AppRelay, you can _clone_ this project and perform the build process manually using `mvn install`.

But you'll probably want a pre-built release. For this you currently have two options:

1. Download the ZIP containing the build of the `*.jar` file. Go to the latest releases page and download the version of your choice. Releases page: https://github.com/felipepoliveira/apprelay/releases.

2. For Windows platform users, we also have an installer. This installer downloads the latest release on your machine and adds the `\bin` path to your user's PATH variable. With this, you can access the AppRelay CLI directly from your terminal using the `ar` or `apprelay` command if there is already a script named _ar_ used on your system. You can access the installers for Windows here. Windows installer releases page: https://github.com/felipepoliveira/appreplay-win-installer/releases.

