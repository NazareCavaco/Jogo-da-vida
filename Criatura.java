import java.util.Random;
import java.sql.Timestamp;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Criatura {
    public String nome;
    BufferedImage image;
    Color cor;
    int vida;
    long nasc;
    int energia;
    int recuperacao;
    public int xpos;
    public int ypos;
    int speed;
    private int direcao=0;

    public Criatura(String name){
        Random r = new Random();
        Timestamp t = new Timestamp(System.currentTimeMillis());
        this.nasc=t.getTime();
        this.nome=name;
        this.cor=Color.WHITE;
        this.vida=10;
        this.energia=200;
        this.recuperacao=r.nextInt(5)+1;
        this.xpos=r.nextInt(256)+1;
        this.ypos=r.nextInt(192)+1;
        this.direcao=r.nextInt(5);
        this.speed=1;
        try{
            image = ImageIO.read(new File("criatura.jpg"));
        } catch(Exception e){e.printStackTrace();}
    }
    void procurar(int[][] map2){
        int dir0=0,dir1=0,dir2=0,dir3=0;
        if(xpos==0) direcao=2;
        if(ypos==0) direcao=3;
        if(xpos==255) direcao=0;
        if(ypos==191) direcao=1;

        if(energia<150){
            int xmax=xpos+energia, ymax=ypos+energia;
            int xmin=xpos-energia, ymin=ypos-energia;
            if (xmax>255) xmax=255;
            if (ymax>192) ymax=192;
            if (xmin<0) xmin=0;
            if (ymin<0) ymin=0;
            for(int x=xpos;x<xmax;x++) dir0+=map2[x][ypos];
            for(int x=xpos;x>0;x--) dir1+=map2[xpos-x][ypos];
            for(int y=ypos;y>0;y--) dir2+=map2[xpos][ypos-y];
            for(int y=ypos;y<ymax;y++) dir3+=map2[xpos][y];
        if(dir0>dir1&&dir0>dir2&&dir0>dir3) direcao=2;
        else
        if(dir1>dir0&&dir1>dir2&&dir1>dir3) direcao=0;
        else
        if(dir2>dir1&&dir2>dir0&&dir2>dir3) direcao=1;
        else direcao=3;
        }

        if(energia<200){
        if (map2[xpos+1][ypos]>0||map2[xpos+2][ypos]>0) direcao=2;
        else
        if(xpos>2) if (map2[xpos-1][ypos]>0||map2[xpos-2][ypos]>0) direcao=0;
        else
        if (map2[xpos][ypos+1]>0||map2[xpos][ypos+2]>0) direcao=3;
        else
        if(ypos>2) 
            if (map2[xpos][ypos-1]>0||map2[xpos][ypos-2]>0) direcao=1;
        else
    if (map2[xpos+1][ypos+1]>0||map2[xpos+2][ypos+2]>0) direcao=5;
        else 
        if(ypos>2) 
    if (map2[xpos+1][ypos-1]>0||map2[xpos+2][ypos-2]>0) direcao=7;
    else
        if(xpos>2)
    if (map2[xpos-1][ypos+1]>0||map2[xpos-2][ypos+2]>0) direcao=6;
        else 
        if(ypos>2&&xpos>2) 
    if (map2[xpos-1][ypos-1]>0||map2[xpos-2][ypos-2]>0) direcao=8;
        }
    }
   public boolean update(char[][] map, int[][] map2){
    boolean sexo=false;
    Random r= new Random();
    int temp=r.nextInt(100);
    procurar(map2);
    if (temp<10) {direcao=r.nextInt(9);}
    if (direcao!=4) energia--;
    if (energia<0) energia=0;
    if (energia>250) energia=250;
    cor=new Color(energia,energia,energia);
    
    switch (direcao){
        case 0:
            if(xpos-speed<0) xpos=0;
            else
            if (map[xpos-speed][ypos]==' '){
                map[xpos-speed][ypos]='X';
                map[xpos][ypos]=' ';
                xpos-=speed;
            }else if (temp<10){energia-=50;sexo=true;cor=Color.RED;direcao=2;}
        break;
        case 1:
        if(ypos-speed<0) ypos=0;
        else
        if (map[xpos][ypos-speed]==' '){
            map[xpos][ypos-speed]='X';
            map[xpos][ypos]=' ';
            ypos-=speed;
        }else if (temp<10){energia-=50;sexo=true;cor=Color.RED;direcao=3;}
        break;
        case 2:
            if(xpos+speed>255) xpos=255;
            else
            if (map[xpos+speed][ypos]==' '){
                map[xpos+speed][ypos]='X';
                map[xpos][ypos]=' ';
                xpos+=speed;
            }else if (temp<10) {energia-=50;sexo=true;cor=Color.RED;direcao=0;}
        break;
        case 3:
        if(ypos+speed>191) ypos=191;
        else
        if (map[xpos][ypos+speed]==' '){
            map[xpos][ypos+speed]='X';
            map[xpos][ypos]=' ';
            ypos+=speed;
        } else if (temp<10) {energia-=50;sexo=true;cor=Color.RED;direcao=1;}
        break;
        case 4:
        procurar(map2);
            //if (energia<250)energia+=recuperacao;
        break;
        case 5:
        if(ypos+speed>191) ypos=191;
        if(xpos+speed>255) xpos=255;
        if (map[xpos+speed][ypos+speed]==' '){
            map[xpos+speed][ypos+speed]='X';
            map[xpos][ypos]=' ';
            ypos+=speed;
            xpos+=speed;
        } else if (temp<10) {energia-=50;sexo=true;cor=Color.RED;direcao=1;}
        break;
        case 6:
        if(ypos+speed>191) ypos=191;
        if(xpos-speed<1) xpos=1;
        if (map[xpos-speed][ypos+speed]==' '){
            map[xpos-speed][ypos+speed]='X';
            map[xpos][ypos]=' ';
            ypos+=speed;
            xpos-=speed;
        } else if (temp<10) {energia-=50;sexo=true;cor=Color.RED;direcao=1;}
        break;
        case 7:
        if(ypos-speed<1) ypos=1;
        if(xpos+speed>255) xpos=255;
        if (map[xpos+speed][ypos-speed]==' '){
            map[xpos+speed][ypos-speed]='X';
            map[xpos][ypos]=' ';
            ypos-=speed;
            xpos+=speed;
        } else if (temp<10) {energia-=50;sexo=true;cor=Color.RED;direcao=1;}
        break;
        case 8:
        if(ypos-speed<1) ypos=1;
        if(xpos-speed<1) xpos=1;
        if (map[xpos-speed][ypos-speed]==' '){
            map[xpos-speed][ypos-speed]='X';
            map[xpos][ypos]=' ';
            ypos-=speed;
            xpos-=speed;
        } else if (temp<10) {energia-=50;sexo=true;cor=Color.RED;direcao=1;}
        break;
    }

    return sexo;
   }
   public void draw(Graphics2D g2){
    if (energia>1){
        g2.setColor(cor);
        //g2.fillRect(xpos*3,ypos*3,3 ,3);
        g2.drawImage(image, null, xpos*3, ypos*3);
    }

}
    
}
