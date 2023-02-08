import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.sql.Timestamp;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;

public class Predador {
    public String nome;
    BufferedImage image;
    Color cor;
    int vida;
    long nasc;
    int energia;
    int recuperacao;
    public int bichospapados=0;
    public int xpos;
    public int ypos;
    int speed;
    public int direcao=0;
    Sound som = new Sound();

    private void resize( double X, double Y){
        BufferedImage before = image;
        int x =((int)X*before.getWidth());
        int y =((int)Y*before.getHeight());
        int w = before.getWidth();
        int h = before.getHeight();
        BufferedImage after = 
            new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(X, Y);
        AffineTransformOp scaleOp = 
            new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(before, after);
        image=after;
    }

    public Predador(String name, String foto){
        Random r = new Random();
        Timestamp t = new Timestamp(System.currentTimeMillis());
        this.nasc=t.getTime();
        this.nome=name;
        this.cor=Color.RED;
        this.vida=10;
        this.energia=250;
        this.recuperacao=r.nextInt(5)+1;
        this.xpos=r.nextInt(256)+1;
        this.ypos=r.nextInt(192)+1;
        this.direcao=r.nextInt(5);
        this.speed=1;
        try{
            image = ImageIO.read(new File(foto));
        } catch(Exception e){e.printStackTrace();}
        resize(2,2);
    }
    void procurar(char[][] map){
        int dir0=0,dir1=0,dir2=0,dir3=0;
        if(xpos==0) direcao=2;
        if(ypos==0) direcao=3;
        if(xpos==255) direcao=0;
        if(ypos==191) direcao=1;

        if(energia>0){
            int xmax=xpos+energia, ymax=ypos+energia;
            int xmin=xpos-energia, ymin=ypos-energia;
            if (xmax>255) xmax=255;
            if (ymax>192) ymax=192;
            if (xmin<0) xmin=0;
            if (ymin<0) ymin=0;
            for(int x=xpos;x<xmax;x++) 
                if (map[x][ypos]=='X') dir0++;
            
            for(int x=xpos;x>0;x--) 
                if (map[x][ypos]=='X') dir1++;

            for(int y=ypos;y>0;y--) 
                if (map[xpos][y]=='X') dir2++;

            for(int y=ypos;y<ymax;y++) 
                if (map[xpos][y]=='X') dir3++;

        if(dir0>dir1&&dir0>dir2&&dir0>dir3) direcao=2;
        else
        if(dir1>dir0&&dir1>dir2&&dir1>dir3) direcao=0;
        else
        if(dir2>dir1&&dir2>dir0&&dir2>dir3) direcao=1;
        else 
        if(dir3>dir0&&dir3>dir1&&dir3>dir2) direcao=3;

        }

        if(energia>0){
        if (map[xpos+1][ypos]=='X'||map[xpos+2][ypos]=='X'||map[xpos+3][ypos]=='X') direcao=2;
        else
        if(xpos>3) if (map[xpos-1][ypos]=='X'||map[xpos-2][ypos]=='X'||map[xpos-3][ypos]=='X') direcao=0;
        else
        if (map[xpos][ypos+1]=='X'||map[xpos][ypos+2]=='X'||map[xpos][ypos+3]=='X') direcao=3;
        else
        if(ypos>3) 
            if (map[xpos][ypos-1]=='X'||map[xpos][ypos-2]=='X'||map[xpos][ypos-3]=='X') direcao=1;
        else
    if (map[xpos+1][ypos+1]=='X'||map[xpos+2][ypos+2]=='X'||map[xpos+3][ypos+3]=='X') direcao=5;
        else 
        if(ypos>3) 
    if (map[xpos+1][ypos-1]=='X'||map[xpos+2][ypos-2]=='X'||map[xpos+3][ypos-3]=='X') direcao=7;
    else
        if(xpos>3)
    if (map[xpos-1][ypos+1]=='X'||map[xpos-2][ypos+2]=='X'||map[xpos-3][ypos+3]=='X') direcao=6;
        else 
        if(ypos>3&&xpos>3) 
    if (map[xpos-1][ypos-1]=='X'||map[xpos-2][ypos-2]=='X'||map[xpos-3][ypos-3]=='X') direcao=8;
        }

    }
    public void come_bichos(List<Criatura> cabras, int x, int y, char[][] map){
        for(ListIterator<Criatura> it = cabras.listIterator(); it.hasNext(); ) {
            Criatura c = it.next();
            if (c.xpos==x&&c.ypos==y) {
                som.setFile(2);
                //som.play();
                it.remove();
                map[x][y]=' ';
                bichospapados++;
                energia+=(c.energia/2);
            }
        }
    }

