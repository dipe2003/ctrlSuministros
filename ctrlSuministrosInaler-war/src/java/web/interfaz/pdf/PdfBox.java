/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web.interfaz.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.ejb.Stateful;
import javax.inject.Named;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author Diego
 */
@Named
@Stateful
public class PdfBox {
    
    public ByteArrayOutputStream CrearPdf(String texto) throws IOException, COSVisitorException{
        // arreglo de bytes del archivo
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        // Se crea el documento y se le agrega un pagina.
        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage();
        documento.addPage(pagina);
        
        // Se crea un objeto fuente seleccionando la fuente base del pdf
        PDFont font = PDType1Font.HELVETICA_BOLD;
        
        // Se inicia un contentstream para mantener el contenido creado.
        PDPageContentStream contentStream = new PDPageContentStream(documento, pagina);
        
        // Se define el texto usando la fuente seleccionada, se mueve el cursos y se agrega al contentstream.
        contentStream.beginText();
        contentStream.setFont( font, 12 );
        contentStream.moveTextPositionByAmount( 100, 700 );
        contentStream.drawString(texto);
        contentStream.endText();
        
        // Se cierra el contentStream
        contentStream.close();
        
        // Se guarda el resultado en el arreglo de bytes y se cierra el documento
        documento.save(output);
        documento.close();
        return output;
    }
    
}
