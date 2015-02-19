import java.util.*;
import java.io.*;
public class Rotate{
    
    public static final String filename = "C:/Users/Kevin/algs4/CodeJam/A-large-practice.in";
    public static int counter;
    
    
    public Rotate(){
    }
    
    public static char[][] rotater(int N, Scanner s){
        char[][] grid = new char[N][N];
        s.nextLine();
        for(int i = 0; i < N; ++i){
            grid[i]= s.nextLine().toCharArray();
            int j = N-1;
            int k = N-1;
            while(j >= 0){
                if(grid[i][j] == '.') --j;
                else{
                    grid[i][k] = grid[i][j];
                    --k; --j;
                }
            }
            while(k >= 0){
                grid[i][k] = '.'; --k;
            }
        }
        return grid;
    }
    
    public static void checkSolutions(int N, int K, char[][] grid){
        boolean redsol = checkSolns(N, K, grid, 'R');
        boolean bluesol = checkSolns(N, K, grid, 'B');
        String status = "?";
        if(redsol && bluesol) status = "Both";
        if(redsol && !bluesol) status = "Red";
        if(!redsol && bluesol) status = "Blue";
        if(!redsol && !bluesol) status = "Neither";
        try{
            FileOutputStream fos = new FileOutputStream("mysolution.txt", true);
            fos.write(("Case #" + counter + ": " + status + "\n").getBytes());
            fos.close();
            counter++;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public static boolean checkSolns(int N, int K, char[][] grid, char x){
        for(int i = 0; i < N; ++i){
            int j = N-1;
            while(j>=0){
                while(j>= 0 && grid[i][j] != x) --j;
                if(j < 0 || grid[i][j] == '.') break;
                if(checkAt(N, K, grid, i, j)) return true;
                --j;
            }
        }
        return false;
    }
    
    public static boolean checkAt(int N, int K, char[][] grid, int i, int j){
        char x = grid[i][j];
        if(j >= K-1){
            int k;
            for(k = 0; k < K; ++k){
                if(grid[i][j-k] != x) break;
            }
            if(k == K) return true;
        }
        
        if((j >= K-1) && (N-i >= K)){
            int k;
            for(k = 0; k < K; ++k){
                if(grid[i+k][j-k] != x) break;
            }
            if(k == K) return true;
        }
        
        if(N-i >= K){
            int k;
            for(k = 0; k < K; ++k){
                if(grid[i+k][j] != x) break;
            }
            if(k == K) return true;
        }
        
        if((N-j >= K) && (N-i >= K)){
            int k;
            for(k = 0; k < K; ++k){
                if(grid[i+k][j+k] != x) break;
            }
            if(k == K) return true;
        }
        
        return false;
    }
    
    
    
    public static void main(String[] args){
        int num_of_files;
        int N;
        int K;
        counter = 1;
        try{
            Scanner s = new Scanner(new FileInputStream(new File(filename)));
            num_of_files = s.nextInt();
            for(int i = 0; i < num_of_files; ++i){
                N = s.nextInt();
                K = s.nextInt();
                char[][] rot_grid = rotater(N, s);
                checkSolutions(N, K, rot_grid);
                
                
                
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }
        
        
   }
    
    
    
    
}