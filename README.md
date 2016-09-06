# Poker Hand Calculator
Classifies poker hands and determines order

## To Run
- `mvn clean install` will test, compile, and package application
- `mvn exec:java` will run the code

## A note on scope
I have cut some scope from this application in two ways that the problem description left
unspecified. First, I have not provided any sort of user input to the application, as I
decided that the exercise of prompting and validating user input wasn't of interest to
the specifications and would therefore be a waste of time. Second, as the specification
gave [Wikipedia](https://en.wikipedia.org/wiki/List_of_poker_hand_categories) as the
source of hand ordering, and the page gave no information about tie-breaker situations, I
determined that the specification was only for hand classification, and therefore defined
a tie as any pair of hands that have the same classification. In reality, such a pair
would go through a series of
[tie-breaker checks](http://www.pokerhands.com/poker_hand_tie_rules.html) that are
classification-dependent. This would likely be implemented in a second service similar
to the HandClassifierService.
