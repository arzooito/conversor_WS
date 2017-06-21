/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.diputacion.policia.conversor_WS;

import com.bcl.easyconverter.word.*;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author AlejandroSA
 */
@WebService(serviceName = "conversor")
public class Conversor {

    final static int ULTIMA = -1;
    final static int PRIMERA = -1;
    final static String SINPASS = "";
    final static int CALIDAD_MAXIMA = 100;
    
    
    
    
    /**
     * Convierte un archivo pdf en un archivo docx
     * @param pdf tranformado a array de bytes
     * @return array de bytes con el archivo docx
     */
    
    @WebMethod(operationName = "Pdf2Word")
    public byte[] convertirPdf(@WebParam(name = "pdf") byte[] pdf) {
      
        PDF2Word pdf2word = new PDF2Word(); 
        byte[] byteArray = null;
        pdf2word.setOutputDocumentFormat(optOutputDocumentFormat.OPT_OUTPUT_DOCX);
        pdf2word.setJpegQuality(CALIDAD_MAXIMA); //de 0 a 100
        try{        
           byteArray = pdf2word.ConvertToWord3( pdf, SINPASS, PRIMERA, ULTIMA);
        }
        catch(PDF2WordException ex)
        {
           System.out.println(ex.getMessage());
        }
        finally
        {
           pdf2word.dispose();
        }

          return byteArray;
      }
    
    
     /**
     * Convierte un archivo pdf en un archivo de texto editable
     * @param pdf archivo pdf tranformado a array de bytes
     * @param tipo tipo de formato de salida. Los valores son "doc", "docx" o "rtf"
     * @param password contraseña del pdf si la hubiera, en caso contrario dejar en blanco "".
     * @param desde página desde la que comienza la conversión
     * @param hasta página en la que acaba la conversion
     * @return array de bytes con el archivo docx
     */
    @WebMethod(operationName = "Pdf2")
    public byte[] convertirPdf2(
            @WebParam(name = "pdf") byte[] pdf, 
            @WebParam(name = "tipo")String tipo, 
            @WebParam(name = "password")String password,
            @WebParam(name = "desde")int desde,
            @WebParam(name = "hasta")int hasta){
      
        PDF2Word pdf2word = new PDF2Word(); 
        byte[] byteArray = null;
        pdf2word.setJpegQuality(CALIDAD_MAXIMA); //de 0 a 100
        
        switch(tipo){
            case "doc":
                pdf2word.setOutputDocumentFormat(optOutputDocumentFormat.OPT_OUTPUT_DOC);
                break;
            case "docx":
                pdf2word.setOutputDocumentFormat(optOutputDocumentFormat.OPT_OUTPUT_DOCX);
                break;
            case "rtf":
                pdf2word.setOutputDocumentFormat(optOutputDocumentFormat.OPT_OUTPUT_RTF);
                break;
            default:
                break;
        }
        
        try{        
           byteArray = pdf2word.ConvertToWord3( pdf, password, desde, hasta);
        }
        catch(PDF2WordException ex)
        {
           System.out.println(ex.getMessage());
        }
        finally
        {
           pdf2word.dispose();
        }

          return byteArray;
      }
   
}
