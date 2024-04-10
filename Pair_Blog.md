
# **Assignment II Pair Blog Template**

  
  

## **Task 1) Code Analysis and Refactoring ⛏️**

  
  

### **a) From DRY to Design Patterns**

  

Links to your merge requests _[https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/7](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/7) _

  
  

> i. Look inside src/main/java/dungeonmania/entities/enemies. Where can you notice an instance of repeated code? Note down the particular offending lines/methods/fields.

  

Both ZombieToast and Mercenary classes have very similar repeated code that violates the DRY principle.

  

Repeated code exists in the move() methods (lines 26-53 in zombieToast, lines 102, 129 in mercenary java).

  
  

> ii. What Design Pattern could be used to improve the quality of the code and avoid repetition? Justify your choice by relating the scenario to the key characteristics of your chosen Design Pattern.

  

A strategy pattern could be used to improve the quality of the design and also avoid repetition. Each enemy entity has various “strategies” as to what it could do when the move method is called, depending on the various states of the game (if the player has a potion activated etc..). Hence a strategy pattern is a good design choice here as it makes the code flexible to add new strategies if new potions or enemies are added. Also, by all enemies inheriting the movement strategy, we remove the repeated code

  
  

> iii. Using your chosen Design Pattern, refactor the code to remove the repetition.

  

Added a strategy pattern for movement of enemies. All the enemies inherited the movement strategy, hence no repeated code as they all used the same strategy pattern. Each potential movement type (invisible, invincible, ally etc..) was a new class of strategy pattern. Then within each enemy class, a single if statement was used to assign the correct strategy for the given game state.

  
  

###

  
  

### b) Observer Pattern

  
  

> Identify one place where the Observer Pattern is present in the codebase, and outline how the implementation relates to the key characteristics of the Observer Pattern.

  

The observer pattern can be seen in interactions between the Switch and Bomb classes.

  

The Switch class acts as the subject that is being observed. We can add/remove instances of Bomb (observers) to the Switch class using the subscribe and unsubscribe methods, hence holding a list of observers to notify later on

  

The Bomb class are the observers, that are notified by the Switch class, when the switch overlaps with a Boulder.

  

The onOverlap method is the notification method that “updates” our observers (Bomb) causing them “notify” and hence “explode”.

  
  

### c) Inheritance Design 

  

Links to your merge requests: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/9](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/9)

  
  

> i. Name the code smell present in the above code. Identify all subclasses of Entity which have similar code smells that point towards the same root cause.

  

The code smell is Refused Bequest, as the Exit class is inheriting methods that it doesn’t need to implement from the Entity superclass - the methods onOverlap, onMovedAway, and onDestroy have no actual implementation and just returns.

  

This is the case for the following subclasses of Entity: Buildable, Potion, Arrow, Bomb, Key, Sword, Treasure, Wood, Enemy, ZombieToastSpawner, Boulder, Door, Exit, Player, Portal, Switch, and Wall - all these subclasses inherit some methods from Entity that they do not need to implement.

  
  

> ii. Redesign the inheritance structure to solve the problem, in doing so remove the smells.

  
  
  

* Created specific interfaces Moveable, Overlappable, Destroyable which are only implemented by the specific entities that implement these methods

  
  

###

  
  

### **d) More Code Smells**

  

Links to your merge requests: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/6](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/6)

  
  

> i. What design smell is present in the above description?

  

The design smell present is shotgun surgery, where a small change in the code forces a lot of changes to different classes. This indicates the code is rigid and has a high degree of coupling.

  
  

> ii. Refactor the code to resolve the smell and underlying problem causing it.

  

Created a superclass Collectables that extends Entity and implements the InventoryItem interface. As the implementation for onOverlap() in all collectables was identical, this was moved to be in the Collectables superclass. This decouples the pickup logic from specific entities, to a generalised Collectables class - meaning that if the way picking up items needs to be modified in the future, this will only need to be modified in one place.

  
  

### **e) Open-Closed Goals**

  

Links to your merge requests: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/5](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/5)

  
  

> i. Do you think the design is of good quality here? Do you think it complies with the open-closed principle? Do you think the design should be changed?

  

