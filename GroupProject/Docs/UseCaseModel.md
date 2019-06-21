# Use Case Model

**Author**: Team 09

## 1 Use Case Diagram

![use case diagram](https://github.gatech.edu/storage/user/23637/files/a9b44118-c88b-11e8-8b14-96d89bc2e58a)

## 2 Use Case Descriptions

### (1) Register

- Requirements: The use case Register allows new students to register.
- Pre-conditions: Any students who did not register before.
- Post-conditions: A newly added student is immediately created in the system.
- Scenarios: A new student provides username, major, seniority level, and email address to sign up.

### (2) Log In

- Requirements: The use case Log In allows registered students to log in.
- Pre-conditions: Any students registered before.
- Post-conditions: The unique username will be remembered as the student adds, removes, practices, and views stats until another student logs in.
- Scenarios: The registered student only has to select or enter his/her username.

### (3) Add A Quiz

- Requirements: The use case Add A Quiz allows students to add a quiz.
- Pre-conditions: Any registered student who currently occupies the device has logged in and clicked on add a quiz.
- Post-conditions: A newly added quiz is immediately created in the system.
- Scenarios: The student provides quizname, description, N words paired with their correct definitions, and 3N incorrect definitions.

### (4) Remove A Quiz

- Requirements: The use case Remove A Quiz allows students to remove a quiz.
- Pre-conditions: The registered student who currently occupies the device has logged in.
- Post-conditions: The specified quiz along with its stats is removed from the system.
- Scenarios: As the student operates removing a quiz, the included use case **Display Own Quizzes** is first executed. After the student specify the quiz to be removed, *Remove A Quiz* itself and the included use case **Remove Quiz Stats** are executed.

#### (A) Display Own Quizzes

- Requirements: The included use case *Display Own Quizzes* allows the system to retrieve all of quizzes the student created and display the list in the format of scroll down menu, which the student choose from.
- Pre-conditions: Everytime *Remove A Quiz* is executed.
- Post-conditions: A list of all of quizzes the student created will be displayed in the format of scroll down menu, which the student choose from.
- Scenarios: The quizzes will be filtered by their creator (username). Those with creator being the student will be put in the list. The list of quizzes will be displayed in the format of scroll down menu, which the student choose from.

#### (B) Remove Quiz Stats

- Requirements: The included use case *Remove Quiz Stats* allows students to remove quiz stats of the specified quiz.
- Pre-conditions: Once the student chooses any quiz he/she created.
- Post-conditions: The stats of the specified quiz is removed from the system.
- Scenarios: Any stats associated with the specified quiz is removed from the system.


### (5) Practice A Quiz

- Requirements: The use case Practice A Quiz allows students to practice a quiz.
- Pre-conditions: 2 conditions should be met. First, the registered student who currently occupies the device has logged in. Second, the student is not the creator of the specified quiz. The included use case **Verify Creator** listed below will examine whether the student is not the creator and can practice the quiz. If the student is the creator, the extended use case **Deny Practice** will deny the student’s attempt to practice the quiz.
- Post-conditions: The quiz score stats will be saved in the system and can be retrieved once view stats related operations are executed.
- Scenarios: 
	- As the student operates practicing a quiz, *Verify Creator* is first executed. If the exception that the student is the creator occurs, *Deny Practice* is executed. 
	- Otherwise, *Practice A Quiz* itself and the included use case **Display Word and Definitions**, **Get Selected Definition**, **Display If Correct**, **Display Correct Percentage**, and **Save Quiz Score Stats** are executed in order. 
	- Once *Display Word and Definitions* is executed, its included use case **Randomize Words** and **Randomly Select 3 Incorrect Definitions** are executed to shuffle the order of word appearance and randomly select 3 incorrect definitions for each word. 
	- To *Display If Correct*, the included use case **Check Correctness** has to examine whether the student get the answer right before display. 
	- Finally, to *Display Correct Percentage* and *Save Quiz Score Stats*, the included use case **Calculate Correct Percentage** must be executed.

#### (A) Verify Creator

- Requirements: The included use case *Verify Creator* allows the system to examine whether the student is the creator of the quiz
- Pre-conditions: Everytime *Practice A Quiz* is executed.
- Post-conditions: A boolean value is returned.
- Scenarios: The student username is compared with *username (creator)* of the specified quiz.

#### (B) Deny Practice

- Requirements: The extended use case Deny Practice allows the system to deny the student’s attempt to practice the quiz.
- Pre-conditions: If *Verify Creator* returns True.
- Post-conditions: No subsequent included use cases will be executed.
- Scenarios: The subsequent included use cases are suspended.

#### (C) Display Word and Definitions

- Requirements: The included use case *Display Word and Definitions* enables the system to display words in random order and three accompanying incorrect definitions.
- Pre-conditions: If *Verify Creator* returns False.
- Post-conditions: The word-definition sets are displayed and waiting for the student’s choice of definition.
- Scenarios: As *Display Word and Definitions* is executed, the included use case **Randomize Words** and **Randomly Select 3 Incorrect Definitions** are also executed to prepare the list of shuffled word-definition pairs and three incorrect definitions for each word.

##### (a) Randomize Words

- Requirements: The included use case *Randomize Words* enables the system to prepare a list of word-definition pairs in random order.
- Pre-conditions: Everytime *Display Word and Definitions* is executed.
- Post-conditions: A list of word-definition pairs in random order is generated.
- Scenarios: The N word-definition pairs of specified quiz is shuffled.

##### (b) Randomly Select 3 Incorrect Definitions

- Requirements: The included use case *Randomly Select 3 Incorrect Definitions* enables the system to randomly pick three accompanying incorrect definitions for each word.
- Pre-conditions: Everytime *Display Word and Definitions* is executed.
- Post-conditions: A list of three incorrect definitions for each word is generated.
- Scenarios: 3 incorrect definitions are drawn from the 3N incorrect definitions for N times.

#### (D) Get Selected Definition

- Requirements: The included use case *Get Selected Definition* allows the system to get the student’s selection of definition to each word.
- Pre-conditions: If a word-definition set is displayed.
- Post-conditions: The selected definition will be recorded and can be used to check correctness.
- Scenarios: The selected definition for each word is recorded.

#### (E) Display If Correct

- Requirements: The included use case *Display If Correct* allows the system to display if the student get each answer right.
- Pre-conditions: Once the student’s answer to the current words is collected.
- Post-conditions: The feedback of correctness to each word is displayed.
- Scenarios: “Correct” is displayed if the answer matches the correct definition. That is, if the included use case **Check Correctness** return True. Otherwise, “Incorrect” is displayed.

##### (a) Check Correctness

- Requirements: The included use case *Check Correctness* allows the system to compare the student’s answer to the correct definition.
- Pre-conditions: Everytime *Display If Correct* is executed.
- Post-conditions: A boolean value is returned.
- Scenarios: The system checks if the answer matches the correct definition. If yes, return True. Otherwise, return False.

#### (F) Display Correct Percentage

- Requirements: The included use case *Display Correct Percentage* allows the system to display the final correct percentage once the whole list of N words are completed.
- Pre-conditions: Once the student answers all the questions.
- Post-conditions: The overall correct percentage is displayed.
- Scenarios: The included use case **Calculate Correct Percentage** is executed as *Display Correct Percentage* is executed. The *Calculate Correct Percentage* returns the overall correct percentage to be displayed.

##### (a) Calculate Correct Percentage

- Requirements: The included use case *Calculate Correct Percentage* allows the system to sum up the count of correct answers and divide it by N.
- Pre-conditions: Everytime *Display Correct Percentage* is executed.
- Post-conditions: A float value indicating the correct percentage is returned.
- Scenarios: The system to sum up the count of correct answers and divide it by N.

#### (G) Save Quiz Score Stats

- Requirements: The included use case *Save Quiz Score Stats* allows the system to save the final correct percentage once the whole list of N words are completed.
- Pre-conditions: Once the student answers all the questions.
- Post-conditions: The overall correct percentage is saved.
- Scenarios: The included use case **Calculate Correct Percentage** is executed as *Save Quiz Score Stats* is executed. The *Calculate Correct Percentage* returns the overall correct percentage to be saved.

##### (a) Calculate Correct Percentage

- Requirements: The included use case *Calculate Correct Percentage* allows the system to sum up the count of correct answers and divide it by N.
- Pre-conditions: Everytime *Save Quiz Score Stats* is executed.
- Post-conditions: A float value indicating the correct percentage is returned.
- Scenarios: The system to sum up the count of correct answers and divide it by N.

### (6) View All Quiz Stats List

- Requirements: The use case View All Quiz Stats List allows the system to display the list of all the quizzes including those done by the student listed on the top in the order of recency and those not done at the bottom in any order.
- Pre-conditions: Any registered student who currently occupies the device has logged in and clicked on view quiz stats.
- Post-conditions: The whole list of quizzes is displayed.
- Scenarios: The included use case **Sort Quiz List** is executed as *View All Quiz Stats List* is executed. The *Sort Quiz List* returns the list of quizzes ordered by recency to be displayed.

#### (A) Sort Quiz List

- Requirements: The included use case *Sort Quiz List* allows the system to sort the quizzes done by the student in the order of recency on the top of the list and those not done by the student at the bottom in any order.
- Pre-conditions: Everytime *View All Quiz Stats List* is executed.
- Post-conditions: A list of sorted quizzes is returned.
- Scenarios: Quizzes that are done by the student is sorted by the most-recent-first rule and put at the front of the list. Quizzes that are not done by the student are appended after those done.

### (7) View Single Quiz Stats

- Requirements: The use case View Single Quiz Stats allows the system to display the student’s first score and time, the student’s highest score and time, and the names of first three students who score 100% for the single specified quiz.
- Pre-conditions: Any registered student who currently occupies the device has logged in, viewed quiz stats, and clicked on a specified quiz.
- Post-conditions: The student’s first score and time, the student’s highest score and time, and the names of first three students who score 100 % for the single specified quiz are displayed.
- Scenarios: 
	- As the student operates View Single Quiz Stats, three included use case **Display First Score and Time**, **Display Highest Score and Time**, and **Display First 3 Students Who Score 100%** are executed. 
	- *Display First Score and Time* retrieves the student’s score and time when the student first practice the quiz. 
	- *Display Highest Score and Time* retrieves the student’s highest score and time when the student achieved that score. 
	- *Display First 3 Students Who Score 100%* retrieves the names of the three students who first who score 100%.
	- Three above stats are returned and displayed.

#### (A) Display First Score and Time

- Requirements: The included use case *Display First Score and Time* allows the system to retrieve the student’s score and time when the student first practice the quiz.
- Pre-conditions: Everytime *View Single Quiz Stats* is executed.
- Post-conditions: A tuple of score and time is returned.
- Scenarios: The student’s score of the specified quiz is sorted ascendingly by time. The first score and time is returned.

#### (B) Display Highest Score and Time

- Requirements: The included use case *Display Highest Score and Time* allows the system to retrieve the student’s highest score and time when the student achieved that score.
- Pre-conditions: Everytime *View Single Quiz Stats* is executed.
- Post-conditions: A tuple of score and time is returned.
- Scenarios: The student’s score of the specified quiz is sorted descendingly by score. The first score and time is returned.

#### (C) Display First 3 Students Who Score 100%

- Requirements: The included use case *Display First 3 Students Who Score 100%* allows the system to retrieve the names of the three students who first who score 100%.
- Pre-conditions: Everytime *View Single Quiz Stats* is executed.
- Post-conditions: A list of three usernames is returned.
- Scenarios: The score stats of the specified quiz is filtered by score 100% and sorted ascendingly by time. The first three names are sorted alphabetically. The sorted list of three names is returned.
