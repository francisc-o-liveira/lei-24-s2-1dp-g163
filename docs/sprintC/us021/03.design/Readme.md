# US021 - Add a new Entry to the To-Do List

## 3. Design - User Story Realization 

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

| SSD Interaction ID                   | Question: Which class is responsible for... | Answer                 | Justification (with patterns)                                                                                                                                                                                                  |
|--------------------------------------|---------------------------------------------|------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1: createRegisterTaskController()    | create the controller                       | RegisterTaskController | **Controller**: The `:RegisterTaskController` handles the request to add a new controller, coordinating the necessary operations between the UI and the data layer without performing business logic or data retrieval itself. |
| 2: getInstance()                     | get the instance of repository              | Repositories           | **Information Expert**: The `Repositories` knows how to extract the information, as it holds the knowledge of data and structure.                                                                                              |
| 3: getEntryRepository()              | get the entry repository                    | Repositories           | **Information Expert**: The `Repositories` knows how to extract the information, as it holds the knowledge of data and structure.                                                                                              |
| 4: getGreenSpaceList()               | get green space list                        | GreenSpace             | **Information Expert**: The `GreenSpace` knows how to extract the the green space list, as it holds the knowledge of data and structure.                                                                                       |                                                                                                                                                                                                                                   |
| 5: toDto(greenSpaceList)             | who creates the dto with the data           | GreenSpaceMapper       | **Creator**: The `GreenSpaceMapper` is responsible for create the dto with the data.                                                                                                                                           |
| 6: greenSpace = getGreenSpace()      | providing the green space                   | GreenSpace             | **Information Expert**: The `GreenSpace` contains the information, making it the expert on providing this piece of information when needed.                                                                                    |
| 7: greenSpaceDto = toDto(greenSpace) | create the green space dto                  | GreenSpaceMapper       | **Pure Fabrication**: The `GreenSpaceMapper` is a utility class created to handle data transformation tasks, such as adding the green space DTO, without adding complexity to the business logic.                              |
| 8: add(GreenSpaceDto)                | add the green space Dto                     | GreenSpaceDto          | **Information Expert**: The `GreenSpaceDto` class knows how to assign a team to itself, as it holds the data and methods for managing its state.                                                                               |
| 9: getDegreeUrgency()                | get the degree urgency                      | RegisterTaskController | **Controller**: The `RegisterTaskController` class gets the degree urgency.                                                                                                                                                    |
| 10: create(entryDto)                 | create the entry                            | Entry                  | **Creator**: The `Entry` creates the new entry.                                                                                                                                                                                |

### Systematization ##

Software classes (i.e. **Pure Fabrication**) identified:
* RegisterTaskUI

Other software classes (i.e Information Expert) identified:

* Repositories
* Organization

Other software classes (i.e. Pure Fabrication) identified:

* GreenSpaceMapper


## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us021-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us021-sequence-diagram-split.svg)

**Get Green Space List Partial SD**

![Sequence Diagram - Partial](svg/us021-sequence-diagram-partial-get-greenspace-list.svg)

**Register New Entry Partial SD**

![Sequence Diagram - Partial](svg/us021-sequence-diagram-partial-register-new-entry.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us021-class-diagram.svg)