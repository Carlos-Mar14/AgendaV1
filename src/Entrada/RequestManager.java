package Entrada;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestManager { private String filePath;

    public RequestManager(String filePath) {
        this.filePath = filePath;
    }

    public List<Reserva> readRequests() throws IOException {
        List<Reserva> requests = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                if (data.length == 6) {
                    requests.add(new Reserva(data[0], data[1], data[2], data[3], data[4], data[5]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }
        return requests;
    }
}