No this is not good design quality, because it violates open closed principle. The code requires lots of modification if a new goal is added**. **It does not comply with the open-close principle. It is very rigid, if new goals are created, numerous methods within the Goal class will need to be changed.** **The design should be changed.

  
  

ii. If you think the design is sufficient as it is, justify your decision. If you think the answer is no, pick a suitable Design Pattern that would improve the quality of the code and refactor the code accordingly.

  

We have refactored this class to use a Strategy Pattern. This strategy interface can handle the `achieved` and `toString` methods. It is also open to adding new goal methods as the code develops. It also does not require modification of existing strategies to implement new ones.

  
  

###

  
  

### **f) Open Refactoring**

  

Merge Request 1: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/11](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/11)

  

The following changes have been made in regards to enemy movement:

  
  
  

* more self documenting code: moved logic statements into appropriately named helper functions in the class, renamed some variables for better readability
    * e.g. `isValidMovePosition()`, `calculateNextXPosition()`
* new getters to reduce Demeter violations:
    *  `Game.getPlayerPosition()`: returns a Player's position
    *  `Game.getPlayerPreviousPosition()`: returns a Player's previous position
    *  `Game.getEntities()`: returns a list of entities at a given position from the map
    *  `Game.moveTo()`: will move a given entity to a given position on the map
    *  `Entity.getCardinallyAdjacentPositions()`: returns a List of Positions adjacent to the entity
    *  `MoveInvincible.java`: simplified redundant move logic
    *  `MoveInvisibleAndZombieToast.java`: movement behaviour in original Invisible and ZombieToast classes was identical - moved these into one class in accordance with DRY

    

Merge Request 2: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/13](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/13)
*  `Game.registerEnemyMovement()`: new method to specifically register enemy movement (reduces repeated code)
* renamed some variables for better self documenting
* moved some logic to helper functions to reduce repeated code + code clarity
*  see `EntityFactory.isValidSpawnRate()`. `EntityFactory.getAdjacentWalls()`, `EntityFactory.isValidSpawnRate()`

  

Merge Request 3: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/14](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/14)

* Refactored `PlayerState` into a strategy pattern instead of state pattern
* Decoupled `Player` and active potion by moving potion to PlayerState
* Refactored `Player` to reduce Demeter violations

  

Merge Request 4: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/15](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/15)
* abstracted logic into self-documenting helper functions in `BattleFacade` and `BattleStatistics`
* new getters/setters `getBattleStatisticsHealth()` and `setBattleStatisticsHealth()` in the Battleable interface to reduce Demeter violations

  

Merge Request 5: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/16](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/16)
* default behaviour of `canMoveOnTo` in `Entity.java` now returns true (instead of false)
* removed all overriding methods of `canMoveOnTo` that returned true

  

Merge Request 6: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/17](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/17)
* removed `Player` as an attribute from `GameMap` to reduce coupling
* `GameMap` now accesses the player attribute from the Game class
* new setter `setPlayer` in Game.java to keep existing functionality

  

Merge Request 7: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/18](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/18)
* Removed deprecated methods marked for removal Used alternative methods `setPosition()` to replace method utilising the deprecated `translate()` method

  

Merge Request 8: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/19](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/19)
* Refactored battle classes. Use of decorator pattern to calculate battle item buffs and usage. Refactored battle statistics to not use static methods instead instance methods for clarity `BattleFacade` LoD refactor and coupling reduction

  

Merge Request 9: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/20](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/20)
* Removed all violations of the Law of Demeter from the codebase
* added new getters to do so

  

Merg Requestion 10: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/21](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/21)

* `Buildable`'s creation in Inventory was hard coded
* Refactored `Inventory.java` methods to be open for extension for new buildables.
* Refactored buildable classes to handle building and removing methods

  
  



  
  

## **Task 2) Evolution of Requirements 👽**

  

**Planning** - Enemy Bosses + Sunstone + [snake/logic/swamp/bosses]

  
  
  

