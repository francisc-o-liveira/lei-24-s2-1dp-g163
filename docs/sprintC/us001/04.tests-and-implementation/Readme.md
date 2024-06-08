# US001 - Register a Skill 

## 4. Tests 

**Test 1:** Check that is not possible to create an instance of skill with a special digits.- AC1

	 @Test
    void verifySkillNameDontCanHaveSpecialCharactersOrNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Skill("2313@SkillTest");
        });
    }

**Test 2:**  Check that is not possible to create an instance of skill with a null skill name.- AC3 

	@Test
    void verifySkillNameNullPointer() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Skill("");
        });
    }

**Test 3:**  Check that is not possible to create an instance of skill in which a skill with the same name already exists.

	@Test
    void registerSkill() {
        SkillRepository skillRepository = new SkillRepository();
        try {
            skillRepository.registerSkill("Java");
        } catch (CloneNotSupportedException e) {
            System.out.println(e.getMessage());
        }
        Exception exception = assertThrows(CloneNotSupportedException.class, () -> {
            skillRepository.registerSkill("Java");
        });
    }

    void validateGetSkillListMethod(){
        SkillRepository skillRepository = new SkillRepository();
        Skill skill1 = new Skill("Java");
        Skill skill2 = new Skill("Python");
        Skill skill3 = new Skill("PHP");
        Skill skill4 = new Skill("JavaScript");
        Skill skill5 = new Skill("C");


        try {
            skillRepository.registerSkill("Java");
            skillRepository.registerSkill("Python");
            skillRepository.registerSkill("PHP");
            skillRepository.registerSkill("JavaScript");
            skillRepository.registerSkill("C");
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }


        List<Skill> expectResult = new ArrayList<>();
        expectResult.add(skill1);
        expectResult.add(skill2);
        expectResult.add(skill3);
        expectResult.add(skill4);
        expectResult.add(skill5);


        List<Skill> result = skillRepository.getSkillList();


        assertEquals(expectResult,result);
    }





## 5. Construction (Implementation)

### Class RegisterSkillController

```java
package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.core.application.repository.SkillRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/** Controller to Register a Skill */
public class RegisterSkillController {


    private SkillRepository skillRepository;

    public boolean RegisterSkill(String skillName) throws CloneNotSupportedException {
        Optional<Skill> newSkill = Repositories.getInstance().getSkillRepository().registerSkill(skillName);
        if (newSkill.isPresent()) {
            return true;
        }
        return false;
    }

    public List<Skill> getSkillList() {
        return Repositories.getInstance().getSkillRepository().getSkillList();
    }

    public void removeFromList(Skill skillName) {
        Repositories.getInstance().getSkillRepository().getSkillList().remove(skillName);
    }

    public ArrayList<DocType.Type> getDocTypeList() {
        return new ArrayList<>(Arrays.asList(DocType.Type.values()));
    }

    public boolean loadSkillsByFile(String fileName) throws FileNotFoundException, CloneNotSupportedException {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        boolean operationSucess = false;
        boolean operations = true;
        if (!scan.hasNextLine()) {
            return false;
        }
        while (scan.hasNextLine()) {
            String skillName = scan.nextLine();
            operationSucess = RegisterSkill(skillName);
            if (!operationSucess) {
                operations = false;
            }
        }
        return operations;

    }
}

```

### Class SkillRepository

```java
package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Repository for Skills */
public class SkillRepository {
    /** List of Skills */
    private List<Skill> skillList;
    /** Initialize the list of Skills */
    public SkillRepository(){
        skillList = new ArrayList<>();
    }

    /** Method to register a Skill
     *
     * @param skillName - skill to be registered
     * @return Optional of Skill if Skill has been registered
     * @throws CloneNotSupportedException if Skill already exists
     */
    public Optional<Skill> registerSkill(String skillName) throws CloneNotSupportedException {
        Optional<Skill> optionalValue = Optional.empty();
        Skill skill = new Skill(skillName);
        if (verifyIfExistAndSave(skill)) {
            optionalValue = Optional.of(skill);
        }
        return optionalValue;

    }

    /** Verifies if Skill already exists and saves it
     *
     * @param skill to be verified
     * @return true if Skill did not exist
     * @throws CloneNotSupportedException if Skill already existed
     */
    private boolean verifyIfExistAndSave(Skill skill) throws CloneNotSupportedException {
        boolean operationSuccess = false;
        if (validateSkill(skill)) {
            operationSuccess = skillList.add(skill);
        }else {
            throw new CloneNotSupportedException("This Skill already exists");
        }
        return operationSuccess;
    }

    /** The method gets the List of Skills
     *
     * @return List of Skills
     */
    public List<Skill> getSkillList(){
        return List.copyOf(skillList);
    }

    /** Checks if Skill already exists in the list
     *
     * @param skill to be checked
     * @return true if Skill does not exist on the list
     */
    private boolean validateSkill(Skill skill) {
        boolean isValid = !skillList.contains(skill);
        return isValid;
    }
}
```


## 6. Integration and Demo
* For demo purposes some skills are bootstrapped while system starts.


## 7. Observations
* With the implementation, as the team was ahead of schedule, it was decided that due to JavaFX, exceptions were handled in the UI, so "try{}catch(){}" started to be carried out in the UI at console level as well, as it was not possible to have two ways of catch the exceptions for the two different UI types.

* Some verifications simples are maded in UI, because are simple verifications.