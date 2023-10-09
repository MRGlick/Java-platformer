import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Body extends GameObject{
    
    public CollisionShape collisionShape;
    public Display display;
    protected Vec2 velocity;
    public boolean isOnFloor = false;

    public Body(CollisionShape shape, Display display){
        super();
        setGlobalPosition(new Vec2());
        this.collisionShape = shape;
        
        this.display = display;
        addChild(this.display);
        addChild(this.collisionShape);
        velocity = new Vec2();
        
    }

    public Body(CollisionShape shape) {
        super();
        setGlobalPosition(new Vec2());
        this.collisionShape = shape;
        
        this.display = new RectDisplay(((CollisionRect)shape));
        addChild(this.display);
        addChild(this.collisionShape);
        velocity = new Vec2();
    }


    public Vec2 getVelocity(){
        return velocity;
    }

    public void doFrameUpdate() {
        collisionShape.setLocalPosition(collisionShape.localPosition);
        display.setLocalPosition(display.localPosition);
        move();
    }

    public void frameUpdate(){
        doFrameUpdate();
    }

    public void move(){
       
        CollisionRect colShapeChangeX = new CollisionRect(globalPosition.add(new Vec2(velocity.x, 0)), ((CollisionRect)collisionShape).size);
        CollisionRect colShapeChangeY = new CollisionRect(globalPosition.add(new Vec2(0, velocity.y)), ((CollisionRect)collisionShape).size);
        colShapeChangeX.copyId(collisionShape);
        colShapeChangeY.copyId(collisionShape);
        
            

        colShapeChangeX.setVisible(true);
        colShapeChangeY.setVisible(true);
        colShapeChangeY.setGlobalPosition(new Vec2(globalPosition.x, globalPosition.y + velocity.y));
        boolean collided = false;
        for (CollisionShape shape : Main.collisionShapes) {
            if (shape.equalId(collisionShape) || shape.isArea) continue;
            CollisionData colDataX = colShapeChangeX.Collide(shape);
            CollisionData colDataY = colShapeChangeY.Collide(shape);
            

                

            if (colDataX.didCollide){

                if (this instanceof Player) System.out.println("collided X");

                setLocalPosition(new Vec2(localPosition.x, localPosition.y + velocity.y));
                setVelocity(new Vec2(0, velocity.y));
                // if (!(this instanceof Block)) System.out.println("X collided.");
                collided = true;
                break;
            } if (colDataY.didCollide) {
                if (velocity.y > 0) isOnFloor = true;
                else isOnFloor = false;
                setLocalPosition(new Vec2(localPosition.x + velocity.x, localPosition.y));
                setVelocity(new Vec2(velocity.x, 0));
                System.out.println(isOnFloor ? "collided down" : "collided up");
                collided = true;
                break;
            } else {
                isOnFloor = false;
            }
        }

        // if (!(this instanceof Block)){ // debug block
        //    System.out.println(collided);
        // }

        if (!collided) {
            setLocalPosition(localPosition.add(velocity));
        }
        colShapeChangeX.deleteObject();
        colShapeChangeY.deleteObject();
        
    }
        
    


    public void setVelocity(Vec2 newVel){
        velocity = newVel;
    }


}


class Block extends Body {
    
    Block(CollisionShape shape, Display display){
        super(shape, display);
    }

    Block(CollisionShape shape) {
        super(shape);
    }

    @Override
    public void setVelocity(Vec2 newVel){

    }
    

}

class Player extends Body {
    
    public boolean leftPressed = false, rightPressed = false, jumpPressed = false, isCameraMain = true;

    public double moveSpeed = 5, jumpForce = -9, riseGravity = .4, fallGravity = .5;
    
    public KeyListener keyListener;

    Player(CollisionShape shape, Display display) {
        super(shape, display);
        keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    leftPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rightPressed = true;
                }
                
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
                    jumpPressed = true;
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    leftPressed = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rightPressed = false;
                }
                
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
                    jumpPressed = false;
                }
            }
        };
    }

    Player(CollisionShape shape) {
        super(shape);
        keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    leftPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rightPressed = true;
                }
                
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
                    jumpPressed = true;
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    leftPressed = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rightPressed = false;
                }
                
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
                    jumpPressed = false;
                    jumpRelease();
                }
            }
        };
    }

    public void jumpRelease() {
        if (velocity.y < 0) setVelocity(new Vec2(velocity.x, velocity.y / 2));
    }

    @Override
    public void frameUpdate() {
        
        doFrameUpdate(); // basically super.frameUpdate()
        

        if (isCameraMain) {
            Vec2 pos = new Vec2(globalPosition.x - Main.resolution.x / 2 + ((RectDisplay)display).size.x / 2,
             globalPosition.y - Main.resolution.y / 2 + ((RectDisplay)display).size.y / 2);
            
            Main.cameraPos = Vec2.lerp(Main.cameraPos, pos, 0.1);
        }
           

        double yVel = isOnFloor ? velocity.y : velocity.y + (velocity.y < 0 ? riseGravity : fallGravity);
        double moveX = 0;
        moveX += (rightPressed ? 1 : 0) + (leftPressed ? -1 : 0);// 1 if right, -1 if left, 0 if both or none
        
        
        setVelocity(new Vec2(moveX * moveSpeed, isOnFloor && jumpPressed ? jumpForce : yVel));
        

    }


}

