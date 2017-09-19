package ru.spaceinvasion;

public class RestJsonAnswer {
    private final String result;
    private final String description;

    @SuppressWarnings("unused")
    public RestJsonAnswer(String result, String description) {
        this.result = result;
        this.description = description;
    }

    @SuppressWarnings("unused")
    public String getResult() {
        return result;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }
}
