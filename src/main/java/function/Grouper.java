package function;
import entity.WordEntity;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
public class Grouper {
    private File file;
    private List<String> stringList; //Список строк
    private Map<Integer, Integer> unionGroup = new HashMap<>(); //Мапа групп которые надо объединить
    private List<Map<String, Integer>> posStrGroup = new ArrayList<>(); //Сервисный список для группировки позиция в строке(строка, номер блока строки)
    private List<List<String>> result = new ArrayList<>(); //Результирующий список группа-список строк
    public Grouper(File file) {
        this.file = file;
    }
    public List<List<String>> getResult() {
        return result;
    }
    public void separator() {

        try{
            stringList = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException();
        }
        for (String str : stringList) {

            TreeSet<Integer> crossGroup = new TreeSet<>();   //Сет групп с совпадающими элементами
            List<WordEntity> newBlock = new ArrayList<>();//Список элементов не входящих в столбцы

            String[] stringsInLine = str.split(";");  //Массив строк из линии

            for (int i = 0; i < stringsInLine.length; i++) {

                String tempStr = stringsInLine[i];

                if (posStrGroup.size() == i) {
                    posStrGroup.add(new HashMap<>());
                }
                if (tempStr.equals("")) {
                    continue;
                }

                Map<String, Integer> column = posStrGroup.get(i);
                Integer elGrNum = column.get(tempStr);

                if (elGrNum != null) { //Если группа с этим номером объединена с другой - сохраняем номер группы с которой объедина
                    while (unionGroup.containsKey(elGrNum)) { //<-!!!!!
                        elGrNum = unionGroup.get(elGrNum);
                    }  // <-!!!!!
                        crossGroup.add(elGrNum);
                } else {
                    newBlock.add(new WordEntity(tempStr, i));
                }
            }

            Integer groupNumber;

            if (crossGroup.isEmpty()) {  //Инициализация номера группы в обработку
                groupNumber = result.size();

                result.add(new ArrayList<>());
            } else {
                groupNumber = crossGroup.first();
            }
            for (WordEntity entity : newBlock) {
                posStrGroup.get(entity.index).put(entity.value, groupNumber);
            }
            for (Integer grNumber : crossGroup) {    //Перебор групп имеющих такой элемент
                if (!Objects.equals(grNumber, groupNumber)) {
                    unionGroup.put(grNumber, groupNumber); //Вносим группы на объединение
                    result.get(groupNumber).addAll(result.get(grNumber)); //Объединение
                    result.set(grNumber, null); //Обнуляем текущую группу
                }
            }
            result.get(groupNumber).add(str);
        }
        result.removeAll(Collections.singleton(null)); // Удаляем листы размером <2 эл-ов
        for(int y = result.size() - 1 ; y != 0 ; y--){
            if(result.get(y).size() < 2){
                result.remove(y);
            }
        }
    }
}
