package function;

import entity.WordEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Grouper {

    private File file;
    private List<String> stringList; //Список строк
    private Map<Integer, Integer> unionGroup = new HashMap<>(); //Мапа групп которые надо объединить
    private List<Map<String, Integer>> posStrGroup = new ArrayList<>(); //Сервисный список для группировки позиция в строке(строка, номер блока строки)
    private List<List<String>> result = new ArrayList<>(); //Результирующий список группа-список строк


    public Grouper(File file) {
        this.file = file;
    }

    public List<List<String>>getResult(){
        return result;
    }

    public void separator() {

        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(String.valueOf(file)))) {
            stringList = bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException();
        }

        for(String str : stringList){
            TreeSet<Integer> crossGroup = new TreeSet<>();   //Сет групп с совпадающими элементами
            List<WordEntity> newBlock = new ArrayList<>();//Список элементов не входящих в столбцы

            String [] stringsInLine = str.split(";");  //Массив строк из линии

            for(int i = 0 ; i < stringsInLine.length ; i++){

                String tempStr = stringsInLine[i];
                
                if(posStrGroup.size() == i){
                    posStrGroup.add(new HashMap<>());
                }

                if("".equals(tempStr.replaceAll("\"","").trim())){
                    continue;
                }

                Map<String,Integer> column = posStrGroup.get(i);
                Integer elGrNum = column.get(tempStr);

                if(elGrNum != null){ //Если группа с этим номером объединена с другой -
                                     // сохраняем номер группы с которой объедина
                    while(unionGroup.containsKey(elGrNum)){
                        elGrNum = unionGroup.get(elGrNum);
                        crossGroup.add(elGrNum);
                    }
                } else {
                    newBlock.add(new WordEntity(i,tempStr));
                }
            }

            int groupNumber;

            if(crossGroup.isEmpty()){  //Инициализация номера группы в обработку
                result.add(new ArrayList<>());
                groupNumber = result.size() - 1;
            } else {
                groupNumber = crossGroup.first();
            }
            for (WordEntity entity : newBlock){
                posStrGroup.get(entity.getIndex()).put(entity.getValue(),groupNumber);
            }
            for(int grNumber : crossGroup){    //Перебор групп имеющих такой элемент
                if(grNumber != groupNumber){
                    unionGroup.put(grNumber,groupNumber); //Вносим группы на объединение
                    result.get(groupNumber).addAll(result.get(grNumber)); //Объединение
                    result.set(grNumber, null); //Обнуляем текущую группу
                }
            }
            result.get(groupNumber).add(str);
        }
        result.removeAll(Collections.singleton(null)); //Удаляем синглы
        for(List<String>list : result){
            System.out.println(list);
        }
    }
}
