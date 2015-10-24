import java.io.*;
import java.nio.Buffer;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class dzfileC {
    public static String filename = "dzfileC".toLowerCase();
    public static int read = 0; //0 - .in/.out; 1 - input.txt/output.txt; 2 - stdin/stdout

    public static void main(String[] argv) throws Exception {
        Task t;
        boolean home = argv.length > 0 && argv[0].equals("test");
        InputStream inputStream;
        OutputStream outputStream;
        if (home) {
            inputStream = new FileInputStream(filename + ".in");
            outputStream = new FileOutputStream(filename + ".out");
        } else {
            switch (dzfileC.read) {
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

class Master {
    HashMap<String, Integer> map = new HashMap<>();
    String name;
}
class Case implements Comparable<Case>{
    Integer endtime;
    Master master;
    String name;
    public int compareTo(Case c){
        if (endtime>c.endtime)
            return 1;
        if (endtime<c.endtime)
            return -1;
        return 1;
    }
    String getEndTime()
    {
        return String.format("%02d", 8 + endtime / 60) + ":"  + String.format("%02d", endtime % 60);
    }
}




class Task {
    public OutputWriter out;
    public InputReader in;


    public void run() throws Exception {
        BufferedReader bfF = new BufferedReader(new FileReader("f.txt"));
        BufferedReader bfG = new BufferedReader(new FileReader("g.txt"));
        PriorityQueue<Case> queue = new PriorityQueue<>();
        String str;
        while ((str = bfG.readLine()) != null) {
            StringTokenizer token = new StringTokenizer(str);
            Master mast = new Master();
            mast.name = token.nextToken();
            while (token.hasMoreTokens()) {
                mast.map.put(token.nextToken(), Integer.valueOf(token.nextToken()));
            }
            Case c = new Case();
            c.endtime = 0;
            c.master = mast;
           // c.name = "";
            queue.add(c);

        }
        while ((str = bfF.readLine()) != null) {
            Case c = queue.poll();
            if(c.name != null)
                out.writeln(c.name, " ", c.getEndTime());
            StringTokenizer token = new StringTokenizer(str);
            c.name = token.nextToken();
            c.endtime += c.master.map.get(token.nextToken());
            queue.add(c);
        }
        while (!queue.isEmpty()){
            Case c = queue.poll();
            if(c.name != null)
                out.writeln(c.name, " ", c.master.name," ", c.getEndTime());
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

