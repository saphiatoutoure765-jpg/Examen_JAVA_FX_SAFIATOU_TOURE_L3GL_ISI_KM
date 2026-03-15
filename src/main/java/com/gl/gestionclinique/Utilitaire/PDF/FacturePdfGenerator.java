package com.gl.gestionclinique.Utilitaire.PDF;

import com.gl.gestionclinique.Model.Facture;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class FacturePdfGenerator extends PdfGenerator {

    private final Facture facture;

    public FacturePdfGenerator(Facture facture) {
        this.facture = facture;
    }

    @Override
    public void generer(String cheminFichier) throws Exception {
        Document document = creerDocument(cheminFichier);
        ajouterEnTete(document, "FACTURE");

        // Infos facture
        PdfPTable infoTable = creerTableau(2, 1, 1);

        infoTable.addCell(celluleInfo("N° Facture", String.valueOf(facture.getId())));
        infoTable.addCell(celluleInfo("Date", facture.getDate().toString()));

        if (facture.getConsultation() != null && facture.getConsultation().getPatient() != null) {
            String nomPatient = facture.getConsultation().getPatient().getNom()
                    + " " + facture.getConsultation().getPatient().getPrenom();
            infoTable.addCell(celluleInfo("Patient", nomPatient));
            String nomMedecin = facture.getConsultation().getMedecin() != null
                    ? "Dr. " + facture.getConsultation().getMedecin().getNom()
                    : "-";
            infoTable.addCell(celluleInfo("Médecin", nomMedecin));
        }

        infoTable.addCell(celluleInfo("Mode de paiement",
                facture.getModePaiement() != null ? facture.getModePaiement().toString() : "-"));
        infoTable.addCell(celluleInfo("Statut",
                facture.getStatut() != null ? facture.getStatut().toString() : "-"));

        document.add(infoTable);
        document.add(Chunk.NEWLINE);

        // Tableau montant
        PdfPTable montantTable = creerTableau(2, 3, 1);
        montantTable.addCell(celluleEntete("Description"));
        montantTable.addCell(celluleEntete("Montant (FCFA)"));
        montantTable.addCell(cellule("Consultation médicale"));
        montantTable.addCell(cellule(String.format("%.0f", facture.getMontantTotal())));

        PdfPCell totalLabel = new PdfPCell(new Phrase("TOTAL", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        totalLabel.setPadding(8);
        totalLabel.setBackgroundColor(new BaseColor(230, 230, 230));
        montantTable.addCell(totalLabel);

        PdfPCell totalVal = new PdfPCell(new Phrase(
                String.format("%.0f FCFA", facture.getMontantTotal()),
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, COLOR_PRIMARY)));
        totalVal.setPadding(8);
        totalVal.setBackgroundColor(new BaseColor(230, 230, 230));
        montantTable.addCell(totalVal);

        document.add(montantTable);

        document.add(Chunk.NEWLINE);
        Paragraph merci = new Paragraph("Merci de votre confiance.", FONT_NORMAL);
        merci.setAlignment(Element.ALIGN_CENTER);
        merci.setSpacingBefore(20);
        document.add(merci);

        document.close();
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
