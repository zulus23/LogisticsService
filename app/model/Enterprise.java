package model;

/**
 * Created by Gukov on 05.08.2016.
 */
public class Enterprise extends Base{


    public Enterprise(int id, String name) {
        super(id,name);

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
}
