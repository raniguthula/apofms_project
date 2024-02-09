package in_Apcfss.Apofms.api.ofmsapi.servicesimpl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import in_Apcfss.Apofms.api.ofmsapi.Execptions.ResourceNotFoundException;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.Pdf_View_Repo;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.UsersSecurityRepo;
import in_Apcfss.Apofms.api.ofmsapi.services.Pdf_View_Service;

@Service
public class Pdf_View_ServiceImpl implements Pdf_View_Service {

	@Autowired
	Pdf_View_Repo pdf_View_Repo;
	@Autowired
	UsersSecurityRepo usersSecurityRepo;

	private final Log logger = LogFactory.getLog(getClass());

	public final String RUPUEE_ICON = "₹";

	final String CreatedBy = "CreatedBy";

	// NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en",
	// "IN"));
	private static final Font subTableHeadingFonts = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	private static final Font subTableMainHeadingFonts = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	private static final Font TableHeadingFonts = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
	private static final Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
	private static final Font HeadingFonts = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);

	Font mainHeadingFonts = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	private static final Font MainHeadingFonts = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	private static final Font signFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	public Font color = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, subheaderColor);
	private static final Font underlineFont = new Font(Font.FontFamily.HELVETICA, 12, Font.UNDERLINE);

	private static final BaseColor headerColor = new BaseColor(215, 225, 247);
	private static final BaseColor headerColor1 = new BaseColor(30, 55, 112, 1);
	private static final BaseColor borderColor = new BaseColor(248, 151, 115);
	private static final BaseColor subheaderColor = new BaseColor(255, 255, 255);
	// private static final BaseColor subchildheaderColor = new BaseColor(242, 242,
	// 242);
	private static final BaseColor qrcodeColor = new BaseColor(255, 255, 255);

	//
	private static final BaseColor subchildheaderColor = new BaseColor(242, 242, 242);

	private static final BaseColor specColor = new BaseColor(235, 247, 247);

	private static String logo = "classpath:/images/Andhra-Pradesh-AP-Govt-Logo.png";

	public Document getRegularDocument() {
		Document document = new Document(PageSize.A4, 30, 30, 30, 20);

		return document;
	}

	// ================================RECEIPT VOCHER
	public ByteArrayInputStream get_receiptvoucher_PDF(String payment_receipt_id)
			throws IOException, ResourceNotFoundException, WriterException {

		Document document = getRegularDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Paragraph heading = new Paragraph("", subTableHeadingFonts);
		System.out.println("hello");
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);
		try {
			PdfWriter.getInstance(document, out);
			document.open();
			Image jpg11 = Image.getInstance("classpath:/images/Andhra-Pradesh-AP-Govt-Logo.png");
			Map<String, Object> query_data = pdf_View_Repo.getdata_by_transection_id(payment_receipt_id);
			int x = 0, y = 900;
			x = 20;
			y -= 150;
			jpg11.setAbsolutePosition(x, y);
			float desiredWidth = 70;
			float scaleFactor = desiredWidth / jpg11.getWidth();
			jpg11.scalePercent(scaleFactor * 100);
			document.add(jpg11);

			heading = new Paragraph(
					"Andhra Pradesh State Housing Corporation Limited\nNirman Bhavan,Plot No 11 & 12,\nAuto Nagar,Vijayawada, A.P-520007",
					subTableMainHeadingFonts);

			heading.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(heading);
			String qrData = "Transaction ID: " + query_data.get("payment_receipt_id") + "\nReceipt No: "
					+ query_data.get("receiptno") + "\nPayment Date: " + query_data.get("date")
					+ "\nType of Transaction: " + query_data.get("cash_bank") + "\nBank Name & A/c.No: "
					+ query_data.get("banknameaccountno") + "\nReceipt Amount: " + query_data.get("receiptamount")
					+ "\nBank A/c Balance: " + query_data.get("balance_in_account")
					+ "\nFrom Whom the Amount Received: " + query_data.get("name") + "\nPayment Mode: "
					+ query_data.get("mode") + "\nDescription of the IBT: " + query_data.get("receipt_description");

			int width = 150;
			int height = 150;
			int a = 500; // Adjust the x-coordinate for your desired position
			int b = 750; // Adjust the y-coordinate for your desired position
			BitMatrix bitMatrix = new MultiFormatWriter().encode(qrData, BarcodeFormat.QR_CODE, width, height);
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			ByteArrayOutputStream qrOutputStream = new ByteArrayOutputStream();
			try {
				ImageIO.write(qrImage, "png", qrOutputStream);
			} catch (IOException e) {
				e.printStackTrace(); // Handle the exception properly
			}
			Image qrCodeImage = Image.getInstance(qrOutputStream.toByteArray());
			qrCodeImage.setAbsolutePosition(a, b);
			qrCodeImage.scalePercent(50); // Adjust scale as needed
			qrCodeImage.setAlignment(Element.ALIGN_RIGHT);
			document.add(qrCodeImage);
			System.out.println("query_data.get(\"payment_receipt_id\")" + query_data.get("payment_receipt_id"));
			Object receipt_id = query_data.get("payment_receipt_id");
			Map<String, String> dist_data = pdf_View_Repo.getReceiptdist_data(query_data.get("payment_receipt_id"));

			PdfPTable table3 = new PdfPTable(1);
			table3.setWidths(new int[] { 50 });
			table3.setWidthPercentage(100);
			PdfPCell c44;

			c44 = new PdfPCell(new Phrase("District Name", MainHeadingFonts));
			c44.setBorder(0);
			c44.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table3.addCell(c44);
			c44 = new PdfPCell(new Phrase(dist_data.get("display_name") + "", subTableHeadingFonts));
			c44.setBorder(0);
			c44.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table3.addCell(c44);
			document.add(table3);

			LineSeparator ls = new LineSeparator();
			document.add(new Chunk(ls));

			document.add(getTitle("Receipt Voucher"));
			document.add(setSpecTable());

			PdfPTable table = new PdfPTable(2);
			table.setWidths(new int[] { 40, 60 });
			table.setWidthPercentage(100);

			PdfPCell cell = new PdfPCell();

			Phrase phrase1 = new Phrase();
			phrase1.add(new Chunk("Transaction Id No ", MainHeadingFonts));
			table.addCell(phrase1);
			Phrase phrase1_1 = new Phrase();
			phrase1_1.add(new Chunk(query_data.get("payment_receipt_id") + "", subTableHeadingFonts));
			table.addCell(phrase1_1);

			Phrase phrase2 = new Phrase();
			phrase2.add(new Chunk("Receipt No", MainHeadingFonts));
			table.addCell(phrase2);
			Phrase phrase2_1 = new Phrase();
			phrase2_1.add(new Chunk(query_data.get("receiptno") + "", subTableHeadingFonts));
			table.addCell(phrase2_1);

			Phrase phrase3 = new Phrase();
			phrase3.add(new Chunk("Received Date", MainHeadingFonts));
			table.addCell(phrase3);
			Phrase phrase3_1 = new Phrase();
			phrase3_1.add(new Chunk(query_data.get("date") + "", subTableHeadingFonts));
			table.addCell(phrase3_1);

			Phrase phrase4 = new Phrase();
			phrase4.add(new Chunk("Type of Transanction", MainHeadingFonts));
			table.addCell(phrase4);
			Phrase phrase4_1 = new Phrase();
			phrase4_1.add(new Chunk(query_data.get("cash_bank") + "", subTableHeadingFonts));
			table.addCell(phrase4_1);

			Phrase phrase5 = new Phrase();
			phrase5.add(new Chunk("Bank Name & A/c.No", MainHeadingFonts));
			table.addCell(phrase5);
			Phrase phrase5_1 = new Phrase();
			phrase5_1.add(new Chunk(query_data.get("banknameaccountno") + "", subTableHeadingFonts));
			table.addCell(phrase5_1);

			Phrase phrase6 = new Phrase();
			phrase6.add(new Chunk("Bank A/c Balance", MainHeadingFonts));
			table.addCell(phrase6);
			Phrase phrase6_1 = new Phrase();
			phrase6_1.add(new Chunk(query_data.get("balance_in_account") + "", subTableHeadingFonts));
			table.addCell(phrase6_1);

			Phrase phrase7 = new Phrase();
			phrase7.add(new Chunk("Amount Received in Rs./- ", MainHeadingFonts));
			table.addCell(phrase7);
			Phrase phrase7_1 = new Phrase();
			phrase7_1.add(new Chunk(query_data.get("receiptamount") + "", subTableHeadingFonts));
			table.addCell(phrase7_1);

			Phrase phrase8 = new Phrase();
			phrase8.add(new Chunk("From Whom the Amount Received", MainHeadingFonts));
			table.addCell(phrase8);
			Phrase phrase8_1 = new Phrase();
			phrase8_1.add(new Chunk(query_data.get("name") + "", subTableHeadingFonts));
			table.addCell(phrase8_1);

			Phrase phrase9 = new Phrase();
			phrase9.add(new Chunk("Received Mode", MainHeadingFonts));
			table.addCell(phrase9);
			Phrase phrase9_1 = new Phrase();
			phrase9_1.add(new Chunk(query_data.get("mode") + "", subTableHeadingFonts));
			table.addCell(phrase9_1);

			Phrase phrase10 = new Phrase();
			phrase10.add(new Chunk("Description of the Receipt", MainHeadingFonts));
			table.addCell(phrase10);
			Phrase phrase10_1 = new Phrase();
			phrase10_1.add(new Chunk(query_data.get("receipt_description") + "", subTableHeadingFonts));
			table.addCell(phrase10_1);

			Phrase phrase11 = new Phrase();
			phrase11.add(new Chunk("Cheque / UTR No", MainHeadingFonts));
			table.addCell(phrase11);
			Phrase phrase11_1 = new Phrase();
			phrase11_1.add(new Chunk(query_data.get("cheque_dd_receipt_no") + "", subTableHeadingFonts));
			table.addCell(phrase11_1);

//			Phrase phrase12 = new Phrase();
//			phrase12.add(new Chunk("Status", MainHeadingFonts));
//			table.addCell(phrase12);
//			Phrase phrase12_1 = new Phrase();
//			phrase12_1.add(new Chunk("", subTableHeadingFonts));
//			table.addCell(phrase12_1);

			document.add(table);

			heading = new Paragraph("\n", MainHeadingFonts);
			document.add(heading);

			// for heads

			List<Map<String, Object>> headsData = pdf_View_Repo.getheads(payment_receipt_id);
			PdfPTable table1 = new PdfPTable(4);
			table1.setWidths(new int[] { 20, 75, 75, 40 });

			table1.setWidthPercentage(100);

			PdfPCell c1 = new PdfPCell();

			c1 = new PdfPCell(new Phrase("S.No", MainHeadingFonts));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);

			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase(" Head Names", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Sub-Head Names", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Amount", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);
			
			if (headsData != null) {
				
				AtomicInteger sno = new AtomicInteger(1);
				headsData.forEach(data -> {
					PdfPCell c12;

					c12 = new PdfPCell(new Phrase(String.valueOf(sno.get())));
					c12.setHorizontalAlignment(Element.ALIGN_CENTER);
					table1.addCell(c12);
					if (data.get("heads") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("heads")), subTableHeadingFonts));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("---")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}

					if (data.get("subheads") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("subheads")), subTableHeadingFonts));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("---")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}

