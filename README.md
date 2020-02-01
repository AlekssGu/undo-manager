### Undo manager for Text Editor

Source code consists of a set of interfaces which form the model of an undo-manager for a text editor. 

The exercise consists in implementing the interfaces **UndoManager** and **UndoManagerFactory**, including unit tests. 

The other interfaces should be used "as is" - i.e. the implementation of UndoManager and UndoManagerFactory should not rely on any specific implementation of the other interfaces. 

You are free to use third-party software libraries in your solution if you want. 

---

#### Solution description

[![Build Status](https://travis-ci.org/AlekssGu/undo-manager.svg?branch=master)](https://travis-ci.org/AlekssGu/undo-manager)
[![Unit test coverage](https://codecov.io/github/AlekssGu/undo-manager/coverage.svg?branch=master)](https://codecov.io/github/AlekssGu/undo-manager?branch=master)

Implemented `UndoManager` and `UndoManagerFactory` interfaces.

Created TextEditorModule which is the root module and in real application would consist of all submodules & configuration existing in the application.

Tested solution with unit tests.

##### Questions & Answers:

1. Why I chose `CircularFifoQueue` instead of `Queue` and `Deque`?
    * `Queue`, depending on implementation, could be faster than `Deque` as it is just "one-sided" and `CircularFifoQueue` perfectly fits `DefaultUndoManager#registerChange` requirements - when the queue is full, oldest element should be replaced.
2. Why `Change` and `Document` are moved to separate packages?
    * I believe that `Change` and `Document` are logically separate from UndoManager and can exist without it
3. Why use [Guice Dependency Injection framework](https://github.com/google/guice)?
    * Because it is lightweight and helps to separate logical modules and that way introduces loose coupling
    * Easy to switch implementations
4. Why there are no comments in the code?
    * I believe that code should be self explanatory without the need of comments explaining what certain thing does. I usually try to write code in the way that a person without IT knowledge could read it and understand the concept of what is happening. 

##### Possible improvements:

1. Clean up `DefaultUndoManager` by splitting `Undo` and `Redo` functionality into two different classes so each class does just one thing, however they would still be quite tightly coupled because of Undo<~>Redo connection.
2. Split functionality in the way that can help test values of `Undo` and `Redo` actions easily. 
