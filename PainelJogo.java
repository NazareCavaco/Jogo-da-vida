import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import javax.swing.JPanel;


public class PainelJogo extends JPanel implements Runnable{
    private static final int TOTALBICHOS = 50;
    public int contabichos=0;
    public int bichosvivos=0;
    private int totalerva=0;
    final int OriginalTileSize =16;
    final int scale=3;
    public final int tileSize = OriginalTileSize*scale;
    final int maxScreenCol=16;
    final int maxScreenRow=12;
    final int screenWidth= tileSize*maxScreenCol;
    final int screenHeight= tileSize*maxScreenRow;
    final int FPS=30;
    String Informacao="Bichos";
    KeyHandler keyH= new KeyHandler();
    
    

    Thread gameThread;
    List<Criatura> Bichos = new ArrayList<>();
    Predador predador1 = new Predador("predador1","papatudo.jpg");
    Predador predador2 = new Predador("predador2","papatudo2.jpg");
    papatudo mau= new papatudo("Bichomau");

    public PainelJogo(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);
    }
    char[][] map = new char[screenWidth][screenHeight];
    int[][] map2 = new int[screenWidth][screenHeight];

    void conta_erva(){
        totalerva=0;
        for (int x=0;x<255;x++)
        for (int y=0;y<192;y++){
            if (map2[x][y]>0) totalerva++;
        }
    }
    void conta_bicho(){
        bichosvivos=0;
        for (Criatura c: Bichos){
            if (c.energia>1) bichosvivos++;
            //if (c.energia==0) {System.out.println("\n"+c.hashCode());Bichos.remove(c);}
        }
        
    }

    void init(){
        Random r = new Random();
        int temp;
        for (int x=1;x<255;x++)
        for (int y=1;y<192;y++){
            temp=r.nextInt(100);
            if(temp>95) {
                    if (temp==99) map2[x][y]=100;
                    else map2[x][y]=1;
                    map2[x+1][y]=1;
                    map2[x-1][y]=1;
                    map2[x][y+1]=1;
                    map2[x][y-1]=1;
                }
        }

        for (int x=0;x<screenWidth;x++)
            for (int y=0;y<screenHeight;y++){
                map[x][y]=' ';
            }
    }
    void nascer_erva(){
        Random r = new Random();
        for (int x=1;x<254;x++)
        for (int y=1;y<193;y++){
          int temp=r.nextInt(10000);
          if(map[x][y]==' '){
            if(map2[x][y]>0&&map2[x][y]<10) map2[x][y]++;
            else if(temp==900) map2[x][y]++;
            if (map2[x][y]>250)map2[x][y]=0;
            if(temp<150)
          if (map2[x][y]>0&&
              map2[x+1][y]>0&&
              map2[x-1][y]>0&&
              map2[x][y+1]>0&&
              map2[x][y-1]>0) {
                map2[x+1][y+1]++;
                map2[x+1][y-1]++;
                map2[x-1][y+1]++;
                map2[x-1][y-1]++;
                }
          }

        }
    }

    public void  startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run(){
        boolean sexo=false;
        double drawInterval=1000000000/FPS;
        double delta=0;
        long lastTime= System.nanoTime();
        long currentTime;
        long timer=0;
        int drawCount=0;
        init();

        for (int i=0;i<TOTALBICHOS;i++){
            Criatura bicho= new Criatura("total"+i);
            Bichos.add(bicho);
            map[bicho.xpos][bicho.ypos]='X';
            contabichos++;
        }
        map[predador1.xpos][predador1.ypos]='C';
        map[predador2.xpos][predador2.ypos]='C';
        map[mau.xpos][mau.ypos]='C';

        while (gameThread!= null){
           
            currentTime = System.nanoTime();

            delta += (currentTime -lastTime)/drawInterval;
            timer += (currentTime -lastTime);
            lastTime=currentTime;
            if (delta>=1)
            {
              // 1 : Atualizar as posições do objetos

              for(ListIterator<Criatura> it = Bichos.listIterator(); it.hasNext(); ) {
                Criatura c = it.next();
                sexo=c.update(map,map2);
                if (sexo&&c.energia>110) 
                {
                    Criatura baby = new Criatura("sexy");
                    map[baby.xpos][baby.ypos]='X';
                    it.add(baby);;
                    contabichos++;
                }
                if(map2[c.xpos][c.ypos]>0) c.energia+=map2[c.xpos][c.ypos];
                map2[c.xpos][c.ypos]=0;
                if(c.energia<1) {
                    it.remove();
                    map[c.xpos][c.ypos]=' ';
                }
            }
            predador1.update(map,map2,Bichos);
            predador2.update(map,map2,Bichos);
            //mau.update(map,map2,Bichos);
            mau.mover(map,map2,Bichos,keyH);



/*              for (Criatura c: Bichos) {
                sexo=c.update(map,map2);
                if(map2[c.xpos][c.ypos]>0) c.energia+=map2[c.xpos][c.ypos];
                map2[c.xpos][c.ypos]=0;
            }*/


              //2 : desenhar novamente o ecrã
              nascer_erva();    
              repaint();
              //atualiza o tempo
              delta--;
              drawCount++;
            }
            if (timer>=1000000000){
                //System.out.println("FPS: "+drawCount);
                conta_erva();
                conta_bicho();
                Informacao="Bichos vivos: "+bichosvivos+" erva: " +totalerva+" bichos totais: "+
                contabichos+" papados: "+
                (predador1.bichospapados+predador2.bichospapados+
                mau.bichospapados);
                //System.out.println(Informacao);
                drawCount=0;
                timer=0;
            }


        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        for (int x=0;x<screenWidth;x++)
        for (int y=0;y<screenHeight;y++){
            if(map2[x][y]>0){
            if (map2[x][y]<100) g2.setColor(new Color(0,200,0));
            else  g2.setColor(new Color(255,0,255));
            g2.fillRect(x*3,y*3,1 ,1);                
            } 
        }


        for(ListIterator<Criatura> it = Bichos.listIterator(); it.hasNext(); ) {
            Criatura c = it.next();
            c.draw(g2);
        }
        g2.setColor(new Color(255,255,255));
        g.drawString(Informacao, screenWidth-Informacao.length()*6, 10);

        String concurso= mau.nome +": "+
        mau.bichospapados+" "+predador1.nome +": "+
        predador1.bichospapados+" "+
        predador2.nome +": "+ predador2.bichospapados;
        g.drawString(concurso, screenWidth-concurso.length()*6, 30);
        g.drawString(mau.nome+" "+mau.energia+" "
            +predador2.nome+" "+predador2.energia+" "
            +predador1.nome+" "+predador1.energia, 
            screenWidth-concurso.length()*7, 50);
 

        predador1.draw(g2);
        predador2.draw(g2);
        mau.draw(g2);
        g2.dispose();
    }

    
}
