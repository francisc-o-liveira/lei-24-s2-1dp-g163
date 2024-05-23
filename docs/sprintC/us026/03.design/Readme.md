# US026 - Assign one or more Vehicles to an entry in the Agenda

## 3. Design - User Story Realization

### 3.1. Rationale

| Interaction ID | Question: Which class is responsible for...       | Answer                        | Justification (with patterns)                                                                                 |
|:---------------|:--------------------------------------------------|:------------------------------|:--------------------------------------------------------------------------------------------------------------|
| Step 1         | 	... interacting with the actor?                  | AssignEntryOnAgendaUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
|                | 	... coordinating the US?                         | AssignEntryOnAgendaController | Controller                                                                                                    |
|                | ... setting the Entry?                            | EntryRepository               | Creator (Rule 1): in the DM EntryRepository.                                                                  |
| Step 2         | 	...saving the inputted data?                     | Entry                         | IE: object created in step 1 has its own data.                                                                |
| Step 3         | 	...data transfer object?                         | EntryMapper and VehicleMapper | IE: Pattern (Data Transfer Object)                                                                            |
| Step 4         | 	... validating vehicle (global validation)?      | EntryRepository               | IE:  the status have the verification and attribution method by omission.                                     |
| Step 5         | 	... validating data (local validation)?          | Entry                         | IE: owns its data.                                                                                            | 
|                | 	... validating all data (global validation)?     | EntryRepository               | IE: knows all Entry's.                                                                                        | 
|                | 	... saving the set Vehicle?                      | Entry                         | IE: have vehicles.                                                                                            | 
| Step 6         | 	... informing operation success?                 | AssignEntryOnAgendaUI         | IE: is responsible for user interactions.                                                                     | 

### Systematization

According to the taken rationale, the conceptual classes promoted to software classes are(i.e. Creator):

* EntryRepository
* Entry
* Task
* Vehicle

Other software classes (i.e. Information Expert) identified:

* Repositories
* EntryRepository
* VehicleRepository

Other software classes (i.e. Pure Fabrication) identified:

* AssignEntryOnAgendaUI
* AssignEntryOnAgendaController

Other software classes (i.e. Use of Data Transfer Objects (DTO)) identified:

* EntryDTO
* TaskDTO
* VehicleDTO
* EntryMapper
* VehicleMapper


## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative One is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us026-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us026-sequence-diagram-split.svg)

**Get Agenda Entry's List Partial SD**

![Sequence Diagram - Partial - Get Task Category List](svg/us026-sequence-diagram-partial-get-agenda-list.svg)

**Get Vehicle Dto List Partial SD**

![Sequence Diagram - Partial - Create Task](svg/us026-sequence-diagram-partial-get-vehicles-dto.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us026-class-diagram.svg)