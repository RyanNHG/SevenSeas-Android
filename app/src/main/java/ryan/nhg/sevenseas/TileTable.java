package ryan.nhg.sevenseas;

import android.content.Context;
import android.widget.TableLayout;

/**
 * Created by ryan on 5/26/15.
 */
public class TileTable extends TableLayout {
    public TileTable(Context context)
    {
        super(context);
    }

    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println(widthMeasureSpec);
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