//					if (data.get("amount") != null) {
//						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("amount")), subTableHeadingFonts));
//						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
////						//c12.setPadding(5f);
//						table1.addCell(c12);
//					} else {
//						c12 = new PdfPCell(new Phrase(String.valueOf("----")));
//						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
//						// c12.setPadding(5f);
//						table1.addCell(c12);
//					}
				    double totalAmount = 0.0;
					 if (data.get("amount") != null) {
				            String amountString = String.valueOf(data.get("amount"));
				            c12 = new PdfPCell(new Phrase(amountString, subTableHeadingFonts));
				            c12.setHorizontalAlignment(Element.ALIGN_CENTER);
				            table1.addCell(c12);

				            // Calculate and add the amount to the total
				            try {
				                double amount = Double.parseDouble(amountString);
				                totalAmount += amount;
				            } catch (NumberFormatException e) {
				                // Handle parsing exceptions, if needed
				            }
				            System.out.println("totalAmount"+totalAmount);
				        } else {
							c12 = new PdfPCell(new Phrase(String.valueOf("----")));
							c12.setHorizontalAlignment(Element.ALIGN_CENTER);
							// c12.setPadding(5f);
							table1.addCell(c12);
				        }
					sno.incrementAndGet(); // Increment the serial number
				});
			}

			document.add(table1);

			PdfPTable totaltable = new PdfPTable(2);
			totaltable.setWidths(new int[] { 170, 40 });
			totaltable.setWidthPercentage(100);

			PdfPCell totalc1;

			totalc1 = new PdfPCell(new Phrase("Total Heads Amount", MainHeadingFonts));
			totalc1.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalc1.setPadding(5f);
			totaltable.addCell(totalc1);
			Object sumValue = headsData.get(0).get("receiptsum");
			System.out.println("sumValue.." + sumValue);

			totalc1 = new PdfPCell(new Phrase(String.valueOf(sumValue), MainHeadingFonts));
			totalc1.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalc1.setPadding(5f);
			totaltable.addCell(totalc1);

			document.add(totaltable);

			heading = new Paragraph("\n\n", subTableHeadingFonts);
			document.add(heading);
			PdfPTable table21 = new PdfPTable(3);
			table21.setWidths(new int[] { 45, 45, 60 });
			table21.setWidthPercentage(100);

			PdfPCell c31;

			c31 = new PdfPCell(new Phrase("Prepared By", subTableHeadingFonts));
			c31.setBorder(0);
			c31.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			table21.addCell(c31);

			c31 = new PdfPCell(new Phrase("Manager / Dy.GM (1st Sign)", subTableHeadingFonts));
			c31.setBorder(0);
			c31.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			table21.addCell(c31);

			c31 = new PdfPCell(new Phrase("PD(H) / GM(F) / GM(A) / DHH (2nd Sign)", subTableHeadingFonts));
			c31.setBorder(0);
			c31.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			table21.addCell(c31);

			document.add(table21);
			heading = new Paragraph("\n\n\n", subTableHeadingFonts);
			document.add(heading);
			heading = new Paragraph("Signature Of Receiver", subTableHeadingFonts);

			heading.setAlignment(Element.ALIGN_RIGHT);
			document.add(heading);

			Object timestampObject = query_data.get("timestamp"); 
			System.out.println("timestampObject" + timestampObject);

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String formattedTimestamp = dateFormat.format((Date) timestampObject);

			Phrase phraseTimestamp = new Phrase();
			phraseTimestamp.add(new Chunk("\nTimestamp : ", MainHeadingFonts));
			phraseTimestamp.add(new Chunk(formattedTimestamp, subTableHeadingFonts));

			PdfPTable tableTimestamp = new PdfPTable(1);
			tableTimestamp.setWidthPercentage(100);
			PdfPCell cellTimestamp = new PdfPCell(phraseTimestamp);
			cellTimestamp.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellTimestamp.setBorder(0);
			tableTimestamp.addCell(cellTimestamp);

			// Add the table to the document
			document.add(tableTimestamp);

			document.close();
		} catch (DocumentException e) {

			logger.info(e);

		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	// ============================PAYMENT VOCHER==============
	public ByteArrayInputStream get_paymentvoucher_PDF(String payment_receipt_id)
			throws IOException, ResourceNotFoundException, WriterException {
		Document document = getRegularDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Paragraph heading = new Paragraph("", subTableHeadingFonts);
		System.out.println("hello");
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);

		try {
			// P032006141

			PdfWriter.getInstance(document, out);

			document.open();
			Image jpg11 = Image.getInstance("classpath:/images/Andhra-Pradesh-AP-Govt-Logo.png");
			Map<String, Object> query_data = pdf_View_Repo.getdata_by_transection_id(payment_receipt_id);
			int x = 0, y = 900;
			x = 20;
			y -= 150;
			jpg11.setAbsolutePosition(x, y);
			float desiredWidth = 70;
			float scaleFactor = desiredWidth / jpg11.getWidth();
			jpg11.scalePercent(scaleFactor * 100);
			document.add(jpg11);
			heading = new Paragraph(
					"Andhra Pradesh State Housing Corporation Limited\n Nirman Bhavan,Plot No 11 & 12, Auto Nagar,\nVijayawada, A.P-520007",
					subTableMainHeadingFonts);

			heading.setAlignment(Paragraph.ALIGN_CENTER);

			document.add(heading);

			String qrData = "Transaction ID: " + query_data.get("payment_receipt_id") + "\nReceipt No: "
					+ query_data.get("receiptno") + "\nReceived Date: " + query_data.get("date")
					+ "\nType of Transaction: " + query_data.get("cash_bank") + "\nBank Name & A/c.No: "
					+ query_data.get("banknameaccountno") + "\nPayment Amount: " + query_data.get("receiptamount")
					+ "\nBank A/c Balance: " + query_data.get("balance_in_account")
					+ "\nFrom Whom the Amount Received: " + query_data.get("name") + "\nReceived Mode: "
					+ query_data.get("mode") + "\nDescription of the Receipt: " + query_data.get("receipt_description");

			int width = 150;
			int height = 150;
			int a = 500; // Adjust the x-coordinate for your desired position
			int b = 750; // Adjust the y-coordinate for your desired position
			BitMatrix bitMatrix = new MultiFormatWriter().encode(qrData, BarcodeFormat.QR_CODE, width, height);
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			ByteArrayOutputStream qrOutputStream = new ByteArrayOutputStream();
			try {
				ImageIO.write(qrImage, "png", qrOutputStream);
			} catch (IOException e) {
				e.printStackTrace(); // Handle the exception properly
			}
			Image qrCodeImage = Image.getInstance(qrOutputStream.toByteArray());
			qrCodeImage.setAbsolutePosition(a, b);
			qrCodeImage.scalePercent(50); // Adjust scale as needed
			qrCodeImage.setAlignment(Element.ALIGN_RIGHT);
			document.add(qrCodeImage);
			Map<String, String> dist_data = pdf_View_Repo.getPaymentdist_data(query_data.get("payment_receipt_id"));

			PdfPTable table3 = new PdfPTable(1);
			table3.setWidths(new int[] { 50 });
			table3.setWidthPercentage(100);
			PdfPCell c44;

			c44 = new PdfPCell(new Phrase("District Name", MainHeadingFonts));
			c44.setBorder(0);
			c44.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table3.addCell(c44);
			c44 = new PdfPCell(new Phrase(dist_data.get("display_name") + "", subTableHeadingFonts));
			c44.setBorder(0);
			c44.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table3.addCell(c44);
			document.add(table3);

			LineSeparator ls = new LineSeparator();
			document.add(new Chunk(ls));
			document.add(getTitle("Payment Voucher"));
			document.add(setSpecTable());

			PdfPTable table = new PdfPTable(2);
			table.setWidths(new int[] { 50, 50 });
			table.setWidthPercentage(100);

			Phrase phrase1 = new Phrase();
			phrase1.add(new Chunk("Transaction Id No ", MainHeadingFonts));
			table.addCell(phrase1);
			Phrase phrase1_1 = new Phrase();
			phrase1_1.add(new Chunk(query_data.get("payment_receipt_id") + "", subTableHeadingFonts));
			table.addCell(phrase1_1);
			Phrase phrase2 = new Phrase();
			phrase2.add(new Chunk("Voucher No", MainHeadingFonts));
			table.addCell(phrase2);
			Phrase phrase2_1 = new Phrase();
			phrase2_1.add(new Chunk(query_data.get("receiptno") + "", subTableHeadingFonts));
			table.addCell(phrase2_1);

			Phrase phrase3 = new Phrase();
			phrase3.add(new Chunk("Payment Date", MainHeadingFonts));
			table.addCell(phrase3);
			Phrase phrase3_1 = new Phrase();
			phrase3_1.add(new Chunk(query_data.get("date") + "", subTableHeadingFonts));
			table.addCell(phrase3_1);

			Phrase phrase4 = new Phrase();
			phrase4.add(new Chunk("Type of Transanction", MainHeadingFonts));
			table.addCell(phrase4);
			Phrase phrase4_1 = new Phrase();
			phrase4_1.add(new Chunk(query_data.get("cash_bank") + "", subTableHeadingFonts));
			table.addCell(phrase4_1);

			Phrase phrase5 = new Phrase();
			phrase5.add(new Chunk("Bank Name & A/c.No", MainHeadingFonts));
			table.addCell(phrase5);
			Phrase phrase5_1 = new Phrase();
			phrase5_1.add(new Chunk(query_data.get("banknameaccountno") + "", subTableHeadingFonts));
			table.addCell(phrase5_1);

			Phrase phrase6 = new Phrase();
			phrase6.add(new Chunk("Bank A/c Balance", MainHeadingFonts));
			table.addCell(phrase6);
			Phrase phrase6_1 = new Phrase();
			phrase6_1.add(new Chunk(query_data.get("balance_in_account") + "", subTableHeadingFonts));
			table.addCell(phrase6_1);

			Phrase phrase7 = new Phrase();
			phrase7.add(new Chunk("Amount Paid in Rs./- ", MainHeadingFonts));
			table.addCell(phrase7);
			Phrase phrase7_1 = new Phrase();
			phrase7_1.add(new Chunk(query_data.get("paymentamount") + "", subTableHeadingFonts));
			table.addCell(phrase7_1);

			Phrase phrase8 = new Phrase();
			phrase8.add(new Chunk("To Whom the Amount Paid", MainHeadingFonts));
			table.addCell(phrase8);
			Phrase phrase8_1 = new Phrase();
			phrase8_1.add(new Chunk(query_data.get("name") + "", subTableHeadingFonts));
			table.addCell(phrase8_1);

			Phrase phrase9 = new Phrase();
			phrase9.add(new Chunk("Payment Mode", MainHeadingFonts));
			table.addCell(phrase9);
			Phrase phrase9_1 = new Phrase();
			phrase9_1.add(new Chunk(query_data.get("mode") + "", subTableHeadingFonts));
			table.addCell(phrase9_1);

			Phrase phrase10 = new Phrase();
			phrase10.add(new Chunk("Description of the Payment", MainHeadingFonts));
			table.addCell(phrase10);
			Phrase phrase10_1 = new Phrase();
			phrase10_1.add(new Chunk(query_data.get("receipt_description") + "", subTableHeadingFonts));
			table.addCell(phrase10_1);

			Phrase phrase11 = new Phrase();
			phrase11.add(new Chunk("Cheque / UTR No", MainHeadingFonts));
			table.addCell(phrase11);
			Phrase phrase11_1 = new Phrase();
			phrase11_1.add(new Chunk(query_data.get("cheque_dd_receipt_no") + "", subTableHeadingFonts));
			table.addCell(phrase11_1);

//			Phrase phrase12 = new Phrase();
//			phrase12.add(new Chunk("Status", MainHeadingFonts));
//			table.addCell(phrase12);
//			Phrase phrase12_1 = new Phrase();
//			phrase12_1.add(new Chunk("", subTableHeadingFonts));
//			table.addCell(phrase12_1);

			document.add(table);

			heading = new Paragraph("\n", subTableHeadingFonts);
			document.add(heading);

			// for heads

			List<Map<String, Object>> headsData = pdf_View_Repo.getheads(payment_receipt_id);
			PdfPTable table1 = new PdfPTable(4);
			table1.setWidths(new int[] { 20, 75, 75, 40 });

			table1.setWidthPercentage(100);

			PdfPCell c1 = new PdfPCell();

			c1 = new PdfPCell(new Phrase("S.No", MainHeadingFonts));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);

//			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase(" Head Names", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Sub-Head Names", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Amount", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//			c1.setPadding(5f);
			table1.addCell(c1);

			if (headsData != null) {
				AtomicInteger sno = new AtomicInteger(1);
				headsData.forEach(data -> {
					PdfPCell c12;

					c12 = new PdfPCell(new Phrase(String.valueOf(sno.get())));
					c12.setHorizontalAlignment(Element.ALIGN_CENTER);
					table1.addCell(c12);

					if (data.get("heads") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("heads")), subTableHeadingFonts));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
//						//c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("-")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
//						//c12.setPadding(5f);
						table1.addCell(c12);
					}

					if (data.get("subheads") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("subheads")), subTableHeadingFonts));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
