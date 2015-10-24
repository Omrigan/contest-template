import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by oleg on 10/18/15.
 */
public class ShortTemplate {

    public static void main(String[] argc) throws Exception{
        boolean home = argc.length>0 && argc[0].equals("test");
        String filename = ShortTemplate.class.toString().toLowerCase();
        InputReader in;
        PrintWriter out;
        if(home){
            in = new InputReader(new BufferedReader(new FileReader(filename + ".in")));
            out = new PrintWriter(new FileWriter(filename + ".out"));
            do {
                Task t = new Task();
                t.in = in;
                t.out = out;
                t.run();
                out.println();
                out.flush();

            } while (in.hasNext());
        } else {
            in = new InputReader(new BufferedReader(new InputStreamReader(System.in)));
            out = new PrintWriter(new OutputStreamWriter(System.out));
            Task t = new Task();
            t.in = in;
            t.out = out;
            t.run();
        }
        out.close();
    }

}
class Task {
    InputReader in;
    PrintWriter out;

    void run(){
        out.write(in.nextInt() + in.nextInt());
    }
}
class InputReader {
    BufferedReader buf;
    String nextLine;
    StringTokenizer token;

    InputReader(BufferedReader _buf){
        buf = _buf;
        try {
            nextLine = buf.readLine();
        } catch (Exception e){

        }
        token = new StringTokenizer("");
    }
    String nextLine(){
        String oldLine = nextLine;
        try {
            nextLine = buf.readLine();
        } catch (Exception e){

        }
        return oldLine;
    }
    boolean hasNext(){
        return token.hasMoreTokens() || nextLine!=null;
    }
    String next(){
        if(!token.hasMoreTokens())
            token = new StringTokenizer(nextLine());
        return token.nextToken();
    }
    int nextInt(){
        return Integer.valueOf(next()).intValue();
    }


}


