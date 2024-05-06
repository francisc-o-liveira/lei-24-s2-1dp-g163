package pt.ipp.isep.dei.esoft.project.domain.vehicle;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.Optional;

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
    void verifyEquals() {
        Vehicle v1 = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(2005, 10, 1), new Date(2010, 12, 3), 10000, "15-RD-LR", new Date(2010, 10, 1), 140000);
        Vehicle v2 = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(2005, 10, 1), new Date(2010, 12, 3), 10000, "15-RD-LR", new Date(2010, 10, 1), 140000);
        assertTrue(v1.equals(v2));

    }


    //AC1- US7- Verify if plate is in the corrrect format
    void verifyPlate3792T() {//method is in domain vehicle
        //verify true(1937-1992)
        Vehicle v3792 = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(1937, 10, 1), new Date(2010, 12, 3), 10000, "AB-12-12", new Date(2010, 10, 1), 140000);
        assertTrue(v3792.verifyPlate("AB-12-12"));
    }

    void verifyPlate3792F() {//method is in domain vehicle
        //verify false(1937-1992)
        Vehicle v3693 = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(1937, 10, 1), new Date(2010, 12, 3), 10000, "AB1212", new Date(2010, 10, 1), 140000);
        assertFalse(v3693.verifyPlate("AB1212"));
    }

    void verifyPlate9304T() {//method is in domain vehicle
        //verify true(93-04)
        Vehicle v9304 = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(1993, 10, 1), new Date(2010, 12, 3), 10000, "12-12-AB", new Date(2010, 10, 1), 140000);
        assertTrue(v9304.verifyPlate("12-12-AB"));
    }

    void verifyPlate9304F() {//method is in domain vehicle
        //verify false(1936-1993)
        //valid plate
        Vehicle v9304 = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(1993, 10, 1), new Date(2010, 12, 3), 10000, "1212AB", new Date(2010, 10, 1), 140000);
        assertFalse(v9304.verifyPlate("1212AB"));
    }

    void verifyPlate0519T() {
        //verify true(2005-2019)
        Vehicle v0519 = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(2005, 10, 1), new Date(2010, 12, 3), 10000, "12-AB-12", new Date(2010, 10, 1), 140000);
        assertTrue(v0519.verifyPlate("12-AB-12"));
    }

    void verifyPlate0519F() {
        //verify true(2005-2019)
        Vehicle v0519 = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(2005, 10, 1), new Date(2010, 12, 3), 10000, "12AB12", new Date(2010, 10, 1), 140000);
        assertTrue(v0519.verifyPlate("12AB12"));
    }

    void verifyPlate2024T() {
        //verify true(2020-2024)
        Vehicle v0519 = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(2020, 10, 1), new Date(2021, 12, 3), 10000, "12-AB-12", new Date(2021, 10, 1), 140000);
        assertTrue(v0519.verifyPlate("12-AB-12"));
    }

    void verifyPlate2024F() {
        //verify true(2020-2024)
        Vehicle v0519 = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 150000, new Date(2020, 10, 1), new Date(2021, 12, 3), 10000, "AB12AB", new Date(2021, 10, 1), 140000);
        assertTrue(v0519.verifyPlate("12AB12"));
    }


    //AC2- US8 - Vehicles that need a checkup have a difference minor than 5%
    void verifyCloseToCheck(){
        Vehicle v = new Vehicle("VW", "Golf", Vehicle.Type.LightCargo, 1200, 1500, 149700, new Date(2005, 10, 1), new Date(2010, 12, 3), 10000, "15-RD-LR", new Date(2010, 10, 1), 140000);
        assertTrue(v.isCloseToCheck());
    }
    //
}