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

/**
 * Created by ryan on 5/24/15.
 */
public class TileButton extends ImageButton
{
    private int tileX, tileY;

    public TileButton(Context context, OnClickListener tileListener, int x, int y)
    {
        super(context);
        this.setOnClickListener(tileListener);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);
        tileX = x;
        tileY = y;
        this.setBackgroundColor(Global.COLOR_SEABLUE);
        this.setPadding(0, 0, 0, 0);
    }

    public int getTileX()
    {
        return tileX;
    }

    public int getTileY()
    {
        return tileY;
    }

    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        Global.TILE_SIZE = heightMeasureSpec;
        super.onMeasure(Global.TILE_SIZE, Global.TILE_SIZE);
    }
}
