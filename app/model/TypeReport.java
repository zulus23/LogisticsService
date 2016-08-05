package model;

/**
 * Created by Gukov on 05.08.2016.
 */
public class TypeReport extends Base {


    public TypeReport(int id, String name) {
      super(id,name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeReport)) return false;

        TypeReport that = (TypeReport) o;

        if (this.getId() != that.getId()) return false;
        return getName().equals(that.getName());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        return result;
    }
}
