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
    D fg;
    DisplayMetrics x = new DisplayMetrics();
    Paint p = new Paint();
    Paint p1 = new Paint();
    Paint p2=new Paint();
    int px, py,px1,py1,k=0,con=0;//px,py-present selected block//px1,py1-previous selected block
    float h, w, wx, hx, hr, wr, a, b, m=0;//h-height pixels w-width pixels  a,b-touch coordinates on screen
    int switcher=0,aqc=1,aknc=2,arc=2,abc=2,bqc=1,bknc=2,brc=2,bbc=2;//count of pieces pawn can become queen,rook,bishop,knight
    int x_val=-1;//position of pawn that reaches other end
    int select = -1;
    int is_a_pawn,is_a_king,is_a_queen,is_a_knight,is_a_rook,is_a_bishop;
    int is_b_pawn,is_b_king,is_b_queen,is_b_knight,is_b_rook,is_b_bishop;
    int apawn[][] = new int[8][5];
    int bpawn[][] = new int[8][5];
    int occupied_a[][] = new int[8][8];
    int occupied_b[][] = new int[8][8];
    int arook[][] = new int[10][5];// 2(0,1) are original rook others(2-9)are pawns that can become rook
    int brook[][] = new int[10][5];
    int aknight[][] = new int[10][4];
    int bknight[][] = new int[10][4];
    int abishop[][] = new int[10][4];
    int bbishop[][] = new int[10][4];
    int aking[] = new int[5];
    int aqueen[][] = new int[10][4];
    int bking[] = new int[5];
    int bqueen[][] = new int[10][4];
    float pawn_promotion_x=0,pawn_promotion_y=0;
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
            bpawn[i][0] = i + 1;
            bpawn[i][1] = 2;
            bpawn[i][2] = 0;
            bpawn[i][3] = 0;
            bpawn[i][4] = 0;
            //Occupied pieces(1-occupied/0-empty)-
            //1 Player-
            occupied_a[i][6] = 1;//
            occupied_a[i][7] = 1;
            //2 Player-
            occupied_b[i][0] = 1;
            occupied_b[i][1] = 1;//
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
        //right-
        brook[1][0] = 8;
        brook[1][1] = 1;
        brook[1][2] = 0;
        brook[1][3] = 0;
        brook[1][4] = 0;//indication for castling
        //1 player rook-
        //left-
        arook[0][0] = 1;
        arook[0][1] = 8;
        arook[0][2] = 0;
        arook[0][3] = 0;
        arook[0][4] = 0;//indication for castling
        //right-
        arook[1][0] = 8;
        arook[1][1] = 8;
        arook[1][2] = 0;
        arook[1][3] = 0;
        arook[1][4] = 0;//indication for castling
        //2 player knight-
        //left-
        bknight[0][0] = 2;
        bknight[0][1] = 1;
        bknight[0][2] = 0;
        bknight[0][3] = 0;
        //right-
        bknight[1][0] = 7;
        bknight[1][1] = 1;
        bknight[1][2] = 0;
        bknight[1][3] = 0;
        //1 player knight-
        //left-
        aknight[0][0] = 2;
        aknight[0][1] = 8;
        aknight[0][2] = 0;
        aknight[0][3] = 0;
        //right-
        aknight[1][0] = 7;
        aknight[1][1] = 8;
        aknight[1][2] = 0;
        aknight[1][3] = 0;
        //2 player bishop-
        //left-
        bbishop[0][0] = 3;
        bbishop[0][1] = 1;
        bbishop[0][2] = 0;
        bbishop[0][3] = 0;
        //right-
        bbishop[1][0] = 6;
        bbishop[1][1] = 1;
        bbishop[1][2] = 0;
        bbishop[1][3] = 0;
        //1 player bishop-
        //left-
        abishop[0][0] = 3;
        abishop[0][1] = 8;
        abishop[0][2] = 0;
        abishop[0][3] = 0;
        //right-
        abishop[1][0] = 6;
        abishop[1][1] = 8;
        abishop[1][2] = 0;
        abishop[1][3] = 0;
        //2 player king-
        bking[0] = 4;
        bking[1] = 1;
        bking[2] = 0;
        bking[3] = 0;
        bking[4] = 0;//indication for castling ****0-castling possible else no
        //2 player queen-
        bqueen[0][0] = 5;
        bqueen[0][1] = 1;
        bqueen[0][2] = 0;
        bqueen[0][3] = 0;
        //1 player king-
        aking[0] = 4;
        aking[1] = 8;
        aking[2] = 0;
        aking[3] = 0;
        aking[4] = 0;//indication for castling
        //1 player queen-
        aqueen[0][0] = 5;
        aqueen[0][1] = 8;
        aqueen[0][2] = 0;
        aqueen[0][3] = 0;
        for(int i=2;i<10;i++)
        {
            arook[i][2]=-1;
            abishop[i][2]=-1;
            aknight[i][2]=-1;
            aqueen[i][2]=-1;
            brook[i][2]=-1;
            bbishop[i][2]=-1;
            bknight[i][2]=-1;
            bqueen[i][2]=-1;
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
        }
        //2 player rook-
         for(int i=0;i<brc;i++)
         {
             brook[i][3] = 0;
         }
        //1 player rook-
        //left-
        for(int i=0;i<arc;i++)
        {
            arook[i][3] = 0;
        }
        //2 player knight-
        for(int i=0;i<bknc;i++)
        {
            bknight[i][3] = 0;
        }
        //1 player knight-
        for(int i=0;i<aknc;i++)
        {
            aknight[i][3] = 0;
        }
        //2 player bishop-
        for(int i=0;i<bbc;i++)
        {
            bbishop[i][3] = 0;
        }
        //1 player bishop-
        for(int i=0;i<abc;i++)
        {
            abishop[i][3] = 0;
        }
        bking[3] = 0;
        //2 player queen-
        for(int i=0;i<bqc;i++)
        {
            bqueen[i][3] = 0;
        }
        //1 player queen-
        for(int i=0;i<aqc;i++)
        {
            aqueen[i][3] = 0;
        }
        //2 player king-
        bking[3]=0;
        //1 player king-
        aking[3] = 0;
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
                       /*
                            Toast.makeText(c,"Occupied",Toast.LENGTH_SHORT).show();
                        }
                        if(occup[px-1][py-1]==0)
                        {
                            Toast.makeText(c,"Not Occupied",Toast.LENGTH_LONG).show();
                        }*/
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
        for(int k=0;k<8;k++)
        {
            if (apawn[k][4] == 1)
                apawn[k][4] = -1;//preventing en passant capture if not used immediately
        }
        for (int k = 0; k < 8; k++) {
            if (apawn[k][0] == px && apawn[k][1] == py&&apawn[k][2]!=-1) {
                reset();
                is_a_pawn = 1;
                apawn[k][3] = 1;
            }
        }
        if (aking[0] == px && aking[1] == py&&aking[2]!=-1) {
            reset();
            is_a_king = 1;
            aking[3] = 1;
        }
        for (int k = 0; k < 10; k++) {
            if (abishop[k][0] == px && abishop[k][1] == py&&abishop[k][2]!=-1) {
                reset();
                is_a_bishop = 1;
                abishop[k][3] = 1;
            }

            if (arook[k][0] == px && arook[k][1] == py&&arook[k][2]!=-1) {
                reset();
                is_a_rook = 1;
                arook[k][3] = 1;
            }
            if (aknight[k][0] == px && aknight[k][1] == py&&aknight[k][2]!=-1) {
                reset();
                is_a_knight = 1;
                aknight[k][3] = 1;
            }
            if (aqueen[k][0] == px && aqueen[k][1] == py&&aqueen[k][2]!=-1) {
                reset();
                is_a_queen = 1;
                aqueen[k][3] = 1;
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
        for(int k=0;k<8;k++)
        {
            if (bpawn[k][4] == 1)
                bpawn[k][4] = -1;//preventing en passant capture if not used immediately
        }
        for (int k = 0; k < 8; k++) {
            if (bpawn[k][0] == px && bpawn[k][1] == py&&bpawn[k][2]!=-1) {
                reset();
                is_b_pawn = 1;
                bpawn[k][3] = 1;
            }
        }
        if (bking[0] == px && bking[1] == py&&bking[2]!=-1) {
            reset();
            is_b_king = 1;
            bking[3] = 1;
        }
        for (int k = 0; k < 10; k++) {
            if (bbishop[k][0] == px && bbishop[k][1] == py&&bbishop[k][2]!=-1) {
                reset();
                is_b_bishop = 1;
                bbishop[k][3] = 1;
            }

            if (brook[k][0] == px && brook[k][1] == py&&brook[k][2]!=-1) {
                reset();
                is_b_rook = 1;
                brook[k][3] = 1;
            }
            if (bknight[k][0] == px && bknight[k][1] == py&&bknight[k][2]!=-1) {
                reset();
                is_b_knight = 1;
                bknight[k][3] = 1;
            }
            if (bqueen[k][0] == px && bqueen[k][1] == py&&bqueen[k][2]!=-1) {
                reset();
                is_b_queen = 1;
                bqueen[k][3] = 1;
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
            if (apawn[k][4] == 0) {
                if ((py1 - py == 1  && occupied_a[px - 1][py - 1] != 1 && occupied_b[px - 1][py - 1] != 1)&& px1 == px) {
                    apawn[k][4] = -1;
                    apawn[k][0] = px;
                    apawn[k][1] = py;
                    is_a_pawn = -1;
                }
                if((py1 - py == 2&& occupied_a[px - 1][py - 1] != 1 && occupied_b[px - 1][py - 1] != 1&& occupied_a[px-1][py1-2]!=1&&occupied_b[px-1][py1-2]!=1)&& px1 == px)
                {
                    apawn[k][4] = 1;
                    apawn[k][0] = px;
                    apawn[k][1] = py;
                    is_a_pawn = -1;
                }
            } else if (py1 - py == 1 && px1 == px && occupied_a[px - 1][py - 1] != 1 && occupied_b[px - 1][py - 1] != 1) {
                apawn[k][4]=-1;
                apawn[k][0] = px;
                apawn[k][1] = py;
                is_a_pawn = -1;
            }
            //en passant capture-
            if(px-py==px1-py1&&py1-py==1&&px>0&&py>0&&occupied_b[px-1][py]==1)
            {
                for(int i=0;i<7;i++)
                {
                    if(bpawn[i][4]==1&&px==bpawn[i][0]&&py+1==bpawn[i][1])
                    {
                        bpawn[i][4]=-1;
                        apawn[k][0]=px;
                        apawn[k][1]=py;
                        bpawn[i][2]=-1;
                        occupied_b[px - 1][py ] = 0;
                        is_a_pawn=-1;
                    }
                }
            }
            if(px+py==px1+py1&&py1-py==1&&px>0&&py>0&&occupied_b[px-1][py]==1)
            {
                for(int i=0;i<7;i++)
                {
                    if(bpawn[i][4]==1&&px==bpawn[i][0]&&py+1==bpawn[i][1])
                    {
                        bpawn[i][4]=-1;
                        apawn[k][0]=px;
                        apawn[k][1]=py;
                        bpawn[i][2]=-1;
                        occupied_b[px - 1][py ] = 0;
                        is_a_pawn=-1;
                    }
                }
            }
            if ((px1 - py1 == px - py || px1 + py1 == px + py)&&(py1-py==1)&& occupied_b[px - 1][py - 1] == 1) {
                apawn[k][4] = -1;
                apawn[k][0] = px;
                apawn[k][1] = py;
                is_a_pawn = -1;
                for (int j = 0; j < 8; j++) {
                    if (bpawn[j][0] == px && bpawn[j][1] == py) {
                        bpawn[j][2] = -1;
                    }
                }
                if(bking[0]==px && bking[1]==py)
                {
                 bking[2]=-1;
                }
                for(int j=0;j<10;j++)
                {
                    if(brook[j][0]==px&&brook[j][1]==py)
                    {
                     brook[j][2]=-1;
                    }
                    else if(bbishop[j][0]==px&&bbishop[j][1]==py)
                    {
                        bbishop[j][2]=-1;
                    }
                    else if(bknight[j][0]==px&&bknight[j][1]==py)
                    {
                        bknight[j][2]=-1;
                    }
                    else if(bqueen[j][0]==px && bqueen[j][1]==py)
                    {
                        bqueen[j][2]=-1;
                    }
                }
                occupied_b[px - 1][py - 1] = 0;
            }
            //pokemon
            if(apawn[k][1]==1)
            {
                x_val=apawn[k][0];
                apawn[k][2]=-1;
                switcher=1;
                select=0;
            }
        }
        }
        public void a_bishop(int ind) {
            if (abishop[ind][2] == 0) {
                int ok = 0;
                if (px - py == px1 - py1) {
                    if (px1 < px) {
                        for (int i = px - 1; i > px1; i--) {
                            for (int j = py - 1; j > py1; j--) {
                                if (i - j == px - py) {
                                    if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                        k++;
                                    } else {
                                        k--;
                                        break;
                                    }
                                }
                            }
                        }
                        if (k + 1 == py - py1) {
                            ok = 1;
                        } else {
                            ok = -1;
                        }
                    } else {
                        for (int i = px + 1; i < px1; i++) {
                            for (int j = py + 1; j < py1; j++) {
                                if (i - j == px - py) {
                                    if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                        k++;
                                    } else {
                                        k--;
                                        break;
                                    }
                                }
                            }
                        }
                        if (k + 1 == py1 - py) {
                            ok = 1;
                        } else {
                            ok = -1;
                        }
                    }
                } else if (px + py == px1 + py1) {
                    if (px1 < px) {
                        for (int i = px - 1; i > px1; i--) {
                            for (int j = py + 1; j < py1; j++) {
                                if (i + j == px + py) {
                                    if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                        k++;
                                    } else {
                                        k--;
                                        break;
                                    }
                                }
                            }
                        }
                        if (k + 1 == px - px1) {
                            ok = 1;
                        } else {
                            ok = -1;
                        }
                    } else {
                        for (int i = px + 1; i < px1; i++) {
                            for (int j = py - 1; j > py1; j--) {
                                if (i + j == px + py) {
                                    if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                        k++;
                                    } else {
                                        k--;
                                        break;
                                    }
                                }
                            }
                        }
                        if (k + 1 == px1 - px) {
                            ok = 1;
                        } else {
                            ok = -1;
                        }
                    }
                }
                if (ok == 1) {
                    if (occupied_b[px - 1][py - 1] == 0) {
                        abishop[ind][0] = px;
                        abishop[ind][1] = py;
                        is_a_bishop = -1;
                    } else {
                        abishop[ind][0] = px;
                        abishop[ind][1] = py;
                        for (int j = 0; j < 8; j++) {
                            if (bpawn[j][0] == px && bpawn[j][1] == py) {
                                bpawn[j][2] = -1;
                            }
                        }
                        if (bking[0] == px && bking[1] == py)
                            bking[2] = -1;
                            for (int j = 0; j < 10; j++) {
                                if (brook[j][0] == px && brook[j][1] == py) {
                                    brook[j][2] = -1;
                                } else if (bbishop[j][0] == px && bbishop[j][1] == py) {
                                    bbishop[j][2] = -1;
                                } else if (bknight[j][0] == px && bknight[j][1] == py) {
                                    bknight[j][2] = -1;
                                } else if (bqueen[j][0] == px && bqueen[j][1] == py) {
                                    bqueen[j][2] = -1;
                                }
                            }
                            occupied_b[px - 1][py - 1] = 0;
                            is_a_bishop = -1;
                    }
                }
            }
        }
            public void a_rook(int ind) {
                int ok = 0;
                if (arook[ind][2] == 0) {
                    if (px1 == px) {
                        if (py1 < py) {
                            for (int i = py - 1; i > py1; i--) {
                                if (occupied_b[px - 1][i - 1] == 0 && occupied_a[px - 1][i - 1] == 0) {
                                    k++;
                                } else {
                                    k--;
                                    break;
                                }
                            }
                            if (k + 1 == py - py1) {
                                ok = 1;
                            } else {
                                ok = -1;
                            }
                        } else {
                            for (int i = py + 1; i < py1; i++) {
                                if (occupied_b[px - 1][i - 1] == 0 && occupied_a[px - 1][i - 1] == 0) {
                                    k++;
                                } else {
                                    k--;
                                    break;
                                }
                            }
                            if (k + 1 == py1 - py) {
                                ok = 1;
                            } else {
                                ok = -1;
                            }
                        }
                    } else if (py1 == py) {
                        if (px1 < px) {
                            for (int i = px - 1; i > px1; i--) {
                                if (occupied_b[i - 1][py - 1] == 0 && occupied_a[i - 1][py - 1] == 0) {
                                    k++;
                                } else {
                                    k--;
                                    break;
                                }
                            }
                            if (k + 1 == px - px1) {
                                ok = 1;
                            } else {
                                ok = -1;
                            }
                        } else {
                            for (int i = px + 1; i < px1; i++) {
                                if (occupied_b[i - 1][py - 1] == 0 && occupied_a[i - 1][py - 1] == 0) {
                                    k++;
                                } else {
                                    k--;
                                    break;
                                }
                            }
                            if (k + 1 == px1 - px) {
                                ok = 1;
                            } else {
                                ok = -1;
                            }
                        }
                    }
                    if (ok == 1) {
                        if (occupied_b[px - 1][py - 1] == 0) {
                            arook[ind][0] = px;
                            arook[ind][1] = py;
                            arook[ind][4] = -1;
                            is_a_rook = -1;
                        } else {
                            arook[ind][4] = -1;
                            arook[ind][0] = px;
                            arook[ind][1] = py;
                            for (int j = 0; j < 8; j++) {
                                if (bpawn[j][0] == px && bpawn[j][1] == py) {
                                    bpawn[j][2] = -1;
                                }
                            }
                            if (bking[0] == px && bking[1] == py)
                                bking[2] = -1;
                            for (int j = 0; j < 10; j++) {
                                if (brook[j][0] == px && brook[j][1] == py) {
                                    brook[j][2] = -1;
                                } else if (bbishop[j][0] == px && bbishop[j][1] == py) {
                                    bbishop[j][2] = -1;
                                } else if (bknight[j][0] == px && bknight[j][1] == py) {
                                    bknight[j][2] = -1;
                                } else if (bqueen[j][0] == px && bqueen[j][1] == py) {
                                    bqueen[j][2] = -1;
                                }
                            }
                                occupied_b[px - 1][py - 1] = 0;
                                is_a_rook = -1;

                        }
                    }
                }
            }
            public void a_knight(int k) {
                if (aknight[k][2] == 0)
                {
                    int ok = 0;
                    int[] kx = new int[4];
                    int[] ky = new int[4];
                    kx[0] = px1 - 1;
                    kx[1] = px1 + 1;
                    kx[2] = px1 - 2;
                    kx[3] = px1 + 2;

                    ky[0] = py1 - 2;
                    ky[1] = py1 - 1;
                    ky[2] = py1 + 1;
                    ky[3] = py1 + 2;

                    if (px == kx[0] && py == ky[0] || px == kx[1] && py == ky[0] || px == kx[2] && py == ky[1] || px == kx[2] && py == ky[2]  || px == kx[0] && py == ky[3] || px == kx[1] && py == ky[3] || px == kx[3] && py == ky[1] || px == kx[3] && py == ky[2])  {
                        ok = 1;
                    }
                    if (ok == 1) {
                        if(occupied_a[px - 1][py - 1] != 1 && occupied_b[px - 1][py - 1] != 1)
                        {
                            aknight[k][0] = px;
                            aknight[k][1] = py;
                            is_a_knight=-1;
                        }
                        else if (occupied_b[px - 1][py - 1] == 1) {
                            aknight[k][0] = px;
                            aknight[k][1] = py;
                            for (int j = 0; j < 8; j++) {
                                if (bpawn[j][0] == px && bpawn[j][1] == py) {
                                    bpawn[j][2] = -1;
                                }
                            }
                            if (bking[0] == px && bking[1] == py) {
                                bking[2] = -1;
                            }
                            for (int j = 0; j < 10; j++) {
                                if (brook[j][0] == px && brook[j][1] == py) {
                                    brook[j][2] = -1;
                                } else if (bbishop[j][0] == px && bbishop[j][1] == py) {
                                    bbishop[j][2] = -1;
                                } else if (bknight[j][0] == px && bknight[j][1] == py) {
                                    bknight[j][2] = -1;
                                } else if (bqueen[j][0] == px && bqueen[j][1] == py) {
                                    bqueen[j][2] = -1;
                                }
                            }
                            occupied_b[px - 1][py - 1] = 0;
                            is_a_knight = -1;
                        }
                    }
                }
            }
        public void a_king()
        {
            int castle=0;
            int z=0;
            int ok=0;
         if(aking[2]==0)
         {
             if(py1==py)
             {
                 if((px1-px==1||px-px1==1)&&occupied_a[px-1][py-1]==0)
                 {
                     z=a_check(px,py);
                     if(occupied_b[px-1][py-1]==1)
                     {
                         ok=1;
                     }
                 }
             }
             else if(px1==px)
             {
                 if((py1-py==1||py-py1==1)&&occupied_a[px-1][py-1]==0)
                 {
                     z=a_check(px,py);
                     if(occupied_b[px-1][py-1]==1)
                     {
                         ok=1;
                     }
                 }
             }
             else if(px-py==px1-py1)
             {
                 if((px1-px==1||px-px1==1)&&occupied_a[px-1][py-1]==0)
                 {
                     z=a_check(px,py);
                     if(occupied_b[px-1][py-1]==1)
                     {
                         ok=1;
                     }
                 }
             }
             else if(px+py==px1+py1)
             {
                 if((px1-px==1||px-px1==1)&&occupied_a[px-1][py-1]==0)
                 {
                     z=a_check(px,py);
                     if(occupied_b[px-1][py-1]==1)
                     {
                         ok=1;
                     }
                 }
             }
             if(aking[4]==0)
             {
             if(px-px1==2&&py==py1&&arook[1][4]==0)
             {
                 castle=a_castling(px,py);
                 if(castle==1)
                 {
                     occupied_a[aking[0]-1][aking[1]-1]=0;
                     occupied_a[arook[1][0]-1][arook[1][1]-1]=0;
                     aking[0]=px;
                     aking[4]=-1;
                     arook[1][0]=px-1;
                     is_a_king=-1;
                     occupied_a[px-1][py-1]=1;
                     occupied_a[px-2][py-1]=1;
                 }
             }
             else if(px1-px==2&&py==py1&&arook[0][4]==0)
             {
                 castle=a_castling(px,py);
                 if(castle==1)
                 {
                     occupied_a[aking[0]-1][aking[1]-1]=0;
                     occupied_a[arook[0][0]-1][arook[0][1]-1]=0;
                     aking[0]=px;
                     aking[4]=-1;
                     arook[0][0]=px+1;
                     is_a_king=-1;
                     occupied_a[px-1][py-1]=1;
                     occupied_a[px][py-1]=1;
                 }
             }
             }
             if(z==1)
             {
                 aking[4]=-1;
                 aking[0]=px;
                 aking[1]=py;
                 if(ok==1)
                 {
                     for (int j = 0; j < 8; j++) {
                         if (bpawn[j][0] == px && bpawn[j][1] == py) {
                             bpawn[j][2] = -1;
                         }
                     }
                     if (bking[0] == px && bking[1] == py) {
                         bking[2] = -1;
                     }
                     for (int j = 0; j < 10; j++) {
                         if (brook[j][0] == px && brook[j][1] == py) {
                             brook[j][2] = -1;
                         } else if (bbishop[j][0] == px && bbishop[j][1] == py) {
                             bbishop[j][2] = -1;
                         } else if (bknight[j][0] == px && bknight[j][1] == py) {
                             bknight[j][2] = -1;
                         } else if (bqueen[j][0] == px && bqueen[j][1] == py) {
                             bqueen[j][2] = -1;
                         }
                     }
                     occupied_b[px - 1][py - 1] = 0;
                 }
                 is_a_king = -1;
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
        public int a_castling(int x,int y)
        {
         int akx=aking[0];
         int aky=aking[1];
         int bx[][]=new int[2][2];
         int ok=0;
         for(int i=0;i<2;i++)
         {
             for(int j=0;j<2;j++)
             {
                 bx[i][j]=arook[i][j];
             }
         }
         if(y==aky)
         {
             if(akx-x==2)
             {
                 for(int i=x+1;i<akx;i++)
                 {
                     if(occupied_a[i-1][y-1]==0&&occupied_b[i-1][y-1]==0&&a_check(i,y)==1)
                     {
                         ok=1;
                     }
                     else
                     {
                         ok=-1;
                         break;
                     }
                 }
                 if(ok==1)
                 {
                     return 1;
                 }
                 else
                 {
                     return 0;
                 }
             }
             else if(x-akx==2)
             {
                 for(int i=akx+1;i<x;i++)
                 {
                     if(occupied_a[i-1][y-1]==0&&occupied_b[i-1][y-1]==0&&a_check(i,y)==1)
                     {
                         ok=1;
                     }
                     else
                     {
                         ok=-1;
                         break;
                     }
                 }
                 if(ok==1)
                 {
                     return 1;
                 }
                 else
                 {
                     return 0;
                 }
             }
         }
            return 0;
        }
        public int a_check(int kix,int kiy)//To check if King is moving into check
        {
            con = 0;
            int z = 0;
            k = 0;
            int king_x = aking[0];
            int king_y = aking[1];
            int count1, count2;
            int b_moves[][] = new int[150][2];
            //queen
            for(int wq=0;wq<bqc;wq++)
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
                        k++;
                    } else if (occupied_a[i - 1][y - 1] == 1 && i == king_x && y == king_y) {
                        for (int j = king_x + 1; j < 9; j++) {
                            b_moves[z][0] = j;
                            b_moves[z][1] = y;
                            z++;
                        }
                    } else
                        break;
                }
                if (x > 0 && y > 0 && occupied_a[x + k - 1][y - 1] == 1) {
                    if (aking[0] == x + k || aking[1] == y) {
                        for (int i = x + k + 1; i < 9; i++) {
                            if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                                b_moves[z][0] = i;
                                b_moves[z][1] = y;
                                z++;
                                k++;
                            } else
                                break;
                        }
                    } else {
                        b_moves[z][0] = x + k;
                        b_moves[z][1] = y;
                        z++;
                    }
                }

                k = 0;
                for (int i = x - 1; i > 0; i--) {

                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        b_moves[z][0] = i;
                        b_moves[z][1] = y;
                        z++;
                        k++;
                    } else if (occupied_a[i - 1][y - 1] == 1 && i == king_x && y == king_y) {
                        for (int j = king_x - 1; j > 0; j--) {
                            b_moves[z][0] = j;
                            b_moves[z][1] = y;
                            z++;
                        }
                    } else
                        break;
                }
                if (x > k + 1 && y > 0 && occupied_a[x - k - 2][y - 1] == 1) {
                    b_moves[z][0] = x - k - 1;
                    b_moves[z][1] = y;
                    z++;
                }
                k = 0;
                //left diagonal "1"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i - j == x - y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                b_moves[z][0] = i;
                                b_moves[z][1] = j;
                                z++;
                                k++;
                            } else if (occupied_a[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                                for (int k1 = king_x + 1; k1 < 9; k1++) {
                                    for (int k2 = king_y + 1; k2 < 9; k2++) {
                                        if (k1 - k2 == king_x - king_y && occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                            b_moves[z][0] = k1;
                                            b_moves[z][1] = k2;
                                            z++;
                                        }
                                    }
                                }
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
                    b_moves[z][0] = x + k + 1;
                    b_moves[z][1] = y + k + 1;
                    z++;
                }
                k = 0;
                con = 0;
                //left diagonal "2"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i - j == x - y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                b_moves[z][0] = i;
                                b_moves[z][1] = j;
                                z++;
                                k++;
                            } else if (occupied_a[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                                for (int k1 = king_x - 1; k1 > 0; k1--) {
                                    for (int k2 = king_y - 1; k2 > 0; k2--) {
                                        if (k1 - k2 == king_x - king_y && occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                            b_moves[z][0] = k1;
                                            b_moves[z][1] = k2;
                                            z++;
                                        }
                                    }
                                }
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
                    b_moves[z][0] = x - k - 1;
                    b_moves[z][1] = y - k - 1;
                    z++;
                }
                k = 0;
                con = 0;
                //Right diagonal "1"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i + j == x + y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                b_moves[z][0] = i;
                                b_moves[z][1] = j;
                                z++;
                                k++;
                            } else if (occupied_a[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                                for (int k1 = king_x + 1; k1 < 9; k1++) {
                                    for (int k2 = king_y - 1; k2 > 0; k2--) {
                                        if (k1 + k2 == king_x + king_y && occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                            b_moves[z][0] = k1;
                                            b_moves[z][1] = k2;
                                            z++;
                                        }
                                    }
                                }
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
                    b_moves[z][0] = x + k + 1;
                    b_moves[z][1] = y - k - 1;
                    z++;
                }
                k = 0;
                con = 0;
                //Right diagonal "2"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i + j == x + y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                b_moves[z][0] = i;
                                b_moves[z][1] = j;
                                z++;
                                k++;
                            } else if (occupied_a[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                                for (int k1 = king_x - 1; k1 > 0; k1--) {
                                    for (int k2 = king_y + 1; k2 < 9; k2++) {
                                        if (k1 + k2 == king_x + king_y && occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                            b_moves[z][0] = k1;
                                            b_moves[z][1] = k2;
                                            z++;
                                        }
                                    }
                                }
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
                    b_moves[z][0] = x - k - 1;
                    b_moves[z][1] = y + k + 1;
                    z++;
                }
                k = 0;
                con = 0;
                //constant x-
                for (int i = y + 1; i < 9; i++) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        b_moves[z][0] = x;
                        b_moves[z][1] = i;
                        z++;
                        k++;
                    } else if (occupied_a[x - 1][i - 1] == 1 && x == king_x && i == king_y) {
                        for (int j = king_y + 1; j < 9; j++) {
                            b_moves[z][0] = x;
                            b_moves[z][1] = j;
                            z++;
                        }
                    } else
                        break;
                }
                if (y + k < 8 && x > 0 && occupied_a[x - 1][y + k] == 1) {
                    b_moves[z][0] = x;
                    b_moves[z][1] = y + k + 1;
                    z++;
                }
                k = 0;
                for (int i = y - 1; i > 0; i--) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        b_moves[z][0] = x;
                        b_moves[z][1] = i;
                        z++;
                        k++;
                    } else if (occupied_a[x - 1][i - 1] == 1 && x == king_x && i == king_y) {
                        for (int j = king_y - 1; j > 0; j--) {
                            b_moves[z][0] = x;
                            b_moves[z][1] = j;
                            z++;
                        }
                    } else
                        break;
                }
                if (y > k + 1 && x > 0 && occupied_a[x - 1][y - k - 2] == 1) {
                    b_moves[z][0] = x;
                    b_moves[z][1] = y - k - 1;
                    z++;
                }
                k = 0;
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
                for (int j = 0; j < brc; j++) {
                    if (brook[j][2] == 0) {
                        int x = brook[j][0];
                        int y = brook[j][1];
                        for (int i = x - 1; i > 0; i--) {
                            if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                                b_moves[z][0] = i;
                                b_moves[z][1] = y;
                                z++;
                                k++;
                            }
                            else if(occupied_a[i-1][y-1]==1 && i==king_x&&y==king_y )
                            {
                                for(int k=king_x-1;k>0;k--)
                                {
                                    b_moves[z][0] = k;
                                    b_moves[z][1] = y;
                                    z++;
                                }
                            } else
                                break;
                        }
                        if (x >= k + 2 && y >= 1 && occupied_a[x - k - 2][y - 1] == 1) {
                            b_moves[z][0] = x - k - 1;
                            b_moves[z][1] = y;
                            z++;
                        }
                        k = 0;
                        for (int i = y - 1; i > 0; i--) {
                            if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                                b_moves[z][0] = x;
                                b_moves[z][1] = i;
                                z++;
                                k++;
                            }
                            else if(occupied_a[x-1][i-1]==1 && x==king_x&&i==king_y )
                            {
                                for(int k=king_y-1;k>0;k--)
                                {
                                    b_moves[z][0] = x;
                                    b_moves[z][1] = k;
                                    z++;
                                }
                            } else
                                break;
                        }
                        if (x >= 1 && y >= k + 2 && occupied_a[x - 1][y - k - 2] == 1) {
                            b_moves[z][0] = x;
                            b_moves[z][1] = y - k - 1;
                            z++;
                        }
                        k = 0;
                        for (int i = x + 1; i < 9; i++) {
                            if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                                b_moves[z][0] = i;
                                b_moves[z][1] = y;
                                z++;
                                k++;
                            }
                            else if(occupied_a[i-1][y-1]==1 && i==king_x&&y==king_y )
                            {
                                for(int k=king_x+1;k<9;k++)
                                {
                                    b_moves[z][0] = k;
                                    b_moves[z][1] = y;
                                    z++;
                                }
                            } else
                                break;
                        }
                        if (y >= 1 && x + k < 8 && occupied_a[x + k][y - 1] == 1) {
                            b_moves[z][0] = x + k + 1;
                            b_moves[z][1] = y;
                            z++;
                        }
                        k = 0;
                        for (int i = y + 1; i < 9; i++) {
                            if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                                b_moves[z][0] = x;
                                b_moves[z][1] = i;
                                z++;
                                k++;
                            }
                            else if(occupied_a[x-1][i-1]==1 && x==king_x&&i==king_y )
                            {
                                for(int k=king_y+1;k<9;k++)
                                {
                                    b_moves[z][0] = x;
                                    b_moves[z][1] = k;
                                    z++;
                                }
                            } else
                                break;
                        }
                        if (x >= 1 && y + k < 8 && occupied_a[x - 1][y + k] == 1) {
                            b_moves[z][0] = x;
                            b_moves[z][1] = y + k + 1;
                            z++;
                        }
                        k = 0;
                    }
                }
            //kinght//nothing altered
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
                        //left diagonal "1"-
                        for (int i = x + 1; i < 9; i++) {
                            for (int j = y + 1; j < 9; j++) {
                                if (i - j == x - y) {
                                    if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                        b_moves[z][0] = i;
                                        b_moves[z][1] = j;
                                        z++;
                                        k++;
                                    }
                                    else if (occupied_a[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                                        for(int k1=king_x+1;k1<9;k1++) {
                                            for(int k2=king_y+1;k2<9;k2++) {
                                                if(k1-k2 == king_x-king_y&& occupied_a[k1-1][k2-1] == 0&& occupied_b[k1-1][k2-1] == 0) {
                                                    b_moves[z][0] = k1;
                                                    b_moves[z][1] = k2;
                                                    z++;
                                                }
                                            }
                                        }
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
                        if (x + k < 8 && y + k < 8 && occupied_a[x + k][y + k] == 1) {
                            b_moves[z][0] = x + k + 1;
                            b_moves[z][1] = y + k + 1;
                            z++;
                        }
                        k = 0;
                        con = 0;
                        //left diagonal "2"-
                        for (int i = x - 1; i > 0; i--) {
                            for (int j = y - 1; j > 0; j--) {
                                if (i - j == x - y) {
                                    if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                        b_moves[z][0] = i;
                                        b_moves[z][1] = j;
                                        z++;
                                        k++;
                                    }
                                    else if (occupied_a[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                                        for(int k1=king_x-1;k1>0;k1--) {
                                            for(int k2=king_y-1;k2>0;k2--) {
                                                if(k1-k2 == king_x-king_y&& occupied_a[k1-1][k2-1] == 0&& occupied_b[k1-1][k2-1] == 0) {
                                                    b_moves[z][0] = k1;
                                                    b_moves[z][1] = k2;
                                                    z++;
                                                }
                                            }
                                        }
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
                        if (x >= k + 2 && y >= k + 2 && occupied_a[x - k - 2][y - k - 2] == 1) {
                            b_moves[z][0] = x - k - 1;
                            b_moves[z][1] = y - k - 1;
                            z++;
                        }
                        k = 0;
                        con = 0;
                        //Right diagonal "1"-
                        for (int i = x + 1; i < 9; i++) {
                            for (int j = y - 1; j > 0; j--) {
                                if (i + j == x + y) {
                                    if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                        b_moves[z][0] = i;
                                        b_moves[z][1] = j;
                                        z++;
                                        k++;
                                    }
                                    else if (occupied_a[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                                        for(int k1=king_x+1;k1<9;k1++) {
                                            for(int k2=king_y-1;k2>0;k2--) {
                                                if(k1+k2 == king_x+king_y&& occupied_a[k1-1][k2-1] == 0&& occupied_b[k1-1][k2-1] == 0) {
                                                    b_moves[z][0] = k1;
                                                    b_moves[z][1] = k2;
                                                    z++;
                                                }
                                            }
                                        }
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
                        if (y >= k + 2 && x + k < 8 && occupied_a[x + k][y - k - 2] == 1) {
                            b_moves[z][0] = x + k + 1;
                            b_moves[z][1] = y - k - 1;
                            z++;
                        }
                        k = 0;
                        con = 0;
                        //Right diagonal "2"-
                        for (int i = x - 1; i > 0; i--) {
                            for (int j = y + 1; j < 9; j++) {
                                if (i + j == x + y) {
                                    if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                        b_moves[z][0] = i;
                                        b_moves[z][1] = j;
                                        z++;
                                        k++;
                                    }
                                    else if (occupied_a[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                                        for(int k1=king_x-1;k1>0;k1--) {
                                            for(int k2=king_y+1;k2<9;k2++) {
                                                if(k1+k2 == king_x+king_y&& occupied_a[k1-1][k2-1] == 0&& occupied_b[k1-1][k2-1] == 0) {
                                                    b_moves[z][0] = k1;
                                                    b_moves[z][1] = k2;
                                                    z++;
                                                }
                                            }
                                        }
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
                        if (x >= k + 2 && y + k < 8 && occupied_a[x - k - 2][y + k] == 1) {
                            b_moves[z][0] = x - k - 1;
                            b_moves[z][1] = y + k + 1;
                            z++;
                        }
                        k = 0;
                        con = 0;
                    }
                }
                //king
            if(bking[2]==0)
            {
                int x=bking[0];
                int y=bking[1];
                //constant x-
                if(x>0&&y>1&&occupied_a[x-1][y-2]==0)
                {
                    b_moves[z][0]=x;
                    b_moves[z][1]=y-1;
                    z++;
                }
                if(x>0&&y<8&&occupied_a[x-1][y]==0)
                {
                    b_moves[z][0]=x;
                    b_moves[z][1]=y+1;
                    z++;
                }
                //constant y-
                if(x>1&&y>0&&occupied_a[x-2][y-1]==0)
                {
                    b_moves[z][0]=x-1;
                    b_moves[z][1]=y;
                    z++;
                }
                if(y>0&&x<8&&occupied_a[x][y-1]==0)
                {
                    b_moves[z][0]=x+1;
                    b_moves[z][1]=y;
                    z++;
                }
                //
                if(x<8&&y<8&&occupied_a[x][y]==0)
                {
                    b_moves[z][0]=x+1;
                    b_moves[z][1]=y+1;
                    z++;
                }
                if(x>1&&y>1&&occupied_a[x-2][y-2]==0)
                {
                    b_moves[z][0]=x-1;
                    b_moves[z][1]=y-1;
                    z++;
                }
                //
                if(x<8&&y>1&&occupied_a[x][y-2]==0)
                {
                    b_moves[z][0]=x+1;
                    b_moves[z][1]=y-1;
                    z++;
                }
                if(x>1&&y<8&&occupied_a[x-2][y]==0)
                {
                    b_moves[z][0]=x-1;
                    b_moves[z][1]=y+1;
                    z++;
                }
            }
            count1=z;
            count2=0;
            while(z>0)
            {
                if(kix==b_moves[z-1][0]&&kiy==b_moves[z-1][1])
                {
                    break;
                }
                else
                {
                    count2++;
                    z--;
                }
            }
            if(count2==count1)
            {
                return 1;
            }
            else
                {
                return 0;
                }
        }
        //QUEEN
        public void a_queen(int wq) {
            int ok = 0;
            int k = 0;
            if (aqueen[wq][2] == 0) {
                if (px1 == px) {
                    if (py < py1) {
                        for (int i = py + 1; i < py1; i++) {
                            if (occupied_a[px - 1][i - 1] == 0 && occupied_b[px - 1][i - 1] == 0) {
                                k++;
                            }
                        }
                        if (k + 1 == py1 - py) {
                            ok = 1;
                        }
                    } else {
                        for (int i = py1 + 1; i < py; i++) {

                            if (occupied_a[px - 1][i - 1] == 0 && occupied_b[px - 1][i - 1] == 0) {
                                k++;
                            }
                        }
                        if (k + 1 == py - py1) {
                            ok = 1;
                        }
                    }
                } else if (py1 == py) {
                    if (px1 < px) {
                        for (int i = px1 + 1; i < px; i++) {
                            if (occupied_a[i - 1][py - 1] == 0 && occupied_b[i - 1][py - 1] == 0) {
                                k++;
                            }
                        }

                        if (k + 1 == px - px1) {
                            ok = 1;
                        }
                    } else {
                        for (int i = px + 1; i < px1; i++) {
                            if (occupied_a[i - 1][py - 1] == 0 && occupied_b[i - 1][py - 1] == 0) {
                                k++;
                            }
                        }

                        if (k + 1 == px1 - px) {
                            ok = 1;
                        }
                    }
                } else if (px - py == px1 - py1) {
                    if (px > px1) {
                        for (int i = px1 + 1; i < px; i++) {
                            for (int j = py1 + 1; j < py; j++) {
                                if (i - j == px - py && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                    k++;
                                }
                            }
                        }
                        if (k + 1 == px - px1) {
                            ok = 1;
                        }
                    } else {
                        for (int i = px + 1; i < px1; i++) {
                            for (int j = py + 1; j < py1; j++) {
                                if (i - j == px - py && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                    k++;
                                }
                            }
                        }
                        if (k + 1 == px1 - px) {
                            ok = 1;
                        }
                    }
                } else if (px + py == px1 + py1) {
                    if (px > px1) {
                        for (int i = px1 + 1; i < px; i++) {
                            for (int j = py + 1; j < py1; j++) {
                                if (i + j == px + py && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                    k++;
                                }
                            }
                        }
                        if (k + 1 == px - px1) {
                            ok = 1;
                        }
                    } else {
                        for (int i = px + 1; i < px1; i++) {
                            for (int j = py1 + 1; j < py; j++) {
                                if (i + j == px + py && occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                    k++;
                                }
                            }
                        }
                        if (k + 1 == px1 - px) {
                            ok = 1;
                        }
                    }
                }
                if (ok == 1) {
                    if (occupied_a[px - 1][py - 1] == 0 && occupied_b[px - 1][py - 1] == 0) {
                        aqueen[wq][0] = px;
                        aqueen[wq][1] = py;
                        is_a_queen = -1;
                    } else if (occupied_a[px - 1][py - 1] == 0 && occupied_b[px - 1][py - 1] != 0) {
                        for (int i = 0; i < 8; i++) {
                            if (bpawn[i][0] == px && bpawn[i][1] == py) {
                                bpawn[i][2] = -1;
                            }
                        }
                        if (bking[0] == px && bking[1] == py) {
                            bking[2] = -1;
                        }
                        for (int i = 0; i < 10; i++) {
                            if (bknight[i][0] == px && bknight[i][1] == py) {
                                bknight[i][2] = -1;
                            }
                            if (bbishop[i][0] == px && bbishop[i][1] == py) {
                                bbishop[i][2] = -1;
                            }
                            if (brook[i][0] == px && brook[i][1] == py) {
                                brook[i][2] = -1;
                            }
                            if (bqueen[i][0] == px && bqueen[i][1] == py) {
                                bqueen[i][2] = -1;
                            }
                        }
                        aqueen[wq][0] = px;
                        aqueen[wq][1] = py;
                        is_a_queen = -1;
                    }
                }
            }
        }
            //here
    public void b_pawn(int k) {
        if(bpawn[k][2]==0) {
            if (bpawn[k][4] == 0) {
                if ((py - py1 == 1  && occupied_a[px - 1][py - 1] != 1 && occupied_b[px - 1][py - 1] != 1) && px1 == px) {
                    bpawn[k][4] = -1;
                    bpawn[k][0] = px;
                    bpawn[k][1] = py;
                    is_b_pawn = -1;
                }
                if((py - py1 == 2&& occupied_a[px - 1][py - 1] != 1 && occupied_b[px - 1][py - 1] != 1&& occupied_a[px-1][py1]!=1&&occupied_b[px-1][py1]!=1) && px1 == px){
                    bpawn[k][4] = 1;
                    bpawn[k][0] = px;
                    bpawn[k][1] = py;
                    is_b_pawn = -1;
                }
            } else if (py - py1 == 1 && px1 == px && occupied_a[px - 1][py - 1] != 1 && occupied_b[px - 1][py - 1] != 1) {
                bpawn[k][0] = px;
                bpawn[k][1] = py;
                is_b_pawn = -1;
            }
            //en passant capture-
            if(px-py==px1-py1&&py-py1==1&&px>0&&py>0&&occupied_a[px-1][py-2]==1)
            {
                for(int i=0;i<7;i++)
                {
                    if(apawn[i][4]==1&&px==apawn[i][0]&&py-1==apawn[i][1])
                    {
                        apawn[i][4]=-1;
                        bpawn[k][0]=px;
                        bpawn[k][1]=py;
                        apawn[i][2]=-1;
                        occupied_a[px - 1][py-2] = 0;
                        is_b_pawn=-1;
                    }
                }
            }
            if(px+py==px1+py1&&py-py1==1&&px>0&&py>0&&occupied_a[px-1][py-2]==1)
            {
                for(int i=0;i<7;i++)
                {
                    if(apawn[i][4]==1&&px==apawn[i][0]&&py-1==apawn[i][1])
                    {
                        apawn[i][4]=-1;
                        bpawn[k][0]=px;
                        bpawn[k][1]=py;
                        apawn[i][2]=-1;
                        occupied_a[px - 1][py-2] = 0;
                        is_b_pawn=-1;
                    }
                }
            }
            if ((px1 - py1 == px - py || px1 + py1 == px + py)&&(py-py1==1)&& occupied_a[px - 1][py - 1] == 1) {
                bpawn[k][4] = -1;
                bpawn[k][0] = px;
                bpawn[k][1] = py;
                is_b_pawn = -1;
                for (int j = 0; j < 8; j++) {
                    if (apawn[j][0] == px && apawn[j][1] == py) {
                        apawn[j][2] = -1;
                    }
                }
                if(aking[0]==px && aking[1]==py)
                {
                    aking[2]=-1;
                }
                for(int j=0;j<10;j++)
                {
                    if(arook[j][0]==px&&arook[j][1]==py)
                    {
                        arook[j][2]=-1;
                    }
                    else if(abishop[j][0]==px&&abishop[j][1]==py)
                    {
                        abishop[j][2]=-1;
                    }
                    else if(aknight[j][0]==px&&aknight[j][1]==py)
                    {
                        aknight[j][2]=-1;
                    }
                    else if(aqueen[j][0]==px && aqueen[j][1]==py)
                    {
                        aqueen[j][2]=-1;
                    }
                }
                occupied_a[px - 1][py - 1] = 0;
            }//
            if(bpawn[k][1]==8)
            {
                x_val=bpawn[k][0];
                bpawn[k][2]=-1;
                switcher=1;
                select=1;
            }
        }
    }
    public void b_bishop(int ind) {
        if (bbishop[ind][2] == 0) {
            int ok = 0;
            if (px - py == px1 - py1) {
                if (px1 < px) {
                    for (int i = px - 1; i > px1; i--) {
                        for (int j = py - 1; j > py1; j--) {
                            if (i - j == px - py) {
                                if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                    k++;
                                } else {
                                    k--;
                                    break;
                                }
                            }
                        }
                    }
                    if (k + 1 == py - py1) {
                        ok = 1;
                    } else {
                        ok = -1;
                    }
                } else {
                    for (int i = px + 1; i < px1; i++) {
                        for (int j = py + 1; j < py1; j++) {
                            if (i - j == px - py) {
                                if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                    k++;
                                } else {
                                    k--;
                                    break;
                                }
                            }
                        }
                    }
                    if (k + 1 == py1 - py) {
                        ok = 1;
                    } else {
                        ok = -1;
                    }
                }
            } else if (px + py == px1 + py1) {
                if (px1 < px) {
                    for (int i = px - 1; i > px1; i--) {
                        for (int j = py + 1; j < py1; j++) {
                            if (i + j == px + py) {
                                if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                    k++;
                                } else {
                                    k--;
                                    break;
                                }
                            }
                        }
                    }
                    if (k + 1 == px - px1) {
                        ok = 1;
                    } else {
                        ok = -1;
                    }
                } else {
                    for (int i = px + 1; i < px1; i++) {
                        for (int j = py - 1; j > py1; j--) {
                            if (i + j == px + py) {
                                if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                    k++;
                                } else {
                                    k--;
                                    break;
                                }
                            }
                        }
                    }
                    if (k + 1 == px1 - px) {
                        ok = 1;
                    } else {
                        ok = -1;
                    }
                }
            }
            if (ok == 1) {
                if (occupied_a[px - 1][py - 1] == 0) {
                    bbishop[ind][0] = px;
                    bbishop[ind][1] = py;
                    is_b_bishop = -1;
                } else {
                    bbishop[ind][0] = px;
                    bbishop[ind][1] = py;
                    for (int j = 0; j < 8; j++) {
                        if (apawn[j][0] == px && apawn[j][1] == py) {
                            apawn[j][2] = -1;
                        }
                    }
                    if (aking[0] == px && aking[1] == py)
                        aking[2] = -1;
                    for (int j = 0; j < 10; j++) {
                        if (arook[j][0] == px && arook[j][1] == py) {
                            arook[j][2] = -1;
                        } else if (abishop[j][0] == px && abishop[j][1] == py) {
                            abishop[j][2] = -1;
                        } else if (aknight[j][0] == px && aknight[j][1] == py) {
                            aknight[j][2] = -1;
                        } else if (aqueen[j][0] == px && aqueen[j][1] == py) {
                            aqueen[j][2] = -1;
                        }
                    }
                    occupied_a[px - 1][py - 1] = 0;
                    is_b_bishop = -1;
                }
            }
        }
    }
    public void b_rook(int ind) {
        int ok = 0;
        if(brook[ind][2]==0)
        {
        if (px1 == px) {
            if (py1 < py) {
                for (int i = py - 1; i > py1; i--) {
                    if (occupied_b[px - 1][i - 1] == 0 && occupied_a[px - 1][i - 1] == 0) {
                        k++;
                    } else {
                        k--;
                        break;
                    }
                }
                if (k + 1 == py - py1) {
                    ok = 1;
                } else {
                    ok = -1;
                }
            } else {
                for (int i = py + 1; i < py1; i++) {
                    if (occupied_b[px - 1][i - 1] == 0 && occupied_a[px - 1][i - 1] == 0) {
                        k++;
                    } else {
                        k--;
                        break;
                    }
                }
                if (k + 1 == py1 - py) {
                    ok = 1;
                } else {
                    ok = -1;
                }
            }
        }
        else if (py1 == py) {
            if (px1 < px) {
                for (int i = px-1; i > px1; i--) {
                    if (occupied_b[i-1][py-1] == 0 && occupied_a[i-1][py-1] == 0) {
                        k++;
                    }
                    else
                    {
                        k--;
                        break;
                    }
                }
                if(k+1==px-px1)
                {
                    ok=1;
                }
                else
                {
                    ok=-1;
                }
            } else {
                for (int i = px+1; i < px1; i++) {
                    if (occupied_b[i-1][py-1] == 0 && occupied_a[i-1][py-1] == 0)
                    {
                        k++;
                    }
                    else
                    {
                        k--;
                        break;
                    }
                }
                if(k+1==px1-px)
                {
                    ok=1;
                }
                else
                {
                    ok=-1;
                }
            }
        }
        if (ok == 1) {
            if (occupied_a[px - 1][py - 1] == 0) {
                brook[ind][0] = px;
                brook[ind][1] = py;
                brook[ind][4] = -1;
                is_b_rook = -1;
            } else {
                brook[ind][0] = px;
                brook[ind][1] = py;
                brook[ind][4] = -1;
                for (int j = 0; j < 8; j++) {
                    if (apawn[j][0] == px && apawn[j][1] == py) {
                        apawn[j][2] = -1;
                    }
                }
                if (aking[0] == px && aking[1] == py) {
                    aking[2] = -1;
                }
                for(int j=0;j<10;j++)
                {
                    if(arook[j][0]==px&&arook[j][1]==py)
                    {
                        arook[j][2]=-1;
                    }
                    else if(abishop[j][0]==px&&abishop[j][1]==py)
                    {
                        abishop[j][2]=-1;
                    }
                    else if(aknight[j][0]==px&&aknight[j][1]==py)
                    {
                        aknight[j][2]=-1;
                    }
                    else if(aqueen[j][0]==px && aqueen[j][1]==py)
                    {
                        aqueen[j][2]=-1;
                    }
                }
                occupied_a[px - 1][py - 1] = 0;
                is_b_rook = -1;
            }

        }
    }
    }
    public void b_knight(int k) {
        if (bknight[k][2] == 0)
        {
            int ok = 0;
            int[] kx = new int[4];
            int[] ky = new int[4];
            kx[0] = px1 - 1;
            kx[1] = px1 + 1;
            kx[2] = px1 - 2;
            kx[3] = px1 + 2;

            ky[0] = py1 - 2;
            ky[1] = py1 - 1;
            ky[2] = py1 + 1;
            ky[3] = py1 + 2;

            if (px == kx[0] && py == ky[0] || px == kx[1] && py == ky[0] || px == kx[2] && py == ky[1] || px == kx[2] && py == ky[2] || px == kx[2] && py == ky[1] || px == kx[0] && py == ky[3] || px == kx[1] && py == ky[3] || px == kx[3] && py == ky[1] || px == kx[3] && py == ky[2])  {
                ok = 1;
            }
            if (ok == 1) {
                if(occupied_a[px - 1][py - 1] != 1 && occupied_b[px - 1][py - 1] != 1)
                {
                    bknight[k][0] = px;
                    bknight[k][1] = py;
                    is_b_knight=-1;
                }
                else if (occupied_a[px - 1][py - 1] == 1) {
                    bknight[k][0] = px;
                    bknight[k][1] = py;
                    for (int j = 0; j < 8; j++) {
                        if (apawn[j][0] == px && apawn[j][1] == py) {
                            apawn[j][2] = -1;
                        }
                    }
                    if (aking[0] == px && aking[1] == py) {
                        aking[2] = -1;
                    }
                    for(int j=0;j<10;j++)
                    {
                        if(arook[j][0]==px&&arook[j][1]==py)
                        {
                            arook[j][2]=-1;
                        }
                        else if(abishop[j][0]==px&&abishop[j][1]==py)
                        {
                            abishop[j][2]=-1;
                        }
                        else if(aknight[j][0]==px&&aknight[j][1]==py)
                        {
                            aknight[j][2]=-1;
                        }
                        else if(aqueen[j][0]==px && aqueen[j][1]==py)
                        {
                            aqueen[j][2]=-1;
                        }
                    }
                    occupied_a[px - 1][py - 1] = 0;
                    is_b_knight = -1;
                }
            }
        }
    }
    public void b_king()
    {
        int z=0;
        int ok=0;
        int castle=0;
        if(bking[2]==0)
        {
            if(py1==py)
            {
                if((px1-px==1||px-px1==1)&&occupied_b[px-1][py-1]==0)
                {
                    z=b_check(px,py);
                    if(occupied_a[px-1][py-1]==1)
                    {
                        ok=1;
                    }
                }
            }
            else if(px1==px)
            {
                if((py1-py==1||py-py1==1)&&occupied_b[px-1][py-1]==0)
                {
                    z=b_check(px,py);
                    if(occupied_a[px-1][py-1]==1)
                    {
                        ok=1;
                    }
                }
            }
            else if(px-py==px1-py1)
            {
                if((px1-px==1||px-px1==1)&&occupied_b[px-1][py-1]==0)
                {
                    z=b_check(px,py);
                    if(occupied_a[px-1][py-1]==1)
                    {
                        ok=1;
                    }
                }
            }
            else if(px+py==px1+py1)
            {
                if((px1-px==1||px-px1==1)&&occupied_b[px-1][py-1]==0)
                {
                    z=b_check(px,py);
                    if(occupied_a[px-1][py-1]==1)
                    {
                        ok=1;
                    }
                }
            }
            if(bking[4]==0)
            {
                if(px-px1==2&&py==py1&&brook[1][4]==0)
                {
                    castle=b_castling(px,py);
                    if(castle==1)
                    {
                        occupied_b[bking[0]-1][bking[1]-1]=0;
                        occupied_b[brook[0][0]-1][brook[0][1]-1]=0;
                        bking[0]=px;
                        bking[4]=-1;
                        brook[1][0]=px-1;
                        is_b_king=-1;
                        occupied_b[px-1][py-1]=1;
                        occupied_b[px-2][py-1]=1;
                    }
                }
                else if(px1-px==2&&py==py1&&brook[0][4]==0)
                {
                    castle=b_castling(px,py);
                    if(castle==1)
                    {
                        occupied_b[bking[0]-1][bking[1]-1]=0;
                        occupied_b[brook[0][0]-1][brook[0][1]-1]=0;
                        bking[0]=px;
                        bking[4]=-1;
                        brook[0][0]=px+1;
                        is_b_king=-1;
                        occupied_b[px-1][py-1]=1;
                        occupied_b[px][py-1]=1;
                    }
                }
            }
            if(z==1)
            {
                bking[0]=px;
                bking[1]=py;
                bking[4]=-1;
                if(ok==1)
                {
                    for (int j = 0; j < 8; j++) {
                        if (apawn[j][0] == px && apawn[j][1] == py) {
                            apawn[j][2] = -1;
                        }
                    }
                    if (aking[0] == px && aking[1] == py) {
                        aking[2] = -1;
                    }
                    for(int j=0;j<10;j++)
                    {
                        if(arook[j][0]==px&&arook[j][1]==py)
                        {
                            arook[j][2]=-1;
                        }
                        else if(abishop[j][0]==px&&abishop[j][1]==py)
                        {
                            abishop[j][2]=-1;
                        }
                        else if(aknight[j][0]==px&&aknight[j][1]==py)
                        {
                            aknight[j][2]=-1;
                        }
                        else if(aqueen[j][0]==px && aqueen[j][1]==py)
                        {
                            aqueen[j][2]=-1;
                        }
                    }
                    occupied_a[px - 1][py - 1] = 0;
                }
                is_b_king = -1;
            }
        }
    }
    public int b_castling(int x,int y)
    {
        int akx=bking[0];
        int aky=bking[1];
        int bx[][]=new int[2][2];
        int ok=0;
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<2;j++)
            {
                bx[i][j]=brook[i][j];
            }
        }
        if(y==aky)
        {
            if(akx-x==2)
            {
                for(int i=x+1;i<akx;i++)
                {
                    if(occupied_a[i-1][y-1]==0&&occupied_b[i-1][y-1]==0&&b_check(i,y)==1)
                    {
                        ok=1;
                    }
                    else
                    {
                        ok=-1;
                        break;
                    }
                }
                if(ok==1)
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }
            else if(x-akx==2)
            {
                for(int i=akx+1;i<x;i++)
                {
                    if(occupied_a[i-1][y-1]==0&&occupied_b[i-1][y-1]==0&&b_check(i,y)==1)
                    {
                        ok=1;
                    }
                    else
                    {
                        ok=-1;
                        break;
                    }
                }
                if(ok==1)
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }
        }
        return 0;
    }
    public int b_check(int kix,int kiy)//To check if King is moving into check
    {
        con = 0;
        int z = 0;
        k = 0;
        int king_x = bking[0];
        int king_y = bking[1];
        int count1, count2;
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
                    k++;
                } else if (occupied_b[i - 1][y - 1] == 1 && i == king_x && y == king_y) {
                    for (int j = king_x + 1; j < 9; j++) {
                        a_moves[z][0] = j;
                        a_moves[z][1] = y;
                        z++;
                    }
                } else
                    break;
            }
            if (x > 0 && y > 0 && occupied_b[x + k - 1][y - 1] == 1) {
                if (bking[0] == x + k || bking[1] == y) {
                    for (int i = x + k + 1; i < 9; i++) {
                        if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                            a_moves[z][0] = i;
                            a_moves[z][1] = y;
                            z++;
                            k++;
                        } else
                            break;
                    }
                } else {
                    a_moves[z][0] = x + k;
                    a_moves[z][1] = y;
                    z++;
                }
            }
            k = 0;
            for (int i = x - 1; i > 0; i--) {

                if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                    a_moves[z][0] = i;
                    a_moves[z][1] = y;
                    z++;
                    k++;
                } else if (occupied_b[i - 1][y - 1] == 1 && i == king_x && y == king_y) {
                    for (int j = king_x - 1; j > 0; j--) {
                        a_moves[z][0] = j;
                        a_moves[z][1] = y;
                        z++;
                    }
                } else
                    break;
            }
            if (x > k + 1 && y > 0 && occupied_b[x - k - 2][y - 1] == 1) {
                a_moves[z][0] = x - k - 1;
                a_moves[z][1] = y;
                z++;
            }
            k = 0;
            //left diagonal "1"-
            for (int i = x + 1; i < 9; i++) {
                for (int j = y + 1; j < 9; j++) {
                    if (i - j == x - y) {
                        if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                            a_moves[z][0] = i;
                            a_moves[z][1] = j;
                            z++;
                            k++;
                        } else if (occupied_b[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                            for (int k1 = king_x + 1; k1 < 9; k1++) {
                                for (int k2 = king_y + 1; k2 < 9; k2++) {
                                    if (k1 - k2 == king_x - king_y && occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                        a_moves[z][0] = k1;
                                        a_moves[z][1] = k2;
                                        z++;
                                    }
                                }
                            }
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
                a_moves[z][0] = x + k + 1;
                a_moves[z][1] = y + k + 1;
                z++;
            }
            k = 0;
            con = 0;
            //left diagonal "2"-
            for (int i = x - 1; i > 0; i--) {
                for (int j = y - 1; j > 0; j--) {
                    if (i - j == x - y) {
                        if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                            a_moves[z][0] = i;
                            a_moves[z][1] = j;
                            z++;
                            k++;
                        } else if (occupied_b[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                            for (int k1 = king_x - 1; k1 > 0; k1--) {
                                for (int k2 = king_y - 1; k2 > 0; k2--) {
                                    if (k1 - k2 == king_x - king_y && occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                        a_moves[z][0] = k1;
                                        a_moves[z][1] = k2;
                                        z++;
                                    }
                                }
                            }
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
                a_moves[z][0] = x - k - 1;
                a_moves[z][1] = y - k - 1;
                z++;
            }
            k = 0;
            con = 0;
            //Right diagonal "1"-
            for (int i = x + 1; i < 9; i++) {
                for (int j = y - 1; j > 0; j--) {
                    if (i + j == x + y) {
                        if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                            a_moves[z][0] = i;
                            a_moves[z][1] = j;
                            z++;
                            k++;
                        } else if (occupied_b[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                            for (int k1 = king_x + 1; k1 < 9; k1++) {
                                for (int k2 = king_y - 1; k2 > 0; k2--) {
                                    if (k1 + k2 == king_x + king_y && occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                        a_moves[z][0] = k1;
                                        a_moves[z][1] = k2;
                                        z++;
                                    }
                                }
                            }
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
                a_moves[z][0] = x + k + 1;
                a_moves[z][1] = y - k - 1;
                z++;
            }
            k = 0;
            con = 0;
            //Right diagonal "2"-
            for (int i = x - 1; i > 0; i--) {
                for (int j = y + 1; j < 9; j++) {
                    if (i + j == x + y) {
                        if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                            a_moves[z][0] = i;
                            a_moves[z][1] = j;
                            z++;
                            k++;
                        } else if (occupied_b[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                            for (int k1 = king_x - 1; k1 > 0; k1--) {
                                for (int k2 = king_y + 1; k2 < 9; k2++) {
                                    if (k1 + k2 == king_x + king_y && occupied_a[k1 - 1][k2 - 1] == 0 && occupied_b[k1 - 1][k2 - 1] == 0) {
                                        a_moves[z][0] = k1;
                                        a_moves[z][1] = k2;
                                        z++;
                                    }
                                }
                            }
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
            if (x > k + 1 && y + k < 8 && occupied_b[x - k - 2][y + k] == 1) {
                a_moves[z][0] = x - k - 1;
                a_moves[z][1] = y + k + 1;
                z++;
            }
            k = 0;
            con = 0;
            //constant x-
            for (int i = y + 1; i < 9; i++) {
                if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                    a_moves[z][0] = x;
                    a_moves[z][1] = i;
                    z++;
                    k++;
                } else if (occupied_b[x - 1][i - 1] == 1 && x == king_x && i == king_y) {
                    for (int j = king_y + 1; j < 9; j++) {
                        a_moves[z][0] = x;
                        a_moves[z][1] = j;
                        z++;
                    }
                } else
                    break;
            }
            if (y + k < 8 && x > 0 && occupied_b[x - 1][y + k] == 1) {
                a_moves[z][0] = x;
                a_moves[z][1] = y + k + 1;
                z++;
            }
            k = 0;
            for (int i = y - 1; i > 0; i--) {
                if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                    a_moves[z][0] = x;
                    a_moves[z][1] = i;
                    z++;
                    k++;
                } else if (occupied_b[x - 1][i - 1] == 1 && x == king_x && i == king_y) {
                    for (int j = king_y - 1; j > 0; j--) {
                        a_moves[z][0] = x;
                        a_moves[z][1] = j;
                        z++;
                    }
                } else
                    break;
            }
            if (y > k + 1 && x > 0 && occupied_b[x - 1][y - k - 2] == 1) {
                a_moves[z][0] = x;
                a_moves[z][1] = y - k - 1;
                z++;
            }
            k = 0;
        }
    }
        //pawn-
        for (int i = 0; i < 8; i++) {
            if (apawn[i][2] == 0) {
                int x = apawn[i][0];
                int y = apawn[i][1];
                if (x < 8 && y > 1 ) {
                    a_moves[z][0] = x + 1;
                    a_moves[z][1] = y - 1;
                    z++;
                }
                if (x > 1 && y > 1 ) {
                    a_moves[z][0] = x - 1;
                    a_moves[z][1] = y - 1;
                    z++;
                }
            }
        }
        //rook-
        for (int j = 0; j < arc; j++) {
            if (arook[j][2] == 0) {
                int x = arook[j][0];
                int y = arook[j][1];
                for (int i = x - 1; i > 0; i--) {
                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        a_moves[z][0] = i;
                        a_moves[z][1] = y;
                        z++;
                        k++;
                    }
                    else if(occupied_b[i-1][y-1]==1 && i==king_x&&y==king_y )
                    {
                        for(int k=king_x-1;k>0;k--)
                        {
                            a_moves[z][0] = k;
                            a_moves[z][1] = y;
                            z++;
                        }
                    } else
                        break;
                }
                if (x >= k + 2 && y >= 1 && occupied_b[x - k - 2][y - 1] == 1) {
                    a_moves[z][0] = x - k - 1;
                    a_moves[z][1] = y;
                    z++;
                }
                k = 0;
                for (int i = y - 1; i > 0; i--) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        a_moves[z][0] = x;
                        a_moves[z][1] = i;
                        z++;
                        k++;
                    }
                    else if(occupied_b[x-1][i-1]==1 && x==king_x&&i==king_y )
                    {
                        for(int k=king_y-1;k>0;k--)
                        {
                            a_moves[z][0] = x;
                            a_moves[z][1] = k;
                            z++;
                        }
                    } else
                        break;
                }
                if (x >= 1 && y >= k + 2 && occupied_b[x - 1][y - k - 2] == 1) {
                    a_moves[z][0] = x;
                    a_moves[z][1] = y - k - 1;
                    z++;
                }
                k = 0;
                for (int i = x + 1; i < 9; i++) {
                    if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                        a_moves[z][0] = i;
                        a_moves[z][1] = y;
                        z++;
                        k++;
                    }
                    else if(occupied_b[i-1][y-1]==1 && i==king_x&&y==king_y )
                    {
                        for(int k=king_x+1;k<9;k++)
                        {
                            a_moves[z][0] = k;
                            a_moves[z][1] = y;
                            z++;
                        }
                    } else
                        break;
                }
                if (y >= 1 && x + k < 8 && occupied_b[x + k][y - 1] == 1) {
                    a_moves[z][0] = x + k + 1;
                    a_moves[z][1] = y;
                    z++;
                }
                k = 0;
                for (int i = y + 1; i < 9; i++) {
                    if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                        a_moves[z][0] = x;
                        a_moves[z][1] = i;
                        z++;
                        k++;
                    }
                    else if(occupied_b[x-1][i-1]==1 && x==king_x&&i==king_y )
                    {
                        for(int k=king_y+1;k<9;k++)
                        {
                            a_moves[z][0] = x;
                            a_moves[z][1] = k;
                            z++;
                        }
                    } else
                        break;
                }
                if (x >= 1 && y + k < 8 && occupied_b[x - 1][y + k] == 1) {
                    a_moves[z][0] = x;
                    a_moves[z][1] = y + k + 1;
                    z++;
                }
                k = 0;
            }
        }
        //kinght//nothing altered
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
                //left diagonal "1"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i - j == x - y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                a_moves[z][0] = i;
                                a_moves[z][1] = j;
                                z++;
                                k++;
                            }
                            else if (occupied_b[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                                for(int k1=king_x+1;k1<9;k1++) {
                                    for(int k2=king_y+1;k2<9;k2++) {
                                        if(k1-k2 == king_x-king_y&& occupied_a[k1-1][k2-1] == 0&& occupied_b[k1-1][k2-1] == 0) {
                                            a_moves[z][0] = k1;
                                            a_moves[z][1] = k2;
                                            z++;
                                        }
                                    }
                                }
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
                if (x + k < 8 && y + k < 8 && occupied_b[x + k][y + k] == 1) {
                    a_moves[z][0] = x + k + 1;
                    a_moves[z][1] = y + k + 1;
                    z++;
                }
                k = 0;
                con = 0;
                //left diagonal "2"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i - j == x - y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                a_moves[z][0] = i;
                                a_moves[z][1] = j;
                                z++;
                                k++;
                            }
                            else if (occupied_b[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                                for(int k1=king_x-1;k1>0;k1--) {
                                    for(int k2=king_y-1;k2>0;k2--) {
                                        if(k1-k2 == king_x-king_y&& occupied_a[k1-1][k2-1] == 0&& occupied_b[k1-1][k2-1] == 0) {
                                            a_moves[z][0] = k1;
                                            a_moves[z][1] = k2;
                                            z++;
                                        }
                                    }
                                }
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
                if (x >= k + 2 && y >= k + 2 && occupied_b[x - k - 2][y - k - 2] == 1) {
                    a_moves[z][0] = x - k - 1;
                    a_moves[z][1] = y - k - 1;
                    z++;
                }
                k = 0;
                con = 0;
                //Right diagonal "1"-
                for (int i = x + 1; i < 9; i++) {
                    for (int j = y - 1; j > 0; j--) {
                        if (i + j == x + y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                a_moves[z][0] = i;
                                a_moves[z][1] = j;
                                z++;
                                k++;
                            }
                            else if (occupied_b[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                                for(int k1=king_x+1;k1<9;k1++) {
                                    for(int k2=king_y-1;k2>0;k2--) {
                                        if(k1+k2 == king_x+king_y&& occupied_a[k1-1][k2-1] == 0&& occupied_b[k1-1][k2-1] == 0) {
                                            a_moves[z][0] = k1;
                                            a_moves[z][1] = k2;
                                            z++;
                                        }
                                    }
                                }
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
                if (y >= k + 2 && x + k < 8 && occupied_b[x + k][y - k - 2] == 1) {
                    a_moves[z][0] = x + k + 1;
                    a_moves[z][1] = y - k - 1;
                    z++;
                }
                k = 0;
                con = 0;
                //Right diagonal "2"-
                for (int i = x - 1; i > 0; i--) {
                    for (int j = y + 1; j < 9; j++) {
                        if (i + j == x + y) {
                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                a_moves[z][0] = i;
                                a_moves[z][1] = j;
                                z++;
                                k++;
                            }
                            else if (occupied_b[i - 1][j - 1] == 1 && i == king_x && j == king_y) {
                                for(int k1=king_x-1;k1>0;k1--) {
                                    for(int k2=king_y+1;k2<9;k2++) {
                                        if(k1+k2 == king_x+king_y&& occupied_a[k1-1][k2-1] == 0&& occupied_b[k1-1][k2-1] == 0) {
                                            a_moves[z][0] = k1;
                                            a_moves[z][1] = k2;
                                            z++;
                                        }
                                    }
                                }
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
                if (x >= k + 2 && y + k < 8 && occupied_b[x - k - 2][y + k] == 1) {
                    a_moves[z][0] = x - k - 1;
                    a_moves[z][1] = y + k + 1;
                    z++;
                }
                k = 0;
                con = 0;
            }
        }
        if(aking[2]==0)
        {
            int x=aking[0];
            int y=aking[1];
            //constant x-
            if(x>0&&y>1&&occupied_b[x-1][y-2]==0)
            {
                a_moves[z][0]=x;
                a_moves[z][1]=y-1;
                z++;
            }
            if(x>0&&y<8&&occupied_b[x-1][y]==0)
            {
                a_moves[z][0]=x;
                a_moves[z][1]=y+1;
                z++;
            }
            //constant y-
            if(x>1&&y>0&&occupied_b[x-2][y-1]==0)
            {
                a_moves[z][0]=x-1;
                a_moves[z][1]=y;
                z++;
            }
            if(y>0&&x<8&&occupied_b[x][y-1]==0)
            {
                a_moves[z][0]=x+1;
                a_moves[z][1]=y;
                z++;
            }
            //
            if(x<8&&y<8&&occupied_b[x][y]==0)
            {
                a_moves[z][0]=x+1;
                a_moves[z][1]=y+1;
                z++;
            }
            if(x>1&&y>1&&occupied_b[x-2][y-2]==0)
            {
                a_moves[z][0]=x-1;
                a_moves[z][1]=y-1;
                z++;
            }
            //
            if(x<8&&y>1&&occupied_b[x][y-2]==0)
            {
                a_moves[z][0]=x+1;
                a_moves[z][1]=y-1;
                z++;
            }
            if(x>1&&y<8&&occupied_b[x-2][y]==0)
            {
                a_moves[z][0]=x-1;
                a_moves[z][1]=y+1;
                z++;
            }
        }
        count1=z;
        count2=0;
        while(z>0)
        {
            if(kix==a_moves[z-1][0]&&kiy==a_moves[z-1][1])
            {
                break;
            }
            else
            {
                count2++;
                z--;
            }
        }
        if(count2==count1)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    public void b_queen(int wq) {
        int ok = 0;
        int k=0;
        if (bqueen[wq][2] == 0) {
            if(px1==px)
            {
                if(py<py1)
                {
                    for(int i=py+1;i<py1;i++)
                    {
                        if(occupied_a[px-1][i-1]==0&&occupied_b[px-1][i-1]==0)
                        {
                            k++;
                        }
                    }
                    if(k+1==py1-py)
                    {
                        ok=1;
                    }
                }
                else
                {
                    for(int i=py1+1;i<py;i++)
                    {

                        if(occupied_a[px-1][i-1]==0&&occupied_b[px-1][i-1]==0)
                        {
                            k++;
                        }
                    }
                    if(k+1==py-py1)
                    {
                        ok=1;
                    }
                }
            }
            else if(py1==py)
            {
                if(px1<px)
                {
                    for(int i=px1+1;i<px;i++)
                    {
                        if (occupied_a[i - 1][py - 1] == 0 && occupied_b[i - 1][py - 1] == 0) {
                            k++;
                        }
                    }

                    if(k+1==px-px1)
                    {
                        ok=1;
                    }
                }
                else
                {
                    for(int i=px+1;i<px1;i++)
                    {
                        if (occupied_a[i - 1][py - 1] == 0 && occupied_b[i - 1][py - 1] == 0) {
                            k++;
                        }
                    }

                    if(k+1==px1-px)
                    {
                        ok=1;
                    }
                }
            }
            else if(px-py==px1-py1)
            {
                if(px>px1)
                {
                    for(int i=px1+1;i<px;i++)
                    {
                        for(int j=py1+1;j<py;j++)
                        {
                            if(i-j==px-py&&occupied_a[i-1][j-1]==0&&occupied_b[i-1][j-1]==0)
                            {
                                k++;
                            }
                        }
                    }
                    if(k+1==px-px1)
                    {
                        ok=1;
                    }
                }
                else
                {
                    for(int i=px+1;i<px1;i++)
                    {
                        for(int j=py+1;j<py1;j++)
                        {
                            if(i-j==px-py&&occupied_a[i-1][j-1]==0&&occupied_b[i-1][j-1]==0)
                            {
                                k++;
                            }
                        }
                    }
                    if(k+1==px1-px)
                    {
                        ok=1;
                    }
                }
            }
            else if(px+py==px1+py1)
            {
                if(px>px1)
                {
                    for(int i=px1+1;i<px;i++)
                    {
                        for(int j=py+1;j<py1;j++)
                        {
                            if(i+j==px+py&&occupied_a[i-1][j-1]==0&&occupied_b[i-1][j-1]==0)
                            {
                                k++;
                            }
                        }
                    }
                    if(k+1==px-px1)
                    {
                        ok=1;
                    }
                }
                else
                {
                    for(int i=px+1;i<px1;i++)
                    {
                        for(int j=py1+1;j<py;j++)
                        {
                            if(i+j==px+py&&occupied_a[i-1][j-1]==0&&occupied_b[i-1][j-1]==0)
                            {
                                k++;
                            }
                        }
                    }
                    if(k+1==px1-px)
                    {
                        ok=1;
                    }
                }
            }
            if(ok==1)
            {
                if(occupied_a[px-1][py-1]==0&&occupied_b[px-1][py-1]==0)
                {
                    bqueen[wq][0]=px;
                    bqueen[wq][1]=py;
                    is_b_queen=-1;
                }
                else if(occupied_b[px-1][py-1]==0&&occupied_a[px-1][py-1]!=0)
                {
                    for(int i=0;i<8;i++)
                    {
                        if(apawn[i][0]==px&&apawn[i][1]==py)
                        {
                            apawn[i][2]=-1;
                        }
                    }
                    if(aking[0]==px&&aking[1]==py)
                    {
                        aking[2]=-1;
                    }
                    for(int j=0;j<10;j++)
                    {
                        if(arook[j][0]==px&&arook[j][1]==py)
                        {
                            arook[j][2]=-1;
                        }
                        else if(abishop[j][0]==px&&abishop[j][1]==py)
                        {
                            abishop[j][2]=-1;
                        }
                        else if(aknight[j][0]==px&&aknight[j][1]==py)
                        {
                            aknight[j][2]=-1;
                        }
                        else if(aqueen[j][0]==px && aqueen[j][1]==py)
                        {
                            aqueen[j][2]=-1;
                        }
                    }
                    bqueen[wq][0]=px;
                    bqueen[wq][1]=py;
                    is_b_queen=-1;
                }
            }
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
                if(bking[2]==-1)
                {
                    Intent i=new Intent(c,D.class);
                    c.startActivity(i);
                }
            }
            if(m%4==0)
            {
                if(aking[2]==-1)
                {
                    Intent i=new Intent(c,E.class);
                    c.startActivity(i);
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
                for (int i = 0; i < 8; i++) {
                    if (bpawn[i][2] == 0) {
                        canvas.drawBitmap(bp, (bpawn[i][0]) * wr, (bpawn[i][1]) * hr, null);
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (apawn[i][2] == 0) {
                        canvas.drawBitmap(ap, (apawn[i][0]) * wr, (apawn[i][1]) * hr, null);
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
                        k = 0;
                        int qs = -1;
                        for (int i = 0; i < aqc; i++) {
                            if (aqueen[i][2] == 0 && aqueen[i][3] == 1) {
                                qs = i;
                            }
                        }
                        if (qs != -1) {
                            int x = aqueen[qs][0];
                            int y = aqueen[qs][1];
                            int q_moves[][] = new int[32][2];
                            //constant y-
                            for (int i = x + 1; i < 9; i++) {
                                if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                                    q_moves[z][0] = i;
                                    q_moves[z][1] = y;
                                    z++;
                                    k++;
                                } else
                                    break;
                            }
                            if (x + k < 8 && y > 0 && occupied_b[x + k][y - 1] == 1) {
                                q_moves[z][0] = x + k + 1;
                                q_moves[z][1] = y;
                                z++;
                            }
                            k = 0;
                            for (int i = x - 1; i > 0; i--) {

                                if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                                    q_moves[z][0] = i;
                                    q_moves[z][1] = y;
                                    z++;
                                    k++;
                                } else
                                    break;
                            }
                            if (x > k + 1 && y > 0 && occupied_b[x - k - 2][y - 1] == 1) {
                                q_moves[z][0] = x - k - 1;
                                q_moves[z][1] = y;
                                z++;
                            }
                            k = 0;
                            //left diagonal "1"-
                            for (int i = x + 1; i < 9; i++) {
                                for (int j = y + 1; j < 9; j++) {
                                    if (i - j == x - y) {
                                        if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                            q_moves[z][0] = i;
                                            q_moves[z][1] = j;
                                            z++;
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
                                q_moves[z][0] = x + k + 1;
                                q_moves[z][1] = y + k + 1;
                                z++;
                            }
                            k = 0;
                            con = 0;
                            //left diagonal "2"-
                            for (int i = x - 1; i > 0; i--) {
                                for (int j = y - 1; j > 0; j--) {
                                    if (i - j == x - y) {
                                        if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                            q_moves[z][0] = i;
                                            q_moves[z][1] = j;
                                            z++;
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
                                q_moves[z][0] = x - k - 1;
                                q_moves[z][1] = y - k - 1;
                                z++;
                            }
                            k = 0;
                            con = 0;
                            //Right diagonal "1"-
                            for (int i = x + 1; i < 9; i++) {
                                for (int j = y - 1; j > 0; j--) {
                                    if (i + j == x + y) {
                                        if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                            q_moves[z][0] = i;
                                            q_moves[z][1] = j;
                                            z++;
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
                                q_moves[z][0] = aqueen[qs][0] + k + 1;
                                q_moves[z][1] = aqueen[qs][1] - k - 1;
                                z++;
                            }
                            k = 0;
                            con = 0;
                            //Right diagonal "2"-
                            for (int i = x - 1; i > 0; i--) {
                                for (int j = y + 1; j < 9; j++) {
                                    if (i + j == x + y) {
                                        if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                            q_moves[z][0] = i;
                                            q_moves[z][1] = j;
                                            z++;
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
                            if (x > k + 1 && y + k < 8 && occupied_b[x - k - 2][y + k] == 1) {
                                q_moves[z][0] = x - k - 1;
                                q_moves[z][1] = y + k + 1;
                                z++;
                            }
                            k = 0;
                            con = 0;
                            //constant x-
                            for (int i = y + 1; i < 9; i++) {
                                if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                                    q_moves[z][0] = x;
                                    q_moves[z][1] = i;
                                    z++;
                                    k++;
                                } else
                                    break;
                            }
                            if (y + k < 8 && x > 0 && occupied_b[x - 1][y + k] == 1) {
                                q_moves[z][0] = x;
                                q_moves[z][1] = y + k + 1;
                                z++;
                            }
                            k = 0;
                            for (int i = y - 1; i > 0; i--) {
                                if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                                    q_moves[z][0] = x;
                                    q_moves[z][1] = i;
                                    z++;
                                    k++;
                                } else
                                    break;
                            }
                            if (y > k + 1 && x > 0 && occupied_b[x - 1][y - k - 2] == 1)//check condition***********
                            {
                                q_moves[z][0] = x;
                                q_moves[z][1] = y - k - 1;
                                z++;
                            }
                            k = 0;
                            while (z > -1) {
                                if ((q_moves[z][0] != 0 || q_moves[z][1] != 0) && occupied_b[q_moves[z][0] - 1][q_moves[z][1] - 1] == 0) {
                                    canvas.drawRect(q_moves[z][0] * wr, q_moves[z][1] * hr, (q_moves[z][0] + 1) * wr, (q_moves[z][1] + 1) * hr, p1);
                                }
                                else if ((q_moves[z][0] != 0 || q_moves[z][1] != 0) && occupied_b[q_moves[z][0] - 1][q_moves[z][1] - 1] == 1) {
                                    canvas.drawRect(q_moves[z][0] * wr, q_moves[z][1] * hr, (q_moves[z][0] + 1) * wr, (q_moves[z][1] + 1) * hr, p2);
                                }
                                z--;
                            }
                        }
                if(is_a_pawn==1) {
                    int ps = -1;
                    int x, y;
                    int p_moves[][] = new int[6][2];
                    for (int i = 0; i < 8; i++) {
                        if (apawn[i][2] == 0 && apawn[i][3] == 1) {
                            ps = i;
                        }
                    }
                    if (ps != -1 ) {
                        x = apawn[ps][0];
                        y = apawn[ps][1];
                        if (occupied_a[x - 1][y - 2] == 0 && occupied_b[x - 1][y - 2] == 0) {
                            p_moves[z][0] = x;
                            p_moves[z][1] = y - 1;
                            z++;
                            if(apawn[ps][4] == 0)
                            {
                                if (occupied_a[x - 1][y - 3] == 0 && occupied_b[x - 1][y - 3] == 0) {
                                    p_moves[z][0] = x;
                                    p_moves[z][1] = y - 2;
                                    z++;
                                }
                            }
                        }
                        if(x<8&&y>0&&occupied_b[x][y-1]==1)
                        {
                            for(int i=0;i<8;i++)
                            {
                                if(bpawn[i][0]==x+1&&bpawn[i][1]==y&&bpawn[i][4]==1)
                                {
                                    p_moves[z][0]=x+1;
                                    p_moves[z][1]=y-1;
                                    z++;
                                }
                            }
                        }
                        if(x>1&&y>0&&occupied_b[x-2][y-1]==1)
                        {
                            for(int i=0;i<8;i++)
                            {
                                if(apawn[i][0]==x-1&&apawn[i][1]==y&&apawn[i][4]==1)
                                {
                                    p_moves[z][0]=x-1;
                                    p_moves[z][1]=y-1;
                                    z++;
                                }
                            }
                        }
                        if (x<8&&occupied_b[x][y - 2] == 1) {
                            p_moves[z][0] = x + 1;
                            p_moves[z][1] = y - 1;
                            z++;
                        }
                        if (x>1&&occupied_b[x - 2][y - 2] == 1) {
                            p_moves[z][0] = x - 1;
                            p_moves[z][1] = y - 1;
                            z++;
                        }
                    }
                    while (z > -1) {
                        if ((p_moves[z][0] != 0 || p_moves[z][1] != 0)&&occupied_b[p_moves[z][0] - 1][p_moves[z][1] - 1] == 0) {
                            canvas.drawRect(p_moves[z][0] * wr, p_moves[z][1] * hr, (p_moves[z][0] + 1) * wr, (p_moves[z][1] + 1) * hr, p1);
                        }else if ((p_moves[z][0] != 0 || p_moves[z][1] != 0)&&occupied_b[p_moves[z][0] - 1][p_moves[z][1] - 1] == 1) {
                            canvas.drawRect(p_moves[z][0] * wr, p_moves[z][1] * hr, (p_moves[z][0] + 1) * wr, (p_moves[z][1] + 1) * hr, p2);
                        }
                        z--;
                    }
                }
                if(is_a_rook==1){
                    int r_moves[][]=new int[16][2];
                    int rs=-1;
                    int x,y;
                    for(int i=0;i<arc;i++) {
                        if (arook[i][2] == 0 && arook[i][3] == 1) {
                            rs=i;
                        }
                    }
                    if(rs!=-1)
                    {
                        x=arook[rs][0];
                        y=arook[rs][1];
                        for(int i=x-1;i>0;i--)
                        {
                            if(occupied_a[i-1][y-1]==0&&occupied_b[i-1][y-1]==0)
                            {
                                r_moves[z][0]=i;
                                r_moves[z][1]=y;
                                z++;
                                k++;
                            }
                            else
                                break;
                        }
                        if(x>=k+2&&y>=1&&occupied_b[x-k-2][y-1]==1)
                        {
                            r_moves[z][0]=x-k-1;
                            r_moves[z][1]=y;
                            z++;
                        }
                        k=0;
                        for(int i=y-1;i>0;i--)
                        {
                            if(occupied_a[x-1][i-1]==0&&occupied_b[x-1][i-1]==0)
                            {
                                r_moves[z][0]=x;
                                r_moves[z][1]=i;
                                z++;
                                k++;
                            }
                            else
                                break;
                        }
                        if(x>=1&&y>=k+2&&occupied_b[x-1][y-k-2]==1)
                        {
                            r_moves[z][0]=x;
                            r_moves[z][1]=y-k-1;
                            z++;
                        }
                        k=0;
                        for(int i=x+1;i<9;i++)
                        {
                            if(occupied_a[i-1][y-1]==0&&occupied_b[i-1][y-1]==0)
                            {
                                r_moves[z][0]=i;
                                r_moves[z][1]=y;
                                z++;
                                k++;
                            }
                            else
                                break;
                        }
                        if(y>=1&&x+k<8&&occupied_b[x+k][y-1]==1)
                        {
                            r_moves[z][0]=x+k+1;
                            r_moves[z][1]=y;
                            z++;
                        }
                        k=0;
                        for(int i=y+1;i<9;i++)
                        {
                            if(occupied_a[x-1][i-1]==0&&occupied_b[x-1][i-1]==0)
                            {
                                r_moves[z][0]=x;
                                r_moves[z][1]=i;
                                z++;
                                k++;
                            }
                            else
                                break;
                        }
                        if(x>=1&&y+k<8&&occupied_b[x-1][y+k]==1)
                        {
                            r_moves[z][0]=x;
                            r_moves[z][1]=y+k+1;
                            z++;
                        }
                        k=0;
                    }
                    while (z > -1) {
                        if ((r_moves[z][0] != 0 || r_moves[z][1] != 0)&&occupied_b[r_moves[z][0] - 1][r_moves[z][1] - 1] == 0) {
                            canvas.drawRect(r_moves[z][0] * wr, r_moves[z][1] * hr, (r_moves[z][0] + 1) * wr, (r_moves[z][1] + 1) * hr, p1);
                        }else if ((r_moves[z][0] != 0 || r_moves[z][1] != 0)&&occupied_b[r_moves[z][0] - 1][r_moves[z][1] - 1] == 1) {
                            canvas.drawRect(r_moves[z][0] * wr, r_moves[z][1] * hr, (r_moves[z][0] + 1) * wr, (r_moves[z][1] + 1) * hr, p2);
                        }
                        z--;
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
                    if(ks!=-1) {
                        int kx[] = new int[4];
                        int ky[] = new int[4];
                        int x=aknight[ks][0];
                        int y=aknight[ks][1];
                        kx[0] = x - 1;
                        kx[1] = x + 1;
                        kx[2] = x - 2;
                        kx[3] = x + 2;

                        ky[0] = y - 2;
                        ky[1] = y - 1;
                        ky[2] = y + 1;
                        ky[3] = y + 2;

                        if(kx[0]>0&&ky[0]>0&&kx[0]<9&&ky[0]<9&&occupied_a[kx[0]-1][ky[0]-1]==0)
                        {
                            kn_moves[z][0]=kx[0];
                            kn_moves[z][1]=ky[0];
                            z++;
                        }
                        if(kx[1]>0&&ky[0]>0&&kx[1]<9&&ky[0]<9&&occupied_a[kx[1]-1][ky[0]-1]==0)
                        {
                            kn_moves[z][0]=kx[1];
                            kn_moves[z][1]=ky[0];
                            z++;
                        }
                        if(kx[2]>0&&ky[1]>0&&kx[2]<9&&ky[1]<9&&occupied_a[kx[2]-1][ky[1]-1]==0)
                        {
                            kn_moves[z][0]=kx[2];
                            kn_moves[z][1]=ky[1];
                            z++;
                        }
                        if(kx[2]>0&&ky[2]>0&&kx[2]<9&&ky[2]<9&&occupied_a[kx[2]-1][ky[2]-1]==0)
                        {
                            kn_moves[z][0]=kx[2];
                            kn_moves[z][1]=ky[2];
                            z++;
                        }
                        if(kx[0]>0&&ky[3]>0&&kx[0]<9&&ky[3]<9&&occupied_a[kx[0]-1][ky[3]-1]==0)
                        {
                            kn_moves[z][0]=kx[0];
                            kn_moves[z][1]=ky[3];
                            z++;
                        }
                        if(kx[1]>0&&ky[3]>0&&kx[1]<9&&ky[3]<9&&occupied_a[kx[1]-1][ky[3]-1]==0)
                        {
                            kn_moves[z][0]=kx[1];
                            kn_moves[z][1]=ky[3];
                            z++;
                        }
                        if(kx[3]>0&&ky[1]>0&&kx[3]<9&&ky[1]<9&&occupied_a[kx[3]-1][ky[1]-1]==0)
                        {
                            kn_moves[z][0]=kx[3];
                            kn_moves[z][1]=ky[1];
                            z++;
                        }
                        if(kx[3]>0&&ky[2]>0&&kx[3]<9&&ky[2]<9&&occupied_a[kx[3]-1][ky[2]-1]==0)
                        {
                            kn_moves[z][0]=kx[3];
                            kn_moves[z][1]=ky[2];
                            z++;
                        }
                    }
                    while (z > -1) {
                        if ((kn_moves[z][0] != 0 || kn_moves[z][1] != 0)&&occupied_b[kn_moves[z][0] - 1][kn_moves[z][1] - 1] == 0) {
                            canvas.drawRect(kn_moves[z][0] * wr, kn_moves[z][1] * hr, (kn_moves[z][0] + 1) * wr, (kn_moves[z][1] + 1) * hr, p1);
                        }else if ((kn_moves[z][0] != 0 || kn_moves[z][1] != 0)&&occupied_b[kn_moves[z][0] - 1][kn_moves[z][1] - 1] == 1) {
                            canvas.drawRect(kn_moves[z][0] * wr, kn_moves[z][1] * hr, (kn_moves[z][0] + 1) * wr, (kn_moves[z][1] + 1) * hr, p2);
                        }
                        z--;
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
                        int x = abishop[bs][0];
                        int y = abishop[bs][1];
                        //left diagonal "1"-
                        for (int i = x + 1; i < 9; i++) {
                            for (int j = y + 1; j < 9; j++) {
                                if (i - j == x - y) {
                                    if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                        b_moves[z][0] = i;
                                        b_moves[z][1] = j;
                                        z++;
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
                            b_moves[z][0] = x + k + 1;
                            b_moves[z][1] = y + k + 1;
                            z++;
                        }
                        k = 0;
                        con = 0;
                        //left diagonal "2"-
                        for (int i = x - 1; i > 0; i--) {
                            for (int j = y - 1; j > 0; j--) {
                                if (i - j == x - y) {
                                    if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                        b_moves[z][0] = i;
                                        b_moves[z][1] = j;
                                        z++;
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
                            b_moves[z][0] = x - k - 1;
                            b_moves[z][1] = y - k - 1;
                            z++;
                        }
                        k = 0;
                        con = 0;
                        //Right diagonal "1"-
                        for (int i = x + 1; i < 9; i++) {
                            for (int j = y - 1; j > 0; j--) {
                                if (i + j == x + y) {
                                    if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                        b_moves[z][0] = i;
                                        b_moves[z][1] = j;
                                        z++;
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
                            b_moves[z][0] = x + k + 1;
                            b_moves[z][1] = y - k - 1;
                            z++;
                        }
                        k = 0;
                        con = 0;
                        //Right diagonal "2"-
                        for (int i = x - 1; i > 0; i--) {
                            for (int j = y + 1; j < 9; j++) {
                                if (i + j == x + y) {
                                    if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                        b_moves[z][0] = i;
                                        b_moves[z][1] = j;
                                        z++;
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
                        if (x > k + 1 && y + k < 8 && occupied_b[x - k - 2][y + k] == 1) {
                            b_moves[z][0] = x - k - 1;
                            b_moves[z][1] = y + k + 1;
                            z++;
                        }
                        k = 0;
                        con = 0;
                    }
                    while (z > -1) {
                        if ((b_moves[z][0] != 0 || b_moves[z][1] != 0 )&& occupied_b[b_moves[z][0] - 1][b_moves[z][1] - 1] == 0)
                            canvas.drawRect(b_moves[z][0] * wr, b_moves[z][1] * hr, (b_moves[z][0] + 1) * wr, (b_moves[z][1] + 1) * hr, p1);
                        else if ((b_moves[z][0] != 0 || b_moves[z][1] != 0) && occupied_b[b_moves[z][0] - 1][b_moves[z][1] - 1] == 1)
                            canvas.drawRect(b_moves[z][0] * wr, b_moves[z][1] * hr, (b_moves[z][0] + 1) * wr, (b_moves[z][1] + 1) * hr, p2);
                        z--;
                    }
                }
                if(is_a_king==1)
                {
                    int x=aking[0];
                    int y=aking[1];
                    int ok=0;
                    int qwe=0;
                    z=0;
                    int king_moves[][]=new int[10][2];
                    //constant y
                    if(x>1&&y>0&&occupied_a[x-2][y-1]==0)
                    {
                        ok=a_check(x-1,y);
                        if(ok==1)
                        {
                            king_moves[z][0]=x-1;
                            king_moves[z][1]=y;
                            z++;
                        }
                    }
                    if(x<8&&y>0&&occupied_a[x][y-1]==0)
                    {
                        ok=a_check(x+1,y);
                        if(ok==1)
                        {
                            king_moves[z][0]=x+1;
                            king_moves[z][1]=y;
                            z++;
                        }
                    }
                    //constant x
                    if(y>1&&x>0&&occupied_a[x-1][y-2]==0)
                    {
                        ok=a_check(x,y-1);
                        if(ok==1)
                        {
                            king_moves[z][0]=x;
                            king_moves[z][1]=y-1;
                            z++;
                        }
                    }
                    if(y<8&&x>0&&occupied_a[x-1][y]==0)
                    {
                        ok=a_check(x,y+1);
                        if(ok==1)
                        {
                            king_moves[z][0]=x;
                            king_moves[z][1]=y+1;
                            z++;
                        }
                    }
                    //left diagonal 1-
                    if(x>1&&y>1&&occupied_a[x-2][y-2]==0)
                    {
                        ok=a_check(x-1,y-1);
                        if(ok==1)
                        {
                            king_moves[z][0]=x-1;
                            king_moves[z][1]=y-1;
                            z++;
                        }
                    }
                    //left diagonal 2-
                    if(x<8&&y<8&&occupied_a[x][y]==0)
                    {
                        ok=a_check(x+1,y+1);
                        if(ok==1)
                        {
                            king_moves[z][0]=x+1;
                            king_moves[z][1]=y+1;
                            z++;
                        }
                    }
                    //right diagonal 1-
                    if(x>1&&y<8&&occupied_a[x-2][y]==0)
                    {
                        ok=a_check(x-1,y+1);
                        if(ok==1)
                        {
                            king_moves[z][0]=x-1;
                            king_moves[z][1]=y+1;
                            z++;
                        }
                    }
                    //right diagonal 2-
                    if(x<8&&y>1&&occupied_a[x][y-2]==0)
                    {
                        ok=a_check(x+1,y-1);
                        if(ok==1)
                        {
                            king_moves[z][0]=x+1;
                            king_moves[z][1]=y-1;
                            z++;
                        }
                    }//from here
                    if(aking[4]==0)
                    {
                        if(arook[0][4]==0)
                        {
                             for(int i=arook[0][0]+1;i<aking[0];i++)
                             {
                                 if(occupied_a[i-1][y-1]==0&&occupied_b[i-1][y-1]==0&&a_check(i,y)==1)
                                 {
                                     qwe=1;
                                 }
                                 else
                                     {
                                         qwe=-1;
                                         break;
                                     }
                             }
                             if(qwe==1)
                             {
                                 king_moves[z][0]=x-2;
                                 king_moves[z][1]=y;
                                 z++;
                                 qwe=0;
                             }
                        }
                        if(arook[1][4]==0)
                        {
                            for(int i=aking[0]+1;i<arook[1][0];i++)
                            {
                                if(occupied_a[i-1][y-1]==0&&occupied_b[i-1][y-1]==0&&a_check(i,y)==1)
                                {
                                    qwe=1;
                                }
                                else
                                {
                                    qwe=-1;
                                    break;
                                }
                            }
                            if(qwe==1)
                            {
                                king_moves[z][0]=x+2;
                                king_moves[z][1]=y;
                                z++;
                            }
                        }
                    }
                    while (z > -1) {
                        if ((king_moves[z][0] != 0 || king_moves[z][1] != 0)&&occupied_b[king_moves[z][0] - 1][king_moves[z][1] - 1] == 0) {
                            canvas.drawRect(king_moves[z][0] * wr, king_moves[z][1] * hr, (king_moves[z][0] + 1) * wr, (king_moves[z][1] + 1) * hr, p1);

                        }else if ((king_moves[z][0] != 0 || king_moves[z][1] != 0)&&occupied_b[king_moves[z][0] - 1][king_moves[z][1] - 1] == 1) {
                            canvas.drawRect(king_moves[z][0] * wr, king_moves[z][1] * hr, (king_moves[z][0] + 1) * wr, (king_moves[z][1] + 1) * hr, p2);
                        }
                        z--;
                    }
                }
            }
                if(b_check(bking[0],bking[1])==0)
                {
                    p1.setColor(Color.BLACK);
                    canvas.drawText("B king in CHECK",35 ,70,p1);
                }
                if(a_check(aking[0],aking[1])==0)
                {
                    p1.setColor(Color.BLACK);
                    canvas.drawText("A king in CHECK",35 ,70,p1);
                    }
                    if (m % 4 == 3) {
                        p1.setColor(Color.parseColor("#4b06f90e"));
                        p1.setStyle(Paint.Style.FILL);
                        p2.setColor(Color.parseColor("#4be70a1d"));
                        p2.setStyle(Paint.Style.FILL);
                        con = 0;
                        int z = 0;
                        k = 0;
                        int qs = -1;
                        if(is_b_queen==1)
                        {
                        for (int i = 0; i < bqc; i++) {
                            if (bqueen[i][2] == 0 && bqueen[i][3] == 1) {
                                qs = i;
                            }
                        }
                        if (qs != -1) {
                            int q_moves[][] = new int[32][2];
                            int x = bqueen[qs][0];
                            int y = bqueen[qs][1];
                            //constant y-
                            for (int i = x + 1; i < 9; i++) {
                                if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                                    q_moves[z][0] = i;
                                    q_moves[z][1] = y;
                                    z++;
                                    k++;
                                } else
                                    break;
                            }
                            if (y > 0 && x + k < 8 && occupied_a[x + k][y - 1] == 1) {
                                q_moves[z][0] = x + k + 1;
                                q_moves[z][1] = y;
                                z++;
                            }
                            k = 0;
                            for (int i = x - 1; i > 0; i--) {

                                if (occupied_a[i - 1][y - 1] == 0 && occupied_b[i - 1][y - 1] == 0) {
                                    q_moves[z][0] = i;
                                    q_moves[z][1] = y;
                                    z++;
                                    k++;
                                } else
                                    break;
                            }
                            if (x > k + 1 && y > 0 && occupied_a[x - k - 2][y - 1] == 1) {
                                q_moves[z][0] = x - k - 1;
                                q_moves[z][1] = y;
                                z++;
                            }
                            k = 0;
                            //left diagonal "1"-
                            for (int i = x + 1; i < 9; i++) {
                                for (int j = y + 1; j < 9; j++) {
                                    if (i - j == x - y) {
                                        if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                            q_moves[z][0] = i;
                                            q_moves[z][1] = j;
                                            z++;
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
                            if (x + k < 8 && y + k < 8 && occupied_a[x + k][y + k] == 1) {
                                q_moves[z][0] = x + k + 1;
                                q_moves[z][1] = y + k + 1;
                                z++;
                            }
                            k = 0;
                            con = 0;
                            //left diagonal "2"-
                            for (int i = x - 1; i > 0; i--) {
                                for (int j = y - 1; j > 0; j--) {
                                    if (i - j == x - y) {
                                        if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                            q_moves[z][0] = i;
                                            q_moves[z][1] = j;
                                            z++;
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
                            if (x > k + 1 && y > k + 1 && occupied_a[x - k - 2][y - k - 2] == 1) {
                                q_moves[z][0] = x - k - 1;
                                q_moves[z][1] = y - k - 1;
                                z++;
                            }
                            k = 0;
                            con = 0;
                            //Right diagonal "1"-
                            for (int i = x + 1; i < 9; i++) {
                                for (int j = y - 1; j > 0; j--) {
                                    if (i + j == x + y) {
                                        if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                            q_moves[z][0] = i;
                                            q_moves[z][1] = j;
                                            z++;
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
                            if (y > k + 1 && x + k < 8 && occupied_a[x + k][y - k - 2] == 1) {
                                q_moves[z][0] = x + k + 1;
                                q_moves[z][1] = y - k - 1;
                                z++;
                            }
                            k = 0;
                            con = 0;
                            //Right diagonal "2"-
                            for (int i = x - 1; i > 0; i--) {
                                for (int j = y + 1; j < 9; j++) {
                                    if (i + j == x + y) {
                                        if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                            q_moves[z][0] = i;
                                            q_moves[z][1] = j;
                                            z++;
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
                            if (x > k + 1 && y + k < 8 && occupied_a[x - k - 2][y + k] == 1) {
                                q_moves[z][0] = x - k - 1;
                                q_moves[z][1] = y + k + 1;
                                z++;
                            }
                            k = 0;
                            con = 0;
                            //constant x-
                            for (int i = y + 1; i < 9; i++) {
                                if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                                    q_moves[z][0] = x;
                                    q_moves[z][1] = i;
                                    z++;
                                    k++;
                                } else
                                    break;
                            }
                            if (y + k < 8 && x > 0 && occupied_a[x - 1][y + k] == 1) {
                                q_moves[z][0] = x;
                                q_moves[z][1] = y + k + 1;
                                z++;
                            }
                            k = 0;
                            for (int i = y - 1; i > 0; i--) {
                                if (occupied_a[x - 1][i - 1] == 0 && occupied_b[x - 1][i - 1] == 0) {
                                    q_moves[z][0] = x;
                                    q_moves[z][1] = i;
                                    z++;
                                    k++;
                                } else
                                    break;
                            }
                            if (y > k + 1 && x > 0 && occupied_a[x - 1][y - k - 2] == 1) {
                                q_moves[z][0] = x;
                                q_moves[z][1] = y - k - 1;
                                z++;
                            }
                            k = 0;
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
                        if(is_b_pawn==1) {
                            int ps = -1;
                            int x, y;
                            int p_moves[][] = new int[6][2];
                            for (int i = 0; i < 8; i++) {
                                if (bpawn[i][2] == 0 && bpawn[i][3] == 1) {
                                    ps = i;
                                }
                            }
                            if (ps != -1) {
                                x = bpawn[ps][0];
                                y = bpawn[ps][1];
                                if (occupied_a[x - 1][y] == 0 && occupied_b[x - 1][y] == 0) {
                                    p_moves[z][0] = x;
                                    p_moves[z][1] = y + 1;
                                    z++;
                                    if (bpawn[ps][4] == 0) {
                                        if (occupied_a[x - 1][y + 1] == 0 && occupied_b[x - 1][y + 1] == 0) {
                                            p_moves[z][0] = x;
                                            p_moves[z][1] = y + 2;
                                            z++;
                                        }
                                    }
                                }
                                if (x < 8 && y > 0 && occupied_a[x][y - 1] == 1) {
                                    for (int i = 0; i < 8; i++) {
                                        if (apawn[i][0] == x + 1 && apawn[i][1] == y && apawn[i][4] == 1) {
                                            p_moves[z][0] = x + 1;
                                            p_moves[z][1] = y + 1;
                                            z++;
                                        }
                                    }
                                }
                                if (x > 1 && y > 0 && occupied_a[x - 2][y - 1] == 1) {
                                    for (int i = 0; i < 8; i++) {
                                        if (apawn[i][0] == x - 1 && apawn[i][1] == y && apawn[i][4] == 1) {
                                            p_moves[z][0] = x - 1;
                                            p_moves[z][1] = y + 1;
                                            z++;
                                        }
                                    }
                                }
                                if (x < 8 && y < 8 && occupied_a[x][y] == 1) {
                                    p_moves[z][0] = x + 1;
                                    p_moves[z][1] = y + 1;
                                    z++;
                                }
                                if (x > 1 && y < 8 && occupied_a[x - 2][y] == 1) {
                                    p_moves[z][0] = x - 1;
                                    p_moves[z][1] = y + 1;
                                    z++;
                                }
                            }
                            while (z > -1) {
                                if ((p_moves[z][0] != 0 || p_moves[z][1] != 0) && occupied_a[p_moves[z][0] - 1][p_moves[z][1] - 1] == 0) {
                                    canvas.drawRect(p_moves[z][0] * wr, p_moves[z][1] * hr, (p_moves[z][0] + 1) * wr, (p_moves[z][1] + 1) * hr, p1);
                                }
                                else if ((p_moves[z][0] != 0 || p_moves[z][1] != 0) && occupied_a[p_moves[z][0] - 1][p_moves[z][1] - 1] == 1) {
                                    canvas.drawRect(p_moves[z][0] * wr, p_moves[z][1] * hr, (p_moves[z][0] + 1) * wr, (p_moves[z][1] + 1) * hr, p2);
                                }
                                z--;
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
                            if(rs!=-1)
                            {
                                x=brook[rs][0];
                                y=brook[rs][1];
                                for(int i=x-1;i>0;i--)
                                {
                                    if(occupied_a[i-1][y-1]==0&&occupied_b[i-1][y-1]==0)
                                    {
                                        r_moves[z][0]=i;
                                        r_moves[z][1]=y;
                                        z++;
                                        k++;
                                    }
                                    else
                                        break;
                                }
                                if(x>k+1&&y>0&&occupied_a[x-k-2][y-1]==1)
                                {
                                    r_moves[z][0]=x-k-1;
                                    r_moves[z][1]=y;
                                    z++;
                                }
                                k=0;
                                for(int i=y-1;i>0;i--)
                                {
                                    if(occupied_a[x-1][i-1]==0&&occupied_b[x-1][i-1]==0)
                                    {
                                        r_moves[z][0]=x;
                                        r_moves[z][1]=i;
                                        z++;
                                        k++;
                                    }
                                    else
                                        break;
                                }
                                if(x>0&&y>k+1&&occupied_a[x-1][y-k-2]==1)
                                {
                                    r_moves[z][0]=x;
                                    r_moves[z][1]=y-k-1;
                                    z++;
                                }
                                k=0;
                                for(int i=x+1;i<9;i++)
                                {
                                    if(occupied_a[i-1][y-1]==0&&occupied_b[i-1][y-1]==0)
                                    {
                                        r_moves[z][0]=i;
                                        r_moves[z][1]=y;
                                        z++;
                                        k++;
                                    }
                                    else
                                        break;
                                }
                                if(y>0&&x+k<8&&occupied_a[x+k][y-1]==1)
                                {
                                    r_moves[z][0]=x+k+1;
                                    r_moves[z][1]=y;
                                    z++;
                                }
                                k=0;
                                for(int i=y+1;i<9;i++)
                                {
                                    if(occupied_a[x-1][i-1]==0&&occupied_b[x-1][i-1]==0)
                                    {
                                        r_moves[z][0]=x;
                                        r_moves[z][1]=i;
                                        z++;
                                        k++;
                                    }
                                    else
                                        break;
                                }
                                if(x>0&&y+k<8&&occupied_a[x-1][y+k]==1)
                                {
                                    r_moves[z][0]=x;
                                    r_moves[z][1]=y+k+1;
                                    z++;
                                }
                                k=0;
                            }
                            while (z > -1) {
                                if ((r_moves[z][0] != 0 || r_moves[z][1] != 0)&&occupied_a[r_moves[z][0]-1][r_moves[z][1]-1]==0) {
                                    canvas.drawRect(r_moves[z][0] * wr, r_moves[z][1] * hr, (r_moves[z][0] + 1) * wr, (r_moves[z][1] + 1) * hr, p1);
                                }if ((r_moves[z][0] != 0 || r_moves[z][1] != 0)&&occupied_a[r_moves[z][0]-1][r_moves[z][1]-1]==1) {
                                    canvas.drawRect(r_moves[z][0] * wr, r_moves[z][1] * hr, (r_moves[z][0] + 1) * wr, (r_moves[z][1] + 1) * hr, p2);
                                }
                                z--;
                            }
                        }
                        if(is_b_knight==1)
                        {
                            int ks=-1;
                            int kn_moves[][]=new int[10][2];
                            for(int i=0;i<bknc;i++)
                            {
                                if(bknight[i][3]==1&&bknight[i][2]==0)
                                {
                                    ks=i;
                                }
                            }
                            if(ks!=-1) {
                                int kx[] = new int[4];
                                int ky[] = new int[4];
                                int x=bknight[ks][0];
                                int y=bknight[ks][1];
                                kx[0] = x - 1;
                                kx[1] = x + 1;
                                kx[2] = x - 2;
                                kx[3] = x + 2;

                                ky[0] = y - 2;
                                ky[1] = y - 1;
                                ky[2] = y + 1;
                                ky[3] = y + 2;

                                if(kx[0]>0&&ky[0]>0&&kx[0]<9&&ky[0]<9&&occupied_b[kx[0]-1][ky[0]-1]==0)
                                {
                                    kn_moves[z][0]=kx[0];
                                    kn_moves[z][1]=ky[0];
                                    z++;
                                }
                                if(kx[1]>0&&ky[0]>0&&kx[1]<9&&ky[0]<9&&occupied_b[kx[1]-1][ky[0]-1]==0)
                                {
                                    kn_moves[z][0]=kx[1];
                                    kn_moves[z][1]=ky[0];
                                    z++;
                                }
                                if(kx[2]>0&&ky[1]>0&&kx[2]<9&&ky[1]<9&&occupied_b[kx[2]-1][ky[1]-1]==0)
                                {
                                    kn_moves[z][0]=kx[2];
                                    kn_moves[z][1]=ky[1];
                                    z++;
                                }
                                if(kx[2]>0&&ky[2]>0&&kx[2]<9&&ky[2]<9&&occupied_b[kx[2]-1][ky[2]-1]==0)
                                {
                                    kn_moves[z][0]=kx[2];
                                    kn_moves[z][1]=ky[2];
                                    z++;
                                }
                                if(kx[0]>0&&ky[3]>0&&kx[0]<9&&ky[3]<9&&occupied_b[kx[0]-1][ky[3]-1]==0)
                                {
                                    kn_moves[z][0]=kx[0];
                                    kn_moves[z][1]=ky[3];
                                    z++;
                                }
                                if(kx[1]>0&&ky[3]>0&&kx[1]<9&&ky[3]<9&&occupied_b[kx[1]-1][ky[3]-1]==0)
                                {
                                    kn_moves[z][0]=kx[1];
                                    kn_moves[z][1]=ky[3];
                                    z++;
                                }
                                if(kx[3]>0&&ky[1]>0&&kx[3]<9&&ky[1]<9&&occupied_b[kx[3]-1][ky[1]-1]==0)
                                {
                                    kn_moves[z][0]=kx[3];
                                    kn_moves[z][1]=ky[1];
                                    z++;
                                }
                                if(kx[3]>0&&ky[2]>0&&kx[3]<9&&ky[2]<9&&occupied_b[kx[3]-1][ky[2]-1]==0)
                                {
                                    kn_moves[z][0]=kx[3];
                                    kn_moves[z][1]=ky[2];
                                    z++;
                                }
                            }

                            while (z > -1) {
                                if ((kn_moves[z][0] != 0 || kn_moves[z][1] != 0)&&occupied_a[kn_moves[z][0]-1][kn_moves[z][1]-1]==0)
                                {
                                    canvas.drawRect(kn_moves[z][0] * wr, kn_moves[z][1] * hr, (kn_moves[z][0] + 1) * wr, (kn_moves[z][1] + 1) * hr, p1);
                                }
                                else if ((kn_moves[z][0] != 0 || kn_moves[z][1] != 0)&&occupied_a[kn_moves[z][0]-1][kn_moves[z][1]-1]==1)
                                {
                                    canvas.drawRect(kn_moves[z][0] * wr, kn_moves[z][1] * hr, (kn_moves[z][0] + 1) * wr, (kn_moves[z][1] + 1) * hr, p2);
                                 }
                                z--;
                            }
                        }
                        if(is_b_bishop==1)
                        {
                            int bs=-1;
                            int b_moves [][]=new int [16][2];
                            for(int i=0;i<bbc;i++)
                            {
                                if(bbishop[i][3]==1&&bbishop[i][2]==0)
                                {
                                    bs=i;
                                }
                            }
                            if(bs!=-1)
                            {
                                int x=bbishop[bs][0];
                                int y=bbishop[bs][1];
                                //left diagonal "1"-
                                for (int i = x + 1; i < 9; i++) {
                                    for (int j = y + 1; j < 9; j++) {
                                        if (i - j == x-y) {
                                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                                b_moves[z][0] = i;
                                                b_moves[z][1] = j;
                                                z++;
                                                k++;
                                            }
                                            else {
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
                                if(x+k<8&&y+k<8&&occupied_a[x+k][y+k]==1)
                                {
                                    b_moves[z][0] = x+k+1;
                                    b_moves[z][1] = y+k+1;
                                    z++;
                                }
                                k=0;
                                con=0;
                                //left diagonal "2"-
                                for (int i = x - 1; i > 0; i--) {
                                    for (int j = y - 1; j > 0; j--) {
                                        if (i - j == x-y) {
                                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                                b_moves[z][0] = i;
                                                b_moves[z][1] = j;
                                                z++;
                                                k++;
                                            }
                                            else {
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
                                if(x>k+1&&y>k+1&&occupied_a[x-k-2][y-k-2]==1)
                                {
                                    b_moves[z][0] = x-k-1;
                                    b_moves[z][1] = y-k-1;
                                    z++;
                                }
                                k=0;
                                con=0;
                                //Right diagonal "1"-
                                for (int i = x + 1; i < 9; i++) {
                                    for (int j = y - 1; j > 0; j--) {
                                        if (i + j == x+y) {
                                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                                b_moves[z][0] = i;
                                                b_moves[z][1] = j;
                                                z++;
                                                k++;
                                            }
                                            else
                                            {
                                                con=-1;
                                                break;
                                            }
                                        }
                                    }
                                    if(con==-1) {
                                        break;
                                    }
                                }
                                if(y>k+1&&x+k<8&&occupied_a[x+k][y-k-2]==1)
                                {
                                    b_moves[z][0] = x+k+1;
                                    b_moves[z][1] = y-k-1;
                                    z++;
                                }
                                k=0;
                                con=0;
                                //Right diagonal "2"-
                                for (int i = x - 1; i > 0; i--) {
                                    for (int j = y + 1; j < 9; j++) {
                                        if (i + j == x+y) {
                                            if (occupied_a[i - 1][j - 1] == 0 && occupied_b[i - 1][j - 1] == 0) {
                                                b_moves[z][0] = i;
                                                b_moves[z][1] = j;
                                                z++;
                                                k++;
                                            }
                                            else
                                            {
                                                con=-1;
                                                break;
                                            }
                                        }
                                    }
                                    if(con==-1)
                                    {
                                        break;
                                    }
                                }
                                if(x>k+1&&y+k<8&&occupied_a[x-k-2][y+k]==1)
                                {
                                    b_moves[z][0] = x-k-1;
                                    b_moves[z][1] = y+k+1;
                                    z++;
                                }
                                k=0;
                                con=0;
                            }
                            while (z > -1) {
                                if ((b_moves[z][0] != 0 || b_moves[z][1] != 0)&&occupied_a[b_moves[z][0]-1][b_moves[z][1]-1]==0) {
                                    canvas.drawRect(b_moves[z][0] * wr, b_moves[z][1] * hr, (b_moves[z][0] + 1) * wr, (b_moves[z][1] + 1) * hr, p1);
                                }else if ((b_moves[z][0] != 0 || b_moves[z][1] != 0)&&occupied_a[b_moves[z][0]-1][b_moves[z][1]-1]==1) {
                                    canvas.drawRect(b_moves[z][0] * wr, b_moves[z][1] * hr, (b_moves[z][0] + 1) * wr, (b_moves[z][1] + 1) * hr, p2);
                                }
                                z--;
                            }
                        }
                        if(is_b_king==1)
                        {
                            int x=bking[0];
                            int y=bking[1];
                            int ok=0;
                            int qwe=0;
                            z=0;
                            int king_moves[][]=new int[10][2];
                            //constant y
                            if(x>1&&y>0&&occupied_b[x-2][y-1]==0)
                            {
                                ok=b_check(x-1,y);
                                if(ok==1)
                                {
                                    king_moves[z][0]=x-1;
                                    king_moves[z][1]=y;
                                    z++;
                                }
                            }
                            if(x<8&&y>0&&occupied_b[x][y-1]==0)
                            {
                                ok=b_check(x+1,y);
                                if(ok==1)
                                {
                                    king_moves[z][0]=x+1;
                                    king_moves[z][1]=y;
                                    z++;
                                }
                            }
                            //constant x
                            if(y>1&&x>0&&occupied_b[x-1][y-2]==0)
                            {
                                ok=b_check(x,y-1);
                                if(ok==1)
                                {
                                    king_moves[z][0]=x;
                                    king_moves[z][1]=y-1;
                                    z++;
                                }
                            }
                            if(y<8&&x>0&&occupied_b[x-1][y]==0)
                            {
                                ok=b_check(x,y+1);
                                if(ok==1)
                                {
                                    king_moves[z][0]=x;
                                    king_moves[z][1]=y+1;
                                    z++;
                                }
                            }
                            //left diagonal 1-
                            if(x>1&&y>1&&occupied_b[x-2][y-2]==0)
                            {
                                ok=b_check(x-1,y-1);
                                if(ok==1)
                                {
                                    king_moves[z][0]=x-1;
                                    king_moves[z][1]=y-1;
                                    z++;
                                }
                            }
                            //left diagonal 2-
                            if(x<8&&y<8&&occupied_b[x][y]==0)
                            {
                                ok=b_check(x+1,y+1);
                                if(ok==1)
                                {
                                    king_moves[z][0]=x+1;
                                    king_moves[z][1]=y+1;
                                    z++;
                                }
                            }
                            //right diagonal 1-
                            if(x>1&&y<8&&occupied_b[x-2][y]==0)
                            {
                                ok=b_check(x-1,y+1);
                                if(ok==1)
                                {
                                    king_moves[z][0]=x-1;
                                    king_moves[z][1]=y+1;
                                    z++;
                                }
                            }
                            //right diagonal 2-
                            if(x<8&&y>1&&occupied_b[x][y-2]==0)
                            {
                                ok=b_check(x+1,y-1);
                                if(ok==1)
                                {
                                    king_moves[z][0]=x+1;
                                    king_moves[z][1]=y-1;
                                    z++;
                                }
                            }
                            //castling
                            if(bking[4]==0)
                            {
                                if(brook[0][4]==0)
                                {
                                    for(int i=brook[0][0]+1;i<bking[0];i++)
                                    {
                                        if(occupied_a[i-1][y-1]==0&&occupied_b[i-1][y-1]==0&&b_check(i,y)==1)
                                        {
                                            qwe=1;
                                        }
                                        else
                                        {
                                            qwe=-1;
                                            break;
                                        }
                                    }
                                    if(qwe==1)
                                    {
                                        king_moves[z][0]=x-2;
                                        king_moves[z][1]=y;
                                        z++;
                                        qwe=0;
                                    }
                                }
                                if(brook[1][4]==0)
                                {
                                    for(int i=bking[0]+1;i<brook[1][0];i++)
                                    {
                                        if(occupied_a[i-1][y-1]==0&&occupied_b[i-1][y-1]==0&&b_check(i,y)==1)
                                        {
                                            qwe=1;
                                        }
                                        else
                                        {
                                            qwe=-1;
                                            break;
                                        }
                                    }
                                    if(qwe==1)
                                    {
                                        king_moves[z][0]=x+2;
                                        king_moves[z][1]=y;
                                        z++;
                                    }
                                }
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
                {
                    p1.setColor(Color.BLACK);
                    if (m % 4 == 0)
                        canvas.drawText("A's Turn", 10, 50, p1);
                    if (m % 4 == 1)
                        canvas.drawText("A's Turn", 10, 50, p1);
                    if (m % 4 == 2)
                        canvas.drawText("B's Turn", 10, 50, p1);
                    if (m % 4 == 3)
                        canvas.drawText("B's Turn", 10, 50, p1);
                }
                for(int i=0;i<arc;i++) {
                    if (arook[i][2] == 0) {
                        canvas.drawBitmap(ar, (arook[i][0]) * wr, (arook[i][1]) * hr, null);
                    }
                }
            for(int i=0;i<brc;i++) {
                if (brook[i][2] == 0) {
                    canvas.drawBitmap(br, (brook[i][0]) * wr, (brook[i][1]) * hr, null);
                }
            }
            for(int i=0;i<aknc;i++) {
                if (aknight[i][2] == 0) {
                    canvas.drawBitmap(akn, (aknight[i][0]) * wr, (aknight[i][1]) * hr, null);
                }
            }
            for(int i=0;i<bknc;i++) {
                if (bknight[i][2] == 0) {
                    canvas.drawBitmap(bkn, (bknight[i][0]) * wr, (bknight[i][1]) * hr, null);
                }
            }
            for(int i=0;i<abc;i++) {
                if (abishop[i][2] == 0) {
                    canvas.drawBitmap(ab, (abishop[i][0]) * wr, (abishop[i][1]) * hr, null);
                }
            }
            for(int i=0;i<bbc;i++) {
                if (bbishop[i][2] == 0) {
                    canvas.drawBitmap(bb, (bbishop[i][0]) * wr, (bbishop[i][1]) * hr, null);
                }
            }
            for(int i=0;i<aqc;i++) {
                if (aqueen[i][2] == 0) {
                    canvas.drawBitmap(aq, aqueen[i][0] * wr, aqueen[i][1] * hr, null);
                }
            }
            for(int i=0;i<bqc;i++) {

                if (bqueen[i][2] == 0) {
                    canvas.drawBitmap(bq, bqueen[i][0] * wr, bqueen[i][1] * hr, null);
                }
            }
            if (aking[2] == 0) {
                canvas.drawBitmap(ak, aking[0] * wr, aking[1] * hr, null);
            }
            if (bking[2] == 0) {
                canvas.drawBitmap(bk, bking[0] * wr, bking[1] * hr, null);
            }
                canvas.drawText("Init" + " " + px1 + " " + py1 + " " + "Fin" + " " + px + " " + py + " " + con, 10, 10, p1);
            }
    }

}