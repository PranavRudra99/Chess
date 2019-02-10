package com.example.prana.project;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.util.DisplayMetrics;
import android.view.MotionEvent;


public class C extends View {
    Context c=getContext();
    DisplayMetrics x = new DisplayMetrics();
    Paint p = new Paint(),p1 = new Paint(),p2=new Paint(),p3=new Paint();
    int px, py,px1,py1,k=0,con=0;//px,py-present selected block//px1,py1-previous selected block
    float h, w, wx, hx, hr, wr, a, b, m=0;//h-height pixels w-width pixels  a,b-touch coordinates on screen
    int switcher=0,aqc=1,aknc=2,arc=2,abc=2,bqc=1,bknc=2,brc=2,bbc=2;//count of pieces pawn can become queen,rook,bishop,knight
    int x_val=-1;//position of pawn that reaches other end
    int select = -1;
    int is_a_pawn,is_a_king,is_a_queen,is_a_knight,is_a_rook,is_a_bishop;
    int is_b_pawn,is_b_king,is_b_queen,is_b_knight,is_b_rook,is_b_bishop;
    int apawn[][] = new int[8][6];
    int bpawn[][] = new int[8][6];
    int occupied_a[][] = new int[8][8];
    int occupied_b[][] = new int[8][8];
    int arook[][] = new int[10][6];// 2(0,1) are original rook others(2-9)are pawns that can become rook
    int brook[][] = new int[10][6];
    int aknight[][] = new int[10][5];
    int bknight[][] = new int[10][5];
    int abishop[][] = new int[10][5];
    int bbishop[][] = new int[10][5];
    int aking[] = new int[6];
    int aqueen[][] = new int[10][5];
    int bking[] = new int[6];
    int bqueen[][] = new int[10][5];


    int t_apawn[][]=new int[8][5];
    int t_aqueen[][]=new int[10][4];
    int t_arook[][] = new int[10][5];
    int t_aknight[][] = new int[10][4];
    int t_abishop[][] = new int[10][4];
    int t_aking[] = new int[5];
    int t_occupied_a[][] = new int[8][8];

    int t_bpawn[][]=new int[8][5];
    int t_bqueen[][]=new int[10][4];
    int t_brook[][] = new int[10][5];
    int t_bknight[][] = new int[10][4];
    int t_bbishop[][] = new int[10][4];
    int t_bking[] = new int[5];
    int t_occupied_b[][] = new int[8][8];

    /*These are used to store moves that can be performed when king is in check
    [x][y][z]
    x-piece number
    y-move number
    z-move coordinates x,y
    ****king has only y,z
    */
    int a_king_moves[][] = new int [20][2];
    int a_queen_moves[][][] = new int [9][50][2];
    int a_bishop_moves[][][] = new int [9][50][2];
    int a_knight_moves[][][] = new int [9][50][2];
    int a_rook_moves[][][] = new int [9][50][2];
    int a_pawn_moves[][][]= new int [8][10][3];//x,y,en passant

    int a_pawn_count[]=new int[8];
    int a_queen_count[]=new int[9];
    int a_bishop_count[]=new int[9];
    int a_rook_count[]=new int[9];
    int a_knight_count[]=new int[9];
    int a_king_count=0;


    int king_a_moves[][] = new int[20][2];
    int queen_a_moves[][][] = new int [9][50][2];
    int bishop_a_moves[][][] = new int [9][50][2];
    int knight_a_moves[][][] = new int [9][50][2];
    int rook_a_moves[][][] = new int [9][50][2];
    int pawn_a_moves[][][]= new int [8][10][3];//x,y,indication for en passant

    int pawn_a_count[]=new int[8];
    int queen_a_count[]=new int[9];
    int bishop_a_count[]=new int[9];
    int rook_a_count[]=new int[9];
    int knight_a_count[]=new int[9];
    int king_a_count=0;

    int king_b_moves[][] = new int[20][2];
    int queen_b_moves[][][] = new int [9][50][2];
    int bishop_b_moves[][][] = new int [9][50][2];
    int knight_b_moves[][][] = new int [9][50][2];
    int rook_b_moves[][][] = new int [9][50][2];
    int pawn_b_moves[][][]= new int [8][10][3];

    int pawn_b_count[]=new int[8];
    int queen_b_count[]=new int[9];
    int bishop_b_count[]=new int[9];
    int rook_b_count[]=new int[9];
    int knight_b_count[]=new int[9];
    int king_b_count=0;

    int b_king_moves[][] = new int [20][2];
    int b_queen_moves[][][] = new int [9][50][2];
    int b_bishop_moves[][][] = new int [9][50][2];
    int b_knight_moves[][][] = new int [9][50][2];
    int b_rook_moves[][][] = new int [9][50][2];
    int b_pawn_moves[][][]= new int [8][10][3];

    int b_pawn_count[]=new int[8];
    int b_queen_count[]=new int[9];
    int b_bishop_count[]=new int[9];
    int b_rook_count[]=new int[9];
    int b_knight_count[]=new int[9];
    int b_king_count=0;

    int a_count = 0;
    int b_count = 0;

    int z_val=0;//to prevent pieces from being moved when checking for stalemate
    /*
     * check()-returns 0 if king in check
     *         returns 1 if king not in check
     * checkmate()-returns 0 if checkmate
     *             returns 1 if not checkmate
     *  increment()-returns true when check==1
     *              else returns false
     * */

