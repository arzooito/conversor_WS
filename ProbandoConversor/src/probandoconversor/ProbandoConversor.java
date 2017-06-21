/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probandoconversor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AlejandroSA
 */
public class ProbandoConversor {

    final static String NOPASS = "";
    final static int PRIMERA = -1;
    final static int FINAL = -1;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String rutaDir = "./pruebas";
        File dir = new File(rutaDir);
        
        String[] archivos = dir.list();
        String[] tipos = {"doc","docx","rtf"};
        String[] ext = {".doc",".docx",".rtf"};
        String pdf = null;
        String salida = null;
        String dirSalida = null;
        
        for(int n = 0; n < tipos.length; n++){
            for (String archivo : archivos){
                
                dirSalida = rutaDir + "\\" + tipos[n];
                new File(dirSalida).mkdir(); 
                pdf = rutaDir + "\\" + archivo;
                salida = dirSalida + "\\" + archivo;
                convertirPDF(pdf, salida.replace(".pdf", ext[n]), tipos[n]);
            }
        }
        
    }
    

    private static void convertirPDF(String pdfEntrada, String archivoSalida, String tipo){
          
        File ficheroPdf = new File(pdfEntrada);
        File ficheroDocx = new File(archivoSalida);
        byte[] pdfArray = null;
        byte[] docxArray = null;
        FileOutputStream fos = null;
        
        try {
            pdfArray = Files.readAllBytes(ficheroPdf.toPath());
            docxArray = pdf2(pdfArray, tipo, NOPASS, PRIMERA, FINAL);
            fos = new FileOutputStream(ficheroDocx);
            if(docxArray != null)
                fos.write(docxArray);
            
        } catch (IOException ex) {
            Logger.getLogger(ProbandoConversor.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                if(fos != null)
                fos.close();
            }catch (IOException | NullPointerException ex) {
                Logger.getLogger(ProbandoConversor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
    }

    private static byte[] pdf2(byte[] pdf, java.lang.String tipo, java.lang.String password, int desde, int hasta) {
        org.diputacion.policia.conversor_ws.Conversor_Service service = new org.diputacion.policia.conversor_ws.Conversor_Service();
        org.diputacion.policia.conversor_ws.Conversor port = service.getConversorPort();
        return port.pdf2(pdf, tipo, password, desde, hasta);
    }
    
 
}
