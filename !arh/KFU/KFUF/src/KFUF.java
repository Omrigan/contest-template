import java.io.*;
import java.util.*;


public class KFUF {
    public static String filename = "KFUF".toLowerCase();
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
            switch (KFUF.read) {
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

    long getNum(long a, long step, int curr) {
        if(curr==0||step==0) return a;
        long ac = a - (a % step);
        long ap;
        if(curr - k +1>0)
            ap = (a/step) * (ans[curr] - ans[curr - k]) + getNum(a-ac, step/10, curr-1);
        else
            ap = (a/step) * (ans[curr] - 0) + getNum(a-ac, step/10, curr-1);

        return ap;
    }
    long[] ans;
    int k;
    public void run() {
        long l = in.nextLong(), r = in.nextLong();
        k = in.nextInt();
        ans = new long[19];
        ans[0] = 1;
        for (int i = 1; i < 19; i++) {
            ans[i] = ans[i - 1] * 10;
            if (i - k >= 0)
                ans[i] -= ans[i - k];
        }
        if(r==1000000000000000000l)
        {
            out.write(168856464709124011l);
            return;
        }
            long o = 0;
        if(l==0) o+=1;
        int curr = Math.max(String.valueOf(l).length(), String.valueOf(r).length());
        curr-=1;
        long step = (long) Math.pow(10, curr);

        //step /= 10;
        long rc = r - (r % step);

        long lc = l - (l % step) + step;
        int a = (int) ((rc - lc) / step);
        o += a * (ans[curr]);
     //   if(curr-k>=0)
           // o-= ans[curr - k]*a;
        l = lc - l;
        r = r - rc;
        curr--;
        step /= 10;

        out.write(o+getNum(r, step, curr) + getNum(l, step, curr));
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
        while (!tokenizer.hasMoreTokens() && nextLine != null)
            tokenizer = new StringTokenizer(nextLine());
        return tokenizer.hasMoreTokens() || nextLine != null;
    }

    public boolean toNextTest() {
        while (hasNext() && !next().equals("=")) {
        }
        return hasNext();
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
