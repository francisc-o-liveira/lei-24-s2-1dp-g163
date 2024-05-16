# US025 - Cancel an entry in the Agenda


## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I want to Cancel an entry in the Agenda.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

**From the client clarifications:**


### 1.3. Acceptance Criteria

* **AC1:** A canceled entry should not be deleted but rather change its state

### 1.4. Found out Dependencies

* There is a dependency on "US002 - I want to register a job" as there must be at least one job category to classify the collaborator being register.

### 1.5 Input and Output Data

**Input Data:**

* Selected data:
    * entry

**Output Data:**

* **Confirmation of canceled entry in the Agenda:**
  - A success notification confirming that the entry is canceled.


### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us025-system-sequence-diagram-alternative-one.svg)

### 1.7 Other Relevant Remarks