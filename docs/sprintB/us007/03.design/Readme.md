# US004 - To assign one or more skills to a collaborator.

## 3. Design - User Story Realization

### 3.1. Rationale
| SSD Interaction ID | Question: Which class is responsible for... | Answer | Justification (with patterns) |
|--------------------|---------------------------------------------|--------|------------------------------|
| Step/Msg 1: Selects Employee ID | ...instantiating the class that handles the UI? | `HRUI` | **Creator**: The UI class is created as it is the first point of interaction in the system. |
|                    | ...obtaining the employee IDs list? | `HREmployeeController` | **Controller**: The Controller class is responsible for handling the request and delegating it. |
|                    | ...retrieving employee IDs? | `EmployeeService` | **Information Expert**: Services are typically responsible for data retrieval and business logic. |
| Step/Msg 2: Validates employee ID and displays skills | ...displaying the employee validation and skills? | `HRUI` | **Pure Fabrication**: The UI class should handle all display operations. |
|                    | ...validating employee ID? | `EmployeeValidator` | **Information Expert**: Specific classes should handle their own validation. |
| Step/Msg 3: Selects Skill Name(s) to assign | ...displaying the form for skill selection? | `HRUI` | **Pure Fabrication**: The UI class should handle all display operations. |
|                    | ...obtaining skill names list? | `SkillService` | **Information Expert**: Responsible for providing data about skills. |
| Step/Msg 4: Confirms skill assignment is possible | ...confirming skill assignment possibility? | `SkillValidator` | **Information Expert**: Validator classes should confirm the possibility of an assignment. |
| Step/Msg 5: Confirms skill assignment | ...processing the skill assignment confirmation? | `SkillAssigner` | **Information Expert**: A class dedicated to assigning skills. |
| Step/Msg 6: Assigns Skill(s) to Collaborator | ...assigning skills to the collaborator? | `SkillAssigner` | **Information Expert**: Handles the business logic of assigning skills. |
| Step/Msg 7: Displays operation success message | ...displaying the operation success message? | `HRUI` | **Pure Fabrication**: The UI class is responsible for displaying results to the user. |




### Systematization

According to the taken rationale, the conceptual classes promoted to software classes are:

- Organization
- Task

Other software classes (i.e. Pure Fabrication) identified:

- CreateTaskUI
- CreateTaskController

## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us006-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us006-sequence-diagram-split.svg)

**Get Task Category List Partial SD**

![Sequence Diagram - Partial - Get Task Category List](svg/us006-sequence-diagram-partial-get-task-category-list.svg)

**Get Task Category Object**

![Sequence Diagram - Partial - Get Task Category Object](svg/us006-sequence-diagram-partial-get-task-category.svg)

**Get Employee**

![Sequence Diagram - Partial - Get Employee](svg/us006-sequence-diagram-partial-get-collaborator.svg)

**Create Task**

![Sequence Diagram - Partial - Create Task](svg/us006-sequence-diagram-partial-create-task.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us006-class-diagram.svg)
