import java.util.*;
import java.io.*;
public class NumberGame{
    public static int counter;
    
    public static final String input = "C:/Users/Kevin/algs4/CodeJam/NumberGame/numberlargepractice.in";
    public static final String output = "largeoutput.txt";
    public static final double golden = (1.0 + Math.sqrt(5))/2;
    public static final double goldinv = (Math.sqrt(5)-1)/2;
    
    public static boolean isWinningPosition(int a, int b){
        if(a > b) return isWinningPosition(b,a); //Now a <= b
        if(a == b) return false; //Now a < b
        if(b >= 2*a) return true; // Now a < b < 2a => b-a < a.
        return !isWinningPosition(b-a, a);
    }
    
    
    public static int howMany(double i, double B1, double B2){
        int above;
        if(B1 < golden*i) above = (int) Math.max(B2 -  Math.ceil(golden*i)+1, 0.0);
        else above = (int) (B2-B1+1);
        
        int below;
        if(B2 > goldinv*i) below = (int) Math.max(Math.floor(goldinv*i) - B1+1, 0.0);
        else below = (int) (B2-B1+1);
        
            if((above + below) < 0){
                System.out.println("i = " + i);
                System.out.println("B1 = " + B1 + " B2 = " + B2);
                
                
            }
            
        
        return (above + below);
    }
    
    
    public static long howManyWinning(int A1, int A2, int B1, int B2){
        long count = 0;
        for(int i = A1; i <= A2; ++i){
            int temp = howMany(i, B1, B2);
            count += temp;
        }
        return count;
    }
    
    
    public static void main(String[] args){
        try{
            Scanner sc = new Scanner(new FileInputStream(new File(input)));
            FileOutputStream fos = new FileOutputStream(output, true);
            int num_trials = sc.nextInt();
            for(int i = 0; i < num_trials; ++i){
                counter = i;
                int A1 = sc.nextInt();
                int A2 = sc.nextInt();
                int B1 = sc.nextInt();
                int B2 = sc.nextInt();
                
                fos.write(("Case #" + (i+1) + ": " + howManyWinning(A1,A2,B1,B2) + "\n").getBytes());
                
            }
            fos.close();
            sc.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
}