package ua.nure.bieiaiev.SummaryTask4.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ua.nure.bieiaiev.SummaryTask4.entities.Subject;
import ua.nure.bieiaiev.SummaryTask4.entities.User;
import ua.nure.bieiaiev.SummaryTask4.entities.test.UsersTest;
import ua.nure.bieiaiev.SummaryTask4.exceptions.ApplicationException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.Messages;

public class ReportUtil implements Serializable {

    private static final long serialVersionUID = -5129073607312840043L;

    private static final Logger LOG = Logger.getLogger(ReportUtil.class);


    /**
     * Writes user's info into PDF file using iText library
     *
     * @param file
     * @param user
     * @param usersTests
     * @param sjList
     * @throws ApplicationException
     */
    public void writePDF(File file, User user, List<UsersTest> usersTests, List<Subject> sjList)
            throws ApplicationException {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.getAcroForm().setNeedAppearances(true);
            document.open();

            BaseFont unicode = BaseFont.createFont(getPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font font = new Font(unicode);

            Anchor anchorTarget = new Anchor("User's Tests Report");
            anchorTarget.setName("BackToTop");

            Paragraph paragraph1 = new Paragraph();
            paragraph1.setFont(font);
            paragraph1.setSpacingBefore(50);
            paragraph1.add(anchorTarget);

            document.add(paragraph1);

            Paragraph paragraph2 = new Paragraph();
            paragraph2.setFont(font);
            paragraph2.setSpacingBefore(50);
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            paragraph2.add(firstName + " " + lastName + ", " + "this is your tests report:");
            paragraph2.setSpacingAfter(50);
            document.add(paragraph2);

            Paragraph paragraph3 = new Paragraph();
            paragraph3.setFont(font);
            String average = String.valueOf(user.getTestsResult());
            String testsCount = String.valueOf(user.getTestsCount());
            paragraph3.add("Your average result is " + average + "%" + System.lineSeparator()
                    + "Number of passed tests: " + testsCount);
            paragraph3.setSpacingAfter(50);
            document.add(paragraph3);

            if (usersTests != null && !usersTests.isEmpty()) {
                PdfPTable t = createTable(usersTests, sjList, font);
                document.add(t);
            } else {
                Paragraph paragraph4 = new Paragraph();
                paragraph4.setFont(font);
                paragraph4.add("You have no available tests to view");
                document.add(paragraph4);
            }
            document.close();
            writer.close();

        } catch (DocumentException | IOException e) {
            LOG.error(Messages.ERR_CANNOT_GENERATE_REPORT, e);
            throw new ApplicationException(Messages.ERR_CANNOT_GENERATE_REPORT, e);
        }

    }

    /**
     * Writes user's info into XML file using JAXB
     *
     * @param file
     * @param usersTests
     * @throws ApplicationException
     */
    public void writeXML(File file, List<UsersTest> usersTests) throws ApplicationException {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(UsersTest.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            if (!usersTests.isEmpty()) {
                UsersTest usersTest = usersTests.get(0);
                jaxbMarshaller.marshal(usersTest, writer);
            }
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            for (int i = 0; i < usersTests.size(); i++) {
                if (i != 0) {
                    UsersTest usersTest = usersTests.get(i);
                    jaxbMarshaller.marshal(usersTest, writer);
                    writer.append(System.lineSeparator());
                }
            }
            FileWriter fw = new FileWriter(file, true);
            fw.write(writer.toString());
            fw.close();
        } catch (JAXBException | IOException e) {
            LOG.error(Messages.ERR_CANNOT_GENERATE_REPORT, e);
            throw new ApplicationException(Messages.ERR_CANNOT_GENERATE_REPORT, e);
        }
    }

