package pl.edu.agh.amwj.collector;

/**
 * Created by P on 29.11.2016.
 */
public class MyEdge {
    private FieldType type;

    public MyEdge(String type) {
        this.type = FieldType.valueOf("FIELD_" + type.toUpperCase());
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MyEdge{" +
                "type=" + type.getName() +
                '}';
    }

    public enum FieldType {
        FIELD_F1("f1"), FIELD_F2("f2");

        private String name;

        FieldType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

