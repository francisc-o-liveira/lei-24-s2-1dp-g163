# US022 - Add a new entry in Agenda

## 3. Design - User Story Realization 

### 3.1. Rationale

_**Note that SSD - Alternative Two is adopted.**_

| Interaction ID | Question: Which class is responsible for...        | Answer                        | Justification (with patterns)                                                                                                                                               |
|:---------------|:---------------------------------------------------|:------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1  		     | 	... interacting with the actor?                   | AssignEntryOnAgendaUI         | Pure Fabrication: The AssignEntryOnAgendaUI manages user interaction to keep the UI logic separate from the business logic, ensuring high cohesion and low coupling.        |
| 			  		        | 	... coordinating the US?                          | AssignEntryOnAgendaController | Controller: The AssignEntryOnAgendaController coordinates the process, delegating the request to appropriate handlers, ensuring separation of concerns and central control. |
| 			  		        | ... set the Entry?                                 | EntryRepository               | Information Expert: The EntryRepository holds the agenda data and is responsible for providing it.                                                                          |
| 	Step 2	       | 	...have the entries too show?						               | ToDoList                      | Information Expert: The ToDoList have the entry's that can be assigned on Agenda.                                                                                           |
| Step 3  		     | 	...saving the inputted data?                      | Entry                         | Creator: The Entry are responsible to owns all the data.                                                                                                                    |
| Step 4         | ... saving the entry?                              | AgendaList                    | Information Expert: The AgendaList saves the entries that are assigned to him.                                                                                              |
| Step 5  		     | 	...data transfer object?                          | EntryMapper                   | Pure Fabrication: The EntryMapper converts entry entities to DTOs, separating transformation logic from business logic.                                                     |
| Step 6  		     | 	... validating startDate (local validation)? 				 | EntryRepository               |                                                                                                                                                                             |
| Step 7  		     | 	... validating data (local validation)?           | Entry                         |                                                                                                                                                                             | 
|                | 	... validating all data (global validation)?      | EntryRepository               |                                                                                                                                                                             | 
| Step 8		       | 	... saving the set Entry?                         | EntryRepository               |                                                                                                                                                                             | 
| Step 9  		     | 	... informing operation success?                  | AssignEntryOnAgendaUI         |                                                                                                                                                                             | 

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are(i.e. Creator): 

* EntryRepository
* Entry
* Task


Other software classes (i.e Information Expert) identified:

* Repositories
* EntryRepository

Other software classes (i.e. Pure Fabrication) identified: 

* AssignEntryOnAgendaUI  
* AssignEntryOnAgendaController

Other software classes (i.e. Use of Data Transfer Objects (DTO)) identified:

* EntryDTO
* TaskDTO
* EntryMapper

## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative One is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us022-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us022-sequence-diagram-split.svg)

**Get To Do List Partial SD**

![Sequence Diagram - Partial - Get To Do List](svg/us022-sequence-diagram-partial-get-to-do-list.svg)

**EntryDto To Entry SD**

![Sequence Diagram - Partial - Mapper](svg/us022-sequence-diagram-partial-to-model-mapper.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us022-class-diagram.svg)