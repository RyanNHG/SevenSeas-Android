package ryan.nhg.sevenseas;

import android.graphics.Color;

/**
 * Created by ryan on 5/26/15.
 */
public class Global
{
    public static final int         NUM_COLS = 9,
                                    NUM_ROWS = NUM_COLS;

    public static int               TILE_SIZE = 0;
    public static TileButton[][]    tiles;


    public static final int         COLOR_SEABLUE = Color.rgb(0x00,0x66,0xff);

    public static final int         DIR_E = 0,  DIR_NE = 1, DIR_N = 2,
                                    DIR_NW = 3, DIR_W = 4,  DIR_SW = 5,
                                    DIR_S = 6,  DIR_SE = 7;
    public static final float[]     rotation =
                                    { 270, 225, 180,
                                        135, 90, 45,
                                        0, 315};

    public static final int         MOVE_DURATION = 1000;
    public static final int         CANNON_RANGE = 3;

    public static final int         TYPE_EMPTY = 0,
                                    TYPE_WHIRLPOOL = 1,
                                    TYPE_ISLAND = 2,
                                    TYPE_WRECKAGE = 3;

    public static final int         CHANCE_ISLAND = 10;
}
