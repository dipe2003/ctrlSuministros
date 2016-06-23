/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web.interfaz.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateful;
import javax.inject.Named;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author Diego
 */
@Named
@Stateful
public class PdfBox {
    
    public ByteArrayOutputStream CrearPdf(String texto) throws IOException{
        // arreglo de bytes del archivo
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        // Se crea el documento y se le agrega un pagina.
        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage();
        pagina.setMediaBox(PDRectangle.A4);
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
        
        //agregar pie al documento
        addPageFooter(documento, texto);
        
        // Se guarda el resultado en el arreglo de bytes y se cierra el documento
        documento.save(output);
        documento.close();
        return output;
    }
    
    private void addPageFooter(final PDDocument doc, final String reportName) throws IOException {
        PDPageContentStream footercontentStream = null;
        try {
            Iterator it = doc.getDocumentCatalog().getPages().iterator();
            int i = 0;
            while(it.hasNext()){
                PDPage page = ((PDPage) it.next());
                footercontentStream = new PDPageContentStream(doc, page, true, true);
                footercontentStream.beginText();
                footercontentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
                footercontentStream.moveTextPositionByAmount(5f,(page.getMediaBox().getLowerLeftY() + 30f));
                footercontentStream.drawString(reportName);
                footercontentStream.moveTextPositionByAmount((page.getMediaBox().getUpperRightX() / 2),(page.getMediaBox().getLowerLeftY()));
                footercontentStream.drawString((i++ + 1) + " - " + doc.getDocumentCatalog().getPages().getCount());
                footercontentStream.endText();
                footercontentStream.close();
            }
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        } finally {
            if (footercontentStream != null) {
                try {
                    footercontentStream.close();
                } catch (final IOException exception) {
                    throw new RuntimeException(exception);
                }
            }
        }
    }
        
        
//    public ByteArrayOutputStream EditarPdf(PDDocument document, String texto){
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//    try {
//        PDPage page = (PDPage) document.getDocumentCatalog().getAllPages().get(0);
//        PDFont font = PDType1Font.HELVETICA_BOLD;
//        PDPageContentStream contentStream = new PDPageContentStream(
//                document, page);
//        page.getContents().getStream();
//        contentStream.beginText();
//        contentStream.setFont(font, 12);
//        contentStream.moveTextPositionByAmount(100, 100);
//        contentStream.drawString(texto);
//        contentStream.endText();
//        contentStream.close();
//        document.save(output);
//        document.close();
//    } catch (IOException | COSVisitorException e) {
//        e.printStackTrace();
//    }
//    return output;
//    }

    }
