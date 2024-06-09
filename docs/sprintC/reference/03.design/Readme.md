# Reference

## 3. Design - User Story Realization

### 3.1. Rationale

| SD Interaction ID | Question: Which class is responsible for... | Answer | Justification (with patterns) |
|-------------------|---------------------------------------------|--------|------------------------------|
| 1: Asks to get the details and management options of a specific entry on the agenda | asking to get the details and management options of a specific entry on the agenda? | ManageAgendaUI | **Pure Fabrication**: The `ManageAgendaUI` handles the request from the GSM to get the details and management options. This ensures that the UI manages the interaction with the user, keeping the logic separate. |
| 1 | delegating the request to get agenda details? | AssignEntryOnAgendaController | **Controller**: The `AssignEntryOnAgendaController` coordinates the request for agenda details, following the Controller pattern to delegate to the appropriate handler. |
| 1 | fetching the agenda list from the entry repository? | EntryRepository | **Information Expert**: The `EntryRepository` is responsible for providing the agenda list because it holds the necessary data. |
| 1 | retrieving the list of entries from the agenda? | AgendaList | **Information Expert**: The `AgendaList` knows the details of the entries it contains and can provide them. |
| 1 | converting the list of entries to DTOs? | EntryMapper | **Pure Fabrication**: The `EntryMapper` converts the list of entries to DTOs, keeping the data transformation separate from the domain logic. |
| 2: Shows Agenda Entries | displaying the agenda entries to the user? | ManageAgendaUI | **Pure Fabrication**: The UI displays the retrieved data to the user, managing the interaction independently of the business logic. |
| 3: Selects an Entry | selecting an entry from the agenda? | ManageAgendaUI | **Pure Fabrication**: The UI handles the user's selection, keeping the interaction logic separate from business logic. |
| 3 | setting labels with the selected entry's DTO? | ViewDetailsEntryAgendaUI | **Pure Fabrication**: This UI component handles the presentation logic for the selected entry, ensuring a clear separation of responsibilities. |
| 4: Displays entry details and management options | displaying entry details and management options? | ViewDetailsEntryAgendaUI | **Pure Fabrication**: The UI component is responsible for displaying the entry details and management options, not the controller. |

### Systematization

**Software classes (i.e. Pure Fabrication) identified**

* ManageAgendaUI
* EntryMapper
* ViewDetailsEntryAgendaUI

**Other software classes (i.e. Controller) identified**

* AssignEntryOnAgendaController

**Other software classes (i.e. Information Expert) identified**

* EntryRepository
* AgendaList


## 3.2. Sequence Diagram (SD)

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/ref_get_details_and_management_options_of_a_selected_entry_on_the_agenda.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/ref-class-diagram.svg)