package entity;

public class WordEntity {

    private int index;
    private String value;

    public WordEntity(int index, String value){
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }
}
