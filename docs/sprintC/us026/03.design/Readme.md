# US026 - Assign one or more Vehicles to an entry in the Agenda

## 3. Design - User Story Realization 

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

| Interaction ID | Question: Which class is responsible for...          | Answer                         | Justification (with patterns)                                                                                 |
|:---------------|:-----------------------------------------------------|:-------------------------------|:--------------------------------------------------------------------------------------------------------------|
| Step 1  		     | 	... interacting with the actor?                     | RegisterCollaboratorUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
| 			  		        | 	... coordinating the US?                            | RegisterCollaboratorController | Controller                                                                                                    |
| 			  		        | ... instantiating a new Collaborator?                | CollaboratorRepository         | Creator (Rule 1): in the DM CollaboratorRepository.                                                           |
| 		             | 							                                              |                                |                                                                                                               |
| Step 2  		     | 	...saving the inputted data?                        | Collaborator                   | IE: object created in step 1 has its own data.                                                                |
| Step 3  		     | 	...knowing the Job categories to show?              | Repositories                   | IE: Job Categories are defined by the Administrators.                                                         |
| Step 4  		     | 	... saving the selected category?                   | Collaborator                   | IE: object created in step 1 is classified in one jobCategory.                                                |
| Step 5  		     | 	...knowing the DocType to show?                     | Enum DocType                   | IE: DocType are static and final.                                                                             |
| Step 6  		     | 	... saving the selected docType?                    | Collaborator                   | IE: object created in step 1 is classified in one docType.                                                    |
| Step 7  		     | 	... validating docIDNumber (local validation)? 				 | docType                        | IE:  the docType have the verification method by omission.                                                    |
| Step 8  		     | 	... validating data (local validation)?             | Collaborator                   | IE: owns its data.                                                                                            | 
|                | 	... validating all data (global validation)?        | CollaboratorRepository         | IE: knows all collaborators.                                                                                  | 
| Step 9		       | 	... saving the registered Collaborator?             | CollaboratorRepository         | IE: owns all collaborators.                                                                                   | 
| Step 10  		    | 	... informing operation success?                    | RegisterCollaboratorUI         | IE: is responsible for user interactions.                                                                     | 

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are(i.e. Creator): 

* Collaborator Repository
* Collaborator

Other software classes (i.e Information Expert) identified:

* Repositories
* DocTypeRepository
* JobCategoryRepository
* CollaboratorRepository

Other software classes (i.e. Pure Fabrication) identified: 

* RegisterCollaboratorUI  
* RegisterCollaboratorController

## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us006-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us003-sequence-diagram-split.svg)

**Get Job Category List Partial SD**

![Sequence Diagram - Partial - Get Task Category List](svg/us003-sequence-diagram-partial-get-job-category-list.svg)

**Register a Collaborator Partial SD**

![Sequence Diagram - Partial - Create Task](svg/us003-sequence-diagram-partial-register-collaborator.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us003-class-diagram.svg)