import java.io.*;

public class PipeReader {
    public static void main(String[] args) throws IOException {
        String pipePath = "/tmp/test";

        try (BufferedReader reader = new BufferedReader(new FileReader(pipePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Received from Python: " + line);
            }
        }
    }
}
