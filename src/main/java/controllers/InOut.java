package controllers;

import java.io.IOException;

public interface InOut {
    String getInput(String prompt) throws IOException;
    void setOutput(String output);
}
