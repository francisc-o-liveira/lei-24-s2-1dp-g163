# US003 - Register a Collaborator

## 3. Design - User Story Realization 

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

| Interaction ID | Question: Which class is responsible for...                | Answer                 | Justification (with patterns)                                                                                 |
|:---------------|:-----------------------------------------------------------|:-----------------------|:--------------------------------------------------------------------------------------------------------------|
| Step 1  		     | 	... interacting with the actor?                           | GenerateTeamUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
| 			  		        | 	... coordinating the US?                                  | GenerateTeamController | Controller:  Pure Fabrication(Rule 3)                                                                         |
| 			  		        | ... instantiating a new Team?                              | TeamRepository         | Creator (Rule 1): in the DM Organization has a Task.                                                          |
| Step 2  		     | 	...saving the inputted data?                              | Team                   | IE: object created in step 1 has its own data.                                                                |
| Step 3  		     | 	...knowing the Skills to show?                            | Repositories           | IE: Skills are defined by the HRMs. Information Expert(Rule 2)                                                |
| Step 4  		     | 	... saving the selected skill?                            | Team                   | IE: The team have a SkillSet.                                                                                 |
| Step 5  		     | 	...knowing the Collaborators with Skills to generateTeam? | Repositories           | IE: Skills assigned to Collaborators by the HRMs.   Information Expert(Rule 2)                                |
| Step 6  		     | 	... saving the selected Collaborators?                    | Team                   | IE: The team have a List of Collaborators.                                                                    |
| Step 7  		     | 	... validating data (local validation)?                   | Team                   | IE: owns its data.                                                                                            | 
|                | 	... validating all data (global validation)?              | TeamRepository         | IE: knows all its teams.                                                                                      | 
| 		             | 	... saving the registered Team?                           | TeamRepository         | IE: owns all its teams.                                                                                       | 
| Step 8  		     | 	... informing operation success?                          | GenerateTeamUI         | IE: is responsible for user interactions.                                                                     | 

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are(i.e. Creator): 

* TeamRepository
* Team

Other software classes (i.e. Information Expert) identified:

* Repositories
* SkillRepository
* CollaboratorRepository

Other software classes (i.e. Pure Fabrication) identified: 

* GenerateTeamUI  
* GenerateTeamController

## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us005-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us005-sequence-diagram-split.svg)

**Get Skill List Partial SD**

![Sequence Diagram - Partial - Get Task Category List](svg/us005-sequence-diagram-partial-get-skill-list.svg)

**Register Collaborator Object**

![Sequence Diagram - Partial - Get Task Category Object](svg/us005-sequence-diagram-partial-register-collaborator.svg)


## 3.3. Class Diagram (CD)

![Class Diagram](svg/us005-class-diagram.svg)