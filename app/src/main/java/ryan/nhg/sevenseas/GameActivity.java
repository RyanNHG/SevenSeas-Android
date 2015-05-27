package ryan.nhg.sevenseas;

import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;


public class GameActivity extends ActionBarActivity {


    private View.OnClickListener tileListener, playerListener;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initListeners();
        initGameGrid();
        initPlayer();
    }

    private void initListeners() 
    {
        tileListener = new View.OnClickListener(){

            public void onClick(View v)
            {
                TileButton tileButton = (TileButton)v;
                System.out.println(tileButton.getTileX() + ", " + tileButton.getTileY());
                movePlayer(tileButton.getTileX(),tileButton.getTileY());
            }
        };

        playerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Fire!");
            }
        };
    }

    private void initGameGrid()
    {
        RelativeLayout mainLayout = (RelativeLayout)this.findViewById(R.id.mainLayout);

        GameLayout gameLayout = new GameLayout(this);
        mainLayout.addView(gameLayout);
        gameLayout.setBackgroundColor(Color.GREEN);

        for(int y = 0; y < Global.NUM_ROWS; y++)
        {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setBackgroundColor(Global.COLOR_SEABLUE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            row.setLayoutParams(params);
            gameLayout.addView(row);

            for(int x = 0; x < Global.NUM_COLS; x++)
            {
                row.addView(new TileButton(this, tileListener,x,y));
            }

        }
    }

    private void initPlayer()
    {
        RelativeLayout mainLayout = (RelativeLayout)this.findViewById(R.id.mainLayout);
        player = new Player(this,playerListener);
        mainLayout.addView(player);
    }

    private void movePlayer(final int x, final int y)
    {
        ViewGroup.MarginLayoutParams params = ((ViewGroup.MarginLayoutParams)player.getLayoutParams());
        final int size = player.getSize();
        final int px = params.leftMargin/size;
        final int py = params.topMargin/size;

        if( (px-x)*(px-x) > 1 || (py-y)*(py-y) > 1) return;

        Animation a = new Animation() {

            protected void applyTransformation(float interpolatedTime, Transformation t) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)player.getLayoutParams();

                if( x-px > 0) params.leftMargin = (int)(px*size + size * interpolatedTime);
                else if( x-px < 0) params.leftMargin = (int)(px*size - size * interpolatedTime);

                if( y-py > 0) params.topMargin = (int)(py*size + size * interpolatedTime);
                else if( y-py < 0) params.topMargin = (int)(py*size - size * interpolatedTime);

                player.setLayoutParams(params);
            }
        };
        a.setDuration(300); // in ms
        player.startAnimation(a);
        player.update(x,y);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
