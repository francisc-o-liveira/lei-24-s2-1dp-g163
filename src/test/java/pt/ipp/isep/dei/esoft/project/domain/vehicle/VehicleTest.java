package pt.ipp.isep.dei.esoft.project.domain.vehicle;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void isCloseToCheck() {
        Vehicle v = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(2005, 10, 1), new Date(2010, 12, 3), 10000, "15-RD-LR", new Date(2010, 10, 1), 140000);
        assertTrue(v.isCloseToCheck());
    }

    @Test
    void registerCheckUp() {
        Vehicle v = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(2005, 10, 1), new Date(2010, 12, 3), 10000, "15-RD-LR", new Date(2010, 10, 1), 100000);
        Optional<CheckUp> operation = v.registerCheckUp(155555, new Date(2024, 4, 29), 10000);
        assertTrue(operation.isPresent());
    }

    //AC2- Vehicle cannot be the same
    @Test
    void verifyEquals() {
        Vehicle v1 = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(2005, 10, 1), new Date(2010, 12, 3), 10000, "15-RD-LR", new Date(2010, 10, 1), 140000);
        Vehicle v2 = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(2005, 10, 1), new Date(2010, 12, 3), 10000, "15-RD-LR", new Date(2010, 10, 1), 140000);
        assertTrue(v1.equals(v2));

    }

    //AC1- US7- Verify if plate is in the corrrect format
    @Test
    void verifyPlate3792() {
        Exception exception=assertThrows(RejectedExecutionException.class, () ->{
            new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(1937, 10, 1), new Date(2010, 12, 3), 10000, "AB1212", new Date(2010, 10, 1), 140000);
            });
    }
    @Test
    void verifyPlate9304() {
        Exception exception=assertThrows(RejectedExecutionException.class, () ->{
            new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(1993, 10, 1), new Date(2010, 12, 3), 10000, "1212AB", new Date(2010, 10, 1), 140000);
        });
    }
    @Test
    void verifyPlate0519() {
        Exception exception=assertThrows(RejectedExecutionException.class, () ->{
            new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(2005, 10, 1), new Date(2010, 12, 3), 10000, "12AB12", new Date(2010, 10, 1), 140000);
        });
    }
    @Test
    void verifyPlate2024() {
        Exception exception=assertThrows(RejectedExecutionException.class, () ->{
            new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(2020, 10, 1), new Date(2010, 12, 3), 10000, "AB12AB", new Date(2010, 10, 1), 140000);
        });
    }

    @Test
    //AC2- US8 - Vehicles that need a checkup have a difference minor than 5%
    void verifyCloseToCheck(){
        Vehicle v = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 149700, new Date(2005, 10, 1), new Date(2010, 12, 3), 10000, "15-RD-LR", new Date(2010, 10, 1), 140000);
        assertTrue(v.isCloseToCheck());
    }
}