1. Requirements Engineering. Analyse the task requirements, including the technical and product specifications. If you need to, make some assumptions and document these in your pair blog post.
2. Detailed Design. In your pair blog post, plan out a detailed design for the task. This should include:
    * What fields/methods you will need to add/change in a class
    * What new classes/packages you will need to create
3. Design Review. Have your partner review the design, and go back and iterate on the design if needed.
4. Create a Test List. Once the design is approved, write a test list (a list of all the tests you will write) for the task. Map out each of the conceptual cases you want to test. This can be written in your blog post, or if you want make function stubs for JUnit tests and put up a Merge Request (link in your blog).
5. Test List Review. Have someone else in your team review the test list to make sure the test cases all make sense and cover the input space.
6. Create the Skeleton. Stub out anything you need to with class/method prototypes.
7. Write the tests, which should be failing assertions currently since the functionality hasn't been implemented.
8. Development. Implement the functionality so that your tests pass.
9. Run a usability test (check your functionality works on the frontend).
10. Where needed, refactor your code to improve the style and design while keeping the tests passing.
11. Put up a merge request with your changes into master. The CI should be passing. The merge request should have a meaningful title and contain a description of the changes you have made. In most cases you should just be able to link them to your design/test list blog.
12. Code Review from your partner, iterate where needed then they should approve the MR.

  
  
  

---

  
  
  

### a) Microevolution - Enemy Goal

  

Links to your merge requests: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/27](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/27), [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/28](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/28)

  

**Assumptions**

  
  
  

* If there are no spawners to begin with, the requirement to achieve the enemy goal is still met
* Minimum number of defeated enemies must be >= 0 - (but can be 0) [https://edstem.org/au/courses/15090/discussion/1860919](https://edstem.org/au/courses/15090/discussion/1860919)
* enemies killed by player placed bombs don’t count towards the enemies goal https://edstem.org/au/courses/15090/discussion/1865615

  
  

**Design**

  

New classes:
* (in goalStrategy) `public class EnemyGoal implements GoalStrategy`
	*  `@Override public boolean achieved(Game game)`
	*  `@Override public toString(Game game)`
* (update Player)
	*  `private int numDefeatedEnemies = 0`
	*  `public void setNumDefeatedEnemies(int)`
	*  `Public int getNumDefeatedEnemies()`

  

**Changes after review**

  

Consider updating:
* public void onOverlap/onInteract such that upon enemy deletion, the numDefeatedEnemies is incremented.
* Considering this, the counter may need to be stored in game, depending on variables passed into the enemy class

  

Tests:
Approved

  

**Test list**
1. Set enemy goal, destroy all spawners and minimum number of enemies -> should be achieved
2. Set enemy goal, destroy all but 1 spawners and minimum number of enemies -> shouldnt be achieved
3. Set enemy goal, destory all spawners and minimum number of enemies - 1 -> shouldn’t be achieved
4. Set enemy goal, no spawners and minimum number of enemies -> should be achieved
5. Wait for the spawners to spawn and then kill all enemies
6. Set enemy goal and exit goal. Enemy goal should be complete before getting to the exit (exit goal done last)
7. Set enemy goal OR treasure goal. Treasure goal gets completed -> game = complete
8. Boulder goal AND (Boulder AND Enemy goal) -> ensure all done before complete
9. Exit goal OR (Enemy goal OR Boulder goal) -> get to exit first, but game not complete (need to complete enemy/boudler first)

  

**Other notes**

  
  
  

* Look at treasure goal for task 3

  
  
  

---

  
  
  

### **Choice 1 Sun Stone & More Buildables**

  

[https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/25](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/25)

  

**Assumptions**

  
  
  

* Whether multiple midnight armour can be used is undefined
* Sceptre is not considered a weapon to destroy entity factories
* Midnight Armour is considered a weapon to destroy entity factories
* Note that initialising the sceptre promotes a game tick
* A bribed enemy that is then mind-controlled, will no longer be an ally once the mindcontrol has worn off

  

**Design**

  

New classes:
* (in collectables) `public class SunStone extends Collectable implements DoorUnlockable, TreasureCount`
    1.  `public SunStone()`
* (in buildables) `public class Sceptre extends Buildable`
    1.  `private int duration //same as durability`
    2.  `public Sceptre(int duration)`
    3.  `public void use(Game game)`
    4.  `public void applyBuff(BattleStatistics origin)`
    5.  `public int getDurability()`
    6.  `public class Treasure extends Collectable implements TreasureCount`
* (in buildables) `public class MidnightArmour extends Buildable`
    1.  `private int attack`
    2.  `private int defence`
    3.  `public MidnightArmour(double attack, double defence)`
    4.  `public void use(Game game)`
    5.  `public void applyBuff(BattleStatistics origin)`
    6.  `public int getDurability()`
    7.  `public interface Useable`
    8.  `public interface TreasureCountable`

Updated Classes:
* In `EntityFactory`:
    1.  `public MidnightArmour buildMidnightArmour()`
    2.  `public Sceptre buildSceptre()`
* update construct entity to include 3 new entities
* In `Player`:
    1. update treasure count to look for the TreasureCount interface instead of the treasure class
* In `Inventory`:
    1. update Constructor
* In `Game`:
    1. update `init()`
* In `Mercenary`
    1.  `public void onTick()`
    2.  update `interactable()`
    3.  update `interact()`

  

**Changes after review**

* Suggest SunStone to not be a subclass of Treasure but a Collectable - both to inherit TreasureClass interface
* Ticks to be controlled in Game.java and Mercenary rather than player.java

  

**Test list**

  

[https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/23](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/23)



1. Collect Sunstone and check in inventory
2. Use sunstone to open door, check retained after use
3. Sunstone collection to meet treasure goal
4. Create sceptre, with 2 sunstones, 1 should be retained, the other consumed
5. Create a shield with 2 wood + 1 sunstone - sunstone should be retained (as it replaces treasure/key)
6. Create a shield with 2 arrows + (1 treasure or key) and sunstone in inventory - 1 treasure/key should be consumed
7. MindControl 1 mercenary and go into battle, let mindcontrol duration end, then go into battle again
8. Attempt to craft midnight armour with zombies in the dungeon (shouldnt work)

  


  

---

  
  

### Choice 2 Logic Switches

  

Links to your merge requests: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/33](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/33)

  

