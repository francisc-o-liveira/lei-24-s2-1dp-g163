# US023 - To Assign a Team to an entry in the Agenda

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt 

![Domain Model](svg/us023-domain-model.svg)

- **Entry**: Represents each task or activity scheduled in the agenda. It includes essential details for its management and execution:
  - `reference`: A unique identifier for the entry.
  - `status`: The current state of the entry (e.g., planned, completed).
  - `startDate`: The date when the entry is scheduled to begin.
  - `vehicleList`: A list of vehicles assigned to the entry.
  - `finishDate`: The date when the entry is expected to be completed.

- **GSM**: The Green Space Manager responsible for managing entries and teams. Key attributes include:
  - `email`: The GSM's email address for communication and notifications.
  - `password`: The GSM's password for system access.

- **Team**: Represents a group of collaborators assigned to tasks. Important attributes include:
  - `name`: The name of the team.
  - `maxSize`: The maximum number of members in the team.
  - `minSize`: The minimum number of members in the team.
  - `skillsSelected`: The skills required for the team members.

- **Collaborator**: Represents an individual team member. Key attributes include:
  - `name`: The name of the collaborator.
  - `email`: The email address of the collaborator for communication and notifications.
  - `birthdate`: The birthdate of the collaborator.
  - `salary`: The salary of the collaborator.

- **EmailService**: The service responsible for sending email notifications to team members when an entry is assigned.

- **Agenda**: Represents the agenda that contains multiple entries.

### 2.2 Associations:

- **manages**: This association indicates that the GSM is responsible for managing one or more entries. Each GSM can manage multiple entries.

- **has assigned**: This association shows that an entry can have a team assigned to it. An entry can have 0 or 1 team assigned, and each team can be assigned to multiple entries.

- **includes**: This association shows that a team consists of multiple collaborators.

- **receives assignment**: This association shows that the EmailService receives notifications when entries are assigned.

- **notifies**: This association indicates that the EmailService notifies the team members about the assignment of an entry.

- **contains**: This association shows that an Agenda contains multiple entries.
