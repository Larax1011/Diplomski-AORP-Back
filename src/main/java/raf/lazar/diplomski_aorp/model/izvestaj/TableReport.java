package raf.lazar.diplomski_aorp.model.izvestaj;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class TableReport implements Reports {
    private List<String> columns;
    private List<List<String>> rows;
    private String title;
    private float[] widths;

    public TableReport(String title, List<String> columns, List<List<String>> rows, float[] widths) {
        this.rows = rows;
        this.columns = columns;
        this.title = title;
        this.widths = widths;
    }

    @Override
    public byte[] getReport() throws DocumentException {
        return ReportBuilder.generateTableReport(title, rows, columns,widths).toByteArray();
    }
}