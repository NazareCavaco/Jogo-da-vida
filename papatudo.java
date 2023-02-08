import java.awt.Color;
import java.util.List;


public class papatudo extends Predador{

    public papatudo(String nome){
        super(nome,"papatudo2.jpg");
        cor=Color.YELLOW;
    }

    public void mover(char[][] map, int[][] map2, List<Criatura> cabras,KeyHandler keyH){
        if (keyH.upPressed==true){
            direcao=1;
        }
        else if (keyH.downPressed==true){
            direcao=3;
        }
        else if (keyH.leftPressed==true){
            direcao=0;
        }
        else if (keyH.rightPressed==true){
            direcao=2;
        }
        if (keyH.upPressed==true&&keyH.leftPressed==true){
            direcao=8;
        }
        if (keyH.downPressed==true&&keyH.rightPressed==true){
            direcao=5;
        }
        if (keyH.downPressed==true&&keyH.leftPressed==true){
            direcao=6;
        }
        if (keyH.upPressed==true&&keyH.rightPressed==true){
            direcao=7;
        }
        if (keyH.rightPressed==false&&keyH.leftPressed==false&&keyH.upPressed==false&&keyH.downPressed==false) direcao=4;
        else energia--;
        switch (direcao){
            case 0:
                if(xpos-speed<1) xpos=1;
                if (map[xpos-speed][ypos]=='X') come_bichos(cabras,xpos-speed,ypos,map);
                if (map[xpos-speed][ypos]==' '){
                    map[xpos-speed][ypos]='C';
                    map[xpos][ypos]=' ';
                    xpos-=speed;
                }else direcao=2;
                
            break;
            case 1:
            if(ypos-speed<1) ypos=1;
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
    }
 
}
