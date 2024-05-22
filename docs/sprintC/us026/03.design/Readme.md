# US026 - Assign one or more Vehicles to an entry in the Agenda

## 3. Design - User Story Realization 

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

| Interaction ID | Question: Which class is responsible for...           | Answer                           | Justification (with patterns)                                                                                 |
|:---------------|:------------------------------------------------------|:---------------------------------|:--------------------------------------------------------------------------------------------------------------|
| Step 1  		     | 	... interacting with the actor?                      | AssignedVehicleToEntryUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
| 			  		        | 	... coordinating the US?                             | AssignedVehicleToEntryController | Controller                                                                                                    |
| 			  		        | ... attributing the vehicles?                         | EntryRepository                  | Creator (Rule 1): in the DM CollaboratorRepository.                                                           |
| 		             | 							                                               |                                  |                                                                                                               |
| Step 3  		     | 	...knowing the Vehicles to show?                     | Repositories                     | IE: Job Categories are defined by the Administrators.                                                         |
| Step 4  		     | 	... saving the selected Vehicle?                     | Entry                            | IE: object created in step 1 is classified in one jobCategory.                                                |
| Step 5  		     | 	... filtering the vehicles (global validation)? 				 | EntryRepository                  | IE:  the docType have the verification method by omission.                                                    |
| Step 6		       | 	... saving the attributed Collaborator?              | CollaboratorRepository           | IE: owns all collaborators.                                                                                   | 
| Step 7  		     | 	... informing operation success?                     | AssignedVehicleToEntryUI         | IE: is responsible for user interactions.                                                                     | 

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are(i.e. Creator): 

* EntryRepository
* VehicleRepository
* Entry
* Task
* Vehicle

Other software classes (i.e Information Expert) identified:

* Repositories
* EntryRepository
* VehicleRepository

Other software classes (i.e. Use of Data Transfer Objects (DTO)) identified:

* EntryDTO
* TaskDTO
* VehicleDTO

Other software classes (i.e. Pure Fabrication) identified: 

* AssignedVehicleToEntryUI  
* AssignedVehicleToEntryController

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

![Sequence Diagram - Partial - Get Task Category List](svg/us026-sequence-diagram-partial-get-job-category-list.svg)

**Get Vehicle Dto List Partial SD**

![Sequence Diagram - Partial - Create Task](svg/us026-sequence-diagram-partial-register-collaborator.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us026-class-diagram.svg)