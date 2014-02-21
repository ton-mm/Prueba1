package src;

import java.awt.Image;
import java.awt.Toolkit;

public class Malo extends Base {
    
    private int velocidad;

    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Base</code>.
     *
     * @param posX es el arreglo de <code>posX</code> de la animacion.
     * @param posY es el <code>indice del cuadro</code> actual.
     */
    public Malo(int posX, int posY) {
        super(posX, posY);

        anim = new Animacion();

        Image malo1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/1.gif"));
        Image malo2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/2.gif"));
        Image malo3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/3.gif"));
        Image malo4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/4.gif"));
        Image malo5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/5.gif"));
        Image malo6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/6.gif"));
        Image malo7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/7.gif"));

        anim.sumaCuadro(malo1, 100);
        anim.sumaCuadro(malo2, 100);
        anim.sumaCuadro(malo3, 100);
        anim.sumaCuadro(malo4, 100);
        anim.sumaCuadro(malo5, 100);
        anim.sumaCuadro(malo6, 100);
        anim.sumaCuadro(malo7, 100);
        
        velocidad = (int) (Math.random() * (6 - 3) ) +3; //le asigna velocidad entre 3 y 6
        
        
    }
    
    public int getVelocidad(){
        return velocidad;
    }
}
