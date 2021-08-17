import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class US_elections {

	public static int solution(int num_states, int[] delegates, int[] votes_Biden, int[] votes_Trump, int[] votes_Undecided){
    //The program decompose the states into the subsets of states that gives the sum of delegates required. 
    //Then from those subsets, it choose the minimum number of votes.
    //The program used the recursive brute force and hence it is not very efficient.
    //The program could be modified to use dynamic programming. 


    //Determine the total number of delegates required.
    int total_delegates = 0;
    for (int delegate : delegates) {
      total_delegates += delegate;
    }

    //Determine the minimum number of delegates to win 
    int delegates_to_win = 0;
    if (total_delegates%2!=0) {
      delegates_to_win = (total_delegates+1)/2;
    }
    else {
      delegates_to_win = (total_delegates+2)/2;
    }

    int [] states_lost = new int [num_states];
    int delegates_lost = 0;

    int [] states_used_to_win = new int [num_states];
    int [] weight_of_states = new int [num_states];
    int [] votes_required = new int [num_states];

    //States that biden can still win.  
    for (int state = 0; state<num_states; state++ ){
      // only the states that biden can win will be chosed
      if (votes_Biden[state] > (votes_Trump[state] - votes_Undecided[state])) {
        //States that biden is assured to win and does not require any vote
        if (votes_Biden[state] > (votes_Trump[state] + votes_Undecided[state])){
          states_used_to_win[state] = state;
          weight_of_states[state] = delegates[state];
          votes_required[state] = 0;
        }
        else {
          states_used_to_win[state] = state;
          weight_of_states[state] = delegates[state];
          int total_votes = (votes_Biden[state] + votes_Trump[state] + votes_Undecided[state]);
          if (total_votes%2!=0) {
            votes_required[state] = (((total_votes+1)/2)-votes_Biden[state]);
          }
          else {
            votes_required[state] = (((total_votes+2)/2)-votes_Biden[state]);
          }
        }
        
      }
      else {
        //states that biden is assured to lose
        delegates_lost += delegates[state];
      }
    }

    // no way for biden to win 
    int sum = 0;
    for (int i = 0; i < weight_of_states.length; i++) {
      sum += weight_of_states[i];
    }

    if (sum < delegates_to_win) {
      return -1;
    }
    //Find the combination of states from states_used_to_win array that will give a larger
    //result from sum than delegates_to_win.

    else {
      //For each number larger than the minimum number of delegates, I determine all the subsets of states possible 
      // The minimum number fo votes from those is determined and then from those determine the minimum of the loop.
      int votes = 1000000000*10;
      int count =0;
      int remain = (total_delegates - delegates_to_win - delegates_lost);
      //in order for the loop to not be large, I removed the delegates that trump is assured to win.
      for (int i =0; i <= remain; i++) {
        int del = delegates_to_win +i;
        int votes_combi = findmin(weight_of_states,votes_required,del);
        //In case the number of votes is negative
        if (votes_combi < 0) {
          votes = 0;
        }
        else {
          votes = Math.min(votes,votes_combi);
        }
        count++;
      }

      return votes;
    }

	}

  public static int votes_per_delegates(int [] delegates_per_state, int n, int [] votes, int total) {
    int x[] = new int[delegates_per_state.length];
    int j = delegates_per_state.length-1;
    //I create an array representing that stores the states
    while (n > 0) {
      x[j] = n%2;
      n = n/2;
      j--;
    }

    int sum = 0;
    int vote = 0;

    for (int i = 0; i < delegates_per_state.length; i++) {
      //The state is included
      if (x[i] == 1) {
        sum = sum + delegates_per_state[i];
      }
    }
    boolean set =true;
    // there is a subset that sum is the number of delegates
    if (sum == total) {
      set = true;
      for (int z=0;z<delegates_per_state.length;z++) {
        //add the number of votes
        if (x[z] == 1) {
          if (votes[z] <= 0) {
            vote += 0;
          }
          else {
            vote += votes[z];
          }
        }
      }
      return vote;
    }
    else {
      return -5;
    }
  }
  public static int findmin(int [] arr, int [] votes, int K) {
    int min = 1000000;
    int count = 0;
    int x = (int)Math.pow(2,arr.length); //power set to find all subsets of states that sum is the number
    for (int i = 1; i <x; i++) {
      int y = votes_per_delegates(arr,i,votes,K);
      //remove the negative number of votes when biden is assured to win.
      if (y!=-5) {
        if (count==0) {
          min =y;
        }
        else {
          //return the minimum votes.
          min = Math.min(min,y);
        }
        count++;
      } 
    }
    return min;
  }

	public static void main(String[] args) {
	 try {
			String path = args[0];
      File myFile = new File(path);
      Scanner sc = new Scanner(myFile);
      int num_states = sc.nextInt();
      int[] delegates = new int[num_states];
      int[] votes_Biden = new int[num_states];
      int[] votes_Trump = new int[num_states];
 			int[] votes_Undecided = new int[num_states];	
      for (int state = 0; state<num_states; state++){
			  delegates[state] =sc.nextInt();
				votes_Biden[state] = sc.nextInt();
				votes_Trump[state] = sc.nextInt();
				votes_Undecided[state] = sc.nextInt();
      }
      sc.close();
      int answer = solution(num_states, delegates, votes_Biden, votes_Trump, votes_Undecided);
      	System.out.println(answer);
    	} catch (FileNotFoundException e) {
      	System.out.println("An error occurred.");
      	e.printStackTrace();
    	}
  	}

