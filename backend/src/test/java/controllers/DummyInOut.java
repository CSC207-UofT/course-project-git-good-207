package controllers;

import java.util.ArrayList;

/**
 * DummyInOut with String input and output.
 * For testing purposes only
 */
public class DummyInOut implements InOut {
    private final ArrayList<String> inputs = new ArrayList<>();
    private final ArrayList<String> outputs = new ArrayList<>();

    @Override
    public String getInput(String prompt) {
        String input = this.inputs.get(0);
        inputs.remove(0);
        return input;
    }

    @Override
    public void setOutput(String output) {
        this.outputs.add(output);
    }

    /**
     * Clears all previous input and sets the Strings in the
     * given ArrayList as input
     * @param input ArrayList of Strings representing the input
     */
    public void setInput(ArrayList<String> input) {
        this.inputs.clear();
        this.inputs.addAll(input);
    }

    /**
     * Returns all the collected outputs
     * @return ArrayList of Strings representing the output
     */
    public ArrayList<String> getOutputs() {
        return outputs;
    }

    /**
     * Clears all output
     */
    public void clearOutputs() { outputs.clear(); }
}
