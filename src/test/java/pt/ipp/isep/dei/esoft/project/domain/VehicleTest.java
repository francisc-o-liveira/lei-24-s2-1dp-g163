package pt.ipp.isep.dei.esoft.project.domain.vehicle;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void isCloseToCheck() {
        Vehicle v = new Vehicle("VW","Golf", Vehicle.Type.LigeiroPassageiros,1200,1500,150000,new Date(2005,10,1),new Date(2010,12,3),10000,"15-RD-LR",new Date(2010,10,1),140000);
        assertTrue(v.isCloseToCheck());
    }

    @Test
    void registerCheckUp() {
        Vehicle v = new Vehicle("VW","Golf", Vehicle.Type.LigeiroPassageiros,1200,1500,150000,new Date(2005,10,1),new Date(2010,12,3),10000,"15-RD-LR",new Date(2010,10,1),100000);
        Optional<CheckUp> operation =v.registerCheckUp(155555,new Date(2024,4,29));
        assertTrue(operation.isPresent());
    }

    @Test
    void registerVehicle(){

    }
}