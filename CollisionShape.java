

class CollisionData{
    boolean didCollide;
    CollisionShape collider;
    Vec2 collisionNormal;
    CollisionData(boolean didCollide, Vec2 collisionNormal){
        this.didCollide = didCollide;
        this.collisionNormal = collisionNormal;
    }
    CollisionData(){
        this.didCollide = false;
        this.collisionNormal = null;
    }

    public static void printCollisionData(CollisionData colData){
        System.out.println("didCollide: " + colData.didCollide + ", Normal: (" + colData.collisionNormal.x + ", " + colData.collisionNormal.y + ")");
    }

}


public abstract class CollisionShape extends GameObject implements Cloneable{

    public String type = "";


    @Override
    public void addToObjects(){
        Main.collisionShapes.add(this);
    }

    public String getType(){
        return type;
    }

    public CollisionData Collide(CollisionShape shape){
        return new CollisionData();
    }

    public CollisionShape clone() throws CloneNotSupportedException {
        return ((CollisionShape)super.clone());
    }


}

class CollisionRect extends CollisionShape{
    
    Vec2 size;
    public RectDisplay debugDisplay;


    CollisionRect(Vec2 localPos, Vec2 size){
        type = "rect";
        setLocalPosition(localPos);
        addChild(debugDisplay);
        setSize(size);
        setVisible(false);
        
    }

    public void setVisible(boolean v) {
        
        if (debugDisplay != null) {
            debugDisplay.rect.setVisible(v);
        }
       
    }

    @Override
    public CollisionData Collide(CollisionShape shape){
        
        if (shape == this) return new CollisionData();

        if (shape.getType() == "rect"){
            
            //System.out.println(pointInRect(new Vec2(15, 15), new Vec2(0, 0), new Vec2(30, 30)));

            CollisionData colData = new CollisionData();
            
            colData.didCollide = collideRects(globalPosition, size, ((CollisionRect)shape).globalPosition, ((CollisionRect)shape).size);
            

            return colData;
        }
        return new CollisionData();
    }

    public void setSize(Vec2 size) {
        this.size = size;
        if (debugDisplay != null) debugDisplay.rect.setBounds(
            ((int)globalPosition.x), ((int)globalPosition.y), ((int)size.x), ((int)size.y)
        );
    }


    public static boolean collideRects(Vec2 pos1, Vec2 size1, Vec2 pos2, Vec2 size2){
        
        Vec2 A = pos1, B = pos1.add(new Vec2(size1.x, 0)),
         C = pos1.add(new Vec2(0, size1.y)), D = pos1.add(size1);

        if (pos1.x == pos2.x)
            return intersectRanges(pos1.y, pos1.y + size1.y, pos2.y, pos2.y + size2.y);
        if (pos1.y == pos2.y)
            return intersectRanges(pos1.x, pos1.x + size1.x, pos2.x, pos2.x + size2.x);

        
        return pointInRect(A, pos2, size2) || pointInRect(B, pos2, size2) ||
         pointInRect(C, pos2, size2) || pointInRect(D, pos2, size2);
    }

    private static boolean intersectRanges(double min1, double max1, double min2, double max2){
        return ((min1 > min2 && min1 < max2) || (min2 > min1 && min2 < max1));
    }

    public double diff(double num1, double num2){
        return Math.abs(num1 - num2);
    }

    public static boolean pointInRect(Vec2 point, Vec2 rectPos, Vec2 rectSize){

        return ((point.x >= rectPos.x && point.x <= rectPos.x + rectSize.x) && (point.y >= rectPos.y && point.y <= rectPos.y + rectSize.y));

    }


}









