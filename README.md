# US-elections-Predictor

A function that return the miniimum number of votes that Mr Biden needs to convince in order to secure presidency of the US. 

The function gets 5 parameters:

- An int variable called num states that represents the number of states considered by the Poll.

- An int[] array of integers called delegates with the number of delegated for the num states states.

- An int[] array of integers called votes Biden with the number of votes for Mr Biden for the num states states.

- An int[] array of integers called votes Trump with the number of votes for Mr Trump for the num states states.

- An int[] array of integers called votes Undecided with the number of Undecided votes for the num states states.

I provide two solutions. The first one is a greedy algorithm that find the combination of states needed to win by always trying to win the largest state possible 
and removing the number of delegates from the number of delegates required to win.

The second solution modifies the dynamic programming problems combinational sum, by finding the different combinations of states possible. 
