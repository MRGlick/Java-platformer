import java.util.ArrayList;

interface AreaFunc {
    void apply(CollisionShape shape);
}



public class Area extends GameObject{
    
    public CollisionRect collisionRect;

    public AreaFunc entered, exited;

    public ArrayList<CollisionShape> overlappingBodies = new ArrayList<>();

    Area(Vec2 localPos, Vec2 size) {
        setLocalPosition(localPos);
        collisionRect = new CollisionRect(Vec2.ZERO, size);
        collisionRect.isArea = true;
        addChild(collisionRect);
    }

    @Override
    public void frameUpdate() { 
        ArrayList<CollisionShape> prevOverlappingBodies = new ArrayList<>(overlappingBodies);
        overlappingBodies.clear();
        for (CollisionShape shape : Main.collisionShapes) {
            if (!collisionRect.Collide(shape).didCollide) continue;

            overlappingBodies.add(shape);
            if (!prevOverlappingBodies.contains(shape)) {
                entered.apply(shape);
            } else {
                prevOverlappingBodies.remove(shape);
            }
        }
        for (CollisionShape shape : prevOverlappingBodies) {
            exited.apply(shape);
        }
    }

    public void setEntered(AreaFunc func) {
        entered = func;
    }
    public void setExited(AreaFunc func) {
        exited = func;
    }

}
