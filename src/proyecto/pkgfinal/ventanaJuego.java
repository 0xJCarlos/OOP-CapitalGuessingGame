
package proyecto.pkgfinal;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ventanaJuego extends JFrame {
    
    private int puntuacion;
    private JPanel pnlBotones;
    private JLabel mostrarPuntos;
    private JTextField ingresarRespuesta;
    private JButton btnConfirmarRespuesta;
    private JButton btnOmitirPregunta;
    private String pais;
    private URL url;
    
    APIPaises ap = new APIPaises();
    
    public ventanaJuego(){
        //Configuración básica de la ventana.
        this.setSize(400,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Adivina la bandera.");
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        
        puntuacion = 0;
        
        //Texto de puntuación
        JLabel mostraPuntos = new JLabel("Puntuacion: "+ puntuacion);
        
        //Campo de entrada de texto
        JTextField ingresarRespuesta = new JTextField(20);
        
        //Definir panel de botones
        JPanel pnlBotones = new JPanel(new GridLayout(1,2));
        
        //Definir botones
        JButton btnConfirmarRespuesta = new JButton("Enviar Respuesta");
        JButton btnOmitirPregunta = new JButton("Omitir Pregunta");
        
        //Añadir botones al panel
        pnlBotones.add(btnConfirmarRespuesta);
        pnlBotones.add(btnOmitirPregunta);
        
        //Añadir elementos a la ventana
        this.getContentPane().add(mostraPuntos);
        this.getContentPane().add(ingresarRespuesta);
        this.add(pnlBotones);   
        //Hacer visible la ventana
        this.setVisible(true);
    }//Fin clase ventana
    
    
    
}
