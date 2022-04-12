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
import org.json.JSONObject;


public class APIPaises {
    
    private static HttpURLConnection conexion;
    
    public APIPaises(){
        //Intento 1
        
        BufferedReader reader;
        String line;
        StringBuffer contenidoRespuesta = new StringBuffer();
        
        try {
            
            URL url = new URL("https://restcountries.com/v2/all?fields=name,flag");
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
            parse(contenidoRespuesta.toString());
            
        } catch (MalformedURLException ex) {
            
        } catch (IOException ex) {
            
        }
        finally{
            conexion.disconnect();
        }      
    } //FIN MAIN
    public static String parse(String cuerpoRespuesta){
        JSONArray paises = new JSONArray(cuerpoRespuesta);
        for (int i = 0; i<paises.length(); i++){
            JSONObject pais = paises.getJSONObject(i);
            String nombre = pais.getString("name");
            String linkBandera = pais.getString("flag");
            System.out.println(nombre + " " + linkBandera);
        }
       return null;
    }
}

