package HTTPS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
//import javax.net.ssl.X509TrustManager;

public class TestHTTPS {
   public static void main(String[] args) {
      try {
         // Carga del fichero que tiene los certificados de los servidores en
         // los que confiamos.
         InputStream fileCertificadosConfianza = new FileInputStream(new File(
               "C:\\Program Files\\Java\\jre1.8.0_271\\lib\\security\\cacerts"));
         KeyStore ksCertificadosConfianza = KeyStore.getInstance(KeyStore
               .getDefaultType());
         ksCertificadosConfianza.load(fileCertificadosConfianza,
               "changeit".toCharArray());
         fileCertificadosConfianza.close();

         // Ponemos el contenido en nuestro manager de certificados de
         // confianza.
         TrustManagerFactory tmf = TrustManagerFactory
               .getInstance(TrustManagerFactory.getDefaultAlgorithm());
         tmf.init(ksCertificadosConfianza);

         // Creamos un contexto SSL con nuestro manager de certificados en los
         // que confiamos.
         SSLContext context = SSLContext.getInstance("TLS");
         context.init(null, tmf.getTrustManagers(), null);
         SSLSocketFactory sslSocketFactory = context.getSocketFactory();

         // Abrimos la conexión y le pasamos nuestro contexto SSL
         URL url = new URL("https://www.nasa.gov/");
         URLConnection conexion = url.openConnection();
         ((HttpsURLConnection) conexion).setSSLSocketFactory(sslSocketFactory);

         // Ya podemos conectar y leer
         conexion.connect();
         InputStream is = conexion.getInputStream();
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         char[] buffer = new char[1000];
         int leido;
         while ((leido = br.read(buffer)) > 0) {
            System.out.println(new String(buffer, 0, leido));
         }
      } catch (MalformedURLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (KeyStoreException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (NoSuchAlgorithmException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (CertificateException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (KeyManagementException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}