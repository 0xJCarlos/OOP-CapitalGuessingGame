package proyecto.pkgfinal;

public class HighScores {
    
    String nombreJugador;
    int puntaje;
    
    public HighScores(String nombreJugador, int puntaje){
        this.nombreJugador = nombreJugador;
        this.puntaje = puntaje;
    }
    
    public String getNombre(){
        return nombreJugador;
    }
    
    public void setNombre(String nombreJugador){
        this.nombreJugador = nombreJugador;
    }
    
    public int getPuntaje(){
        return puntaje;
    }
    
    public void setPuntaje(int puntaje){
        this.puntaje = puntaje;
    }
}
