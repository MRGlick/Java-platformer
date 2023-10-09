import java.awt.Color;

import javax.swing.JPanel;

public class Display extends GameObject{
}


class RectDisplay extends Display {
    
    public JPanel rect;
    public Vec2 size;
    public Color color;

    public RectDisplay(Vec2 position, Vec2 size){
        localPosition = position;
        this.size = size;
        GameObject parentOrNull = getParentOrNull();
        if (parentOrNull != null) globalPosition = parentOrNull.globalPosition;
        else globalPosition = localPosition;
        rect = new JPanel();
        rect.setBounds((int)globalPosition.x - (int)size.x / 2 - (int)cameraPos.x, (int)globalPosition.y - (int)size.y / 2 - (int)cameraPos.y, (int)size.x, (int)size.y);
        Main.frame.add(rect);
    }
    public RectDisplay(Vec2 size){
        this.size = size;
        localPosition = new Vec2();
        GameObject parentOrNull = getParentOrNull();
        if (parentOrNull != null) globalPosition = parentOrNull.globalPosition;
        else globalPosition = localPosition;
        rect = new JPanel();
        rect.setBounds((int)globalPosition.x - (int)size.x / 2, (int)globalPosition.y - (int)size.y / 2, (int)size.x, (int)size.y);
        Main.frame.add(rect);
    }

    public RectDisplay(CollisionRect rect){
        this.size = rect.size;
        setLocalPosition(rect.localPosition);
        this.rect = new JPanel();
        this.rect.setBounds((int)globalPosition.x, (int)globalPosition.y, (int)size.x, (int)size.y);
        Main.frame.add(this.rect);
    }


    public void frameUpdate(){//ik wat to do
        rect.setBounds((int)globalPosition.x - ((int)cameraPos.x) , (int)globalPosition.y - ((int)cameraPos.y), (int)size.x, (int)size.y);
        rect.repaint();
    }

    public void setColor(Color color){
        rect.setBackground(color);
        //rect.repaint();
    }

}
