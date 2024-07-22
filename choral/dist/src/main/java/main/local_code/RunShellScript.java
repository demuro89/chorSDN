package main.local_code;
import java.io.IOException;


public class RunShellScript {
    public static void main(String script_exec) {
        try {
            // Path to your shell script
            String scriptPath = script_exec;

            // Create ProcessBuilder for the shell script

            // Start the process
            ProcessBuilder pb = new ProcessBuilder(scriptPath);

            // Start the process asynchronously
            pb.start();

            // Continue with the program execution
            System.out.println("Shell script started. Program continues.");
            // Add your program logic here
        } catch (IOException e) {
            e.printStackTrace();
        }
}}
