package entity;

public class WordEntity {

    public int index;
    public String value;

    public WordEntity(String value, int index) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
