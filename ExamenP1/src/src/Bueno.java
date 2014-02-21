package src;


import java.awt.Image;
import java.awt.Toolkit;

public class Bueno extends Base {
    
    private static final String PAUSA = "Pausado";
    private static final String DESAPARECE = "Desaparece";

    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Base</code>.
     *
     * @param posX es el arreglo de <code>posX</code> de la animacion.
     * @param posY es el <code>indice del cuadro</code> actual.
     */
    public Bueno(int posX, int posY) {
        super(posX, posY);
        
        anim = new Animacion();

        Image conejo1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/der_1.gif"));
        Image conejo2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/der_2.gif"));
        Image conejo3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/der_3.gif"));
        Image conejo4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/der_4.gif"));
        Image conejo5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/der_5.gif"));
        Image conejo6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/der_6.gif"));
        Image conejo7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/imagenes/der_7.gif"));

        anim.sumaCuadro(conejo1, 100);
        anim.sumaCuadro(conejo2, 100);
        anim.sumaCuadro(conejo3, 100);
        anim.sumaCuadro(conejo4, 100);
        anim.sumaCuadro(conejo5, 100);
        anim.sumaCuadro(conejo6, 100);
        anim.sumaCuadro(conejo7, 100);
    }
    
    public String getPausa() {
        return PAUSA;
    }
    
    public String getDesaparece() {
        return DESAPARECE;
    }
}
