package com.gl.gestionclinique.Utilitaire.PDF;

import com.gl.gestionclinique.Model.Consultation;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class OrdonnancePdfGenerator extends PdfGenerator {

    private final Consultation consultation;

    public OrdonnancePdfGenerator(Consultation consultation) {
        this.consultation = consultation;
    }

    @Override
    public void generer(String cheminFichier) throws Exception {
        Document document = creerDocument(cheminFichier);
        ajouterEnTete(document, "ORDONNANCE MÉDICALE");

        PdfPTable infoTable = creerTableau(2, 1, 1);

        String nomPatient = consultation.getPatient() != null
                ? consultation.getPatient().getNom() + " " + consultation.getPatient().getPrenom()
                : "-";
        String nomMedecin = consultation.getMedecin() != null
                ? "Dr. " + consultation.getMedecin().getNom() + " " + consultation.getMedecin().getPrenom()
                : "-";
        String specialite = consultation.getMedecin() != null
                ? consultation.getMedecin().getSpecialite()
                : "-";

        infoTable.addCell(celluleInfo("Patient", nomPatient));
        infoTable.addCell(celluleInfo("Date", consultation.getDate().toString()));
        infoTable.addCell(celluleInfo("Médecin", nomMedecin));
        infoTable.addCell(celluleInfo("Spécialité", specialite));

        document.add(infoTable);
        document.add(Chunk.NEWLINE);

        ajouterSection(document, "Diagnostic", consultation.getDiagnostic());
        ajouterSection(document, "Prescription / Traitement", consultation.getPrescription());
        ajouterSection(document, "Observations", consultation.getObservations());

        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        Paragraph signature = new Paragraph("Signature du Médecin", FONT_LABEL);
        signature.setAlignment(Element.ALIGN_RIGHT);
        document.add(signature);

        Paragraph nomSigne = new Paragraph(nomMedecin, FONT_NORMAL);
        nomSigne.setAlignment(Element.ALIGN_RIGHT);
        document.add(nomSigne);

        document.close();
    }

    private void ajouterSection(Document document, String titre, String contenu) throws Exception {
        Paragraph t = new Paragraph(titre,
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, COLOR_PRIMARY));
        t.setSpacingBefore(10);
        t.setSpacingAfter(4);
        document.add(t);

        LineSeparator sep = new LineSeparator();
        sep.setLineColor(COLOR_PRIMARY);
        sep.setLineWidth(0.5f);
        document.add(new Chunk(sep));

        Paragraph contenuP = new Paragraph(contenu != null ? contenu : "-", FONT_NORMAL);
        contenuP.setSpacingBefore(6);
        contenuP.setSpacingAfter(8);
        document.add(contenuP);
    }

    private PdfPCell celluleInfo(String label, String valeur) {
        PdfPCell cell = new PdfPCell();
        cell.addElement(new Phrase(label + " : ", FONT_LABEL));
        cell.addElement(new Phrase(valeur, FONT_NORMAL));
        cell.setPadding(6);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        return cell;
    }
}