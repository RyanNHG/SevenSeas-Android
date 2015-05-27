package ryan.nhg.sevenseas;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by ryan on 5/26/15.
 */
public abstract class Actor extends ImageButton
{
    private int x,y,size,dir;

    public Actor(Context context)
    {
        super(context);
        this.x = 0;
        this.y = 0;
        this.size = 0;
        this.dir = Global.DIR_S;
        this.setBackgroundColor(Color.MAGENTA);
    }

    public Actor(Context context, int x, int y)
    {
        super(context);
        this.x = x;
        this.y = y;
        this.size = 0;
        this.dir = Global.DIR_S;
        this.setBackgroundColor(Color.MAGENTA);
    }

    public void setTileX(int x)
    {
        this.x = x;
    }

    public void setTileY(int y)
    {
        this.y = y;
    }

    public void setTileLocation(int x, int y)
    {
        setTileX(x);
        setTileY(y);
    }

    public void setTileDir(int dir)
    {
        this.dir = dir;
    }

    public int getTileX()
    {
        return x;
    }

    public int getTileY()
    {
        return y;
    }

    public int getTileDir()
    {
        return dir;
    }

    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {

        if(this.size == 0)
        {
            this.size = View.MeasureSpec.getSize(Global.TILE_SIZE);
            setInitialPosition();
        }
        super.onMeasure(Global.TILE_SIZE, Global.TILE_SIZE);
    }

    abstract void setInitialPosition();


    public int getSize()
    {
        return this.size;
    }

    public void update(int x2, int y2)
    {
        if( x2-x > 0 )
        {
            if( y2-y > 0 )
                dir = Global.DIR_SE;
            else if( y2-y < 0 )
                dir = Global.DIR_NE;
            else dir = Global.DIR_E;

        }
        else if( x2-x < 0 )
        {
            if( y2-y > 0 )
                dir = Global.DIR_SW;
            else if( y2-y < 0 )
                dir = Global.DIR_NW;
            else dir = Global.DIR_W;
        }
        else
        {
            if( y2-y > 0 )
                dir = Global.DIR_S;
            else if( y2-y < 0 )
                dir = Global.DIR_N;
        }

        this.setRotation(Global.rotation[dir]);
        x = x2;
        y = y2;
    }
}
