package pt.ipp.isep.dei.esoft.project.domain.greenSpace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.core.application.mapper.GreenSpaceMapper;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Organization;
import pt.ipp.isep.dei.esoft.project.utilities.Address;

import java.util.Optional;

class RegisterGreenSpaceTest{

private Organization org;

private GreenSpaceMapper mapper;
private String name;
private String address;
private String city;
private String zipCode;
private double area;

private String email;
private GreenSpace.Type type;

    @BeforeEach
     void setupAll() {
        org = new Organization();
        mapper = new GreenSpaceMapper();
         name = "isep";
         address = "Rua Sao Tome";
         city = "Porto";
         zipCode ="4400-123";
         area = 20.0;
         type = GreenSpace.Type.Garden;
         email = "gsm@this.app";
    }

    @Test
    void testGreenSpaceRegistery(){
        GreenSpace gs = new GreenSpace(area,name,type,email,new Address(zipCode,address,city),null);
        GreenSpaceDto gsDto = mapper.greenSpaceToGreenSpaceDto(gs);
        Optional<GreenSpace> gsOpt = org.registerGreenSpace(gsDto);
        assertNotNull(gsOpt.get());
        assertEquals(gsOpt.get().getName(),name);
        assertEquals(gsOpt.get().getArea(),area);
        assertEquals(gsOpt.get().getAddress().getCity(),city);
        assertEquals(gsOpt.get().getAddress().getStreet(),address);
        assertEquals(gsOpt.get().getAddress().getZipCode(),zipCode);
        assertEquals(gsOpt.get().getType(), type);
        assertEquals(gsOpt.get().getEmail(), email);
    }

}