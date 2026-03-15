package com.gl.gestionclinique.Utilitaire.PDF;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileOutputStream;
import java.io.InputStream;

public abstract class PdfGenerator {

    protected static final Font FONT_TITLE  = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD,   BaseColor.DARK_GRAY);
    protected static final Font FONT_HEADER = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD,   BaseColor.WHITE);
    protected static final Font FONT_NORMAL = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
    protected static final Font FONT_LABEL  = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD,   BaseColor.DARK_GRAY);
    protected static final BaseColor COLOR_PRIMARY = new BaseColor(41, 128, 185);

    public abstract void generer(String cheminFichier) throws Exception;

    protected Document creerDocument(String cheminFichier) throws Exception {
        Document document = new Document(PageSize.A4, 40, 40, 60, 40);
        PdfWriter.getInstance(document, new FileOutputStream(cheminFichier));
        document.open();
        return document;
    }

    protected void ajouterEnTete(Document document, String titre) throws Exception {
        try {
            InputStream logoStream = getClass().getResourceAsStream(
                    "/com/gl/gestionclinique/images/logo.png");
            if (logoStream != null) {
                Image logo = Image.getInstance(logoStream.readAllBytes());
                logo.scaleToFit(80, 80);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
            }
        } catch (Exception ignored) {}

        Paragraph nomClinique = new Paragraph("Clinique Médicale El Amine", FONT_TITLE);
        nomClinique.setAlignment(Element.ALIGN_CENTER);
        nomClinique.setSpacingAfter(4);
        document.add(nomClinique);

        Font sousTitre = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);
        Paragraph adresse = new Paragraph("Dakar, Sénégal | Tel: +221 77 000 00 00", sousTitre);
        adresse.setAlignment(Element.ALIGN_CENTER);
        adresse.setSpacingAfter(10);
        document.add(adresse);

        LineSeparator line = new LineSeparator();
        line.setLineColor(COLOR_PRIMARY);
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);

        Paragraph titreParagraph = new Paragraph(titre,
                new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, COLOR_PRIMARY));
        titreParagraph.setAlignment(Element.ALIGN_CENTER);
        titreParagraph.setSpacingBefore(6);
        titreParagraph.setSpacingAfter(16);
        document.add(titreParagraph);
    }

    protected PdfPTable creerTableau(int colonnes, float... largeurs) throws Exception {
        PdfPTable table = new PdfPTable(colonnes);
        table.setWidthPercentage(100);
        table.setWidths(largeurs);
        table.setSpacingBefore(10);
        return table;
    }

    protected PdfPCell celluleEntete(String texte) {
        PdfPCell cell = new PdfPCell(new Phrase(texte, FONT_HEADER));
        cell.setBackgroundColor(COLOR_PRIMARY);
        cell.setPadding(8);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    protected PdfPCell cellule(String texte) {
        PdfPCell cell = new PdfPCell(new Phrase(texte, FONT_NORMAL));
        cell.setPadding(6);
        return cell;
    }
}