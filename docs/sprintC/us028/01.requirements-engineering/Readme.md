# US028 - View Tasks Assigned to Collaborator

## 1. Requirements Engineering

### 1.1. User Story Description

As a Collaborator, I wish to consult the tasks assigned to me.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>  The Agenda is made up of entries that relate to a task (which was previously in the To-Do List), the team that will carry out the task, the vehicles/equipment assigned to the task, expected duration, and the status (Planned, Postponed, Canceled, Done).

>  Collaborator – a person who is an manager in the organization and carries out design, construction and/or maintenance tasks for green areas, depending on their skills.

>  Tasks are carried out on an occasional or regular basis, in one or more green spaces, for example: tree pruning, installation of an irrigation system, and installation of a lighting system.
Teams are temporary associations of employees who will carry out a determined set of tasks in one or more green spaces. When creating multipurpose teams, the number of members and the set of skills that must be covered are crucial.

**From the client clarifications:**

> **Question:** What type of Status can the Collaborator see assigned to him!
>
> **Answer:** 

> **Question:**  
>
> **Answer:** 

> **Question:** 
>
> **Answer:**


### 1.3. Acceptance Criteria

* **AC1:** The list of green spaces must be sorted by date, starting with the first to be performed.
* **AC2:** The Collaborator should be able to filter the results by the status of the task.
* **AC3:** 

### 1.4. Found out Dependencies

* 

### 1.5 Input and Output Data

**Input Data:**

* Sorted Options:
  * By Green Space
  * By Date (Month/Week/Year)
  * By Status of Entry


**Output Data:**

* **TableView/Calendar with the Entry's assigned to Collaborator**
  - Sort options by date, green space, status of entry's
* **Warnings or Errors (if applicable):**
  - Error messages for any issues encountered during the process of getting the entry's, such non-existent data or duplications ,etc...
* **Operational Feedback:**
  - Overall status of the operation (success or failure), with immediate feedback to the Collaborator.


### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us003-system-sequence-diagram-alternative-one.svg)

#### Alternative Two

![System Sequence Diagram - Alternative Two](svg/us003-system-sequence-diagram-alternative-two.svg)

### 1.7 Other Relevant Remarks

* 