
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Main {
    
    public static Vec2 cameraPos = new Vec2();
    public static final Vec2 resolution = new Vec2(1920, 1024);
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();
    public static ArrayList<CollisionShape> collisionShapes = new ArrayList<>();
    public static ArrayList<Vec2> tilePositions = new ArrayList<>();
    public static final int TILE_SIZE = 80;
    public static boolean mouseDown = false;


    public static JFrame frame;
    
    public static Vec2 getScreenMousePosition() {
        return new Vec2(MouseInfo.getPointerInfo().getLocation());
    }
    
    public static Vec2 getGlobalMousePosition() {
        return getScreenMousePosition().add(cameraPos);
    }


    public static void main(String[] args){

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(((int)resolution.x), ((int)resolution.y));
        frame.getContentPane().setBackground(new Color(20, 20, 20));
        frame.setLayout(null);
        frame.setTitle("My Platformer!");
        
        
        
        

        Player player = new Player(new CollisionRect(new Vec2(0, 0), new Vec2(50, 50)));
        player.setVelocity(new Vec2(1, 0));
        Block block = new Block(new CollisionRect(new Vec2(0, 0), new Vec2(150, 50)));
        Block block2 = new Block(new CollisionRect(new Vec2(0, 0), new Vec2(50, 150)));

        player.setGlobalPosition(new Vec2(0, 0));
        block.setGlobalPosition(new Vec2(10, 400));
        block2.setGlobalPosition(new Vec2(150, 300));

        ((RectDisplay)player.display).setColor(new Color(40, 230, 255));
        
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    mouseDown = true;
            }
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    mouseDown = false;
            }
        });

        for (GameObject object : gameObjects){
            if (object instanceof Player && object != null) {
                frame.addKeyListener(((Player)object).keyListener);
            }
        }
        
        frame.setVisible(true);
        
        updateObjects();
       
        
    }

    // if (e.getButton() == MouseEvent.BUTTON1) {
    //     Vec2 blockPosition = getGlobalMousePosition().divide(TILE_SIZE).floor().mul(TILE_SIZE); // snap to grid
    //     if (!(tilePositions.contains(blockPosition))) return;
    //     Block block = new Block(new CollisionRect(Vec2.ZERO, new Vec2(TILE_SIZE, TILE_SIZE)));
    //     block.setGlobalPosition(blockPosition);
    //     tilePositions.add(blockPosition);
    //     System.out.println(((RectDisplay)block.display).rect.getBounds());
    // }


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

            if (mouseDown) {
                Vec2 blockPosition = getGlobalMousePosition().divide(TILE_SIZE).floor().mul(TILE_SIZE); // snap to grid
                
                boolean posInList = false;
                for (Vec2 pos : tilePositions) {
                    if (pos.x == blockPosition.x && pos.y == blockPosition.y) {
                        posInList = true;
                        break;
                    }
                }
                
                if (!posInList) {
                    Block block = new Block(new CollisionRect(Vec2.ZERO, new Vec2(TILE_SIZE, TILE_SIZE)));
                    block.setGlobalPosition(blockPosition);
                    tilePositions.add(blockPosition);
                }
            }

            frame.repaint();
            
            
        }
    }


}
