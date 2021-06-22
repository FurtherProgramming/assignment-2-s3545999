# Readme

## About Me

- Aidan McGinniss 
  
- s3545999
  
- Bachelor of Software Engineering

## About the application
This project is a visual desk booking app made in javafx with sqlite to store data.

Logging in with a non-admin account allows for desk booking and checking in. 
Only one booking can be made at a time.
While an admin account is only used for controlling the bookings and users.


This projected should be downloaded from github and then loaded into intellij IDEA Ultimate.
Main can be found in src/main/Main. Clicking on main and clicking run will run the program.
Running requires javafx8 and sqlite to be installed.

## Packaging
The main class is Main.java

Packaging for classes:
 - main.controller
 - main.model
 - main.ui

## Information to login

### Admin
- Username: test

- Password: abcd

### User

- Username: aidan

- Password: aidan

## Design Decisions

The FXML file for creating accounts is reused for editing accounts and for
admins to make new accounts as well. This reduces the need to make extra 
pages. 
A singleton class has been used as a messenger on how the page should be set up.

A singleton class has also been used to remember which user is currently logged in. 
This reduces the need to pass around and remember who is logged in. 

A blacklist is used to determine which tables can be booked. An Sql table of desk
availability is changed depending on which level of lockdown is implemented. All bookings
are canceled on increase of lockdown level as the circumstances have changed. 

The creating and modifying of a user uses a observableList which allows everytime a
textbox is changed it can be checked for inaccuracy. This made the creating a user much
clearer in what was going wrong for the user. 

A CSS file was used which helped keep consistency across the entire application.
This was a simple way to make everything flow together and look like one application.

## Lessons Learned

This assignment taught me a lot about coding with visual interfaces as I had never
done it before. This required significant amount of checking as users have many ways to
change things how they should not be changed. 

This was also my first time using a database, so a lot was learnt to do with getting things
out and placing them in the database. 

## Improvements

To improve this assigment the use of database could be more efficient
due to my lack of familiarity. Possibly some inheritance to allow a get user from
one model to be used in another. 

## Code References 

James D, ValidatingTextFieldExample, 
https://stackoverflow.com/questions/24231610/javafx-red-border-for-text-field, 15/06/2014