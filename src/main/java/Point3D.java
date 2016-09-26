/**
 * Created by smirnov on 25.09.2016.
 */
public class Point3D extends Point2D {

    protected int z;

    public Point3D(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    public int getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Point3D point3D = (Point3D) o;

        return z == point3D.z;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
