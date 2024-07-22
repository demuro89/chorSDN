package main.local_code;

import choreography.Result;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PipeReader {
    public static Result main(String name_pipe) throws IOException {
        String pipePath = name_pipe;

        try (BufferedReader reader = new BufferedReader(new FileReader(pipePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Received from 1 Model: " + line);
                return Result.valueOf(line);
            }
        }
        return Result.UNDECIDED;
    }
}