**Assumptions**

* Sunstones cannot open logic doors
* Only Players can move onto logic doors if they are open
* Logic doors open/close independent of where the player is (ie player doesnt have to be on the door for it to evaluate the logical condition and open)
* Switches only create currents (ie activate adjacent wires), but they don't conduct current themselves – so if we have [on switch -> on wire -> off switch], even though the wire is conducting current, this does not turn the off switch on
* Switch doors are created closed
* Player can’t walk onto switch door if closed
* What happens to a Co_and logical item when 1 or more adjacent blocks are destroyed after the co_and condition is satisfied is undefined
* Boulders can be moved on top of wires
* Players can move on top of wires

  

**Design**

  

New Classes:

  
* (in Entities) `public class LightBuib extends Entity implements LogicalEntity, Toggleable`
	1. `public LightBulb(strategy)`
	2.  `private Strategy strategy`
	3.  `Private boolean isOn()`
* (Entities folder) - Strategy Pattern
	1.  `public boolean evaluate()`
	2.  `OrStrategy`
	3.  `AndStrategy`
	4.  `XorStrategy`
	5.  `CoAndStrategy`
	6.  `Static StrategyFactory`
		1.  `Public static createStrategy(String)`
* (in Entities) `public class SwitchDoor extends Door implements LogicalEntity, Toggleable`
	1. overrides on `onOverlap` and `canMoveOnto`
* (in Entities) `public class Wire extends Entity implements Toggleable`
	1.  `public boolean hasCurrent()`
	2.  `Private int activatedOnTickNumber()` - used for checking CoAnd eligibility
	3.  `public void onTick(tickCount)`
	4.  `public void setCurrent`
* (in Collectables) `public class LogicalBomb extends Bomb implements logicalEntity`
	1. do not update interact (click to put down remains the same)
	2. overrides `onPutDown()`
	3. should not explode on putdown, but check for on current
*  `Public interface Toggleable()`
	1.  `Public void turnOff()`
