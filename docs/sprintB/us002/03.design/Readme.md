# US002 - Register a Job 

## 3. Design - User Story Realization 

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

| Interaction ID                                                            | Question: Which class is responsible for...                       | Answer       | Justification (with patterns)                                                                                 |
|:--------------------------------------------------------------------------|:------------------------------------------------------------------|:-------------|:--------------------------------------------------------------------------------------------------------------|
| Step 1: 	asks to register a new job    		                                 | ... instantiating the class that handles the UI?                  | CreateJobUI  | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
| 			  		                                                                   | ... interacting with the actor?                                   | CreateJobUI  | Controller                                                                                                    |
| Step 2: requests data (job name)		  		                                    | ... displaying the form for the actor to input data?              | Organization | Creator (Rule 1): in the DM Organization has a Task.                                                          |
| Step 3: types requested data 	 	  		                                      | ... validating input data? ... temporarily keeping input data?    | UserSession  | IE: cf. A&A component documentation.                                                                          |
| Step 4: requests confirmation	                                            | ... display request confirmation? 							                         | Organization | IE: knows/has its own Employees                                                                               |
| Step 5: confirms data	  		                                                | 	... validating the data locally (mandatory data)?					           | Employee     | IE: knows its own data (e.g. email)                                                                           |
| 	                                                                         | ...... creating the job object?						                             |              |                                                                                                               |
| Step 6: displays operation success and the list of jobs for collaborators.| ... informing operation success?	                                 | CreateJobUI  | Pure Fabrication                                                                                              |
| 		                                                                        | ... saving the created data (the list of jobs)?	                  | System       | IE: Job Categories are defined by the Administrators.                                                         |



### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

* Organization
* Job

Other software classes (i.e Information Expert) identified:

* Repositories
* DocTypeRepository
* JobCategoryRepository

Other software classes (i.e. Pure Fabrication) identified: 

* CreateJobUI  
* CreateJobController


## 3.2. Sequence Diagram (SD)

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us006-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us006-sequence-diagram-split.svg)

**Get Job Category List Partial SD**

![Sequence Diagram - Partial - Get Task Category List](svg/us006-sequence-diagram-partial-get-task-category-list.svg)

**Get Job Category Object**

![Sequence Diagram - Partial - Get Task Category Object](svg/us006-sequence-diagram-partial-get-task-category.svg)

**Get Employee**

![Sequence Diagram - Partial - Get Employee](svg/us006-sequence-diagram-partial-get-collaborator.svg)

**Create Job**

![Sequence Diagram - Partial - Create Task](svg/us006-sequence-diagram-partial-create-task.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us006-class-diagram.svg)