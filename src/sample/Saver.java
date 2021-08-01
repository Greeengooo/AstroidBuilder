package sample;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Saver {
    private File file;
    private String lower_range;
    private String upper_range;
    private String step;
    private String p;


    public Saver(int lower_range ,int upper_range, int step, int p) {
        this.lower_range = Integer.toString(lower_range);
        this.upper_range = Integer.toString(upper_range);
        this.step = Integer.toString(step);
        this.p = Integer.toString(p);
    }

    public void save() {
        file = new File("Astroid.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(lower_range);
            fileWriter.write("#");
            fileWriter.write(upper_range);
            fileWriter.write("#");
            fileWriter.write(step);
            fileWriter.write("#");
            fileWriter.write(p);
            fileWriter.write("#");
            fileWriter.close();
        }catch (IOException e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }
}