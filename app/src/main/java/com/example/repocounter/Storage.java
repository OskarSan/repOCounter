package com.example.repocounter;

import android.content.Context;

import com.example.repocounter.exercisePackage.Exercise;
import com.example.repocounter.exercisePackage.ExerciseType;
import com.example.repocounter.statisticsPackage.WorkoutLogEntry;
import com.example.repocounter.workoutsPackage.Workout;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Storage {

    private static Storage storage = null;
    public static Storage getInstance(){
        if(storage == null){
            storage = new Storage();
            /*
            storage.addExercise(new Exercise(ExerciseType.PUSH, "pena", "pena", 10, 10 ));
            storage.addExercise(new Exercise(ExerciseType.PULL, "vetoo", "vetoilu", 10, 10 ));
            storage.addExercise(new Exercise(ExerciseType.LEGS, "kykky", "kykky", 10, 10 ));
            storage.addExercise(new Exercise(ExerciseType.PUSH, "vinopena", "vinoilupena", 10, 10 ));

            storage.addWorkout(new Workout("test", storage.getExerciseArrayList()));
            storage.addWorkout(new Workout("test2", storage.getExerciseArrayList()));
            */

        }
        return storage;
    }

    ArrayList<Exercise> exerciseArrayList = new ArrayList<>();
    ArrayList<Workout> workoutArrayList = new ArrayList<>();
    HashMap<LocalDateTime, WorkoutLogEntry> workoutLog = new HashMap<>();

    Exercise exerciseCarrier = null;
    Workout workoutCarrier = null;

    public void setExerciseCarrier(Exercise ex){
        this.exerciseCarrier = ex;
    }

    public void setWorkoutCarrier(Workout workout){
        this.workoutCarrier = workout;
    }

    public Exercise getExerciseCarrier(){
        return exerciseCarrier;
    }

    public Workout getWorkoutCarrier(){
        return workoutCarrier;
    }


    private Integer exerciseID = 0, workoutID = 0;

    public void addExercise(Exercise exercise){
        exerciseArrayList.add(exercise);

    }

    public void addWorkout(Workout workout){
        workoutArrayList.add(workout);

    }

    public void addWorkoutLogEntry(WorkoutLogEntry workoutLogEntry){

        workoutLog.put(workoutLogEntry.getDate(), workoutLogEntry);
        System.out.println(workoutLog.size());
    }

    public ArrayList<Exercise> getExerciseArrayList(){
        System.out.println(exerciseArrayList.size());
        return exerciseArrayList;
    }
    public ArrayList<Workout> getWorkoutArrayList(){
        return workoutArrayList;
    }

    public HashMap<LocalDateTime, WorkoutLogEntry> getWorkoutLog(){return workoutLog;}


    public Exercise findExerciseById(String id){
        for(Exercise exercise : exerciseArrayList){
            if(exercise.getExerciseID().equals(id)){
                return exercise;
            }
        }
        return null;
    }

    public void editExercise(Exercise newExercise, String oldID) {
        Iterator<Exercise> iterator = exerciseArrayList.iterator();
        while (iterator.hasNext()) {
            Exercise ex = iterator.next();
            if (ex.getExerciseID().equals(oldID)) {
                iterator.remove(); // Use iterator.remove() to safely remove the element
                newExercise.setExerciseID(oldID);
                exerciseArrayList.add(newExercise);
                break; // Exit the loop after replacing the exercise
            }
        }


    }

    public void editWorkout(Workout newWorkout, String oldID){
        Iterator<Workout> iterator = workoutArrayList.iterator();
        while (iterator.hasNext()) {
            Workout workout = iterator.next();
            if (workout.getWorkoutID().equals(oldID)){
                iterator.remove();
                newWorkout.setWorkoutID(oldID);
                workoutArrayList.add(newWorkout);
                break;
            }
        }
    }



    public void sortExercisesByType(){
        exerciseArrayList.sort((o1, o2) -> o1.getExerciseType().compareTo(o2.getExerciseType()));
    }
    public void sortWorkoutsByName(){
        workoutArrayList.sort((o1, o2) -> o1.getWorkoutName().compareTo(o2.getWorkoutName()));
    }
    public void saveExercisesToFile(Context context){
        try {
            ObjectOutputStream exerciseWriter = new ObjectOutputStream(context.openFileOutput("exercises.ser", Context.MODE_PRIVATE));
            System.out.println("savinge ex");
            exerciseWriter.writeObject(exerciseArrayList);
            exerciseWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadExercisesFromFile(Context context){
        try {
            ObjectInputStream exerciseReader = new ObjectInputStream(context.openFileInput("exercises.ser"));
            System.out.println("exercies loaded");
            exerciseArrayList = (ArrayList<Exercise>) exerciseReader.readObject();
            exerciseReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveWorkoutsToFile(Context context){
        try {
            ObjectOutputStream workoutWriter = new ObjectOutputStream(context.openFileOutput("workouts.ser", Context.MODE_PRIVATE));
            System.out.println("saving workouts");
            workoutWriter.writeObject(workoutArrayList);
            workoutWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadWorkoutsFromFile(Context context){
        try {
            ObjectInputStream workoutReader = new ObjectInputStream(context.openFileInput("workouts.ser"));
            System.out.println("Workouts loaded");
            workoutArrayList = (ArrayList<Workout>) workoutReader.readObject();
            workoutReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveWorkoutLogToFile(Context context){
        try {
            ObjectOutputStream workoutLogWriter = new ObjectOutputStream(context.openFileOutput("workoutLog.ser", Context.MODE_PRIVATE));
            System.out.println("saving log");
            workoutLogWriter.writeObject(workoutLog);
            workoutLogWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void loadWorkoutLogFromFile(Context context){
        try {
            ObjectInputStream workoutLogReader = new ObjectInputStream(context.openFileInput("workoutLog.ser"));
            System.out.println("log loaded");
            workoutLog = (HashMap<LocalDateTime, WorkoutLogEntry>) workoutLogReader.readObject();
            workoutLogReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
