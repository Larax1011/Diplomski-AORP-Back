package raf.lazar.diplomski_aorp.model.izvestaj;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ReportBuilder {

    private static final String CREATOR = "RAF-OBRACUN";

    private ReportBuilder() {
    }

    /**
     * Generise izvestaj tabelarnog prikaza za odredjeni tip.
     *
     * @param title  Naziv dokumenta.
     * @param rows   Sadrzaj dokumenta.
     * @return pdf u bajt streamu u koji su upisani bajtovi.
     * @throws DocumentException izuzetak biblioteke za pravljenje pdf-a kod citanja i pisanja.
     */
    public static ByteArrayOutputStream generateTableReport(String title,
                                                            List<List<String>> rows,
                                                            List<String> columns,
                                                            float[] widths) throws DocumentException {
        Document document = new Document(new Rectangle(595 , 842 ), 0f, 0f, 0f, 0f);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, bytes);
        document.open();

        Paragraph header = new Paragraph(title);
        header.setSpacingAfter(10);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        PdfPTable table = new PdfPTable(columns.size());
        table.setHeaderRows(1);
        table.setWidths(widths);
        table.setWidthPercentage(95f);
        columns.forEach(c -> table.addCell(new PdfPCell(new Phrase(c))));
        boolean b = true;
        for (List<String> row : rows) {
            for (String val : row) {
                PdfPCell cell = new PdfPCell(new Phrase(val));
                cell.setBackgroundColor(b ? BaseColor.LIGHT_GRAY : BaseColor.WHITE);
                table.addCell(cell);
            }
            b = !b;
        }
        document.add(table);

        document.addTitle(title);
        document.addCreator(CREATOR);

        document.close();
        return bytes;
    }
}
