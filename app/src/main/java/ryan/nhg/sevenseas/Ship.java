package ryan.nhg.sevenseas;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by ryan on 5/26/15.
 */
public abstract class Ship extends ImageButton
{
    private int x,y,size,dir;

    public Ship(Context context)
    {
        super(context);
        this.x = 0;
        this.y = 0;
        this.size = 0;
        this.dir = Global.DIR_S;
        this.setBackgroundColor(Color.MAGENTA);
    }

    public Ship(Context context, int x, int y)
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

        System.out.println("Ship is at [" + x + ", " + y + "]");
    }

    public void move(final int x, final int y)
    {
        final Ship ship = this;
        final ViewGroup.MarginLayoutParams params = ((ViewGroup.MarginLayoutParams) ship.getLayoutParams());
        final int size = ship.getSize();
        final int px = params.leftMargin/size;
        final int py = params.topMargin/size;

        if( (px-x)*(px-x) > 1 || (py-y)*(py-y) > 1) return;

        Animation a = new Animation() {

            protected void applyTransformation(float interpolatedTime, Transformation t)
            {
                if( x-px > 0) params.leftMargin = (int)(px*size + size * interpolatedTime);
                else if( x-px < 0) params.leftMargin = (int)(px*size - size * interpolatedTime);

                if( y-py > 0) params.topMargin = (int)(py*size + size * interpolatedTime);
                else if( y-py < 0) params.topMargin = (int)(py*size - size * interpolatedTime);

                ship.setLayoutParams(params);
            }
        };
        a.setDuration(300);
        a.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}

            public void onAnimationEnd(Animation animation)
            {
                if (Global.tiles[x][y].getType() == Global.TYPE_WHIRLPOOL)
                    ship.whirlpool();
            }

            public void onAnimationRepeat(Animation animation) {}
        });

        ship.startAnimation(a);
        ship.update(x, y);

    }

    public void whirlpool()
    {
        final Ship ship = this;
        final ViewGroup.MarginLayoutParams params = ((ViewGroup.MarginLayoutParams) ship.getLayoutParams());
        final float rotation = this.getRotation();

        Animation a = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t)
            {
                ship.setRotation(rotation + 360*interpolatedTime*interpolatedTime);
                ship.setAlpha(1 - interpolatedTime);
            }
        };

        final Animation b = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t)
            {
                ship.setRotation(rotation - 360*interpolatedTime*interpolatedTime);
                ship.setAlpha(interpolatedTime);
            }
        };

        a.setDuration(1000);
        a.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int randX = 0, randY = 0;

                while (Global.tiles[randX][randY].getType() != Global.TYPE_EMPTY)
                {
                    randX = (int) (Math.random() * Global.NUM_COLS);
                    randY = (int) (Math.random() * Global.NUM_ROWS);
                    System.out.println(Global.tiles[randX][randY].getType());
                }

                LinearLayout.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) ship.getLayoutParams();
                params.leftMargin = randX * ship.getSize();
                params.topMargin = randY * ship.getSize();
                ship.setLayoutParams(params);

                ship.update(randX, randY);
                ship.startAnimation(b);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        b.setDuration(1000);

        ship.startAnimation(a);
    }

    public int[][] getCannonTargets()
    {
        int[][] targets = { {-1,-1}, {-1,-1}};

        return targets;
    }
}
