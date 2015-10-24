import java.io.*;
import java.util.*;


public class CF2E {
    public static String filename = "CF2E".toLowerCase();
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
            switch (CF2E.read) {
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

    boolean equal(int[] arr1, int[] arr2, int n)
    {
        for(int i = 0;i < n; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public void run() {
        int n = in.nextInt();
        int[] arr1 = new int[n],
                coors1 = new int[n],
                arr2 = new int[n],
                coors2 = new int[n];
        for(int i = 0;i < n; i++)
        {
            int a = in.nextInt()-1;
            arr1[i] = a;
            coors1[a] = i;

        }
        for(int i = 0;i < n; i++)
        {
            int a = in.nextInt()-1;
            arr2[i] = a;
            coors2[a] = i;

        }
        int ans = 0;
        ArrayList<String> queue = new ArrayList<>();
       /* int cur = n-1;
        while(cur>0){
            if(arr2[cur]==arr1[cur]){
                cur--;
            }else{
                int c1 = coors1[arr2[cur]];
                int a = arr1[cur];
                arr1[cur] = arr2[cur];
                arr1[c1] = a;
                coors1[a] = c1;
                ans += cur-c1;
                queue.add((cur+1) + " " + (c1+1));
            }
        }*/
        while (!equal(arr1, arr2, n)){
            boolean flag = true;
            for(int i = 0;(i < n&&flag); i++)
            {

                for(int j = i;(j < n&&flag); j++)
                {
                    if(i==2&&j==3)
                        out.debug("LOl");
                    if(i!=j){
                        if(coors2[arr1[i]]>=j && coors2[arr1[j]]<=i){
                            flag = false;
                            int a = arr1[j];
                            arr1[j] = arr1[i];
                            arr1[i] = a;
                            coors1[a] = i;
                            ans += j-i;
                            queue.add((j+1) + " " + (i+1));
                        }
                    }
                }
            }
        }
        out.writeln(ans);
        out.writeln(queue.size());
        for(String s : queue){
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
