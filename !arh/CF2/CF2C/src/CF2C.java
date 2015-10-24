import java.io.*;
import java.util.*;


public class CF2C {
    public static String filename = "CF2C".toLowerCase();
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
            switch (CF2C.read) {
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

    void out1(char[] ans){
        for (int i = 0; i < n ; i++){
            if(ans[i]==0){
                ans[i] = 'a';
                while (ans[i]==str1[i]||ans[i]==str2[i])
                    ans[i]++;
            }
            out.write(ans[i]);
        }
    }
    char[] str1 ,str2;
    int n;
    public void run() {
        n = in.nextInt();
        int t = in.nextInt(),
                q = n - t;
        str1 = in.next().toCharArray();
        str2 = in.next().toCharArray();
                char[] ans = new char[n];
        for (int i = 0; (i < n && q > 0); i++) {
            if (str1[i] == str2[i]) {
                q--;
                ans[i] = str1[i];
            }
        }
        if (q == 0) {
            out1(ans);
            return;
        }
        int i, qp = q;
        for (i = 0; (i < n && q > 0); i++) {
            if(ans[i]==0){
                q--;
                ans[i] = str1[i];
            }
        }
        q = qp;
        for (i = i; (i < n && q > 0); i++) {
            if(ans[i]==0){
                q--;
                ans[i] = str2[i];
            }
        }
        if(q>0)
        {
            out.write(-1);
        }else{
            out1(ans);
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
