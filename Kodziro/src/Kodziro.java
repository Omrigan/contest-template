import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;



public class Kodziro {
    public static String filename = Kodziro.class.getCanonicalName().toLowerCase();
    public static int read = 2; //0 - .in/.out; 1 - input.txt/output.txt; 2 - stdin/stdout

    public static void main(String[] argv) throws IOException {
        Task t;
        boolean home = argv.length > 0 && argv[0].equals("oleg");
        InputStream inputStream;
        OutputStream outputStream;
        if (home) {
            inputStream = new FileInputStream(filename + ".in");
            outputStream = new FileOutputStream(filename + ".out");
        } else {
            switch (Kodziro.read) {
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
            } while (in.hasNext());
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


    public void run() {
        final int boost = (int) Math.pow(10,9);
        int e = in.nextInt(), s = in.nextInt(), n = in.nextInt(), m = in.nextInt();
        int[][] benz = new int[n+1][5];
                //dp = new int[2][n];
        int[] starts = new int[m];
        for(int i =0 ;i <n;i++){
            benz[i][1] = in.nextInt();
            benz[i][0] = in.nextInt();
        }
        benz[n][0] = Integer.MAX_VALUE;
        for(int i =0 ;i <m;i++){
            starts[i] = in.nextInt();
        }
        Arrays.sort(benz, (o1, o2) -> Integer.compare(o1[0], o2[0]));
        boolean cutted = false;
        for(int i =n-1;i>=0;i--){
            if(cutted){
                int dist = benz[i+1][0]-benz[i][0];
                if(benz[i+1][2]==-1||dist>s){
                    benz[i][2] = -1;
                    benz[i][3] = -1;
                    benz[i][4] = -1;
                }
                int ahead = i+1;
                int promotedmin = 0;
                benz[i][2] = benz[i+1][2];
                benz[i][3] = benz[i+1][3];
                benz[i][4] = benz[i+1][4];
                benz[i][benz[i][1]+1]+=dist;
                while (ahead<n&&benz[ahead][1]>promotedmin&& benz[ahead][0]-benz[i][0]<=s){
                    if(benz[ahead][1]<benz[i][1]){
                        int prom = s-(benz[ahead][0]-benz[i][0]);
                        benz[i][benz[ahead][1]+1]-=prom;
                        benz[i][benz[i][1]+1]+=prom;
                        promotedmin = benz[ahead][1];
                    }
                    ahead++;
                }

            }
            else {
                if(benz[i][0]<e){
                    cutted = true;
                    benz[i + 1][0] = e;
                    benz[i + 1][1] = 0;
                    benz[i + 1][2] = 0;
                    benz[i + 1][3] = 0;
                    i++;
                }
            }
        }
        for(int i = 0; i<m;i++)
        {
            //starts[i]
        }

        out.write("Lol");


    }


}

class Pair<T1, T2> implements Comparable<Pair<T1, T2>>{
    T1 left;
    T2 right;
    Pair (T1 _left, T2 _right){
        left = _left;
        right = _right;
    }


    @Override
    public int compareTo(Pair o) {
        return ((Comparable) left).compareTo(o.left);
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
        tokenizer = null;
        reader = new BufferedReader(new InputStreamReader(stream));
        nextLine = reader.readLine();
    }

    public boolean hasNext() {
        return tokenizer.hasMoreTokens() || (nextLine != null);
    }


    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {

                tokenizer = new StringTokenizer(nextLine);
                nextLine = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException();
            }
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

        tokenizer = new StringTokenizer("");
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
