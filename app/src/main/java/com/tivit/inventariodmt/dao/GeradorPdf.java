package com.tivit.inventariodmt.dao;

import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by alex.almeida on 05/09/2016.
 */
public class GeradorPdf {

    public static final String RESULT = "/relatorioEquipamento.pdf";

    public void criarPdf(String filename) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() +""+ RESULT));
        document.open();
        document.add(new Paragraph("Hello World!!!"));
        document.close();
    }
}
