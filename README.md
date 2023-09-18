# codefellowship

## Overview

CodeFellowship is a web application designed to foster a supportive community for people who are learning to code. 
It provides a platform for users to connect with fellow coders, share their coding journeys, and offer support to one 
another. The application is built using Spring Boot and incorporates various features to facilitate user interaction 
and engagement.


## Key Features

1. **User Authentication:** CodeFellowship allows users to create accounts and log in securely. User passwords are 
                         hashed using BCrypt for enhanced security.

2. **User Profile:** Each user has a profile that includes their username, first name, last name, date of birth, and
                  a bio. These details are set during the signup process and cannot be edited later, ensuring data 
                  integrity.

3. **User Signup:** Users can easily sign up for CodeFellowship by providing the required information. The application
                 uses Spring Security to authenticate and authorize users.

4. **User Login:** Registered users can log in to their accounts, and the application securely manages user sessions. 
                A session ensures that users remain logged in across different parts of the site.

5. **Homepage:** The homepage serves as the central hub of the application. It contains essential information about
              CodeFellowship and provides links to the login and signup pages for non-logged-in users.

6. **Logout Functionality:** Users can log out at any time, and upon logout, they are redirected to the login page, 
                          depending on their previous state.

7. **Accessibility:** CodeFellowship ensures that critical routes, such as the homepage, login, and registration, 
                   are accessible to both logged-in and non-logged-in users, making it easy for newcomers to join the 
                   community.

   

## Resources

- [Spring Security cheat sheet](https://github.com/jonashackt/spring-boot-vuejs/blob/master/backend/src/main/resources/cheat-sheets/spring-security-cheat-sheet.md)
