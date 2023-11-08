package raf.lazar.diplomski_aorp.model.izvestaj;

import com.itextpdf.text.DocumentException;

public interface Reports {

    byte[] getReport() throws DocumentException;
}
