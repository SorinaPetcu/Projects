package Utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteOutput {

    private String outputPath;

    public WriteOutput(String outputPath ){
        this.outputPath = outputPath;
    }


    public void writeToFile(String message){
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(this.outputPath, true)  //Set true for append mode
            );
            if (message.equals("")){
            writer.newLine();   //Add new line
            }else {
                writer.write(message);
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteFile(){
        File file = new File(this.outputPath);
        file.delete();
    }




}
