package pt.ipp.isep.dei.esoft.project.application.controller;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;

import static org.junit.jupiter.api.Assertions.*;

class RegisterCollaboratorControllerTest {

    @Test
    void validateDocTypePassport() {
        RegisterCollaboratorController controller = new RegisterCollaboratorController();
        assertTrue(controller.validateDocType(DocType.Type.Passport,1231321223));
    }

    @Test
    void validateDocTypeCitizenCard() {
        RegisterCollaboratorController controller = new RegisterCollaboratorController();
        assertTrue(controller.validateDocType(DocType.Type.CitizenCard,1231321279));
    }

    @Test
    void validateDocTypeTaxPayerCard() {
        RegisterCollaboratorController controller = new RegisterCollaboratorController();
        assertTrue(controller.validateDocType(DocType.Type.TaxPayerCard,1239991223));
    }

}