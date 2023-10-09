
import javax.swing.JFrame;
import java.awt.Color;
import java.util.ArrayList;

public class Main {
    
    public static Vec2 cameraPos = new Vec2();
    public static final Vec2 resolution = new Vec2(1200, 800);
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();
    public static ArrayList<CollisionShape> collisionShapes = new ArrayList<>();

    public static JFrame frame;
    

    public static void main(String[] args){
       
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(((int)resolution.x), ((int)resolution.y));
        frame.getContentPane().setBackground(new Color(20, 20, 20));
        frame.setLayout(null);
        frame.setTitle("My Platformer!");
        
        Player body = new Player(new CollisionRect(new Vec2(0, 0), new Vec2(50, 50)));
        body.setVelocity(new Vec2(1, 0));
        Block block = new Block(new CollisionRect(new Vec2(0, 0), new Vec2(150, 50)));
        Block block2 = new Block(new CollisionRect(new Vec2(0, 0), new Vec2(50, 150)));

        body.setGlobalPosition(new Vec2(0, 0));
        block.setGlobalPosition(new Vec2(10, 400));
        block2.setGlobalPosition(new Vec2(250, 400));

        ((RectDisplay)body.display).setColor(new Color(40, 230, 255));
        
        Area area = new Area(Vec2.ZERO, new Vec2(50, 50));
        area.setEntered((s) -> {
            System.out.println(s + " - Entered!");
        });
        area.setExited((s) -> {
            System.out.println(s + " - Exited!");
        });
        
        

        for (GameObject object : gameObjects){
            if (object instanceof RectDisplay){
                frame.add(((RectDisplay)object).rect);
            }
            if (object instanceof Player && object != null) {
                frame.addKeyListener(((Player)object).keyListener);
            }
        }
        
        frame.setVisible(true);
        
        updateObjects();
       
        
    }

    public static void updateObjects(){
        // 50 FPS
        while (true){
            
            long now = System.nanoTime();
            int waitVar = 0;

            while (System.nanoTime() - now < .02 * 1000000000){    
                if (waitVar < 100) waitVar += 1;
                if (waitVar > 100) waitVar -= 1; // 
            }
            for (GameObject object : gameObjects){
                object.frameUpdate();
                object.cameraPos = cameraPos;
            }
            frame.repaint();
            
            
        }
    }


}
