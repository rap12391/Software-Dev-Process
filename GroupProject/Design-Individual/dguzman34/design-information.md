# Requirements
1. When starting the application, a user can choose whether to (1) log in as a specific student or (2) register as a new student.

    *To realize this requirement, I added a class which acts as an entry point to the application called ApplicationManager, which uses (1) method login with a username to retrieve a student's information, or use (2) method registerStudent to create a new student, passing the necessary details.*
    
    a. To register as a new student, the user must provide the following student information:
    - A unique username
    - A major
    - A seniority level (i.e., freshman, sophomore, junior, senior, or grad)
    - An email address

    *To accomplish registering as a new student, I created a class called Student, with fields username, major, seniorityLevel and email to represent student information.*

    b. The newly added student is immediately created in the system.
    
    *This is not mapped by the design, as it this task should be performed by a database utility, which doesn't have any business logic.*
    
    c. For simplicity, there is no password creation/authentication; that is, selecting or entering a student username is sufficient to log in as that student.
    
    *There is no need to represent password creation/authentication in the design.*
    
    d. Also for simplicity, student and quiz information is local to a device.
    
    *Distributed computing and communication are not necessary in this design. However, a local database should be set up to store information represented by the Student, Quiz and Score classes.*

2. The application allows students to (1) add a quiz, (2) remove a quiz they created, (3) practice quizzes, and (4) view the list of quiz score statistics.

    *To perform any of these operations, the Student class has a dependency on the Quiz class. To (1) add a quiz, the addQuizz method is used, with all the with quiz details. To remove a quiz (2) the removeQuiz method is used, passing only the quizName as quiz names are unique. This name is obtained after calling the method getStudentQuizzes which returns a list of Quiz belonging to that particular student. To practice quizzes (3) a getPracticeQuizzes has a dependency on Quiz and uses it to retrieve the Quizzes that do not belong to the student. To view the list of quiz score statistics (4), Student depends on ScoreStatistics, which is in charge of retrieving the list of score statistics for a Student's username.* 

3. To add a quiz, a student must enter the following quiz information:
    a. Unique name
    b. Short description
    c. List of N words, where N is between 1 and 10,  together with their definitions 
    d. List of N * 3 incorrect definitions, not tied to any particular word, where N is the number of words in the quiz.

    *A class named Quiz with maps the quiz information. The field quizName represents the unique name for the quiz. the username keeps track of which student created the quiz in order to make them retrievable in the DB. The fields description definitionsDictionary and incorrectDefinitions represent the details of the quiz information. Additionally, a calculated property numberOfWords tracks N.*

4. To remove a quiz, students must select it from the list of the quizzes they created. Removing a quiz must also remove the score statistics associated with that quiz.

    *To remove a quiz, Student uses the method removeQuiz and its dependency to Quiz to perform the action. Also, Student will use the method removeQuizStats from ScoreStatistics to delete the associated statistics to that quiz.*

5. To practice a quiz, students must select it from the list of quizzes created by other students.

    *The dependency between Student and Quiz allows the generation of a List of Quiz in getPracticeQuizzes, so Student can retrieve the desired quiz they want to practice.* 

6. When a student is practicing a quiz, the application must do the following:
    a. Until all words in the quiz have been used in the current practice session: 
    - Display a random word in the quiz word list.
    - Display four definitions, including the correct definition for that word (the other three definitions must be randomly selected from the union of (1) the set of definitions for the other words in the quiz and (2) the set of incorrect definitions for the quiz. 
    - Let the student select a definition and display “correct” (resp., “incorrect”) if the definition is correct (resp., incorrect).

    *An association relationship is generated between Student and Quiz is created whenever a student practices a quiz. A temporary dependency on PracticeSession is created to represent such interaction. It keeps the current practice score in scoreInPercentage which will later be saved once the practice session is finished. Method displayWord depends on Quiz.getRandomWord to display a random word in the quiz word list. In a similar fashion, displayDefinitions depends on Quiz.getDefnintions, which retrieves the correct definition and performs the random selection of incorrect words. getSelection allows the student select a definition. Finally, updateScore and displayCorrectness update scoreInPercentage and let the student know about the correctness of their choice. Properties such as numberOfWords are necessary in PracticeSession to determine whether the session has finished but it's not modelled in the class as it is not part of the busisness logic.*

    b. After every word in the quiz has been used, the student will be shown the percentage of words they correctly defined, and this information will be saved in the quiz score statistics for that quiz and student.

    *An object Score is created after when the practice ends*

7. The list of quiz score statistics for a student must list all quizzes, ordered based on when they were last played by the student (most recent first). Clicking on a quiz must display (1) the student’s first score and when it was achieved (date and time), (2) the student’s highest score and when it was achieved (date and time), and (3) the names of the first three students to score 100% on the quiz, ordered alphabetically.

    *Class ScoreStatistics has a one to many relationship with Score, in a way that a student can list all quizzes by calling method getScoresByMostRecentPlayed. When the student clicks on a quiz, methods getFirstScore (1) and getHighestScore (3) are called to further display the student's first and highest scores, with their dates and times, which are recorded in the Score class. Furthermore, method getFirstThree fetches the first three students with 100% scores for that particular quizz.*
8. The user interface must be intuitive and responsive.

    *This is not represented in my design as it is handled by the GUI implementation*
9. The performance of the game should be such that students do not experience any considerable lag between their actions and the response of the application.

    *This is not represented in the design as the lag between the user interaction and the application response normally involves modelling the GUI and the Database, which are not performing any business logic so they are not represented.*


# General comments
1. Other utility classes, such as Map, List or String are not represented in the design.
