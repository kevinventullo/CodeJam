import java.util.*;
import java.util.regex.*;
import java.io.*;

public class SquareProblem{
    
    public static final String input = "C:/Users/Kevin/algs4/CodeJam/D-large-practice.in";
    public static final String output = "largesquaregoodoutput.txt";
    
    ArrayList<int[]> squares;
    
    public int howManyTiles(int M, int[] sizes){
        squares = new ArrayList<int[]>();
        
        Arrays.sort(sizes);//Change to reverse order actually
        int[] reversed = new int[sizes.length];
        
        for(int i = 0; i < sizes.length; ++i){
            reversed[i] = sizes[sizes.length - i - 1];
        }
        
        sizes = reversed;
        
        for(int i = 0; i < sizes.length; ++i){
            int j;
            for(j = 0; j < squares.size(); ++j){
                if(sizeOntoSquare(squares.get(j), sizes[i])) break;
            }
            if(j == squares.size()){
                squares.add(new int[M]);
                sizeOntoSquare(squares.get(j), sizes[i]);
            }
        }
        return squares.size();
    }
    
    public boolean sizeOntoSquare(int[] square, int size){
        for(int i = 0; i < square.length; ++i){
            if((square.length - i) < size) break;
            if((square.length - square[i]) < size) continue;
            for(int j = 0; j < size; ++j){
                square[i+j] += size;
            }
            return true;
        }
        return false;
    }
    
    
    
    
    public static void main(String[] args){
        try{
            Scanner sc = new Scanner(new FileInputStream(new File(input)));
            int totalnum = sc.nextInt();
            SquareProblem sp = new SquareProblem();
            
            for(int i = 0; i < totalnum; ++i){
                
                int n = sc.nextInt();
                
                int M = sc.nextInt();
                int[] sizes = new int[n];
                int[] repeats = new int[32];
                
                
                
                for(int j = 0; j < n; ++j){
                    int temp = sc.nextInt();
                    repeats[temp]++;
                    sizes[j] = 1 << temp;
                }
                Arrays.sort(sizes);
                
                if(n==1){
                    FileOutputStream fos = new FileOutputStream(output, true);
                    fos.write(("Case #" + (i+1) + ": " + 1 + "\n").getBytes());
                    fos.close();
                    continue;
                }
                
                
                if(M > (1 << 9)){
                    int log2m = 0;
                    while(M > 1){
                        M = M/2;
                        log2m++;
                    }
                    
                    for(int k = 0; k < 31; ++k){
                        repeats[k+1] += (repeats[k]+3)/4;
                    }
                    
                    FileOutputStream fos = new FileOutputStream(output, true);
                    fos.write(("Case #" + (i+1) + ": " + repeats[log2m] + "\n").getBytes());
                    fos.close();
                    continue;
                }
                
                
                
                
                
                
                
                
                
                
                int ans = sp.howManyTiles(M, sizes);
                FileOutputStream fos = new FileOutputStream(output, true);
                fos.write(("Case #" + (i+1) + ": " + ans + "\n").getBytes());
                fos.close();
            }
            sc.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            
        }
        
    }
    
    
}