import java.io.*;
import java.util.*;


public class Lscext{

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

    public static final String filename = "Lscext";
    public static final int read = 1; //0 - .in/.out; 1 - input.txt/output.txt; 2 - stdin/stdout

    public void run() {
        int N  = in.nextInt(), M;
        int[] arr = new int[N], arr2;
        for(int i = 0; i< N;i++)
            arr[i] = in.nextInt();
        M = in.nextInt();
        arr2 = new int[M];
        for(int i = 0; i< M;i++)
            arr2[i] = in.nextInt();
        int[][][] ans = new int[N][M][3];
        ans[0][0][0] = arr[0]==arr2[0]?1:0;
        ans[0][0][1] = arr[0]==arr2[0]?0:5;
        for(int i = 1; i< N;i++) {
            ans[i][0][0] = Math.max(ans[i - 1][0][0], arr[i] == arr2[0] ? 1 : 0);
            ans[i][0][1] = ans[i][0][0] > ans[i-1][0][0]?0:1;
        }
        for(int i = 1; i< M;i++) {
            ans[0][i][0] = Math.max(ans[0][i - 1][0], arr[0] == arr2[i] ? 1 : 0);
            ans[0][i][1] = ans[0][i][0] > ans[0][i-1][0]?0:2;
        }for(int i = 1; i< N;i++)
            for(int j = 1; j< M;j++){
                if(arr[i]==arr2[j] && ans[i-1][j-1][0] + 1>Math.max(ans[i-1][j][0],
                        ans[i][j-1][0])){
                    ans[i][j][0] = ans[i-1][j-1][0] + 1;
                    ans[i][j][1] = 0;
                }
                else {
                    if(ans[i-1][j][0]>ans[i][j-1][0])
                    {
                        ans[i][j][0]=ans[i-1][j][0];
                        ans[i][j][1] = 1;
                    }else {
                        ans[i][j][0]=ans[i][j-1][0];
                        ans[i][j][1] = 2;
                    }
                }
            }
        //out.writeln(ans[N - 1][M - 1][0]);
        boolean[] ext = new boolean[N];
        int curi = N-1, curj = M-1;
        boolean flag = false;
        while (curi>=0 && curj>=0){
            switch (ans[curi][curj][1]){
                case 0:
                    ext[curi] = true;
                    curi--;
                    curj--;

                    break;
                case 1:
                    curi--;
                break;
                case 2:
                    curj--;
                    break;
                default:
                    curi--;
                    curj--;
                    break;

            }
        }
        for(int i = 0; i< N;i++)
            if(ext[i])
                out.write(arr[i] + " ");

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
