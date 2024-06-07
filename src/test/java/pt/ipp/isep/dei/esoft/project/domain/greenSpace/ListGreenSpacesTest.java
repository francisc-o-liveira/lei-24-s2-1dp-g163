package pt.ipp.isep.dei.esoft.project.domain.greenSpace;

import org.junit.jupiter.api.BeforeEach;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.ui.Bootstrap;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ListGreenSpacesTest {

    private Organization org;
    private String email;

    @BeforeEach
    void setupAll() {
        org = new Organization();
        email = "gsm@this.app";
    }

    @Test
    void listSpacesTest(){
        Bootstrap boot= new Bootstrap();
        boot.run();
        List<GreenSpace> list = org.getGreenSpaceListByManagerEmail(email);
        assertNotNull(list);
    }
}
