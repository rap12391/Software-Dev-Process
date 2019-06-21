**List of Requirements**

**1 - When starting the application, a user can choose whether to (1) log in as a specific student or (2) register as a new student.**
**To register as a new student, the user must provide the following student information:**
**A unique username**
**A major**
**A seniority level (i.e., freshman, sophomore, junior, senior, or grad)**
**An email address**
**The newly added student is immediately created in the system.**
**For simplicity, there is no password creation/authentication; that is, selecting or entering a student username is sufficient to log in as that student.**
**Also for simplicity, student and quiz information is local to a device**
 
1) The **LoginManager** will handle how to login/register **Student**s.
	1a)The **Student** class will contain information like username,
	   major, seniority level, and email address stored in a **Student** object. 
	1b) The **LoginManager** will handle adding the **Student** immediately
	    to the system via the login(string) function.
	1c) The **LoginManager** will let logins without passwords via
            get**Student**Info(String) function.
        1d) There will be a **QuizManager** that stores results.
        
**2-The application allows students to (1) add a quiz, (2) remove a quiz they created, (3) practice quizzes, 
and (4) view the list of quiz score statistics.**

2) The **QuizManager** will handle adding a quiz (addQuiz()), 
   removing a quiz(removeQuiz()),practice quizzes (practiceQuiz()),
   and viewing the quiz score stats (getQuizStats()).

**3-To add a quiz, a student must enter the following quiz information**:
**Unique name**
**Short description**
**List of N words, where N is between 1 and 10,  together with their definitions**
**List of N * 3 incorrect definitions, not tied to any particular word, where N is the number of words in the quiz.**

3) **QuizManager** will handle adding quiz
	3a) Unique names will be stored in the Quiz object: setUniqueName()
	3b) Short descriptions will be stored in the Quiz object: setDescription()
	3c) Quizzes will contain N number of questions with definitions: setCorrectDefinitions()
	3d) Quizzes will contain N times 3 incorrect answers: setIncorrectDefinitions()
    
**4-To remove a quiz, students must select it from the list of the quizzes they created.
Removing a quiz must also remove the score statistics associated with that quiz.**

4) **QuizManager** will allow the user to remove quizzes:removeQuiz(String)
   removeQuiz will also remove data about the saved quiz.  

**5-To practice a quiz, students must select it from the list of quizzes created by other students.**  
 
5) **QuizManager** will allow the user to access **Student** quizzes: practiceQuiz(String)

**6-When a student is practicing a quiz, the application must do the following:
Until all words in the quiz have been used in the current practice session: 
Display a random word in the quiz word list.
Display four definitions, including the correct definition for that word (the other three definitions must be randomly selected from the union of
(1) the set of definitions for the other words in the quiz and
(2) the set of incorrect definitions for the quiz. Let the student select a definition and display “correct” (resp., “incorrect”)
    if the definition is correct (resp., incorrect).
After every word in the quiz has been used, the student will be shown the percentage of words they correctly defined, and this information will be saved in the quiz score statistics for that quiz and student.**

6) **QuizRunner** will handle the execution of quizzes.
	6a) 
    	   i) **QuizRunner** will get random words from the quiz: getRandomWord()
           ii)**QuizRunner** will get the corect definition and three incorrect ones:  getCorrectDefinition() and getIncorrectDefinition() x3
       	   iii) **QuizRunner** will allow the user to pick a definition and then display
         	the correct answer. selectDefinition() and isCorrect()
  	6b) **QuizRunner** will display the results of the quiz and then return the quiz
            results back to **QuizManager**: getQuizResults()
            
**7-The list of quiz score statistics for a student must list all quizzes, ordered based on when they were last played by the student
 (most recent first). Clicking on a quiz must display 
 (1) the student’s first score and when it was achieved (date and time), 
 (2) the student’s highest score and when it was achieved (date and time), and 
 (3) the names of the first three students to score 100% on the quiz, ordered alphabetically.**
 
7) **QuizManager** will contain running information about the quizzes.
  7a) **QuizManager** will return the **Student**s first score: getFirstScore()
  7b) **QuizManager** will return the **Student**s highest score: getHighestScore()
  7c) **QuizManager** will get the three first **Student**s to get 100% on the quiz: GetFirstThreePerfectScores()
  
**8- The user interface must be intuitive and responsive.**

8) The user interface design was not handled in this design. Therefore,
the design cannot be evaluated for intuitiveness or responsiveness.

**9- The performance of the game should be such that students do not 
experience any considerable lag between their actions and the response of the application.**

9) Performance cannot be taken into account since the design
has not been implemented in a real-time environment.