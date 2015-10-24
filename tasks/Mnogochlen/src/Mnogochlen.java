import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Mnogochlen{

    public static void main(String[] argv) throws IOException
    {
        Task t;
        boolean home = argv.length > 0 && argv[0].equals("test");
        InputStream inputStream;
        OutputStream outputStream;
        if (home) {
            inputStream = new FileInputStream(Task.filename + ".in");
            outputStream = new FileOutputStream(Task.filename + ".out");
        } else {
            switch (Task.read) {
                case 0:
                    inputStream = new FileInputStream(Task.filename + ".in");
                    outputStream = new FileOutputStream(Task.filename + ".out");
                    break;
                case 1:
                    inputStream = new FileInputStream("input.txt");
                    outputStream = new FileOutputStream("output.txt");
                    break;
                default:
                    inputStream = System.in;
                    outputStream = System.out;
                    break;

            }

        }
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream, home);


        if (home)
            do {
                long time = System.currentTimeMillis();
                t = new Task();
                t.in = in;
                t.out = out;
                t.run();

                out.writeln();
                out.writeln("=====Time:" + (System.currentTimeMillis() - time));
                out.flush();
            } while (in.toNextTest());
        else {
            t = new Task();
            t.in = in;
            t.out = out;
            t.run();
        }

        out.close();

    }

}
class Single {

    double pow;

}


class MultConstructException extends Exception {

}

class Mult {
    TreeMap<Double, Double> map = new TreeMap<Double, Double>();

    Mult (){

    }

    Mult (String s){

    }
    int count(){
        return map.size();
    }
    double eval(double x){
        double ans = 0;
        for(Map.Entry<Double, Double> e : map.entrySet()){
            ans += e.getValue() * Math.pow(x, e.getKey());
        }
        return ans;
    }

    Mult add(Mult m){
        Mult ans = new Mult();
        for(Map.Entry<Double, Double> e : m.map.entrySet()){
            double val = map.getOrDefault(e.getKey(), 0d) + e.getValue();
            if(val!=0d)
                ans.map.put(e.getKey(), val);
        }
        return ans;
    }
    Mult substract(Mult m){
        Mult ans = new Mult();
        for(Map.Entry<Double, Double> e : m.map.entrySet()){
            double val = map.getOrDefault(e.getKey(), 0d) - e.getValue();
            if(val!=0d)
                ans.map.put(e.getKey(), val);
        }
        return ans;
    }
    Mult multiply(Mult m){
        Mult ans = new Mult();
        for(Map.Entry<Double, Double> e : m.map.entrySet()){
            double val = map.getOrDefault(e.getKey(), 0d) - e.getValue();
            if(val!=0d)
                ans.map.put(e.getKey(), val);
        }
        return ans;
    }

}

class Task {
    public OutputWriter out;
    public InputReader in;

    public static final String filename = "Mnogochlen".toLowerCase();
    public static final int read = 0; //0 - .in/.out; 1 - input.txt/output.txt; 2 - stdin/stdout

    ArrayList<Mult> lst;
    public void run() {
        int N = in.nextInt();
        for(int i = 0; i<N;i++){
            lst.add(new Mult(in.next()));
        }

    }


}


/*
===Readers===
 */
class InputReader {

    BufferedReader reader;
    StringTokenizer tokenizer;
    String nextLine;


    public InputReader(InputStream stream) throws IOException {
        reader = new BufferedReader(new InputStreamReader(stream));
        tokenizer = new StringTokenizer("");
        nextLine = reader.readLine();
    }

    public boolean hasNext() {
        while (!tokenizer.hasMoreTokens() && nextLine!=null)
            tokenizer = new StringTokenizer(nextLine());
        return tokenizer.hasMoreTokens() || nextLine!=null;
    }
    public boolean toNextTest(){
        while (hasNext() && !next().equals("=")){}
        return  hasNext();
    }


    public String next() {
        while (!tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(nextLine());

        }

        return tokenizer.nextToken();
    }

    public String nextLine() {
        String oldLine = nextLine;

        try {
            nextLine = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return oldLine;
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }

    public int[] nextIntArray(int n) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++)
            res[i] = nextInt();

        return res;
    }

}

class OutputWriter {

    PrintWriter out;


    boolean home;

    public OutputWriter(OutputStream stream, boolean _home) {
        out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(stream)));
        home = _home;
    }

    public void write(Object... o) {
        for (Object cur : o)
            out.print(cur);

    }

    public void debug(Object... o) {
        if (home) {

            out.print("==DEBUG:");
            for (Object cur : o)
                out.print(" " + cur);
            out.println();
        }
    }

    public void writeln(Object... o) {
        write(o);
        out.println();
    }

    public void flush() {
        out.flush();
    }

    public void close() {
        out.close();
    }

}
