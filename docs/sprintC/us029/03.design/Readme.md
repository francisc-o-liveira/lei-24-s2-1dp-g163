# US028 - View Tasks Assigned to Collaborator

## 3. Design - User Story Realization

### 3.1. Rationale

_**Note that SSD - Alternative Two is adopted.**_

| Interaction ID | Question: Which class is responsible for... | Answer                         | Justification (with patterns)                                                                                |
|:---------------|:--------------------------------------------|:-------------------------------|:-------------------------------------------------------------------------------------------------------------|
| Step 1  	      | 	...interacting with the actor?             | ViewTaskListAssignedUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model.|
| Step 2         | 	...coordinating the US?                    | ViewTaskListAssignedController | Controller                                                                                                   |
| Step 3         | 	...saving the inputted data?               | Collaborator                   | Controller                                                                                                    |
| Step 4         | 	...recording the completion of a task?     | CollaboratorRepository         | Controller                                                                                                    |
| Step 5         | 	...informing operation success?            | ViewTaskListAssignedUI        | Controller                                                                                                    |


### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are(i.e. Creator):

* CollaboratorRepository
* Collaborator

Other software classes (i.e Information Expert) identified:

* Repositories
* TaskRepository

Other software classes (i.e. Pure Fabrication) identified:

* ViewTaskListAssignedUI
* ViewTaskListAssignedController

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