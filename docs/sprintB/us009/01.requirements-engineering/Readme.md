# US006 - Register a Vehicle 

## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I want to know the exact costs referring to water consumption of specific green space so that I may manage these expenses efficiently

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	Therefore, within this US, the aim is to carry out a
statistical analysis concerning the water consumption costs in all parks.
The ”WaterUsed.csv” file provide the necessary data to carry out the
study. This file records daily water consumption (in m3) since the day
each park opened. The amount paid for water is 0.7 AC/m3, up to a consumption of 50 m3 , with a fee of 15% added for higher consumption levels.

> The data file contains records of the following information: ”Park Identification”, ”Year”, ”Month”, ”Day”, ”Consumption”. Consider this
data in order to obtain the following outcomes

> 

**From the client clarifications:**

> **Question:** Should the application identify a registered vehicle by a serial number or other attribute?
>
> **Answer:** By plate id.

> **Question:** Should the application a group the vehicles by their brand, serial number or other attribute?
>
> **Answer:**  No requirements were set concerning groups of vehicles;

> **Question:** For the application to work does the FM need to fill all the attributes of the vehicle?
>
> **Answer:** Yes, besides the vehicle plate

### 1.3. Acceptance Criteria

* **AC1:** The barplot need to have the park identification and start month and end month.
* **AC2:** When creating a vehicle with an existing reference, the system must reject such operation and the user must be able to modify it.
* **AC3:** When introducing a plate, the system must ask first the register date, so that can change the format of the plate.

### 1.4. Found out Dependencies

N/A

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * brand
    * model 
    * type
    * tare
    * gross weight
    * current km
    * register date 
    * acquisition date
    * maintenance/check-up frequency (in kms)
	
* Selected data:
    * vehicle category

**Output Data:**

* Display confirmation success

### 1.6. System Sequence Diagram (SSD)
#### Alternative One

![System Sequence Diagram - Alternative One](../01.requirements-engineering/svg/us006-system-sequence-diagram-alternative-one-System_Sequence_Diagram__SSD____Alternative_One.svg)

#### Alternative Two

![System Sequence Diagram - Alternative Two](../01.requirements-engineering/svg/us006-system-sequence-diagram-alternative-two-System_Sequence_Diagram__SSD____Alternative_Two.svg)

### 1.7 Other Relevant Remarks

N/A