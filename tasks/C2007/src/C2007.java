import java.io.*;
import java.util.*;


public class C2007{

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


class Task {
    public OutputWriter out;
    public InputReader in;

    public static final String filename = "C2007";
    public static final int read = 2; //0 - .in/.out; 1 - input.txt/output.txt; 2 - stdin/stdout

    public void run() {
        int n = in.nextInt();
        int[] mas = new int[n];
        for(int i=0;i<n;i++)
            mas[i] = in.nextInt();
        double[] dp = new double[n+5];
        boolean[] isusemax = new boolean[n+5];
        dp[0]=0; isusemax[0]=false;
        dp[1]=0; isusemax[1]=false;
        dp[2]=mas[1]; isusemax[2]=true;
        for(int i=3;i<=n;i++)
        {
            double help = dp[i-1] + (double)mas[i-1]/mas[i-2];
            double help2 = dp[i-2] + (double)mas[i-1]+1/mas[i-3];
            if(help>=help2)isusemax[i]=true;
            else
            {
                isusemax[i-2]=true;
                isusemax[i-1]=false;
                isusemax[i]=true;
            }
            dp[i]=Math.max(help,help2);
        }
        out.writeln(dp[n]);
        for(int i=1;i<=n;i++)
        {
            if(isusemax[i]) out.write(mas[i-1] + " ");
            else out.write("1 ");
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
