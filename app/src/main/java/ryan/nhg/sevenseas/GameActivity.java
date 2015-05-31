package ryan.nhg.sevenseas;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Gravity;
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


public class GameActivity extends AppCompatActivity {


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
                player.move(tileButton.getTileX(), tileButton.getTileY());
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
        
        Global.tiles = new TileButton[Global.NUM_COLS][Global.NUM_ROWS];

        for(int y = 0; y < Global.NUM_ROWS; y++)
        {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setBackgroundColor(Global.COLOR_SEABLUE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            row.setLayoutParams(params);
            row.setGravity(Gravity.CENTER);
            gameLayout.addView(row);

            for(int x = 0; x < Global.NUM_COLS; x++)
            {
                Global.tiles[x][y] = new TileButton(this, tileListener,x,y);

                if(getRandomChance(Global.CHANCE_ISLAND)) Global.tiles[x][y].setType(Global.TYPE_ISLAND);

                row.addView(Global.tiles[x][y]);
            }
        }

        Global.tiles[Global.NUM_COLS/2][Global.NUM_ROWS/2].setType(Global.TYPE_EMPTY);

        initWhirlpools();
    }

    private boolean getRandomChance(int percent)
    {
        return (int)(Math.random()*100) < percent;
    }

    private void initWhirlpools()
    {
        Global.tiles[0][0].setType(Global.TYPE_WHIRLPOOL);
        Global.tiles[0][Global.NUM_ROWS-1].setType(Global.TYPE_WHIRLPOOL);
        Global.tiles[Global.NUM_COLS-1][0].setType(Global.TYPE_WHIRLPOOL);
        Global.tiles[Global.NUM_COLS-1][Global.NUM_COLS-1].setType(Global.TYPE_WHIRLPOOL);
    }

    private void initPlayer()
    {
        RelativeLayout mainLayout = (RelativeLayout)this.findViewById(R.id.mainLayout);
        player = new Player(this,playerListener);
        mainLayout.addView(player);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings) return true;

        return super.onOptionsItemSelected(item);
    }
}
