## Requirements
1. When starting the application, a user can choose whether to (1) log in as a specific student or (2) register as a new student.
   * To register as a new student, the user must provide the following student information:
      * A unique username
      * A major
      * A seniority level (i.e., freshman, sophomore, junior, senior, or grad)
      * An email address
   * The newly added student is immediately created in the system.
   * For simplicity, there is no password creation/authentication; that is, selecting or entering a student username is sufficient to log in as that student.
   * Also for simplicity, student and quiz information is local to a device.
   
Requirement 1 was met by: The LoginandRegistration class has an attribute for username. Once a user name has been input and "login" has been selected, the checkStudent() method will be invoked and the username will be checked against he usernames in the AllStudents class, if the username exists then a Student will be able to log in, if not, an error will be thrown. If the "register" button is selected, then the register() method will be invoked and the user will be prompted to create a new instance in the Student class. The Student class contains infomartion about a unique username, a major, seniority level, and an email address through the attributes username, major, seniorityLevel and email respectively.

2. The application allows students to (1) add a quiz, (2) remove a quiz they created, (3) practice quizzes, and (4) view the list of quiz score statistics.

Requirement 2 was met by: Incorporating methods addQuiz(), remQuiz(), takeQuiz(), and viewStats() to the Student class. These methods carry out actions 1-4 listed in the requirement respectively. The addQuiz() method creates a new instance of the Quiz class, where student's provide information to make a quiz. The remQuiz() method removes a quiz from the QuizManager class, removing a quiz here will not allow any student to take the quiz. The takeQuiz() method invokes the QuizManager class, which allows the student to only take quizzes from the TakableQuizzes list. The viewstats() method invokes the StatisticsManager class where all quizzes that have been taken are listed. 

3. To add a quiz, a student must enter the following quiz information:
    * Unique name
    * Short description
    * List of N words, where N is between 1 and 10,  together with their definitions 
    * List of N * 3 incorrect definitions, not tied to any particular word, where N is the number of words in the quiz.

Requirement 3 was met by: The addQuiz() method invokes the Quiz class where a new instance of a quiz can be created. The attributes name, description, wordAndDef, and incorrectDef are needed to satisfy requirement 3. The name attribute allows the user to give the quiz a name. The description attribute allows the student to input a description of the class. The wordAndDef attribute are a key, value pairs of all the words and their correct definitions. The incorrectDefs attribute takes a list of the incorrect definitions. Since there should only be 3 incorrect per question, the checkNumofDefs() method will ensure this is the case and will throw an error if not. The checkNumofQuestions() method will check their is no less than 1 word and no greater than 10 words in a quiz.


4. To remove a quiz, students must select it from the list of the quizzes they created. Removing a quiz must also remove the score statistics associated with that quiz.

Requirement 4 was met by: The remQuiz() method within the Student class removes the object for the quiz statistics in StatisticsManager. remQuiz() also invokes the QuizManager and allows a student to remove from the studentQuizzes list, which are quizzes that the student has created.

5. To practice a quiz, students must select it from the list of quizzes created by other students.

Requirement 5 was met by: The takeQuiz() method within the Student class, which invokes the QuizManager and only allows a student to select a quiz from the takableQuizzes list. The takableQuizzes list is a list of quizzes that were not created by the student.

6. When a student is practicing a quiz, the application must do the following:
    * Until all words in the quiz have been used in the current practice session: 
      * Display a random word in the quiz word list.
      * Display four definitions, including the correct definition for that word (the other three definitions must be randomly selected from the union of (1) the set of definitions for the other words in the quiz and (2) the set of incorrect definitions for the quiz. 
      * Let the student select a definition and display “correct” (resp., “incorrect”) if the definition is correct (resp., incorrect).
    * After every word in the quiz has been used, the student will be shown the percentage of words they correctly defined, and this information will be saved in the quiz score statistics for that quiz and student.
    
Requirement 6 was met by: After the takeQuiz() method is invoked and a quiz is selected for taking, the generateQuiz() method is invoked. This method populates the quiz. The quiz questions will be randomized and the 3 randomized incorrect definitions for every question will be pulled from a combination of the incorrectDefs list (which is a list of all incorrect definitions inputted by the creator) in the Quiz class and the definitions in the wordAndDef dictionary (also in the Quiz class) that do not correspond to the correct definition for the prompted word. generateQuiz() also puts the correct definition in a random position amongst the answer choices. After every question, the checkQuestion() method will be invoked and will display "correct" or "incorrect" accordingly depending on if the student selected the right answer. The calcResult() method will be invoked at the end of the quiz to calculate the percentage of questions that were answered correctly throughout the quiz. 
    
7. The list of quiz score statistics for a student must list all quizzes, ordered based on when they were last played by the student (most recent first). Clicking on a quiz must display (1) the student’s first score and when it was achieved (date and time), (2) the student’s highest score and when it was achieved (date and time), and (3) the names of the first three students to score 100% on the quiz, ordered alphabetically.

Requirement 7 was met by: The StatisticsManager class displays a list of quizzes taken by the student through the quizzesTakenList. Once a student selects a quiz the showQuizStat() method is called and utilizes QuizStatistics (the QuizStatistics class is grouped by quizID so all the quizzes generated from the same Quiz class are grouped together) methods studentFirstScore() and studentHighScore() to get a particular student's score when they first took a particular quiz and to get the student's highest quiz score. These values are stored in studentFirstScore and studentHighScore attributes in the StatisticsManager. The showQuizStat() method also steps in the QuizStatistics class for a particular quiz and gets the firstPerfectScores list. This list is populated in the QuizStatistics class through the method compareResultsandListScores(), which gets all the perfect scores for a quiz and takes the names of the first three students to score 100% on the quiz (through date and time attributes) and then arranges the alphabetically.

8. The user interface must be intuitive and responsive.

Requirement 8 was met by: This is not represented in my design, as it will be handled entirely by GUI implementation.


9. The performance of the game should be such that students do not experience any considerable lag between their actions and the response of the application.

Requirement 9 was met by: This is not represented in my design, although it is assumed since the application is running locally and minimal calculations are needed. 
