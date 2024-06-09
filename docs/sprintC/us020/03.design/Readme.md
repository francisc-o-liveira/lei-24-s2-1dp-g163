# US020 - Register a Green Space

## 3. Design - User Story Realization 

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

| Interaction ID                                                                                                             | Question: Which class is responsible for...                       | Answer                       | Justification (with patterns)                                                                                                                                             |
|:---------------------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------|:-----------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1: asks to register a new green space 		                                                                              | 	... interacting with the actor?                                  | RegisterGreenSpaceUI         | **Pure Fabrication**: The `RegisterGreenSpaceUI` manages user interaction to keep the UI logic separate from the business logic, ensuring high cohesion and low coupling. |
| 			  		                                                                                                                    | 	... coordinating the US?                                         | RegisterGreenSpaceController | **Controller**: The `RegisterGreenSpaceController` manages the regist process, ensuring central control and coordination.                                                 |
| 			  		                                                                                                                    | ... saving green space tyes?                                      | Organization                 | **Information Expert**: The `Organization` manages data persistence and is responsible for saving green space types.                                                      |	
| Step 2: Displays all available green space types and requests the name, address, area, and type for the new green space 		 | 	...returning green space type list?	                             | RegisterGreenSpaceController | **Controller**: The `RegisterGreenSpaceController` manages the process of returning green space types.                                                                    |
| 	                                                                                                                          | 	...displaying all available green space types?                   | RegisterGreenSpaceUI         | **Pure Fabrication**: The `RegisterGreenSpaceUI` manages user interaction to keep the UI logic separate from the business logic, ensuring high cohesion and low coupling. |
| 	                                                                                                                          | 	... saving green space types?                                    | Organization                 | **Information Expert**: The `Organization` manages data persistence and is responsible for saving green space types.                                                      |
| Step 3:Provides the name, address, area, and selects a green space type		                                                  | 	... receives the data and requests the organization repository to register the new green space.                                                             | RegisterGreenSpaceController | **Controller**: The `RegisterGreenSpaceController` coordinates the process of receiving the data and requests to register the new green space.                            |
|                                                                                                                            | 	... saving the name, area and address                            | Organization                 | **Information Expert**: The `Organization` manages data persistence and is responsible for saving green space name, area and address.                                     |
| 		                                                                                                                         | 	... verifying if the green space exits and save the data?			     | Organization                 | **Information Expert**: The `Organization` manages data persistence and is responsible for verifying and save data.                                                       |
| Step 4: displays operation success		                                                                                       | ... informing operation success?                                  | RegisterGreenSpaceUI         | **Pure Fabrication**: The `RegisterGreenSpaceUI` presents feedback to the user, maintaining separation of concerns between UI and business logic                          | 
 		                                                                                                          
### Systematization ##

Software classes (i.e. **Pure Fabrication**) identified

* RegisterGreenSpaceUI


Other software classes (i.e. **Controller**) identified

* RegisterGreenSpaceController

Other software classes (i.e. **Information Expert**) identified

* Organization
* GreensSpace


## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us020-sequence-diagram-full.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us020-class-diagram.svg)