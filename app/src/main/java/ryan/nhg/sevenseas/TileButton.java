package ryan.nhg.sevenseas;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.internal.widget.ActionBarOverlayLayout;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by ryan on 5/24/15.
 */
public class TileButton extends ImageButton
{
    private int tileX, tileY, type;

    public TileButton(Context context, OnClickListener tileListener, int x, int y)
    {
        super(context);
        tileX = x;
        tileY = y;
        setType(Global.TYPE_EMPTY);
        this.setOnClickListener(tileListener);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);
    }

    public int getTileX()
    {
        return tileX;
    }

    public int getTileY()
    {
        return tileY;
    }

    public void setType(int type)
    {
        this.type = type;

        if(type == Global.TYPE_ISLAND || type == Global.TYPE_WRECKAGE)
            this.setClickable(false);
        else this.setClickable(true);

        updateImage();
    }

    public int getType()
    {
        return type;
    }

    private void updateImage()
    {
        switch(type)
        {
            case Global.TYPE_WHIRLPOOL:
                this.setBackground(getResources().getDrawable(R.mipmap.whirlpool));
                break;
            case Global.TYPE_ISLAND:
                this.setBackground(getResources().getDrawable(R.mipmap.island));
                break;
            case Global.TYPE_WRECKAGE:
                this.setBackground(getResources().getDrawable(R.mipmap.wreckage));
                break;
            default:
                this.setBackground(null);
                break;
        }
    }

    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)
    {
        Global.TILE_SIZE = Math.min(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(Global.TILE_SIZE, Global.TILE_SIZE);
    }
}
