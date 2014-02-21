/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.applet.Applet;

import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.LinkedList;

public class AppletExamen1 extends Applet implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables.
    private int vidas;      // contador de vidas del fantasma
    private int puntuacion;     // contador de puntos
    private int conteoVidas;     // contador de vidas
    private int teclasPresionadas;  //contador de teclas presionadas
    
    //private AudioClip sonido;    // Objeto AudioClip

    private int direccion;   // direccion a tomar
    
    //Variables de control de tiempo de la animación
    private long tiempoActual;

    private boolean pausa;      // bandera para la pausa

    private Image gameover;     // imagen de gameover
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico

    private Bueno bueno;    // Objeto de la clase Bueno
    private Malo malo;    //Objeto de la clase Malo
    
    private boolean bandera = false;//variable que nos sirve para checar si se dio click con el mouse
    private int mouseX;
    private int mouseY;
    
    //private int numero;

    private LinkedList<Malo> malos;   //Lista de los malos aleatorios

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        this.setSize(700, 600);

        teclasPresionadas = 0;  //bandera para que no se siga moviendo el bueno
        pausa = false;      //bandera para tener pausa
        conteoVidas = 10; //contador para que se reduzca una vida
        vidas = 5; //vidas del elefante inicializadas
        puntuacion = 0; // inicio de conteo de puntos

        //Se cargan las imagenes y se crean los objetos.
        //malos
        malos = creacion();

        
        bueno = new Bueno(getWidth()/2, getHeight()-5); // crea el bueno en medio de la pantalla

        //fin del juego
        URL goURL = this.getClass().getResource("/imagenes/gameover.jpg");
        gameover = Toolkit.getDefaultToolkit().getImage(goURL);

        

        addKeyListener(this);
        
        //sonidos
        //URL eaURL = this.getClass().getResource("sonidos/Explosion.wav");
	//sonido = getAudioClip (eaURL);
        
        
        setBackground(Color.yellow);
    }

    /*
     metodo para crear la lista de asteroides
     */
    public LinkedList<Malo> creacion() {
        LinkedList<Malo> malos = new LinkedList<Malo>(); //inicia la lista de malos
        
        int numero = (int) (Math.random() * (6 - 3) ) +3; //crea malos entre 
        
        for (int i = 0; i < numero; i++) {
            int posrX = (int) ((Math.random() * getWidth()));    //posision x es tres cuartos del applet
            int posrY = (int) (Math.random() * (getHeight() * -4));    //posision y es tres cuartos del applet
            malo = new Malo(posrX, (posrY - 10));
            malos.add(malo);
        }
        return malos;

    }

    /**
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init o cuando el usuario visita otra pagina y
     * luego regresa a la pagina en donde esta este <code>Applet</code>
     *
     */
    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {
            
        while (true) {
            actualiza();
            checaColision();
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo usado para actualizar la posicion de objetos elefante y raton.
     *
     */
    public void actualiza() {
        //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        
        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;
        if (!pausa){
            //Actualiza la animación en base al tiempo transcurrido
            bueno.actualizaAnimacion(tiempoActual);
            for (Malo malo : malos) {
                malo.actualizaAnimacion(tiempoActual);
            }
        
            //Empieza a actualizarse si las vidas son mayores a 0
            if (vidas > 0) {
                if (conteoVidas < 1) {       // si el contador de meteoritos que paso es mayor a 10
                    vidas--;
                    conteoVidas = 10;
                }

                //movimiento del bueno
                if(bandera){
                bueno.setPosY(mouseY);
                bueno.setPosX(mouseX);
                bandera = false;
            }
                

                //movimiento de los malos
                for (Malo malo : malos) {
                    malo.setPosY(malo.getPosY() + malo.getVelocidad()); // mueve los malos abajo
                    if (malo.getPosY() > getWidth()) {
                        puntuacion -= 20; // baja tu puntuacion
                        int posrY = (int) (Math.random() * (getHeight() * -4));    //posision y es tres cuartos del applet
                        malo.setPosY(posrY);
                        conteoVidas--;  //reduce las vidas
                    }
                }
            }
        }
    }

    /**
     * Metodo usado para checar las colisiones del objeto elefante y raton con
     * las orillas del <code>Applet</code>.
     */
    public void checaColision() {
        //colision con la ventana
        if (bueno.getPosX() < 0) {
            bueno.setPosX(0);
        }
        if (bueno.getPosX() > getWidth() - bueno.getAncho()) {
            bueno.setPosX(getWidth() - bueno.getAncho());
        }

        if (bueno.getPosY() > getHeight() - bueno.getAlto()) {
            bueno.setPosY(getHeight() - bueno.getAlto());
        }
        if (bueno.getPosY() < 0) {
            bueno.setPosY(0);
        }
        
        
        //colision con la ventana
        if (malo.getPosX() < 0) {
            //sonido.play();
            malo.setPosX(0);
        }
        if (malo.getPosX() > getWidth() - malo.getAncho()) {
            //sonido.play();
            malo.setPosX(getWidth() - malo.getAncho());
        }

        
        

        //Colision entre objetos
        for (Malo malo : malos) {
            if (bueno.intersecta(malo)) {
                puntuacion += 100;    // aumenta los puntos
                //bomb.play();    //sonido al colisionar

                //El malo se mueve al azar afuera del applet.
                malo.setPosY((int) (Math.random() * (getHeight() + getHeight() / 2) * -1));
            }
        }
    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void update(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint(dbg);

        // Dibuja la imagen actualizada
        g.setColor(Color.white);
        g.drawImage(dbImage, 0, 0, this);

    }

    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {    //Presiono flecha izquierda
            direccion = 3;
            teclasPresionadas++;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {    //Presiono flecha derecha
            direccion = 4;
            teclasPresionadas++;
        }
    }

    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT){
            teclasPresionadas--;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP){
            teclasPresionadas--;
        }
        if (teclasPresionadas < 1 && (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT)) {    //Presiono flecha arriba
            direccion = 0;  //detiene el movimiento al soltar la tecla
        }
        if (e.getKeyCode() == KeyEvent.VK_P){
            pausa = !pausa;
        }
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        g.setColor(Color.black); // dibuja las letras blancas
        if (vidas > 0) {
            //dibuja sobre el menu
            //g.drawString("Vidas: " + Integer.toString(vidas), 10, 15); // las vidas
            g.drawString("Score: " + Integer.toString(puntuacion), 80, 15); // la puntuacion
            

            if (bueno != null && malo != null) {
                //Dibuja la imagen en la posicion actualizada
                if (!pausa){
                    g.drawImage(bueno.getImagenI(), bueno.getPosX(), bueno.getPosY(), this);
                }
                else{
                    g.drawString(bueno.getPausa(), bueno.getPosX(), bueno.getPosY());
                }
                for (Malo malo : malos) {
                    g.drawImage(malo.getImagenI(), malo.getPosX(), malo.getPosY(), this);
                }

            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }
        } else {
            g.drawString("Puntuacion: " + Integer.toString(puntuacion), 10, 50);
            g.drawImage(gameover, getWidth() / 4, getHeight() / 4, this);
            g.drawImage(bueno.getImagenI(), getWidth() / 5, getHeight() / 2, this);
        }

    }
    
    
    public void mouseClicked(MouseEvent e) {

                bandera = true;
                mouseX = e.getX();
                mouseY = e.getY();
                
        }
        
        public void mouseEntered(MouseEvent e) {
        }
     
        public void mouseExited(MouseEvent e) {
        }
    
        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }  
    
    
    
    
    
    
}
