# US006 - Register a Vehicle 

## 3. Design - User Story Realization 

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

| Interaction ID | Question: Which class is responsible for...   | Answer            | Justification (with patterns)             |
|:---------------|:----------------------------------------------|:------------------|:------------------------------------------|
| Step 1	        | ...interacting with the actor?                | VehicleUI         | Pure Fabrication                          |
| 			  	         | ...coordinating the US?                       | VehicleController | Controller                                |
| 			  	         | ... knowing the user using the system?        	 | UserSession       |                                           |
| Step 2 		      | ...saving the input data?                     | Vehicle           | IE: object created in step 1 has its own data.|                                                |                                                      |             |                                           |
| Step 3 		      | ...display all data?                          | VehicleUI         |                                           |
| Step 4  		     | ...accepts confirmation?                      | VehicleUI         | Pure Fabrication                          |                                       |                                                                                      | 
| 			            | ...saving the created data?                   | VehicleRepository | Owns all the vehicles                     | 
| Step 5  		     | ...informing operation success?               | VehicleUI         | IE: is responsible for user interactions. | 

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are(i.e. Creator):

* VehicleRepository
* Vehicle

Other software classes

* VehicleUI
* VehicleController
* Repositories


## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us006-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

**Register Vehicle Partial SD**

![Sequence Diagram - Partial - Get Task Category List](svg/us006-sequence-diagram-partia-register-vehicle.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us006-class-diagram.svg)