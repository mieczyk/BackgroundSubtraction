Background Subtraction
======================

Simple Java application that compares different [background subtraction](https://en.wikipedia.org/wiki/Background_subtraction) techniques provided by the OpenCV 3.2.0 library.

Run on Windows
--------------

The application requires OpenCV and FFmpeg DLLs to run so the `java.library.path` property must point at the `lib\dll` directory. The property can be set in the JVM (Java Virtual Machine) options. Just run the application with the following parameter:

```
-Djava.library.path=lib\dll
```
