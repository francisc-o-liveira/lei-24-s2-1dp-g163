# US024 - Register skills for collaborators

## 3. Design - User Story Realization 

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

 | SD Interaction ID                                   | Question: Which class is responsible for...                         | Answer                     | Justification (with patterns)                                                                                                                                                  |
|-----------------------------------------------------|---------------------------------------------------------------------|----------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1: Asks to Postpone an Entry                        | handling the user's request to postpone an entry?                   | ViewDetailsEntryAgendaUI   | **Pure Fabrication**: The `ViewDetailsEntryAgendaUI` manages user interaction to keep the UI logic separate from the business logic, ensuring high cohesion and low coupling.  |
|                                                     | delegating the request to get possible teams for the entry?         | ViewDetailsEntryController | **Controller**: The `ViewDetailsEntryController` coordinates the process, delegating the request to appropriate handlers, ensuring separation of concerns and central control. |
|                                                     | fetching the agenda list from the entry repository?                 | EntryRepository            | **Information Expert**: The `EntryRepository` holds the agenda data and is responsible for providing it.                                                                       |
|                                                     | fetching the list of teams from the team repository?                | TeamRepository             | **Information Expert**: The `TeamRepository` holds the team data and is responsible for providing it.                                                                          |
|                                                     | filtering the team list based on the entry time?                    | AgendaList                 | **Information Expert**: The `AgendaList` knows the details and logic for filtering teams based on their activation times.                                                      |
|                                                     | converting the filtered team list to a list of DTOs?                | TeamMapper                 | **Pure Fabrication**: The `TeamMapper` converts team entities to DTOs, separating transformation logic from business logic.                                                    |
| 2: Ask to introduce a new date and a new start hour |                                                                     | AssignTeamUI               | **Pure Fabrication**: The `AssignTeamUI` presents the list of possible teams to the user, maintaining separation of concerns.                                                  |
| 3: introduces requested data                        | handling the user's selection of a team and assignment to an entry? | AssignTeamUI               | **Pure Fabrication**: The `AssignTeamUI` manages user interaction for team selection and assignment, ensuring UI responsibilities are distinct from business logic.            |
| 3                                                   | delegating the request to assign the selected team to the entry?    | ViewDetailsEntryController | **Controller**: The `ViewDetailsEntryController` manages the assignment process, ensuring central control and coordination.                                                    |
| 3                                                   | delegating the task to update the entry in the repository?          | EntryRepository            | **Information Expert**: The `EntryRepository` manages data persistence and is responsible for updating the entry with the assigned team.                                       |
| 3                                                   | converting the entry DTO to an entry entity?                        | EntryMapper                | **Pure Fabrication**: The `EntryMapper` handles the transformation of entry DTOs to domain entities, ensuring separation of concerns.                                          |
| 3                                                   | updating the team and vehicles in the entry?                        | EntryMapper                | **Pure Fabrication**: The `EntryMapper` updates the entry based on the provided DTO, applying domain logic appropriately.                                                      |
| 3                                                   | preparing the email content for sending?                            | EmailService               | **Pure Fabrication**: The `EmailService` prepares and sends the email content, managing the email-sending logic.                                                               |
| 3                                                   | sending the email using an external service?                        | SomeEmailService           | **Information Expert**: The `SomeEmailService` implements the external email sending functionality, handling the necessary details for email transmission.                     |
| 4: Confirms the Change                              | confirms the change                                                 | PostponeUI                 | **Pure Fabrication**: The `PostpondemUI` presents feedback to the user, maintaining separation of concerns between UI and business logic.                                      |
                                                                                                                                                                             |                                                                                                               |              

### Systematization ##

Software classes (i.e. **Pure Fabrication**) identified

* ViewDetailsEntryAgendaUI
* PostponeUI
* EntryMapper


Other software classes (i.e. **Controller**) identified

* ViewDetailsEntryController

Other software classes (i.e. **Information Expert**) identified

* EntryRepository
* Entry



## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us024-sequence-diagram-full.svg)

### Split Diagram
**Get details and management options of a selected entry on the agenda**

![Reference Domain Model](../../reference/03.design/svg/ref_get_details_and_management_options_of_a_selected_entry_on_the_agenda.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us024-class-diagram.svg)