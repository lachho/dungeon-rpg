# Assignment feedback

- Marked By Ryan

### Task 1
#### a)
- Correct Pattern identified
- Correct implementation

#### b)
- Correct identification
- Correct comparison between the pattern and the example

#### c)
- Correct code smell
- Correct identification of subclasses that have the smell
- Correct solution and implementation of the solution

#### d)
- Correct smell identified
- Correct solution for the smell

#### e)
- Correct decision on if the design quality is good. Could've had a little more explanation as to why it needs modification (It's not complete to say 'No this is not good design quality, because it violates open closed principle'.)
- You named the pattern as strategy, but implemented a composite pattern (composite is the correct pattern here but I have to penalise slightly for naming the wrong pattern)

#### f)
- Identified and corrected most of the issues that are required to get full marks
- Note: To get the 10 / 10 for this question, you had to get each and every problem in the codebase, which is pretty impossible imo. With that being said, you got a fair chunk of them :)

### Task 2

- Coupling is minimised throughout the codebase
- This is because many of the features have not been implemented correctly (such as creating the subclasses of device and satellite) and hence there is no opportunity to create coupling
- The simulate method within the blackout controller indicates coupling (the logic in this function should be split among the teleporting satellite, relay satellite etc.).

#### Enemy Goal
- Correctly Implemented
- Design is good
- Proper test cases written out

#### Sunstone & Buildables
- Great implmentation for this. Basically everything looks good here
- One small issue that a lot of students fall into - the `use` method inside for MidnightArmour is effectively useless; durability doesn't decrease for the armour. This is refused bequest and should have been refactored.
- Testing looks good

#### Logic Switches
- Great implementation here as well. Interesting decision with the StrategyFactory, I like it.
- Tests look good here as well

## Task 3
- The requirement here is that you need to discover X number of the defects given in our basic defect list. I think that you guys have found many that aren't apart of our list and I'm willing to count them as substitutes for the ones that we have :)


## Cody Hygiene
- Branch coverage is good
- Linter is passing
- Regression tests remain passing

## Merge Requests
- Merge requests look good

Incredible job with this assignment!
