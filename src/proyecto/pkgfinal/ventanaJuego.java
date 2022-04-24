
package proyecto.pkgfinal;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Comparator;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.json.JSONException;


public class ventanaJuego extends JFrame {
    
    private int puntuacion;
    private int cantidadSkips;
    private JPanel pnlBotones;
    private JPanel pnlImagen;
    private JLabel mostraPuntos;
    private JLabel mostraSkips;
    private JLabel pais;
    private JLabel lblBandera;
    private JTextField ingresarCapital;
    private JButton btnConfirmarRespuesta;
    private JButton btnOmitirPregunta;
    
      
    StringBuffer cuerpoRespuesta = APIPaises.getCuerpoRespuesta();
    public HighScores jugador;
    String nombrePais;
    String capitalPais;
    String nombre;
    String capital; 
    String nombreJugador;
    String cadenaPuntos;
    int indicePais;
    boolean estaJugando;
    boolean tieneCapital;
    boolean respuestaIntroducida;
    
    
 
    public ventanaJuego() throws MalformedURLException, IOException, FileNotFoundException, TranscoderException{
        HighScores perfilJugador;
        SortedSet<HighScores> puntajes = new TreeSet<>(Comparator.comparingInt(puntaje->puntaje.puntaje));
        //Configuración básica de la ventana.
        this.setSize(400,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Adivina la Capital.");
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        
        //TO-DO 0.LEER ARCHIVO PUNTOS.TXT, SI NO HAY: CREARLO
        
        //1. MOSTRAR INSTRUCCIONES EN UN POP UP - LISTO
        JOptionPane.showMessageDialog(this,
                "Bienvenido a Adivina la Capital.\n"
                        + "A continuación se te presentará con el nombre de un país y tendrás que escribir el nombre de su ciudad capital en Inglés.\n"
                        + "Si tu respuesta es correcta se tu sumará un punto y seguirás jugando\n"
                        + "Si no, se terminará tu racha y perderás el juego, pero puedes volver a jugar.\n"
                        + "Si no estás seguro de tu respuesta, puedes saltarte hasta 3 preguntas.\n"
                        + "¡Disfruta! ",
                "Bienvenido a Adivina la Capital", //Titulo del mensaje
                INFORMATION_MESSAGE //Tipo de Mensaje
        );
        
        //2 MOSTRAR POP UP DE INICIO CON 1 LABEL Y 2 BOTONES
        nombreJugador = null;
        
        do{
            nombreJugador = JOptionPane.showInputDialog(this,
                    "¿Cual es tu nombre?",
                    "Inicio del Juego",
                    QUESTION_MESSAGE);

            if (nombreJugador!= null && nombreJugador.trim().length() == 0){
                JOptionPane.showMessageDialog(this, "Ingresa un nombre válido", "Ingresa un nombre valido", INFORMATION_MESSAGE);
                nombreJugador = null;
                
            } 
            else if (nombreJugador == null){
                JOptionPane.showMessageDialog(this, "Has decidido no empezar el juego.", "Juego cancelado", INFORMATION_MESSAGE);
                System.out.println("2");
                System.exit(0);
                break;
            }
        }
        while(nombreJugador == null);
       
        //3 LOOP DEL JUEGO
        estaJugando = true;
        puntuacion = 0;
        cantidadSkips = 3;
        
        //Definir elementos del panel
        //Definir panel de botones
        JPanel pnlBotones = new JPanel(new GridLayout(1,2));
        
        
        //Definir botones
        JButton btnConfirmarRespuesta = new JButton("Enviar Respuesta");
        JButton btnOmitirPregunta = new JButton("Omitir Pregunta");
        
        //Añadir botones al panel
        pnlBotones.add(btnConfirmarRespuesta);
        pnlBotones.add(btnOmitirPregunta);        
        //Definir campo de ingresar texto
        ingresarCapital = new JTextField(20);
        
        //Definir labels de puntos y skips
        JLabel mostraPuntos = new JLabel("Puntuacion: "+ puntuacion); //Puntos al panel
        JLabel mostraSkips = new JLabel("Skips disponibles: "+ cantidadSkips);
        
        //Cuestiones de Generacion de paises (Meter en el ActionListener de enviar respuesta)
            
        tieneCapital = false;
             
        if(!tieneCapital){ //Repetir mientras se elija un país sin capital (Antartica Macau, etc.)
            indicePais = generarNumeroRandom(1, 251);
            nombre = conseguirNombrePais(indicePais);
            capital = conseguirCapitalPais(indicePais);    
        }
        
        System.out.println(nombre); //Debugger, remover después
        System.out.println(capital);

        JLabel pais = new JLabel(nombre); //Label del país, se define hasta que ya tenemos país fijo.
            
        //AÑADIR ELEMENTOS AL PANEL
        this.add(mostraPuntos);
        this.add(mostraSkips);
        this.getContentPane().add(pais); //Nombre del Pais, repetir en el ActionListener
        this.add(ingresarCapital);
        this.add(pnlBotones);
        this.setVisible(true);
                      
        btnConfirmarRespuesta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                respuestaIntroducida = comprobarRespuesta(); //Comprobar respuesta
                tieneCapital = false;
                if(respuestaIntroducida == false){
                    ingresarCapital.setEditable(false);
                    btnConfirmarRespuesta.setEnabled(false);
                    btnOmitirPregunta.setEnabled(false);
                    String gameOver = nombreJugador + ", \nEl juego ha terminado\n Tu puntuación es : " + puntuacion;
                    advertencia(gameOver,"Juego finalizado");
                    System.exit(0);
                }
                
                else if(!tieneCapital){ //Repetir mientras se elija un país sin capital (Antartica Macau, etc.)
                    indicePais = generarNumeroRandom(1, 251);
                    nombre = conseguirNombrePais(indicePais);
                    capital = conseguirCapitalPais(indicePais);    
                }
                System.out.println(nombre); //Debugger, remover después
                System.out.println(capital);
                //Actualizar label del país (Hacer lo mismo para los skips)
                ingresarCapital.setText("");
                pais.setText(nombre);
                
                cadenaPuntos = ("Puntuación: " + puntuacion);
                mostraPuntos.setText(cadenaPuntos);                     
            }
        });
        
        btnOmitirPregunta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //Generar un nuevo pais
                tieneCapital = false;
                //Mostrar nueva cantidad de Skips disponibles
                if(cantidadSkips > 0){
                    cantidadSkips--;
                    
                    if(!tieneCapital){ //Repetir mientras se elija un país sin capital (Antartica Macau, etc.)
                            indicePais = generarNumeroRandom(1, 251);
                            nombre = conseguirNombrePais(indicePais);
                            capital = conseguirCapitalPais(indicePais);    
                    }
                    System.out.println(nombre); //Debugger, remover después
                    System.out.println(capital);
                    //Actualizar label del país (Hacer lo mismo para los skips)
                    ingresarCapital.setText("");
                    pais.setText(nombre);
                    }
                else if (cantidadSkips < 1){
                    advertencia("Ya no te quedan Skips disponibles.","No quedan skips");
                }
                String cadenaSkips = ("Skips disponibles: " + cantidadSkips);
                mostraSkips.setText(cadenaSkips);
            }
        });
                       
        estaJugando = respuestaIntroducida;
        //Guardar nuevo puntaje.               
   
        puntajes.add(new HighScores(nombreJugador, puntuacion));
        System.out.println(puntajes);
        
    }//FIN MAIN
    
    //Funcion para generar un numero random en el rango 1-
    private int generarNumeroRandom(int min, int max){
        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);
        
        return (int)(rand.nextInt(max-min) + min);
        
    }
    
    private void conseguirPais(){
        try{      
            nombre = APIPaises.getNombre(cuerpoRespuesta.toString(), indicePais);
            capital = APIPaises.getCapital(cuerpoRespuesta.toString(), indicePais);
            tieneCapital = true;
            System.out.println(nombre);
            System.out.println(capital);
                //System.out.println("Sali del try");
            }
        catch(JSONException e){
            if (nombre == null || capital == null){
               tieneCapital = false;
            }
        }
    }
    
    private String conseguirNombrePais(int indice){
        try{
            nombrePais = APIPaises.getNombre(cuerpoRespuesta.toString(), indice);
            capitalPais = APIPaises.getCapital(cuerpoRespuesta.toString(), indice);
            tieneCapital = true;
        }
        catch(JSONException e){
            if (nombrePais == null || capitalPais == null){
                tieneCapital = false;
            }
        }
        return nombrePais;
    }
    private String conseguirCapitalPais(int indice){
        try{
            nombrePais = APIPaises.getNombre(cuerpoRespuesta.toString(), indice);
            capitalPais = APIPaises.getCapital(cuerpoRespuesta.toString(), indice);
            tieneCapital = true;
        }
        catch(JSONException e){
            if (nombrePais == null || capitalPais == null){
                tieneCapital = false;
            }
        }
        return capitalPais;
    }    
    
    private boolean comprobarRespuesta(){
        boolean respuestaCorrecta = false;
        System.out.println(ingresarCapital.getText());
        System.out.println(capital);
        String respuestaIngresada = ingresarCapital.getText();
        
        if(respuestaIngresada.trim().length() == 0 || respuestaIngresada == null){
            JOptionPane.showMessageDialog(this,
                    "No escribiste nada en el cuadro de texto.",
                    "Ingresa una capital",
                     JOptionPane.WARNING_MESSAGE);
        }
        
        else if (!(respuestaIngresada.equals(capital))){
            String textoRespuestaCorrecta = "Respuesta incorrecta.\n La respuesta correcta era: " + capital;
            JOptionPane.showMessageDialog(this, textoRespuestaCorrecta, "Respuesta incorrecta.", JOptionPane.ERROR_MESSAGE);
            respuestaCorrecta = false;
            estaJugando = false;
        }
        
        else if (respuestaIngresada.toString().trim().equals(capital.trim().toString())){
            respuestaCorrecta = true;
            puntuacion = puntuacion+1;
            System.out.println("correcto");
            System.out.println(puntuacion);
        }
        return respuestaCorrecta;
        
        
    };
     
    private void advertencia(String mensaje, String titulo){
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}
