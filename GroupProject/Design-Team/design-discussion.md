### Section 1: Individual Design Discussions

#### Design 1
![david](https://github.gatech.edu/storage/user/22182/files/038708ca-c273-11e8-83bd-66a6b1b50c2a)  

**Pros:**

* Relationship between PracticeSession, Score, and ScoreStatistics is simple and intuitive. It is clear that once a PracticSession is completed, the scores will be computed and uploaded to the ScoreStatistics class
  
* Dependent relationships between the ApplicationManager and Student classes are well addressed. It is clear that the ApplicationManager uses the Student class to verify login and registration.
  
* Aggregate relationship between Score and ScoreStatistics is well defined. One score is only one part of the ScoreStatistics class.
  
* PracticeSession entity only exists in the context of practicing a Quiz, similar to the example given in the Library diagram where checkout made sense when a Patron requested an item.
  
**Cons:**

* Diagram could address the relationship between the Student class and how the list of quizzes taken from other peers are managed.
  
* Relationship between Student and ScoreStatistics should be better defined. Although the Student class does use resources from the ScoreStatistics class, there is also a relationship where removing a student can modify the ScoreStatistics class.


#### Design 2
![hungyi](https://github.gatech.edu/storage/user/22182/files/3dc62be2-c278-11e8-9566-23c2d28282b4)

**Pros:**

* Each instance of the Student class houses all the information necessary to create, view, and take a quiz for a particular student

* Simplifies logging in by referring to the Student class. Interface will match against a Student class instance

**Cons:**

* Storing quiz statistics and quizzes can be simplified by adding a layer of abstraction beyond the Quiz class

* Functionality can be separated into more classes (Single Responsibility Principle)


#### Design 3
![alex](https://github.gatech.edu/storage/user/22182/files/19df83e0-c273-11e8-921c-25e2dc6bcef3)  

**Pros:**

* Quiz Manager houses QuizStats and Quizzes, so removing a quiz and it’s statistics can be done in one class. Also, viewing and displaying statistics can easily be managed when it’s all in one class.

* Aggregate relationships between QuizManager, Quiz, QuizStats and QuizResults are well defined.

* Multiplicity is well defined between relationships

* Login manager separates the responsibility of registering or logging in as a new student in the system.

**Cons:**

* Adding or removing quizzes as a Student has many chained dependencies.

* The relationships between the Student and the Quiz, QuizRunner, and Quiz Manager classes when practicing a session are unclear.


#### Design 4
![ronak](https://github.gatech.edu/storage/user/22182/files/2112656a-c273-11e8-8635-701e784d6481)

**Pros:**

* There is a clear separation of responsibilities for all entities in the diagram, which makes it easy to follow how each of the requirements is met.

* Good abstraction of requirements that were not explicitly stated in the Requirements document in classes such as AllStudents and QuizManager.

* Utility classes are defined for date and time stamping

**Cons:**

* Student depends on Quiz to add a quiz. This task could be delegated to the Quiz Manager to remove one dependency

* It is unclear how the Practice Session Requirement (6) is met.

* The relationship between StatisticsManager and QuizStatistics is unclear (has / depends on).


### Section 2: Team Design 

![vocabquizgroup 4](https://github.gatech.edu/storage/user/22182/files/0101d0e0-c349-11e8-89d2-1aa4396ed897)

**Requirements**  

1. When starting the application, a user can choose whether to (1) log in as a specific student or (2) register as a new student.
To register as a new student, the user must provide the following student information:

   * A unique username
   * A major
   * A seniority level (i.e., freshman, sophomore, junior, senior, or grad)
   * An email address

   The newly added student is immediately created in the system.  
   For simplicity, there is no password creation/authentication; that is, selecting or entering a student username is sufficient to login as that student.  
   Also for simplicity, student and quiz information is local to a device.  

---

   *Meeting the Requirement:*

The LoginManager class will handle functionality to login in as a specific student or register as a new student via the login() and registerUser() methods. (a) When a user is registered, the username, major, seniority, and email address of the student will be stored in an instance of the student class. (b) The newly added student will be stored in a local database on the device via the persistStudent() method. This will allow student info to persist even after the application stops running. (c) As stated before, the login() method will handle logging in by simply passing in a student username. (d) Similarly to requirement b, student and quiz information will be held in a local database on the running device.  

---

2. The application allows students to (1) add a quiz, (2) remove a quiz they created, (3) practice quizzes, and (4) view the list of quiz score statistics.  

---  

   *Meeting the Requirement:*

The ApplicationManager class will handle functionality to manage quizzes. (1) The user will be able to add a quiz via the addQuiz() method. When adding a quiz, the user will pass a series of parameters to populate quizName, correctDefinitions with N number of questions and incorrectDefinitions with N*3 number of incorrect answers. (2) The user will also be able to remove a quiz that was created by first calling the getQuizList(Student.username) method, returning only the quizzes belonging to that particular student, and then using the removeQuiz(Quiz.quizName) method. (3) The ApplicationManager will allow users to practice quizzes using the practiceQuiz(Student.username) method. The practiceQuiz() method will allow the user to take a quiz and start a practiceSession. (4) The user will be able to view all score statistics by calling the getStats() which uses QuizScoreStatistics to view past quiz results.

---  

3. To add a quiz, a student must enter the following quiz information:
   * Unique name
   * Short description
   * List of N words, where N is between 1 and 10,  together with their definitions 
   * List of N * 3 incorrect definitions, not tied to any particular word, where N is the number of words in the quiz.  

---

   *Meeting the Requirement:*

The Student class will use the addQuiz() method under the ApplicationManager class. Then the unique quizName, Student.username, description, correctDefinitions, and incorrectDefinitions will be passed and used to create a new Quiz object.

---

4. To remove a quiz, students must select it from the list of the quizzes they created. Removing a quiz must also remove the score statistics associated with that quiz.

---

   *Meeting the Requirement:*

The Student class will use the removeQuiz(Quiz.quizName) method under the ApplicationManager class only among the list of quizzes created by the student, after using the getQuizList method. The removeQuiz() under ApplicationManager will also call removeQuizStat() under the QuizScoreStatistics class to remove each of the Scores associated with the Quiz object.

---

5. To practice a quiz, students must select it from the list of quizzes created by other students.

---

   *Meeting the Requirement:*
   
The list of quizzes that a student can take is separated from the quizzes the student created through the username attribute in the Quiz class. Only quizzes that other students have created will be shown before selecting a quiz to take via the method getPracticeQuizzes(Student.username). When selecting a quiz to take, the student will invoke the practiceQuiz() method in the Quiz class. This will start a practice session between the student and the quiz, passing a shuffled version of correctDefinitions via the method shuffleWords(), as well as and the incorrect definitions, which will be used in a randomized draft process (with replacement) while running the practice session.

---

6. When a student is practicing a quiz, the application must do the following:
   * Until all words in the quiz have been used in the current practice session: 
     * Display a random word in the quiz word list.
     * Display four definitions, including the correct definition for that word (the other three definitions must be randomly selected from the union of (1) the set of definitions for the other words in the quiz and (2) the set of incorrect definitions for the quiz. 
     * Let the student select a definition and display “correct” (resp., “incorrect”) if the definition is correct (resp., incorrect).
   * After every word in the quiz has been used, the student will be shown the percentage of words they correctly defined, and this information will be saved in the quiz score statistics for that quiz and student.
   
---

   *Meeting the Requirement:*
   
An association relationship is generated between Student and Quiz when the method practiceQuiz is called. A class called PracticeSession is initialized with the shuffledWords and the incorrectDefinitions. The class also has a score scoreAsPercentage to keep track of the correct answers, and it will later be saved once the practice session is finished. The practice() method iterates through shuffledWords as the session carries on. The method displayWord() is used to show each shuffledWord along with displayIncorrectDefs(), which draws 3 incorrect definitions from incorrectDefinitions in order to display them among the options. In addition, method getSelection allows the student to select a definition. Finally, updateScore and displayCorrectness update scoreAsPercentage and let the student know about the correctness of their choice, using numberOfCorrectWords and numberOfWords as auxiliary variables.

---

7. The list of quiz score statistics for a student must list all quizzes, ordered based on when they were last played by the student (most recent first). Clicking on a quiz must display (1) the student’s first score and when it was achieved (date and time), (2) the student’s highest score and when it was achieved (date and time), and (3) the names of the first three students to score 100% on the quiz, ordered alphabetically.

---

   *Meeting the Requirement:*

When a student wants to view a quiz statistics the getStats(Quiz.quizName) method will be called in the ApplicationManager. This will in turn call the queryAllQuizStatsByMostRecent() method within the QuizScoreStatistics class. This method will display a list of all the quizzes and will prioritize the quizzes most recently taken by the student first. If a student wants to view a particular quiz, the querySingleQuizStats(Quiz.quizName) method will be called, which will display the following statistics by utilizing the methods getStudentFirstScore, getStudentHighScore, and getFirstThreePerfectScores, respectively:

   * The student’s first score and when it was achieved (date and time)
   * The student’s highest score and when it was achieved (date and time)
   * The name of the first three students to score 100%, alphabetically ordered

---

8. The user interface must be intuitive and responsive.

---

   *Meeting the Requirement:*
   
   This is not represented in the design as it is handled by the GUI implementation

---

9. The performance of the game should be such that students do not experience any considerable lag between their actions and the response of the application.

---

   *Meeting the Requirement:*
   
   This is not represented in the design as the lag between the user interaction and the application response normally involves modelling the GUI and the Database, which are not performing any business logic so they are not represented.
	
---

**Clarification and Additional Requirements from the Assignment Rubric:**  
  
Your friend has also supplied some additional clarification for some of the requirements, based on discussions with the students in your team and in other teams:
  
1. Requirement 7 (quiz score statistics):  

   * The quiz score statistics for a student S should list all quizzes, whether they were played by S or not, and including the quizzes created by S.
   * The quizzes not played by S can be displayed in any order (after the ones played).
   
   * For quizzes not played by S, only the names of the first three students to score 100% on the quiz should be displayed.
   
   * The names displayed (and used to sort) in the statistics for the first three students to score 100% on the quiz can be either their usernames or their real names.
   
---
   
   *Meeting the Requirement:*
   
   Clarifications for Requirement 7 were already addressed above
   
---
   
2. Requirement 6 (practicing a quiz):

   * Every word in a quiz should be shown once and only once.
   
   * Incorrect definitions, conversely, may repeat.
   
---

*Meeting the Requirement:*
   
The attribute shownWords keeps track of all the words shown during the PracticeSession so far. The implementation of displayWord should now take into consideration returning a Word that is not in shownWords.  

The implementation of displayDefinitions should not change.

---

3. General clarification: all relevant data (scores, statistics, quizzes, student logins) should persist between uses of the application.

---

   *Meeting the Requirement:*
   
The set of utility Data Object classes: StudentDO, QuizDO and ScoreDO, are in charge of persisting data of Student, Quiz and Score to the local database.


### Section 3: Summary

We discussed as a team each design and created a final version, in three virtual meetings throughout the week. In the process, we learned that:

* There should be a balance between the complexity of what the class diagram can represent and its capability to explain how the different criteria are met. For instance, a design with few classes can be difficult to read because some of the classes can contain multiple responsibilities so it is hard to see the relationships between the modeled entities. On the other hand, if a class diagram is too detailed, the main functionality get lost while following chained relationships until getting to the correct class. Keeping this in mind, we tried to keep the class count marginal. Our application as 7 total classes and the relationships between each are clearly defined.

* Single responsibility is important. We kept in mind that application requirements may change with time. By making sure each class is responsible for a certain requirement, only the code for a particular class will have to be modified if requirements change. 

* A lot of communication is required in order to understand implicit assumptions about what the system is meant to do, as well as the way in which the class diagram meets the requirements. We found that effective communication eliminated a lot of complexity in our diagram and kept us from implementing class attributes and methods that repeated themselves.


