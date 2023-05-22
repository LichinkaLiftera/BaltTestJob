package function;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
public class FileRecorder {
    private List<List<String>> result;
    public FileRecorder(List<List<String>> result){
        this.result = result;
    }
    public void writeInFile(){
        try(FileWriter fileWriter = new FileWriter("GroupingResult.txt", false)){
            fileWriter.write("Количество групп = " + result.size() + "\n"+ "\n");

            for(int i = 0 ; i < result.size() ; i++){
                fileWriter.write("Группа " + (i+1) + "\n");
                for(int x = 0 ; x < result.get(i).size() ; x++){
                    fileWriter.write(result.get(i).get(x) + "\n");
                }
                fileWriter.write("\n");
            }
        }catch (IOException e){
            throw new RuntimeException();
        }
    }
}
