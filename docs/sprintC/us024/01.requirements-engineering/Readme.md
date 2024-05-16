# US024 - Postpone an entry in the Agenda to a future date


## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I want to Postpone an entry in the Agenda to a future date.

### 1.2. Customer Specifications and Clarifications

**From the specifications document:**

>  The management of green areas for public use requires the timely management and completion of multiple tasks throughout the year. In the daily management, the GSM uses two essential tools: the Agenda and the Task List (aka To-Do List). The Agenda is made up of entries that relate to a task (which was previously in the To-Do List), the team that will carry out the task, the vehicles/equipment assigned to the task, expected duration, and the status (Planned, Postponed, Canceled, Done).

**From the client clarifications:**

> **Question:**
>
> **Answer:**

### 1.3. Acceptance Criteria

* **AC1:**
* **AC2:**

### 1.4. Found out Dependencies

* There is a dependency on "US022 - As a GSM,I want to add a new entry in the Agenda" since it is necessary to first register a new entry in the calendar to be able to postpone it to a future date.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
  * future date


* Selected data:
  * Agenda entry

**Output Data:**
* **Confirmation of Postpone:**
  - A success notification confirming the future date.
* **Warnings or Errors (if applicable):**
  - Error messages for any issues encountered when postponing an Agenda entry.
* **Operational Feedback:**
  - Overall status of the operation (success or failure), with immediate feedback to the GSM.


### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us024-system-sequence-diagram-alternative-one-System_Sequence_Diagram__SSD____Alternative_One.svg)

### 1.7 Other Relevant Remarks
N/A