package dungeonmania.logicSwitches;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class LogicSwitchesTest {
    @Test
    @Tag("21-1")
    @DisplayName("Complex path wire system should turn on lights")
    public void testWirePath() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_ComplexPath", "c_LogicSwitchesTest");

        // lights start off off
        assertEquals(5, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on switch
        res = dmc.tick(Direction.RIGHT);
        // all lights turned on, indicating wire system is all has current
        assertEquals(5, TestUtils.getEntities(res, "light_bulb_on").size());
        assertEquals(0, TestUtils.getEntities(res, "light_bulb_off").size());
    }

    @Test
    @Tag("21-2")
    @DisplayName("2 switches Complex path wire system should turn on")
    public void test2Switches() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_2Switches", "c_LogicSwitchesTest");

        // lights start off off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on switch
        res = dmc.tick(Direction.RIGHT);
        // all lights turned on
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // activate 2nd switch, all lights should stay on
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());

        // turn off 1st switch, all lights should stay on
        res = dmc.tick(Direction.UP);
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());
    }

    @Test
    @Tag("21-3")
    @DisplayName("OR Light Bulb turns on when 1 or more wire adjacent is on")
    public void testOrLight() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_OrLight", "c_LogicSwitchesTest");
        // lights start off off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on switch
        res = dmc.tick(Direction.RIGHT);
        // all lights turned on
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // activate 2nd switch, all lights should stay on
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());

        // turn off 1st switch, all lights should stay on
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());
    }

    @Test
    @Tag("21-4")
    @DisplayName("AND light bulb turns on when only 2 wires adjacent")
    public void testAnd2Wires() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_And2Wire", "c_LogicSwitchesTest");

        // lights start off off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on switch
        res = dmc.tick(Direction.RIGHT);
        // light off still
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // activate 2nd switch, light turns on
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());

        // turn off 1st switch, light should turn off
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());
    }

    @Test
    @Tag("21-5")
    @DisplayName("AND light bulb does not turn on when 3 wires adjacent, 2 are on")
    public void testAnd3Wires() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_And3Wire", "c_LogicSwitchesTest");

        // lights start off off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on switch
        res = dmc.tick(Direction.RIGHT);
        // light off still
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // activate 2nd switch, light still off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());
    }

    @Test
    @Tag("21-6")
    @DisplayName("AND light bulb does not turn on when only 1 adjacent wire ")
    public void testAnd1Wire() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_And1wire", "c_LogicSwitchesTest");

        // lights start off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on switch
        res = dmc.tick(Direction.RIGHT);
        // all lights not on since And
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());
    }

    @Test
    @Tag("21-7")
    @DisplayName("XOR light bulb turns on when only 1 wire adjacent and is on")
    public void testXor1Wire() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_Xor1wire", "c_LogicSwitchesTest");

        // lights start off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on switch
        res = dmc.tick(Direction.RIGHT);
        // light turns on
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());
    }

    @Test
    @Tag("21-8")
    @DisplayName("XOR lightbulb turns on  when 2 wires adjacent and 1 is on ")
    public void testXor2Wire() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_Xor2Wire", "c_LogicSwitchesTest");

        // lights start off off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on switch
        res = dmc.tick(Direction.RIGHT);
        // light switch on
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // activate 2nd switch, light now off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn off 1st switch, light should turn back on
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());
    }

    @Test
    @Tag("21-9")
    @DisplayName("COAND lightbulb does not turn on when 1 wire adjacent is on")
    public void testCoand1Wire() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_Coand1wire", "c_LogicSwitchesTest");

        // lights start off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on switch
        res = dmc.tick(Direction.RIGHT);
        // all lights not on since CoAnd
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());
    }

    @Test
    @Tag("21-10")
    @DisplayName("COAND lightbulb turns on when 2 adjacent wires turn on")
    public void testCoand2Wires() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_CoandOn", "c_LogicSwitchesTest");
        // lights start off off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // activate switch, all lights should turn on
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());

        // turn off switch, all lights should turn off
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());
    }

    @Test
    @Tag("21-11")
    @DisplayName("COAND lightbulb does not turn on when 2 wires adjacent, turned on separately")
    public void testCoand2WiresDelay() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_Coand2Wire", "c_LogicSwitchesTest");

        // lights start off off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on switch
        res = dmc.tick(Direction.RIGHT);
        // light off still
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // activate 2nd switch, light stays off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn off 1st switch, light should turn off
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());
    }

    @Test
    @Tag("21-12")
    @DisplayName("test door switch works, similar to light bulb")
    public void testDoorSwitch() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_LogicDoor", "c_LogicSwitchesTest");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();

        // cannot walk through switch door with sunstone
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

        // get rid of sun_stone
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));

        // activate switch
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        // assert can now pass through the door
        res = dmc.tick(Direction.UP);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

        res = dmc.tick(Direction.DOWN);

        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        // assert normal door is not unlocked by current (should be locked)
        res = dmc.tick(Direction.DOWN);
        assertEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @Tag("21-13")
    @DisplayName(" Logical bombs will only explode when their logical condition is fulfilled and destroys everything")
    public void testAndBomb() throws InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_AndBomb", "c_LogicSwitchesTest");

        // Pick up Bomb
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "bomb").size());

        // Place Cardinally Adjacent
        res = dmc.tick(TestUtils.getInventory(res, "bomb").get(0).getId());

        // Activate Switch
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);

        // Check Bomb did not explode
        assertEquals(1, TestUtils.getEntities(res, "bomb").size());
        assertEquals(2, TestUtils.getEntities(res, "boulder").size());
        assertEquals(2, TestUtils.getEntities(res, "switch").size());
        assertEquals(1, TestUtils.getEntities(res, "wall").size());
        assertEquals(1, TestUtils.getEntities(res, "treasure").size());
        assertEquals(5, TestUtils.getEntities(res, "wire").size());
        assertEquals(1, TestUtils.getEntities(res, "player").size());

        // activate 2nd switch
        res = dmc.tick(Direction.RIGHT);

        // Check Bomb exploded
        assertEquals(0, TestUtils.getEntities(res, "bomb").size());
        assertEquals(1, TestUtils.getEntities(res, "boulder").size());
        assertEquals(1, TestUtils.getEntities(res, "switch").size());
        assertEquals(0, TestUtils.getEntities(res, "wall").size());
        assertEquals(0, TestUtils.getEntities(res, "treasure").size());
        assertEquals(4, TestUtils.getEntities(res, "wire").size());
        assertEquals(1, TestUtils.getEntities(res, "player").size());
    }

    @Test
    @Tag("21-14")
    @DisplayName("Test light bulb and switch doors do not conduct")
    public void testNonConduct() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_NonConduct", "c_LogicSwitchesTest");

        // lights start off off
        assertEquals(2, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on switch
        res = dmc.tick(Direction.RIGHT);
        // 1 on, 1 off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());
        assertTrue(TestUtils.entityAtPosition(res, "light_bulb_on", new Position(8, 1)));
        assertTrue(TestUtils.entityAtPosition(res, "light_bulb_off", new Position(8, 0)));

        // check 1 door opened
        res = dmc.tick(Direction.DOWN);

        // check moved into top door
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

        // check moved cannot move into botton door
        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.DOWN);
        assertEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @Tag("21-15")
    @DisplayName("Test current not conduct diagonally")
    public void testCurrentDiagonal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_CurrentDiagonal", "c_LogicSwitchesTest");
        // lights start off off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on switch
        res = dmc.tick(Direction.RIGHT);
        // no lights turned on
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());
    }

    @Test
    @Tag("21-16")
    @DisplayName("another conductor powers an already activated component, the current is not 'refreshed'.")
    public void testCoandRefresh() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_Coand2WireRefresh", "c_LogicSwitchesTest");

        // lights start off off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on switch
        res = dmc.tick(Direction.RIGHT);
        // light off still
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // activate 2nd switch, light stays off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn off 1st switch, light should turn off
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn off 2nd switch, stil off
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        //turn on 2nd switch, light should be on since it turns all wires on
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());

    }

    @Test
    @Tag("21-17")
    @DisplayName("Normal bombs will only explode when next to acive switch")
    public void testNormalBomb() throws InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_NormalBomb", "c_LogicSwitchesTest");

        // Pick up Bomb
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "bomb").size());

        // Place Cardinally Adjacent
        res = dmc.tick(TestUtils.getInventory(res, "bomb").get(0).getId());

        // Activate Wire Switch
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Check Bomb did not explode
        assertEquals(1, TestUtils.getEntities(res, "bomb").size());
        assertEquals(2, TestUtils.getEntities(res, "boulder").size());
        assertEquals(2, TestUtils.getEntities(res, "switch").size());
        assertEquals(1, TestUtils.getEntities(res, "wall").size());
        assertEquals(1, TestUtils.getEntities(res, "treasure").size());
        assertEquals(5, TestUtils.getEntities(res, "wire").size());
        assertEquals(1, TestUtils.getEntities(res, "player").size());

        // activate cardinally adjacent switch
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.LEFT);

        // Check Bomb exploded
        assertEquals(0, TestUtils.getEntities(res, "bomb").size());
        assertEquals(1, TestUtils.getEntities(res, "boulder").size());
        assertEquals(1, TestUtils.getEntities(res, "switch").size());
        assertEquals(0, TestUtils.getEntities(res, "wall").size());
        assertEquals(0, TestUtils.getEntities(res, "treasure").size());
        assertEquals(4, TestUtils.getEntities(res, "wire").size());
        assertEquals(1, TestUtils.getEntities(res, "player").size());
    }

    @Test
    @Tag("21-18")
    @DisplayName("COAND lightbulb does not turn on when 3 wires adjacent, 2 turned on same tick and one after")
    public void testCoand3WiresDelay() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_LogicSwitchesTest_Coand3Wire", "c_LogicSwitchesTest");

        // lights start off off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        // turn on first switch
        res = dmc.tick(Direction.DOWN);
        // light off still
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);

        // activate 2nd switch, light stays off
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_off").size());
    }
}
