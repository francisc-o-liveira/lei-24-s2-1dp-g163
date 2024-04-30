# US008 - Show Vehicle Check-Up List

## 3. Design - User Story Realization

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

| Interaction ID | Question: Which class is responsible for... | Answer            | Justification (with patterns)                                                                                 |
|:-------------|:--------------------------------------------|:------------------|:--------------------------------------------------------------------------------------------------------------|
| Step 1  		| 	... interacting with the actor?            | VehicleCheckUpUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
| 			  	| ... knowing the user using the system?      | UserSession       | IE: cf. A&A component documentation.                                                                          |
| Step 2  		| 	...knowing which vehicle to show?          | VehicleCheckUpRepository | IE: the function which shows the list of vehicles are saved on VehicleRepository.                             |
| Step 3  		| 	...informing operation success?            | VehicleCheckUpUI         | IE: is responsible for user interactions.                                                                     | 

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are(i.e. Creator):

* VehicleCheckUpRepository
* Vehicle

Other software classes

* VehicleCheckUpUI
* VehicleCheckUpController
* Repositories

## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us008-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this
user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

**Show List of Cehck-Up List Partial SD**

![Sequence Diagram - split](svg/us008-sequence-diagram-partial-show-list.svg)


## 3.3. Class Diagram (CD)

![Class Diagram](svg/us008-class-diagram.svg)