package GUI;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
public class PDF{
    JPanel panel;
    public PDF(JTable invoiceTable, String StartTime, String PaymentTime, String ID)
    {
        try {
            Document document = new Document(new Rectangle(300, 500));
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/InvoicePDF/" + "Inv." + ID + ".pdf"));
            document.open();

            //---------------------- Header -----------------------//
            Paragraph HeaderHyphen = new Paragraph("---------------------------------------------------------");
            Paragraph Header = new Paragraph("Invoice", new Font(Font.FontFamily.TIMES_ROMAN, 25, Font.BOLD));
            Header.setAlignment(Element.ALIGN_CENTER);
            Paragraph hyphen = new Paragraph("----------------------------");
            hyphen.setAlignment(Element.ALIGN_CENTER);
            //----------------------------------------------------//

            //----------------------------------------------------//
            Paragraph IDInv = new Paragraph("ID: " + ID,
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            IDInv.setAlignment(Element.ALIGN_RIGHT);

            Paragraph Date = new Paragraph("Day: " + java.time.LocalDate.now().toString(),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            Date.setAlignment(Element.ALIGN_RIGHT);

            Paragraph StartTimePara = new Paragraph("In: " + StartTime,
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            StartTimePara.setAlignment(Element.ALIGN_RIGHT);

            Paragraph PaymentTimePara = new Paragraph("Out: " + PaymentTime,
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            PaymentTimePara.setAlignment(Element.ALIGN_RIGHT);

            Paragraph Cashier = new Paragraph("Cashier: " + LoginGUI.WelcomeUsername,
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            Cashier.setAlignment(Element.ALIGN_LEFT);

            Paragraph Customer = new Paragraph("Customer: " + InvoiceGUI.TFCustomerName.getText(),
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            //----------------------------------------------------//

            //---------------------- Table -----------------------//
            PdfPTable PDFTable = new PdfPTable(4);
            PDFTable.setWidthPercentage(125f);
            PDFTable.setWidths(new int[]{6, 3, 3, 3});
            int[] ColumnNeed = {1, 3, 2, 4};

            Font BoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

            for(var i : ColumnNeed)
            {
                PDFTable.addCell(new PdfPCell(new Phrase(invoiceTable.getModel().getColumnName(i), BoldFont)));
            }
            for(int i = 0; i < invoiceTable.getRowCount(); i++)
            {
                for(var j : ColumnNeed)
                {
                    PDFTable.addCell(invoiceTable.getModel().getValueAt(i, j).toString());
                }
            }
            for(int i = 0; i < invoiceTable.getRowCount(); i++)
            {
                for(var j : ColumnNeed)
                {
                    if(j == 4)
                    {
                        PDFTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                    }
                }
            }
            PDFTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            //----------------------------------------------------//

            //----------------------------------------------------//
            PdfPTable Footer = new PdfPTable(4);
            Footer.setWidthPercentage(125f);
            Footer.setWidths(new int[]{6, 3, 3, 3});
            String[] TotalInfo = {InvoiceGUI.TFTotalPrice.getText(), InvoiceGUI.TFCustomerPayment.getText(), InvoiceGUI.TFCustomerChange.getText()};
            for(int i = 0; i < 3; i++)
            {
                for(int j = 0; j < 4; j++)
                {
                    if(i == 0)
                    {
                        if(j == 0) Footer.addCell(new PdfPCell(new Phrase("Total", BoldFont)));
                        else if(j == 3) Footer.addCell(new PdfPCell(new Phrase(TotalInfo[0])));
                        else Footer.addCell(new PdfPCell(new Phrase("")));
                    }
                    if(i == 1)
                    {
                        if(j == 0) Footer.addCell(new PdfPCell(new Phrase("Customer Payment", BoldFont)));
                        else if(j == 3) Footer.addCell(new PdfPCell(new Phrase(TotalInfo[1])));
                        else Footer.addCell(new PdfPCell(new Phrase("")));
                    }
                    if(i == 2)
                    {
                        if(j == 0) Footer.addCell(new PdfPCell(new Phrase("Customer Change", BoldFont)));
                        else if(j == 3) Footer.addCell(new PdfPCell(new Phrase(TotalInfo[2])));
                        else Footer.addCell(new PdfPCell(new Phrase("")));
                    }
                }
            }
            for(int i = 0; i < 3; i++)
            {
                for(int j = 0; j < 4; j++)
                {
                    PdfPCell thiscell = Footer.getRow(i).getCells()[j];
                    thiscell.setBorder(Rectangle.NO_BORDER);
                }
            }
            //----------------------------------------------------//

            //----------------------------------------------------//
            Paragraph ThankYou = new Paragraph("Thank you! See you again!",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            ThankYou.setAlignment(Element.ALIGN_CENTER);
            //----------------------------------------------------//

            //----------------------------------------------------//
            document.add(Header);
            document.add(hyphen);
            document.add(IDInv);
            document.add(Date);
            document.add(StartTimePara);
            document.add(PaymentTimePara);
            document.add(Cashier);
            document.add(Customer);
            document.add(new Paragraph(" "));
            document.add(PDFTable);
            document.add(new Paragraph(" "));
            document.add(Footer);
            document.add(new Paragraph(" "));
            document.add(ThankYou);
            document.add(HeaderHyphen);

            document.setPageSize(new Rectangle(300, 100));
            //----------------------------------------------------//

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
