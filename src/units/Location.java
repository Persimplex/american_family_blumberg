package units;

/**
 * Created by Tim on 04/10/16.
 */
public class Location {

    private int x;
    private int y;

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }
}