   public int update(char[][] map, int[][] map2, List<Criatura> cabras){
    int comeu=-1;

    procurar(map);

    //if (energia<0) energia=0;
    //if (energia>250) energia=250;

    energia--;
    
    switch (direcao){
        case 0:
            if(xpos-speed<0) xpos=0;
            if (map[xpos-speed][ypos]=='X') come_bichos(cabras,xpos-speed,ypos,map);
            if (map[xpos-speed][ypos]==' '){
                map[xpos-speed][ypos]='C';
                map[xpos][ypos]=' ';
                xpos-=speed;
            }else direcao=2;
            
        break;
        case 1:
        if(ypos-speed<0) ypos=0;
        if (map[xpos][ypos-speed]=='X') come_bichos(cabras,xpos,ypos-speed,map);
        if (map[xpos][ypos-speed]==' '){
            map[xpos][ypos-speed]='C';
            map[xpos][ypos]=' ';
            ypos-=speed;
        }else direcao=3;
        
        break;
        case 2:
            if(xpos+speed>255) xpos=255;
            if (map[xpos+speed][ypos]=='X') come_bichos(cabras,xpos+speed,ypos,map);
            if (map[xpos+speed][ypos]==' '){
                map[xpos+speed][ypos]='C';
                map[xpos][ypos]=' ';
                xpos+=speed;
            } else direcao=0;
            
        break;
        case 3:
        if(ypos+speed>191) ypos=191;
        if (map[xpos][ypos+speed]=='X') come_bichos(cabras,xpos,ypos+speed,map);
        if (map[xpos][ypos+speed]==' '){
            map[xpos][ypos+speed]='C';
            map[xpos][ypos]=' ';
            ypos+=speed;
        } else direcao=1;
        
        break;
        case 4:
        procurar(map);
            //if (energia<250)energia+=recuperacao;
        break;
        case 5:
        if(ypos+speed>191) ypos=191;
        if(xpos+speed>255) xpos=255;
        if (map[xpos+speed][ypos+speed]=='X') come_bichos(cabras,xpos+speed,ypos+speed,map);
        if (map[xpos+speed][ypos+speed]==' '){
            map[xpos+speed][ypos+speed]='C';
            map[xpos][ypos]=' ';
            ypos+=speed;
            xpos+=speed;
        } else direcao=1;
        break;
        case 6:
        if(ypos+speed>191) ypos=191;
        if(xpos-speed<1) xpos=1;
        if (map[xpos-speed][ypos+speed]=='X') come_bichos(cabras,xpos-speed,ypos+speed,map);
        if (map[xpos-speed][ypos+speed]==' '){
            map[xpos-speed][ypos+speed]='C';
            map[xpos][ypos]=' ';
            ypos+=speed;
            xpos-=speed;
        } else direcao=1;
        break;
        case 7:
        if(ypos-speed<1) ypos=1;
        if(xpos+speed>255) xpos=255;
        if (map[xpos+speed][ypos-speed]=='X') come_bichos(cabras,xpos+speed,ypos-speed,map);
        if (map[xpos+speed][ypos-speed]==' '){
            map[xpos+speed][ypos-speed]='C';
            map[xpos][ypos]=' ';
            ypos-=speed;
            xpos+=speed;
        } else direcao=1;
        break;
        case 8:
        if(ypos-speed<1) ypos=1;
        if(xpos-speed<1) xpos=1;
        if (map[xpos-speed][ypos-speed]=='X') come_bichos(cabras,xpos-speed,ypos-speed,map);
        if (map[xpos-speed][ypos-speed]==' '){
            map[xpos-speed][ypos-speed]='C';
            map[xpos][ypos]=' ';
            ypos-=speed;
            xpos-=speed;
        } else direcao=1;
        break;
    }

    return comeu;
   }
   public void draw(Graphics2D g2){
    if (energia>0){
        g2.setColor(cor);
        //g2.fillRect(xpos*3,ypos*3,6 ,6);
        g2.drawImage(image, null, xpos*3, ypos*3);
    }

}
    
}