    /**
     * Writes user's info into DOC file using Apache POI library
     *
     * @param user
     * @param file
     * @param usersTests
     * @param sjList
     * @throws ApplicationException
     */
    public void writeDOC(User user, File file, List<UsersTest> usersTests, List<Subject> sjList)
            throws ApplicationException {
        try {
            FileOutputStream out = new FileOutputStream(file);
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("User's tests report: ");

            XWPFParagraph paragraph1 = document.createParagraph();
            run = paragraph1.createRun();
            run.setText(user.getFirstName() + " " + user.getLastName() + " " + "this is your tests report:");

            XWPFParagraph paragraph2 = document.createParagraph();
            run = paragraph2.createRun();
            run.setText("Your average result is " + user.getTestsResult() + "%");

            XWPFParagraph paragraph3 = document.createParagraph();
            run = paragraph3.createRun();
            run.setText("Number of passed tests: " + user.getTestsCount());

            if (usersTests == null || usersTests.isEmpty()) {
                XWPFParagraph paragraph4 = document.createParagraph();
                run = paragraph4.createRun();
                run.setText("You have no available tests to view ");
            } else {

                XWPFTable table = document.createTable();

                XWPFTableRow tableRowOne = table.getRow(0);
                tableRowOne.getCell(0).setText("Test Name");
                tableRowOne.addNewTableCell().setText("Subject");
                tableRowOne.addNewTableCell().setText("Complexity");
                tableRowOne.addNewTableCell().setText("Test time (seconds)");
                tableRowOne.addNewTableCell().setText("Test Result");
                tableRowOne.addNewTableCell().setText("Test Date");

                for (UsersTest usersTest : usersTests) {
                    Subject sj = new Subject();
                    sj.setId(usersTest.getTestSubjectId());
                    String sjName = sjList.get(sjList.indexOf(sj)).getName();

                    XWPFTableRow tableRow = table.createRow();
                    tableRow.getCell(0).setText(usersTest.getTestName());
                    tableRow.getCell(1).setText(sjName);
                    tableRow.getCell(2).setText(String.valueOf(usersTest.getComplexity()));
                    tableRow.getCell(3).setText(String.valueOf(usersTest.getTestTime()));
                    tableRow.getCell(4).setText(String.valueOf(usersTest.getTestResult()));
                    tableRow.getCell(5).setText(usersTest.getDate().toString());
                }
            }
            document.write(out);
            out.close();
            document.close();
        } catch (IOException e) {
            LOG.error(Messages.ERR_CANNOT_GENERATE_REPORT, e);
            throw new ApplicationException(Messages.ERR_CANNOT_GENERATE_REPORT, e);
        }
    }

    /**
     * Method to create and fill the table in PDF document
     *
     * @param usersTests
     * @param sjList
     * @param font
     * @return
     */
    private PdfPTable createTable(List<UsersTest> usersTests, List<Subject> sjList, Font font) {
        PdfPTable t = new PdfPTable(6);
        t.setSpacingBefore(25);

        t.setSpacingAfter(25);
        Phrase p1 = new Phrase("Test Name");
        p1.setFont(font);
        PdfPCell c1 = new PdfPCell(p1);

        Phrase p2 = new Phrase("Subject");
        p2.setFont(font);
        PdfPCell c2 = new PdfPCell(p2);

        Phrase p3 = new Phrase("Complexity");
        p3.setFont(font);
        PdfPCell c3 = new PdfPCell(p3);

        Phrase p4 = new Phrase("Test time (seconds)");
        p4.setFont(font);
        PdfPCell c4 = new PdfPCell(p4);

        Phrase p5 = new Phrase("Test Result");
        p5.setFont(font);
        PdfPCell c5 = new PdfPCell(p5);

        Phrase p6 = new Phrase("Test Date");
        p6.setFont(font);
        PdfPCell c6 = new PdfPCell(p6);

        t.addCell(c1);
        t.addCell(c2);
        t.addCell(c3);
        t.addCell(c4);
        t.addCell(c5);
        t.addCell(c6);

        for (UsersTest usersTest : usersTests) {

            Subject sj = new Subject();
            sj.setId(usersTest.getTestSubjectId());
            String sjName = sjList.get(sjList.indexOf(sj)).getName();

            Phrase p11 = new Phrase();
            p11.setFont(font);
            p11.add(usersTest.getTestName());
            PdfPCell c11 = new PdfPCell(p11);

            Phrase p21 = new Phrase();
            p21.setFont(font);
            p21.add(sjName);
            PdfPCell c21 = new PdfPCell(p21);

            Phrase p31 = new Phrase(String.valueOf(usersTest.getComplexity()));
            p31.setFont(font);
            PdfPCell c31 = new PdfPCell(p31);

            Phrase p41 = new Phrase(String.valueOf(usersTest.getTestTime()));
            p41.setFont(font);
            PdfPCell c41 = new PdfPCell(p41);

            Phrase p51 = new Phrase(String.valueOf(usersTest.getTestResult()) + "%");
            p51.setFont(font);
            PdfPCell c51 = new PdfPCell(p51);

            Phrase p61 = new Phrase(usersTest.getDate().toString());
            p61.setFont(font);
            PdfPCell c61 = new PdfPCell(p61);

            t.addCell(c11);
            t.addCell(c21);
            t.addCell(c31);
            t.addCell(c41);
            t.addCell(c51);
            t.addCell(c61);
        }
        return t;
    }

    /**
     * Method returns an absolute path to the font supports сyrillic in compiled
     * web application
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getPath() throws UnsupportedEncodingException {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("/build/production/SummaryTask4/");
        fullPath = pathArr[0];

        String reponsePath = "";
        reponsePath = new File(fullPath).getPath() + File.separatorChar + "WebContent" + File.separatorChar + "font" + File.separatorChar + "FreeSans.ttf";
        return reponsePath;
    }
}
