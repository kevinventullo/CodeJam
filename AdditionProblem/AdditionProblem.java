import java.util.*;
import java.util.regex.*;
import java.io.*;

public class AdditionProblem{
    
    static final String filename = "C:/Users/Kevin/algs4/CodeJam/C-large-practice.in";
    static final Pattern word = Pattern.compile("[a-z]+");
    static final Pattern num = Pattern.compile("(\\-)?\\d+");
    
    
    static int count = 1;
    HashMap<String, StringOffset> mymap;
    Scanner sc;
    
    public AdditionProblem(Scanner sc){
        mymap = new HashMap<String,StringOffset>();
        this.sc = sc;
    }
    
    public class StringOffset{
        //idea is that if t -> (slope, incpt, s) then
        //t = slope*s + incpt;
        //Slope is always +/- 1;
        //We can initialize all elements t -> (1, 0, t)
        //When we meet a new pair, we iteratively apply
        //oldslope *= newslope; oldintcpt += oldslope*newintcpt;
        //to both sides, then change one.
        public double slope;
        public double incpt;
        public String s;
        
        public StringOffset(double slope, double incpt, String s){
            this.slope = slope; this.incpt = incpt; this.s = s;
        }
    }
    
    public void GO(Scanner sc){
        
        int n = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < n; ++i){
            String line = sc.nextLine();
            Matcher sm = word.matcher(line);
            Matcher nm = num.matcher(line);
            sm.find();
            String x = sm.group();
            sm.find();
            String y = sm.group();
            nm.find();
            int z = Integer.parseInt(nm.group());
            process(x, y, z);
        }
        
        for(String s : mymap.keySet()){
           
            if(s.equals(mymap.get(s).s)){
                if(mymap.get(s).slope == 1) continue;
                
                mymap.get(s).incpt = mymap.get(s).incpt/(1-mymap.get(s).slope);
                mymap.get(s).slope = 0;
            }
        }
        
        if(count == 10){
            for(String s: mymap.keySet()){
                System.out.println(s + " = " + mymap.get(s).slope + mymap.get(s).s + " + " + mymap.get(s).incpt);
            }
        }
        
        
        n = sc.nextInt();
        try{
            FileOutputStream fos = new FileOutputStream("clarge.txt", true);
            fos.write(("Case #" + count + ":\n").getBytes());
            
            
            sc.nextLine();
            
            for(int i = 0; i < n; ++i){
                String line = sc.nextLine();
                Matcher sm = word.matcher(line);
                sm.find();
                String x = sm.group();
                sm.find();
                String y = sm.group();
                
                Double z = APSolve(x, y);
                if(z!=null){
                    fos.write((x + "+" + y + "=" + (int)(double)z + "\n").getBytes());
                }
                
            }
            fos.close();
            ++count;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Double APSolve(String x, String y){
        double xslope = 1, xincpt = 0, yslope = 1, yincpt = 0;
        
        if(!mymap.containsKey(x) || !mymap.containsKey(y)) return null;
        
        while(mymap.get(x).s != x){
            xincpt += xslope*mymap.get(x).incpt;
            xslope *= mymap.get(x).slope;
            x = mymap.get(x).s;
        }
        xincpt += xslope*mymap.get(x).incpt;
        xslope *= mymap.get(x).slope;
            
        while(mymap.get(y).s != y){
            yincpt += yslope*mymap.get(y).incpt;
            yslope *= mymap.get(y).slope;
            y = mymap.get(y).s;
        }
        yincpt += yslope*mymap.get(y).incpt;
        yslope *= mymap.get(y).slope;
        
        //xslope*x + xincpt + yslope*y + yincpt = ?;
        if(xslope == 0 && yslope == 0) return xincpt + yincpt;
        if(x == y && xslope == -yslope) return xincpt + yincpt;
        return null;
        
    }
    
    
    public void process(String x, String y, double z){
        
        if(!mymap.containsKey(x)){
            
            if(x.equals(y)){
                mymap.put(x, new StringOffset(0, z/2, x));
                return;
            }
            
            mymap.put(x, new StringOffset(-1, z, y));
            if(!mymap.containsKey(y)){
                mymap.put(y, new StringOffset(1,0,y));
            }
            return;
        }
        
        if(!mymap.containsKey(y)){
            mymap.put(y, new StringOffset(-1, z, x));
            return;
        }
        
        double xslope = 1, xincpt = 0, yslope = 1, yincpt = 0;
        
        while(mymap.get(x).s != x){
            xincpt += xslope*mymap.get(x).incpt;
            xslope *= mymap.get(x).slope;
            x = mymap.get(x).s;
        }
        xincpt += xslope*mymap.get(x).incpt;
        xslope *= mymap.get(x).slope;
        
        
        
        while(mymap.get(y).s != y){
            yincpt += yslope*mymap.get(y).incpt;
            yslope *= mymap.get(y).slope;
            y = mymap.get(y).s;
        }
        yincpt += yslope*mymap.get(y).incpt;
        yslope *= mymap.get(y).slope;
        
        
        if(x==y){
            if(Math.abs(xslope) < .1) return;
            if(xslope == -yslope) return;
            mymap.put(x, new StringOffset(0, ((double)(z-xincpt-yincpt))/(2*xslope), x));
            return;
        }
        
        if(xslope==0){
            if(yslope == 0) return;
            mymap.put(y, new StringOffset(0, yslope*(z-xincpt-yincpt), y));
            return;
        }
        
        if(mymap.get(y).slope==0){
            mymap.put(x, new StringOffset(0, xslope*(z-xincpt-yincpt), x));
            return;
        }
        
        //we have xslope*x + xincpt + yslope*y + yincpt = z;
        //x = -xslope*yslope*y + xslope*(z-yincpt-xincpt)
        mymap.put(x, new StringOffset(-xslope*yslope, xslope*(z-yincpt-xincpt),y));
        return;
        
        
    }
    
    
    
    
    
    
    
    
    public static void main(String[] args){
        try{
            Scanner sc = new Scanner(new FileInputStream(new File(filename)));
            int no_of_times = sc.nextInt();
            
            for(int i = 0; i < no_of_times; ++i){
                AdditionProblem ad = new AdditionProblem(sc);
                ad.GO(sc);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}