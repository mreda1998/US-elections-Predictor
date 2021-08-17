import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class US_elections {

	public static int solution(int num_states, int[] delegates, int[] votes_Biden, int[] votes_Trump, int[] votes_Undecided){
    //Determine the total number of delegates required.
    int total_delegates = 0;
    for (int delegate : delegates) {
      total_delegates += delegate;
    }
    int delegates_to_win = 0;
    if (total_delegates%2!=0) {
      delegates_to_win = (total_delegates+1)/2;
    }
    else {
      delegates_to_win = (total_delegates+2)/2;
    }

   //Determine the states that biden have no possible way of winning
    int [] states_lost = new int [num_states];

    int [] states_used_to_win = new int [num_states];
    int [] weight_of_states = new int [num_states];
    int [] votes_required = new int [num_states];

    //States biden can still win.  
    for (int state = 0; state<num_states; state++ ){
      if (votes_Biden[state] > (votes_Trump[state] - votes_Undecided[state])) {
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
    int sum = 0; 
    for (int i = 0; i < weight_of_states.length; i++) {
      sum += weight_of_states[i];
    }
    // no way for biden to win 
    if (sum < delegates_to_win) {
      return -1;
    }
    //Find the combination of states from states_used_to_win array that will give a larger
    //result from sum than delegates_to_win.

    // I used a greedy algorithm : I started by sorting the list in decreasing order of delegates
    // Then decrease the number of delegates from the total until it become equal or smaller than 0

    else {
      list_sorting_asc(states_used_to_win,weight_of_states,votes_required);
      
      int delegates_left_1 = delegates_to_win;
      int delegates_left_2 = delegates_to_win;

      int votes_needed1 = 0;
      int votes_needed2 =0;

      for (int i = 0; i < states_used_to_win.length; i++) {
        if (delegates_left_1 >=0) {
          delegates_left_1 -= weight_of_states[i];
          if (votes_required[i] <= 0) {
            votes_needed1 += 0;
          }
          else {
            votes_needed1 += votes_required[i];
          }
        } 
        else {
          break;
        }
      }

      list_sorting_des(states_used_to_win,weight_of_states,votes_required);
      for (int i = 0; i < states_used_to_win.length; i++) {
        if (delegates_left_2 >= 0) {
          delegates_left_2 -= weight_of_states[i];
          if (votes_required[i] <= 0) {
            votes_needed2 += 0;
          }
          else {
            votes_needed2 += votes_required[i];
          }

        } 
        else {
          break;
        }
      }      
      return Math.min(votes_needed1,votes_needed2);

    }


	}
  public static void swap (int[] arr, int i, int j){
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
  public static void list_sorting_asc (int[] states, int [] weight, int [] votes) {
    for (int i = states.length - 1; i >= 0; i--) {
      for (int j = 0; j<= i-1; j++) {
        if (weight[j] < weight[j+1])
        {
          swap(states,j,j+1);
          swap(weight,j,j+1);
          swap(votes,j,j+1);
        }
      }
    }
  }
  public static void list_sorting_des (int[] states, int [] weight, int [] votes) {
    for (int i = states.length - 1; i >= 0; i--) {
      for (int j = 0; j<= i-1; j++) {
        if (weight[j] > weight[j+1])
        {
          swap(states,j,j+1);
          swap(weight,j,j+1);
          swap(votes,j,j+1);
        }
      }
    }
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

}
