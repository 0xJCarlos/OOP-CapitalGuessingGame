package proyecto.pkgfinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;


public class APIPaises {
    
    int indice;
    
    private static HttpURLConnection conexion;
    
    public APIPaises(StringBuffer cuerpoRespuesta, String nombre, String capital, int indice){
       cuerpoRespuesta = getCuerpoRespuesta();
       nombre = getNombre(cuerpoRespuesta.toString(), indice);
       capital = getCapital(cuerpoRespuesta.toString(), indice); 
    } //FIN MAIN
    
    public static StringBuffer getCuerpoRespuesta(){
        
        BufferedReader reader;
        String line;
        StringBuffer contenidoRespuesta = new StringBuffer();
        
        try {
            
            URL url = new URL("https://restcountries.com/v2/all?fields=name,capital");
            conexion = (HttpURLConnection) url.openConnection( );
            
            //Configuracion para peticiones HTTP
            conexion.setRequestMethod("GET");
            conexion.setConnectTimeout(5000);
            conexion.setReadTimeout(5000);
            
            //Buscar respuesta de código
            
            int status =  conexion.getResponseCode();
            //System.out.println(status);
             
            if(status > 299){ //Si la conexión no es exitosa, listar los errores al contenido de respuesta.
                reader = new BufferedReader(new InputStreamReader(conexion.getErrorStream()));
                while((line = reader.readLine()) != null){
                     contenidoRespuesta.append(line);
                }
                reader.close();
            }
            else{ //Listar el contenido de la respuesta (JSON de la API REST Countries) 
                reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                while((line = reader.readLine()) != null){
                    contenidoRespuesta.append(line);
                }
                reader.close();
            } 
            //parse(contenidoRespuesta.toString());
            //System.out.println(getNombre(contenidoRespuesta.toString(),3));
            //System.out.println(getLinkBandera(contenidoRespuesta.toString(),3));
            
        } catch (MalformedURLException ex) {
            
        } catch (IOException ex) {
            
        }
        finally{
            conexion.disconnect();
        } 
        
        
        return contenidoRespuesta;
    }
    
    public static String parse(String cuerpoRespuesta){
        JSONArray paises = new JSONArray(cuerpoRespuesta); 
        for (int i = 0; i<paises.length(); i++){
            JSONObject pais = paises.getJSONObject(i);
            String nombre = pais.getString("name");
            String capital = pais.getString("capital");
            
            System.out.println(nombre + " " + capital);
        }
       return null;
    }
    
    public static String getNombre(String cuerpoRespuesta, int indice) throws JSONException{
        JSONArray paises = new JSONArray(cuerpoRespuesta);
        JSONObject pais = paises.getJSONObject(indice);
        String nombre = pais.getString("name");
        
        return nombre;
    }
    
    public static String getCapital(String cuerpoRespuesta, int indice) throws JSONException{
        JSONArray paises = new JSONArray(cuerpoRespuesta);
        JSONObject pais = paises.getJSONObject(indice);
        String capital = pais.getString("capital");
        
        return capital;
    }
}

