package com.TsuberaKristian.database;

public class Exercises {
    private int exercisesID;
    private String exercisesTask;
    private double exercisesAnswer ;

    public Exercises() {

    }

    public int getExercisesID() {
        return exercisesID;
    }

    public void setExercisesID(int exercisesID) {
        this.exercisesID = exercisesID;
    }

    public String getExercisesTask() {
        return exercisesTask;
    }

    public void setExercisesTask(String exercisesTask) {
        this.exercisesTask = exercisesTask;
    }

    public double getExercisesAnswer() {
        return exercisesAnswer;
    }

    public void setExercisesAnswer(double exercisesAnswer) {
        this.exercisesAnswer = exercisesAnswer;
    }

    @Override
    public String toString() {
        return "Exercises{" +
                "exercisesID=" + exercisesID +
                ", exercisesTask='" + exercisesTask + '\'' +
                ", exercisesAnswer=" + exercisesAnswer +
                '}';
    }

}

