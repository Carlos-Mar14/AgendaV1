package Entrada;

public class Configuration {
    private String year;
    private String month;
    private String inputLanguage;
    private String outputLanguage;

    public Configuration(String year, String month, String inputLanguage, String outputLanguage) {
        this.year = year;
        this.month = month;
        this.inputLanguage = inputLanguage;
        this.outputLanguage = outputLanguage;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getInputLanguage() {
        return inputLanguage;
    }

    public String getOutputLanguage() {
        return outputLanguage;
    }
}
