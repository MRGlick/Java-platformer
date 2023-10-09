import java.util.ArrayList;
import java.util.Random;

abstract public class GameObject {
    
    private String id;
    private GameObject parent;
    public Vec2 cameraPos = new Vec2();
    ArrayList<GameObject> children = new ArrayList<>();
    public Vec2 globalPosition, localPosition;
    
    GameObject(){
        addToObjects();
        this.id = generateId();
    }

    public boolean equalId(GameObject obj) {
        return id.equals(obj.getId());
    }

    public void copyId(GameObject obj) {
        id = obj.getId();
    }

    public String getId() {
        return id;
    }

    public void deleteObject() {

        if (this instanceof CollisionShape) {
            Main.collisionShapes.remove(this);
        } else {
            Main.collisionShapes.remove(this);
        }
        
    }

    public void printId() {
        System.out.println("Object ID: " + id);
    }

    public void addToObjects(){
        System.out.println("adding: " + this);
        Main.gameObjects.add(this);
    }

    protected static String generateId() {
        char[] characters = new char[36];

        int index = 0;
        for (int i = (int)'a'; i < (int)'z'; i++) {
            characters[index++] = (char)i;
        }

        for (int i = (int)'0'; i < (int)'9'; i++) {
            
            characters[index++] = (char)i;
        }

        StringBuilder id = new StringBuilder(20);

        for (int i = 0; i < 20; i++) {
            Random rand = new Random();
            id.append(characters[rand.nextInt(36)]);
        }

        return id.toString();
        
    }

    public void frameUpdate(){
        
    }

    public void addChild(GameObject child){
        if (child == null) return;
        
        child.parent = this;
        child.globalPosition = child.localPosition.sub(globalPosition);
        children.add(child);
    }

    public GameObject getParentOrNull(){
        return parent;
    }

    public void setLocalPosition(Vec2 pos){
        localPosition = pos;
        GameObject parent = getParentOrNull();
        if (parent != null){
            globalPosition = parent.globalPosition.add(localPosition);
        }else {
            globalPosition = localPosition;
        }

    }

    


    public void setGlobalPosition(Vec2 pos){
        globalPosition = pos;
        GameObject parent = getParentOrNull();
        if (parent != null){
            localPosition = globalPosition.sub(parent.globalPosition);
        }else {
            localPosition = globalPosition;
        }

        for (GameObject child : children) {
            child.setLocalPosition(child.globalPosition);;
        }
    }


}
