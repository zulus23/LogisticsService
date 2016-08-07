package model;

/**
 * Created by Gukov on 05.08.2016.
 */
public class Enterprise extends Base{
    private String nameInDb;

    public Enterprise(int id, String name, String nameInDb) {
        super(id,name);
        this.nameInDb = nameInDb;

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enterprise)) return false;

        Enterprise that = (Enterprise) o;

        if (this.getId() != that.getId()) return false;
        return getName().equals(that.getName());

    }

    @Override
    public int hashCode() {
        int result = this.getId();
        result = 31 * result + getName().hashCode();
        return result;
    }

    public String getNameInDb() {
        return nameInDb;
    }
}