//						//c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("--")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}

					// debit
					if (data.get("amount") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("amount")), subTableHeadingFonts));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
//						//c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("--")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
//						//c12.setPadding(5f);
						table1.addCell(c12);
					}

					sno.incrementAndGet(); // Increment the serial number
				});
			}

			document.add(table1);

			PdfPTable totaltable = new PdfPTable(2);
			totaltable.setWidths(new int[] { 170, 40 });
			totaltable.setWidthPercentage(100);

			PdfPCell totalc1;

			totalc1 = new PdfPCell(new Phrase("Total Heads Amount", MainHeadingFonts));
			totalc1.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalc1.setPadding(5f);
			totaltable.addCell(totalc1);
			Object sumValue = headsData.get(0).get("paymentsum");
			System.out.println("sumValue.." + sumValue);

			totalc1 = new PdfPCell(new Phrase(String.valueOf(sumValue), MainHeadingFonts));
			totalc1.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalc1.setPadding(5f);
			totaltable.addCell(totalc1);

			document.add(totaltable);

			heading = new Paragraph("\n\n\n", subTableHeadingFonts);
			document.add(heading);
			PdfPTable table21 = new PdfPTable(3);
			table21.setWidths(new int[] { 45, 45, 60 });
			table21.setWidthPercentage(100);

			PdfPCell c31;

			c31 = new PdfPCell(new Phrase("Prepared By", subTableHeadingFonts));
			c31.setBorder(0);
			c31.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			table21.addCell(c31);

			c31 = new PdfPCell(new Phrase("Manager / Dy.GM (1st Sign)", subTableHeadingFonts));
			c31.setBorder(0);
			c31.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			table21.addCell(c31);

			c31 = new PdfPCell(new Phrase("PD(H) / GM(F) / GM(A) / DHH (2nd Sign)", subTableHeadingFonts));
			c31.setBorder(0);
			c31.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			table21.addCell(c31);

			document.add(table21);
			heading = new Paragraph("\n\n\n", subTableHeadingFonts);
			document.add(heading);
			heading = new Paragraph("Signature Of Receiver", subTableHeadingFonts);

			heading.setAlignment(Element.ALIGN_RIGHT);
			document.add(heading);

			Object timestampObject = query_data.get("timestamp");
			System.out.println("timestampObject" + timestampObject);

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String formattedTimestamp = dateFormat.format((Date) timestampObject);

			Phrase phraseTimestamp = new Phrase();
			phraseTimestamp.add(new Chunk("\nTimestamp : ", MainHeadingFonts));
			phraseTimestamp.add(new Chunk(formattedTimestamp, subTableHeadingFonts));

			PdfPTable tableTimestamp = new PdfPTable(1);
			tableTimestamp.setWidthPercentage(100);
			PdfPCell cellTimestamp = new PdfPCell(phraseTimestamp);
			cellTimestamp.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellTimestamp.setBorder(0);
			tableTimestamp.addCell(cellTimestamp);

			// Add the table to the document
			document.add(tableTimestamp);
			document.close();
		} catch (DocumentException e) {

			logger.info(e);

		}
		return new ByteArrayInputStream(out.toByteArray());
	}