    public void init() {
        p.setColor(Color.YELLOW);
        p1.setColor(Color.GREEN);
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(x);
        h = x.heightPixels;
        w = x.widthPixels;
        wr = w / 10;
        hr = h / 10;
        wx = 5 * wr / 6;
        hx = 5 * hr / 6;
        //selected piece grouping
        is_a_king=0;
        is_a_queen=0;
        is_a_bishop=0;
        is_a_bishop=0;
        is_a_rook=0;
        is_a_pawn=0;

        is_b_king=0;
        is_b_queen=0;
        is_b_bishop=0;
        is_b_knight=0;
        is_b_rook=0;
        is_b_pawn=0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                occupied_a[i][j] = 0;
                occupied_a[i][j] = 0;
            }
        }
        for (int i = 0; i < 8; i++) {
            //pawns-"[i][2]"-indicates whether piece is alive or not["0"-alive/"-1"-dead]
            apawn[i][0] = i + 1;
            apawn[i][1] = 7;
            apawn[i][2] = 0;//alive=0/dead=-1
            apawn[i][3] = 0;//selected y=1/n=0
            apawn[i][4] = 0;//first move (1 or 2 steps) 0 initially if piece moved once changed to -1
            apawn[i][5] = -1;//to be moved or not if king is in check initially '-1' not to be moved later computed '1' can be moved
            bpawn[i][0] = i + 1;
            bpawn[i][1] = 2;
            bpawn[i][2] = 0;
            bpawn[i][3] = 0;
            bpawn[i][4] = 0;
            bpawn[i][5] = -1;
            //Occupied pieces(1-occupied/0-empty)-
            //1 Player-
            occupied_a[i][6] = 1;//1
            occupied_a[i][7] = 1;
            //2 Player-
            occupied_b[i][0] = 1;
            occupied_b[i][1] = 1;//1
        }
        /*[x][y]- //not applicable for king and queen
         * x- piece identifier 0-left 1-right
         * y-piece information ("0"-x coordinate) ("1"-y coordinate) ("2"-(0)alive/(-1)dead)*/
        //2 player rook-
        //left-
        brook[0][0] = 1;
        brook[0][1] = 1;
        brook[0][2] = 0;
        brook[0][3] = 0;
        brook[0][4] = 0;//indication for castling
        brook[0][5] = -1;
        //occupied_b[2][0]=1;//remove
        //right-
        brook[1][0] = 8;
        brook[1][1] = 1;
        brook[1][2] = 0;
        brook[1][3] = 0;
        brook[1][4] = 0;//indication for castling
        brook[1][5] = -1;
        //1 player rook-
        //left-
        arook[0][0] = 1;
        arook[0][1] = 8;//8
        arook[0][2] = 0;
        arook[0][3] = 0;
        arook[0][4] = 0;//indication for castling
        arook[0][5] = -1;
        //occupied_a[0][5]=1;//remove
        //right-
        arook[1][0] = 8;
        arook[1][1] = 8;
        arook[1][2] = 0;
        arook[1][3] = 0;
        arook[1][4] = 0;//indication for castling
        arook[1][5] = -1;
        //2 player knight-
        //left-
        bknight[0][0] = 2;
        bknight[0][1] = 1;
        bknight[0][2] = 0;
        bknight[0][3] = 0;
        bknight[0][4] = -1;
        //right-
        bknight[1][0] = 7;
        bknight[1][1] = 1;
        bknight[1][2] = 0;
        bknight[1][3] = 0;
        bknight[1][4] = -1;
        //1 player knight-
        //left-
        aknight[0][0] = 2;
        aknight[0][1] = 8;
        aknight[0][2] = 0;
        aknight[0][3] = 0;
        aknight[0][4] = -1;
        //right-
        aknight[1][0] = 7;
        aknight[1][1] = 8;
        aknight[1][2] = 0;
        aknight[1][3] = 0;
        aknight[1][4] = -1;
        //2 player bishop-
        //left-
        bbishop[0][0] = 3;
        bbishop[0][1] = 1;
        bbishop[0][2] = 0;
        bbishop[0][3] = 0;
        bbishop[0][4] = -1;
        //right-
        bbishop[1][0] = 6;
        bbishop[1][1] = 1;
        bbishop[1][2] = 0;
        bbishop[1][3] = 0;
        bbishop[1][4] = -1;
        //1 player bishop-
        //left-
        abishop[0][0] = 3;
        abishop[0][1] = 8;
        abishop[0][2] = 0;
        abishop[0][3] = 0;
        abishop[0][4] = -1;
        //right-
        abishop[1][0] = 6;
        abishop[1][1] = 8;
        abishop[1][2] = 0;
        abishop[1][3] = 0;
        abishop[1][4] = -1;
        //2 player king-
        bking[0] = 5;//5
        bking[1] = 1;//1
        bking[2] = 0;
        bking[3] = 0;
        bking[4] = 0;//indication for castling ****0-castling possible else no
        bking[5] = -1;
        //occupied_b[4][0]=1;//remove
        //2 player queen-
        bqueen[0][0] = 4;//4
        bqueen[0][1] = 1;//1
        bqueen[0][2] = 0;
        bqueen[0][3] = 0;
        bqueen[0][4] = -1;
        //1 player king-
        aking[0] = 5;//5
        aking[1] = 8;//8
        aking[2] = 0;
        aking[3] = 0;
        aking[4] = 0;//indication for castling
        aking[5] = -1;
        //occupied_a[4][7]=1;//remove
        //1 player queen-
        aqueen[0][0] = 4;//4
        aqueen[0][1] = 8;//8
        aqueen[0][2] = 0;
        aqueen[0][3] = 0;
        aqueen[0][4] = -1;

        aqueen[1][2]=-1;
        aqueen[1][4]=-1;
        bqueen[1][2]=-1;
        bqueen[1][4]=-1;
        for(int i=2;i<10;i++)
        {
            arook[i][2]=-1;
            arook[i][5]=-1;
            abishop[i][2]=-1;
            abishop[i][4]=-1;
            aknight[i][2]=-1;
            aknight[i][4]=-1;
            aqueen[i][2]=-1;
            aqueen[i][4]=-1;
            brook[i][2]=-1;
            brook[i][5]=-1;
            bbishop[i][2]=-1;
            bbishop[i][4]=-1;
            bknight[i][2]=-1;
            bknight[i][4]=-1;
            bqueen[i][2]=-1;
            bqueen[i][4]=-1;
        }
    }

    public C(Context context) {
        super(context);
        init();
    }

    public void reset() {
        for (int i = 0; i < 8; i++) {
            apawn[i][3] = 0;//selected y=1/n=0
            bpawn[i][3] = 0;
            apawn[i][5] = -1;
            bpawn[i][5] = -1;
        }
        //2 player rook-
        for(int i=0;i<brc;i++)
        {
            brook[i][3] = 0;
            brook[i][5] = -1;
        }
        //1 player rook-
        for(int i=0;i<arc;i++)
        {
            arook[i][3] = 0;
            arook[i][5] = -1;
        }
        //2 player knight-
        for(int i=0;i<bknc;i++)
        {
            bknight[i][3] = 0;
            bknight[i][4] = -1;
        }
        //1 player knight-
        for(int i=0;i<aknc;i++)
        {
            aknight[i][3] = 0;
            aknight[i][4] = -1;
        }
        //2 player bishop-
        for(int i=0;i<bbc;i++)
        {
            bbishop[i][3] = 0;
            bbishop[i][4] = -1;
        }
        //1 player bishop-
        for(int i=0;i<abc;i++)
        {
            abishop[i][3] = 0;
            abishop[i][4] = -1;
        }
        bking[3] = 0;
        //2 player queen-
        for(int i=0;i<bqc;i++)
        {
            bqueen[i][3] = 0;
            bqueen[i][4] = -1;
        }
        //1 player queen-
        for(int i=0;i<aqc;i++)
        {
            aqueen[i][3] = 0;
            aqueen[i][4] = -1;
        }
        //2 player king-
        bking[3] = 0;
        bking[5] = -1;
        //1 player king-
        aking[3] = 0;
        aking[5] = -1;
        //selected piece grouping
        is_a_king=0;
        is_a_queen=0;
        is_a_bishop=0;
        is_a_knight=0;
        is_a_rook=0;
        is_a_pawn=0;

        is_b_king=0;
        is_b_queen=0;
        is_b_bishop=0;
        is_b_knight=0;
        is_b_rook=0;
        is_b_pawn=0;

        k=0;
    }
    public void re_set()
    {
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                occupied_a[i][j]=t_occupied_a[i][j];
                occupied_b[i][j]=t_occupied_b[i][j];
            }
        }
        for(int i=0;i<5;i++)
        {
            aking[i]=t_aking[i];
        }
        for(int i=0;i<abc;i++)
        {
            for(int j=0;j<3;j++)
            {
                abishop[i][j]=t_abishop[i][j];
            }
        }
        for(int i=0;i<aknc;i++)
        {
            for(int j=0;j<3;j++)
            {
                aknight[i][j]=t_aknight[i][j];
            }
        }
        for(int i=0;i<arc;i++)
        {
            for(int j=0;j<4;j++)
            {
                arook[i][j]=t_arook[i][j];
            }
        }
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<4;j++)
            {
                apawn[i][j]=t_apawn[i][j];
            }
        }
        for(int i=0;i<aqc;i++)
        {
            for(int j=0;j<3;j++)
            {
                aqueen[i][j]=t_aqueen[i][j];
            }
        }
        for(int i=0;i<4;i++)
        {
            bking[i]=t_bking[i];
        }
        for(int i=0;i<bbc;i++)
        {
            for(int j=0;j<3;j++)
            {
                bbishop[i][j]=t_bbishop[i][j];
            }
        }
        for(int i=0;i<bknc;i++)
        {
            for(int j=0;j<3;j++)
            {
                bknight[i][j]=t_bknight[i][j];
            }
        }
        for(int i=0;i<brc;i++)
        {
            for(int j=0;j<4;j++)
            {
                brook[i][j]=t_brook[i][j];
            }
        }
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<4;j++)
            {
                bpawn[i][j]=t_bpawn[i][j];
            }
        }
        for(int i=0;i<bqc;i++)
        {
            for(int j=0;j<3;j++)
            {
                bqueen[i][j]=t_bqueen[i][j];
            }
        }
    }
    public void set()
    {
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                t_occupied_a[i][j]=occupied_a[i][j];
                t_occupied_b[i][j]=occupied_b[i][j];
            }
        }
        for(int i=0;i<5;i++)
        {
            t_aking[i]=aking[i];
        }
        for(int i=0;i<abc;i++)
        {
            for(int j=0;j<4;j++)
            {
                t_abishop[i][j]=abishop[i][j];
            }
        }
        for(int i=0;i<aknc;i++)
        {
            for(int j=0;j<4;j++)
            {
                t_aknight[i][j]=aknight[i][j];
            }
        }
        for(int i=0;i<arc;i++)
        {
            for(int j=0;j<5;j++)
            {
                t_arook[i][j]=arook[i][j];
            }
        }
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<5;j++)
            {
                t_apawn[i][j]=apawn[i][j];
            }
        }
        for(int i=0;i<aqc;i++)
        {
            for(int j=0;j<4;j++)
            {
                t_aqueen[i][j]=aqueen[i][j];
            }
        }
        for(int i=0;i<5;i++)
        {
            t_bking[i]=bking[i];
        }
        for(int i=0;i<bbc;i++)
        {
            for(int j=0;j<4;j++)
            {
                t_bbishop[i][j]=bbishop[i][j];
            }
        }
        for(int i=0;i<bknc;i++)
        {
            for(int j=0;j<4;j++)
            {
                t_bknight[i][j]=bknight[i][j];
            }
        }
        for(int i=0;i<brc;i++)
        {
            for(int j=0;j<5;j++)
            {
                t_brook[i][j]=brook[i][j];
            }
        }
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<5;j++)
            {
                t_bpawn[i][j]=bpawn[i][j];
            }
        }
        for(int i=0;i<bqc;i++)
        {
            for(int j=0;j<4;j++)
            {
                t_bqueen[i][j]=bqueen[i][j];
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        a = (int) event.getX();
        b = (int) event.getY();
        if(switcher==1)
        {//poke
            p.setColor(Color.parseColor("#303f9f"));
            float scale_y=h/8;
            float scale_x=w/3;
            int kz=-1;
            if(select==0)
            {
                //a-queen
                if (a > scale_x && a <  2*scale_x && b > 2.4*scale_y && b < 2.8*scale_y) {
                    aqueen[aqc][2] = 0;
                    aqueen[aqc][0] = x_val;
                    aqueen[aqc][1] = 1;
                    aqc++;
                    kz=1;
                }
                //a-rook
                if (a > scale_x && a <  2*scale_x&& b > 3.8*scale_y && b < 4.2*scale_y) {
                    arook[arc][2] = 0;
                    arook[arc][0] = x_val;
                    arook[arc][1] = 1;
                    arc++;
                    kz=1;
                }
                //a-bishop
                if (a > scale_x && a <  2*scale_x&& b > 5.2*scale_y && b < 5.6*scale_y) {
                    abishop[abc][2] = 0;
                    abishop[abc][0] = x_val;
                    abishop[abc][1] = 1;
                    abc++;
                    kz=1;
                }
                //a-knight
                if (a > scale_x && a <  2*scale_x&& b > 6.6*scale_y && b < 7*scale_y) {
                    aknight[aknc][2] = 0;
                    aknight[aknc][0] = x_val;
                    aknight[aknc][1] = 1;
                    aknc++;
                    kz=1;
                }
            }
            if(select==1)
            {
                //b-queen
                if (a > scale_x && a <  2*scale_x&& b > 2.4*scale_y && b < 2.8*scale_y) {
                    bqueen[bqc][2] = 0;
                    bqueen[bqc][0] = x_val;
                    bqueen[bqc][1] = 8;
                    bqc++;
                    kz=1;
                }
                //b-rook
                if (a > scale_x && a <  2*scale_x&& b > 3.8*scale_y && b < 4.2*scale_y) {
                    brook[brc][2] = 0;
                    brook[brc][0] = x_val;
                    brook[brc][1] = 8;
                    brc++;
                    kz=1;
                }
                //b-bishop
                if (a > scale_x && a <  2*scale_x&& b > 5.2*scale_y && b < 5.6*scale_y) {
                    bbishop[bbc][2] = 0;
                    bbishop[bbc][0] = x_val;
                    bbishop[bbc][1] = 8;
                    bbc++;
                    kz=1;
                }
                //b-knight
                if (a > scale_x && a <  2*scale_x&& b > 6.6*scale_y && b < 7*scale_y) {
                    bknight[bknc][2] = 0;
                    bknight[bknc][0] = x_val;
                    bknight[bknc][1] = 8;
                    bknc++;
                    kz=1;
                }
            }
            if(kz==1)
            {
                switcher=0;
                a=0;
                b=0;
            }
        }
        if (switcher==0 && a > wr && a < 9 * wr && b > hr && b < 9 * hr)
        {
            for (int i = 1; i < 9; i++)
            {
                for (int j = 1; j < 9; j++)
                {
                    if (a > i * wr && a < (i + 1) * wr && b > j * hr && b < (j + 1) * hr)
                    {
                        px = i;
                        py = j;
                        if(m%4==0)
                        {
                            if (occupied_a[px - 1][py - 1] != 0)
                            {
                                modulo_0();
                            }
                        }
                        else if(m%4==1)
                        {
                            if(occupied_a[px-1][py-1]!=0)
                            {
                                m--;
                                modulo_0();
                            }
                            if(occupied_a[px-1][py-1]==0)
                            {
                                modulo_1();
                            }
                        }
                        else if(m%4==2) {
                            if (occupied_b[px - 1][py - 1] != 0)
                            {
                                modulo_2();
                            }
                        }
                        else if(m%4==3)
                        {
                            if(occupied_b[px-1][py-1]!=0)
                            {
                                m--;
                                modulo_2();
                            }
                            if(occupied_b[px-1][py-1]==0)
                            {
                                modulo_3();
                            }
                        }
                    }
                }
            }
        }
        invalidate();
        return super.onTouchEvent(event);
    }
    public void modulo_0()
    {
        a_count=0;//here
        for (int k = 0; k < 8; k++) {
            if (apawn[k][4] == 1)
                apawn[k][4] = -1;//preventing en passant capture if not used immediately
        }
        if(a_check(aking[0],aking[1])==1)
        {
            for (int k = 0; k < 8; k++) {
                if (apawn[k][0] == px && apawn[k][1] == py && apawn[k][2] != -1) {
                    reset();
                    is_a_pawn = 1;
                    apawn[k][3] = 1;
                }
            }
            if (aking[0] == px && aking[1] == py && aking[2] != -1) {

                reset();
                is_a_king = 1;
                aking[3] = 1;

            }
            for (int k = 0; k < 10; k++) {
                if (abishop[k][0] == px && abishop[k][1] == py && abishop[k][2] != -1) {
                    reset();
                    is_a_bishop = 1;
                    abishop[k][3] = 1;
                }
                if (arook[k][0] == px && arook[k][1] == py && arook[k][2] != -1) {
                    reset();
                    is_a_rook = 1;
                    arook[k][3] = 1;
                }
                if (aknight[k][0] == px && aknight[k][1] == py && aknight[k][2] != -1) {
                    reset();
                    is_a_knight = 1;
                    aknight[k][3] = 1;
                }
                if (aqueen[k][0] == px && aqueen[k][1] == py && aqueen[k][2] != -1) {
                    reset();
                    is_a_queen = 1;
                    aqueen[k][3] = 1;
                }
            }
        }
        else
        {
            a_checkmate();
            for (int k = 0; k < 8; k++) {
                if (apawn[k][0] == px && apawn[k][1] == py && apawn[k][2] != -1 && apawn[k][5] == 1) {
                    reset();
                    is_a_pawn = 1;
                    apawn[k][3] = 1;
                }
            }
            if (aking[0] == px && aking[1] == py && aking[2] != -1 && aking[5] == 1)
            {
                reset();
                is_a_king = 1;
                aking[3] = 1;

            }
            for (int k = 0; k < abc; k++) {
                if (abishop[k][0] == px && abishop[k][1] == py && abishop[k][2] != -1 && abishop[k][4] == 1) {
                    reset();
                    is_a_bishop = 1;
                    abishop[k][3] = 1;
                }
            }
            for (int k = 0; k < arc; k++) {
                if (arook[k][0] == px && arook[k][1] == py && arook[k][2] != -1 && arook[k][5] == 1) {
                    reset();
                    is_a_rook = 1;
                    arook[k][3] = 1;
                }
            }
            for (int k = 0; k < aknc; k++) {
                if (aknight[k][0] == px && aknight[k][1] == py && aknight[k][2] != -1 && aknight[k][4] == 1) {
                    reset();
                    is_a_knight = 1;
                    aknight[k][3] = 1;
                }
            }
            for (int k = 0; k < aqc; k++) {
                if (aqueen[k][0] == px && aqueen[k][1] == py && aqueen[k][2] != -1 && aqueen[k][4] == 1) {
                    reset();
                    is_a_queen = 1;
                    aqueen[k][3] = 1;
                }
            }
        }
        if (is_a_knight == 1 || is_a_rook == 1 || is_a_bishop == 1 || is_a_queen == 1 || is_a_king == 1 || is_a_pawn == 1) {
            m++;
            px1 = px;
            py1 = py;
        }
    }
    public void modulo_1()
    {
        for(int k=0;k<8;k++)
        {
            if(apawn[k][3]==1)
            {
                a_pawn(k);
            }
        }
        if(aking[3]==1)
        {
            a_king();
        }
        for(int k=0;k<10;k++)
        {
            if(abishop[k][3]==1)
            {
                a_bishop(k);
            }
            if(arook[k][3]==1)
            {
                a_rook(k);
            }
            if(aknight[k][3]==1)
            {
                a_knight(k);
            }
            if(aqueen[k][3]==1)
            {
                a_queen(k);
            }
        }
        if(is_a_knight==-1||is_a_rook==-1||is_a_bishop==-1||is_a_queen==-1||is_a_king==-1||is_a_pawn==-1)
        {
            m++;
            occupied_a[px-1][py-1]=1;
            occupied_b[px-1][py-1]=0;
            occupied_a[px1-1][py1-1]=0;
        }
    }
    public void modulo_2()
    {
        b_count=0;
        for(int k=0;k<8;k++)
        {
            if (bpawn[k][4] == 1)
                bpawn[k][4] = -1;//preventing en passant capture if not used immediately
        }
        if(b_check(bking[0],bking[1])==1) {
            for (int k = 0; k < 8; k++) {
                if (bpawn[k][0] == px && bpawn[k][1] == py && bpawn[k][2] != -1) {
                    reset();
                    is_b_pawn = 1;
                    bpawn[k][3] = 1;
                }
            }
            if (bking[0] == px && bking[1] == py && bking[2] != -1) {
                reset();
                is_b_king = 1;
                bking[3] = 1;
            }
            for (int k = 0; k < 10; k++) {
                if (bbishop[k][0] == px && bbishop[k][1] == py && bbishop[k][2] != -1) {
                    reset();
                    is_b_bishop = 1;
                    bbishop[k][3] = 1;
                }

                if (brook[k][0] == px && brook[k][1] == py && brook[k][2] != -1) {
                    reset();
                    is_b_rook = 1;
                    brook[k][3] = 1;
                }
                if (bknight[k][0] == px && bknight[k][1] == py && bknight[k][2] != -1) {
                    reset();
                    is_b_knight = 1;
                    bknight[k][3] = 1;
                }
                if (bqueen[k][0] == px && bqueen[k][1] == py && bqueen[k][2] != -1) {
                    reset();
                    is_b_queen = 1;
                    bqueen[k][3] = 1;
                }
            }
        }
        else
        {
            b_checkmate();
            for (int k = 0; k < 8; k++) {
                if (bpawn[k][0] == px && bpawn[k][1] == py && bpawn[k][2] != -1 && bpawn[k][5] == 1) {
                    reset();
                    is_b_pawn = 1;
                    bpawn[k][3] = 1;
                }
            }
            if (bking[0] == px && bking[1] == py && bking[2] != -1 && bking[5] == 1) {
                reset();
                is_b_king = 1;
                bking[3] = 1;
            }
            for (int k = 0; k < 10; k++) {
                if (bbishop[k][0] == px && bbishop[k][1] == py && bbishop[k][2] != -1 && bbishop[k][4] == 1) {
                    reset();
                    is_b_bishop = 1;
                    bbishop[k][3] = 1;
                }

                if (brook[k][0] == px && brook[k][1] == py && brook[k][2] != -1 && brook[k][5] == 1) {
                    reset();
                    is_b_rook = 1;
                    brook[k][3] = 1;
                }
                if (bknight[k][0] == px && bknight[k][1] == py && bknight[k][2] != -1 && bknight[k][4] == 1) {
                    reset();
                    is_b_knight = 1;
                    bknight[k][3] = 1;
                }
                if (bqueen[k][0] == px && bqueen[k][1] == py && bqueen[k][2] != -1 && bqueen[k][4] == 1) {
                    reset();
                    is_b_queen = 1;
                    bqueen[k][3] = 1;
                }
            }
        }
        if (is_b_knight == 1 || is_b_rook == 1 || is_b_bishop == 1 || is_b_queen == 1 || is_b_king == 1 || is_b_pawn == 1) {
            m++;
            px1 = px;
            py1 = py;
        }
    }
    public void modulo_3()
    {
        for(int k=0;k<8;k++)
        {
            if(bpawn[k][3]==1)
            {
                b_pawn(k);
            }
        }
        if(bking[3]==1)
        {
            b_king();
        }
        for(int k=0;k<10;k++)
        {
            if(bbishop[k][3]==1)
            {
                b_bishop(k);
            }

            if(brook[k][3]==1)
            {
                b_rook(k);
            }
            if(bknight[k][3]==1)
            {
                b_knight(k);
            }
            if(bqueen[k][3]==1)
            {
                b_queen(k);
            }
        }
        if(is_b_knight==-1||is_b_rook==-1||is_b_bishop==-1||is_b_queen==-1||is_b_king==-1||is_b_pawn==-1)
        {
            m++;
            occupied_b[px-1][py-1]=1;
            occupied_a[px-1][py-1]=0;
            occupied_b[px1-1][py1-1]=0;
        }
    }
    public void a_pawn(int k) {
        if(apawn[k][2]==0) {
            pawn_a_count[k]=0;
            set();
            int z=-1;
            if(a_check(aking[0],aking[1])==1)
            {

                int x = apawn[k][0];
                int y = apawn[k][1];
                if (x>0&&y>1&&occupied_a[x - 1][y - 2] == 0 && occupied_b[x - 1][y - 2] == 0) {
                    occupied_a[apawn[k][0]-1][apawn[k][1]-1] = 0;
                    apawn[k][0]=x;
                    apawn[k][1]=y-1;
                    occupied_a[apawn[k][0]-1][apawn[k][1]-1] = 1;
                    if(a_check(aking[0],aking[1])==1)
                    {
                        pawn_a_moves[k][pawn_a_count[k]][0] = x;
                        pawn_a_moves[k][pawn_a_count[k]][1] = y - 1;
                        pawn_a_count[k]++;
                    }
                    re_set();
                    if (apawn[k][4] == 0) {
                        if (x>=1&&y>2&&occupied_a[x - 1][y - 3] == 0 && occupied_b[x - 1][y - 3] == 0) {
                            occupied_a[apawn[k][0]-1][apawn[k][1]-1] = 0;
                            apawn[k][0]=x;
                            apawn[k][1]=y-2;
                            occupied_a[apawn[k][0]-1][apawn[k][1]-1] = 1;
                            if(a_check(aking[0],aking[1])==1)
                            {
                                pawn_a_moves[k][pawn_a_count[k]][0] = x;
                                pawn_a_moves[k][pawn_a_count[k]][1] = y - 2;
                                pawn_a_count[k]++;
                            }
                            re_set();
                        }
                    }
                }
                //en passant
                if (x < 8 && y > 0 && occupied_b[x][y - 1] == 1) {
                    for (int i = 0; i < 8; i++) {
                        if (bpawn[i][0] == x + 1 && bpawn[i][1] == y && bpawn[i][4] == 1) {
                            occupied_a[apawn[k][0]-1][apawn[k][1]-1] = 0;
                            apawn[k][0]=x+1;
                            apawn[k][1]=y-1;
                            bpawn[i][2]=-1;
                            occupied_a[apawn[k][0]-1][apawn[k][1]-1] = 1;
                            if(a_check(aking[0],aking[1])==1)
                            {
                                pawn_a_moves[k][pawn_a_count[k]][0] = x + 1;
                                pawn_a_moves[k][pawn_a_count[k]][1] = y - 1;
                                pawn_a_moves[k][pawn_a_count[k]][2] = 1;
                                pawn_a_count[k]++;
                                z=1;
                            }
                            re_set();
                        }
                    }
                }
                if (x > 1 && y > 0 && occupied_b[x - 2][y - 1] == 1) {
                    for (int i = 0; i < 8; i++) {
                        if (bpawn[i][0] == x - 1 && bpawn[i][1] == y && bpawn[i][4] == 1) {
                            occupied_a[apawn[k][0]-1][apawn[k][1]-1] = 0;
                            apawn[k][0]=x-1;
                            apawn[k][1]=y-1;
                            bpawn[i][2]=-1;
                            occupied_a[apawn[k][0]-1][apawn[k][1]-1] = 1;
                            if(a_check(aking[0],aking[1])==1)
                            {
                                pawn_a_moves[k][pawn_a_count[k]][0] = x - 1;
                                pawn_a_moves[k][pawn_a_count[k]][1] = y - 1;
                                pawn_a_moves[k][pawn_a_count[k]][2] = 1;
                                pawn_a_count[k]++;
                                z=2;
                            }
                            re_set();
                        }
                    }
                }
                //end
                if (x < 8 && occupied_b[x][y - 2] == 1) {
                    occupied_a[apawn[k][0]-1][apawn[k][1]-1] = 0;
                    apawn[k][0]=x+1;
                    apawn[k][1]=y-1;
                    a_kill_b(x+1,y-1);
                    occupied_a[apawn[k][0]-1][apawn[k][1]-1] = 1;
                    if(a_check(aking[0],aking[1])==1)
                    {
                        pawn_a_moves[k][pawn_a_count[k]][0] = x + 1;
                        pawn_a_moves[k][pawn_a_count[k]][1] = y - 1;
                        pawn_a_count[k]++;
                    }
                    re_set();
                }
                if (x > 1 && occupied_b[x - 2][y - 2] == 1) {
                    occupied_a[apawn[k][0]-1][apawn[k][1]-1] = 0;
                    apawn[k][0]=x-1;
                    apawn[k][1]=y-1;
                    a_kill_b(x-1,y-1);
                    occupied_a[apawn[k][0]-1][apawn[k][1]-1] = 1;
                    if(a_check(aking[0],aking[1])==1)
                    {
                        pawn_a_moves[k][pawn_a_count[k]][0] = x - 1;
                        pawn_a_moves[k][pawn_a_count[k]][1] = y - 1;
                        pawn_a_count[k]++;
                    }
                    re_set();
                }
                for(int i=0;i<pawn_a_count[k];i++)
                {
                    if(z_val==0 && px == pawn_a_moves[k][i][0] && py == pawn_a_moves[k][i][1])
                    {
                        apawn[k][0]=px;
                        apawn[k][1]=py;
                        is_a_pawn = -1;
                        if(py1-py==2)
                        {
                            apawn[k][4]=1;
                        }
                        if(py1-py==1)
                        {
                            apawn[k][4]=-1;
                        }
                        a_kill_b(px,py);
                        occupied_a[px-1][py-1]=1;
                        if(px-py==px1-py1||px+py==px1+py1)
                        {
                            if (z == 1) {
                                a_kill_b(x + 1, y);//capture
                                z = -1;
                            }
                            if (z == 2) {
                                a_kill_b(x - 1, y);//capture
                                z = -1;
                            }
                        }
                    }
                }
            }
            else
            {
                for(int i=0;i<a_pawn_count[k];i++)
                {
                    if(px == a_pawn_moves[k][i][0] && py == a_pawn_moves[k][i][1])
                    {
                        apawn[k][0]=px;
                        apawn[k][1]=py;
                        if(py1-py==2)
                        {
                            apawn[k][4]=1;
                        }
                        if(py1-py==1)
                        {
                            apawn[k][4]=-1;
                        }
                        is_a_pawn = -1;
                        a_kill_b(px,py);
                        occupied_a[px-1][py-1]=1;
                    }
                }
            }
            if (z_val==0 && apawn[k][1] == 1 && (bking[0] != apawn[k][0] || bking[1] != apawn[k][1])) {
                x_val = apawn[k][0];
                apawn[k][2] = -1;
                switcher = 1;
                select = 0;
            }
        }
    }
    public void a_bishop(int ind) {
        if (abishop[ind][2] == 0) {
            set();
            if(a_check(aking[0],aking[1])==1)
            {
                bishop_a_count[ind]=0;
                int x = abishop[ind][0];
                int y = abishop[ind][1];
                //diagonal "1"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i - j == x - y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 0;
                                abishop[ind][0] = i;
                                abishop[ind][1] = j;
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 1;
                                if (a_check(aking[0], aking[1]) == 1) {
                                    bishop_a_moves[ind][bishop_a_count[ind]][0] = i;
                                    bishop_a_moves[ind][bishop_a_count[ind]][1] = j;
                                    bishop_a_count[ind]++;
                                }
                                re_set();
                            } else if(occupied_b[i - 1][j - 1] == 1) {
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 0;
                                abishop[ind][0] = i;
                                abishop[ind][1] = j;
                                a_kill_b(abishop[ind][0], abishop[ind][1]);
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 1;
                                if (a_check(aking[0], aking[1]) == 1) {
                                    bishop_a_moves[ind][bishop_a_count[ind]][0] = i;
                                    bishop_a_moves[ind][bishop_a_count[ind]][1] = j;
                                    bishop_a_count[ind]++;
                                }
                                re_set();
                                con = -1;
                                break;
                            }
                            else
                            {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                con = 0;
                //diagonal "2"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i - j == x - y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 0;
                                abishop[ind][0] = i;
                                abishop[ind][1] = j;
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 1;
                                if (a_check(aking[0], aking[1]) == 1) {
                                    bishop_a_moves[ind][bishop_a_count[ind]][0] = i;
                                    bishop_a_moves[ind][bishop_a_count[ind]][1] = j;
                                    bishop_a_count[ind]++;
                                }
                                re_set();
                            } else if(occupied_b[i - 1][j - 1] == 1) {
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 0;
                                abishop[ind][0] = i;
                                abishop[ind][1] = j;
                                a_kill_b(abishop[ind][0], abishop[ind][1]);
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 1;
                                if (a_check(aking[0], aking[1]) == 1) {
                                    bishop_a_moves[ind][bishop_a_count[ind]][0] = i;
                                    bishop_a_moves[ind][bishop_a_count[ind]][1] = j;
                                    bishop_a_count[ind]++;
                                }
                                re_set();
                                con = -1;
                                break;
                            }
                            else
                            {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                con = 0;
                //diagonal "3"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i + j == x + y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 0;
                                abishop[ind][0] = i;
                                abishop[ind][1] = j;
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 1;
                                if (a_check(aking[0], aking[1]) == 1) {
                                    bishop_a_moves[ind][bishop_a_count[ind]][0] = i;
                                    bishop_a_moves[ind][bishop_a_count[ind]][1] = j;
                                    bishop_a_count[ind]++;
                                }
                                re_set();
                            } else if(occupied_b[i - 1][j - 1] == 1) {
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 0;
                                abishop[ind][0] = i;
                                abishop[ind][1] = j;
                                a_kill_b(abishop[ind][0], abishop[ind][1]);
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 1;
                                if (a_check(aking[0], aking[1]) == 1) {
                                    bishop_a_moves[ind][bishop_a_count[ind]][0] = i;
                                    bishop_a_moves[ind][bishop_a_count[ind]][1] = j;
                                    bishop_a_count[ind]++;
                                }
                                re_set();
                                con = -1;
                                break;
                            }
                            else
                            {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                con = 0;
                //diagonal "4"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i + j == x + y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 0;
                                abishop[ind][0] = i;
                                abishop[ind][1] = j;
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 1;
                                if (a_check(aking[0], aking[1]) == 1) {
                                    bishop_a_moves[ind][bishop_a_count[ind]][0] = i;
                                    bishop_a_moves[ind][bishop_a_count[ind]][1] = j;
                                    bishop_a_count[ind]++;
                                }
                                re_set();
                            } else if(occupied_b[i - 1][j - 1] == 1) {
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 0;
                                abishop[ind][0] = i;
                                abishop[ind][1] = j;
                                a_kill_b(abishop[ind][0], abishop[ind][1]);
                                occupied_a[abishop[ind][0] - 1][abishop[ind][1] - 1] = 1;
                                if (a_check(aking[0], aking[1]) == 1) {
                                    bishop_a_moves[ind][bishop_a_count[ind]][0] = i;
                                    bishop_a_moves[ind][bishop_a_count[ind]][1] = j;
                                    bishop_a_count[ind]++;
                                }
                                re_set();
                                con = -1;
                                break;
                            }
                            else
                            {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }for (int i = 0; i < bishop_a_count[ind]; i++)
            {
                if(z_val==0 && px==bishop_a_moves[ind][i][0]&&py==bishop_a_moves[ind][i][1])
                {
                    abishop[ind][0] = px;
                    abishop[ind][1] = py;
                    a_kill_b(abishop[ind][0],abishop[ind][1]);
                    is_a_bishop = -1;
                }
            }
            }
            else{
                for (int i = 0; i < a_bishop_count[ind]; i++)
                {
                    if(px==a_bishop_moves[ind][i][0]&&py==a_bishop_moves[ind][i][1])
                    {
                        if (occupied_b[px - 1][py - 1] == 0) {
                            abishop[ind][0] = px;
                            abishop[ind][1] = py;
                            is_a_bishop = -1;
                        } else {
                            abishop[ind][0] = px;
                            abishop[ind][1] = py;
                            a_kill_b(abishop[ind][0],abishop[ind][1]);
                            is_a_bishop = -1;
                        }
                    }
                }
            }
        }
    }
    public void a_rook(int ind) {
        if (arook[ind][2] == 0) {
            set();
            if (a_check(aking[0], aking[1]) == 1)
            {
                rook_a_count[ind]=0;
                int x = arook[ind][0];
                int y = arook[ind][1];
                int z = 1;
                k = 0;
                for (int i = x - 1; i > 0; i--) {
                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 0;
                        arook[ind][0] = i;
                        arook[ind][1] = y;
                        occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 1;
                        if (a_check(aking[0], aking[1]) == 1) {
                            rook_a_moves[ind][rook_a_count[ind]][0] = i;
                            rook_a_moves[ind][rook_a_count[ind]][1] = y;
                            rook_a_count[ind]++;
                        }
                        re_set();
                        k++;
                    }
                    else {
                        z=i;
                        break;
                    }
                }
                if ( z >= 1 && y >= 1 && occupied_b[z - 1][y - 1] == 1) {
                    occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 0;
                    arook[ind][0] = z;
                    arook[ind][1] = y;
                    a_kill_b(z, y);
                    occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        rook_a_moves[ind][rook_a_count[ind]][0] = z;
                        rook_a_moves[ind][rook_a_count[ind]][1] = y;
                        rook_a_count[ind]++;
                    }
                    re_set();
                }
                k = 0;
                for (int i = y - 1; i > 0; i--) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 0;
                        arook[ind][0] = x;
                        arook[ind][1] = i;
                        occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 1;
                        if (a_check(aking[0], aking[1]) == 1) {
                            rook_a_moves[ind][rook_a_count[ind]][0] = x;
                            rook_a_moves[ind][rook_a_count[ind]][1] = i;
                            rook_a_count[ind]++;
                        }
                        re_set();
                        k++;
                    }
                    else {
                        z=i;
                        break;
                    }
                }
                if (x >= 1 && z >= 1 && occupied_b[x - 1][z-1] == 1) {
                    occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 0;
                    arook[ind][0] = x;
                    arook[ind][1] = z;
                    a_kill_b(x, z);
                    occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        rook_a_moves[ind][rook_a_count[ind]][0] = x;
                        rook_a_moves[ind][rook_a_count[ind]][1] = z;
                        rook_a_count[ind]++;
                    }
                    re_set();
                }
                k = 0;
                for (int i = x + 1; i < 9; i++) {
                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 0;
                        arook[ind][0] = i;
                        arook[ind][1] = y;
                        occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 1;
                        if (a_check(aking[0], aking[1]) == 1) {
                            rook_a_moves[ind][rook_a_count[ind]][0] = i;
                            rook_a_moves[ind][rook_a_count[ind]][1] = y;
                            rook_a_count[ind]++;
                        }
                        re_set();
                        k++;
                    }
                    else {
                        z=i;
                        break;
                    }
                }
                if (y >= 1 && z >= 1 && occupied_b[z-1][y - 1] == 1) {
                    occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 0;
                    arook[ind][0] = z;
                    arook[ind][1] = y;
                    a_kill_b(z, y);
                    occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        rook_a_moves[ind][rook_a_count[ind]][0] = z;
                        rook_a_moves[ind][rook_a_count[ind]][1] = y;
                        rook_a_count[ind]++;
                    }
                    re_set();
                }
                k = 0;
                for (int i = y + 1; i < 9; i++) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 0;
                        arook[ind][0] = x;
                        arook[ind][1] = i;
                        occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 1;
                        if (a_check(aking[0], aking[1]) == 1) {
                            rook_a_moves[ind][rook_a_count[ind]][0] = x;
                            rook_a_moves[ind][rook_a_count[ind]][1] = i;
                            rook_a_count[ind]++;
                        }
                        re_set();
                        k++;
                    }
                    else {
                        z=i;
                        break;
                    }
                }
                if (x >= 1 && z >= 1 && occupied_b[x - 1][z-1] == 1) {
                    occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 0;
                    arook[ind][0] = x;
                    arook[ind][1] = z;
                    a_kill_b(x, z);
                    occupied_a[arook[ind][0] - 1][arook[ind][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        rook_a_moves[ind][rook_a_count[ind]][0] = x;
                        rook_a_moves[ind][rook_a_count[ind]][1] = z;
                        rook_a_count[ind]++;
                    }
                    re_set();
                }
                for(int i=0;i<rook_a_count[ind];i++)
                {
                    if(z_val==0 && px==rook_a_moves[ind][i][0]&&py==rook_a_moves[ind][i][1])
                    {
                        arook[ind][0]=px;
                        arook[ind][1]=py;
                        arook[ind][4]=-1;
                        a_kill_b(px,py);
                        is_a_rook=-1;
                    }
                }
            }
            else {
                for (int i = 0; i < a_rook_count[ind]; i++) {
                    if (px == a_rook_moves[ind][i][0] && py == a_rook_moves[ind][i][1])
                    {
                        if (occupied_b[px - 1][py - 1] == 0)
                        {
                            arook[ind][0] = px;
                            arook[ind][1] = py;
                            arook[ind][4] = -1;
                            is_a_rook = -1;
                        }
                        else {
                            arook[ind][4] = -1;
                            arook[ind][0] = px;
                            arook[ind][1] = py;
                            a_kill_b(px,py);
                            is_a_rook = -1;
                        }
                    }
                }
            }
        }
    }
    public void a_knight(int k) {
        if (aknight[k][2] == 0) {
            set();
            if (a_check(aking[0], aking[1]) == 1) {
                knight_a_count[k] = 0;
                int kx[] = new int[4];
                int ky[] = new int[4];
                int x = aknight[k][0];
                int y = aknight[k][1];
                kx[0] = x - 1;
                kx[1] = x + 1;
                kx[2] = x - 2;
                kx[3] = x + 2;

                ky[0] = y - 2;
                ky[1] = y - 1;
                ky[2] = y + 1;
                ky[3] = y + 2;
                if (kx[0] > 0 && ky[0] > 0 && kx[0] < 9 && ky[0] < 9 && occupied_a[kx[0] - 1][ky[0] - 1] == 0) {
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 0;
                    aknight[k][0] = kx[0];
                    aknight[k][1] = ky[0];
                    a_kill_b(aknight[k][0], aknight[k][1]);
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        knight_a_moves[k][knight_a_count[k]][0] = kx[0];
                        knight_a_moves[k][knight_a_count[k]][1] = ky[0];
                        knight_a_count[k]++;
                    }
                    re_set();
                }
                if (kx[1] > 0 && ky[0] > 0 && kx[1] < 9 && ky[0] < 9 && occupied_a[kx[1] - 1][ky[0] - 1] == 0) {
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 0;
                    aknight[k][0] = kx[1];
                    aknight[k][1] = ky[0];
                    a_kill_b(aknight[k][0], aknight[k][1]);
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        knight_a_moves[k][knight_a_count[k]][0] = kx[1];
                        knight_a_moves[k][knight_a_count[k]][1] = ky[0];
                        knight_a_count[k]++;
                    }
                    re_set();
                }
                if (kx[2] > 0 && ky[1] > 0 && kx[2] < 9 && ky[1] < 9 && occupied_a[kx[2] - 1][ky[1] - 1] == 0) {
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 0;
                    aknight[k][0] = kx[2];
                    aknight[k][1] = ky[1];
                    a_kill_b(aknight[k][0], aknight[k][1]);
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        knight_a_moves[k][knight_a_count[k]][0] = kx[2];
                        knight_a_moves[k][knight_a_count[k]][1] = ky[1];
                        knight_a_count[k]++;
                    }
                    re_set();
                }
                if (kx[2] > 0 && ky[2] > 0 && kx[2] < 9 && ky[2] < 9 && occupied_a[kx[2] - 1][ky[2] - 1] == 0) {
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 0;
                    aknight[k][0] = kx[2];
                    aknight[k][1] = ky[2];
                    a_kill_b(aknight[k][0], aknight[k][1]);
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        knight_a_moves[k][knight_a_count[k]][0] = kx[2];
                        knight_a_moves[k][knight_a_count[k]][1] = ky[2];
                        knight_a_count[k]++;
                    }
                    re_set();
                }
                if (kx[0] > 0 && ky[3] > 0 && kx[0] < 9 && ky[3] < 9 && occupied_a[kx[0] - 1][ky[3] - 1] == 0) {
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 0;
                    aknight[k][0] = kx[0];
                    aknight[k][1] = ky[3];
                    a_kill_b(aknight[k][0], aknight[k][1]);
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        knight_a_moves[k][knight_a_count[k]][0] = kx[0];
                        knight_a_moves[k][knight_a_count[k]][1] = ky[3];
                        knight_a_count[k]++;
                    }
                    re_set();
                }
                if (kx[1] > 0 && ky[3] > 0 && kx[1] < 9 && ky[3] < 9 && occupied_a[kx[1] - 1][ky[3] - 1] == 0) {
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 0;
                    aknight[k][0] = kx[1];
                    aknight[k][1] = ky[3];
                    a_kill_b(aknight[k][0], aknight[k][1]);
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        knight_a_moves[k][knight_a_count[k]][0] = kx[1];
                        knight_a_moves[k][knight_a_count[k]][1] = ky[3];
                        knight_a_count[k]++;
                    }
                    re_set();
                }
                if (kx[3] > 0 && ky[1] > 0 && kx[3] < 9 && ky[1] < 9 && occupied_a[kx[3] - 1][ky[1] - 1] == 0) {
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 0;
                    aknight[k][0] = kx[3];
                    aknight[k][1] = ky[1];
                    a_kill_b(aknight[k][0], aknight[k][1]);
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        knight_a_moves[k][knight_a_count[k]][0] = kx[3];
                        knight_a_moves[k][knight_a_count[k]][1] = ky[1];
                        knight_a_count[k]++;
                    }
                    re_set();
                }
                if (kx[3] > 0 && ky[2] > 0 && kx[3] < 9 && ky[2] < 9 && occupied_a[kx[3] - 1][ky[2] - 1] == 0) {
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 0;
                    aknight[k][0] = kx[3];
                    aknight[k][1] = ky[2];
                    a_kill_b(aknight[k][0], aknight[k][1]);
                    occupied_a[aknight[k][0] - 1][aknight[k][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        knight_a_moves[k][knight_a_count[k]][0] = kx[3];
                        knight_a_moves[k][knight_a_count[k]][1] = ky[2];
                        knight_a_count[k]++;
                    }
                    re_set();
                }
                for (int i = 0; i < knight_a_count[k]; i++) {
                    if (z_val==0 && px == knight_a_moves[k][i][0] && py == knight_a_moves[k][i][1]) {
                        aknight[k][0] = px;
                        aknight[k][1] = py;
                        a_kill_b(px, py);
                        is_a_knight = -1;
                    }
                }
            }
            else {
                for (int i = 0; i < a_knight_count[k]; i++) {
                    if (px == a_knight_moves[k][i][0] && py == a_knight_moves[k][i][1]) {
                        if (occupied_a[px - 1][py - 1] != 1 && occupied_b[px - 1][py - 1] != 1) {
                            aknight[k][0] = px;
                            aknight[k][1] = py;
                            is_a_knight = -1;
                        } else if (occupied_b[px - 1][py - 1] == 1) {
                            aknight[k][0] = px;
                            aknight[k][1] = py;
                            a_kill_b(px, py);
                            is_a_knight = -1;
                        }
                    }
                }
            }
        }
    }
    public void a_king()//hii
    {
        king_a_count=0;
        int castling=-1;
        int ok=0;
        if(aking[2]==0) {
            int x=aking[0];
            int y=aking[1];
            int qwe=0;
            z=0;
            //constant y
            set();
            if(x>1&&y>0&&occupied_a[x-2][y-1]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=x-1;
                aking[1]=y;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                ok=a_check(x-1,y);
                if(ok==1)
                {
                    king_a_moves[king_a_count][0]=x-1;
                    king_a_moves[king_a_count][1]=y;
                    king_a_count++;
                }
                re_set();
            }
            if(x<8&&y>0&&occupied_a[x][y-1]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=x+1;
                aking[1]=y;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                ok=a_check(x+1,y);
                if(ok==1)
                {
                    king_a_moves[king_a_count][0]=x+1;
                    king_a_moves[king_a_count][1]=y;
                    king_a_count++;
                }
                re_set();
            }
            //constant x
            if(y>1&&x>0&&occupied_a[x-1][y-2]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=x;
                aking[1]=y-1;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                ok=a_check(x,y-1);
                if(ok==1)
                {
                    king_a_moves[king_a_count][0]=x;
                    king_a_moves[king_a_count][1]=y-1;
                    king_a_count++;
                }
                re_set();
            }
            if(y<8&&x>0&&occupied_a[x-1][y]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=x;
                aking[1]=y+1;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                ok=a_check(x,y+1);
                if(ok==1)
                {
                    king_a_moves[king_a_count][0]=x;
                    king_a_moves[king_a_count][1]=y+1;
                    king_a_count++;
                }
                re_set();
            }
            //diagonal 1-
            if(x>1&&y>1&&occupied_a[x-2][y-2]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=x-1;
                aking[1]=y-1;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                ok=a_check(x-1,y-1);
                if(ok==1)
                {
                    king_a_moves[king_a_count][0]=x-1;
                    king_a_moves[king_a_count][1]=y-1;
                    king_a_count++;
                }
                re_set();
            }
            //diagonal 2-
            if(x<8&&y<8&&occupied_a[x][y]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=x+1;
                aking[1]=y+1;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                ok=a_check(x+1,y+1);
                if(ok==1)
                {
                    king_a_moves[king_a_count][0]=x+1;
                    king_a_moves[king_a_count][1]=y+1;
                    king_a_count++;
                }
                re_set();
            }
            //diagonal 3-
            if(x>1&&y<8&&occupied_a[x-2][y]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=x-1;
                aking[1]=y+1;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                ok=a_check(x-1,y+1);
                if(ok==1)
                {
                    king_a_moves[king_a_count][0]=x-1;
                    king_a_moves[king_a_count][1]=y+1;
                    king_a_count++;
                }
                re_set();
            }
            //diagonal 4-
            if(x<8&&y>1&&occupied_a[x][y-2]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=x+1;
                aking[1]=y-1;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                ok=a_check(x+1,y-1);
                if(ok==1)
                {
                    king_a_moves[king_a_count][0]=x+1;
                    king_a_moves[king_a_count][1]=y-1;
                    king_a_count++;
                }
                re_set();
            }
            //castling
            if (aking[4] == 0 && a_check(aking[0], aking[1]) == 1) {
                if (arook[0][4] == 0 && arook[0][2] == 0) {
                    for (int i = arook[0][0] + 1; i < aking[0]; i++) {
                        if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0 && a_check(i, y) == 1) {
                            qwe = 1;
                        } else {
                            qwe = -1;
                            break;
                        }
                    }
                    if (qwe == 1) {
                        king_a_moves[king_a_count][0] = x - 2;
                        king_a_moves[king_a_count][1] = y;
                        king_a_count++;
                        castling = 1;
                    }
                }
                if (arook[1][4] == 0 && arook[1][2] == 0) {
                    for (int i = aking[0] + 1; i < arook[1][0]; i++) {
                        if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0 && a_check(i, y) == 1) {
                            qwe = 1;
                        } else {
                            qwe = -1;
                            break;
                        }
                    }
                    if (qwe == 1) {
                        king_a_moves[king_a_count][0] = x + 2;
                        king_a_moves[king_a_count][1] = y;
                        king_a_count++;
                        castling = 1;
                    }
                }
            }
            for (int i = 0; i < king_a_count; i++) {
                if (z_val==0 && px == king_a_moves[i][0] && py == king_a_moves[i][1]) {
                    aking[4] = -1;
                    aking[0] = px;
                    aking[1] = py;
                    if(castling==1 && px1-px==2)
                    {
                        occupied_a[arook[0][0]-1][arook[0][1]-1]=0;
                        arook[0][0]=px+1;
                        arook[0][1]=py;
                        occupied_a[arook[0][0]-1][arook[0][1]-1]=1;
                    }
                    if(castling==1 && px-px1==2)
                    {
                        occupied_a[arook[1][0]-1][arook[1][1]-1]=0;
                        arook[1][0]=px-1;
                        arook[1][1]=py;
                        occupied_a[arook[1][0]-1][arook[1][1]-1]=1;
                    }
                    a_kill_b(px, py);
                    is_a_king = -1;
                }
            }
        }
    }
    /*
        Castling is a special type of chess move. When castling, you simultaneously move your king and one of your rooks.
        The king moves two squares towards a rook, and that rook moves to the square on the other side of the king.

        ****Castling RULES-
        Your king has been moved earlier in the game.
        The rook that you would castle with has been moved earlier in the game.
        There are pieces standing between your king and rook.
        The king is in check.
        The king moves through a square that is attacked by a piece of the opponent.
        The king would be in check after castling.
        */
    public boolean increment()
    {
        if(a_check(aking[0],aking[1])==1)
        {
            return true;
        }
        else
            return false;
    }
    public boolean increment1()
    {
        if(b_check(bking[0],bking[1])==1)
        {
            return true;
        }
        else
            return false;
    }
    //QUEEN
    public void a_queen(int wq) {
        int k = 0;
        if (aqueen[wq][2] == 0) {
            set();
            if(a_check(aking[0],aking[1])==1)
            {
                queen_a_count[wq]=0;
                int x = aqueen[wq][0];
                int y = aqueen[wq][1];
                //constant y-
                k=0;
                for (int i = x + 1; i < 9; i++) {
                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                        aqueen[wq][0] = i;
                        aqueen[wq][1] = y;
                        occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                        if (a_check(aking[0], aking[1]) == 1) {
                            queen_a_moves[wq][queen_a_count[wq]][0] = i;
                            queen_a_moves[wq][queen_a_count[wq]][1] = y;
                            queen_a_count[wq]++;
                        }
                        re_set();
                        k++;
                    }
                    else {
                        break;
                    }
                }
                if (x + k < 8 && y > 0 && occupied_b[x + k][y - 1] == 1) {
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                    aqueen[wq][0] = x + k + 1;
                    aqueen[wq][1] = y;
                    a_kill_b(aqueen[wq][0], aqueen[wq][1]);
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        queen_a_moves[wq][queen_a_count[wq]][0] = x+k+1;
                        queen_a_moves[wq][queen_a_count[wq]][1] = y;
                        queen_a_count[wq]++;
                    }
                    re_set();
                }
                k = 0;
                for (int i = x - 1; i > 0; i--) {
                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                        aqueen[wq][0] = i;
                        aqueen[wq][1] = y;
                        occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                        if (a_check(aking[0], aking[1]) == 1) {
                            queen_a_moves[wq][queen_a_count[wq]][0] = i;
                            queen_a_moves[wq][queen_a_count[wq]][1] = y;
                            queen_a_count[wq]++;
                        }
                        k++;
                        re_set();
                    }
                    else {
                        break;
                    }
                }
                if (x > k + 1 && y > 0 && occupied_b[x - k - 2][y - 1] == 1) {
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                    aqueen[wq][0] = x - k - 1;
                    aqueen[wq][1] = y;
                    a_kill_b(aqueen[wq][0], aqueen[wq][1]);
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        queen_a_moves[wq][queen_a_count[wq]][0] = x-k-1;
                        queen_a_moves[wq][queen_a_count[wq]][1] = y;
                        queen_a_count[wq]++;
                    }
                    re_set();
                }
                k = 0;
                //diagonal "1"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i - j == x - y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                                aqueen[wq][0] = i;
                                aqueen[wq][1] = j;
                                occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                                if (a_check(aking[0], aking[1]) == 1) {
                                    queen_a_moves[wq][queen_a_count[wq]][0] = i;
                                    queen_a_moves[wq][queen_a_count[wq]][1] = j;
                                    queen_a_count[wq]++;
                                }
                                re_set();
                                k++;
                            } else {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                if (x + k < 8 && y + k < 8 && occupied_b[x + k][y + k] == 1) {
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                    aqueen[wq][0] = x + k + 1;
                    aqueen[wq][1] = y + k + 1;
                    a_kill_b(aqueen[wq][0], aqueen[wq][1]);
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        queen_a_moves[wq][queen_a_count[wq]][0] = x+k+1;
                        queen_a_moves[wq][queen_a_count[wq]][1] = y+k+1;
                        queen_a_count[wq]++;
                    }
                    re_set();
                }
                k = 0;
                con = 0;
                //diagonal "2"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i - j == x - y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                                aqueen[wq][0] = i;
                                aqueen[wq][1] = j;
                                occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                                if (a_check(aking[0], aking[1]) == 1) {
                                    queen_a_moves[wq][queen_a_count[wq]][0] = i;
                                    queen_a_moves[wq][queen_a_count[wq]][1] = j;
                                    queen_a_count[wq]++;
                                }
                                re_set();
                                k++;
                            } else {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                if (x > k + 1 && y > k + 1 && occupied_b[x - k - 2][y - k - 2] == 1) {
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                    aqueen[wq][0] = x - k - 1;
                    aqueen[wq][1] = y - k - 1;
                    a_kill_b(aqueen[wq][0], aqueen[wq][1]);
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        queen_a_moves[wq][queen_a_count[wq]][0] = x-k-1;
                        queen_a_moves[wq][queen_a_count[wq]][1] = y-k-1;
                        queen_a_count[wq]++;
                    }
                    re_set();
                }
                k = 0;
                con = 0;
                //diagonal "3"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i + j == x + y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                                aqueen[wq][0] = i;
                                aqueen[wq][1] = j;
                                occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                                if (a_check(aking[0], aking[1]) == 1) {
                                    queen_a_moves[wq][queen_a_count[wq]][0] = i;
                                    queen_a_moves[wq][queen_a_count[wq]][1] = j;
                                    queen_a_count[wq]++;
                                }
                                re_set();
                                k++;
                            } else {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                if (y > k + 1 && x + k < 8 && occupied_b[x + k][y - k - 2] == 1) {
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                    aqueen[wq][0] = x + k + 1;
                    aqueen[wq][1] = y - k - 1;
                    a_kill_b(aqueen[wq][0], aqueen[wq][1]);
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        queen_a_moves[wq][queen_a_count[wq]][0] = x+k+1;
                        queen_a_moves[wq][queen_a_count[wq]][1] = y-k-1;
                        queen_a_count[wq]++;
                    }
                    re_set();
                }
                k = 0;
                con = 0;
                //diagonal "4"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i + j == x + y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                                aqueen[wq][0] = i;
                                aqueen[wq][1] = j;
                                occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                                if (a_check(aking[0], aking[1]) == 1) {
                                    queen_a_moves[wq][queen_a_count[wq]][0] = i;
                                    queen_a_moves[wq][queen_a_count[wq]][1] = j;
                                    queen_a_count[wq]++;
                                }
                                re_set();
                                k++;
                            }
                            else {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                if (x > k + 1 && y + k < 8 && occupied_b[x - k - 2][y + k] == 1) {
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                    aqueen[wq][0] = x - k - 1;
                    aqueen[wq][1] = y + k + 1;
                    a_kill_b(aqueen[wq][0], aqueen[wq][1]);
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        queen_a_moves[wq][queen_a_count[wq]][0] = x-k-1;
                        queen_a_moves[wq][queen_a_count[wq]][1] = y+k+1;
                        queen_a_count[wq]++;
                    }
                    re_set();
                }
                k = 0;
                con = 0;
                //constant x-
                for (int i = y + 1; i < 9; i++) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                        aqueen[wq][0] = x;
                        aqueen[wq][1] = i;
                        occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                        if (a_check(aking[0], aking[1]) == 1) {
                            queen_a_moves[wq][queen_a_count[wq]][0] = x;
                            queen_a_moves[wq][queen_a_count[wq]][1] = i;
                            queen_a_count[wq]++;
                        }
                        k++;
                        re_set();
                    }
                    else {
                        break;
                    }
                }
                if (y + k < 8 && x > 0 && occupied_b[x - 1][y + k] == 1) {
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                    aqueen[wq][0] = x;
                    aqueen[wq][1] = y + k + 1;
                    a_kill_b(aqueen[wq][0],aqueen[wq][1]);
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        queen_a_moves[wq][queen_a_count[wq]][0] = x;
                        queen_a_moves[wq][queen_a_count[wq]][1] = y+k+1;
                        queen_a_count[wq]++;
                    }
                    re_set();
                }
                k = 0;
                for (int i = y - 1; i > 0; i--) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                        aqueen[wq][0] = x;
                        aqueen[wq][1] = i;
                        occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                        if (a_check(aking[0], aking[1]) == 1) {
                            queen_a_moves[wq][queen_a_count[wq]][0] = x;
                            queen_a_moves[wq][queen_a_count[wq]][1] = i;
                            queen_a_count[wq]++;
                        }
                        k++;
                        re_set();
                    }

                    else {
                        break;
                    }
                }
                if (y > k + 1 && x > 0 && occupied_b[x - 1][y - k - 2] == 1)//check condition***********
                {
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 0;
                    aqueen[wq][0] = x;
                    aqueen[wq][1] = y - k - 1;
                    a_kill_b(aqueen[wq][0],aqueen[wq][1]);
                    occupied_a[aqueen[wq][0] - 1][aqueen[wq][1] - 1] = 1;
                    if (a_check(aking[0], aking[1]) == 1) {
                        queen_a_moves[wq][queen_a_count[wq]][0] = x;
                        queen_a_moves[wq][queen_a_count[wq]][1] = y-k-1;
                        queen_a_count[wq]++;
                    }
                    re_set();
                }
                for(int i=0;i<queen_a_count[wq];i++) {
                    if (z_val==0 && px == queen_a_moves[wq][i][0] && py == queen_a_moves[wq][i][1]) {
                        aqueen[wq][0] = px;
                        aqueen[wq][1] = py;
                        a_kill_b(px, py);
                        is_a_queen = -1;
                    }
                }
            }
            else
            {
                for (int x = 0; x < a_queen_count[wq]; x++)
                {
                    if (px == a_queen_moves[wq][x][0] && py == a_queen_moves[wq][x][1])
                    {
                        if (occupied_a[px - 1][py - 1] == 0 && occupied_b[px - 1][py - 1] == 0) {
                            aqueen[wq][0] = px;
                            aqueen[wq][1] = py;
                            occupied_a[px-1][py-1]=1;
                            is_a_queen = -1;
                        } else if (occupied_a[px - 1][py - 1] == 0 && occupied_b[px - 1][py - 1] != 0) {
                            aqueen[wq][0] = px;
                            aqueen[wq][1] = py;
                            a_kill_b(px,py);
                            occupied_a[px-1][py-1]=1;
                            is_a_queen = -1;
                        }
                    }
                }
            }
        }
    }
    public void a_kill_b(int x,int y)
    {
        if(occupied_b[x-1][y-1]==1)
        {
            for(int i=0;i<8;i++)
            {
                if(bpawn[i][2]==0)
                {
                    if(bpawn[i][0]==x && bpawn[i][1]==y)
                    {
                        bpawn[i][2]=-1;
                        occupied_b[x-1][y-1]=0;
                    }
                }
            }
            for(int i=0;i<bqc;i++)
            {
                if(bqueen[i][2]==0)
                {
                    if(bqueen[i][0]==x && bqueen[i][1]==y)
                    {
                        bqueen[i][2]=-1;
                        occupied_b[x-1][y-1]=0;
                    }
                }
            }
            for(int i=0;i<bknc;i++)
            {
                if(bknight[i][2]==0)
                {
                    if(bknight[i][0]==x && bknight[i][1]==y)
                    {
                        bknight[i][2]=-1;
                        occupied_b[x-1][y-1]=0;
                    }
                }
            }
            for(int i=0;i<bbc;i++)
            {
                if(bbishop[i][2]==0)
                {
                    if(bbishop[i][0]==x && bbishop[i][1]==y)
                    {
                        bbishop[i][2]=-1;
                        occupied_b[x-1][y-1]=0;
                    }
                }
            }
            for(int i=0;i<brc;i++)
            {
                if(brook[i][2]==0)
                {
                    if(brook[i][0]==x && brook[i][1]==y)
                    {
                        brook[i][2]=-1;
                        occupied_b[x-1][y-1]=0;
                    }
                }
            }
            if(bking[2]==0 && bking[0]==x && bking[1]==y)
            {
                bking[2]=-1;
                occupied_b[x-1][y-1]=0;
            }
        }
    }
    public int a_check(int kix,int kiy)//To check if King is moving into check
    {
        con = 0;
        int z = 0;
        k = 0;
        int king_x = aking[0];
        int king_y = aking[1];
        int count=0;
        int b_moves[][] = new int[150][2];
        //queen
        for(int wq=0;wq<aqc;wq++)
        {
            if (bqueen[wq][2] == 0) {
                int x = bqueen[wq][0];
                int y = bqueen[wq][1];
                //constant y-
                for (int i = x + 1; i < 9; i++) {

                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        b_moves[z][0] = i;
                        b_moves[z][1] = y;
                        z++;
                    }
                    else if (occupied_a[i - 1][y - 1] == 1) {
                        if (i == king_x && y == king_y) {
                            b_moves[z][0] = i;
                            b_moves[z][1] = y;
                            z++;
                            for (int j = king_x + 1; j < 9; j++) {
                                if(occupied_a[j-1][y-1]==0&&occupied_b[j-1][y-1]==0) {
                                    b_moves[z][0] = j;
                                    b_moves[z][1] = y;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            b_moves[z][0] = i;
                            b_moves[z][1] = y;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
                for (int i = x - 1; i > 0; i--) {

                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        b_moves[z][0] = i;
                        b_moves[z][1] = y;
                        z++;
                    } else if (occupied_a[i - 1][y - 1] == 1)
                    {
                        if (i == king_x && y == king_y){
                            b_moves[z][0] = i;
                            b_moves[z][1] = y;
                            z++;
                            for (int j = king_x - 1; j > 0; j--) {
                                if(occupied_a[j-1][y-1]==0&&occupied_b[j-1][y-1]==0) {
                                    b_moves[z][0] = j;
                                    b_moves[z][1] = y;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            b_moves[z][0] = i;
                            b_moves[z][1] = y;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
                //diagonal "1"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i - j == x - y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            b_moves[z][0] = i;
                            b_moves[z][1] = j;
                            z++;
                        }
                        else if (i - j == x - y && occupied_a[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                b_moves[z][0]=king_x;
                                b_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x + 1; k1 < 9; k1++) {
                                    for (int k2 = king_y + 1; k2 < 9; k2++) {
                                        if (k1 - k2 == king_x - king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                b_moves[z][0] = k1;
                                                b_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                break;
                                        }
                                    }
                                }
                                con = -1;
                            }
                            else {
                                b_moves[z][0] = i;
                                b_moves[z][1] = j;
                                con = -1;
                                z++;
                                break;
                            }
                        }
                        else if (i - j == x - y && occupied_b[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }
                //diagonal "2"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i - j == x - y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            b_moves[z][0] = i;
                            b_moves[z][1] = j;
                            z++;
                        }
                        else if (i - j == x - y && occupied_a[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                b_moves[z][0]=king_x;
                                b_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x - 1; k1 > 0; k1--) {
                                    for (int k2 = king_y - 1; k2 > 0; k2--) {
                                        if (k1 - k2 == king_x - king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                b_moves[z][0] = k1;
                                                b_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                break;
                                        }
                                    }
                                }
                                con = -1;
                            }
                            else {
                                b_moves[z][0] = i;
                                b_moves[z][1] = j;
                                con = -1;
                                z++;
                                break;
                            }
                        }
                        else if (i - j == x - y && occupied_b[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }

                //diagonal "3"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i + j == x + y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            b_moves[z][0] = i;
                            b_moves[z][1] = j;
                            z++;
                        }
                        else if (i + j == x + y && occupied_a[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                b_moves[z][0]=king_x;
                                b_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x + 1; k1 < 9; k1++) {
                                    for (int k2 = king_y - 1; k2 > 0; k2--) {
                                        if (k1 + k2 == king_x + king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                b_moves[z][0] = k1;
                                                b_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                con = -1;
                                            break;
                                        }
                                    }
                                    if(con == -1)
                                    {
                                        con =0;
                                        break;
                                    }
                                }
                                con = -1;
                            }
                            else {
                                b_moves[z][0] = i;
                                b_moves[z][1] = j;
                                con = -1;
                                z++;
                                break;
                            }
                        }
                        else if (i + j == x + y && occupied_b[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }
                //diagonal "4"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i + j == x + y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            b_moves[z][0] = i;
                            b_moves[z][1] = j;
                            z++;
                        }
                        else if (i + j == x + y && occupied_a[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                b_moves[z][0]=king_x;
                                b_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x - 1; k1 > 0; k1--) {
                                    for (int k2 = king_y + 1; k2 < 9; k2++) {
                                        if (k1 + k2 == king_x + king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                b_moves[z][0] = k1;
                                                b_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                con=-1;
                                            break;
                                        }
                                    }
                                    if(con == -1)
                                    {
                                        con =0;
                                        break;
                                    }
                                }
                                con = -1;
                            }
                            else {
                                b_moves[z][0] = i;
                                b_moves[z][1] = j;
                                con = -1;
                                z++;
                                break;
                            }
                        }
                        else if (i + j == x + y && occupied_b[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }
                //constant x-
                for (int i = y + 1; i < 9; i++) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        b_moves[z][0] = x;
                        b_moves[z][1] = i;
                        z++;
                    }
                    else if (occupied_a[x - 1][i - 1] == 1) {
                        if (x == king_x && i == king_y) {
                            b_moves[z][0] = x;
                            b_moves[z][1] = i;
                            z++;
                            for (int j = king_y + 1; j < 9; j++) {
                                if(occupied_a[x-1][j-1]==0&&occupied_b[x-1][j-1]==0) {
                                    b_moves[z][0] = x;
                                    b_moves[z][1] = j;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            b_moves[z][0] = x;
                            b_moves[z][1] = i;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
                for (int i = y - 1; i > 0; i--) {

                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        b_moves[z][0] = x;
                        b_moves[z][1] = i;
                        z++;
                    }
                    else if (occupied_a[x - 1][i - 1] == 1) {
                        if (x == king_x && i == king_y) {
                            b_moves[z][0] = x;
                            b_moves[z][1] = i;
                            z++;
                            for (int j = king_y - 1; j > 0; j--) {
                                if(occupied_a[x-1][j-1]==0&&occupied_b[x-1][j-1]==0) {
                                    b_moves[z][0] = x;
                                    b_moves[z][1] = j;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            b_moves[z][0] = x;
                            b_moves[z][1] = i;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
            }
        }
        //pawn-
        for (int i = 0; i < 8; i++) {
            if (bpawn[i][2] == 0) {
                int x = bpawn[i][0];
                int y = bpawn[i][1];
                if (x < 8 && y < 8 ) {//altered so king can't be moved voluntarily into a place where pawn can kill him
                    b_moves[z][0] = x + 1;
                    b_moves[z][1] = y + 1;
                    z++;
                }
                if (x > 1 && y < 8 ) {
                    b_moves[z][0] = x - 1;
                    b_moves[z][1] = y + 1;
                    z++;
                }
            }
        }
        //rook-
        for (int wq = 0; wq < brc; wq++) {
            if (brook[wq][2] == 0) {
                int x = brook[wq][0];
                int y = brook[wq][1];
                //constant y-
                for (int i = x + 1; i < 9; i++) {

                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        b_moves[z][0] = i;
                        b_moves[z][1] = y;
                        z++;
                    }
                    else if (occupied_a[i - 1][y - 1] == 1) {
                        if (i == king_x && y == king_y) {
                            b_moves[z][0] = i;
                            b_moves[z][1] = y;
                            z++;
                            for (int j = king_x + 1; j < 9; j++) {
                                if(occupied_a[j-1][y-1]==0&&occupied_b[j-1][y-1]==0) {
                                    b_moves[z][0] = j;
                                    b_moves[z][1] = y;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            b_moves[z][0]=i;
                            b_moves[z][1]=y;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
                for (int i = x - 1; i > 0; i--) {

                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        b_moves[z][0] = i;
                        b_moves[z][1] = y;
                        z++;
                    } else if (occupied_a[i - 1][y - 1] == 1)
                    {
                        if (i == king_x && y == king_y){
                            b_moves[z][0] = i;
                            b_moves[z][1] = y;
                            z++;
                            for (int j = king_x - 1; j > 0; j--) {
                                if(occupied_a[j-1][y-1]==0&&occupied_b[j-1][y-1]==0) {
                                    b_moves[z][0] = j;
                                    b_moves[z][1] = y;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            b_moves[z][0]=i;
                            b_moves[z][1]=y;
                            z++;
                            break;
                        }
                    }
                    else
                        break;

                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
                //constant x-
                for (int i = y + 1; i < 9; i++) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        b_moves[z][0] = x;
                        b_moves[z][1] = i;
                        z++;
                    }
                    else if (occupied_a[x - 1][i - 1] == 1) {
                        if (x == king_x && i == king_y) {
                            b_moves[z][0] = x;
                            b_moves[z][1] = i;
                            z++;
                            for (int j = king_y + 1; j < 9; j++) {
                                if(occupied_a[x-1][j-1]==0&&occupied_b[x-1][j-1]==0) {
                                    b_moves[z][0] = x;
                                    b_moves[z][1] = j;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            b_moves[z][0]=x;
                            b_moves[z][1]=i;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
                for (int i = y - 1; i > 0; i--) {

                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        b_moves[z][0] = x;
                        b_moves[z][1] = i;
                        z++;
                    }
                    else if (occupied_a[x - 1][i - 1] == 1) {
                        if (x == king_x && i == king_y) {
                            b_moves[z][0] = x;
                            b_moves[z][1] = i;
                            z++;
                            for (int j = king_y - 1; j > 0; j--) {
                                if(occupied_a[x-1][j-1]==0&&occupied_b[x-1][j-1]==0) {
                                    b_moves[z][0] = x;
                                    b_moves[z][1] = j;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            b_moves[z][0]=x;
                            b_moves[z][1]=i;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
            }
        }
        //knight//nothing altered
        for (int i = 0; i < bknc; i++) {
            if (bknight[i][2] == 0) {
                int kx[] = new int[4];
                int ky[] = new int[4];
                int x = bknight[i][0];
                int y = bknight[i][1];
                kx[0] = x - 1;
                kx[1] = x + 1;
                kx[2] = x - 2;
                kx[3] = x + 2;

                ky[0] = y - 2;
                ky[1] = y - 1;
                ky[2] = y + 1;
                ky[3] = y + 2;

                if (kx[0] > 0 && ky[0] > 0 && kx[0] < 9 && ky[0] < 9 && occupied_b[kx[0] - 1][ky[0] - 1] == 0) {
                    b_moves[z][0] = kx[0];
                    b_moves[z][1] = ky[0];
                    z++;
                }
                if (kx[1] > 0 && ky[0] > 0 && kx[1] < 9 && ky[0] < 9 && occupied_b[kx[1] - 1][ky[0] - 1] == 0) {
                    b_moves[z][0] = kx[1];
                    b_moves[z][1] = ky[0];
                    z++;
                }
                if (kx[2] > 0 && ky[1] > 0 && kx[2] < 9 && ky[1] < 9 && occupied_b[kx[2] - 1][ky[1] - 1] == 0) {
                    b_moves[z][0] = kx[2];
                    b_moves[z][1] = ky[1];
                    z++;
                }
                if (kx[2] > 0 && ky[2] > 0 && kx[2] < 9 && ky[2] < 9 && occupied_b[kx[2] - 1][ky[2] - 1] == 0) {
                    b_moves[z][0] = kx[2];
                    b_moves[z][1] = ky[2];
                    z++;
                }
                if (kx[0] > 0 && ky[3] > 0 && kx[0] < 9 && ky[3] < 9 && occupied_b[kx[0] - 1][ky[3] - 1] == 0) {
                    b_moves[z][0] = kx[0];
                    b_moves[z][1] = ky[3];
                    z++;
                }
                if (kx[1] > 0 && ky[3] > 0 && kx[1] < 9 && ky[3] < 9 && occupied_b[kx[1] - 1][ky[3] - 1] == 0) {
                    b_moves[z][0] = kx[1];
                    b_moves[z][1] = ky[3];
                    z++;
                }
                if (kx[3] > 0 && ky[1] > 0 && kx[3] < 9 && ky[1] < 9 && occupied_b[kx[3] - 1][ky[1] - 1] == 0) {
                    b_moves[z][0] = kx[3];
                    b_moves[z][1] = ky[1];
                    z++;
                }
                if (kx[3] > 0 && ky[2] > 0 && kx[3] < 9 && ky[2] < 9 && occupied_b[kx[3] - 1][ky[2] - 1] == 0) {
                    b_moves[z][0] = kx[3];
                    b_moves[z][1] = ky[2];
                    z++;
                }
            }
        }
        //Bishop
        for (int w = 0; w < bbc; w++) {
            if (bbishop[w][2] == 0) {
                int x = bbishop[w][0];
                int y = bbishop[w][1];
                //diagonal "1"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i - j == x - y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            b_moves[z][0] = i;
                            b_moves[z][1] = j;
                            z++;
                        }
                        else if (i - j == x - y && occupied_a[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                b_moves[z][0]=king_x;
                                b_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x + 1; k1 < 9; k1++) {
                                    for (int k2 = king_y + 1; k2 < 9; k2++) {
                                        if (k1 - k2 == king_x - king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                b_moves[z][0] = k1;
                                                b_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                break;
                                        }
                                    }
                                }
                                con = -1;
                            }
                            else {
                                b_moves[z][0]=i;
                                b_moves[z][1]=j;
                                con = -1;
                                break;
                            }
                        }
                        else if (i - j == x - y && occupied_b[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }
                //diagonal "2"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i - j == x - y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            b_moves[z][0] = i;
                            b_moves[z][1] = j;
                            z++;
                        }
                        else if (i - j == x - y && occupied_a[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                b_moves[z][0]=king_x;
                                b_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x - 1; k1 > 0; k1--) {
                                    for (int k2 = king_y - 1; k2 > 0; k2--) {
                                        if (k1 - k2 == king_x - king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                b_moves[z][0] = k1;
                                                b_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                break;
                                        }
                                    }
                                }
                                con = -1;
                            }
                            else {
                                b_moves[z][0]=i;
                                b_moves[z][1]=j;
                                con = -1;
                                break;
                            }
                        }
                        else if (i - j == x - y && occupied_b[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }

                //diagonal "3"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i + j == x + y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            b_moves[z][0] = i;
                            b_moves[z][1] = j;
                            z++;
                        }
                        else if (i + j == x + y && occupied_a[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                b_moves[z][0]=king_x;
                                b_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x + 1; k1 < 9; k1++) {
                                    for (int k2 = king_y - 1; k2 > 0; k2--) {
                                        if (k1 + k2 == king_x + king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                b_moves[z][0] = k1;
                                                b_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                con = -1;
                                            break;
                                        }
                                    }
                                    if(con == -1)
                                    {
                                        con =0;
                                        break;
                                    }
                                }
                                con = -1;
                            }
                            else {
                                b_moves[z][0]=i;
                                b_moves[z][1]=j;
                                con = -1;
                                break;
                            }
                        }
                        else if (i + j == x + y && occupied_b[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }
                //diagonal "4"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i + j == x + y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            b_moves[z][0] = i;
                            b_moves[z][1] = j;
                            z++;
                        }
                        else if (i + j == x + y && occupied_a[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                b_moves[z][0]=king_x;
                                b_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x - 1; k1 > 0; k1--) {
                                    for (int k2 = king_y + 1; k2 < 9; k2++) {
                                        if (k1 + k2 == king_x + king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                b_moves[z][0] = k1;
                                                b_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                con=-1;
                                            break;
                                        }
                                    }
                                    if(con == -1)
                                    {
                                        con =0;
                                        break;
                                    }
                                }
                                con = -1;
                            }
                            else {
                                b_moves[z][0]=i;
                                b_moves[z][1]=j;
                                con = -1;
                                break;
                            }
                        }
                        else if (i + j == x + y && occupied_b[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }
            }
        }
        //king
        if(bking[2]==0)
        {
            int x=bking[0];
            int y=bking[1];
            //constant x-
            if(x>0&&y>1&&occupied_b[x-1][y-2]==0)
            {
                b_moves[z][0]=x;
                b_moves[z][1]=y-1;
                z++;
            }
            if(x>0&&y<8&&occupied_b[x-1][y]==0)
            {
                b_moves[z][0]=x;
                b_moves[z][1]=y+1;
                z++;
            }
            //constant y-
            if(x>1&&y>0&&occupied_b[x-2][y-1]==0)
            {
                b_moves[z][0]=x-1;
                b_moves[z][1]=y;
                z++;
            }
            if(y>0&&x<8&&occupied_b[x][y-1]==0)
            {
                b_moves[z][0]=x+1;
                b_moves[z][1]=y;
                z++;
            }
            //
            if(x<8&&y<8&&occupied_b[x][y]==0)
            {
                b_moves[z][0]=x+1;
                b_moves[z][1]=y+1;
                z++;
            }
            if(x>1&&y>1&&occupied_b[x-2][y-2]==0)
            {
                b_moves[z][0]=x-1;
                b_moves[z][1]=y-1;
                z++;
            }
            //
            if(x<8&&y>1&&occupied_b[x][y-2]==0)
            {
                b_moves[z][0]=x+1;
                b_moves[z][1]=y-1;
                z++;
            }
            if(x>1&&y<8&&occupied_b[x-2][y]==0)
            {
                b_moves[z][0]=x-1;
                b_moves[z][1]=y+1;
                z++;
            }
        }
        for(int i=0;i<z;i++)
        {
            if(kix==b_moves[i][0]&&kiy==b_moves[i][1])
            {
                count++;
            }
        }
        if(count>0)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
    //soup
    int count1;
    int z;
    public int a_checkmate() {
        z = 0;
        count1 = 0;
        set();
        //PAWN
        for (int i = 0; i < 8; i++) {
            if (apawn[i][2] == 0) {
                a_pawn_count[i] = 0;
                int ax = apawn[i][0];
                int ay = apawn[i][1];
                if (apawn[i][4] == 0) {
                    if ((ax > 0 && ay > 2) && occupied_a[ax - 1][ay - 2] == 0 && occupied_a[ax - 1][ay - 3] == 0 && occupied_b[ax - 1][ay - 2] == 0 && occupied_b[ax - 1][ay - 3] == 0) {
                        occupied_a[apawn[i][0] - 1][apawn[i][1] - 1] = 0;
                        apawn[i][1] = ay - 2;
                        occupied_a[apawn[i][0] - 1][apawn[i][1] - 1] = 1;
                        if (increment()) {
                            a_pawn_moves[i][a_pawn_count[i]][0] = apawn[i][0];
                            a_pawn_moves[i][a_pawn_count[i]][1] = apawn[i][1];
                            a_pawn_count[i]++;
                            count1++;
                            apawn[i][5] = 1;
                        }
                        re_set();
                    }
                }
                if ((ax > 0 && ay > 1) && occupied_a[ax - 1][ay - 2] == 0 && occupied_b[ax - 1][ay - 2] == 0) {
                    occupied_a[apawn[i][0] - 1][apawn[i][1] - 1] = 0;
                    apawn[i][1] = ay - 1;
                    occupied_a[apawn[i][0] - 1][apawn[i][1] - 1] = 1;
                    if (increment()) {
                        a_pawn_moves[i][a_pawn_count[i]][0] = apawn[i][0];
                        a_pawn_moves[i][a_pawn_count[i]][1] = apawn[i][1];
                        a_pawn_count[i]++;
                        count1++;
                        apawn[i][5] = 1;
                    }
                    re_set();
                }
                if (ax > 1 && ay > 1 && occupied_b[ax - 2][ay - 2] == 1) {
                    occupied_a[apawn[i][0] - 1][apawn[i][1] - 1] = 0;
                    apawn[i][0] = ax - 1;
                    apawn[i][1] = ay - 1;
                    a_kill_b(ax - 1, ay - 1);
                    occupied_a[apawn[i][0] - 1][apawn[i][1] - 1] = 1;
                    if (increment()) {
                        a_pawn_moves[i][a_pawn_count[i]][0] = ax - 1;
                        a_pawn_moves[i][a_pawn_count[i]][1] = ay - 1;
                        a_pawn_count[i]++;
                        count1++;
                        apawn[i][5] = 1;
                    }
                    re_set();
                }
                if (ax < 8 && ay > 1 && occupied_b[ax][ay - 2] == 1) {
                    occupied_a[apawn[i][0] - 1][apawn[i][1] - 1] = 0;
                    apawn[i][0] = ax + 1;
                    apawn[i][0] = ay - 1;
                    a_kill_b(ax + 1, ay - 1);
                    occupied_a[apawn[i][0] - 1][apawn[i][1] - 1] = 1;
                    if (increment()) {
                        a_pawn_moves[i][a_pawn_count[i]][0] = ax + 1;
                        a_pawn_moves[i][a_pawn_count[i]][1] = ay - 1;
                        a_pawn_count[i]++;
                        count1++;
                        apawn[i][5] = 1;
                    }
                    re_set();
                }
                //en passant
                if (ax < 8 && ay > 0 && occupied_b[ax][ay - 1] == 1)
                {
                    for(int k=0;k<8;k++)
                    {
                        if(bpawn[k][0]==ax+1&&bpawn[k][1]==ay&&bpawn[k][4]==1)
                        {
                            occupied_a[apawn[i][0] - 1][apawn[i][1] - 1] = 0;
                            apawn[i][0] = ax + 1;
                            apawn[i][0] = ay - 1;
                            bpawn[k][2]=-1;
                            occupied_a[apawn[i][0] - 1][apawn[i][1] - 1] = 1;
                            if (increment())
                            {
                                a_pawn_moves[i][a_pawn_count[i]][0] = ax + 1;
                                a_pawn_moves[i][a_pawn_count[i]][1] = ay - 1;
                                a_pawn_moves[i][a_pawn_count[i]][2] = 1;
                                a_pawn_count[i]++;
                                count1++;
                                apawn[i][5] = 1;
                            }
                            re_set();
                        }
                    }
                }
                if (ax > 1 && ay > 0 && occupied_b[ax-2][ay - 1] == 1)
                {
                    for(int k=0;k<8;k++)
                    {
                        if(bpawn[k][0]==ax-1&&bpawn[k][1]==ay&&bpawn[k][4]==1)
                        {
                            occupied_a[apawn[i][0] - 1][apawn[i][1] - 1] = 0;
                            apawn[i][0] = ax - 1;
                            apawn[i][0] = ay - 1;
                            a_kill_b(ax - 1, ay);
                            bpawn[k][2]=-1;
                            occupied_a[apawn[i][0] - 1][apawn[i][1] - 1] = 1;
                            if (increment())
                            {
                                a_pawn_moves[i][a_pawn_count[i]][0] = ax - 1;
                                a_pawn_moves[i][a_pawn_count[i]][1] = ay - 1;
                                a_pawn_moves[i][a_pawn_count[i]][2] = 1;
                                a_pawn_count[i]++;
                                count1++;
                                apawn[i][5] = 1;
                            }
                            re_set();
                        }
                    }
                }
            }
        }
        //QUEEN
        for (int i = 0; i < aqc; i++) {
            if (aqueen[i][2] == 0) {
                int ax = aqueen[i][0];
                int ay = aqueen[i][1];
                a_queen_count[i] = 0;
                set();
                //constant x-
                con = 0;
                for (int j = ay + 1; j < 9; j++) {
                    if (occupied_a[ax - 1][j - 1] == 0 && con != -1) {
                        if (occupied_b[ax - 1][j - 1] == 0) {
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                            aqueen[i][1] = j;
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                            if (increment()) {
                                a_queen_moves[i][a_queen_count[i]][0] = ax;
                                a_queen_moves[i][a_queen_count[i]][1] = j;
                                a_queen_count[i]++;
                                count1++;
                                aqueen[i][4] = 1;
                            }
                            re_set();
                        }
                        if (occupied_b[ax - 1][j - 1] == 1) {

                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                            aqueen[i][1] = j;
                            a_kill_b(aqueen[i][0], aqueen[i][1]);
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                            if (increment()) {
                                a_queen_moves[i][a_queen_count[i]][0] = ax;
                                a_queen_moves[i][a_queen_count[i]][1] = j;
                                a_queen_count[i]++;
                                count1++;
                                aqueen[i][4] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_a[ax - 1][j - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                con = 0;
                for (int j = ay - 1; j > 0; j--) {
                    if (occupied_a[ax - 1][j - 1] == 0 && con != -1) {
                        if (occupied_b[ax - 1][j - 1] == 0) {
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                            aqueen[i][1] = j;
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                            if (increment()) {
                                a_queen_moves[i][a_queen_count[i]][0] = ax;
                                a_queen_moves[i][a_queen_count[i]][1] = j;
                                a_queen_count[i]++;
                                count1++;
                                aqueen[i][4] = 1;
                            }
                            re_set();
                        }
                        if (occupied_b[ax - 1][j - 1] == 1) {
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                            aqueen[i][1] = j;
                            a_kill_b(aqueen[i][0], aqueen[i][1]);
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                            if (increment()) {
                                a_queen_moves[i][a_queen_count[i]][0] = ax;
                                a_queen_moves[i][a_queen_count[i]][1] = j;
                                a_queen_count[i]++;
                                count1++;
                                aqueen[i][4] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_a[ax - 1][j - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //constant y-
                con = 0;
                for (int j = ax + 1; j < 9; j++) {
                    if (occupied_a[j - 1][ay - 1] == 0 && con != -1) {
                        if (occupied_b[j - 1][ay - 1] == 0) {
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                            aqueen[i][0] = j;
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                            if (increment()) {
                                a_queen_moves[i][a_queen_count[i]][0] = j;
                                a_queen_moves[i][a_queen_count[i]][1] = ay;
                                a_queen_count[i]++;
                                count1++;
                                aqueen[i][4] = 1;
                            }
                            re_set();
                        }
                        if (occupied_b[j - 1][ay - 1] == 1) {
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                            aqueen[i][0] = j;
                            a_kill_b(aqueen[i][0], aqueen[i][1]);
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                            if (increment()) {
                                a_queen_moves[i][a_queen_count[i]][0] = j;
                                a_queen_moves[i][a_queen_count[i]][1] = ay;
                                a_queen_count[i]++;
                                count1++;
                                aqueen[i][4] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_a[j - 1][ay - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                con = 0;
                for (int j = ax - 1; j > 0; j--) {
                    if (occupied_a[j - 1][ay - 1] == 0 && con != -1) {
                        if (occupied_b[j - 1][ay - 1] == 0) {
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                            aqueen[i][0] = j;
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                            if (increment()) {
                                a_queen_moves[i][a_queen_count[i]][0] = j;
                                a_queen_moves[i][a_queen_count[i]][1] = ay;
                                a_queen_count[i]++;
                                count1++;
                                aqueen[i][4] = 1;
                            }
                            re_set();
                        }
                        if (occupied_b[j - 1][ay - 1] == 1) {
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                            aqueen[i][0] = j;
                            a_kill_b(aqueen[i][0], aqueen[i][1]);
                            occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                            if (increment()) {
                                a_queen_moves[i][a_queen_count[i]][0] = j;
                                a_queen_moves[i][a_queen_count[i]][1] = ay;
                                a_queen_count[i]++;
                                count1++;
                                aqueen[i][4] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_a[j - 1][ay - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "1" -
                con = 0;
                for (int j = ax + 1; j < 9; j++) {
                    for (int k = ay + 1; k < 9; k++) {
                        if (j - k == ax - ay) {
                            if (occupied_a[j - 1][k - 1] == 0 && con != -1) {
                                if (occupied_b[j - 1][k - 1] == 0) {
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                                    aqueen[i][0] = j;
                                    aqueen[i][1] = k;
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_queen_moves[i][a_queen_count[i]][0] = j;
                                        a_queen_moves[i][a_queen_count[i]][1] = k;
                                        a_queen_count[i]++;
                                        count1++;
                                        aqueen[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_b[j - 1][k - 1] == 1) {
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                                    aqueen[i][0] = j;
                                    aqueen[i][1] = k;
                                    a_kill_b(aqueen[i][0], aqueen[i][1]);
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_queen_moves[i][a_queen_count[i]][0] = j;
                                        a_queen_moves[i][a_queen_count[i]][1] = k;
                                        a_queen_count[i]++;
                                        count1++;
                                        aqueen[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_a[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "2" -
                con = 0;
                for (int j = ax - 1; j > 0; j--) {
                    for (int k = ay - 1; k > 0; k--) {
                        if (j - k == ax - ay) {
                            if (occupied_a[j - 1][k - 1] == 0 && con != -1) {
                                if (occupied_b[j - 1][k - 1] == 0) {
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                                    aqueen[i][0] = j;
                                    aqueen[i][1] = k;
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_queen_moves[i][a_queen_count[i]][0] = j;
                                        a_queen_moves[i][a_queen_count[i]][1] = k;
                                        a_queen_count[i]++;
                                        count1++;
                                        aqueen[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_b[j - 1][k - 1] == 1) {
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                                    aqueen[i][0] = j;
                                    aqueen[i][1] = k;
                                    a_kill_b(aqueen[i][0], aqueen[i][1]);
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_queen_moves[i][a_queen_count[i]][0] = j;
                                        a_queen_moves[i][a_queen_count[i]][1] = k;
                                        a_queen_count[i]++;
                                        count1++;
                                        aqueen[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_a[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "3" -
                con = 0;
                for (int j = ax + 1; j < 9; j++) {
                    for (int k = ay - 1; k > 0; k--) {
                        if (j + k == ax + ay) {
                            if (occupied_a[j - 1][k - 1] == 0 && con != -1) {
                                if (occupied_b[j - 1][k - 1] == 0) {
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                                    aqueen[i][0] = j;
                                    aqueen[i][1] = k;
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_queen_moves[i][a_queen_count[i]][0] = j;
                                        a_queen_moves[i][a_queen_count[i]][1] = k;
                                        a_queen_count[i]++;
                                        count1++;
                                        aqueen[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_b[j - 1][k - 1] == 1) {
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                                    aqueen[i][0] = j;
                                    aqueen[i][1] = k;
                                    a_kill_b(aqueen[i][0], aqueen[i][1]);
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_queen_moves[i][a_queen_count[i]][0] = j;
                                        a_queen_moves[i][a_queen_count[i]][1] = k;
                                        a_queen_count[i]++;
                                        count1++;
                                        aqueen[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_a[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "4" -
                con = 0;
                for (int j = ax - 1; j > 0; j--) {
                    for (int k = ay + 1; k < 9; k++) {
                        if (j + k == ax + ay) {
                            if (occupied_a[j - 1][k - 1] == 0) {
                                if (occupied_b[j - 1][k - 1] == 0) {
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                                    aqueen[i][0] = j;
                                    aqueen[i][1] = k;
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_queen_moves[i][a_queen_count[i]][0] = j;
                                        a_queen_moves[i][a_queen_count[i]][1] = k;
                                        a_queen_count[i]++;
                                        count1++;
                                        aqueen[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_b[j - 1][k - 1] == 1) {
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 0;
                                    aqueen[i][0] = j;
                                    aqueen[i][1] = k;
                                    a_kill_b(aqueen[i][0], aqueen[i][1]);
                                    occupied_a[aqueen[i][0] - 1][aqueen[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_queen_moves[i][a_queen_count[i]][0] = j;
                                        a_queen_moves[i][a_queen_count[i]][1] = k;
                                        a_queen_count[i]++;
                                        count1++;
                                        aqueen[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_a[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
            }
        }
        //KNIGHT--
        for (int i = 0; i < aknc; i++) {
            if (aknight[i][2] == 0) {
                int ax = aknight[i][0];
                int ay = aknight[i][1];

                int[] kx = new int[4];
                int[] ky = new int[4];
                kx[0] = ax - 2;
                kx[1] = ax - 1;
                kx[2] = ax + 1;
                kx[3] = ax + 2;

                ky[0] = ay - 2;
                ky[1] = ay - 1;
                ky[2] = ay + 1;
                ky[3] = ay + 2;
                a_knight_count[i] = 0;
                set();
                if (kx[1] > 0 && kx[1] < 9 && ky[0] > 0 && ky[0] < 9) {
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 0;
                    aknight[i][0] = kx[1];
                    aknight[i][1] = ky[0];
                    a_kill_b(aknight[i][0], aknight[i][1]);
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 1;
                    if (increment()) {
                        a_knight_moves[i][a_knight_count[i]][0] = aknight[i][0];
                        a_knight_moves[i][a_knight_count[i]][1] = aknight[i][1];
                        a_knight_count[i]++;
                        count1++;
                        aknight[i][4] = 1;
                    }
                    re_set();
                }
                if (kx[0] > 0 && kx[0] < 9 && ky[1] > 0 && ky[1] < 9) {
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 0;
                    aknight[i][0] = kx[0];
                    aknight[i][1] = ky[1];
                    a_kill_b(aknight[i][0], aknight[i][1]);
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 1;
                    if (increment()) {
                        a_knight_moves[i][a_knight_count[i]][0] = aknight[i][0];
                        a_knight_moves[i][a_knight_count[i]][1] = aknight[i][1];
                        a_knight_count[i]++;
                        count1++;
                        aknight[i][4] = 1;
                    }
                    re_set();
                }

                if (kx[0] > 0 && kx[0] < 9 && ky[2] > 0 && ky[2] < 9) {
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 0;
                    aknight[i][0] = kx[0];
                    aknight[i][1] = ky[2];
                    a_kill_b(aknight[i][0], aknight[i][1]);
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 1;
                    if (increment()) {
                        a_knight_moves[i][a_knight_count[i]][0] = aknight[i][0];
                        a_knight_moves[i][a_knight_count[i]][1] = aknight[i][1];
                        a_knight_count[i]++;
                        count1++;
                        aknight[i][4] = 1;
                    }
                    re_set();
                }
                if (kx[1] > 0 && kx[1] < 9 && ky[3] > 0 && ky[3] < 9) {
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 0;
                    aknight[i][0] = kx[1];
                    aknight[i][1] = ky[3];
                    a_kill_b(aknight[i][0], aknight[i][1]);
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 1;
                    if (increment()) {
                        a_knight_moves[i][a_knight_count[i]][0] = aknight[i][0];
                        a_knight_moves[i][a_knight_count[i]][1] = aknight[i][1];
                        a_knight_count[i]++;
                        count1++;
                        aknight[i][4] = 1;
                    }
                    re_set();
                }
                if (kx[2] > 0 && kx[2] < 9 && ky[0] > 0 && ky[0] < 9) {
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 0;
                    aknight[i][0] = kx[2];
                    aknight[i][1] = ky[0];
                    a_kill_b(aknight[i][0], aknight[i][1]);
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 1;
                    if (increment()) {
                        a_knight_moves[i][a_knight_count[i]][0] = aknight[i][0];
                        a_knight_moves[i][a_knight_count[i]][1] = aknight[i][1];
                        a_knight_count[i]++;
                        count1++;
                        aknight[i][4] = 1;
                    }
                    re_set();
                }
                if (kx[3] > 0 && kx[3] < 9 && ky[1] > 0 && ky[1] < 9) {
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 0;
                    aknight[i][0] = kx[3];
                    aknight[i][1] = ky[1];
                    a_kill_b(aknight[i][0], aknight[i][1]);
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 1;
                    if (increment()) {
                        a_knight_moves[i][a_knight_count[i]][0] = aknight[i][0];
                        a_knight_moves[i][a_knight_count[i]][1] = aknight[i][1];
                        a_knight_count[i]++;
                        count1++;
                        aknight[i][4] = 1;
                    }
                    re_set();
                }
                if (kx[3] > 0 && kx[3] < 9 && ky[2] > 0 && ky[2] < 9) {
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 0;
                    aknight[i][0] = kx[3];
                    aknight[i][1] = ky[2];
                    a_kill_b(aknight[i][0], aknight[i][1]);
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 1;
                    if (increment()) {
                        a_knight_moves[i][a_knight_count[i]][0] = aknight[i][0];
                        a_knight_moves[i][a_knight_count[i]][1] = aknight[i][1];
                        a_knight_count[i]++;
                        count1++;
                        aknight[i][4] = 1;
                    }
                    re_set();
                }
                if (kx[2] > 0 && kx[2] < 9 && ky[3] > 0 && ky[3] < 9) {
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 0;
                    aknight[i][0] = kx[2];
                    aknight[i][1] = ky[3];
                    a_kill_b(aknight[i][0], aknight[i][1]);
                    occupied_a[aknight[i][0] - 1][aknight[i][1] - 1] = 1;
                    if (increment()) {
                        a_knight_moves[i][a_knight_count[i]][0] = aknight[i][0];
                        a_knight_moves[i][a_knight_count[i]][1] = aknight[i][1];
                        a_knight_count[i]++;
                        count1++;
                        aknight[i][4] = 1;
                    }
                    re_set();
                }
            }
        }
        //BISHOP
        for (int i = 0; i < abc; i++) {
            if (abishop[i][2] == 0) {
                re_set();
                int ax = abishop[i][0];
                int ay = abishop[i][1];
                set();
                //diagonal "1" -
                con = 0;
                a_bishop_count[i] = 0;
                for (int j = ax + 1; j < 9; j++) {
                    for (int k = ay + 1; k < 9; k++) {
                        if (j - k == ax - ay) {
                            if (occupied_a[j - 1][k - 1] == 0) {
                                if (occupied_b[j - 1][k - 1] == 0) {
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 0;
                                    abishop[i][0] = j;
                                    abishop[i][1] = k;
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_bishop_moves[i][a_bishop_count[i]][0] = j;
                                        a_bishop_moves[i][a_bishop_count[i]][1] = k;
                                        a_bishop_count[i]++;
                                        count1++;
                                        abishop[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_b[j - 1][k - 1] == 1) {
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 0;
                                    abishop[i][0] = j;
                                    abishop[i][1] = k;
                                    a_kill_b(abishop[i][0], abishop[i][1]);
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_bishop_moves[i][a_bishop_count[i]][0] = j;
                                        a_bishop_moves[i][a_bishop_count[i]][1] = k;
                                        a_bishop_count[i]++;
                                        count1++;
                                        abishop[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_a[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "2" -
                con = 0;
                for (int j = ax - 1; j > 0; j--) {
                    for (int k = ay - 1; k > 0; k--) {
                        if (j - k == ax - ay) {
                            if (occupied_a[j - 1][k - 1] == 0) {
                                if (occupied_b[j - 1][k - 1] == 0) {
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 0;
                                    abishop[i][0] = j;
                                    abishop[i][1] = k;
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_bishop_moves[i][a_bishop_count[i]][0] = j;
                                        a_bishop_moves[i][a_bishop_count[i]][1] = k;
                                        a_bishop_count[i]++;
                                        count1++;
                                        abishop[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_b[j - 1][k - 1] == 1) {
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 0;
                                    abishop[i][0] = j;
                                    abishop[i][1] = k;
                                    a_kill_b(abishop[i][0], abishop[i][1]);
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_bishop_moves[i][a_bishop_count[i]][0] = j;
                                        a_bishop_moves[i][a_bishop_count[i]][1] = k;
                                        a_bishop_count[i]++;
                                        count1++;
                                        abishop[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_a[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "3" -
                con = 0;
                for (int j = ax + 1; j < 9; j++) {
                    for (int k = ay - 1; k > 0; k--) {
                        if (j + k == ax + ay) {
                            if (occupied_a[j - 1][k - 1] == 0) {
                                if (occupied_b[j - 1][k - 1] == 0) {
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 0;
                                    abishop[i][0] = j;
                                    abishop[i][1] = k;
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_bishop_moves[i][a_bishop_count[i]][0] = j;
                                        a_bishop_moves[i][a_bishop_count[i]][1] = k;
                                        a_bishop_count[i]++;
                                        count1++;
                                        abishop[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_b[j - 1][k - 1] == 1) {
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 0;
                                    abishop[i][0] = j;
                                    abishop[i][1] = k;
                                    a_kill_b(abishop[i][0], abishop[i][1]);
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_bishop_moves[i][a_bishop_count[i]][0] = j;
                                        a_bishop_moves[i][a_bishop_count[i]][1] = k;
                                        a_bishop_count[i]++;
                                        count1++;
                                        abishop[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_a[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "4" -
                con = 0;
                for (int j = ax - 1; j > 0; j--) {
                    for (int k = ay + 1; k < 9; k++) {
                        if (j + k == ax + ay) {
                            if (occupied_a[j - 1][k - 1] == 0) {
                                if (occupied_b[j - 1][k - 1] == 0) {
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 0;
                                    abishop[i][0] = j;
                                    abishop[i][1] = k;
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_bishop_moves[i][a_bishop_count[i]][0] = j;
                                        a_bishop_moves[i][a_bishop_count[i]][1] = k;
                                        a_bishop_count[i]++;
                                        count1++;
                                        abishop[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_b[j - 1][k - 1] == 1) {
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 0;
                                    abishop[i][0] = j;
                                    abishop[i][1] = k;
                                    a_kill_b(abishop[i][0], abishop[i][1]);
                                    occupied_a[abishop[i][0] - 1][abishop[i][1] - 1] = 1;
                                    if (increment()) {
                                        a_bishop_moves[i][a_bishop_count[i]][0] = j;
                                        a_bishop_moves[i][a_bishop_count[i]][1] = k;
                                        a_bishop_count[i]++;
                                        count1++;
                                        abishop[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_a[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
            }
        }
        //ROOK
        for (int i = 0; i < arc; i++) {
            if (arook[i][2] == 0) {
                int ax = arook[i][0];
                int ay = arook[i][1];
                a_rook_count[i] = 0;
                set();
                //constant x-
                con = 0;
                for (int j = ay + 1; j < 9; j++) {
                    if (occupied_a[ax - 1][j - 1] == 0 && con != -1) {
                        if (occupied_b[ax - 1][j - 1] == 0) {
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 0;
                            arook[i][1] = j;
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 1;
                            if (increment()) {
                                a_rook_moves[i][a_rook_count[i]][0] = ax;
                                a_rook_moves[i][a_rook_count[i]][1] = j;
                                a_rook_count[i]++;
                                count1++;
                                arook[i][5] = 1;
                            }
                            re_set();
                        }
                        if (occupied_b[ax - 1][j - 1] == 1) {

                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 0;
                            arook[i][1] = j;
                            a_kill_b(arook[i][0], arook[i][1]);
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 1;
                            if (increment()) {
                                a_rook_moves[i][a_rook_count[i]][0] = ax;
                                a_rook_moves[i][a_rook_count[i]][1] = j;
                                a_rook_count[i]++;
                                count1++;
                                arook[i][5] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_a[ax - 1][j - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                con = 0;
                for (int j = ay - 1; j > 0; j--) {
                    if (occupied_a[ax - 1][j - 1] == 0 && con != -1) {
                        if (occupied_b[ax - 1][j - 1] == 0) {
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 0;
                            arook[i][1] = j;
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 1;
                            if (increment()) {
                                a_rook_moves[i][a_rook_count[i]][0] = ax;
                                a_rook_moves[i][a_rook_count[i]][1] = j;
                                a_rook_count[i]++;
                                count1++;
                                arook[i][5] = 1;
                            }
                            re_set();
                        }
                        if (occupied_b[ax - 1][j - 1] == 1) {
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 0;
                            arook[i][1] = j;
                            a_kill_b(arook[i][0], arook[i][1]);
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 1;
                            if (increment()) {
                                a_rook_moves[i][a_rook_count[i]][0] = ax;
                                a_rook_moves[i][a_rook_count[i]][1] = j;
                                a_rook_count[i]++;
                                count1++;
                                arook[i][5] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_a[ax - 1][j - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //constant y-
                con = 0;
                for (int j = ax + 1; j < 9; j++) {
                    if (occupied_a[j - 1][ay - 1] == 0 && con != -1) {
                        if (occupied_b[j - 1][ay - 1] == 0) {
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 0;
                            arook[i][0] = j;
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 1;
                            if (increment()) {
                                a_rook_moves[i][a_rook_count[i]][0] = j;
                                a_rook_moves[i][a_rook_count[i]][1] = ay;
                                a_rook_count[i]++;
                                count1++;
                                arook[i][5] = 1;
                            }
                            re_set();
                        }
                        if (occupied_b[j - 1][ay - 1] == 1) {
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 0;
                            arook[i][0] = j;
                            a_kill_b(arook[i][0], arook[i][1]);
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 1;
                            if (increment()) {
                                a_rook_moves[i][a_rook_count[i]][0] = j;
                                a_rook_moves[i][a_rook_count[i]][1] = ay;
                                a_rook_count[i]++;
                                count1++;
                                arook[i][5] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_a[j - 1][ay - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                con = 0;
                for (int j = ax - 1; j > 0; j--) {
                    if (occupied_a[j - 1][ay - 1] == 0 && con != -1) {
                        if (occupied_b[j - 1][ay - 1] == 0) {
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 0;
                            arook[i][0] = j;
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 1;
                            if (increment()) {
                                a_rook_moves[i][a_rook_count[i]][0] = j;
                                a_rook_moves[i][a_rook_count[i]][1] = ay;
                                a_rook_count[i]++;
                                count1++;
                                arook[i][5] = 1;
                            }
                            re_set();
                        }
                        if (occupied_b[j - 1][ay - 1] == 1) {
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 0;
                            arook[i][0] = j;
                            a_kill_b(arook[i][0], arook[i][1]);
                            occupied_a[arook[i][0] - 1][arook[i][1] - 1] = 1;
                            if (increment()) {
                                a_rook_moves[i][a_rook_count[i]][0] = j;
                                a_rook_moves[i][a_rook_count[i]][1] = ay;
                                a_rook_count[i]++;
                                count1++;
                                arook[i][5] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_a[j - 1][ay - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
            }
        }
        //KING
        if(aking[2]==0)
        {
            a_king_count=0;
            re_set();
            int ax = aking[0];
            int ay = aking[1];
            //int qwe=0;
            if(ax>1&&occupied_a[ax-2][ay-1]==0)//here
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=ax-1;
                aking[1]=ay;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                z++;
                if(increment())
                {
                    a_king_moves[a_king_count][0]=aking[0];
                    a_king_moves[a_king_count][1]=aking[1];
                    a_king_count++;
                    count1++;
                    aking[5]=1;
                }
                re_set();
            }
            if(ax<8&&occupied_a[ax][ay-1]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=ax+1;
                aking[1]=ay;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                z++;
                if(increment())
                {
                    a_king_moves[a_king_count][0]=aking[0];
                    a_king_moves[a_king_count][1]=aking[1];
                    a_king_count++;
                    count1++;
                    aking[5]=1;
                }
                re_set();
            }
            if(ay>1&&occupied_a[ax-1][ay-2]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=ax;
                aking[1]=ay-1;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                z++;
                if(increment())
                {
                    a_king_moves[a_king_count][0]=aking[0];
                    a_king_moves[a_king_count][1]=aking[1];
                    a_king_count++;
                    count1++;
                    aking[5]=1;
                }
                re_set();
            }
            if(ay<8&&occupied_a[ax-1][ay]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=ax;
                aking[1]=ay+1;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                z++;
                if(increment())
                {
                    a_king_moves[a_king_count][0]=aking[0];
                    a_king_moves[a_king_count][1]=aking[1];
                    a_king_count++;
                    count1++;
                    aking[5]=1;
                }
                re_set();
            }
            if(ax<8&&ay<8&&occupied_a[ax][ay]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=ax+1;
                aking[1]=ay+1;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                z++;
                if(increment())
                {
                    a_king_moves[a_king_count][0]=aking[0];
                    a_king_moves[a_king_count][1]=aking[1];
                    a_king_count++;
                    count1++;
                    aking[5]=1;
                }
                re_set();
            }
            if(ax>1&&ay<8&&occupied_a[ax-2][ay]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=ax-1;
                aking[1]=ay+1;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                z++;
                if(increment())
                {
                    a_king_moves[a_king_count][0]=aking[0];
                    a_king_moves[a_king_count][1]=aking[1];
                    a_king_count++;
                    count1++;
                    aking[5]=1;
                }
                re_set();
            }
            if(ax>1&&ay>1&&occupied_a[ax-2][ay-2]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=ax-1;
                aking[1]=ay-1;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                z++;
                if(increment())
                {
                    a_king_moves[a_king_count][0]=aking[0];
                    a_king_moves[a_king_count][1]=aking[1];
                    a_king_count++;
                    count1++;
                    aking[5]=1;
                }
                re_set();
            }
            if(ax<8&&ay>1&&occupied_a[ax][ay-2]==0)
            {
                occupied_a[aking[0]-1][aking[1]-1]=0;
                aking[0]=ax+1;
                aking[1]=ay-1;
                a_kill_b(aking[0],aking[1]);
                occupied_a[aking[0]-1][aking[1]-1]=1;
                z++;
                if(increment())
                {
                    a_king_moves[a_king_count][0]=aking[0];
                    a_king_moves[a_king_count][1]=aking[1];
                    a_king_count++;
                    count1++;
                    aking[5]=1;
                }
                re_set();
            }
                /*
                //castling- Cannot be done when king in check
                if(aking[4]==0) {
                    if (arook[0][4] == 0 && arook[0][2] == 0) {
                        for (int i = arook[0][0] + 1; i < aking[0]; i++) {
                            if (occupied_a[i - 1][ay - 1] == 0 && occupied_b[i - 1][ay - 1] == 0 && a_check(i, ay) == 1) {
                                qwe = 1;
                            } else {
                                qwe = -1;
                                break;
                            }
                        }
                        if (qwe == 1) {
                            a_king_moves[a_king_count][0]=arook[0][0];
                            a_king_moves[a_king_count][1]=aking[1];
                            a_king_count++;
                            z++;
                            count1++;
                            aking[5]=1;
                            qwe = 0;
                        }
                    }
                    if (arook[1][4] == 0 && arook[1][2] == 0) {
                        for (int i = aking[0] + 1; i < arook[1][0]; i++) {
                            if (occupied_a[i - 1][ay - 1] == 0 && occupied_b[i - 1][ay - 1] == 0 && a_check(i, ay) == 1) {
                                qwe = 1;
                            } else {
                                qwe = -1;
                                break;
                            }
                        }
                        if (qwe == 1) {
                            a_king_moves[a_king_count][0]=arook[1][0];
                            a_king_moves[a_king_count][1]=aking[1];
                            a_king_count++;
                            z++;
                            count1++;
                            aking[5]=1;
                            qwe=0;
                        }
                    }
                }*/
        }
        if(count1==0)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
    public void b_pawn(int k) {
        if(bpawn[k][2]==0) {
            int z=-1;
            if(b_check(bking[0],bking[1])==1)
            {
                set();
                pawn_b_count[k]=0;
                int x = bpawn[k][0];
                int y = bpawn[k][1];
                if (x > 0 && y < 8 && occupied_a[x - 1][y] == 0 && occupied_b[x - 1][y] == 0) {
                    occupied_b[bpawn[k][0] - 1][bpawn[k][1] - 1] = 0;
                    bpawn[k][0] = x;
                    bpawn[k][1] = y + 1;
                    occupied_b[bpawn[k][0] - 1][bpawn[k][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        pawn_b_moves[k][pawn_b_count[k]][0] = x;
                        pawn_b_moves[k][pawn_b_count[k]][1] = y + 1;
                        pawn_b_count[k]++;
                    }
                    re_set();
                    if (bpawn[k][4] == 0) {
                        if (occupied_a[x - 1][y + 1] == 0 && occupied_b[x - 1][y + 1] == 0) {
                            occupied_b[bpawn[k][0] - 1][bpawn[k][1] - 1] = 0;
                            bpawn[k][0] = x;
                            bpawn[k][1] = y + 2;
                            occupied_b[bpawn[k][0] - 1][bpawn[k][1] - 1] = 1;
                            if (b_check(bking[0], bking[1]) == 1) {
                                pawn_b_moves[k][pawn_b_count[k]][0] = x;
                                pawn_b_moves[k][pawn_b_count[k]][1] = y + 2;
                                pawn_b_count[k]++;
                            }
                            re_set();
                        }
                    }
                }
                if (x < 8 && y > 0 && occupied_a[x][y - 1] == 1) {
                    for (int i = 0; i < 8; i++) {
                        if (apawn[i][0] == x + 1 && apawn[i][1] == y && apawn[i][4] == 1) {
                            occupied_b[bpawn[k][0] - 1][bpawn[k][1] - 1] = 0;
                            bpawn[k][0] = x + 1;
                            bpawn[k][1] = y + 1;
                            b_kill_a(x+1,y);
                            occupied_b[bpawn[k][0] - 1][bpawn[k][1] - 1] = 1;
                            if (b_check(bking[0], bking[1]) == 1) {
                                pawn_b_moves[k][pawn_b_count[k]][0] = x + 1;
                                pawn_b_moves[k][pawn_b_count[k]][1] = y + 1;
                                pawn_b_moves[k][pawn_b_count[k]][2] = 1;
                                pawn_b_count[k]++;
                                z=1;
                            }
                            re_set();
                        }
                    }
                }
                if (x > 1 && y > 0 && occupied_a[x - 2][y - 1] == 1) {
                    for (int i = 0; i < 8; i++) {
                        if (apawn[i][0] == x - 1 && apawn[i][1] == y && apawn[i][4] == 1) {
                            occupied_b[bpawn[k][0] - 1][bpawn[k][1] - 1] = 0;
                            bpawn[k][0] = x - 1;
                            bpawn[k][1] = y + 1;
                            b_kill_a(x-1,y);
                            occupied_b[bpawn[k][0] - 1][bpawn[k][1] - 1] = 1;
                            if (b_check(bking[0], bking[1]) == 1) {
                                pawn_b_moves[k][pawn_b_count[k]][0] = x - 1;
                                pawn_b_moves[k][pawn_b_count[k]][1] = y + 1;
                                pawn_b_moves[k][pawn_b_count[k]][2] = 1;
                                pawn_b_count[k]++;
                                z=2;
                            }
                            re_set();
                        }
                    }
                }
                if (x < 8 && y < 8 && occupied_a[x][y] == 1) {
                    occupied_b[bpawn[k][0] - 1][bpawn[k][1] - 1] = 0;
                    bpawn[k][0] = x + 1;
                    bpawn[k][1] = y + 1;
                    b_kill_a(x + 1, y + 1);
                    occupied_b[bpawn[k][0] - 1][bpawn[k][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        pawn_b_moves[k][pawn_b_count[k]][0] = x + 1;
                        pawn_b_moves[k][pawn_b_count[k]][1] = y + 1;
                        pawn_b_count[k]++;
                    }
                    re_set();
                }
                if (x > 1 && y < 8 && occupied_a[x - 2][y] == 1) {
                    occupied_b[bpawn[k][0] - 1][bpawn[k][1] - 1] = 0;
                    bpawn[k][0] = x - 1;
                    bpawn[k][1] = y + 1;
                    b_kill_a(x - 1, y + 1);
                    occupied_b[bpawn[k][0] - 1][bpawn[k][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        pawn_b_moves[k][pawn_b_count[k]][0] = x - 1;
                        pawn_b_moves[k][pawn_b_count[k]][1] = y + 1;
                        pawn_b_count[k]++;
                    }
                    re_set();
                }
                for(int i=0;i<pawn_b_count[k];i++)
                {
                    if(z_val==0 && px==pawn_b_moves[k][i][0] && py==pawn_b_moves[k][i][1])
                    {
                        bpawn[k][0] = px;
                        bpawn[k][1] = py;
                        if(py-py1==2)
                        {
                            bpawn[k][4]=1;
                        }
                        if(py-py1==1)
                        {
                            bpawn[k][4]=-1;
                        }
                        b_kill_a(px,py);
                        occupied_b[px-1][py-1]=1;
                        if(px-py==px1-py1||px+py==px1+py1)
                        {
                            if (z == 1) {
                                b_kill_a(x + 1, y);
                            }
                            if (z == 2) {
                                b_kill_a(x - 1, y);
                            }
                        }
                        is_b_pawn = -1;
                    }
                }
            }
            else
            {
                for(int i=0;i<b_pawn_count[k];i++)
                {
                    if(px==b_pawn_moves[k][i][0]&&py==b_pawn_moves[k][i][1])
                    {
                        if(py-py1==2)
                        {
                            bpawn[k][4]=1;
                        }
                        if(py-py1==1)
                        {
                            bpawn[k][4]=-1;
                        }
                        bpawn[k][0]=px;
                        bpawn[k][1]=py;
                        is_b_pawn=-1;
                        occupied_b[px-1][py-1]=1;
                        b_kill_a(px,py);
                    }
                }
            }
            if (bpawn[k][1] == 8 && (aking[0] != bpawn[k][0] || aking[1] != bpawn[k][1])) {
                x_val = bpawn[k][0];
                bpawn[k][2] = -1;
                switcher = 1;
                select = 1;
            }
        }
    }
    public void b_bishop(int ind) {
        if (bbishop[ind][2] == 0) {
            set();
            if(b_check(bking[0],bking[1])==1)
            {
                bishop_b_count[ind] = 0;int x = bbishop[ind][0];
                int y = bbishop[ind][1];
                //diagonal "1"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i - j == x - y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 0;
                                bbishop[ind][0] = i;
                                bbishop[ind][1] = j;
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 1;
                                if (b_check(bking[0], bking[1]) == 1) {
                                    bishop_b_moves[ind][bishop_b_count[ind]][0] = i;
                                    bishop_b_moves[ind][bishop_b_count[ind]][1] = j;
                                    bishop_b_count[ind]++;
                                }
                                re_set();
                            }
                            else if(occupied_a[i - 1][j - 1] == 1)
                            {
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 0;
                                bbishop[ind][0] = i;
                                bbishop[ind][1] = j;
                                b_kill_a(i, j);
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 1;
                                if (b_check(bking[0], bking[1]) == 1) {
                                    bishop_b_moves[ind][bishop_b_count[ind]][0] = i;
                                    bishop_b_moves[ind][bishop_b_count[ind]][1] = j;
                                    bishop_b_count[ind]++;
                                }
                                re_set();
                                con = -1;
                                break;
                            }
                            else
                            {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                con = 0;
                //diagonal "2"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i - j == x - y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 0;
                                bbishop[ind][0] = i;
                                bbishop[ind][1] = j;
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 1;
                                if (b_check(bking[0], bking[1]) == 1) {
                                    bishop_b_moves[ind][bishop_b_count[ind]][0] = i;
                                    bishop_b_moves[ind][bishop_b_count[ind]][1] = j;
                                    bishop_b_count[ind]++;
                                }
                                re_set();
                                k++;
                            }
                            else if(occupied_a[i - 1][j - 1] == 1)
                            {
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 0;
                                bbishop[ind][0] = i;
                                bbishop[ind][1] = j;
                                b_kill_a(i, j);
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 1;
                                if (b_check(bking[0], bking[1]) == 1) {
                                    bishop_b_moves[ind][bishop_b_count[ind]][0] = i;
                                    bishop_b_moves[ind][bishop_b_count[ind]][1] = j;
                                    bishop_b_count[ind]++;
                                }
                                re_set();
                                con = -1;
                                break;
                            }
                            else {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                con = 0;
                //diagonal "3"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i + j == x + y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 0;
                                bbishop[ind][0] = i;
                                bbishop[ind][1] = j;
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 1;
                                if (b_check(bking[0], bking[1]) == 1) {
                                    bishop_b_moves[ind][bishop_b_count[ind]][0] = i;
                                    bishop_b_moves[ind][bishop_b_count[ind]][1] = j;
                                    bishop_b_count[ind]++;
                                }
                                re_set();
                                k++;
                            }
                            else if(occupied_a[i - 1][j - 1] == 1)
                            {
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 0;
                                bbishop[ind][0] = i;
                                bbishop[ind][1] = j;
                                b_kill_a(i, j);
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 1;
                                if (b_check(bking[0], bking[1]) == 1) {
                                    bishop_b_moves[ind][bishop_b_count[ind]][0] = i;
                                    bishop_b_moves[ind][bishop_b_count[ind]][1] = j;
                                    bishop_b_count[ind]++;
                                }
                                re_set();
                                con = -1;
                                break;
                            }
                            else {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                con = 0;
                //diagonal "4"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i + j == x + y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 0;
                                bbishop[ind][0] = i;
                                bbishop[ind][1] = j;
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 1;
                                if (b_check(bking[0], bking[1]) == 1) {
                                    bishop_b_moves[ind][bishop_b_count[ind]][0] = i;
                                    bishop_b_moves[ind][bishop_b_count[ind]][1] = j;
                                    bishop_b_count[ind]++;
                                }
                                re_set();
                                k++;
                            }
                            else if(occupied_a[i - 1][j - 1] == 1)
                            {
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 0;
                                bbishop[ind][0] = i;
                                bbishop[ind][1] = j;
                                b_kill_a(i, j);
                                occupied_b[bbishop[ind][0] - 1][bbishop[ind][1] - 1] = 1;
                                if (b_check(bking[0], bking[1]) == 1) {
                                    bishop_b_moves[ind][bishop_b_count[ind]][0] = i;
                                    bishop_b_moves[ind][bishop_b_count[ind]][1] = j;
                                    bishop_b_count[ind]++;
                                }
                                re_set();
                                con = -1;
                                break;
                            }
                            else {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                for(int i=0;i<bishop_b_count[ind];i++)
                {
                    if(z_val==0 && px == bishop_b_moves[ind][i][0] && py == bishop_b_moves[ind][i][1])
                    {
                        bbishop[ind][0] = px;
                        bbishop[ind][1] = py;
                        b_kill_a(px,py);
                        is_b_bishop = -1;
                    }
                }
            }
            else
            {
                for(int i=0;i<b_bishop_count[ind];i++)
                {
                    if(px==b_bishop_moves[ind][i][0]&&py==b_bishop_moves[ind][i][1])
                    {
                        bbishop[ind][0]=px;
                        bbishop[ind][1]=py;
                        is_b_bishop=-1;
                        occupied_b[px-1][py-1]=1;
                        b_kill_a(px,py);
                    }
                }
            }
        }
    }
    public void b_rook(int ind) {
        int ok = 0;
        if (brook[ind][2] == 0) {
            set();
            if (b_check(bking[0], bking[1]) == 1) {
                rook_b_count[ind] = 0;
                int x = brook[ind][0];
                int y = brook[ind][1];
                for (int i = x - 1; i > 0; i--) {
                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 0;
                        brook[ind][0] = i;
                        brook[ind][1] = y;
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 1;
                        if (b_check(bking[0], bking[1]) == 1) {
                            rook_b_moves[ind][rook_b_count[ind]][0] = i;
                            rook_b_moves[ind][rook_b_count[ind]][1] = y;
                            rook_b_count[ind]++;
                        }
                        re_set();
                    }
                    else if (occupied_a[i - 1][y - 1] == 1)
                    {
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 0;
                        brook[ind][0] = i;
                        brook[ind][1] = y;
                        b_kill_a(i, y);
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 1;
                        if (b_check(bking[0], bking[1]) == 1) {
                            rook_b_moves[ind][rook_b_count[ind]][0] = i;
                            rook_b_moves[ind][rook_b_count[ind]][1] = y;
                            rook_b_count[ind]++;
                        }
                        re_set();
                        break;
                    }
                    else
                    {
                        break;
                    }
                }
                for (int i = y - 1; i > 0; i--) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 0;
                        brook[ind][0] = x;
                        brook[ind][1] = i;
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 1;
                        if (b_check(bking[0], bking[1]) == 1) {
                            rook_b_moves[ind][rook_b_count[ind]][0] = x;
                            rook_b_moves[ind][rook_b_count[ind]][1] = i;
                            rook_b_count[ind]++;
                            k++;
                        }
                        re_set();
                    }
                    else if (occupied_a[x - 1][i - 1] == 1)
                    {
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 0;
                        brook[ind][0] = x;
                        brook[ind][1] = i;
                        b_kill_a(i, y);
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 1;
                        if (b_check(bking[0], bking[1]) == 1) {
                            rook_b_moves[ind][rook_b_count[ind]][0] = x;
                            rook_b_moves[ind][rook_b_count[ind]][1] = i;
                            rook_b_count[ind]++;
                        }
                        re_set();
                        break;
                    }
                    else
                    {
                        break;
                    }
                }
                for (int i = x + 1; i < 9; i++) {
                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 0;
                        brook[ind][0] = i;
                        brook[ind][1] = y;
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 1;
                        if (b_check(bking[0], bking[1]) == 1) {
                            rook_b_moves[ind][rook_b_count[ind]][0] = i;
                            rook_b_moves[ind][rook_b_count[ind]][1] = y;
                            rook_b_count[ind]++;
                            k++;
                        }
                        re_set();
                    }
                    else if (occupied_a[i - 1][y - 1] == 1)
                    {
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 0;
                        brook[ind][0] = i;
                        brook[ind][1] = y;
                        b_kill_a(i, y);
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 1;
                        if (b_check(bking[0], bking[1]) == 1) {
                            rook_b_moves[ind][rook_b_count[ind]][0] = i;
                            rook_b_moves[ind][rook_b_count[ind]][1] = y;
                            rook_b_count[ind]++;
                        }
                        re_set();
                        break;
                    }
                    else
                    {
                        break;
                    }
                }
                for (int i = y + 1; i < 9; i++) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 0;
                        brook[ind][0] = x;
                        brook[ind][1] = i;
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 1;
                        if (b_check(bking[0], bking[1]) == 1) {
                            rook_b_moves[ind][rook_b_count[ind]][0] = x;
                            rook_b_moves[ind][rook_b_count[ind]][1] = i;
                            rook_b_count[ind]++;
                            k++;
                        }
                        re_set();
                    }
                    else if (occupied_a[x - 1][i - 1] == 1)
                    {
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 0;
                        brook[ind][0] = x;
                        brook[ind][1] = i;
                        b_kill_a(i, y);
                        occupied_b[brook[ind][0] - 1][brook[ind][1] - 1] = 1;
                        if (b_check(bking[0], bking[1]) == 1) {
                            rook_b_moves[ind][rook_b_count[ind]][0] = x;
                            rook_b_moves[ind][rook_b_count[ind]][1] = i;
                            rook_b_count[ind]++;
                        }
                        re_set();
                        break;
                    }
                    else
                    {
                        break;
                    }
                }
                for (int i = 0; i < rook_b_count[ind]; i++) {
                    if (z_val==0 && px == rook_b_moves[ind][i][0] && py == rook_b_moves[ind][i][1]) {
                        brook[ind][0] = px;
                        brook[ind][1] = py;
                        brook[ind][4] = -1;
                        b_kill_a(px, py);
                        is_b_rook = -1;
                    }
                }
            }
            else {
                for (int i = 0; i < b_rook_count[ind]; i++) {
                    if (px == b_rook_moves[ind][i][0] && py == b_rook_moves[ind][i][1]) {
                        brook[ind][0] = px;
                        brook[ind][1] = py;
                        is_b_rook = -1;
                        occupied_b[px - 1][py - 1] = 1;
                        b_kill_a(px, py);
                    }
                }
            }
        }
    }
    public void b_knight(int k) {
        if (bknight[k][2] == 0) {
            set();
            if(b_check(bking[0],bking[1])==1)
            {
                knight_b_count[k]=0;
                int kx[] = new int[4];
                int ky[] = new int[4];
                int x = bknight[k][0];
                int y = bknight[k][1];
                kx[0] = x - 1;
                kx[1] = x + 1;
                kx[2] = x - 2;
                kx[3] = x + 2;

                ky[0] = y - 2;
                ky[1] = y - 1;
                ky[2] = y + 1;
                ky[3] = y + 2;

                if (kx[0] > 0 && ky[0] > 0 && kx[0] < 9 && ky[0] < 9 && occupied_b[kx[0] - 1][ky[0] - 1] == 0) {
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 0;
                    bknight[k][0] = kx[0];
                    bknight[k][1] = ky[0];
                    b_kill_a(bknight[k][0], bknight[k][1]);
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        knight_b_moves[k][knight_b_count[k]][0] = kx[0];
                        knight_b_moves[k][knight_b_count[k]][1] = ky[0];
                        knight_b_count[k]++;
                    }
                    re_set();
                }
                if (kx[1] > 0 && ky[0] > 0 && kx[1] < 9 && ky[0] < 9 && occupied_b[kx[1] - 1][ky[0] - 1] == 0) {
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 0;
                    bknight[k][0] = kx[1];
                    bknight[k][1] = ky[0];
                    b_kill_a(bknight[k][0], bknight[k][1]);
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        knight_b_moves[k][knight_b_count[k]][0] = kx[1];
                        knight_b_moves[k][knight_b_count[k]][1] = ky[0];
                        knight_b_count[k]++;
                    }
                    re_set();
                }
                if (kx[2] > 0 && ky[1] > 0 && kx[2] < 9 && ky[1] < 9 && occupied_b[kx[2] - 1][ky[1] - 1] == 0) {
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 0;
                    bknight[k][0] = kx[2];
                    bknight[k][1] = ky[1];
                    b_kill_a(bknight[k][0], bknight[k][1]);
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        knight_b_moves[k][knight_b_count[k]][0] = kx[2];
                        knight_b_moves[k][knight_b_count[k]][1] = ky[1];
                        knight_b_count[k]++;
                    }
                    re_set();
                }
                if (kx[2] > 0 && ky[2] > 0 && kx[2] < 9 && ky[2] < 9 && occupied_b[kx[2] - 1][ky[2] - 1] == 0) {
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 0;
                    bknight[k][0] = kx[2];
                    bknight[k][1] = ky[2];
                    b_kill_a(bknight[k][0], bknight[k][1]);
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        knight_b_moves[k][knight_b_count[k]][0] = kx[2];
                        knight_b_moves[k][knight_b_count[k]][1] = ky[2];
                        knight_b_count[k]++;
                    }
                    re_set();
                }
                if (kx[0] > 0 && ky[3] > 0 && kx[0] < 9 && ky[3] < 9 && occupied_b[kx[0] - 1][ky[3] - 1] == 0) {
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 0;
                    bknight[k][0] = kx[0];
                    bknight[k][1] = ky[3];
                    b_kill_a(bknight[k][0], bknight[k][1]);
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        knight_b_moves[k][knight_b_count[k]][0] = kx[0];
                        knight_b_moves[k][knight_b_count[k]][1] = ky[3];
                        knight_b_count[k]++;
                    }
                    re_set();
                }
                if (kx[1] > 0 && ky[3] > 0 && kx[1] < 9 && ky[3] < 9 && occupied_b[kx[1] - 1][ky[3] - 1] == 0) {
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 0;
                    bknight[k][0] = kx[1];
                    bknight[k][1] = ky[3];
                    b_kill_a(bknight[k][0], bknight[k][1]);
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        knight_b_moves[k][knight_b_count[k]][0] = kx[1];
                        knight_b_moves[k][knight_b_count[k]][1] = ky[3];
                        knight_b_count[k]++;
                    }
                    re_set();
                }
                if (kx[3] > 0 && ky[1] > 0 && kx[3] < 9 && ky[1] < 9 && occupied_b[kx[3] - 1][ky[1] - 1] == 0) {
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 0;
                    bknight[k][0] = kx[3];
                    bknight[k][1] = ky[1];
                    b_kill_a(bknight[k][0], bknight[k][1]);
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        knight_b_moves[k][knight_b_count[k]][0] = kx[3];
                        knight_b_moves[k][knight_b_count[k]][1] = ky[1];
                        knight_b_count[k]++;
                    }
                    re_set();
                }
                if (kx[3] > 0 && ky[2] > 0 && kx[3] < 9 && ky[2] < 9 && occupied_b[kx[3] - 1][ky[2] - 1] == 0) {
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 0;
                    bknight[k][0] = kx[3];
                    bknight[k][1] = ky[2];
                    b_kill_a(bknight[k][0], bknight[k][1]);
                    occupied_b[bknight[k][0] - 1][bknight[k][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        knight_b_moves[k][knight_b_count[k]][0] = kx[3];
                        knight_b_moves[k][knight_b_count[k]][1] = ky[2];
                        knight_b_count[k]++;
                    }
                    re_set();
                }
                for(int i=0;i<knight_b_count[k];i++)
                {
                    if(z_val==0 && px==knight_b_moves[k][i][0] && py==knight_b_moves[k][i][1])
                    {
                        bknight[k][0] = px;
                        bknight[k][1] = py;
                        b_kill_a(px,py);
                        is_b_knight=-1;
                    }
                }
            }
            else
            {
                for(int i=0;i<b_knight_count[k];i++)
                {
                    if(px==b_knight_moves[k][i][0]&&py==b_knight_moves[k][i][1])
                    {
                        bknight[k][0]=px;
                        bknight[k][1]=py;
                        is_b_knight=-1;
                        occupied_b[px-1][py-1]=1;
                        b_kill_a(px,py);
                    }
                }
            }
        }
    }
    public void b_king()
    {
        king_b_count = 0;
        int ok = 0;
        int castling = -1;
        if (bking[2] == 0) {
            int x = bking[0];
            int y = bking[1];
            int qwe = 0;
            z = 0;
            //constant y
            set();
            if (x > 1 && y > 0 && occupied_b[x - 2][y - 1] == 0) {
                occupied_b[bking[0] - 1][bking[1] - 1] = 0;
                bking[0] = x - 1;
                bking[1] = y;
                b_kill_a(bking[0], bking[1]);
                occupied_b[bking[0] - 1][bking[1] - 1] = 1;
                ok = b_check(x - 1, y);
                if (ok == 1) {
                    king_b_moves[king_b_count][0] = x - 1;
                    king_b_moves[king_b_count][1] = y;
                    king_b_count++;
                }
                re_set();
            }
            if (x < 8 && y > 0 && occupied_b[x][y - 1] == 0) {
                occupied_b[bking[0] - 1][bking[1] - 1] = 0;
                bking[0] = x + 1;
                bking[1] = y;
                b_kill_a(bking[0], bking[1]);
                occupied_b[bking[0] - 1][bking[1] - 1] = 1;
                ok = b_check(x + 1, y);
                if (ok == 1) {
                    king_b_moves[king_b_count][0] = x + 1;
                    king_b_moves[king_b_count][1] = y;
                    king_b_count++;
                }
                re_set();
            }
            //constant x
            if (y > 1 && x > 0 && occupied_b[x - 1][y - 2] == 0) {
                occupied_b[bking[0] - 1][bking[1] - 1] = 0;
                bking[0] = x;
                bking[1] = y - 1;
                b_kill_a(bking[0], bking[1]);
                occupied_b[bking[0] - 1][bking[1] - 1] = 1;
                ok = b_check(x, y - 1);
                if (ok == 1) {
                    king_b_moves[king_b_count][0] = x;
                    king_b_moves[king_b_count][1] = y - 1;
                    king_b_count++;
                }
                re_set();
            }
            if (y < 8 && x > 0 && occupied_b[x - 1][y] == 0) {
                occupied_b[bking[0] - 1][bking[1] - 1] = 0;
                bking[0] = x;
                bking[1] = y + 1;
                b_kill_a(bking[0], bking[1]);
                occupied_b[bking[0] - 1][bking[1] - 1] = 1;
                ok = b_check(x, y + 1);
                if (ok == 1) {
                    king_b_moves[king_b_count][0] = x;
                    king_b_moves[king_b_count][1] = y + 1;
                    king_b_count++;
                }
                re_set();
            }
            //diagonal 1-
            if (x > 1 && y > 1 && occupied_b[x - 2][y - 2] == 0) {
                occupied_b[bking[0] - 1][bking[1] - 1] = 0;
                bking[0] = x - 1;
                bking[1] = y - 1;
                b_kill_a(bking[0], bking[1]);
                occupied_b[bking[0] - 1][bking[1] - 1] = 1;
                ok = b_check(x - 1, y - 1);
                if (ok == 1) {
                    king_b_moves[king_b_count][0] = x - 1;
                    king_b_moves[king_b_count][1] = y - 1;
                    king_b_count++;
                }
                re_set();
            }
            //diagonal 2-
            if (x < 8 && y < 8 && occupied_b[x][y] == 0) {
                occupied_b[bking[0] - 1][bking[1] - 1] = 0;
                bking[0] = x + 1;
                bking[1] = y + 1;
                b_kill_a(bking[0], bking[1]);
                occupied_b[bking[0] - 1][bking[1] - 1] = 1;
                ok = b_check(x + 1, y + 1);
                if (ok == 1) {
                    king_b_moves[king_b_count][0] = x + 1;
                    king_b_moves[king_b_count][1] = y + 1;
                    king_b_count++;
                }
                re_set();
            }
            //diagonal 3-
            if (x > 1 && y < 8 && occupied_b[x - 2][y] == 0) {
                occupied_b[bking[0] - 1][bking[1] - 1] = 0;
                bking[0] = x - 1;
                bking[1] = y + 1;
                b_kill_a(bking[0], bking[1]);
                occupied_b[bking[0] - 1][bking[1] - 1] = 1;
                ok = b_check(x - 1, y + 1);
                if (ok == 1) {
                    king_b_moves[king_b_count][0] = x - 1;
                    king_b_moves[king_b_count][1] = y + 1;
                    king_b_count++;
                }
                re_set();
            }
            //diagonal 4-
            if (x < 8 && y > 1 && occupied_b[x][y - 2] == 0) {
                occupied_b[bking[0] - 1][bking[1] - 1] = 0;
                bking[0] = x + 1;
                bking[1] = y - 1;
                b_kill_a(bking[0], bking[1]);
                occupied_b[bking[0] - 1][bking[1] - 1] = 1;
                ok = b_check(x + 1, y - 1);
                if (ok == 1) {
                    king_b_moves[king_b_count][0] = x + 1;
                    king_b_moves[king_b_count][1] = y - 1;
                    king_b_count++;
                }
                re_set();
            }
            //castling
            if (bking[4] == 0 && b_check(bking[0], bking[1]) == 1) {
                if (brook[0][4] == 0 && brook[0][2] == 0) {
                    for (int i = brook[0][0] + 1; i < bking[0]; i++) {
                        if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0 && b_check(i, y) == 1) {
                            qwe = 1;
                        } else {
                            qwe = -1;
                            break;
                        }
                    }
                    if (qwe == 1) {
                        king_b_moves[king_b_count][0] = x - 2;
                        king_b_moves[king_b_count][1] = y;
                        king_b_count++;
                        castling = 1;
                    }
                }
                if (brook[1][4] == 0 && brook[1][2] == 0) {
                    for (int i = bking[0] + 1; i < brook[1][0]; i++) {
                        if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0 && b_check(i, y) == 1) {
                            qwe = 1;
                        } else {
                            qwe = -1;
                            break;
                        }
                    }
                    if (qwe == 1) {
                        king_b_moves[king_b_count][0] = x + 2;
                        king_b_moves[king_b_count][1] = y;
                        king_b_count++;
                        castling = 1;
                    }
                }
            }
            for (int i = 0; i < king_b_count; i++) {
                if (z_val==0 && px == king_b_moves[i][0] && py == king_b_moves[i][1]) {
                    bking[4] = -1;
                    bking[0] = px;
                    bking[1] = py;
                    if(castling==1 && px1-px==2)
                    {
                        occupied_b[brook[0][0]-1][brook[0][1]-1]=0;
                        brook[0][0]=px+1;
                        brook[0][1]=py;
                        occupied_b[brook[0][0]-1][brook[0][1]-1]=1;
                    }
                    if(castling==1 && px-px1==2)
                    {
                        occupied_b[brook[1][0]-1][brook[1][1]-1]=0;
                        brook[1][0]=px-1;
                        brook[1][1]=py;
                        occupied_b[brook[1][0]-1][brook[1][1]-1]=1;
                    }
                    b_kill_a(px, py);
                    is_b_king = -1;
                }
            }
        }
    }
    public void b_queen(int wq) {
        int k=0;
        if (bqueen[wq][2] == 0) {
            set();
            if(b_check(bking[0],bking[1])==1)
            {
                queen_b_count[wq]=0;
                int x = bqueen[wq][0];
                int y = bqueen[wq][1];
                //constant y-
                for (int i = x + 1; i < 9; i++) {
                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                        bqueen[wq][0] = i;
                        bqueen[wq][1] = y;
                        occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                        if (b_check(bking[0], bking[1]) == 1) {
                            queen_b_moves[wq][queen_b_count[wq]][0] = i;
                            queen_b_moves[wq][queen_b_count[wq]][1] = y;
                            queen_b_count[wq]++;
                            k++;
                        }
                        re_set();
                    } else
                        break;
                }
                if (y > 0 && x + k < 8 && occupied_a[x + k][y - 1] == 1) {
                    occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                    bqueen[wq][0] = x + k + 1;
                    bqueen[wq][1] = y;
                    b_kill_a(x + k + 1, y);
                    occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        queen_b_moves[wq][queen_b_count[wq]][0] =x + k + 1;
                        queen_b_moves[wq][queen_b_count[wq]][1] = y;
                        queen_b_count[wq]++;
                    }
                    re_set();
                }
                k = 0;
                for (int i = x - 1; i > 0; i--) {

                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                        bqueen[wq][0] = i;
                        bqueen[wq][1] = y;
                        occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                        if (b_check(bking[0], bking[1]) == 1) {
                            queen_b_moves[wq][queen_b_count[wq]][0] = i;
                            queen_b_moves[wq][queen_b_count[wq]][1] = y;
                            queen_b_count[wq]++;
                            k++;
                        }
                        re_set();
                    } else
                        break;
                }
                if (x > k + 1 && y > 0 && occupied_a[x - k - 2][y - 1] == 1) {
                    occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                    bqueen[wq][0] = x - k - 1;
                    bqueen[wq][1] = y;
                    b_kill_a(x - k - 1, y);
                    occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        queen_b_moves[wq][queen_b_count[wq]][0] = x - k - 1;
                        queen_b_moves[wq][queen_b_count[wq]][1] = y;
                        queen_b_count[wq]++;
                    }
                    re_set();
                }
                k = 0;
                //diagonal "1"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i - j == x - y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                                bqueen[wq][0] = i;
                                bqueen[wq][1] = j;
                                occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                                if (b_check(bking[0], bking[1]) == 1) {
                                    queen_b_moves[wq][queen_b_count[wq]][0] = i;
                                    queen_b_moves[wq][queen_b_count[wq]][1] = j;
                                    queen_b_count[wq]++;
                                    k++;
                                }
                                re_set();
                            } else {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                if (x + k < 8 && y + k < 8 && occupied_a[x + k][y + k] == 1) {
                    occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                    bqueen[wq][0] = x + k + 1;
                    bqueen[wq][1] = y + k + 1;
                    b_kill_a(x + k + 1, y + k + 1);
                    occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        queen_b_moves[wq][queen_b_count[wq]][0] = x + k + 1 ;
                        queen_b_moves[wq][queen_b_count[wq]][1] = y + k + 1;
                        queen_b_count[wq]++;
                        k++;
                    }
                    re_set();
                }
                k = 0;
                con = 0;
                //diagonal "2"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i - j == x - y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                                bqueen[wq][0] = i;
                                bqueen[wq][1] = j;
                                occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                                if (b_check(bking[0], bking[1]) == 1) {
                                    queen_b_moves[wq][queen_b_count[wq]][0] = i;
                                    queen_b_moves[wq][queen_b_count[wq]][1] = j;
                                    queen_b_count[wq]++;
                                    k++;
                                }
                                re_set();
                            } else {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                if (x > k + 1 && y > k + 1 && occupied_a[x - k - 2][y - k - 2] == 1) {
                    occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                    bqueen[wq][0] = x - k - 1;
                    bqueen[wq][1] = y - k - 1;
                    b_kill_a(x - k - 1, y - k - 1);
                    occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        queen_b_moves[wq][queen_b_count[wq]][0] = x - k - 1 ;
                        queen_b_moves[wq][queen_b_count[wq]][1] = y - k - 1;
                        queen_b_count[wq]++;
                        k++;
                    }
                    re_set();
                }
                k = 0;
                con = 0;
                //diagonal "3"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i + j == x + y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                                bqueen[wq][0] = i;
                                bqueen[wq][1] = j;
                                occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                                if (b_check(bking[0], bking[1]) == 1) {
                                    queen_b_moves[wq][queen_b_count[wq]][0] = i;
                                    queen_b_moves[wq][queen_b_count[wq]][1] = j;
                                    queen_b_count[wq]++;
                                    k++;
                                }
                                re_set();
                            } else {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                if (y > k + 1 && x + k < 8 && occupied_a[x + k][y - k - 2] == 1) {
                    occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                    bqueen[wq][0] = x + k + 1;
                    bqueen[wq][1] = y - k - 1;
                    b_kill_a(x + k + 1, y - k - 1);
                    occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        queen_b_moves[wq][queen_b_count[wq]][0] = x + k + 1 ;
                        queen_b_moves[wq][queen_b_count[wq]][1] = y - k - 1;
                        queen_b_count[wq]++;
                        k++;
                    }
                    re_set();
                }
                k = 0;
                con = 0;
                //diagonal "4"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i + j == x + y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                                bqueen[wq][0] = i;
                                bqueen[wq][1] = j;
                                occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                                if (b_check(bking[0], bking[1]) == 1) {
                                    queen_b_moves[wq][queen_b_count[wq]][0] = i;
                                    queen_b_moves[wq][queen_b_count[wq]][1] = j;
                                    queen_b_count[wq]++;
                                    k++;
                                }
                                re_set();
                            } else {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if (con == -1) {
                        break;
                    }
                }
                if (x > k + 1 && y + k < 8 && occupied_a[x - k - 2][y + k] == 1) {
                    occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                    bqueen[wq][0] = x - k - 1;
                    bqueen[wq][1] = y + k + 1;
                    b_kill_a(x - k - 1, y + k + 1);
                    occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                    if (b_check(bking[0], bking[1]) == 1) {
                        queen_b_moves[wq][queen_b_count[wq]][0] = x - k - 1 ;
                        queen_b_moves[wq][queen_b_count[wq]][1] = y + k + 1;
                        queen_b_count[wq]++;
                        k++;
                    }
                    re_set();
                }
                k = 0;
                con = 0;
                //constant x-
                for (int i = y + 1; i < 9; i++) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                        bqueen[wq][0] = x;
                        bqueen[wq][1] = i;
                        occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                        if (b_check(bking[0], bking[1]) == 1) {
                            queen_b_moves[wq][queen_b_count[wq]][0] = x;
                            queen_b_moves[wq][queen_b_count[wq]][1] = i;
                            queen_b_count[wq]++;
                            k++;
                        }
                        re_set();
                    } else
                        break;
                }
                if (y + k < 8 && x > 0 && occupied_a[x - 1][y + k] == 1) {
                    queen_b_moves[wq][queen_b_count[wq]][0] = x;
                    queen_b_moves[wq][queen_b_count[wq]][1] = y + k + 1;
                    queen_b_count[wq]++;
                }
                k = 0;
                for (int i = y - 1; i > 0; i--) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 0;
                        bqueen[wq][0] = x;
                        bqueen[wq][1] = i;
                        occupied_b[bqueen[wq][0] - 1][bqueen[wq][1] - 1] = 1;
                        if (b_check(bking[0], bking[1]) == 1) {
                            queen_b_moves[wq][queen_b_count[wq]][0] = x;
                            queen_b_moves[wq][queen_b_count[wq]][1] = i;
                            queen_b_count[wq]++;
                            k++;
                        }
                        re_set();
                    } else
                        break;
                }
                if (y > k + 1 && x > 0 && occupied_a[x - 1][y - k - 2] == 1) {
                    queen_b_moves[wq][queen_b_count[wq]][0] = x;
                    queen_b_moves[wq][queen_b_count[wq]][1] = y - k - 1;
                    queen_b_count[wq]++;
                }
                for(int i=0;i<queen_b_count[wq];i++)
                {
                    if(z_val==0 && px==queen_b_moves[wq][i][0]&&py==queen_b_moves[wq][i][1])
                    {
                        bqueen[wq][0]=px;
                        bqueen[wq][1]=py;
                        b_kill_a(px,py);
                        is_b_queen=-1;
                    }
                }
            }
            else
            {
                for(int i=0;i<b_queen_count[wq];i++)
                {
                    if(px==b_queen_moves[wq][i][0]&&py==b_queen_moves[wq][i][1])
                    {
                        bqueen[wq][0]=px;
                        bqueen[wq][1]=py;
                        b_kill_a(px,py);
                        is_b_queen=-1;
                    }
                }
            }

        }
    }
    public void b_kill_a(int x,int y)
    {
        if(occupied_a[x-1][y-1]==1)
        {
            for(int i=0;i<8;i++)
            {
                if(apawn[i][2]==0)
                {
                    if(apawn[i][0]==x && apawn[i][1]==y)
                    {
                        apawn[i][2]=-1;
                        occupied_a[x-1][y-1]=0;
                    }
                }
            }
            for(int i=0;i<aqc;i++)
            {
                if(aqueen[i][2]==0)
                {
                    if(aqueen[i][0]==x && aqueen[i][1]==y)
                    {
                        aqueen[i][2]=-1;
                        occupied_a[x-1][y-1]=0;
                    }
                }
            }
            for(int i=0;i<aknc;i++)
            {
                if(aknight[i][2]==0)
                {
                    if(aknight[i][0]==x && aknight[i][1]==y)
                    {
                        aknight[i][2]=-1;
                        occupied_a[x-1][y-1]=0;
                    }
                }
            }
            for(int i=0;i<abc;i++)
            {
                if(abishop[i][2]==0)
                {
                    if(abishop[i][0]==x && abishop[i][1]==y)
                    {
                        abishop[i][2]=-1;
                        occupied_a[x-1][y-1]=0;
                    }
                }
            }
            for(int i=0;i<arc;i++)
            {
                if(arook[i][2]==0)
                {
                    if(arook[i][0]==x && arook[i][1]==y)
                    {
                        arook[i][2]=-1;
                        occupied_a[x-1][y-1]=0;
                    }
                }
            }
            if(aking[2]==0 && aking[0]==x && aking[1]==y)
            {
                aking[2]=-1;
                occupied_a[x-1][y-1]=0;
            }
        }
    }
    public int b_check(int kix,int kiy)//To check if King is moving into check
    {
        con = 0;
        int z = 0;
        k = 0;
        int king_x = bking[0];
        int king_y = bking[1];
        int count=0;
        int a_moves[][] = new int[150][2];
        //queen
        for(int wq=0;wq<aqc;wq++)
        {
            if (aqueen[wq][2] == 0) {
                int x = aqueen[wq][0];
                int y = aqueen[wq][1];
                //constant y-
                for (int i = x + 1; i < 9; i++) {

                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        a_moves[z][0] = i;
                        a_moves[z][1] = y;
                        z++;
                    }
                    else if (occupied_b[i - 1][y - 1] == 1) {
                        if (i == king_x && y == king_y) {
                            a_moves[z][0] = i;
                            a_moves[z][1] = y;
                            z++;
                            for (int j = king_x + 1; j < 9; j++) {
                                if(occupied_a[j-1][y-1]==0&&occupied_b[j-1][y-1]==0) {
                                    a_moves[z][0] = j;
                                    a_moves[z][1] = y;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            a_moves[z][0] = i;
                            a_moves[z][1] = y;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
                for (int i = x - 1; i > 0; i--) {

                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        a_moves[z][0] = i;
                        a_moves[z][1] = y;
                        z++;
                    } else if (occupied_b[i - 1][y - 1] == 1)
                    {
                        if (i == king_x && y == king_y){
                            a_moves[z][0] = i;
                            a_moves[z][1] = y;
                            z++;
                            for (int j = king_x - 1; j > 0; j--) {
                                if(occupied_a[j-1][y-1]==0&&occupied_b[j-1][y-1]==0) {
                                    a_moves[z][0] = j;
                                    a_moves[z][1] = y;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            a_moves[z][0] = i;
                            a_moves[z][1] = y;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
                //diagonal "1"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i - j == x - y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            a_moves[z][0] = i;
                            a_moves[z][1] = j;
                            z++;
                        }
                        else if (i - j == x - y && occupied_b[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                a_moves[z][0]=king_x;
                                a_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x + 1; k1 < 9; k1++) {
                                    for (int k2 = king_y + 1; k2 < 9; k2++) {
                                        if (k1 - k2 == king_x - king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                a_moves[z][0] = k1;
                                                a_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                break;
                                        }
                                    }
                                }
                                con = -1;
                            }
                            else {
                                a_moves[z][0] = i;
                                a_moves[z][1] = j;
                                con = -1;
                                z++;
                                break;
                            }
                        }
                        else if (i - j == x - y && occupied_a[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }
                //diagonal "2"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i - j == x - y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            a_moves[z][0] = i;
                            a_moves[z][1] = j;
                            z++;
                        }
                        else if (i - j == x - y && occupied_b[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                a_moves[z][0]=king_x;
                                a_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x - 1; k1 > 0; k1--) {
                                    for (int k2 = king_y - 1; k2 > 0; k2--) {
                                        if (k1 - k2 == king_x - king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                a_moves[z][0] = k1;
                                                a_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                break;
                                        }
                                    }
                                }
                                con = -1;
                            }
                            else {
                                a_moves[z][0] = i;
                                a_moves[z][1] = j;
                                con = -1;
                                z++;
                                break;
                            }
                        }
                        else if (i - j == x - y && occupied_a[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }

                //diagonal "3"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i + j == x + y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            a_moves[z][0] = i;
                            a_moves[z][1] = j;
                            z++;
                        }
                        else if (i + j == x + y && occupied_b[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                a_moves[z][0]=king_x;
                                a_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x + 1; k1 < 9; k1++) {
                                    for (int k2 = king_y - 1; k2 > 0; k2--) {
                                        if (k1 + k2 == king_x + king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                a_moves[z][0] = k1;
                                                a_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                con = -1;
                                            break;
                                        }
                                    }
                                    if(con == -1)
                                    {
                                        con =0;
                                        break;
                                    }
                                }
                                con = -1;
                            }
                            else {
                                a_moves[z][0] = i;
                                a_moves[z][1] = j;
                                con = -1;
                                z++;
                                break;
                            }
                        }
                        else if (i + j == x + y && occupied_a[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }
                //diagonal "4"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i + j == x + y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            a_moves[z][0] = i;
                            a_moves[z][1] = j;
                            z++;
                        }
                        else if (i + j == x + y && occupied_b[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                a_moves[z][0]=king_x;
                                a_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x - 1; k1 > 0; k1--) {
                                    for (int k2 = king_y + 1; k2 < 9; k2++) {
                                        if (k1 + k2 == king_x + king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                a_moves[z][0] = k1;
                                                a_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                con=-1;
                                            break;
                                        }
                                    }
                                    if(con == -1)
                                    {
                                        con =0;
                                        break;
                                    }
                                }
                                con = -1;
                            }
                            else {
                                a_moves[z][0] = i;
                                a_moves[z][1] = j;
                                con = -1;
                                z++;
                                break;
                            }
                        }
                        else if (i + j == x + y && occupied_a[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }
                //constant x-
                for (int i = y + 1; i < 9; i++) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        a_moves[z][0] = x;
                        a_moves[z][1] = i;
                        z++;
                    }
                    else if (occupied_b[x - 1][i - 1] == 1) {
                        if (x == king_x && i == king_y) {
                            a_moves[z][0] = x;
                            a_moves[z][1] = i;
                            z++;
                            for (int j = king_y + 1; j < 9; j++) {
                                if(occupied_a[x-1][j-1]==0&&occupied_b[x-1][j-1]==0) {
                                    a_moves[z][0] = x;
                                    a_moves[z][1] = j;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            a_moves[z][0] = x;
                            a_moves[z][1] = i;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
                for (int i = y - 1; i > 0; i--) {

                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        a_moves[z][0] = x;
                        a_moves[z][1] = i;
                        z++;
                    }
                    else if (occupied_b[x - 1][i - 1] == 1) {
                        if (x == king_x && i == king_y) {
                            a_moves[z][0] = x;
                            a_moves[z][1] = i;
                            z++;
                            for (int j = king_y - 1; j > 0; j--) {
                                if(occupied_a[x-1][j-1]==0&&occupied_b[x-1][j-1]==0) {
                                    a_moves[z][0] = x;
                                    a_moves[z][1] = j;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            a_moves[z][0] = x;
                            a_moves[z][1] = i;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
            }
        }
        //pawn-
        for (int i = 0; i < 8; i++) {
            if (apawn[i][2] == 0) {
                int x = apawn[i][0];
                int y = apawn[i][1];
                if (x < 8 && y < 8 ) {//altered so king can't be moved voluntarily into a place where pawn can kill him
                    a_moves[z][0] = x + 1;
                    a_moves[z][1] = y - 1;
                    z++;
                }
                if (x > 1 && y < 8 ) {
                    a_moves[z][0] = x - 1;
                    a_moves[z][1] = y - 1;
                    z++;
                }
            }
        }
        //rook-
        for (int wq = 0; wq < arc; wq++) {
            if (arook[wq][2] == 0) {
                int x = arook[wq][0];
                int y = arook[wq][1];
                //constant y-
                for (int i = x + 1; i < 9; i++) {
                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        a_moves[z][0] = i;
                        a_moves[z][1] = y;
                        z++;
                    }
                    else if (occupied_b[i - 1][y - 1] == 1) {
                        if (i == king_x && y == king_y) {
                            a_moves[z][0] = i;
                            a_moves[z][1] = y;
                            z++;
                            for (int j = king_x + 1; j < 9; j++) {
                                if(occupied_a[j-1][y-1]==0&&occupied_b[j-1][y-1]==0) {
                                    a_moves[z][0] = j;
                                    a_moves[z][1] = y;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            a_moves[z][0] = i;
                            a_moves[z][1] = y;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
                for (int i = x - 1; i > 0; i--)
                {
                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        a_moves[z][0] = i;
                        a_moves[z][1] = y;
                        z++;
                    } else if (occupied_b[i - 1][y - 1] == 1)
                    {
                        if (i == king_x && y == king_y){
                            a_moves[z][0] = i;
                            a_moves[z][1] = y;
                            z++;
                            for (int j = king_x - 1; j > 0; j--) {
                                if(occupied_a[j-1][y-1]==0&&occupied_b[j-1][y-1]==0) {
                                    a_moves[z][0] = j;
                                    a_moves[z][1] = y;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            a_moves[z][0] = i;
                            a_moves[z][1] = y;
                            z++;
                            break;
                        }
                    }
                    else
                        break;

                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
                //constant x-
                for (int i = y + 1; i < 9; i++) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        a_moves[z][0] = x;
                        a_moves[z][1] = i;
                        z++;
                    }
                    else if (occupied_b[x - 1][i - 1] == 1) {
                        if (x == king_x && i == king_y) {
                            a_moves[z][0] = x;
                            a_moves[z][1] = i;
                            z++;
                            for (int j = king_y + 1; j < 9; j++) {
                                if(occupied_a[x-1][j-1]==0&&occupied_b[x-1][j-1]==0) {
                                    a_moves[z][0] = x;
                                    a_moves[z][1] = j;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            a_moves[z][0] = x;
                            a_moves[z][1] = i;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
                for (int i = y - 1; i > 0; i--) {

                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        a_moves[z][0] = x;
                        a_moves[z][1] = i;
                        z++;
                    }
                    else if (occupied_b[x - 1][i - 1] == 1) {
                        if (x == king_x && i == king_y) {
                            a_moves[z][0] = x;
                            a_moves[z][1] = i;
                            z++;
                            for (int j = king_y - 1; j > 0; j--) {
                                if(occupied_a[x-1][j-1]==0&&occupied_b[x-1][j-1]==0) {
                                    a_moves[z][0] = x;
                                    a_moves[z][1] = j;
                                    z++;
                                }
                                else
                                    break;
                            }
                            con=-1;
                        }
                        else {
                            a_moves[z][0] = x;
                            a_moves[z][1] = i;
                            z++;
                            break;
                        }
                    }
                    else
                        break;
                    if(con==-1)
                    {
                        con=0;
                        break;
                    }
                }
            }
        }
        //knight//nothing altered
        for (int i = 0; i < aknc; i++) {
            if (aknight[i][2] == 0) {
                int kx[] = new int[4];
                int ky[] = new int[4];
                int x = aknight[i][0];
                int y = aknight[i][1];
                kx[0] = x - 1;
                kx[1] = x + 1;
                kx[2] = x - 2;
                kx[3] = x + 2;

                ky[0] = y - 2;
                ky[1] = y - 1;
                ky[2] = y + 1;
                ky[3] = y + 2;

                if (kx[0] > 0 && ky[0] > 0 && kx[0] < 9 && ky[0] < 9 && occupied_a[kx[0] - 1][ky[0] - 1] == 0) {
                    a_moves[z][0] = kx[0];
                    a_moves[z][1] = ky[0];
                    z++;
                }
                if (kx[1] > 0 && ky[0] > 0 && kx[1] < 9 && ky[0] < 9 && occupied_a[kx[1] - 1][ky[0] - 1] == 0) {
                    a_moves[z][0] = kx[1];
                    a_moves[z][1] = ky[0];
                    z++;
                }
                if (kx[2] > 0 && ky[1] > 0 && kx[2] < 9 && ky[1] < 9 && occupied_a[kx[2] - 1][ky[1] - 1] == 0) {
                    a_moves[z][0] = kx[2];
                    a_moves[z][1] = ky[1];
                    z++;
                }
                if (kx[2] > 0 && ky[2] > 0 && kx[2] < 9 && ky[2] < 9 && occupied_a[kx[2] - 1][ky[2] - 1] == 0) {
                    a_moves[z][0] = kx[2];
                    a_moves[z][1] = ky[2];
                    z++;
                }
                if (kx[0] > 0 && ky[3] > 0 && kx[0] < 9 && ky[3] < 9 && occupied_a[kx[0] - 1][ky[3] - 1] == 0) {
                    a_moves[z][0] = kx[0];
                    a_moves[z][1] = ky[3];
                    z++;
                }
                if (kx[1] > 0 && ky[3] > 0 && kx[1] < 9 && ky[3] < 9 && occupied_a[kx[1] - 1][ky[3] - 1] == 0) {
                    a_moves[z][0] = kx[1];
                    a_moves[z][1] = ky[3];
                    z++;
                }
                if (kx[3] > 0 && ky[1] > 0 && kx[3] < 9 && ky[1] < 9 && occupied_a[kx[3] - 1][ky[1] - 1] == 0) {
                    a_moves[z][0] = kx[3];
                    a_moves[z][1] = ky[1];
                    z++;
                }
                if (kx[3] > 0 && ky[2] > 0 && kx[3] < 9 && ky[2] < 9 && occupied_a[kx[3] - 1][ky[2] - 1] == 0) {
                    a_moves[z][0] = kx[3];
                    a_moves[z][1] = ky[2];
                    z++;
                }
            }
        }
        //Bishop
        for (int w = 0; w < abc; w++) {
            if (abishop[w][2] == 0) {
                int x = abishop[w][0];
                int y = abishop[w][1];
                //diagonal "1"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i - j == x - y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            a_moves[z][0] = i;
                            a_moves[z][1] = j;
                            z++;
                        }
                        else if (i - j == x - y && occupied_b[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                a_moves[z][0]=king_x;
                                a_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x + 1; k1 < 9; k1++) {
                                    for (int k2 = king_y + 1; k2 < 9; k2++) {
                                        if (k1 - k2 == king_x - king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                a_moves[z][0] = k1;
                                                a_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                break;
                                        }
                                    }
                                }
                                con = -1;
                            }
                            else {
                                a_moves[z][0] = i;
                                a_moves[z][1] = j;
                                con = -1;
                                z++;
                                break;
                            }
                        }
                        else if (i - j == x - y && occupied_a[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }
                //diagonal "2"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i - j == x - y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            a_moves[z][0] = i;
                            a_moves[z][1] = j;
                            z++;
                        }
                        else if (i - j == x - y && occupied_b[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                a_moves[z][0]=king_x;
                                a_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x - 1; k1 > 0; k1--) {
                                    for (int k2 = king_y - 1; k2 > 0; k2--) {
                                        if (k1 - k2 == king_x - king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                a_moves[z][0] = k1;
                                                a_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                break;
                                        }
                                    }
                                }
                                con = -1;
                            }
                            else {
                                a_moves[z][0] = i;
                                a_moves[z][1] = j;
                                con = -1;
                                z++;
                                break;
                            }
                        }
                        else if (i - j == x - y && occupied_a[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }

                //diagonal "3"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i + j == x + y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            a_moves[z][0] = i;
                            a_moves[z][1] = j;
                            z++;
                        }
                        else if (i + j == x + y && occupied_b[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                a_moves[z][0]=king_x;
                                a_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x + 1; k1 < 9; k1++) {
                                    for (int k2 = king_y - 1; k2 > 0; k2--) {
                                        if (k1 + k2 == king_x + king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                a_moves[z][0] = k1;
                                                a_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                con = -1;
                                            break;
                                        }
                                    }
                                    if(con == -1)
                                    {
                                        con =0;
                                        break;
                                    }
                                }
                                con = -1;
                            }
                            else {
                                a_moves[z][0] = i;
                                a_moves[z][1] = j;
                                con = -1;
                                z++;
                                break;
                            }
                        }
                        else if (i + j == x + y && occupied_a[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }
                //diagonal "4"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i + j == x + y && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0 && con == 0)
                        {
                            a_moves[z][0] = i;
                            a_moves[z][1] = j;
                            z++;
                        }
                        else if (i + j == x + y && occupied_b[i - 1][j - 1] == 1) {
                            if (i == king_x && j == king_y) {
                                a_moves[z][0]=king_x;
                                a_moves[z][1]=king_y;
                                z++;
                                for (int k1 = king_x - 1; k1 > 0; k1--) {
                                    for (int k2 = king_y + 1; k2 < 9; k2++) {
                                        if (k1 + k2 == king_x + king_y) {
                                            if (occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                                a_moves[z][0] = k1;
                                                a_moves[z][1] = k2;
                                                z++;
                                            }
                                            else
                                                con=-1;
                                            break;
                                        }
                                    }
                                    if(con == -1)
                                    {
                                        con =0;
                                        break;
                                    }
                                }
                                con = -1;
                            }
                            else {
                                a_moves[z][0] = i;
                                a_moves[z][1] = j;
                                con = -1;
                                z++;
                                break;
                            }
                        }
                        else if (i + j == x + y && occupied_a[i - 1][j - 1] == 1) {
                            con = -1;
                            break;
                        }
                    }
                    if (con == -1) {
                        con = 0;
                        break;
                    }
                }
            }
        }
        //king
        if(aking[2]==0)
        {
            int x=aking[0];
            int y=aking[1];
            //constant x-
            if(x>0&&y>1&&occupied_a[x-1][y-2]==0)
            {
                a_moves[z][0]=x;
                a_moves[z][1]=y-1;
                z++;
            }
            if(x>0&&y<8&&occupied_a[x-1][y]==0)
            {
                a_moves[z][0]=x;
                a_moves[z][1]=y+1;
                z++;
            }
            //constant y-
            if(x>1&&y>0&&occupied_a[x-2][y-1]==0)
            {
                a_moves[z][0]=x-1;
                a_moves[z][1]=y;
                z++;
            }
            if(y>0&&x<8&&occupied_a[x][y-1]==0)
            {
                a_moves[z][0]=x+1;
                a_moves[z][1]=y;
                z++;
            }
            //
            if(x<8&&y<8&&occupied_a[x][y]==0)
            {
                a_moves[z][0]=x+1;
                a_moves[z][1]=y+1;
                z++;
            }
            if(x>1&&y>1&&occupied_a[x-2][y-2]==0)
            {
                a_moves[z][0]=x-1;
                a_moves[z][1]=y-1;
                z++;
            }
            //
            if(x<8&&y>1&&occupied_a[x][y-2]==0)
            {
                a_moves[z][0]=x+1;
                a_moves[z][1]=y-1;
                z++;
            }
            if(x>1&&y<8&&occupied_a[x-2][y]==0)
            {
                a_moves[z][0]=x-1;
                a_moves[z][1]=y+1;
                z++;
            }
        }
        for(int i=0;i<z;i++)
        {
            if(kix==a_moves[i][0]&&kiy==a_moves[i][1])
            {
                count++;
            }
        }
        if(count>0)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
    public int b_checkmate() {
        z = 0;
        count1 = 0;
        set();
        //PAWN
        for (int i = 0; i < 8; i++) {
            if (bpawn[i][2] == 0) {
                b_pawn_count[i] = 0;
                int ax = bpawn[i][0];
                int ay = bpawn[i][1];
                if (bpawn[i][4] == 0) {
                    if ((ax > 0 && ay < 7) && occupied_a[ax - 1][ay] == 0 && occupied_a[ax - 1][ay + 1] == 0 && occupied_b[ax - 1][ay] == 0 && occupied_b[ax - 1][ay + 1] == 0) {
                        occupied_b[bpawn[i][0] - 1][bpawn[i][1] - 1] = 0;
                        bpawn[i][1] = ay + 2;
                        occupied_b[apawn[i][0] - 1][bpawn[i][1] - 1] = 1;
                        if (increment1()) {
                            b_pawn_moves[i][b_pawn_count[i]][0] = bpawn[i][0];
                            b_pawn_moves[i][b_pawn_count[i]][1] = bpawn[i][1];
                            b_pawn_count[i]++;
                            count1++;
                            bpawn[i][5] = 1;
                        }
                        re_set();
                    }
                }
                if ((ax > 0 && ay < 8) && occupied_a[ax - 1][ay] == 0 && occupied_b[ax - 1][ay] == 0) {
                    occupied_b[bpawn[i][0] - 1][bpawn[i][1] - 1] = 0;
                    bpawn[i][1] = ay + 1;
                    occupied_b[bpawn[i][0] - 1][bpawn[i][1] - 1] = 1;
                    if (increment1()) {
                        b_pawn_moves[i][b_pawn_count[i]][0] = bpawn[i][0];
                        b_pawn_moves[i][b_pawn_count[i]][1] = bpawn[i][1];
                        b_pawn_count[i]++;
                        count1++;
                        bpawn[i][5] = 1;
                    }
                    re_set();
                }
                if (ax > 1 && ay < 8 && occupied_a[ax - 2][ay] == 1) {
                    occupied_b[bpawn[i][0] - 1][bpawn[i][1] - 1] = 0;
                    bpawn[i][0] = ax - 1;
                    bpawn[i][1] = ay + 1;
                    b_kill_a(ax - 1, ay + 1);
                    occupied_b[bpawn[i][0] - 1][bpawn[i][1] - 1] = 1;
                    if (increment1()) {
                        b_pawn_moves[i][b_pawn_count[i]][0] = ax - 1;
                        b_pawn_moves[i][b_pawn_count[i]][1] = ay + 1;
                        b_pawn_count[i]++;
                        count1++;
                        bpawn[i][5] = 1;
                    }
                    re_set();
                }
                if (ax < 8 && ay < 8 && occupied_a[ax][ay] == 1) {
                    occupied_b[bpawn[i][0] - 1][bpawn[i][1] - 1] = 0;
                    bpawn[i][0] = ax + 1;
                    bpawn[i][0] = ay + 1;
                    b_kill_a(ax + 1, ay + 1);
                    occupied_b[bpawn[i][0] - 1][bpawn[i][1] - 1] = 1;
                    if (increment1()) {
                        b_pawn_moves[i][b_pawn_count[i]][0] = ax + 1;
                        b_pawn_moves[i][b_pawn_count[i]][1] = ay + 1;
                        b_pawn_count[i]++;
                        count1++;
                        bpawn[i][5] = 1;
                    }
                    re_set();
                }
                //en passant
                if (ax < 8 && ay > 0 && occupied_a[ax][ay - 1] == 1)
                {
                    for(int k=0;k<8;k++)
                    {
                        if(apawn[k][0]==ax+1&&apawn[k][1]==ay&&apawn[k][4]==1)
                        {
                            occupied_b[bpawn[i][0] - 1][bpawn[i][1] - 1] = 0;
                            bpawn[i][0] = ax + 1;
                            bpawn[i][0] = ay + 1;
                            apawn[k][2]=-1;
                            occupied_b[bpawn[i][0] - 1][bpawn[i][1] - 1] = 1;
                            if (increment())
                            {
                                b_pawn_moves[i][b_pawn_count[i]][0] = ax + 1;
                                b_pawn_moves[i][b_pawn_count[i]][1] = ay + 1;
                                b_pawn_moves[i][b_pawn_count[i]][2] = 1;
                                b_pawn_count[i]++;
                                count1++;
                                bpawn[i][5] = 1;
                            }
                            re_set();
                        }
                    }
                }
                if (ax > 1 && ay > 0 && occupied_a[ax-2][ay - 1] == 1)
                {
                    for(int k=0;k<8;k++)
                    {
                        if(apawn[k][0]==ax-1&&apawn[k][1]==ay&&apawn[k][4]==1)
                        {
                            occupied_b[bpawn[i][0] - 1][bpawn[i][1] - 1] = 0;
                            bpawn[i][0] = ax - 1;
                            bpawn[i][0] = ay + 1;
                            b_kill_a(ax - 1, ay);
                            apawn[k][2]=-1;
                            occupied_b[bpawn[i][0] - 1][bpawn[i][1] - 1] = 1;
                            if (increment())
                            {
                                b_pawn_moves[i][b_pawn_count[i]][0] = ax - 1;
                                b_pawn_moves[i][b_pawn_count[i]][1] = ay + 1;
                                b_pawn_moves[i][b_pawn_count[i]][2] = 1;
                                b_pawn_count[i]++;
                                count1++;
                                bpawn[i][5] = 1;
                            }
                            re_set();
                        }
                    }
                }
            }
        }
        //QUEEN
        for (int i = 0; i < bqc; i++) {
            if (bqueen[i][2] == 0) {
                int ax = bqueen[i][0];
                int ay = bqueen[i][1];
                b_queen_count[i] = 0;
                set();
                //constant x-
                con = 0;
                for (int j = ay + 1; j < 9; j++) {
                    if (occupied_b[ax - 1][j - 1] == 0) {
                        if (occupied_a[ax - 1][j - 1] == 0) {
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                            bqueen[i][1] = j;
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                            if (increment1()) {
                                b_queen_moves[i][b_queen_count[i]][0] = ax;
                                b_queen_moves[i][b_queen_count[i]][1] = j;
                                b_queen_count[i]++;
                                count1++;
                                bqueen[i][4] = 1;
                            }
                            re_set();
                        }
                        if (occupied_a[ax - 1][j - 1] == 1) {
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                            bqueen[i][1] = j;
                            b_kill_a(bqueen[i][0], bqueen[i][1]);
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                            if (increment1()) {
                                b_queen_moves[i][b_queen_count[i]][0] = ax;
                                b_queen_moves[i][b_queen_count[i]][1] = j;
                                b_queen_count[i]++;
                                count1++;
                                bqueen[i][4] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    }
                    else if (occupied_b[ax - 1][j - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                con = 0;
                for (int j = ay - 1; j > 0; j--) {
                    if (occupied_b[ax - 1][j - 1] == 0) {
                        if (occupied_a[ax - 1][j - 1] == 0) {
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                            bqueen[i][1] = j;
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                            if (increment1()) {
                                b_queen_moves[i][b_queen_count[i]][0] = ax;
                                b_queen_moves[i][b_queen_count[i]][1] = j;
                                b_queen_count[i]++;
                                count1++;
                                bqueen[i][4] = 1;
                            }
                            re_set();
                        }
                        if (occupied_a[ax - 1][j - 1] == 1) {
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                            bqueen[i][1] = j;
                            b_kill_a(bqueen[i][0], bqueen[i][1]);
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                            if (increment1()) {
                                b_queen_moves[i][b_queen_count[i]][0] = ax;
                                b_queen_moves[i][b_queen_count[i]][1] = j;
                                b_queen_count[i]++;
                                count1++;
                                bqueen[i][4] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_a[ax - 1][j - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //constant y-
                con = 0;
                for (int j = ax + 1; j < 9; j++) {
                    if (occupied_b[j - 1][ay - 1] == 0) {
                        if (occupied_a[j - 1][ay - 1] == 0) {
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                            bqueen[i][0] = j;
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                            if (increment1()) {
                                b_queen_moves[i][b_queen_count[i]][0] = j;
                                b_queen_moves[i][b_queen_count[i]][1] = ay;
                                b_queen_count[i]++;
                                count1++;
                                bqueen[i][4] = 1;
                            }
                            re_set();
                        }
                        if (occupied_a[j - 1][ay - 1] == 1) {
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                            bqueen[i][0] = j;
                            b_kill_a(bqueen[i][0], bqueen[i][1]);
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                            if (increment1()) {
                                b_queen_moves[i][b_queen_count[i]][0] = j;
                                b_queen_moves[i][b_queen_count[i]][1] = ay;
                                b_queen_count[i]++;
                                count1++;
                                bqueen[i][4] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_b[j - 1][ay - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                con = 0;
                for (int j = ax - 1; j > 0; j--) {
                    if (occupied_b[j - 1][ay - 1] == 0) {
                        if (occupied_a[j - 1][ay - 1] == 0) {
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                            bqueen[i][0] = j;
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                            if (increment1()) {
                                b_queen_moves[i][b_queen_count[i]][0] = j;
                                b_queen_moves[i][b_queen_count[i]][1] = ay;
                                b_queen_count[i]++;
                                count1++;
                                bqueen[i][4] = 1;
                            }
                            re_set();
                        }
                        if (occupied_a[j - 1][ay - 1] == 1) {
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                            bqueen[i][0] = j;
                            b_kill_a(bqueen[i][0], bqueen[i][1]);
                            occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                            if (increment1()) {
                                b_queen_moves[i][b_queen_count[i]][0] = j;
                                b_queen_moves[i][b_queen_count[i]][1] = ay;
                                b_queen_count[i]++;
                                count1++;
                                bqueen[i][4] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_b[j - 1][ay - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "1" -
                con = 0;
                for (int j = ax + 1; j < 9; j++) {
                    for (int k = ay + 1; k < 9; k++) {
                        if (j - k == ax - ay) {
                            if (occupied_b[j - 1][k - 1] == 0) {
                                if (occupied_a[j - 1][k - 1] == 0) {
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                                    bqueen[i][0] = j;
                                    bqueen[i][1] = k;
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_queen_moves[i][b_queen_count[i]][0] = j;
                                        b_queen_moves[i][b_queen_count[i]][1] = k;
                                        b_queen_count[i]++;
                                        count1++;
                                        bqueen[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_a[j - 1][k - 1] == 1) {
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                                    bqueen[i][0] = j;
                                    bqueen[i][1] = k;
                                    b_kill_a(bqueen[i][0], bqueen[i][1]);
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_queen_moves[i][b_queen_count[i]][0] = j;
                                        b_queen_moves[i][b_queen_count[i]][1] = k;
                                        b_queen_count[i]++;
                                        count1++;
                                        bqueen[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_b[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "2" -
                con = 0;
                for (int j = ax - 1; j > 0; j--) {
                    for (int k = ay - 1; k > 0; k--) {
                        if (j - k == ax - ay) {
                            if (occupied_b[j - 1][k - 1] == 0) {
                                if (occupied_a[j - 1][k - 1] == 0) {
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                                    bqueen[i][0] = j;
                                    bqueen[i][1] = k;
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_queen_moves[i][b_queen_count[i]][0] = j;
                                        b_queen_moves[i][b_queen_count[i]][1] = k;
                                        b_queen_count[i]++;
                                        count1++;
                                        bqueen[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_a[j - 1][k - 1] == 1) {
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                                    bqueen[i][0] = j;
                                    bqueen[i][1] = k;
                                    b_kill_a(bqueen[i][0], bqueen[i][1]);
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_queen_moves[i][b_queen_count[i]][0] = j;
                                        b_queen_moves[i][b_queen_count[i]][1] = k;
                                        b_queen_count[i]++;
                                        count1++;
                                        bqueen[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_b[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "3" -
                con = 0;
                for (int j = ax + 1; j < 9; j++) {
                    for (int k = ay - 1; k > 0; k--) {
                        if (j + k == ax + ay) {
                            if (occupied_b[j - 1][k - 1] == 0) {
                                if (occupied_a[j - 1][k - 1] == 0) {
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                                    bqueen[i][0] = j;
                                    bqueen[i][1] = k;
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_queen_moves[i][b_queen_count[i]][0] = j;
                                        b_queen_moves[i][b_queen_count[i]][1] = k;
                                        b_queen_count[i]++;
                                        count1++;
                                        bqueen[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_a[j - 1][k - 1] == 1) {
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                                    bqueen[i][0] = j;
                                    bqueen[i][1] = k;
                                    b_kill_a(bqueen[i][0], bqueen[i][1]);
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_queen_moves[i][b_queen_count[i]][0] = j;
                                        b_queen_moves[i][b_queen_count[i]][1] = k;
                                        b_queen_count[i]++;
                                        count1++;
                                        bqueen[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_b[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "4" -
                con = 0;
                for (int j = ax - 1; j > 0; j--) {
                    for (int k = ay + 1; k < 9; k++) {
                        if (j + k == ax + ay) {
                            if (occupied_b[j - 1][k - 1] == 0) {
                                if (occupied_a[j - 1][k - 1] == 0) {
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                                    bqueen[i][0] = j;
                                    bqueen[i][1] = k;
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_queen_moves[i][b_queen_count[i]][0] = j;
                                        b_queen_moves[i][b_queen_count[i]][1] = k;
                                        b_queen_count[i]++;
                                        count1++;
                                        bqueen[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_a[j - 1][k - 1] == 1) {
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 0;
                                    bqueen[i][0] = j;
                                    bqueen[i][1] = k;
                                    b_kill_a(bqueen[i][0], bqueen[i][1]);
                                    occupied_b[bqueen[i][0] - 1][bqueen[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_queen_moves[i][b_queen_count[i]][0] = j;
                                        b_queen_moves[i][b_queen_count[i]][1] = k;
                                        b_queen_count[i]++;
                                        count1++;
                                        bqueen[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_b[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
            }
        }
        //KNIGHT--
        for (int i = 0; i < bknc; i++) {
            if (bknight[i][2] == 0) {
                int ax = bknight[i][0];
                int ay = bknight[i][1];

                int[] kx = new int[4];
                int[] ky = new int[4];
                kx[0] = ax - 2;
                kx[1] = ax - 1;
                kx[2] = ax + 1;
                kx[3] = ax + 2;

                ky[0] = ay - 2;
                ky[1] = ay - 1;
                ky[2] = ay + 1;
                ky[3] = ay + 2;
                b_knight_count[i] = 0;
                set();
                if (kx[1] > 0 && kx[1] < 9 && ky[0] > 0 && ky[0] < 9) {
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 0;
                    bknight[i][0] = kx[1];
                    bknight[i][1] = ky[0];
                    b_kill_a(bknight[i][0], bknight[i][1]);
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 1;
                    if (increment1()) {
                        b_knight_moves[i][b_knight_count[i]][0] = bknight[i][0];
                        b_knight_moves[i][b_knight_count[i]][1] = bknight[i][1];
                        b_knight_count[i]++;
                        count1++;
                        bknight[i][4] = 1;
                    }
                    re_set();
                }
                if (kx[0] > 0 && kx[0] < 9 && ky[1] > 0 && ky[1] < 9) {
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 0;
                    bknight[i][0] = kx[0];
                    bknight[i][1] = ky[1];
                    b_kill_a(bknight[i][0], bknight[i][1]);
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 1;
                    if (increment1()) {
                        b_knight_moves[i][b_knight_count[i]][0] = bknight[i][0];
                        b_knight_moves[i][b_knight_count[i]][1] = bknight[i][1];
                        b_knight_count[i]++;
                        count1++;
                        bknight[i][4] = 1;
                    }
                    re_set();
                }

                if (kx[0] > 0 && kx[0] < 9 && ky[2] > 0 && ky[2] < 9) {
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 0;
                    bknight[i][0] = kx[0];
                    bknight[i][1] = ky[2];
                    b_kill_a(bknight[i][0], bknight[i][1]);
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 1;
                    if (increment1()) {
                        b_knight_moves[i][b_knight_count[i]][0] = bknight[i][0];
                        b_knight_moves[i][b_knight_count[i]][1] = bknight[i][1];
                        b_knight_count[i]++;
                        count1++;
                        bknight[i][4] = 1;
                    }
                    re_set();
                }
                if (kx[1] > 0 && kx[1] < 9 && ky[3] > 0 && ky[3] < 9) {
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 0;
                    bknight[i][0] = kx[1];
                    bknight[i][1] = ky[3];
                    b_kill_a(bknight[i][0], bknight[i][1]);
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 1;
                    if (increment1()) {
                        b_knight_moves[i][b_knight_count[i]][0] = bknight[i][0];
                        b_knight_moves[i][b_knight_count[i]][1] = bknight[i][1];
                        b_knight_count[i]++;
                        count1++;
                        bknight[i][4] = 1;
                    }
                    re_set();
                }
                if (kx[2] > 0 && kx[2] < 9 && ky[0] > 0 && ky[0] < 9) {
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 0;
                    bknight[i][0] = kx[2];
                    bknight[i][1] = ky[0];
                    b_kill_a(bknight[i][0], bknight[i][1]);
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 1;
                    if (increment1()) {
                        b_knight_moves[i][b_knight_count[i]][0] = bknight[i][0];
                        b_knight_moves[i][b_knight_count[i]][1] = bknight[i][1];
                        b_knight_count[i]++;
                        count1++;
                        bknight[i][4] = 1;
                    }
                    re_set();
                }
                if (kx[3] > 0 && kx[3] < 9 && ky[1] > 0 && ky[1] < 9) {
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 0;
                    bknight[i][0] = kx[3];
                    bknight[i][1] = ky[1];
                    b_kill_a(bknight[i][0], bknight[i][1]);
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 1;
                    if (increment1()) {
                        b_knight_moves[i][b_knight_count[i]][0] = bknight[i][0];
                        b_knight_moves[i][b_knight_count[i]][1] = bknight[i][1];
                        b_knight_count[i]++;
                        count1++;
                        bknight[i][4] = 1;
                    }
                    re_set();
                }
                if (kx[3] > 0 && kx[3] < 9 && ky[2] > 0 && ky[2] < 9) {
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 0;
                    bknight[i][0] = kx[3];
                    bknight[i][1] = ky[2];
                    b_kill_a(bknight[i][0], bknight[i][1]);
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 1;
                    if (increment1()) {
                        b_knight_moves[i][b_knight_count[i]][0] = bknight[i][0];
                        b_knight_moves[i][b_knight_count[i]][1] = bknight[i][1];
                        b_knight_count[i]++;
                        count1++;
                        bknight[i][4] = 1;
                    }
                    re_set();
                }
                if (kx[2] > 0 && kx[2] < 9 && ky[3] > 0 && ky[3] < 9) {
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 0;
                    bknight[i][0] = kx[2];
                    bknight[i][1] = ky[3];
                    b_kill_a(bknight[i][0], bknight[i][1]);
                    occupied_b[bknight[i][0] - 1][bknight[i][1] - 1] = 1;
                    if (increment1()) {
                        b_knight_moves[i][b_knight_count[i]][0] = bknight[i][0];
                        b_knight_moves[i][b_knight_count[i]][1] = bknight[i][1];
                        b_knight_count[i]++;
                        count1++;
                        bknight[i][4] = 1;
                    }
                    re_set();
                }
            }
        }
        //BISHOP
        for (int i = 0; i < bbc; i++) {
            if (bbishop[i][2] == 0) {
                set();
                int ax = bbishop[i][0];
                int ay = bbishop[i][1];
                //diagonal "1" -
                con = 0;
                b_bishop_count[i] = 0;
                for (int j = ax + 1; j < 9; j++) {
                    for (int k = ay + 1; k < 9; k++) {
                        if (j - k == ax - ay) {
                            if (occupied_b[j - 1][k - 1] == 0 && con != -1) {
                                if (occupied_a[j - 1][k - 1] == 0) {
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 0;
                                    bbishop[i][0] = j;
                                    bbishop[i][1] = k;
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_bishop_moves[i][b_bishop_count[i]][0] = j;
                                        b_bishop_moves[i][b_bishop_count[i]][1] = k;
                                        b_bishop_count[i]++;
                                        count1++;
                                        bbishop[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_a[j - 1][k - 1] == 1) {
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 0;
                                    bbishop[i][0] = j;
                                    bbishop[i][1] = k;
                                    b_kill_a(bbishop[i][0], bbishop[i][1]);
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_bishop_moves[i][b_bishop_count[i]][0] = j;
                                        b_bishop_moves[i][b_bishop_count[i]][1] = k;
                                        b_bishop_count[i]++;
                                        count1++;
                                        bbishop[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_b[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "2" -
                con = 0;
                for (int j = ax - 1; j > 0; j--) {
                    for (int k = ay - 1; k > 0; k--) {
                        if (j - k == ax - ay) {
                            if (occupied_b[j - 1][k - 1] == 0 && con != -1) {
                                if (occupied_a[j - 1][k - 1] == 0) {
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 0;
                                    bbishop[i][0] = j;
                                    bbishop[i][1] = k;
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_bishop_moves[i][b_bishop_count[i]][0] = j;
                                        b_bishop_moves[i][b_bishop_count[i]][1] = k;
                                        b_bishop_count[i]++;
                                        count1++;
                                        bbishop[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_a[j - 1][k - 1] == 1) {
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 0;
                                    bbishop[i][0] = j;
                                    bbishop[i][1] = k;
                                    b_kill_a(bbishop[i][0], bbishop[i][1]);
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_bishop_moves[i][b_bishop_count[i]][0] = j;
                                        b_bishop_moves[i][b_bishop_count[i]][1] = k;
                                        b_bishop_count[i]++;
                                        count1++;
                                        bbishop[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_b[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "3" -
                con = 0;
                for (int j = ax + 1; j < 9; j++) {
                    for (int k = ay - 1; k > 0; k--) {
                        if (j + k == ax + ay) {
                            if (occupied_b[j - 1][k - 1] == 0 && con != -1) {
                                if (occupied_a[j - 1][k - 1] == 0) {
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 0;
                                    bbishop[i][0] = j;
                                    bbishop[i][1] = k;
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_bishop_moves[i][b_bishop_count[i]][0] = j;
                                        b_bishop_moves[i][b_bishop_count[i]][1] = k;
                                        b_bishop_count[i]++;
                                        count1++;
                                        bbishop[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_a[j - 1][k - 1] == 1) {
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 0;
                                    bbishop[i][0] = j;
                                    bbishop[i][1] = k;
                                    b_kill_a(bbishop[i][0], bbishop[i][1]);
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_bishop_moves[i][b_bishop_count[i]][0] = j;
                                        b_bishop_moves[i][b_bishop_count[i]][1] = k;
                                        b_bishop_count[i]++;
                                        count1++;
                                        bbishop[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_b[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //diagonal "4" -
                con = 0;
                for (int j = ax - 1; j > 0; j--) {
                    for (int k = ay + 1; k < 9; k++) {
                        if (j + k == ax + ay) {
                            if (occupied_b[j - 1][k - 1] == 0 && con != -1) {
                                if (occupied_a[j - 1][k - 1] == 0) {
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 0;
                                    bbishop[i][0] = j;
                                    bbishop[i][1] = k;
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_bishop_moves[i][b_bishop_count[i]][0] = j;
                                        b_bishop_moves[i][b_bishop_count[i]][1] = k;
                                        b_bishop_count[i]++;
                                        count1++;
                                        bbishop[i][4] = 1;
                                    }
                                    re_set();
                                }
                                if (occupied_a[j - 1][k - 1] == 1) {
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 0;
                                    bbishop[i][0] = j;
                                    bbishop[i][1] = k;
                                    b_kill_a(bbishop[i][0], bbishop[i][1]);
                                    occupied_b[bbishop[i][0] - 1][bbishop[i][1] - 1] = 1;
                                    if (increment1()) {
                                        b_bishop_moves[i][b_bishop_count[i]][0] = j;
                                        b_bishop_moves[i][b_bishop_count[i]][1] = k;
                                        b_bishop_count[i]++;
                                        count1++;
                                        bbishop[i][4] = 1;
                                    }
                                    re_set();
                                    con = -1;
                                    break;
                                }
                            } else if (occupied_b[j - 1][k - 1] == 1) {
                                con = -1;
                                break;
                            }
                        }
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
            }
        }
        //ROOK
        for (int i = 0; i < brc; i++) {
            if (brook[i][2] == 0) {
                int ax = brook[i][0];
                int ay = brook[i][1];
                b_rook_count[i] = 0;
                set();
                //constant x-
                con = 0;
                for (int j = ay + 1; j < 9; j++) {
                    if (occupied_b[ax - 1][j - 1] == 0) {
                        if (occupied_a[ax - 1][j - 1] == 0) {
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 0;
                            brook[i][1] = j;
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 1;
                            if (increment1()) {
                                b_rook_moves[i][b_rook_count[i]][0] = ax;
                                b_rook_moves[i][b_rook_count[i]][1] = j;
                                b_rook_count[i]++;
                                count1++;
                                brook[i][5] = 1;
                            }
                            re_set();
                        }
                        if (occupied_a[ax - 1][j - 1] == 1) {
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 0;
                            brook[i][1] = j;
                            b_kill_a(brook[i][0], brook[i][1]);
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 1;
                            if (increment1()) {
                                b_rook_moves[i][b_rook_count[i]][0] = ax;
                                b_rook_moves[i][b_rook_count[i]][1] = j;
                                b_rook_count[i]++;
                                count1++;
                                brook[i][5] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    }
                    else if (occupied_b[ax - 1][j - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                con = 0;
                for (int j = ay - 1; j > 0; j--) {
                    if (occupied_b[ax - 1][j - 1] == 0) {
                        if (occupied_a[ax - 1][j - 1] == 0) {
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 0;
                            brook[i][1] = j;
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 1;
                            if (increment1()) {
                                b_rook_moves[i][b_rook_count[i]][0] = ax;
                                b_rook_moves[i][b_rook_count[i]][1] = j;
                                b_rook_count[i]++;
                                count1++;
                                brook[i][5] = 1;
                            }
                            re_set();
                        }
                        if (occupied_a[ax - 1][j - 1] == 1) {
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 0;
                            brook[i][1] = j;
                            b_kill_a(brook[i][0], brook[i][1]);
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 1;
                            if (increment1()) {
                                b_rook_moves[i][b_rook_count[i]][0] = ax;
                                b_rook_moves[i][b_rook_count[i]][1] = j;
                                b_rook_count[i]++;
                                count1++;
                                brook[i][5] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_a[ax - 1][j - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                //constant y-
                con = 0;
                for (int j = ax + 1; j < 9; j++) {
                    if (occupied_b[j - 1][ay - 1] == 0) {
                        if (occupied_a[j - 1][ay - 1] == 0) {
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 0;
                            brook[i][0] = j;
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 1;
                            if (increment1()) {
                                b_rook_moves[i][b_rook_count[i]][0] = j;
                                b_rook_moves[i][b_rook_count[i]][1] = ay;
                                b_rook_count[i]++;
                                count1++;
                                brook[i][5] = 1;
                            }
                            re_set();
                        }
                        if (occupied_a[j - 1][ay - 1] == 1) {
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 0;
                            brook[i][0] = j;
                            b_kill_a(brook[i][0], brook[i][1]);
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 1;
                            if (increment1()) {
                                b_rook_moves[i][b_rook_count[i]][0] = j;
                                b_rook_moves[i][b_rook_count[i]][1] = ay;
                                b_rook_count[i]++;
                                count1++;
                                brook[i][5] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_b[j - 1][ay - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
                con = 0;
                for (int j = ax - 1; j > 0; j--) {
                    if (occupied_b[j - 1][ay - 1] == 0) {
                        if (occupied_a[j - 1][ay - 1] == 0) {
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 0;
                            brook[i][0] = j;
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 1;
                            if (increment1()) {
                                b_rook_moves[i][b_rook_count[i]][0] = j;
                                b_rook_moves[i][b_rook_count[i]][1] = ay;
                                b_rook_count[i]++;
                                count1++;
                                brook[i][5] = 1;
                            }
                            re_set();
                        }
                        if (occupied_a[j - 1][ay - 1] == 1) {
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 0;
                            brook[i][0] = j;
                            b_kill_a(brook[i][0], brook[i][1]);
                            occupied_b[brook[i][0] - 1][brook[i][1] - 1] = 1;
                            if (increment1()) {
                                b_rook_moves[i][b_rook_count[i]][0] = j;
                                b_rook_moves[i][b_rook_count[i]][1] = ay;
                                b_rook_count[i]++;
                                count1++;
                                brook[i][5] = 1;
                            }
                            re_set();
                            con = -1;
                        }
                    } else if (occupied_b[j - 1][ay - 1] == 1) {
                        con = -1;
                    }
                    if(con==-1)
                    {
                        break;
                    }
                }
            }
        }
        //KING
        if(bking[2]==0)
        {
            b_king_count=0;
            re_set();
            int ax = bking[0];
            int ay = bking[1];
            //int qwe=0;
            if(ax>1&&occupied_b[ax-2][ay-1]==0)//here
            {
                occupied_b[bking[0]-1][bking[1]-1]=0;
                bking[0]=ax-1;
                bking[1]=ay;
                occupied_b[bking[0]-1][bking[1]-1]=1;
                if(increment1())
                {
                    b_king_moves[b_king_count][0]=bking[0];
                    b_king_moves[b_king_count][1]=bking[1];
                    b_king_count++;
                    count1++;
                    bking[5]=1;
                }
                re_set();
            }
            if(ax<8&&occupied_b[ax][ay-1]==0)
            {
                occupied_b[bking[0]-1][bking[1]-1]=0;
                bking[0]=ax+1;
                bking[1]=ay;
                occupied_b[bking[0]-1][bking[1]-1]=1;
                if(increment1())
                {
                    b_king_moves[b_king_count][0]=bking[0];
                    b_king_moves[b_king_count][1]=bking[1];
                    b_king_count++;
                    count1++;
                    bking[5]=1;
                }
                re_set();
            }
            if(ay>1&&occupied_b[ax-1][ay-2]==0)
            {
                occupied_b[bking[0]-1][bking[1]-1]=0;
                bking[0]=ax;
                bking[1]=ay-1;
                occupied_b[bking[0]-1][bking[1]-1]=1;
                if(increment1())
                {
                    b_king_moves[b_king_count][0]=bking[0];
                    b_king_moves[b_king_count][1]=bking[1];
                    b_king_count++;
                    count1++;
                    bking[5]=1;
                }
                re_set();
            }
            if(ay<8&&occupied_b[ax-1][ay]==0)
            {
                occupied_b[bking[0]-1][bking[1]-1]=0;
                bking[0]=ax;
                bking[1]=ay+1;
                occupied_b[bking[0]-1][bking[1]-1]=1;
                if(increment1())
                {
                    b_king_moves[b_king_count][0]=bking[0];
                    b_king_moves[b_king_count][1]=bking[1];
                    b_king_count++;
                    count1++;
                    bking[5]=1;
                }
                re_set();
            }
            if(ax<8&&ay<8&&occupied_b[ax][ay]==0)
            {
                occupied_b[bking[0]-1][bking[1]-1]=0;
                bking[0]=ax+1;
                bking[1]=ay+1;
                occupied_b[bking[0]-1][bking[1]-1]=1;
                if(increment1())
                {
                    b_king_moves[b_king_count][0]=bking[0];
                    b_king_moves[b_king_count][1]=bking[1];
                    b_king_count++;
                    count1++;
                    bking[5]=1;
                }
                re_set();
            }
            if(ax>1&&ay<8&&occupied_b[ax-2][ay]==0)
            {
                occupied_b[bking[0]-1][bking[1]-1]=0;
                bking[0]=ax-1;
                bking[1]=ay+1;
                occupied_b[bking[0]-1][bking[1]-1]=1;
                if(increment1())
                {
                    b_king_moves[b_king_count][0]=bking[0];
                    b_king_moves[b_king_count][1]=bking[1];
                    b_king_count++;
                    count1++;
                    bking[5]=1;
                }
                re_set();
            }
            if(ax>1&&ay>1&&occupied_b[ax-2][ay-2]==0)
            {
                occupied_b[bking[0]-1][bking[1]-1]=0;
                bking[0]=ax-1;
                bking[1]=ay-1;
                occupied_b[bking[0]-1][bking[1]-1]=1;
                if(increment1())
                {
                    b_king_moves[b_king_count][0]=bking[0];
                    b_king_moves[b_king_count][1]=bking[1];
                    b_king_count++;
                    count1++;
                    bking[5]=1;
                }
                re_set();
            }
            if(ax<8&&ay>1&&occupied_b[ax][ay-2]==0) {
                occupied_b[bking[0] - 1][bking[1] - 1] = 0;
                bking[0] = ax + 1;
                bking[1] = ay - 1;
                occupied_b[bking[0] - 1][bking[1] - 1] = 1;
                if (increment1()) {
                    b_king_moves[b_king_count][0] = bking[0];
                    b_king_moves[b_king_count][1] = bking[1];
                    b_king_count++;
                    count1++;
                    bking[5] = 1;
                }
                re_set();
            }
        }
        if(count1==0)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(switcher==1) {
            int x,y;
            Resources r = getResources();
            Bitmap bm = BitmapFactory.decodeResource(r, R.drawable.backgr);
            int width=bm.getWidth();
            int height=bm.getHeight();
            Matrix matrix=new Matrix();
            float scalewidth ;
            float scaleheight;
            if(width>0&&height>0) {
                scalewidth = w / width;
                scaleheight = h / height;
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap back = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            if(bm!=null)
            {
                bm.recycle();
                bm=null;
            }
            canvas.drawBitmap(back,0,0,null);
        }
        if(switcher==0)
        {
            Resources r = getResources();
            Bitmap bm = BitmapFactory.decodeResource(r, R.drawable.pqr);
            int width=bm.getWidth();
            int height=bm.getHeight();
            Matrix matrix=new Matrix();
            float scalewidth ;
            float scaleheight;
            if(width>0&&height>0) {
                scalewidth = w / width;
                scaleheight = h / height;
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap back = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            canvas.drawBitmap(back,0,0,null);
            bm.recycle();
            bm = BitmapFactory.decodeResource(r, R.drawable.apawn);
            width = bm.getWidth();
            height = bm.getHeight();
            matrix=new Matrix();
            if(width>0&&height>0) {
                scalewidth = wx / width;
                scaleheight = hx / height;
                matrix = new Matrix();
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap ap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            bm = BitmapFactory.decodeResource(r, R.drawable.bpawn);
            width = bm.getWidth();
            height = bm.getHeight();
            if(width>0&&height>0) {
                scalewidth = wx / width;
                scaleheight = hx / height;
                matrix = new Matrix();
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap bp = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            bm = BitmapFactory.decodeResource(r, R.drawable.aking);
            width = bm.getWidth();
            height = bm.getHeight();
            if(width>0&&height>0) {
                scalewidth = wx / width;
                scaleheight = hx / height;
                matrix = new Matrix();
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap ak = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            bm = BitmapFactory.decodeResource(r, R.drawable.bking);
            width = bm.getWidth();
            height = bm.getHeight();
            if(width>0&&height>0) {
                scalewidth = wx / width;
                scaleheight = hx / height;
                matrix = new Matrix();
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap bk = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            bm = BitmapFactory.decodeResource(r, R.drawable.aqueen);
            width = bm.getWidth();
            height = bm.getHeight();
            if(width>0&&height>0) {
                scalewidth = wx / width;
                scaleheight = hx / height;
                matrix = new Matrix();
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap aq = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            bm = BitmapFactory.decodeResource(r, R.drawable.bqueen);
            width = bm.getWidth();
            height = bm.getHeight();
            if(width>0&&height>0) {
                scalewidth = wx / width;
                scaleheight = hx / height;
                matrix = new Matrix();
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap bq = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            bm = BitmapFactory.decodeResource(r, R.drawable.aknight);
            width = bm.getWidth();
            height = bm.getHeight();
            if(width>0&&height>0) {
                scalewidth = wx / width;
                scaleheight = hx / height;
                matrix = new Matrix();
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap akn = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            bm = BitmapFactory.decodeResource(r, R.drawable.bknight);
            width = bm.getWidth();
            height = bm.getHeight();
            if(width>0&&height>0) {
                scalewidth = wx / width;
                scaleheight = hx / height;
                matrix = new Matrix();
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap bkn = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            bm = BitmapFactory.decodeResource(r, R.drawable.arook);
            width = bm.getWidth();
            height = bm.getHeight();
            if(width>0&&height>0) {
                scalewidth = wx / width;
                scaleheight = hx / height;
                matrix = new Matrix();
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap ar = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            bm = BitmapFactory.decodeResource(r, R.drawable.brook);
            width = bm.getWidth();
            height = bm.getHeight();
            if(width>0&&height>0) {
                scalewidth = wx / width;
                scaleheight = hx / height;
                matrix = new Matrix();
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap br = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            bm = BitmapFactory.decodeResource(r, R.drawable.abishop);
            width = bm.getWidth();
            height = bm.getHeight();
            height = bm.getHeight();
            if(width>0&&height>0) {
                scalewidth = wx / width;
                scaleheight = hx / height;
                matrix = new Matrix();
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap ab = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            bm = BitmapFactory.decodeResource(r, R.drawable.bbishop);
            width = bm.getWidth();
            height = bm.getHeight();
            if(width>0&&height>0) {
                scalewidth = wx / width;
                scaleheight = hx / height;
                matrix = new Matrix();
                matrix.postScale(scalewidth, scaleheight);
            }
            Bitmap bb = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            //mark
            if(m%4==2)
            {
                if (b_check(bking[0], bking[1]) == 0) {
                    if (b_checkmate() == 0) {
                        Intent intent = new Intent(c, D.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        c.startActivity(intent);
                    }
                }
            }
            if(m%4==0)
            {
                if (a_check(aking[0], aking[1]) == 0) {
                    if (b_checkmate() == 0) {
                        Intent intent = new Intent(c, D.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        c.startActivity(intent);
                    }
                }
            }
            if(m%4==2) {
                if (bking[2] == -1) {
                    Intent i = new Intent(c, D.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    c.startActivity(i);
                }
                if(b_check(bking[0],bking[1])==1)
                {
                    b_count = 0;
                    z_val = -1;
                    for (int i = 0; i < 8; i++) {
                        if (bpawn[i][2] == 0) {
                            b_pawn(i);
                            b_count += pawn_b_count[i];
                        }
                    }
                    for (int i = 0; i < 10; i++) {
                        if (bqueen[i][2] == 0) {
                            b_queen(i);
                            b_count += queen_b_count[i];
                        }
                        if (bbishop[i][2] == 0) {
                            b_bishop(i);
                            b_count += bishop_b_count[i];
                        }
                        if (brook[i][2] == 0) {
                            b_rook(i);
                            b_count += rook_b_count[i];
                        }
                        if (bknight[i][2] == 0) {
                            b_knight(i);
                            b_count += knight_b_count[i];
                        }
                    }
                    b_king();
                    b_count += king_b_count;
                    if (b_count == 0) {
                        Intent i = new Intent(c, F.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        c.startActivity(i);
                    }
                    z_val = 0;
                }
            }
            if(m%4==0) {
                if (aking[2] == -1) {
                    Intent i = new Intent(c, E.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    c.startActivity(i);
                }
                if(a_check(aking[0],aking[1])==1)
                {
                    a_count = 0;
                    z_val = -1;
                    for (int i = 0; i < 8; i++) {
                        if (apawn[i][2] == 0) {
                            a_pawn(i);
                            a_count += pawn_a_count[i];
                        }
                    }
                    for (int i = 0; i < 10; i++) {
                        if (aqueen[i][2] == 0) {
                            a_queen(i);
                            a_count += queen_a_count[i];
                        }
                        if (abishop[i][2] == 0) {
                            a_bishop(i);
                            a_count += bishop_a_count[i];
                        }
                        if (arook[i][2] == 0) {
                            a_rook(i);
                            a_count += rook_a_count[i];
                        }
                        if (aknight[i][2] == 0) {
                            a_knight(i);
                            a_count += knight_a_count[i];
                        }
                    }
                    a_king();
                    a_count += king_a_count;
                    if (a_count == 0) {
                        Intent i = new Intent(c, F.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        c.startActivity(i);
                    }
                    z_val = 0;
                }
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((i + j) % 2 == 0) {
                        p.setColor(Color.WHITE);
                        p.setStyle(Paint.Style.FILL);
                        canvas.drawRect((i + 1) * wr, (j + 1) * hr, (i + 2) * wr, (j + 2) * hr, p);
                    }if ((i + j) % 2 == 1) {
                        p.setColor(Color.BLACK);
                        canvas.drawRect((i + 1) * wr, (j + 1) * hr, (i + 2) * wr, (j + 2) * hr, p);
                    }
                }
            }
            p3.setColor(Color.parseColor("#66A09393"));
            for (int i = 0; i < 8; i++) {
                if (bpawn[i][2] == 0) {
                    canvas.drawBitmap(bp, (bpawn[i][0]) * wr, (bpawn[i][1]) * hr, null);
                    if(m%4==0||m%4==1)
                    {
                        canvas.drawRect((bpawn[i][0])*wr,(bpawn[i][1])*hr,(bpawn[i][0]+1)*wr,(bpawn[i][1]+1)*hr,p3);
                    }
                }
            }
            for (int i = 0; i < 8; i++) {
                if (apawn[i][2] == 0) {
                    canvas.drawBitmap(ap, (apawn[i][0]) * wr, (apawn[i][1]) * hr, null);
                    if(m%4==2||m%4==3)
                    {
                        canvas.drawRect((apawn[i][0])*wr,(apawn[i][1])*hr,(apawn[i][0]+1)*wr,(apawn[i][1]+1)*hr,p3);
                    }
                }
            }
            if (m != 0) {
                if (m % 4 == 3) {
                    p1.setColor(Color.parseColor("#933f51b5"));
                    p1.setStyle(Paint.Style.FILL);
                    canvas.drawRect(px * wr, py * hr, (px + 1) * wr, (py + 1) * hr, p1);
                }
                if (m % 4 == 1) {
                    p1.setColor(Color.parseColor("#933f51b5"));
                    p1.setStyle(Paint.Style.FILL);
                    canvas.drawRect(px * wr, py * hr, (px + 1) * wr, (py + 1) * hr, p1);
                }
                if (m % 4 == 1) {
                    p1.setColor(Color.parseColor("#4b06f90e"));
                    p1.setStyle(Paint.Style.FILL);
                    p2.setColor(Color.parseColor("#4be70a1d"));
                    p2.setStyle(Paint.Style.FILL);
                    con = 0;
                    int z = 0;
                    int k = 0;
                    if (is_a_queen == 1) {
                        int qs = -1;
                        int q_moves[][] = new int[32][2];
                        for (int i = 0; i < aqc; i++) {
                            if (aqueen[i][2] == 0 && aqueen[i][3] == 1) {
                                qs = i;
                            }
                        }
                        if (qs != -1) {
                            z=0;
                            a_queen(qs);
                            if (a_check(aking[0], aking[1]) == 1)
                            {
                                while (z < queen_a_count[qs]) {
                                    q_moves[z][0] = queen_a_moves[qs][z][0];
                                    q_moves[z][1] = queen_a_moves[qs][z][1];
                                    z++;
                                }
                            }
                            else {
                                while (z < a_queen_count[qs]) {
                                    q_moves[z][0] = a_queen_moves[qs][z][0];
                                    q_moves[z][1] = a_queen_moves[qs][z][1];
                                    z++;
                                }
                            }
                            while (z > -1) {
                                if ((q_moves[z][0] != 0 || q_moves[z][1] != 0) && occupied_b[q_moves[z][0] - 1][q_moves[z][1] - 1] == 0) {
                                    canvas.drawRect(q_moves[z][0] * wr, q_moves[z][1] * hr, (q_moves[z][0] + 1) * wr, (q_moves[z][1] + 1) * hr, p1);
                                } else if ((q_moves[z][0] != 0 || q_moves[z][1] != 0) && occupied_b[q_moves[z][0] - 1][q_moves[z][1] - 1] == 1) {
                                    canvas.drawRect(q_moves[z][0] * wr, q_moves[z][1] * hr, (q_moves[z][0] + 1) * wr, (q_moves[z][1] + 1) * hr, p2);
                                }
                                z--;
                            }
                        }
                    }
                    if (is_a_pawn == 1) {
                        int ps = -1;
                        int x, y;
                        int p_moves[][] = new int[6][3];
                        for (int i = 0; i < 8; i++) {
                            if (apawn[i][2] == 0 && apawn[i][3] == 1) {
                                ps = i;
                            }
                        }
                        if (ps != -1) {
                            z=0;
                            a_pawn(ps);
                            if(a_check(aking[0],aking[1])==1)
                            {
                                while (z < pawn_a_count[ps]) {
                                    p_moves[z][0] = pawn_a_moves[ps][z][0];
                                    p_moves[z][1] = pawn_a_moves[ps][z][1];
                                    p_moves[z][2] = a_pawn_moves[ps][z][2];
                                    z++;
                                }
                            }
                            else
                            {
                                while (z < a_pawn_count[ps]) {
                                    p_moves[z][0] = a_pawn_moves[ps][z][0];
                                    p_moves[z][1] = a_pawn_moves[ps][z][1];
                                    p_moves[z][2] = a_pawn_moves[ps][z][2];
                                    z++;
                                }
                            }
                            while (z > -1) {
                                if ((p_moves[z][0] != 0 && p_moves[z][1] != 0) && occupied_b[p_moves[z][0] - 1][p_moves[z][1] - 1] == 0 && p_moves[z][2] == 0) {
                                    canvas.drawRect(p_moves[z][0] * wr, p_moves[z][1] * hr, (p_moves[z][0] + 1) * wr, (p_moves[z][1] + 1) * hr, p1);
                                } else if ((p_moves[z][0] != 0 && p_moves[z][1] != 0) && (occupied_b[p_moves[z][0] - 1][p_moves[z][1] - 1] == 1)|| p_moves[z][2] == 1) {
                                    canvas.drawRect(p_moves[z][0] * wr, p_moves[z][1] * hr, (p_moves[z][0] + 1) * wr, (p_moves[z][1] + 1) * hr, p2);
                                }
                                z--;
                            }
                        }
                    }
                    if(is_a_rook==1){
                        int r_moves[][]=new int[16][2];
                        int rs=-1;
                        for(int i=0;i<arc;i++) {
                            if (arook[i][2] == 0 && arook[i][3] == 1) {
                                rs=i;
                            }
                        }
                        if(rs!=-1) {
                            z=0;
                            a_rook(rs);
                            if (a_check(aking[0], aking[1]) == 1) {
                                while (z < rook_a_count[rs]) {
                                    r_moves[z][0] = rook_a_moves[rs][z][0];
                                    r_moves[z][1] = rook_a_moves[rs][z][1];
                                    z++;
                                }
                            }
                            else {
                                while (z < a_rook_count[rs]) {
                                    r_moves[z][0] = a_rook_moves[rs][z][0];
                                    r_moves[z][1] = a_rook_moves[rs][z][1];
                                    z++;
                                }
                            }
                            while (z > -1) {
                                if ((r_moves[z][0] != 0 || r_moves[z][1] != 0) && occupied_b[r_moves[z][0] - 1][r_moves[z][1] - 1] == 0)
                                {
                                    canvas.drawRect(r_moves[z][0] * wr, r_moves[z][1] * hr, (r_moves[z][0] + 1) * wr, (r_moves[z][1] + 1) * hr, p1);
                                }
                                else if ((r_moves[z][0] != 0 || r_moves[z][1] != 0) && occupied_b[r_moves[z][0] - 1][r_moves[z][1] - 1] == 1)
                                {
                                    canvas.drawRect(r_moves[z][0] * wr, r_moves[z][1] * hr, (r_moves[z][0] + 1) * wr, (r_moves[z][1] + 1) * hr, p2);
                                }
                                z--;
                            }
                        }
                    }
                    if(is_a_knight==1)
                    {
                        int ks=-1;
                        int kn_moves[][]=new int[10][2];
                        for(int i=0;i<aknc;i++)
                        {
                            if(aknight[i][3]==1&&aknight[i][2]==0)
                            {
                                ks=i;
                            }
                        }
                        if(ks !=-1) {
                            z=0;
                            a_knight(ks);
                            if(a_check(aking[0],aking[1])==1)
                            {
                                while(z<knight_a_count[ks])
                                {
                                    kn_moves[z][0]=knight_a_moves[ks][z][0];
                                    kn_moves[z][1]=knight_a_moves[ks][z][1];
                                    z++;
                                }
                            }
                            else
                            {
                                while(z<a_knight_count[ks])
                                {
                                    kn_moves[z][0]=a_knight_moves[ks][z][0];
                                    kn_moves[z][1]=a_knight_moves[ks][z][1];
                                    z++;
                                }
                            }
                            while (z > -1) {
                                if ((kn_moves[z][0] != 0 || kn_moves[z][1] != 0) && occupied_b[kn_moves[z][0] - 1][kn_moves[z][1] - 1] == 0) {
                                    canvas.drawRect(kn_moves[z][0] * wr, kn_moves[z][1] * hr, (kn_moves[z][0] + 1) * wr, (kn_moves[z][1] + 1) * hr, p1);
                                } else if ((kn_moves[z][0] != 0 || kn_moves[z][1] != 0) && occupied_b[kn_moves[z][0] - 1][kn_moves[z][1] - 1] == 1) {
                                    canvas.drawRect(kn_moves[z][0] * wr, kn_moves[z][1] * hr, (kn_moves[z][0] + 1) * wr, (kn_moves[z][1] + 1) * hr, p2);
                                }
                                z--;
                            }
                        }
                    }
                    if(is_a_bishop==1) {
                        int bs = -1;
                        int b_moves[][] = new int[16][2];
                        for (int i = 0; i < abc; i++) {
                            if (abishop[i][3] == 1 && abishop[i][2] == 0) {
                                bs = i;
                            }
                        }
                        if (bs != -1) {
                            z=0;
                            a_bishop(bs);
                            if(a_check(aking[0], aking[1]) == 1) {
                                while (z < bishop_a_count[bs]) {
                                    b_moves[z][0] = bishop_a_moves[bs][z][0];
                                    b_moves[z][1] = bishop_a_moves[bs][z][1];
                                    z++;
                                }
                            }
                            else {
                                while (z < a_bishop_count[bs]) {
                                    b_moves[z][0] = a_bishop_moves[bs][z][0];
                                    b_moves[z][1] = a_bishop_moves[bs][z][1];
                                    z++;
                                }
                            }
                            while (z > -1) {
                                if ((b_moves[z][0] != 0 || b_moves[z][1] != 0) && occupied_b[b_moves[z][0] - 1][b_moves[z][1] - 1] == 0)
                                    canvas.drawRect(b_moves[z][0] * wr, b_moves[z][1] * hr, (b_moves[z][0] + 1) * wr, (b_moves[z][1] + 1) * hr, p1);
                                else if ((b_moves[z][0] != 0 || b_moves[z][1] != 0) && occupied_b[b_moves[z][0] - 1][b_moves[z][1] - 1] == 1)
                                    canvas.drawRect(b_moves[z][0] * wr, b_moves[z][1] * hr, (b_moves[z][0] + 1) * wr, (b_moves[z][1] + 1) * hr, p2);
                                z--;
                            }
                        }
                    }
                    if(is_a_king==1)//hii
                    {
                        z=0;
                        int king_moves[][]=new int[20][2];
                        a_king();
                        while (z<king_a_count)
                        {
                            king_moves[z][0]=king_a_moves[z][0];
                            king_moves[z][1]=king_a_moves[z][1];
                            z++;
                        }
                        while (z > -1) {
                            if ((king_moves[z][0] != 0 || king_moves[z][1] != 0)&&occupied_b[king_moves[z][0] - 1][king_moves[z][1] - 1] == 0) {
                                canvas.drawRect(king_moves[z][0] * wr, king_moves[z][1] * hr, (king_moves[z][0] + 1) * wr, (king_moves[z][1] + 1) * hr, p1);
                            }
                            else if ((king_moves[z][0] != 0 || king_moves[z][1] != 0)&&occupied_b[king_moves[z][0] - 1][king_moves[z][1] - 1] == 1) {
                                canvas.drawRect(king_moves[z][0] * wr, king_moves[z][1] * hr, (king_moves[z][0] + 1) * wr, (king_moves[z][1] + 1) * hr, p2);
                            }
                            z--;
                        }
                    }
                }
                if(b_check(bking[0],bking[1])==0)
                {
                    p1.setColor(Color.RED);
                    p2.setColor(Color.parseColor("#d881e1ed"));
                    canvas.drawText("B king in CHECK",35 ,70,p1);
                    b_checkmate();
                    canvas.drawText(count1+" ",35 ,80,p1);
                    for(int i=0;i<bbc;i++)
                    {
                        if (bbishop[i][2] == 0)
                        {
                            canvas.drawText("bb " + b_bishop_count[i] + " ", 100, 80 + 10 * i, p1);
                        }
                        if(bbishop[i][4]==1 && bbishop[i][3]!=1)
                        {
                            canvas.drawRect(bbishop[i][0] * wr, bbishop[i][1] * hr, (bbishop[i][0] + 1) * wr, (bbishop[i][1] + 1) * hr, p2);
                        }
                    }
                    canvas.drawText("bki "+b_king_count+" ",200 ,80,p1);
                    if(bking[5]==1 && bking[3]!=1)
                    {
                        canvas.drawRect(bking[0] * wr, bking[1] * hr, (bking[0] + 1) * wr, (bking[1] + 1) * hr, p2);
                    }
                    for(int i=0;i<bqc;i++) {
                        if(bqueen[i][2]==0)
                            canvas.drawText("bq " + b_queen_count[i] + " ", 300, 80+10*i, p1);
                        if(bqueen[i][4]==1 && bqueen[i][3]!=1)
                        {
                            canvas.drawRect(bqueen[i][0] * wr, bqueen[i][1] * hr, (bqueen[i][0] + 1) * wr, (bqueen[i][1] + 1) * hr, p2);
                        }
                    }

                    for(int i=0;i<8;i++) {
                        if(bpawn[i][2]==0)
                            canvas.drawText("bp " + b_pawn_count[i] + " ", 400, 80+10*i, p1);
                        if(bpawn[i][5]==1 && bpawn[i][3]!=1)
                        {
                            canvas.drawRect(bpawn[i][0] * wr, bpawn[i][1] * hr, (bpawn[i][0] + 1) * wr, (bpawn[i][1] + 1) * hr, p2);
                        }
                    }
                    for(int i=0;i<bknc;i++) {
                        if(bknight[i][2]==0)
                            canvas.drawText("bkn " + b_knight_count[i] + " ", 500, 80+10*i, p1);
                        if(bknight[i][4]==1 && bknight[i][3]!=1)
                        {
                            canvas.drawRect(bknight[i][0] * wr, bknight[i][1] * hr, (bknight[i][0] + 1) * wr, (bknight[i][1] + 1) * hr, p2);
                        }
                    }
                    for(int i=0;i<brc;i++) {
                        if(brook[i][2]==0)
                            canvas.drawText("br " + b_rook_count[i] + " ", 600, 80+10*i, p1);
                        if(brook[i][5]==1 && brook[i][3]!=1)
                        {
                            canvas.drawRect(brook[i][0] * wr, brook[i][1] * hr, (brook[i][0] + 1) * wr, (brook[i][1] + 1) * hr, p2);
                        }
                    }
                }
                if(a_check(aking[0],aking[1])==0)
                {
                    p1.setColor(Color.RED);
                    p2.setColor(Color.parseColor("#d881e1ed"));
                    canvas.drawText("A king in CHECK",35 ,70,p1);
                    a_checkmate();
                    canvas.drawText(count1+" ",35,80,p1);
                    for(int i=0;i<abc;i++) {
                        if(abishop[i][2]==0)
                            canvas.drawText("ab " + a_bishop_count[i] + " ", 100, 80+10*i, p1);
                        if(abishop[i][4]==1 && abishop[i][3]!=1)
                        {
                            canvas.drawRect(abishop[i][0] * wr, abishop[i][1] * hr, (abishop[i][0] + 1) * wr, (abishop[i][1] + 1) * hr, p2);
                        }
                    }
                    canvas.drawText("aki "+a_king_count+" ",200 ,80,p1);
                    if(aking[5]==1 && aking[3]!=1)
                    {
                        canvas.drawRect(aking[0] * wr, aking[1] * hr, (aking[0] + 1) * wr, (aking[1] + 1) * hr, p2);
                    }
                    for(int i=0;i<aqc;i++) {
                        if(aqueen[i][2]==0)
                            canvas.drawText("aq " + a_queen_count[i] + " ", 300, 80+10*i, p1);
                        if(aqueen[i][4]==1 && aqueen[i][3]!=1)
                        {
                            canvas.drawRect(aqueen[i][0] * wr, aqueen[i][1] * hr, (aqueen[i][0] + 1) * wr, (aqueen[i][1] + 1) * hr, p2);
                        }
                    }

                    for(int i=0;i<8;i++) {
                        if(apawn[i][2]==0)
                            canvas.drawText("ap " + a_pawn_count[i] + " ", 400, 80+10*i, p1);
                        if(apawn[i][5]==1 && apawn[i][3]!=1)
                        {
                            canvas.drawRect(apawn[i][0] * wr, apawn[i][1] * hr, (apawn[i][0] + 1) * wr, (apawn[i][1] + 1) * hr, p2);
                        }
                    }
                    for(int i=0;i<aknc;i++) {
                        if(aknight[i][2]==0)
                            canvas.drawText("akn " + a_knight_count[i] + " ", 500, 80+10*i, p1);
                        if(aknight[i][4]==1 && aknight[i][3]!=1)
                        {
                            canvas.drawRect(aknight[i][0] * wr, aknight[i][1] * hr, (aknight[i][0] + 1) * wr, (aknight[i][1] + 1) * hr, p2);
                        }
                    }
                    for(int i=0;i<arc;i++) {
                        if(arook[i][2]==0)
                            canvas.drawText("ar " + a_rook_count[i] + " ", 600, 80+10*i, p1);
                        if(arook[i][5]==1 && arook[i][3]!=1)
                        {
                            canvas.drawRect(arook[i][0] * wr, arook[i][1] * hr, (arook[i][0] + 1) * wr, (arook[i][1] + 1) * hr, p2);
                        }
                    }
                }
                if (m % 4 == 3) {
                    p1.setColor(Color.parseColor("#4b06f90e"));
                    p1.setStyle(Paint.Style.FILL);
                    p2.setColor(Color.parseColor("#4be70a1d"));
                    p2.setStyle(Paint.Style.FILL);
                    con = 0;
                    int z = 0;
                    int k = 0;
                    int qs = -1;
                    b_checkmate();
                    if(is_b_queen==1)
                    {
                        for (int i = 0; i < bqc; i++) {
                            if (bqueen[i][2] == 0 && bqueen[i][3] == 1) {
                                qs = i;
                            }
                        }
                        if (qs != -1) {
                            int q_moves[][] = new int[32][2];
                            b_queen(qs);
                            if(b_check(bking[0],bking[1])==1)
                            {
                                while (z < queen_b_count[qs])
                                {
                                    q_moves[z][0] = queen_b_moves[qs][z][0];
                                    q_moves[z][1] = queen_b_moves[qs][z][1];
                                    z++;
                                }
                            }
                            else
                            {
                                while (z < b_queen_count[qs])
                                {
                                    q_moves[z][0] = b_queen_moves[qs][z][0];
                                    q_moves[z][1] = b_queen_moves[qs][z][1];
                                    z++;
                                }
                            }
                            while (z > -1) {
                                if ((q_moves[z][0] != 0 || q_moves[z][1] != 0)&&occupied_a[q_moves[z][0]-1][q_moves[z][1]-1]==0) {
                                    canvas.drawRect(q_moves[z][0] * wr, q_moves[z][1] * hr, (q_moves[z][0] + 1) * wr, (q_moves[z][1] + 1) * hr, p1);
                                }else if ((q_moves[z][0] != 0 || q_moves[z][1] != 0)&&occupied_a[q_moves[z][0]-1][q_moves[z][1]-1]==1) {
                                    canvas.drawRect(q_moves[z][0] * wr, q_moves[z][1] * hr, (q_moves[z][0] + 1) * wr, (q_moves[z][1] + 1) * hr, p2);
                                }
                                z--;
                            }
                        }
                    }
                    if(is_b_pawn == 1) {
                        int ps = -1;
                        int p_moves[][] = new int[6][3];
                        for (int i = 0; i < 8; i++) {
                            if (bpawn[i][2] == 0 && bpawn[i][3] == 1) {
                                ps = i;
                            }
                        }
                        if (ps != -1) {
                            b_pawn(ps);
                            if (b_check(bking[0], bking[1]) == 1) {
                                z=0;
                                while (z < pawn_b_count[ps])
                                {
                                    p_moves[z][0] = pawn_b_moves[ps][z][0];
                                    p_moves[z][1] = pawn_b_moves[ps][z][1];
                                    p_moves[z][2] = pawn_b_moves[ps][z][2];
                                    z++;
                                }
                            }
                            else
                               {
                                while (z < b_pawn_count[ps]) {
                                    p_moves[z][0] = b_pawn_moves[ps][z][0];
                                    p_moves[z][1] = b_pawn_moves[ps][z][1];
                                    p_moves[z][2] = b_pawn_moves[ps][z][2];
                                    z++;
                                }
                            }
                            while (z > -1) {
                                if ((p_moves[z][0] != 0 && p_moves[z][1] != 0) && occupied_a[p_moves[z][0] - 1][p_moves[z][1] - 1] == 0 && p_moves[z][2] == 0) {
                                    canvas.drawRect(p_moves[z][0] * wr, p_moves[z][1] * hr, (p_moves[z][0] + 1) * wr, (p_moves[z][1] + 1) * hr, p1);
                                } else if ((p_moves[z][0] != 0 && p_moves[z][1] != 0) && (occupied_a[p_moves[z][0] - 1][p_moves[z][1] - 1] == 1|| p_moves[z][2] == 1)) {
                                    canvas.drawRect(p_moves[z][0] * wr, p_moves[z][1] * hr, (p_moves[z][0] + 1) * wr, (p_moves[z][1] + 1) * hr, p2);
                                }
                                z--;
                            }
                        }
                    }
                    if(is_b_rook==1){
                        int r_moves[][]=new int[16][2];
                        int rs=-1;
                        int x,y;
                        for(int i=0;i<brc;i++) {
                            if (brook[i][2] == 0 && brook[i][3] == 1) {
                                rs=i;
                            }
                        }
                        if(rs!=-1) {
                            if (b_check(bking[0], bking[1]) == 1) {
                                z=0;
                                b_rook(rs);
                                while (z < rook_b_count[rs])
                                {
                                    r_moves[z][0] = rook_b_moves[rs][z][0];
                                    r_moves[z][1] = rook_b_moves[rs][z][1];
                                    z++;
                                }
                            }
                            else
                            {
                                while (z < b_rook_count[rs])
                                {
                                    r_moves[z][0] = b_rook_moves[rs][z][0];
                                    r_moves[z][1] = b_rook_moves[rs][z][1];
                                    z++;
                                }
                            }
                            while (z > -1) {
                                if ((r_moves[z][0] != 0 && r_moves[z][1] != 0) && occupied_a[r_moves[z][0] - 1][r_moves[z][1] - 1] == 0) {
                                    canvas.drawRect(r_moves[z][0] * wr, r_moves[z][1] * hr, (r_moves[z][0] + 1) * wr, (r_moves[z][1] + 1) * hr, p1);
                                }
                                if ((r_moves[z][0] != 0 && r_moves[z][1] != 0) && occupied_a[r_moves[z][0] - 1][r_moves[z][1] - 1] == 1) {
                                    canvas.drawRect(r_moves[z][0] * wr, r_moves[z][1] * hr, (r_moves[z][0] + 1) * wr, (r_moves[z][1] + 1) * hr, p2);
                                }
                                z--;
                            }
                        }
                    }
                    if(is_b_knight==1) {
                        int ks = -1;
                        int kn_moves[][] = new int[10][2];
                        for (int i = 0; i < bknc; i++) {
                            if (bknight[i][3] == 1 && bknight[i][2] == 0) {
                                ks = i;
                            }
                        }
                        if (ks != -1)
                        {
                            z=0;
                            b_knight(ks);
                            if(b_check(bking[0],bking[1])==1) {
                                while (z < knight_b_count[ks])
                                {
                                    kn_moves[z][0] = knight_b_moves[ks][z][0];
                                    kn_moves[z][1] = knight_b_moves[ks][z][1];
                                    z++;
                                }
                            }
                            else
                            {
                                while (z < b_knight_count[ks])
                                {
                                    kn_moves[z][0] = b_knight_moves[ks][z][0];
                                    kn_moves[z][1] = b_knight_moves[ks][z][1];
                                    z++;
                                }
                            }
                            while (z > -1) {
                                if ((kn_moves[z][0] != 0 || kn_moves[z][1] != 0) && occupied_a[kn_moves[z][0] - 1][kn_moves[z][1] - 1] == 0) {
                                    canvas.drawRect(kn_moves[z][0] * wr, kn_moves[z][1] * hr, (kn_moves[z][0] + 1) * wr, (kn_moves[z][1] + 1) * hr, p1);
                                } else if ((kn_moves[z][0] != 0 || kn_moves[z][1] != 0) && occupied_a[kn_moves[z][0] - 1][kn_moves[z][1] - 1] == 1) {
                                    canvas.drawRect(kn_moves[z][0] * wr, kn_moves[z][1] * hr, (kn_moves[z][0] + 1) * wr, (kn_moves[z][1] + 1) * hr, p2);
                                }
                                z--;
                            }
                        }
                    }
                    if(is_b_bishop==1) {
                        int bs = -1;
                        int b_moves[][] = new int[16][2];
                        for (int i = 0; i < bbc; i++) {
                            if (bbishop[i][3] == 1 && bbishop[i][2] == 0) {
                                bs = i;
                            }
                        }
                        if (bs != -1) {
                            z=0;
                            b_bishop(bs);
                            if (b_check(bking[0], bking[1]) == 1) {
                                while (z < bishop_b_count[bs])
                                {
                                    b_moves[z][0] = bishop_b_moves[bs][z][0];
                                    b_moves[z][1] = bishop_b_moves[bs][z][1];
                                    z++;
                                }
                            }
                            else
                            {
                                while (z < b_bishop_count[bs])
                                {
                                    b_moves[z][0] = b_bishop_moves[bs][z][0];
                                    b_moves[z][1] = b_bishop_moves[bs][z][1];
                                    z++;
                                }
                            }
                            while (z > -1) {
                                if ((b_moves[z][0] != 0 || b_moves[z][1] != 0) && occupied_a[b_moves[z][0] - 1][b_moves[z][1] - 1] == 0) {
                                    canvas.drawRect(b_moves[z][0] * wr, b_moves[z][1] * hr, (b_moves[z][0] + 1) * wr, (b_moves[z][1] + 1) * hr, p1);
                                } else if ((b_moves[z][0] != 0 || b_moves[z][1] != 0) && occupied_a[b_moves[z][0] - 1][b_moves[z][1] - 1] == 1) {
                                    canvas.drawRect(b_moves[z][0] * wr, b_moves[z][1] * hr, (b_moves[z][0] + 1) * wr, (b_moves[z][1] + 1) * hr, p2);
                                }
                                z--;
                            }
                        }
                    }
                    if(is_b_king==1) {
                        z = 0;
                        b_king();
                        int king_moves[][] = new int[10][2];
                        while (z < king_b_count)
                        {
                            king_moves[z][0] = king_b_moves[z][0];
                            king_moves[z][1] = king_b_moves[z][1];
                            z++;
                        }
                        while (z > -1) {
                            if ((king_moves[z][0] != 0 || king_moves[z][1] != 0)&&occupied_a[king_moves[z][0]-1][king_moves[z][1]-1]==0) {
                                canvas.drawRect(king_moves[z][0] * wr, king_moves[z][1] * hr, (king_moves[z][0] + 1) * wr, (king_moves[z][1] + 1) * hr, p1);
                            }else if ((king_moves[z][0] != 0 || king_moves[z][1] != 0)&&occupied_a[king_moves[z][0]-1][king_moves[z][1]-1]==1) {
                                canvas.drawRect(king_moves[z][0] * wr, king_moves[z][1] * hr, (king_moves[z][0] + 1) * wr, (king_moves[z][1] + 1) * hr, p2);
                            }
                            z--;
                        }
                    }
                }
            }
            for(int i=0;i<arc;i++) {
                if (arook[i][2] == 0) {
                    canvas.drawBitmap(ar, (arook[i][0]) * wr, (arook[i][1]) * hr, null);
                    if(m%4==2||m%4==3)
                    {
                        canvas.drawRect((arook[i][0])*wr,(arook[i][1])*hr,(arook[i][0]+1)*wr,(arook[i][1]+1)*hr,p3);
                    }
                }
            }
            for(int i=0;i<brc;i++) {
                if (brook[i][2] == 0) {
                    canvas.drawBitmap(br, (brook[i][0]) * wr, (brook[i][1]) * hr, null);
                    if(m%4==0||m%4==1)
                    {
                        canvas.drawRect((brook[i][0])*wr,(brook[i][1])*hr,(brook[i][0]+1)*wr,(brook[i][1]+1)*hr,p3);
                    }
                }
            }
            for(int i=0;i<aknc;i++) {
                if (aknight[i][2] == 0) {
                    canvas.drawBitmap(akn, (aknight[i][0]) * wr, (aknight[i][1]) * hr, null);
                    if(m%4==2||m%4==3)
                    {
                        canvas.drawRect((aknight[i][0])*wr,(aknight[i][1])*hr,(aknight[i][0]+1)*wr,(aknight[i][1]+1)*hr,p3);
                    }
                }
            }
            for(int i=0;i<bknc;i++) {
                if (bknight[i][2] == 0) {
                    canvas.drawBitmap(bkn, (bknight[i][0]) * wr, (bknight[i][1]) * hr, null);
                    if(m%4==0||m%4==1)
                    {
                        canvas.drawRect((bknight[i][0])*wr,(bknight[i][1])*hr,(bknight[i][0]+1)*wr,(bknight[i][1]+1)*hr,p3);
                    }
                }
            }
            for(int i=0;i<abc;i++) {
                if (abishop[i][2] == 0) {
                    canvas.drawBitmap(ab, (abishop[i][0]) * wr, (abishop[i][1]) * hr, null);
                    if(m%4==2||m%4==3)
                    {
                        canvas.drawRect((abishop[i][0])*wr,(abishop[i][1])*hr,(abishop[i][0]+1)*wr,(abishop[i][1]+1)*hr,p3);
                    }
                }
            }
            for(int i=0;i<bbc;i++) {
                if (bbishop[i][2] == 0) {
                    canvas.drawBitmap(bb, (bbishop[i][0]) * wr, (bbishop[i][1]) * hr, null);
                    if(m%4==0||m%4==1)
                    {
                        canvas.drawRect((bbishop[i][0])*wr,(bbishop[i][1])*hr,(bbishop[i][0]+1)*wr,(bbishop[i][1]+1)*hr,p3);
                    }
                }
            }
            for(int i=0;i<aqc;i++) {
                if (aqueen[i][2] == 0) {
                    canvas.drawBitmap(aq, aqueen[i][0] * wr, aqueen[i][1] * hr, null);
                    if(m%4==2||m%4==3)
                    {
                        canvas.drawRect((aqueen[i][0])*wr,(aqueen[i][1])*hr,(aqueen[i][0]+1)*wr,(aqueen[i][1]+1)*hr,p3);
                    }
                }
            }
            for(int i=0;i<bqc;i++) {

                if (bqueen[i][2] == 0) {
                    canvas.drawBitmap(bq, bqueen[i][0] * wr, bqueen[i][1] * hr, null);
                    if(m%4==0||m%4==1)
                    {
                        canvas.drawRect((bqueen[i][0])*wr,(bqueen[i][1])*hr,(bqueen[i][0]+1)*wr,(bqueen[i][1]+1)*hr,p3);
                    }
                }
            }
            if (aking[2] == 0) {
                canvas.drawBitmap(ak, aking[0] * wr, aking[1] * hr, null);
                if(m%4==2||m%4==3)
                {
                    canvas.drawRect((aking[0])*wr,(aking[1])*hr,(aking[0]+1)*wr,(aking[1]+1)*hr,p3);
                }
            }
            if (bking[2] == 0) {
                canvas.drawBitmap(bk, bking[0] * wr, bking[1] * hr, null);
                if(m%4==0||m%4==1)
                {
                    canvas.drawRect((bking[0])*wr,(bking[1])*hr,(bking[0]+1)*wr,(bking[1]+1)*hr,p3);
                }
            }
        }
    }

}
