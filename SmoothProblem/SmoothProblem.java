import java.util.*;
import java.io.*;
public class SmoothProblem{
    
    public static final String input = "C:/Users/Kevin/algs4/CodeJam/SmoothProblem/B-large-practice.in";
    public static final String output = "largeout.txt";
//public static integer D, I, Gap;
    
    public static int costOfEntryValue(int[][] costs, int[] A, int j, int n, int D, int I, int Gap){
        //j is between 0 and 255; the potential new value of A[n].
        
        int mincost = Integer.MAX_VALUE;
 
        for(int i = n-1; i >= 0; i--){
            //Compute the cost of keeping A[i] but deleting everything between A[i] and A[n]
            int cost_of_deletion = (n-1-i)*D;
            if(cost_of_deletion >= mincost) break;
            int cost_of_ivalue = Integer.MAX_VALUE;
            //For each of the 256 values of A[i], get cost of A[i]=value followed by A[n] = j
            for(int k = 0; k < 256; k++){
                if(j==k){
                    cost_of_ivalue = Math.min(cost_of_ivalue, cost_of_deletion  + costs[i][k]);
                    continue;
                }
                if(Gap==0) continue;
                
                cost_of_ivalue = Math.min(cost_of_ivalue, 
                                          cost_of_deletion + costs[i][k] + I*((Math.abs(j-k)-1)/Gap));
            }
            mincost = Math.min(mincost, cost_of_ivalue);
        }
        mincost = Math.min(mincost, D*n);
        //Also compute the cost of just deleting _everything_ before A[n]
        
        mincost += Math.abs(j-A[n]);
        return mincost; //This is the new value of cost[n][j]
    
    }
    
    public static void main(String[] args){
        try{
            Scanner sc = new Scanner(new FileInputStream(new File(input)));
            int num_of_cases = sc.nextInt();
            
            for(int i = 0; i < num_of_cases; ++i){
                System.out.println(i);
                int D = sc.nextInt();
                int I = sc.nextInt();
                int Gap = sc.nextInt();
                int N = sc.nextInt();
                //System.out.println("D, I, Gap, N = " + D + "," + I+ "," + Gap+ "," + N);
                int[][] costs = new int[N][256];
                int[] A = new int[N];
                for(int j = 0; j < N; j++){
                    A[j] = sc.nextInt();
                    //System.out.println("A[j] = " + A[j]);
                }
                
                for(int k = 0; k < 256; ++k){
                        costs[0][k] = Math.abs(k-A[0]);
                }
                
                for(int n = 1; n < N; ++n){
                    for(int j = 0; j < 256; ++j) {
                        costs[n][j] = costOfEntryValue(costs, A, j, n, D, I, Gap);
                    }
                }
                int[] base = new int[N];
                for(int n = 0; n < N; ++n){
                    base[n] = costs[n][0];
                    for(int j = 0; j < 256; ++j){
                        base[n] = Math.min(base[n], costs[n][j]);
                    }
                }
                int totalmin = Integer.MAX_VALUE;
                for(int n = 0; n < N; ++n){
                    totalmin = Math.min(totalmin, base[n] + D*(N-n-1));
                }
                totalmin = Math.min(totalmin, D*N);
                FileOutputStream fos = new FileOutputStream(output, true);
                fos.write(("Case #" + (i+1) + ": " + totalmin + "\n").getBytes());
                fos.close();
            }
            sc.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}