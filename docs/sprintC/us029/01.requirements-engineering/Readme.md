# US029 - Record the completion of a task. 


## 1. Requirements Engineering

### 1.1. User Story Description

As a Collaborator, I want to record the completion of a task.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**
>  The Agenda is made up of entries that relate to a task (which was previously in the To-Do List), the team that will carry out the task, the vehicles/equipment assigned to the task, expected duration, and the status (Planned, Postponed, Canceled, Done).

>  Collaborator – a person who is an manager in the organization and carries out design, construction and/or maintenance tasks for green areas, depending on their skills.

>  Tasks are carried out on an occasional or regular basis, in one or more green spaces, for example: tree pruning, installation of an irrigation system, and installation of a lighting system.
Teams are temporary associations of employees who will carry out a determined set of tasks in one or more green spaces. When creating multipurpose teams, the number of members and the set of skills that must be covered are crucial.

**From the client clarifications:**
> **Question** The collaborator can see what type of entrys? Like what status can he filter ? Can he see canceled Entry's?
> 
> **Answer** The ones assigned to him. He can filter by the different values the status of the status, like planned, executed, canceled ...

### 1.3  Found out Dependencies

* There is a dependency with "US28 - View Tasks Assigned to Collaborator" and "US05 - Generate a Team" as to record the completion of a task, the task must be assigned to Collaborator and the Collaborator must be on a team.
* 

### 1.4 Input and Output Data

* Selected data:
    * task

**Output Data:**

* **Confirmation of Register Collaborator:**
  - A success notification confirming that the collaborator has completed record of a task.

### 1.5. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us003-system-sequence-diagram-alternative-one.svg)

### 1.6 Other Relevant Remarks