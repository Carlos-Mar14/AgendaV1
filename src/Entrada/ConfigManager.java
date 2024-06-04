package Entrada;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigManager {
    private final String filePath;

    public ConfigManager(String filePath) {
        this.filePath = filePath;
    }

    public Configuration readConfiguration() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] data = new String[4];
            int index = 0;
            while ((line = br.readLine()) != null && index < 4) {
                data[index] = line;
                index++;
            }
            return new Configuration(data[0], data[1], data[2], data[3]);
        }
    }
}