//============================Journal VOCHER==============
	public ByteArrayInputStream get_journalvoucher_PDF(String paymentreceiptid)
			throws IOException, ResourceNotFoundException, WriterException {
		Document document = getRegularDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Paragraph heading = new Paragraph("", subTableHeadingFonts);
		System.out.println("hello");
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);

		String security_id = usersSecurityRepo.GetSecurity_Id(userid);

		try {
//P032006141

			PdfWriter.getInstance(document, out);

			document.open();
			Image jpg11 = Image.getInstance("classpath:/images/Andhra-Pradesh-AP-Govt-Logo.png");

			Map<String, Object> query_data = pdf_View_Repo.getdata_by_transection_id_journal(paymentreceiptid);
			int x = 0, y = 900;
			x = 20;
			y -= 150;
			jpg11.setAbsolutePosition(x, y);
			float desiredWidth = 70;
			float scaleFactor = desiredWidth / jpg11.getWidth();
			jpg11.scalePercent(scaleFactor * 100);
			document.add(jpg11);
			heading = new Paragraph(
					"Andhra Pradesh State Housing Corporation Limited\nNirman Bhavan,Plot No 11 & 12,\nAuto Nagar, Vijayawada, A.P-520007",
					subTableMainHeadingFonts);

			heading.setAlignment(Paragraph.ALIGN_CENTER);

			document.add(heading);

			String qrData = "Transaction ID: " + query_data.get("payment_receipt_id") + "\nVoucher No: "
					+ query_data.get("voucher_id") + "\nReceived Date: " + query_data.get("date") + "\nDescription: "
					+ query_data.get("description");

			int width = 150;
			int height = 150;
			int a = 500; // Adjust the x-coordinate for your desired position
			int b = 750; // Adjust the y-coordinate for your desired position
			BitMatrix bitMatrix = new MultiFormatWriter().encode(qrData, BarcodeFormat.QR_CODE, width, height);
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			ByteArrayOutputStream qrOutputStream = new ByteArrayOutputStream();
			try {
				ImageIO.write(qrImage, "png", qrOutputStream);
			} catch (IOException e) {
				e.printStackTrace(); // Handle the exception properly
			}
			Image qrCodeImage = Image.getInstance(qrOutputStream.toByteArray());
			qrCodeImage.setAbsolutePosition(a, b);
			qrCodeImage.scalePercent(50); // Adjust scale as needed
			qrCodeImage.setAlignment(Element.ALIGN_RIGHT);
			document.add(qrCodeImage);

			Map<String, String> dist_data = pdf_View_Repo.getJournaldist_data(query_data.get("payment_receipt_id"));

			PdfPTable table3 = new PdfPTable(1);
			table3.setWidths(new int[] { 50 });
			table3.setWidthPercentage(100);
			PdfPCell c44;

			c44 = new PdfPCell(new Phrase("District Name", MainHeadingFonts));
			c44.setBorder(0);
			c44.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table3.addCell(c44);
			c44 = new PdfPCell(new Phrase(dist_data.get("display_name") + "", subTableHeadingFonts));
			c44.setBorder(0);
			c44.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table3.addCell(c44);
			document.add(table3);

			LineSeparator ls = new LineSeparator();
			document.add(new Chunk(ls));
//			if(screen.equals("jv"))
//			{
//				document.add(getTitle("Journal Voucher"));
//			}
//			else if(screen.equals("sjv")){
//				document.add(getTitle("Salary Journal Voucher"));
//				
//			}
//			else if(screen.equals("ojv")){
//				document.add(getTitle("Online Journal Voucher"));
//				
//			}

			document.add(getTitle("Journal Voucher"));
			document.add(setSpecTable());

			PdfPTable table = new PdfPTable(2);
			table.setWidths(new int[] { 50, 50 });
			table.setWidthPercentage(100);
			PdfPCell cell = new PdfPCell();

			Phrase phrase1 = new Phrase();
			phrase1.add(new Chunk("Transaction Id No ", MainHeadingFonts));
			table.addCell(phrase1);
			Phrase phrase1_1 = new Phrase();
			phrase1_1.add(new Chunk(query_data.get("payment_receipt_id") + "", subTableHeadingFonts));
			table.addCell(phrase1_1);

			Phrase phrase2 = new Phrase();
			phrase2.add(new Chunk("Voucher Id", MainHeadingFonts));
			table.addCell(phrase2);
			Phrase phrase2_1 = new Phrase();
			phrase2_1.add(new Chunk(query_data.get("voucher_id") + "", subTableHeadingFonts));
			table.addCell(phrase2_1);

			Phrase phrase3 = new Phrase();
			phrase3.add(new Chunk("Date", MainHeadingFonts));
			table.addCell(phrase3);
			Phrase phrase3_1 = new Phrase();
			phrase3_1.add(new Chunk(query_data.get("date") + "", subTableHeadingFonts));
			table.addCell(phrase3_1);

			Phrase phrase4 = new Phrase();
			phrase4.add(new Chunk("Description", MainHeadingFonts));
			table.addCell(phrase4);
			Phrase phrase4_1 = new Phrase();
			phrase4_1.add(new Chunk(query_data.get("description") + "", subTableHeadingFonts));
			table.addCell(phrase4_1);

			document.add(table);

			heading = new Paragraph("\n", subTableHeadingFonts);
			document.add(heading);
			List<Map<String, Object>> headsData = pdf_View_Repo.getheads_journal(paymentreceiptid);
			// Heads
			PdfPTable table1 = new PdfPTable(5);
			table1.setWidths(new int[] { 20, 75, 75, 35, 35 });
			table1.setWidthPercentage(100);

			PdfPCell c1 = new PdfPCell();

			c1 = new PdfPCell(new Phrase("S.No", MainHeadingFonts));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);

			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase(" Head Names", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Sub-Head Names", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Debit", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Credit", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			if (headsData != null) {
				AtomicInteger sno = new AtomicInteger(1);
				headsData.forEach(data -> {
					PdfPCell c12;

					c12 = new PdfPCell(new Phrase(String.valueOf(sno.get())));
					c12.setHorizontalAlignment(Element.ALIGN_CENTER);
					table1.addCell(c12);

					if (data.get("heads") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("heads")), subTableHeadingFonts));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("-")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}

					if (data.get("subheads") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("subheads")), subTableHeadingFonts));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("-")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}
					if (data.get("debit") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("debit")), subTableHeadingFonts));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("-")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}
					if (data.get("credit") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("credit")), subTableHeadingFonts));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("-")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}

					sno.incrementAndGet(); // Increment the serial number
				});
			}

			document.add(table1);

			PdfPTable totaltable = new PdfPTable(3);
			totaltable.setWidths(new int[] { 170, 35, 35 });
			totaltable.setWidthPercentage(100);

			PdfPCell totalc1;

			totalc1 = new PdfPCell(new Phrase("Total Amount", MainHeadingFonts));
			totalc1.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalc1.setPadding(5f);
			totaltable.addCell(totalc1);

			Object debitsum = headsData.get(0).get("debitsum");
			System.out.println("sumValue.." + debitsum);

			totalc1 = new PdfPCell(new Phrase(String.valueOf(debitsum), MainHeadingFonts));
			totalc1.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalc1.setPadding(5f);
			totaltable.addCell(totalc1);

			Object creditsum = headsData.get(0).get("creditsum");
			System.out.println("sumValue.." + creditsum);

			totalc1 = new PdfPCell(new Phrase(String.valueOf(creditsum), MainHeadingFonts));
			totalc1.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalc1.setPadding(5f);
			totaltable.addCell(totalc1);

			document.add(totaltable);
			heading = new Paragraph("\n\n\n", subTableHeadingFonts);
			document.add(heading);
			PdfPTable table21 = new PdfPTable(3);
			table21.setWidths(new int[] { 45, 45, 60 });
			table21.setWidthPercentage(100);

			PdfPCell c31;

			c31 = new PdfPCell(new Phrase("Prepared By", subTableHeadingFonts));
			c31.setBorder(0);
			c31.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			table21.addCell(c31);

			c31 = new PdfPCell(new Phrase("Manager / Dy.GM (1st Sign)", subTableHeadingFonts));
			c31.setBorder(0);
			c31.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			table21.addCell(c31);

			c31 = new PdfPCell(new Phrase("PD(H) / GM(F) / GM(A) / DHH (2nd Sign)", subTableHeadingFonts));
			c31.setBorder(0);
			c31.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			table21.addCell(c31);

			document.add(table21);
			heading = new Paragraph("\n\n\n", subTableHeadingFonts);
			document.add(heading);
			heading = new Paragraph("Signature Of Receiver", subTableHeadingFonts);

			heading.setAlignment(Element.ALIGN_RIGHT);
			document.add(heading);
			Object timestampObject = query_data.get("timestamp");
			System.out.println("timestampObject" + timestampObject);

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String formattedTimestamp = dateFormat.format((Date) timestampObject);

			Phrase phraseTimestamp = new Phrase();
			phraseTimestamp.add(new Chunk("\nTimestamp : ", MainHeadingFonts));
			phraseTimestamp.add(new Chunk(formattedTimestamp, subTableHeadingFonts));

			PdfPTable tableTimestamp = new PdfPTable(1);
			tableTimestamp.setWidthPercentage(100);
			PdfPCell cellTimestamp = new PdfPCell(phraseTimestamp);
			cellTimestamp.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellTimestamp.setBorder(0);
			tableTimestamp.addCell(cellTimestamp);

			// Add the table to the document
			document.add(tableTimestamp);
			document.close();
		} catch (DocumentException e) {

			logger.info(e);

		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	@Override
	public ByteArrayInputStream interbanktransferpdf(String payment_receipt_id, String receipt_id)
			throws IOException, ResourceNotFoundException, WriterException {
		Document document = getRegularDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Paragraph heading = new Paragraph("", subTableHeadingFonts);
		System.out.println("hello");
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);

		try {
			PdfWriter.getInstance(document, out);

			document.open();
			Image jpg11 = Image.getInstance("classpath:/images/Andhra-Pradesh-AP-Govt-Logo.png");
			System.out.println("payment_receipt_id..." + payment_receipt_id);
			Map<String, Object> query_data = pdf_View_Repo.getdata_by_transection_id(payment_receipt_id);
			int x = 0, y = 900;
			x = 20;
			y -= 150;
			jpg11.setAbsolutePosition(x, y);
			float desiredWidth = 70;
			float scaleFactor = desiredWidth / jpg11.getWidth();
			jpg11.scalePercent(scaleFactor * 100);
			document.add(jpg11);
			heading = new Paragraph(
					"Andhra Pradesh State Housing Corporation Limited\nNirman Bhavan,Plot No 11 & 12,\nAuto Nagar, Vijayawada, A.P-520007",
					subTableMainHeadingFonts);

			heading.setAlignment(Paragraph.ALIGN_CENTER);

			document.add(heading);

			String qrData = "Transaction ID: " + query_data.get("payment_receipt_id") + "\nReceipt No: "
					+ query_data.get("receiptno") + "\nReceived Date: " + query_data.get("date")
					+ "\nType of Transaction: " + query_data.get("cash_bank") + "\nBank Name & A/c.No: "
					+ query_data.get("banknameaccountno") + "\nPayment Amount: " + query_data.get("receiptamount")
					+ "\nBank A/c Balance: " + query_data.get("balance_in_account")
					+ "\nFrom Whom the Amount Received: " + query_data.get("name") + "\nReceived Mode: "
					+ query_data.get("mode") + "\nDescription of the Receipt: " + query_data.get("receipt_description");

			int width = 150;
			int height = 150;
			int a = 500; // Adjust the x-coordinate for your desired position
			int b = 750; // Adjust the y-coordinate for your desired position
			BitMatrix bitMatrix = new MultiFormatWriter().encode(qrData, BarcodeFormat.QR_CODE, width, height);
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			ByteArrayOutputStream qrOutputStream = new ByteArrayOutputStream();
			try {
				ImageIO.write(qrImage, "png", qrOutputStream);
			} catch (IOException e) {
				e.printStackTrace(); // Handle the exception properly
			}
			Image qrCodeImage = Image.getInstance(qrOutputStream.toByteArray());
			qrCodeImage.setAbsolutePosition(a, b);
			qrCodeImage.scalePercent(50); // Adjust scale as needed
			qrCodeImage.setAlignment(Element.ALIGN_RIGHT);
			document.add(qrCodeImage);
			Map<String, String> dist_data = pdf_View_Repo.getInterBankdist_data(query_data.get("payment_receipt_id"));

			PdfPTable table3 = new PdfPTable(1);
			table3.setWidths(new int[] { 50 });
			table3.setWidthPercentage(100);
			PdfPCell c44;

			c44 = new PdfPCell(new Phrase("District Name", MainHeadingFonts));
			c44.setBorder(0);
			c44.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table3.addCell(c44);
			c44 = new PdfPCell(new Phrase(dist_data.get("display_name") + "", subTableHeadingFonts));
			c44.setBorder(0);
			c44.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table3.addCell(c44);
			document.add(table3);

			LineSeparator ls = new LineSeparator();
			document.add(new Chunk(ls));
			document.add(getTitle("Inter Bank Transfer"));
			document.add(setSpecTable());

			PdfPTable table = new PdfPTable(2);
			table.setWidths(new int[] { 50, 50 });
			table.setWidthPercentage(100);
			PdfPCell cell = new PdfPCell();

			Phrase phrase1 = new Phrase();
			phrase1.add(new Chunk("Transaction Id No ", MainHeadingFonts));
			table.addCell(phrase1);
			Phrase phrase1_1 = new Phrase();
			phrase1_1.add(new Chunk(query_data.get("payment_receipt_id") + "", subTableHeadingFonts));
			table.addCell(phrase1_1);

			Phrase phrase2 = new Phrase();
			phrase2.add(new Chunk("Receipt No", MainHeadingFonts));
			table.addCell(phrase2);
			Phrase phrase2_1 = new Phrase();
			phrase2_1.add(new Chunk(query_data.get("receiptno") + "", subTableHeadingFonts));
			table.addCell(phrase2_1);

			Phrase phrase3 = new Phrase();
			phrase3.add(new Chunk("Received Date", MainHeadingFonts));
			table.addCell(phrase3);
			Phrase phrase3_1 = new Phrase();
			phrase3_1.add(new Chunk(query_data.get("date") + "", subTableHeadingFonts));
			table.addCell(phrase3_1);

			Phrase phrase4 = new Phrase();
			phrase4.add(new Chunk("Type of Transanction", MainHeadingFonts));
			table.addCell(phrase4);
			Phrase phrase4_1 = new Phrase();
			phrase4_1.add(new Chunk(query_data.get("cash_bank") + "", subTableHeadingFonts));
			table.addCell(phrase4_1);

			Phrase phrase5 = new Phrase();
			phrase5.add(new Chunk("Bank Name & A/c.No", MainHeadingFonts));
			table.addCell(phrase5);
			Phrase phrase5_1 = new Phrase();
			phrase5_1.add(new Chunk(query_data.get("banknameaccountno") + "", subTableHeadingFonts));
			table.addCell(phrase5_1);

			Phrase phrase6 = new Phrase();
			phrase6.add(new Chunk("Bank A/c Balance", MainHeadingFonts));
			table.addCell(phrase6);
			Phrase phrase6_1 = new Phrase();
			phrase6_1.add(new Chunk(query_data.get("balance_in_account") + "", subTableHeadingFonts));
			table.addCell(phrase6_1);
			if (payment_receipt_id.startsWith("P")) {
				System.out.println("starts with P");
				Phrase phrase7 = new Phrase();
				phrase7.add(new Chunk("Amount paid in Rs./- ", MainHeadingFonts));
				table.addCell(phrase7);
				Phrase phrase7_1 = new Phrase();
				phrase7_1.add(new Chunk(query_data.get("paymentamount") + "", subTableHeadingFonts));
				table.addCell(phrase7_1);

				Phrase phrase8 = new Phrase();
				phrase8.add(new Chunk("To Whom the Amount Paid", MainHeadingFonts));
				table.addCell(phrase8);
				Phrase phrase8_1 = new Phrase();
				phrase8_1.add(new Chunk(query_data.get("name") + "", subTableHeadingFonts));
				table.addCell(phrase8_1);

				Phrase phrase9 = new Phrase();
				phrase9.add(new Chunk("Payment Mode", MainHeadingFonts));
				table.addCell(phrase9);
				Phrase phrase9_1 = new Phrase();
				phrase9_1.add(new Chunk(query_data.get("mode") + "", subTableHeadingFonts));
				table.addCell(phrase9_1);

			} else {
				System.out.println("starts with r");

				Phrase phrase7 = new Phrase();
				phrase7.add(new Chunk("Amount Received in Rs./- ", MainHeadingFonts));
				table.addCell(phrase7);
				Phrase phrase7_1 = new Phrase();
				phrase7_1.add(new Chunk(query_data.get("receiptamount") + "", subTableHeadingFonts));
				table.addCell(phrase7_1);

				Phrase phrase8 = new Phrase();
				phrase8.add(new Chunk("From Whom the Amount Received", MainHeadingFonts));
				table.addCell(phrase8);
				Phrase phrase8_1 = new Phrase();
				phrase8_1.add(new Chunk(query_data.get("name") + "", subTableHeadingFonts));
				table.addCell(phrase8_1);

				Phrase phrase9 = new Phrase();
				phrase9.add(new Chunk("Received Mode", MainHeadingFonts));
				table.addCell(phrase9);
				Phrase phrase9_1 = new Phrase();
				phrase9_1.add(new Chunk(query_data.get("mode") + "", subTableHeadingFonts));
				table.addCell(phrase9_1);
			}

			Phrase phrase10 = new Phrase();
			phrase10.add(new Chunk("Description IBT", MainHeadingFonts));
			table.addCell(phrase10);
			Phrase phrase10_1 = new Phrase();
			phrase10_1.add(new Chunk(query_data.get("receipt_description") + "", subTableHeadingFonts));
			table.addCell(phrase10_1);

			Phrase phrase11 = new Phrase();
			phrase11.add(new Chunk("Cheque / UTR No", MainHeadingFonts));
			table.addCell(phrase11);
			Phrase phrase11_1 = new Phrase();
			phrase11_1.add(new Chunk(query_data.get("cheque_dd_receipt_no") + "", subTableHeadingFonts));
			table.addCell(phrase11_1);

			document.add(table);

			heading = new Paragraph("\n", subTableHeadingFonts);
			document.add(heading);

			// For banks
			System.out.println("..receipt_id" + receipt_id);
			List<Map<String, Object>> banksData = pdf_View_Repo.getbanks(receipt_id);
			PdfPTable table1 = new PdfPTable(5);
			table1.setWidths(new int[] { 20, 50, 50, 50, 50 });
			table1.setWidthPercentage(100);

			PdfPCell c1 = new PdfPCell();

			c1 = new PdfPCell(new Phrase("S.No", MainHeadingFonts));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);

			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Transaction Id No", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Bank Name", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Balance in A/c (Rs/-)", MainHeadingFonts));
			c1.setHorizontalAlignment(1);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Amount (Rs/-)", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			if (banksData != null) {
				AtomicInteger sno = new AtomicInteger(1);

				banksData.forEach(data -> {

					PdfPCell c12;
					c12 = new PdfPCell(new Phrase(String.valueOf(sno.get())));
					c12.setHorizontalAlignment(Element.ALIGN_CENTER);
					table1.addCell(c12);
					if (receipt_id.startsWith("P")) {
						if (data.get("payment_receipt_id") != null) {
							c12 = new PdfPCell(
									new Phrase(String.valueOf(data.get("payment_receipt_id")), subTableHeadingFonts));
							c12.setHorizontalAlignment(Element.ALIGN_CENTER);
							// c12.setPadding(5f);
							table1.addCell(c12);
						} else {
							c12 = new PdfPCell(new Phrase(String.valueOf("-")));
							c12.setHorizontalAlignment(Element.ALIGN_CENTER);
							// c12.setPadding(5f);
							table1.addCell(c12);
						}
					}

					else {
						if (data.get("receipt_id") != null) {
							c12 = new PdfPCell(
									new Phrase(String.valueOf(data.get("receipt_id")), subTableHeadingFonts)); 
							c12.setHorizontalAlignment(Element.ALIGN_CENTER);
							// c12.setPadding(5f);
							table1.addCell(c12);
						} else {
							c12 = new PdfPCell(new Phrase(String.valueOf("-")));
							c12.setHorizontalAlignment(Element.ALIGN_CENTER);
							// c12.setPadding(5f);
							table1.addCell(c12);
						}
					}
					if (data.get("banknameaccountno") != null) {
						c12 = new PdfPCell(
								new Phrase(String.valueOf(data.get("banknameaccountno")), subTableHeadingFonts));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("-")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}

					if (data.get("balance_in_account") != null) {
						c12 = new PdfPCell(
								new Phrase(String.valueOf(data.get("balance_in_account")), subTableHeadingFonts));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("-")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}
					if (receipt_id.startsWith("P")) {
						if (data.get("paymentamount") != null) {
							c12 = new PdfPCell(
									new Phrase(String.valueOf(data.get("paymentamount")), subTableHeadingFonts));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							// c12.setPadding(5f);
							table1.addCell(c12);
						} else {
							c12 = new PdfPCell(new Phrase(String.valueOf("-")));
							c12.setHorizontalAlignment(Element.ALIGN_CENTER);
							// c12.setPadding(5f);
							table1.addCell(c12);
						}
					} else {
						if (data.get("receiptamount") != null) {
							c12 = new PdfPCell(
									new Phrase(String.valueOf(data.get("receiptamount")), subTableHeadingFonts));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							// c12.setPadding(5f);
							table1.addCell(c12);
						} else {
							c12 = new PdfPCell(new Phrase(String.valueOf("-")));
							c12.setHorizontalAlignment(Element.ALIGN_CENTER);
							// c12.setPadding(5f);
							table1.addCell(c12);
						}
					}

					sno.incrementAndGet(); // Increment the serial number
				});
			}

			document.add(table1);

			heading = new Paragraph("\n\n\n", subTableHeadingFonts);
			document.add(heading);
			PdfPTable table21 = new PdfPTable(3);
			table21.setWidths(new int[] { 45, 45, 60 });
			table21.setWidthPercentage(100);

			PdfPCell c31;

			c31 = new PdfPCell(new Phrase("Prepared By", subTableHeadingFonts));
			c31.setBorder(0);
			c31.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			table21.addCell(c31);

			c31 = new PdfPCell(new Phrase("Manager / Dy.GM (1st Sign)", subTableHeadingFonts));
			c31.setBorder(0);
			c31.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			table21.addCell(c31);

			c31 = new PdfPCell(new Phrase("PD(H) / GM(F) / GM(A) / DHH (2nd Sign)", subTableHeadingFonts));
			c31.setBorder(0);
			c31.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			table21.addCell(c31);

			document.add(table21);
			heading = new Paragraph("\n\n\n", subTableHeadingFonts);
			document.add(heading);
			heading = new Paragraph("Signature Of Receiver", subTableHeadingFonts);

			heading.setAlignment(Element.ALIGN_RIGHT);
			document.add(heading);

			Object timestampObject = query_data.get("timestamp");
			System.out.println("timestampObject" + timestampObject);

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String formattedTimestamp = dateFormat.format((Date) timestampObject);

			Phrase phraseTimestamp = new Phrase();
			phraseTimestamp.add(new Chunk("\nTimestamp : ", MainHeadingFonts));
			phraseTimestamp.add(new Chunk(formattedTimestamp, subTableHeadingFonts));

			PdfPTable tableTimestamp = new PdfPTable(1);
			tableTimestamp.setWidthPercentage(100);
			PdfPCell cellTimestamp = new PdfPCell(phraseTimestamp);
			cellTimestamp.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellTimestamp.setBorder(0);
			tableTimestamp.addCell(cellTimestamp);

			// Add the table to the document
			document.add(tableTimestamp);
			document.close();
		} catch (DocumentException e) {

			logger.info(e);

		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	// Reports ::Cash and Bank Rceipts and Cash and Bank Payments (by clicking on
	// HeadId)
	@Override
	public ByteArrayInputStream get_Heads_PDF(String payment_receipt_id) throws IOException, ResourceNotFoundException {

		Document document = getRegularDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Paragraph heading = new Paragraph("", subTableHeadingFonts);
		System.out.println("hello");
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);

		try {
			// P032006141
			List<Map<String, Object>> headsData = pdf_View_Repo.getheads(payment_receipt_id);
			PdfWriter.getInstance(document, out);

			document.open();

			document.add(getLogo(logo));
			PdfPCell cell = new PdfPCell();
			document.add(heading);

			document.add(getTitle("Heads For Transaction-Id" + " " + payment_receipt_id));
			document.add(setSpecTable());

			PdfPTable table1 = new PdfPTable(5);

			table1.setWidths(new int[] { 50, 50, 50, 50, 50 });
			table1.setWidthPercentage(100);

			PdfPCell c1 = new PdfPCell();

			c1 = new PdfPCell(new Phrase(" Head Id", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Head Name", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Sub Head Id", MainHeadingFonts));
			c1.setHorizontalAlignment(1);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Sub Head Name", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Amount", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			if (headsData != null) {
				headsData.forEach(data -> {

					PdfPCell c12;
					if (data.get("headid") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("headid"))));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("---")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}

					if (data.get("headname") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("headname"))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("---")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}
					if (data.get("subheadid") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("subheadid"))));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("-")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}

					if (data.get("subheadname") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("subheadname"))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("----")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}
					if (data.get("amount") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("amount"))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("----")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}
				});
			}

			document.add(table1);

			document.close();
		} catch (DocumentException e) {

			logger.info(e);

		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	// Reports ::Receipts and Payments(Cash && Bank) (by clicking on HeadId)
	@Override
	public ByteArrayInputStream get_Cash_and_Bankpdf(String headid, String fromDate, String toDate)
			throws IOException, ResourceNotFoundException {
		float customWidth = 1000; // Width in points
		float customHeight = 800; // Height in points
		Document document = new Document(new Rectangle(customWidth, customHeight));
//		Document document = getRegularDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Paragraph heading = new Paragraph("", subTableHeadingFonts);
		System.out.println("hello");
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);
		try {
			// P032006141
			List<Map<String, Object>> headsData = pdf_View_Repo.get_Cash_Bank_Heads(headid, fromDate, toDate,
					security_id);
			PdfWriter.getInstance(document, out);

			document.open();

			document.add(getLogo(logo));
			PdfPCell cell = new PdfPCell();
			document.add(heading);

			String titleText = "Journal-Vouchers, Receipts, Payments, and for the Headid: " + headid
					+ "-INTEREST ON BANK DEPOSITS";
			document.add(getColorTitle(titleText, headerColor1));
			document.add(setSpecTable());
			PdfPTable table1 = new PdfPTable(7);

			table1.setWidths(new int[] { 50, 50, 50, 100, 50, 50, 50 });
			table1.setWidthPercentage(100);

			PdfPCell c1 = new PdfPCell();

			c1 = new PdfPCell(new Phrase("Transaction Id", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("VoucherNo", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Date", MainHeadingFonts));
			c1.setHorizontalAlignment(1);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Description", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Debit", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Credit", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);

			c1 = new PdfPCell(new Phrase("Balance", MainHeadingFonts));
			c1.setHorizontalAlignment(1);

			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setPadding(5f);
			table1.addCell(c1);
			if (headsData != null) {
				headsData.forEach(data -> {

					PdfPCell c12;
					if (data.get("payment_receipt_id") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("payment_receipt_id"))));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("---")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}

					if (data.get("receiptno") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("receiptno"))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("---")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}
					if (data.get("to_char") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("to_char"))));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("-")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}

					if (data.get("receipt_description") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("receipt_description"))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("----")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}
					if (data.get("credit") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("credit"))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("----")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}
					if (data.get("debit") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("debit"))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("----")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}
					if (data.get("credit") != null) {
						c12 = new PdfPCell(new Phrase(String.valueOf(data.get("credit"))));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					} else {
						c12 = new PdfPCell(new Phrase(String.valueOf("----")));
						c12.setHorizontalAlignment(Element.ALIGN_CENTER);
						// c12.setPadding(5f);
						table1.addCell(c12);
					}
				});
			}

			document.add(table1);

			document.close();
		} catch (DocumentException e) {

			logger.info(e);

		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	public Image getLogo(String stateLogo) throws BadElementException, IOException {
		Image logoImg = Image.getInstance(stateLogo);

		logoImg.setAlignment(Element.ALIGN_CENTER);
		logoImg.scaleAbsolute(85, 85);
		return logoImg;
	}

	public PdfPTable setSpecTable() {
		PdfPTable table = new PdfPTable(1);
		table.getDefaultCell().setBorder(0);
		table.addCell("\n");
		return table;
	}

	public Paragraph getTitle(String title) {
		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD);
		Paragraph para = new Paragraph(title, titleFont);
		para.setAlignment(Element.ALIGN_CENTER);

		return para;
	}

	private static Paragraph getColorTitle(String text, BaseColor color) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.NORMAL, color);
		Paragraph paragraph = new Paragraph(text, font);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		return paragraph;
	}

	public PdfPCell addCellWithBorder(String name, BaseColor baseColor, int colspan, int elementPosition, Font font) {
		PdfPCell c25 = new PdfPCell(new Phrase(name, font));
		c25.setBackgroundColor(baseColor);
		c25.setColspan(colspan);
		c25.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c25.setPaddingBottom(7);

		c25.setHorizontalAlignment(elementPosition);
		return c25;
	}
//	private Image generateQRCodeImage(Object coupon_number, int width, int height) throws WriterException, IOException {
//
//		QRCodeWriter qrCodeWriter = new QRCodeWriter();
//
//		String coupon_no = coupon_number.toString();
//		BitMatrix bitMatrix = qrCodeWriter.encode(coupon_no, BarcodeFormat.QR_CODE, width, height);
//
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
//		outputStream.flush();
//		byte[] qrCodeBytes = outputStream.toByteArray();
//		outputStream.close();
//		Image qrcode = null;
//		try {
//			qrcode = Image.getInstance(qrCodeBytes);
//		} catch (BadElementException | IOException e) {
//			e.printStackTrace();
//		}
//		return qrcode;
//	}

	@Override
	public String getInterBankSubhead(String payment_receipt_id) {

		return pdf_View_Repo.getInterBankSubhead(payment_receipt_id);
	}

	@Override
	public String getReceipt_id(String payment_receipt_id) {

		return pdf_View_Repo.getReceipt_id(payment_receipt_id);
	}

	@Override
	public String getPayment_Receipt_id(String payment_receipt_id) {

		return pdf_View_Repo.getPayment_Receipt_id(payment_receipt_id);
	}

}
