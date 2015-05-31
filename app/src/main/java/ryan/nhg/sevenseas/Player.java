package ryan.nhg.sevenseas;

import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by ryan on 5/26/15.
 */
public class Player extends Ship
{

    public Player(Context context, OnClickListener playerListener)
    {
        super(context, Global.NUM_COLS / 2, Global.NUM_ROWS / 2);
        this.setOnClickListener(playerListener);
        this.setBackground(context.getResources().getDrawable(R.mipmap.player));
    }

    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(Global.TILE_SIZE, Global.TILE_SIZE);
    }

    protected void setInitialPosition()
    {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)this.getLayoutParams();
        params.topMargin = getSize()*getTileY();
        params.leftMargin = getSize()*getTileX();
        this.setLayoutParams(params);
    }


}

