import java.io.*;
import java.util.*;


public class KFUD {
    public static String filename = "KFUD".toLowerCase();
    public static int read = 2; //0 - .in/.out; 1 - input.txt/output.txt; 2 - stdin/stdout

    public static void main(String[] argv) throws IOException {
        Task t;
        boolean home = argv.length > 0 && argv[0].equals("test");
        InputStream inputStream;
        OutputStream outputStream;
        if (home) {
            inputStream = new FileInputStream(filename + ".in");
            outputStream = new FileOutputStream(filename + ".out");
        } else {
            switch (KFUD.read) {
                case 0:
                    inputStream = new FileInputStream(filename + ".in");
                    outputStream = new FileOutputStream(filename + ".out");
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
class Task {
    public OutputWriter out;
    public InputReader in;

    boolean[][] read(){
        boolean[][] ans = new boolean[n][m];
        for(int  i =0; i<n ;i++){
//            char[] arr = in.next().toCharArray();
            for(int  j =0; j<m ;j++)
                if(in.nextInt() == 1)
                    ans[i][j] = true;
        }
        return ans;
    }

    boolean start(){
        for(int i = 0;i<n; i++){
            boolean[] blocked2 = new boolean[m];
            boolean flag = false;
            for(int  j =0; j<m ;j++){
                if(first[i][j]!=sec[i][j]^blocked[j]){

                        flag = true;

                }
            }
            if(flag){
                deq.add("0 " + (i+1));
                flag = false;
                blocked2 = new boolean[m];
                for(int  j =0; j<m ;j++){
                    if(first[i][j]==sec[i][j]^blocked[j]){

                            flag = true;

                    }
                }
            }
            if(flag){
                return true;
            } else{
                for(int  j =0; j<m ;j++)
                    if(blocked2[j])
                        blocked[j] = true;
            }
        }
        return false;
    }

    int n, m;
    boolean[] blocked;
    boolean[][] first, sec;
    Deque<String> deq;
    public void run() {
        n = in.nextInt();
        m = in.nextInt();
        first = read();
        sec = read();

        blocked = new boolean[m];
        deq = new ArrayDeque<String>();
        for(int  j =0; j<m ;j++){
            blocked[j] = first[0][j]!=sec[0][j];
            if(blocked[j])
                deq.add("1 " + (j+1));
        }

        if(start()){
            blocked = new boolean[m];
            deq = new ArrayDeque<String>();
            deq.add("0 1");
            for(int  j =0; j<m ;j++){
                blocked[j] = first==sec;
                if(blocked[j])
                    deq.add("1 " + (j+1));
            }
            if(start()||n+m<=deq.size())
            {
                out.write(-1);
                return;
            }
        }

        out.writeln(deq.size());
        for(String s : deq){
            out.writeln(s);
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
