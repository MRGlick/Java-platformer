import java.awt.Point;

public class Vec2 {
    double x, y;

    static final Vec2 ZERO = new Vec2(), UP = new Vec2(0, -1), DOWN = new Vec2(0, 1), 
    RIGHT = new Vec2(1, 0), LEFT = new Vec2(-1, 0);


    Vec2(double x, double y){
        this.x = x;
        this.y = y;
    }
    Vec2(){
        this.x = 0;
        this.y = 0;
    }

    

    Vec2(Point point){
        this.x = (double)point.x;
        this.y = (double)point.y;
    }

    
    public Vec2 divide(double by){
        return new Vec2(x / by, y / by);
    }

    public Vec2 floor() {
        return new Vec2(Math.floor(x), Math.floor(y));
    }

    public Vec2 mul(double by){
        return new Vec2(x * by, y * by);
    }
    public Vec2 add(Vec2 vector){
        return new Vec2(vector.x + this.x, vector.y + this.y);
    }
    public Vec2 sub(Vec2 vector){
        return new Vec2(this.x - vector.x, this.y = vector.x);
    }

    public double getLength(){
        return Math.sqrt(x * x + y * y);
    }

    public Vec2 normalized(){
        double len = getLength();
        return new Vec2(this.x / len, this.y / len);
    }


    public static void printVec2(Vec2 vector){
        if (vector == null) return;
        System.out.println("Vec2(" + vector.x + ", " + vector.y + ")");
    }
    
    public static Vec2 lerp(Vec2 v1, Vec2 v2, double step) {
        return new Vec2(Lerp.lerp(v1.x, v2.x, step), Lerp.lerp(v1.y, v2.y, step));
    }

    public double distanceTo(Vec2 pos) {
        return Math.sqrt((x - pos.x) * (x - pos.x) + (y - pos.y) * (y - pos.y));
    }

}