*  `Public interface logicalEntity()`
	1.  `Public boolean evaluate(Game)`

  
  
  

**Changes after review**

  

Design was implemented together in a call hene both perspectives were considered.

  

**Test list**
[https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/32](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/32)

  
  

1. Basic single line path wire system with a block of wires (like a square at the end). + a diagonal wire that does not turn on ever
	* turn on switch, all wires should turn on
	* turn off switch, all wires turn off
2. Same test case as above, but with 2 switches connected
	* turning on 2nd switch should not affect it
	* turn off 1st switch, nothing should change
3. OR Light Bulb turns on when 1 wire adjacent is on
	* in the same test, turn on 2nd wire, and should still be on
4. AND light bulb turns on when only 2 wires adjacent and 2 r turned on
	* then turn off the light by turning off switch
5. AND light bulb does not turn on when 3 wires adjacent and 2 wires are turned on
6. AND light bulb does not turn on when only 1 adjacent wire EXISTS + it is turned on
7. XOR light bulb turns on when only 1 wire adjacent and is on
8. XOR lightbulb turns on when 2 wires adjacent and 1 is on
	* Make the second wire turn on too -> xor lightbulb should be off
	* Turn off the first wire again -> xor lightbulb is on
9. COAND lightbulb does not turn on when 1 wire adjacent is on
10. COAND lightbulb turns on when 2 adjacent wires turn on on same tick
	* then turn off 1 switch, turns off,
11. COAND lightbulb does not turn on when 2 wires adjacent, 1 is on
	* Turn the other wire on -> should not turn on
12. OR door switch
	* no power, can't go through the door
	* get sunstone, still cant open door
	* turn on switch
	* go through the door success
	* check a normal door can still not be opened by current
13. OR Logical bombs will only explode when their logical condition is fulfilled. (and not when they’re placed like a normal bomb)
	* Explode a logic bomb -> everything (walls, adjacent wires/switches/bulbs are destroyed
14. Test light bulb and switch doors do not conduct
	* bulb, bulb, power, door, door (should have 1 on, 1 off for each)
15. Test wires do not conduct diagonally
16. Another conductor powers an already activated component, the current is not 'refreshed'.
17. Normal bomb not activated by a wire and must be activated by a switch

  
  

  
  
  

## Task 3) Investigation Task

  

Merge Request 1: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/29](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/29)
* In BattleStatistics, the static method applyBuff, fails to update the magnifier with a new value.
* We used public getters and setters to allow the battleItem classes to make changes to the player statistics one by one, without having to update all at once.
* Additionally, the bow test had to be updated.

  

Merge Request 2: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/30](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/30)
* Use of optInt instead of optDouble in FactoryEntity(), however, errors do not show as always using integers when testing. Likewise errors follow suit in test files using optInt instead of doubles
* Adjusted by changing attack and defence values to doubles, and using optDouble
* Additionally some tests used optInt instead of optDouble, making attack calculations incorrect.

  

Merge Request 3: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/24](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/24)
* Destroying ZombieToastSpawners with a valid weapon doesn’t remove them from the map. Added a line to remove it from the map after interacting with it.
* Modified test 10-7 in ZombieTest.Java with this updated logic

  

Merge Request 4: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/30](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/30)
* Bribe radius not implemented, just checks if bribe radius is set, needs to compare against distance to player
* Added private method in mercenary to check if the player is within bribe distance
* Additonally, mercenary tests were amended.

  

Merge Request 5: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/35](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/35)
* Added method override in Key class to check if player already holds a key, if so should not pick up key
* Tests also fixed to only collect 1 key at a time

  

Merge Reqest 6: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/36](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/36)
* Zombie class should not teleport
* Fixed method in Portal class that checks which classes on overlap should teleport
* Test fixed to check distance from start remains less than 1

  

Merge Request 7: [https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/37](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T1/teams/T11A_IRON/assignment-ii/-/merge_requests/37)
* noticed that a player couldn't teleport onto a portal if the pair portal only had 1 free cardinally adjacent position
* updated `Portal.canTeleportTo()` to do a more accurate check
* Added new test to check for this