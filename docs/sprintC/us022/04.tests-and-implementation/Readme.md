# US003 - Register a Collaborator

## 4. Tests 

**Test 1:** Check that it is not possible to create an instance of Collaborator with bad DocIdNumber. - AC2

	@Test(expected = IllegalArgumentException.class)

    void verifyDocTypeIDNumber(){
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    new Collaborator("Joaquim Manel Mendes Cunha Manuel Silva Oliveira",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,-1972453213,new JobCategory("Gardener"));
    });
    }
    
	

**Test 2:** Check that it is not possible to create an instance of Collaborator with a Bad name- AC3. 

	@Test(expected = IllegalArgumentException.class)
		void verifyIfCollaboratorNameCanHaveMoreThan6Words(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Joaquim Manel Mendes Cunha Manuel Silva Oliveira",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
        });
    }

**Test 3:** Check that it is not possible to create an instance of Collaborator with less than 18 years old and possible with more than 18 years old. - AC4

	@Test(expected = IllegalArgumentException.class)
		void verifyIfCollaboratorCanHaveLessThan18years(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Joaquim Mendes Manuel Silva Oliveira",new Date(2006,5,1), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
        });
        Collaborator test = new Collaborator("Joaquim Mendes Manuel Silva Oliveira",new Date(2006,4,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
    }

**Test 4:** Check that it is not possible to create an instance of Collaborator with a Bad Email Address - AC5

	@Test(expected = IllegalArgumentException.class)
		void verifyIfCollaboratorCanHaveBadEmail(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Joaquim Mendes Manuel Silva Oliveira",new Date(2001,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","916835384","@gmail", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
        });
    }

**Test 5:** Check that it is not possible to create an instance of Collaborator with a Bad Phone Number - AC6

	@Test(expected = IllegalArgumentException.class)
		void verifyBadPhoneNumber(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Joaquim Mendes Manuel Silva Oliveira",new Date(2001,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+3519168384","joaquim@gmail.com", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
        });
    }

**Test 6:** Check that it is not possible to create an instance of Collaborator with less than Minimum data needed - AC7

	    @Test
        void verifyCreateCollaboratorMinimumCriteria(){
        Collaborator c = new Collaborator("Joaquim Mendes Manuel Silva Oliveira",new Date(2001,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351 916835384","joaquim@gmail.com", DocType.Type.CitizenCard,1972321313,new JobCategory("Gardener"));
        assertNotNull(c);
        }

**Test 7:** Check that it is not possible to create an instance of Collaborator with the same DocIdNumber of other Collaborator (Duplicate Test Not Possible) - AC1

    @Test(expected = CloneNotSupportedException.class)
     void verifyIfCollaboratorExists() { // verify if exist and if not exist !
        CollaboratorRepository repo = Repositories.getInstance().getCollaboratorRepository();
        Collaborator cTest = new Collaborator("Joaquim",new Date(2002,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","jouim.cunha@gmail.com", DocType.Type.CitizenCard,863473624,new JobCategory("Gardener"));
        Collaborator cTestEqual = new Collaborator("Joaquim",new Date(2002,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916835384","jouim.cunha@gmail.com", DocType.Type.CitizenCard,863473624,new JobCategory("Gardener"));

        Collaborator cTest2 = new Collaborator("Joaquim",new Date(2005,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351916323234","joaquim.cunha@gmail.com", DocType.Type.CitizenCard,743626422,new JobCategory("Garder"));
        Collaborator cTest3 = new Collaborator("Joaquim",new Date(2003,10,29), new Date(2024,04,29),"Rua Das Rosas","4630-131","Marco de Canaveses","+351926835384","joaquim.cuha@gmail.com", DocType.Type.CitizenCard,376432422,new JobCategory("Gardener"));
        repo.addCollaborator(cTest);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            repo.addCollaborator(cTestEqual);
        });
    }



_It is also recommended to organize this content by subsections._ 


## 5. Construction (Implementation)

### Class RegisterCollaboratorController 

```java
public Optional<Collaborator> registerCollaborator(String name, Date birthday, Date admissionDate, String address, String addressCity, String addressZipCode, String phoneNumber, String email, Type docType, int docIDNumber, JobCategory jobCategory) throws CloneNotSupportedException {
    Optional<Collaborator> newCollab = collaboratorRepository.createCollaborator(name, birthday, admissionDate, address, addressCity, addressZipCode, email, phoneNumber, docType, docIDNumber, jobCategory);
    return newCollab;
}
```

### Class CollaboratorRepository

```java
public Optional<Collaborator> createCollaborator(String name, Date birthday, Date admissionDate, String address, String addressZipCode, String addressCity, String email, String phoneNumber, DocType.Type docType, int docIDNumber, JobCategory jobCategory) throws CloneNotSupportedException {
    Optional<Collaborator> newCollab = Optional.empty();
    Collaborator collab = new Collaborator(name,birthday,admissionDate,address,addressZipCode,addressCity,phoneNumber,email,docType,docIDNumber,jobCategory);
    newCollab = verifyCollaboratorExistAndSave(collab);
    return newCollab;
}
```


## 6. Integration and Demo 

* A new option on the Collaborator menu options was added.

* For demo purposes some Collaborators are bootstrapped while system starts.


## 7. Observations

* With the implementation, as the team was ahead of schedule, it was decided that due to JavaFX, exceptions were handled in the UI, so "try{}catch(){}" started to be carried out in the UI at console level as well, as it was not possible to have two ways of catch the exceptions for the two different UI types.

* Some verifications simples are maded in UI, because are simple verifications.