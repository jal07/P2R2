
package HTTP;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * Esta clase tiene la capacidad de crear un servidor HTTP para responder una solicitud GET
 * 
 * @author Jonathan Avendaño Leiva
 */

public class ServidorHTTP{
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final int PUERTO = 9009;
        HttpServer httpd = null;
		try {
			httpd = HttpServer.create(new InetSocketAddress(PUERTO), 0);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}        
        HttpContext ctx = httpd.createContext("/");
        ctx.setHandler(arg0 -> {
			try {
				gestionarSolicitud(arg0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
        
        httpd.start();
        System.out.println("Esperando por una comunicación...");
               		
	}//end methodo main
	
	private static void gestionarSolicitud(HttpExchange exchange) throws IOException {
        final int CODIGO_RESPUESTA = 200;
        String contenido = "Respuesta exitosa desde el servidor HTTP, CODIGO=200";
        
        
        exchange.sendResponseHeaders(CODIGO_RESPUESTA, contenido.getBytes().length);
        
        OutputStream os = exchange.getResponseBody();
        
        os.write(contenido.getBytes());
        os.close();
        System.out.println("Respuesta exitosa desde el servidor HTTP, CODIGO=200 / Puerto:9009");
        
    }
	
}//end class
