# Fedator
Fedator is a simple text editor with basic utilities based on javafx
#### what's supported 
  - [x] Line Number
  - [x] Undo/Redo
  - [x] File reading/saving (text files only)
  - [x] Intelligent file saving (automatically save data to the current file without prompting the user for a path..)
  - [x] Multiple tabs
  - [x] Find
  - [x] Find and Replace
  - [x] Copy/Paste/Cut
  - [x] Window title changes when tabs are changed or a new file is opened/saved

##### Architecture ?

High-level: each fxml controller is called a Component, there are 5 Components, 
none of them knows about the other, the way they communicate is with events, 
i used the mediator pattern for this, Mediator acts as a connector between components.
Suppose `Open` Menu is pressed, `MainMenuBar` sends `OPEN_MENU` event to `Mediator` using `getEventBuilder` which returns a builder factory object used to build the events,
now when the event goes to `Mediator` it's either delt with there (in case the event has something related to the gui like updating the window title) or gets redirected to `TabSpace`

`TabSpace` is a wrapper for `TextSpace` `EditorTextHistory` `FindReplaceToolbar` it has a direct access to the textArea, this is where most of the text stuff happens

`smallUndoEngine` is a simple text undo/redo engine based on the `Command` pattern.
