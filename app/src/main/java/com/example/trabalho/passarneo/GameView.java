package com.example.trabalho.passarneo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
  // ----VARIAVEIS----

    //Canvas
    private int canvasWidht;
    private int canvasHeight;

   //Personagem
    //private Bitmap passarinho;
    private Bitmap passarinho[] =  new Bitmap[2];
    private int passarinhoX = 10;
    private int passarinhoY;
    private int passarinhoVelocidade;

    //Bolinha Verde
    private int verdeX;
    private int verdeY;
    private int verdeVelocidade = 15;
    private Paint verdePaint = new Paint();

    //Inimigo
    private int inimigoX;
    private int inimigoY;
    private int inimigoVelocidade = 20;
    private Paint inimigoPaint = new Paint();

    //Background
    private Bitmap background;

    //Pontuacao
    private Paint pontuacaoPaint = new Paint();
    private int pontuacao;

    //Level
    private Paint levelPaint = new Paint();

    //Vida
    private Bitmap vida[] = new Bitmap[2];
    private int contador_vida;

    //Checar Status
    private boolean touch_flg = false;

    public GameView(Context context) {
        super(context);

        //----CARREGAR OS PNGS DA PASTA DRAWABLE----

        //pega o arquivo png do Personagem
        passarinho[0] = BitmapFactory.decodeResource(getResources(),R.drawable.passarinho1);
        passarinho[1] = BitmapFactory.decodeResource(getResources(),R.drawable.passarinho2);

        //pega o arquivo png do Background
        background = BitmapFactory.decodeResource(getResources(),R.drawable.bg);

        //Bolinha Verde
        verdePaint.setColor(Color.GREEN);
        verdePaint.setAntiAlias(false);

        //Inimigo
        inimigoPaint.setColor(Color.BLACK);
        verdePaint.setAntiAlias(false);

        //cria a Pontuacao
        pontuacaoPaint.setColor(Color.BLACK);
        pontuacaoPaint.setTextSize(32);
        pontuacaoPaint.setTypeface(Typeface.DEFAULT_BOLD);
        pontuacaoPaint.setAntiAlias(true);

        //cria na tela a quantidade de Level que o Jogador esta
        levelPaint.setColor(Color.BLACK);
        levelPaint.setTextSize(32);
        levelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        levelPaint.setTextAlign(Paint.Align.CENTER);
        levelPaint.setAntiAlias(true);

        //cria na tela a quantidade de vida
        vida[0] = BitmapFactory.decodeResource(getResources(),R.drawable.heart);
        vida[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_g);


        // ----SETAR OS VALORES INICIAIS DAS VARIAVEIS
        //Posicao Inicial
        passarinhoY = 500;
        pontuacao = 0;
        contador_vida = 3;
    }




    @Override
    protected void onDraw(Canvas canvas) {
        canvasWidht = canvas.getWidth();
        canvasHeight=canvas.getHeight();


        //----DESENHAR----

        //desenha o Background
        canvas.drawBitmap(background,0,0,null);

        //desenha o Personagem
        int minimoYPassarinho = passarinho[0].getHeight();
        int maximoYPassarinho = canvasHeight - passarinho[0].getHeight() * 3;
        passarinhoY += passarinhoVelocidade;
        if(passarinhoY <minimoYPassarinho)
            passarinhoY = minimoYPassarinho;
        if(passarinhoY > maximoYPassarinho)
            passarinhoY = maximoYPassarinho;
        passarinhoVelocidade += 2;

        //desenha a Pontuação
        canvas.drawText("Pontuacao:  " + pontuacao,20,60,pontuacaoPaint);

        //desenha o Level
        canvas.drawText("Level 1", canvasWidht/2 , 60,levelPaint);

        //desenha as vidas
        for(int i = 0; i< 3 ;i++){
            int x = (int) (560 + vida[0].getWidth()*1.5*i);
            int y = 30;
            if(i<contador_vida){
                canvas.drawBitmap(vida[0],x,y,null);
            }  else {
                canvas.drawBitmap(vida[1],x,y,null);
            }
        }


        //----ANIMACAO BATER ASA PASSARINHO----
        if (touch_flg){
            canvas.drawBitmap(passarinho[1],passarinhoX,passarinhoY,null);
            touch_flg = false; }
        else {
            canvas.drawBitmap(passarinho[0],passarinhoX,passarinhoY,null); }


        //----SPAWN BOLINHA VERDE----
        verdeX -= verdeVelocidade;
        if(colisao(verdeX, verdeY)){
           pontuacao += 10;
           verdeX = -100;

        }
        if (verdeX < 0){
            //Spawna a Bolinha 20 pixels atrás da largura do Canvas
            verdeX = canvasWidht + 20;
            verdeY = (int) Math.floor(Math.random() * (maximoYPassarinho - minimoYPassarinho)) + minimoYPassarinho;
        }canvas.drawCircle(verdeX,verdeY,10, verdePaint);



        //----SPAWN INIMIGO
        inimigoX -= inimigoVelocidade;
        if(colisao(inimigoX,inimigoY)){
            inimigoX = -100;
            contador_vida --;
            if(contador_vida == 0){
                //TestarGameOver
                Log.v("MESSAGE","GAME OVER"); } }
        if(inimigoX < 0){
           inimigoX = canvasWidht + 200;
           inimigoY = (int) Math.floor(Math.random() * (maximoYPassarinho - minimoYPassarinho)) + minimoYPassarinho; }
         canvas.drawCircle(inimigoX,inimigoY,20, inimigoPaint); }



         //----COLISAO
         public boolean colisao(int x, int y){
        if(passarinhoX < x && x < (passarinhoX + passarinho[0].getWidth()) && passarinhoY < y && y<(passarinhoY + passarinho[0].getHeight())){
            return true;
            }
            return false; }



            //----EVENTO DE TOCAR NA TELA
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch_flg = true;
            passarinhoVelocidade = -20;
        }
        return true;
    }
}


