# US026 - Assign one or more Vehicles to an entry in the Agenda

## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I want to assign one or more vehicles to an entry in the Agenda.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>  The Agenda is made up of entries that relate to a task (which was previously in the To-Do List),the team that will carry out the task, the vehicles/equipment assigned to the task, expected duration, and the status (Planned, Postponed, Canceled, Done).

>	The Agenda is a crucial mechanism for planning the week’s work. Each entry in the Agenda defines a task (that was previously included in the to-do list). A team will carry out that task in a green space at a certain time interval on a specific date. Comparatively analyzing the Agenda entries and the pending tasks (to-do list) allows you to evaluate the work still to be done, the busyness of the week, and the work performed by a team in a green space at a determined time interval and on a specific date.


**From the client clarifications:**

> **Question:** The GSM can only assign Vehicles without an Entry in the same time ?
>
> **Answer:**  

> **Question:**  How many vehicles can have an Entry on maximum value?
>
> **Answer:** 

> **Question:** 
>
> **Answer:** 




### 1.3. Acceptance Criteria

* **AC1:** 
* **AC2:**

### 1.4. Found out Dependencies

* There is a dependency on "US022 - As a GSM, I want to add a new entry in the Agenda" is needed to have an entry in the Agenda

### 1.5 Input and Output Data

**Input Data:**

* Selected data:
  * Entry
  * Vehicle's

**Output Data:**

* **Confirmation of Assign:**
  - A success notification confirming that the vehicle have been successfully assigned to the Entry.
* **Warnings or Errors (if applicable):**
  - Error messages for any issues encountered during the assign vehicle process, such non-existent data or duplications ,etc...
* **Operational Feedback:**
  - Overall status of the operation (success or failure), with immediate feedback to the GSM.


### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us026-system-sequence-diagram-alternative-one.svg)


### 1.7 Other Relevant Remarks

* The Collaborator Email need to be unique, because to find the Collaborator in the system is needed to search by email (unique by Collaborator).