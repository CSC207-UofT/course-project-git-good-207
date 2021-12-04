package controllers;

import java.util.ArrayList;

public class DummyInOut implements InOut {
    private final ArrayList<String> inputs = new ArrayList<>();
    private final ArrayList<String> outputs = new ArrayList<>();

    @Override
    public String getInput(String prompt){
        return this.inputs.get(0);
    }

    @Override
    public void setOutput(String output) {
        this.outputs.add(output);
    }

    public void setInput(ArrayList<String> input){
        this.inputs.clear();
        this.inputs.addAll(input);
    }

    public ArrayList<String> getOutputs(){
        return outputs;
    }
}