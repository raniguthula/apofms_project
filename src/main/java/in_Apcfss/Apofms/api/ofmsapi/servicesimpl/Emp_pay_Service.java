package in_Apcfss.Apofms.api.ofmsapi.servicesimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.swing.border.Border;
import javax.swing.text.StyleConstants.ColorConstants;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.xwpf.usermodel.Borders;
import org.attoparser.config.ParseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import in_Apcfss.Apofms.api.ofmsapi.Execptions.ResourceNotFoundException;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.PayrollConfirmEmpDetails;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.Pdf_View_Repo;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.UsersSecurityRepo;
import in_Apcfss.Apofms.api.ofmsapi.request.Dummy_journal_voucher_heads_Req;
import in_Apcfss.Apofms.api.ofmsapi.request.MonthlyPaybillRequest;

@Service
public class Emp_pay_Service {
	@Autowired
	PayrollConfirmEmpDetails payrollConfirmEmpDetails;
	@Autowired
	Pdf_View_Repo pdf_View_Repo;
	@Autowired
	UsersSecurityRepo usersSecurityRepo;
	private final Log logger = LogFactory.getLog(getClass());

	public final String RUPUEE_ICON = "₹";

	final String CreatedBy = "CreatedBy";

	// NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en",
	// "IN"));
//	private static final Font subTableHeadingFonts = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
	private static final Font subTableHeadingFonts = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	private static final Font subTableMainHeadingFonts = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
	private static final Font TableHeadingFonts = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//	private static final Font subTableHeadingFonts = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
	private static final Font HeadingFonts = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);

//	private static final Font MainHeadingFonts = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
	private static final Font MainHeadingFonts = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	private static final Font signFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	public Font color = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, subheaderColor);
	private static final Font underlineFont = new Font(Font.FontFamily.HELVETICA, 12, Font.UNDERLINE);

	private static final BaseColor headerColor = new BaseColor(215, 225, 247);
	private static final BaseColor headerColor1 = new BaseColor(30, 55, 112, 1);
	private static final BaseColor borderColor = new BaseColor(248, 151, 115);
	private static final BaseColor subheaderColor = new BaseColor(255, 255, 255);
	private static final BaseColor qrcodeColor = new BaseColor(255, 255, 255);
	 BaseColor baseGreenColor = new BaseColor(0, 128, 0);
	private static final BaseColor subchildheaderColor = new BaseColor(242, 242, 242);

	private static final BaseColor specColor = new BaseColor(235, 247, 247);

	private static String logo = "classpath:/images/Andhra-Pradesh-AP-Govt-Logo.png";

	public Document getRegularDocument() {

		Document document = new Document(PageSize.A4, 30, 30, 30, 20);

		return document;
	}

	public ByteArrayInputStream getMonthly_Paybill_Pdf(String emp_id, String payment_type, String confirm_month,
			String year) throws IOException, ResourceNotFoundException {
		Document document = getRegularDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Paragraph heading = new Paragraph("", subTableHeadingFonts);
		System.out.println("hello");
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);

		try {
			Map<String, String> dist_data = pdf_View_Repo.getdist_data(security_id, userid);
			Map<String, String> empDetails = payrollConfirmEmpDetails.FormEmpnameByName(emp_id, payment_type,
					confirm_month, year);
			Map<String, String> Head = payrollConfirmEmpDetails.FormEmpnameByName(emp_id, payment_type, confirm_month,
					year);
			List<Map<String, Object>> earning = payrollConfirmEmpDetails.FormEarningDetailsByName(emp_id, payment_type,
					confirm_month, year);

			List<Map<String, Object>> deductions = payrollConfirmEmpDetails.FormDeductionDetailsByName(emp_id,
					payment_type, confirm_month, year);

			List<Map<String, Object>> loan = payrollConfirmEmpDetails.FormLoanDetailsByName(emp_id, payment_type,
					confirm_month, year);

			PdfWriter.getInstance(document, out);
			document.open();
			Paragraph heading1 = new Paragraph();
			heading1.setAlignment(Paragraph.ALIGN_CENTER);
			
			Font 	heading1Font = new Font(MainHeadingFonts);
			heading1.add(new Chunk("ANDHRA PRADESH STATE HOUSING CORPORATION LIMITED (APSHCL)",
					new Font(heading1Font.getBaseFont(), heading1Font.getSize(), Font.BOLD, baseGreenColor)));

			document.add(heading1);
			Font 	heading2Font = new Font(subTableHeadingFonts);
			
			Paragraph heading2 = new Paragraph();
			heading2.add(new Chunk("Payslip For " + empDetails.get("month_name") + "-" + empDetails.get("year"),
					new Font(heading2Font.getBaseFont(), heading2Font.getSize(), Font.BOLD, baseGreenColor)));
			heading2.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(heading2);
			Image logo = Image.getInstance("classpath:/images/Andhra-Pradesh-AP-Govt-Logo.png");
			PdfPCell logoCell = new PdfPCell(logo, true);
			logoCell.setBorder(Rectangle.NO_BORDER);
			// Create a new Paragraph
			Paragraph side = new Paragraph();

			Font valueFont = new Font(MainHeadingFonts);
			Font labelFont = new Font(subTableHeadingFonts);

// District Name
			side.add(new Chunk("\nDistrict Name: ", labelFont));
			side.add(new Chunk(dist_data.get("display_name"),
					new Font(labelFont.getBaseFont(), labelFont.getSize(), Font.UNDEFINED, BaseColor.BLUE)));

// Employee Id
			side.add(new Chunk("\nEmployee Id: ", labelFont));
			side.add(new Chunk(empDetails.get("emp_id"),
					new Font(labelFont.getBaseFont(), labelFont.getSize(), Font.UNDEFINED, BaseColor.BLUE)));

// Employee Name
			side.add(new Chunk("\nEmployee Name: ", labelFont));
			side.add(new Chunk(empDetails.get("emp_name"),
					new Font(labelFont.getBaseFont(), labelFont.getSize(), Font.UNDEFINED, BaseColor.BLUE)));

// Designation
			side.add(new Chunk("\nDesignation: ", labelFont));
			side.add(new Chunk(empDetails.get("deg_name"),
					new Font(labelFont.getBaseFont(), labelFont.getSize(), Font.UNDEFINED, BaseColor.BLUE)));

// Payment Type
			side.add(new Chunk("\nPayment Type: ", labelFont));
			side.add(new Chunk(empDetails.get("emptype_name"),
					new Font(labelFont.getBaseFont(), labelFont.getSize(), Font.UNDEFINED, BaseColor.BLUE)));

			PdfPCell SideCell = new PdfPCell(side);
			// Create a table with two cells
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			SideCell.setBorder(Rectangle.NO_BORDER);
			table.setWidths(new float[] { 6, 1 });

			table.addCell(SideCell);
			table.addCell(logoCell);
			document.add(table);

			LineSeparator ls = new LineSeparator();
			document.add(new Chunk(ls));

			PdfPTable tableHead = new PdfPTable(6);
			tableHead.setWidths(new int[] { 20, 20, 20, 20, 25, 20 });
			tableHead.setWidthPercentage(100);
			PdfPCell c1 = new PdfPCell(new Phrase("Earnings", MainHeadingFonts));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setPadding(5f);
			c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableHead.addCell(c1);

			c1 = new PdfPCell(new Phrase("Amount", MainHeadingFonts));
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			c1.setPadding(5f);
			c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableHead.addCell(c1);

			c1 = new PdfPCell(new Phrase("Deductions", MainHeadingFonts));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setPadding(5f);
			c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableHead.addCell(c1);

			c1 = new PdfPCell(new Phrase("Amount", MainHeadingFonts));
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			c1.setPadding(5f);
			c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableHead.addCell(c1);

			c1 = new PdfPCell(new Phrase("Loan/Advances", MainHeadingFonts));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setPadding(5f);
			c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableHead.addCell(c1);

			c1 = new PdfPCell(new Phrase("Amount", MainHeadingFonts));
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			c1.setPadding(5f);
			c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			tableHead.addCell(c1);

			document.add(tableHead);
			PdfPTable table2 = new PdfPTable(3);
			table2.setWidths(new int[] { 40, 40, 45 });
			table2.setWidthPercentage(100);

			for (Map<String, Object> data : earning) {
				PdfPCell cell;
				cell = new PdfPCell();

				String BasicPay = String.valueOf(data.get("basic_pay"));
				String PerPay = String.valueOf(data.get("per_pay"));
				String SplPay = String.valueOf(data.get("spl_pay"));
				String Da = String.valueOf(data.get("da"));
				String Hra = String.valueOf(data.get("hra"));
				String Cca = String.valueOf(data.get("cca"));
				String Gp = String.valueOf(data.get("gp"));
				String Ir = String.valueOf(data.get("ir"));
				String Medical = String.valueOf(data.get("medical"));
				String Ca = String.valueOf(data.get("ca"));
				String Spl_all = String.valueOf(data.get("spl_all"));
				String Misc_h_c = String.valueOf(data.get("misc_h_c"));
				String Addl_hra = String.valueOf(data.get("addl_hra"));
				String Sca = String.valueOf(data.get("sca"));

				// Repeat this process for other data fields

				if (!BasicPay.equals("No")) {

					Phrase EarnigsLabel = new Phrase("Basic Pay", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);

					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(BasicPay, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}

				if (!PerPay.equals("No")) {

					Phrase EarnigsLabel = new Phrase("Per Pay", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(PerPay, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}
				if (!SplPay.equals("No")) {

					Phrase EarnigsLabel = new Phrase("SplPay", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(SplPay, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}

				if (!Da.equals("No")) {

					Phrase EarnigsLabel = new Phrase("Da", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Da, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}

				if (!Hra.equals("No")) {

					Phrase EarnigsLabel = new Phrase("Hra", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Hra, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}
				if (!Cca.equals("No")) {

					Phrase EarnigsLabel = new Phrase("Cca", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Cca, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}
				if (!Gp.equals("No")) {

					Phrase EarnigsLabel = new Phrase("Gp", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Gp, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}
				if (!Ir.equals("No")) {

					Phrase EarnigsLabel = new Phrase("Ir", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Ir, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}
				if (!Medical.equals("No")) {

					Phrase EarnigsLabel = new Phrase("Medical", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Medical, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}

				if (!Ca.equals("No")) {

					Phrase EarnigsLabel = new Phrase("Ca", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Ca, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}
				if (!Spl_all.equals("No")) {

					Phrase EarnigsLabel = new Phrase("Spl_all", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Spl_all, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}

				if (!Misc_h_c.equals("No")) {

					Phrase EarnigsLabel = new Phrase("Misc_h_c", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Misc_h_c, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}
				if (!Addl_hra.equals("No")) {

					Phrase EarnigsLabel = new Phrase("Addl_hra", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Addl_hra, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}

				if (!Sca.equals("No")) {

					Phrase EarnigsLabel = new Phrase("Sca", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(EarnigsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Sca, Element.ALIGN_RIGHT));

					cell.addElement(paragraph);
				}

				table2.addCell(cell);
			}
			for (Map<String, Object> data1 : deductions) {
				PdfPCell cell1 = new PdfPCell();

				String gpfs = String.valueOf(data1.get("gpfs"));
				String gpfsa = String.valueOf(data1.get("gpfsa"));
				String gpfl = String.valueOf(data1.get("gpfl"));
				String apglis = String.valueOf(data1.get("apglis"));
				String apglil = String.valueOf(data1.get("apglil"));
				String gis = String.valueOf(data1.get("gis"));
				String lic = String.valueOf(data1.get("lic"));
				String license_ded = String.valueOf(data1.get("license_ded"));
				String con_decd = String.valueOf(data1.get("con_decd"));
				String epf = String.valueOf(data1.get("epf"));
				String epf_l = String.valueOf(data1.get("epf_l"));
				String vpf = String.valueOf(data1.get("vpf"));
				String ppf = String.valueOf(data1.get("ppf"));
				String rcs_cont = String.valueOf(data1.get("rcs_cont"));
				String cmrf = String.valueOf(data1.get("cmrf"));
				String fcf = String.valueOf(data1.get("fcf"));
				String house_rent = String.valueOf(data1.get("house_rent"));
				String sal_rec = String.valueOf(data1.get("sal_rec"));
				String pt = String.valueOf(data1.get("pt"));
				String it = String.valueOf(data1.get("it"));
				String postal_rd = String.valueOf(data1.get("postal_rd"));
				String other_deductions = String.valueOf(data1.get("other_deductions"));

				if (!gpfs.equals("No")) {
					Phrase DeductionsLabel = new Phrase("GPFS", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(DeductionsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(gpfs, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!gpfsa.equals("No")) {
					Phrase gpfsaLabel = new Phrase("GPFSA", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(gpfsaLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(gpfsa, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}
				if (!gpfl.equals("No")) {
					Phrase gpflLabel = new Phrase("GPFL", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(gpflLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(gpfl, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!apglis.equals("No")) {
					Phrase apglisLabel = new Phrase("APGLIS", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(apglisLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(apglis, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!apglil.equals("No")) {
					Phrase apglilLabel = new Phrase("APGLIL", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(apglilLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(apglil, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!gis.equals("No")) {
					Phrase gisLabel = new Phrase("GIS", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(gisLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(gis, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}
				if (!lic.equals("No")) {
					Phrase licLabel = new Phrase("LIC", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(licLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(lic, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!license_ded.equals("No")) {
					Phrase licenseDedLabel = new Phrase("License Ded", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(licenseDedLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(license_ded, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!con_decd.equals("No")) {
					Phrase conDecdLabel = new Phrase("Con Decd", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(conDecdLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(con_decd, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!epf.equals("No")) {
					Phrase epfLabel = new Phrase("EPF", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(epfLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(epf, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}
				if (!epf_l.equals("No")) {
					Phrase epfLLabel = new Phrase("EPF_L", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(epfLLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(epf_l, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!vpf.equals("No")) {
					Phrase vpfLabel = new Phrase("VPF", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(vpfLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(vpf, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}
				if (!ppf.equals("No")) {
					Phrase ppfLabel = new Phrase("PPF", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(ppfLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(ppf, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!rcs_cont.equals("No")) {
					Phrase rcsContLabel = new Phrase("RCS Cont", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(rcsContLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(rcs_cont, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}
				if (!cmrf.equals("No")) {
					Phrase cmrfLabel = new Phrase("CMRF", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(cmrfLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(cmrf, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!fcf.equals("No")) {
					Phrase fcfLabel = new Phrase("FCF", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(fcfLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(fcf, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!house_rent.equals("No")) {
					Phrase houseRentLabel = new Phrase("House Rent", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(houseRentLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(house_rent, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!sal_rec.equals("No")) {
					Phrase salRecLabel = new Phrase("Sal Rec", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(salRecLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(sal_rec, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}
				// Repeat this block for other data fields

				if (!pt.equals("No")) {
					Phrase ptLabel = new Phrase("PT", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(ptLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(pt, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!it.equals("No")) {
					Phrase itLabel = new Phrase("IT", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(itLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(it, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}
				// Repeat this block for other data fields

				if (!postal_rd.equals("No")) {
					Phrase postalRdLabel = new Phrase("Postal RD", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(postalRdLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(postal_rd, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				if (!other_deductions.equals("No")) {
					Phrase otherDeductionsLabel = new Phrase("Other Deductions", subTableHeadingFonts);
					Paragraph paragraph = new Paragraph();
					paragraph.add(otherDeductionsLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(other_deductions, Element.ALIGN_RIGHT));
					cell1.addElement(paragraph);
				}

				table2.addCell(cell1);
			}

			for (Map<String, Object> data2 : loan) {
				PdfPCell cell3 = new PdfPCell();

				String CarA = String.valueOf(data2.get("car_a"));
				String CarI = String.valueOf(data2.get("car_i"));
				String CycI = String.valueOf(data2.get("cyc_i"));
				String CycA = String.valueOf(data2.get("cyc_a"));
				String McaI = String.valueOf(data2.get("mca_i"));
				String McaA = String.valueOf(data2.get("mca_a"));
				String MarA = String.valueOf(data2.get("mar_a"));
				String MedA = String.valueOf(data2.get("med_a"));
				String Hba = String.valueOf(data2.get("hba"));
				String Hba1 = String.valueOf(data2.get("hbal"));
				String CompA = String.valueOf(data2.get("comp_a"));
				String Fa = String.valueOf(data2.get("fa"));
				String Ea = String.valueOf(data2.get("ea"));
				String Cell = String.valueOf(data2.get("cell"));
				String AddHba = String.valueOf(data2.get("add_hba"));
				String AddHba1 = String.valueOf(data2.get("add_hba1"));
				String SalAdv = String.valueOf(data2.get("sal_adv"));
				String Sfa = String.valueOf(data2.get("sfa"));
				String MedAI = String.valueOf(data2.get("med_a_i"));
				String Hcesa = String.valueOf(data2.get("hcesa"));
				String HcesaI = String.valueOf(data2.get("hcesa_i"));
				String StaffPl = String.valueOf(data2.get("staff_pl"));
				String Court = String.valueOf(data2.get("court"));
				String VijBank = String.valueOf(data2.get("personal_bank"));
				String MarI = String.valueOf(data2.get("mar_i"));
				String HrArrear = String.valueOf(data2.get("hr_arrear"));
				String Hbao = String.valueOf(data2.get("hbao"));
				String Comp1 = String.valueOf(data2.get("comp1"));
				String CarIPi = String.valueOf(data2.get("car_ipi"));
				String CarAPi = String.valueOf(data2.get("car_api"));
				String CycIPi = String.valueOf(data2.get("cyc_ipi"));
				String CycAPi = String.valueOf(data2.get("cyc_api"));
				String McaIPi = String.valueOf(data2.get("mca_ipi"));
				String McaAPi = String.valueOf(data2.get("mca_api"));
				String MarAPi = String.valueOf(data2.get("mar_api"));
				String MedAPi = String.valueOf(data2.get("med_api"));
				String HbaPi = String.valueOf(data2.get("hbapi"));
				String HbalPi = String.valueOf(data2.get("hba1pi"));
				String carIpi = String.valueOf(data2.get("car_ipi"));
				String CarApi = String.valueOf(data2.get("car_api"));
				String CycIpi = String.valueOf(data2.get("cyc_ipi"));
				String CycApi = String.valueOf(data2.get("cyc_api"));
				String McaIpi = String.valueOf(data2.get("mca_ipi"));
				String McaApi = String.valueOf(data2.get("mca_api"));
				String MarApi = String.valueOf(data2.get("mar_api"));
				String MedApi = String.valueOf(data2.get("med_api"));
				String CompPi = String.valueOf(data2.get("comppi"));
				String FaPi = String.valueOf(data2.get("fapi"));
				String EaPi = String.valueOf(data2.get("eapi"));
				String CellPi = String.valueOf(data2.get("cellpi"));
				String AddHbaPi = String.valueOf(data2.get("add_hbapi"));
				String AddHba1Pi = String.valueOf(data2.get("add_hba1pi"));
				String SalAdvPi = String.valueOf(data2.get("sal_adv_pi"));
				String SfaPi = String.valueOf(data2.get("sfapi"));
				String MedAIPi = String.valueOf(data2.get("med_a_ipi"));
				String HcesaPi = String.valueOf(data2.get("hcesapi"));
				String HcesaIPi = String.valueOf(data2.get("hcesa_ipi"));
				String StaffPlPi = String.valueOf(data2.get("staff_plpi"));
				String CourtPi = String.valueOf(data2.get("courtpi"));

				String Bank = String.valueOf(data2.get("vij_bankpi"));
				String MarIPi = String.valueOf(data2.get("mar_ipi"));
				String HrArrearPi = String.valueOf(data2.get("hr_arrearpi"));
				String HbaoPi = String.valueOf(data2.get("hbaopi"));
				String ComplPi = String.valueOf(data2.get("comp1pi"));
				String CarIti = String.valueOf(data2.get("car_iti"));
				String CarAti = String.valueOf(data2.get("car_ati"));
				String CycIti = String.valueOf(data2.get("cyc_iti"));
				String CycAti = String.valueOf(data2.get("cyc_ati"));
				String McaIti = String.valueOf(data2.get("mca_iti"));
				String McaAti = String.valueOf(data2.get("mca_ati"));
				String MarAti = String.valueOf(data2.get("mar_ati"));
				String MedAti = String.valueOf(data2.get("med_ati"));
				String HbaTi = String.valueOf(data2.get("hbati"));
				String Hba1Ti = String.valueOf(data2.get("hba1ti"));
				String CompTi = String.valueOf(data2.get("compti"));
				String Fati = String.valueOf(data2.get("fati"));
				String Eati = String.valueOf(data2.get("eati"));
				String Cellti = String.valueOf(data2.get("cellti"));
				String AddHbaTi = String.valueOf(data2.get("add_hbati"));
				String AddHablTi = String.valueOf(data2.get("add_hba1ti"));
				String SalAdvti = String.valueOf(data2.get("sal_advti"));
				String SfaTi = String.valueOf(data2.get("sfati"));
				String MedAiti = String.valueOf(data2.get("med_a_iti"));
				String Hcesati = String.valueOf(data2.get("hcesati"));
				String Hcesaiti = String.valueOf(data2.get("hcesaiti"));
				String Staffplti = String.valueOf(data2.get("staff_plti"));
				String Courtti = String.valueOf(data2.get("courtti"));
				String Bankti = String.valueOf(data2.get("vij_bankti"));
				String Mariti = String.valueOf(data2.get("mar_iti"));
				String HrArrearTi = String.valueOf(data2.get("hr_arrearti"));
				String HbaoTi = String.valueOf(data2.get("hbaoti"));
				String Comp1ti = String.valueOf(data2.get("comp1ti"));
				if (!CarA.equals("No")) {
					Phrase loanLabel = new Phrase("Car A", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CarA, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CarI.equals("No")) {
					Phrase loanLabel = new Phrase("Car I", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CarI, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!CycA.equals("No")) {
					Phrase loanLabel = new Phrase("Cyc A", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CycA, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CycI.equals("No")) {
					Phrase loanLabel = new Phrase("Cyc I", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CycI, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!McaA.equals("No")) {
					Phrase loanLabel = new Phrase("Mca A", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(McaA, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!McaI.equals("No")) {
					Phrase loanLabel = new Phrase("Mca I", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(McaI, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!MarA.equals("No")) {
					Phrase loanLabel = new Phrase("Mar A", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MarA, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!MedA.equals("No")) {
					Phrase loanLabel = new Phrase("Med A", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MedA, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Hba.equals("No")) {
					Phrase loanLabel = new Phrase("Hba", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Hba, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Hba1.equals("No")) {
					Phrase loanLabel = new Phrase("Hba1", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Hba1, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!MedA.equals("No")) {
					Phrase loanLabel = new Phrase("Med A", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MedA, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CompA.equals("No")) {
					Phrase loanLabel = new Phrase("CompA", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CompA, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Fa.equals("No")) {
					Phrase loanLabel = new Phrase("Fa", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Fa, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Fa.equals("No")) {
					Phrase loanLabel = new Phrase("Fa", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Fa, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!Ea.equals("No")) {
					Phrase loanLabel = new Phrase("Ea", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Ea, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Cell.equals("No")) {
					Phrase loanLabel = new Phrase("Cell", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Cell, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!AddHba.equals("No")) {
					Phrase loanLabel = new Phrase("AddHba", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(AddHba, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!AddHba1.equals("No")) {
					Phrase loanLabel = new Phrase("AddHba1", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(AddHba1, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!SalAdv.equals("No")) {
					Phrase loanLabel = new Phrase("SalAdv", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(SalAdv, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Sfa.equals("No")) {
					Phrase loanLabel = new Phrase("Sfa", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Sfa, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!MedAI.equals("No")) {
					Phrase loanLabel = new Phrase("MedAI", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MedAI, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Hcesa.equals("No")) {
					Phrase loanLabel = new Phrase("Hcesa", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Hcesa, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!HcesaI.equals("No")) {
					Phrase loanLabel = new Phrase("HcesaI", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(HcesaI, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!StaffPl.equals("No")) {
					Phrase loanLabel = new Phrase("StaffPl", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(StaffPl, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Court.equals("No")) {
					Phrase loanLabel = new Phrase("Court", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Court, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!VijBank.equals("No")) {
					Phrase loanLabel = new Phrase("VijBank", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(VijBank, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!MarI.equals("No")) {
					Phrase loanLabel = new Phrase("Sfa", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MarI, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!HrArrear.equals("No")) {
					Phrase loanLabel = new Phrase("HrArrear", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(HrArrear, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Hbao.equals("No")) {
					Phrase loanLabel = new Phrase("Hbao", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Hbao, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Comp1.equals("No")) {
					Phrase loanLabel = new Phrase("Comp1", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Comp1, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!CarIPi.equals("No")) {
					Phrase loanLabel = new Phrase("CarIPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CarIPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CarAPi.equals("No")) {
					Phrase loanLabel = new Phrase("CarAPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CarAPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!CycIPi.equals("No")) {
					Phrase loanLabel = new Phrase("CycIPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CycIPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CycAPi.equals("No")) {
					Phrase loanLabel = new Phrase("CycAPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CycAPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!McaIPi.equals("No")) {
					Phrase loanLabel = new Phrase("McaIPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(McaIPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!McaAPi.equals("No")) {
					Phrase loanLabel = new Phrase("McaAPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(McaAPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!MarAPi.equals("No")) {
					Phrase loanLabel = new Phrase("MarAPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MarAPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!MedAPi.equals("No")) {
					Phrase loanLabel = new Phrase("MedAPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MedAPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!HbaPi.equals("No")) {
					Phrase loanLabel = new Phrase("HbaPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(HbaPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!HbalPi.equals("No")) {
					Phrase loanLabel = new Phrase("HbalPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(HbalPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!carIpi.equals("No")) {
					Phrase loanLabel = new Phrase("carIpi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(carIpi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CarApi.equals("No")) {
					Phrase loanLabel = new Phrase("CarApi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CarApi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CycIpi.equals("No")) {
					Phrase loanLabel = new Phrase("CycIpi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CycIpi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CycApi.equals("No")) {
					Phrase loanLabel = new Phrase("CycApi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CycApi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!McaIpi.equals("No")) {
					Phrase loanLabel = new Phrase("McaIpi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(McaIpi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!McaApi.equals("No")) {
					Phrase loanLabel = new Phrase("McaApi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(McaApi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!MarApi.equals("No")) {
					Phrase loanLabel = new Phrase("MarApi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MarApi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!MedApi.equals("No")) {
					Phrase loanLabel = new Phrase("MedApi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MedApi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!CompPi.equals("No")) {
					Phrase loanLabel = new Phrase("CompPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CompPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!FaPi.equals("No")) {
					Phrase loanLabel = new Phrase("FaPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(FaPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!EaPi.equals("No")) {
					Phrase loanLabel = new Phrase("EaPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(EaPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!CellPi.equals("No")) {
					Phrase loanLabel = new Phrase("CellPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CellPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!AddHbaPi.equals("No")) {
					Phrase loanLabel = new Phrase("AddHbaPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(AddHbaPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!AddHba1Pi.equals("No")) {
					Phrase loanLabel = new Phrase("AddHba1Pi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(AddHba1Pi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!SalAdvPi.equals("No")) {
					Phrase loanLabel = new Phrase("SalAdvPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(SalAdvPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!SfaPi.equals("No")) {
					Phrase loanLabel = new Phrase("SfaPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(SfaPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!MedAIPi.equals("No")) {
					Phrase loanLabel = new Phrase("MedAIPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MedAIPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!HcesaPi.equals("No")) {
					Phrase loanLabel = new Phrase("HcesaPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(HcesaPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!HcesaIPi.equals("No")) {
					Phrase loanLabel = new Phrase("HcesaIPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(HcesaIPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!StaffPlPi.equals("No")) {
					Phrase loanLabel = new Phrase("StaffPlPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(StaffPlPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CourtPi.equals("No")) {
					Phrase loanLabel = new Phrase("CourtPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CourtPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!Bank.equals("No")) {
					Phrase loanLabel = new Phrase("Bank", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Bank, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!MarIPi.equals("No")) {
					Phrase loanLabel = new Phrase("MarIPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MarIPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!HrArrearPi.equals("No")) {
					Phrase loanLabel = new Phrase("HrArrearPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(HrArrearPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!HbaoPi.equals("No")) {
					Phrase loanLabel = new Phrase("HbaoPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(HbaoPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!ComplPi.equals("No")) {
					Phrase loanLabel = new Phrase("ComplPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(ComplPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!CarIti.equals("No")) {
					Phrase loanLabel = new Phrase("CarIti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CarIti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CarAti.equals("No")) {
					Phrase loanLabel = new Phrase("CarAti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CarAti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CycIti.equals("No")) {
					Phrase loanLabel = new Phrase("CycIti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CycIti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CycAti.equals("No")) {
					Phrase loanLabel = new Phrase("CycAti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CycAti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CourtPi.equals("No")) {
					Phrase loanLabel = new Phrase("CourtPi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CourtPi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!CycAti.equals("No")) {
					Phrase loanLabel = new Phrase("McaIti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(McaIti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!McaIti.equals("No")) {
					Phrase loanLabel = new Phrase("McaIti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(McaIti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!McaAti.equals("No")) {
					Phrase loanLabel = new Phrase("McaAti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(McaAti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!MarAti.equals("No")) {
					Phrase loanLabel = new Phrase("MarAti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MarAti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!MedAti.equals("No")) {
					Phrase loanLabel = new Phrase("MedAti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MedAti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!HbaTi.equals("No")) {
					Phrase loanLabel = new Phrase("HbaTi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(HbaTi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!Hba1Ti.equals("No")) {
					Phrase loanLabel = new Phrase("Hba1Ti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Hba1Ti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!CompTi.equals("No")) {
					Phrase loanLabel = new Phrase("CompTi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(CompTi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!Fati.equals("No")) {
					Phrase loanLabel = new Phrase("Fati", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Fati, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Eati.equals("No")) {
					Phrase loanLabel = new Phrase("Eati", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Eati, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Cellti.equals("No")) {
					Phrase loanLabel = new Phrase("Cellti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Cellti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!AddHbaTi.equals("No")) {
					Phrase loanLabel = new Phrase("AddHbaTi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(AddHbaTi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!AddHablTi.equals("No")) {
					Phrase loanLabel = new Phrase("AddHablTi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(AddHablTi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!SalAdvti.equals("No")) {
					Phrase loanLabel = new Phrase("SalAdvti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(SalAdvti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!SfaTi.equals("No")) {
					Phrase loanLabel = new Phrase("SfaTi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(SfaTi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!MedAiti.equals("No")) {
					Phrase loanLabel = new Phrase("MedAiti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(MedAiti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Hcesati.equals("No")) {
					Phrase loanLabel = new Phrase("Hcesati", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Hcesati, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Hcesaiti.equals("No")) {
					Phrase loanLabel = new Phrase("Hcesaiti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Hcesaiti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Staffplti.equals("No")) {
					Phrase loanLabel = new Phrase("Staffplti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Staffplti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!Courtti.equals("No")) {
					Phrase loanLabel = new Phrase("Courtti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Courtti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Bankti.equals("No")) {
					Phrase loanLabel = new Phrase("Bankti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Bankti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Mariti.equals("No")) {
					Phrase loanLabel = new Phrase("Mariti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Mariti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}

				if (!HrArrearTi.equals("No")) {
					Phrase loanLabel = new Phrase("HrArrearTi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(HrArrearTi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!HbaoTi.equals("No")) {
					Phrase loanLabel = new Phrase("HbaoTi", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(HbaoTi, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				if (!Comp1ti.equals("No")) {
					Phrase loanLabel = new Phrase("Comp1ti", subTableHeadingFonts);

					Paragraph paragraph = new Paragraph();
					paragraph.add(loanLabel);
					paragraph.add(new Chunk(new VerticalPositionMark()));
					paragraph.add(createAlignedParagraph(Comp1ti, Element.ALIGN_RIGHT));

					cell3.addElement(paragraph);
				}
				table2.addCell(cell3);
			}

			document.add(table2);

			// table3

			String basic_earnings_sum = payrollConfirmEmpDetails.basic_earnings_sum(emp_id, payment_type, confirm_month,
					year);
			String deductions_sum = payrollConfirmEmpDetails.deductions_sum(emp_id, payment_type, confirm_month, year);
			String loan_sum = payrollConfirmEmpDetails.loan_sum(emp_id, payment_type, confirm_month, year);
			PdfPTable table3 = new PdfPTable(6);
			table3.setWidths(new int[] { 20, 20, 20, 20, 25, 20 });
			table3.setWidthPercentage(100);

			PdfPCell c2;

			c2 = new PdfPCell(new Phrase("Total", MainHeadingFonts));
			c2.setHorizontalAlignment(Element.ALIGN_LEFT);
			c2.setPadding(5f);
			c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table3.addCell(c2);

			c2 = new PdfPCell(new Phrase(basic_earnings_sum, MainHeadingFonts));
			c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			c2.setPadding(5f);
			c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table3.addCell(c2);

			c2 = new PdfPCell(new Phrase("Total", MainHeadingFonts));
			c2.setHorizontalAlignment(Element.ALIGN_LEFT);
			c2.setPadding(5f);
			c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table3.addCell(c2);

			c2 = new PdfPCell(new Phrase(deductions_sum, MainHeadingFonts));
			c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			c2.setPadding(5f);
			c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table3.addCell(c2);

			c2 = new PdfPCell(new Phrase("Total", MainHeadingFonts));
			c2.setHorizontalAlignment(Element.ALIGN_LEFT);
			c2.setPadding(5f);
			c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table3.addCell(c2);

			c2 = new PdfPCell(new Phrase(loan_sum, MainHeadingFonts));
			c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			c2.setPadding(5f);
			c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table3.addCell(c2);

			document.add(table3);

			float float_earning = Float.parseFloat(basic_earnings_sum);
			float float_deductions = Float.parseFloat(deductions_sum);
			float float_loan = Float.parseFloat(loan_sum);
			float net_salary = float_earning - (float_deductions + float_loan);
			long net_salary_long = (long) net_salary;
			BigDecimal net_salary_decimal = new BigDecimal(Float.toString(net_salary));
			String netSalaryInWords = numberToWord(net_salary_decimal);
			System.out.println("net_salary_long" + net_salary_long);
			System.out.println("netSalaryInWords" + netSalaryInWords);
			// table4
			PdfPTable table4 = new PdfPTable(1);
			table4.setWidthPercentage(100);

			// +(basic_earnings_sum-(deductions_sum+loan_sum))
			PdfPCell table4_c1;
			table4_c1 = new PdfPCell(
					new Phrase("Net Salary : " + net_salary + "(" + netSalaryInWords + ")", MainHeadingFonts));
			table4_c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table4_c1.setPadding(5f);
			table4_c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			BaseColor fontColor = new BaseColor(0, 128, 0);  // Red color, you can change the values accordingly
			table4_c1.setBorderColor(fontColor);
			table4.addCell(table4_c1);
			document.add(table4);

//			heading = new Paragraph("\n\n", subTableHeadingFonts);
//			document.add(heading);
			PdfPTable tablenote = new PdfPTable(1);
			tablenote.setWidths(new int[] { 50 });
			tablenote.setWidthPercentage(100);
			PdfPCell notecell;
			Phrase phrase = new Phrase();

			Chunk noteChunk = new Chunk("NOTE:", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, baseGreenColor));
			Chunk restOfTextChunk = new Chunk("This is a computer-generated statement and signature is not required",
					new Font(Font.FontFamily.TIMES_ROMAN, 12));

			phrase.add(noteChunk);
			phrase.add(restOfTextChunk);

			notecell = new PdfPCell(phrase);

			notecell.setBorder(0);
			notecell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tablenote.addCell(notecell);

			notecell = new PdfPCell(new Phrase("\n", subTableHeadingFonts));
			notecell.setBorder(0);
			notecell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tablenote.addCell(notecell);

			PdfPCell dotscell;

			DottedLineCellEvent dottedLineCellEvent = new DottedLineCellEvent();
			dotscell = new PdfPCell(new Phrase("", subTableHeadingFonts));
			dotscell.setBorder(0);
			dotscell.setCellEvent(dottedLineCellEvent);
			notecell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tablenote.addCell(dotscell);
			document.add(tablenote);
			document.close();
		} catch (DocumentException e) {

			logger.info(e);

		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	public ByteArrayInputStream getEmp_Pay_Particulars_Pdf(String emp_id, String emp_type, String payment_type,
			String fromMonth, String toMonth, String fromYear, String toYear)
			throws IOException, ResourceNotFoundException {
//		Document document = new Document(PageSize.A4, 10, 10, 10, 10);
		float customWidth = 1000; // Width in points
		float customHeight = 800; // Height in points
		Document document = new Document(new Rectangle(customWidth, customHeight));

//		Document document = getRegularDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Paragraph heading = new Paragraph("", subTableHeadingFonts);
		System.out.println("hello");

		try {

//			String userid = CommonServiceImpl.getLoggedInUserId();
//			System.out.println("------Userid" + userid);

			Map<String, String> Head = payrollConfirmEmpDetails.Emp_pay_Heading(emp_id);

			PdfWriter.getInstance(document, out);
			document.open();

			document.add(getTitle("APSHCL"));
			heading.setAlignment(Element.ALIGN_CENTER);
			document.add(heading);
			heading = new Paragraph("\n", subTableHeadingFonts);
			document.add(heading);
			PdfPTable table1 = new PdfPTable(1);

			table1.setWidthPercentage(100);

			PdfPCell table1_c1;
			table1_c1 = new PdfPCell(
					new Phrase("ANDHRA  PRADESH  STATE  HOUSING  CORPN. LTD., \n\n Detailed Pay-Particular for "
							+ Head.get("employeename"), TableHeadingFonts));
			table1_c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table1_c1.setPadding(20);

			table1.addCell(table1_c1);
			document.add(table1);
			PdfPTable mainTable = new PdfPTable(1);

			if (payment_type.equals("All")) {
				List<Map<String, String>> data = payrollConfirmEmpDetails.Emp_pay_values(emp_id, emp_type, fromMonth,
						toMonth, fromYear, toYear);

				// Create the nested table for headings
				PdfPTable headingTable = new PdfPTable(4); // Increase the column count to 4 since we have 4 headings
				headingTable.setWidthPercentage(100);

				PdfPCell empidiHeadingCell = new PdfPCell(new Phrase("EMPID"));
				empidiHeadingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				empidiHeadingCell.setBackgroundColor(BaseColor.GRAY);
				empidiHeadingCell.setPadding(5);
				headingTable.addCell(empidiHeadingCell);

				PdfPCell monthHeadingCell = new PdfPCell(new Phrase("MONTH"));
				monthHeadingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				monthHeadingCell.setBackgroundColor(BaseColor.GRAY);
				monthHeadingCell.setPadding(5);
				headingTable.addCell(monthHeadingCell);

				PdfPCell yearHeadingCell = new PdfPCell(new Phrase("YEAR"));
				yearHeadingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				yearHeadingCell.setBackgroundColor(BaseColor.GRAY);
				yearHeadingCell.setPadding(5);
				headingTable.addCell(yearHeadingCell);

				PdfPCell nameHeadingCell = new PdfPCell(new Phrase("Emp Name"));
				nameHeadingCell.setHorizontalAlignment(Element.ALIGN_CENTER); // Fix the alignment to
																				// "Element.ALIGN_CENTER"
				nameHeadingCell.setBackgroundColor(BaseColor.GRAY);
				nameHeadingCell.setPadding(5);
				headingTable.addCell(nameHeadingCell);

				PdfPCell headingCell = new PdfPCell(headingTable);
				headingCell.setBorder(Rectangle.NO_BORDER);
				mainTable.addCell(headingCell);

				PdfPTable dataValueTable = new PdfPTable(4);
				dataValueTable.setWidthPercentage(100);

				for (Map<String, String> rowData : data) {
					PdfPCell empidiDataCell = new PdfPCell(new Phrase(rowData.get("emp_id")));
					dataValueTable.addCell(empidiDataCell);

					PdfPCell monthDataCell = new PdfPCell(new Phrase(rowData.get("month_name")));
					dataValueTable.addCell(monthDataCell);

					PdfPCell yearDataCell = new PdfPCell(new Phrase(rowData.get("year")));
					dataValueTable.addCell(yearDataCell);

					PdfPCell empnameDataCell = new PdfPCell(new Phrase(rowData.get("emp_name")));
					dataValueTable.addCell(empnameDataCell);
				}

				PdfPCell dataValueCell = new PdfPCell(dataValueTable);
				dataValueCell.setBorder(Rectangle.NO_BORDER);
				mainTable.addCell(dataValueCell);

				document.add(mainTable);
			}

			document.close();
		} catch (DocumentException e) {

			logger.info(e);

		}
		return new ByteArrayInputStream(out.toByteArray());
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

	public PdfPCell addCellWithBorder(String name, BaseColor baseColor, int colspan, int elementPosition, Font font) {
		PdfPCell c25 = new PdfPCell(new Phrase(name, font));
		c25.setBackgroundColor(baseColor);
		c25.setColspan(colspan);
		c25.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c25.setPaddingBottom(7);

		c25.setHorizontalAlignment(elementPosition);
		return c25;
	}

	private static final String[] tensArray = { "", " Ten", " Twenty", " Thirty", " Forty", " Fifty", " Sixty",
			" Seventy", " Eighty", " Ninety" };

	private static final String[] unitsArray = { "", " One", " Two", " Three", " Four", " Five", " Six", " Seven",
			" Eight", " Nine", " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen",
			" Seventeen", " Eighteen", " Nineteen" };

	public static String numberToWord(BigDecimal num) {

		Long number = num.longValue();
//        long no = num.longValue();
//        int decimal = (int) (num.remainder(BigDecimal.ONE).doubleValue() * 100);
		StringBuilder sb = new StringBuilder();

		if (number == 0)
			return "zero";
		if (number < 0)
			return sb
					.append("Minus " + numberToWord(BigDecimal.valueOf(Long.parseLong(number.toString().substring(1)))))
					.toString();
		long i = 0;
		if ((i = number / 10000000) > 0) {
			sb.append(numberToWord(BigDecimal.valueOf(i)) + " Crore" + (i > 9 ? "s " : " "));
			number %= 10000000;
		}
		if ((i = number / 100000) > 0) {
			sb.append(numberToWord(BigDecimal.valueOf(i)) + " Lakh" + (i > 9 ? "s " : " "));
			number %= 100000;
		}
		if ((i = number / 1000) > 0) {
			sb.append(numberToWord(BigDecimal.valueOf(i)) + " Thousand ");
			number %= 1000;
		}
		if ((i = number / 100) > 0) {
			sb.append(numberToWord(BigDecimal.valueOf(i)) + " Hundred ");
			number %= 100;
		}
		if (number > 0) {
			if (number < 20)
				sb.append(unitsArray[number.intValue()]);
			else {
				sb.append(tensArray[number.intValue() / 10]);
				if ((number % 10) > 0) {
//					sb.append("-" + unitsArray[number.intValue() % 10]);
					sb.append(unitsArray[number.intValue() % 10]);
				}
			}
		}
//		sb.append(" only");
		return sb.toString();
	}

	private Paragraph createAlignedParagraph(String text, int alignment) {
		Paragraph paragraph = new Paragraph(text, subTableHeadingFonts);
		paragraph.setAlignment(alignment);
		return paragraph;
	}

	public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {

		PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];

		float x = position.getLeft();

		canvas.setLineDash(3f, 3f);

		canvas.moveTo(x, position.getTop());

		canvas.lineTo(x, position.getBottom());

		canvas.stroke();

	}

	public ByteArrayInputStream generatePayslipForEmployees(List<MonthlyPaybillRequest> monthlyPaybillRequests)
			throws IOException {
		Document document = getRegularDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);
		try {
			PdfWriter.getInstance(document, out);
			document.open();
			Map<String, String> dist_data = pdf_View_Repo.getdist_data(security_id, userid);
//			for (MonthlyPaybillRequest request : monthlyPaybillRequests) {
			for (int i = 0; i < monthlyPaybillRequests.size(); i++) {
				MonthlyPaybillRequest request = monthlyPaybillRequests.get(i);

				String emp_id = request.getEmp_id();
				String payment_type = request.getPayment_type();
				String confirm_month = request.getConfirm_month();
				String year = request.getYear();

				Map<String, String> empDetails = payrollConfirmEmpDetails.FormEmpnameByName(emp_id, payment_type,
						confirm_month, year);
				List<Map<String, Object>> earning = payrollConfirmEmpDetails.FormEarningDetailsByName(emp_id,
						payment_type, confirm_month, year);

				List<Map<String, Object>> deductions = payrollConfirmEmpDetails.FormDeductionDetailsByName(emp_id,
						payment_type, confirm_month, year);

				List<Map<String, Object>> loan = payrollConfirmEmpDetails.FormLoanDetailsByName(emp_id, payment_type,
						confirm_month, year);
				// Start new page for every two employees
				if (i > 0 && i % 2 == 0) {
					document.newPage();
				}
				Paragraph heading1 = new Paragraph();
				heading1.setAlignment(Paragraph.ALIGN_CENTER);
				Font 	heading1Font = new Font(MainHeadingFonts);
				 
				heading1.add(new Chunk("ANDHRA PRADESH STATE HOUSING CORPORATION LIMITED (APSHCL)",
						new Font(heading1Font.getBaseFont(), heading1Font.getSize(), Font.BOLD, baseGreenColor)));

				document.add(heading1);
				Font 	heading2Font = new Font(subTableHeadingFonts);
				
				Paragraph heading2 = new Paragraph();
				heading2.add(new Chunk("Payslip For " + empDetails.get("month_name") + "-" + empDetails.get("year"),
						new Font(heading2Font.getBaseFont(), heading2Font.getSize(), Font.BOLD, baseGreenColor)));
				heading2.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(heading2);
				// Add logo
				Image logo = Image.getInstance("classpath:/images/Andhra-Pradesh-AP-Govt-Logo.png");
				PdfPCell logoCell = new PdfPCell(logo, true);
				logoCell.setBorder(Rectangle.NO_BORDER);
				// Create a new Paragraph
				Paragraph side = new Paragraph();

				Font valueFont = new Font(MainHeadingFonts);
				Font labelFont = new Font(subTableHeadingFonts);

	// District Name
				side.add(new Chunk("\nDistrict Name: ", labelFont));
				side.add(new Chunk(dist_data.get("display_name"),
						new Font(labelFont.getBaseFont(), labelFont.getSize(), Font.UNDEFINED, BaseColor.BLUE)));

	// Employee Id
				side.add(new Chunk("\nEmployee Id: ", labelFont));
				side.add(new Chunk(empDetails.get("emp_id"),
						new Font(labelFont.getBaseFont(), labelFont.getSize(), Font.UNDEFINED, BaseColor.BLUE)));

	// Employee Name
				side.add(new Chunk("\nEmployee Name: ", labelFont));
				side.add(new Chunk(empDetails.get("emp_name"),
						new Font(labelFont.getBaseFont(), labelFont.getSize(), Font.UNDEFINED, BaseColor.BLUE)));

	// Designation
				side.add(new Chunk("\nDesignation: ", labelFont));
				side.add(new Chunk(empDetails.get("deg_name"),
						new Font(labelFont.getBaseFont(), labelFont.getSize(), Font.UNDEFINED, BaseColor.BLUE)));

	// Payment Type
				side.add(new Chunk("\nPayment Type: ", labelFont));
				side.add(new Chunk(empDetails.get("emptype_name"),
						new Font(labelFont.getBaseFont(), labelFont.getSize(), Font.UNDEFINED, BaseColor.BLUE)));

				PdfPCell SideCell = new PdfPCell(side);
				// Create a table with two cells
				PdfPTable table = new PdfPTable(2);
				table.setWidthPercentage(100);
				SideCell.setBorder(Rectangle.NO_BORDER);
				table.setWidths(new float[] { 6, 1 });

				table.addCell(SideCell);
				table.addCell(logoCell);
				document.add(table);
				LineSeparator ls = new LineSeparator();
				document.add(new Chunk(ls));
				// table Headings
				PdfPTable tableHead = new PdfPTable(6);
				tableHead.setWidths(new int[] { 20, 20, 20, 20, 25, 20 });
				tableHead.setWidthPercentage(100);

				PdfPCell c1 = new PdfPCell(new Phrase("Earnings", MainHeadingFonts));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c1.setPadding(5f);
				tableHead.addCell(c1);

				c1 = new PdfPCell(new Phrase("Amount", MainHeadingFonts));
				c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c1.setPadding(5f);
				tableHead.addCell(c1);

				c1 = new PdfPCell(new Phrase("Deductions", MainHeadingFonts));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c1.setPadding(5f);
				tableHead.addCell(c1);

				c1 = new PdfPCell(new Phrase("Amount", MainHeadingFonts));
				c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c1.setPadding(5f);
				tableHead.addCell(c1);

				c1 = new PdfPCell(new Phrase("Loan/Advances", MainHeadingFonts));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c1.setPadding(5f);
				tableHead.addCell(c1);

				c1 = new PdfPCell(new Phrase("Amount", MainHeadingFonts));
				c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c1.setPadding(5f);
				tableHead.addCell(c1);

				document.add(tableHead);
				// table values earning decutions and loan
				PdfPTable table2 = new PdfPTable(3);
				table2.setWidths(new int[] { 40, 40, 45 });
				table2.setWidthPercentage(100);

				for (Map<String, Object> data : earning) {
					PdfPCell cell;
					cell = new PdfPCell();

					String BasicPay = String.valueOf(data.get("basic_pay"));
					String PerPay = String.valueOf(data.get("per_pay"));
					String SplPay = String.valueOf(data.get("spl_pay"));
					String Da = String.valueOf(data.get("da"));
					String Hra = String.valueOf(data.get("hra"));
					String Cca = String.valueOf(data.get("cca"));
					String Gp = String.valueOf(data.get("gp"));
					String Ir = String.valueOf(data.get("ir"));
					String Medical = String.valueOf(data.get("medical"));
					String Ca = String.valueOf(data.get("ca"));
					String Spl_all = String.valueOf(data.get("spl_all"));
					String Misc_h_c = String.valueOf(data.get("misc_h_c"));
					String Addl_hra = String.valueOf(data.get("addl_hra"));
					String Sca = String.valueOf(data.get("sca"));

					// Repeat this process for other data fields

					if (!BasicPay.equals("No")) {

						Phrase EarnigsLabel = new Phrase("Basic Pay", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(BasicPay, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}

					if (!PerPay.equals("No")) {

						Phrase EarnigsLabel = new Phrase("Per Pay", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(PerPay, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}
					if (!SplPay.equals("No")) {

						Phrase EarnigsLabel = new Phrase("SplPay", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(SplPay, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}

					if (!Da.equals("No")) {

						Phrase EarnigsLabel = new Phrase("Da", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Da, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}

					if (!Hra.equals("No")) {

						Phrase EarnigsLabel = new Phrase("Hra", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Hra, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}
					if (!Cca.equals("No")) {

						Phrase EarnigsLabel = new Phrase("Cca", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Cca, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}
					if (!Gp.equals("No")) {

						Phrase EarnigsLabel = new Phrase("Gp", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Gp, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}
					if (!Ir.equals("No")) {

						Phrase EarnigsLabel = new Phrase("Ir", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Ir, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}
					if (!Medical.equals("No")) {

						Phrase EarnigsLabel = new Phrase("Medical", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Medical, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}

					if (!Ca.equals("No")) {

						Phrase EarnigsLabel = new Phrase("Ca", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Ca, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}
					if (!Spl_all.equals("No")) {

						Phrase EarnigsLabel = new Phrase("Spl_all", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Spl_all, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}

					if (!Misc_h_c.equals("No")) {

						Phrase EarnigsLabel = new Phrase("Misc_h_c", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Misc_h_c, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}
					if (!Addl_hra.equals("No")) {

						Phrase EarnigsLabel = new Phrase("Addl_hra", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Addl_hra, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}

					if (!Sca.equals("No")) {

						Phrase EarnigsLabel = new Phrase("Sca", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(EarnigsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Sca, Element.ALIGN_RIGHT));

						cell.addElement(paragraph);
					}

					table2.addCell(cell);
				}
				for (Map<String, Object> data1 : deductions) {
					PdfPCell cell1 = new PdfPCell();

					String gpfs = String.valueOf(data1.get("gpfs"));
					String gpfsa = String.valueOf(data1.get("gpfsa"));
					String gpfl = String.valueOf(data1.get("gpfl"));
					String apglis = String.valueOf(data1.get("apglis"));
					String apglil = String.valueOf(data1.get("apglil"));
					String gis = String.valueOf(data1.get("gis"));
					String lic = String.valueOf(data1.get("lic"));
					String license_ded = String.valueOf(data1.get("license_ded"));
					String con_decd = String.valueOf(data1.get("con_decd"));
					String epf = String.valueOf(data1.get("epf"));
					String epf_l = String.valueOf(data1.get("epf_l"));
					String vpf = String.valueOf(data1.get("vpf"));
					String ppf = String.valueOf(data1.get("ppf"));
					String rcs_cont = String.valueOf(data1.get("rcs_cont"));
					String cmrf = String.valueOf(data1.get("cmrf"));
					String fcf = String.valueOf(data1.get("fcf"));
					String house_rent = String.valueOf(data1.get("house_rent"));
					String sal_rec = String.valueOf(data1.get("sal_rec"));
					String pt = String.valueOf(data1.get("pt"));
					String it = String.valueOf(data1.get("it"));
					String postal_rd = String.valueOf(data1.get("postal_rd"));
					String other_deductions = String.valueOf(data1.get("other_deductions"));

					if (!gpfs.equals("No")) {
						Phrase DeductionsLabel = new Phrase("GPFS", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(DeductionsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(gpfs, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!gpfsa.equals("No")) {
						Phrase gpfsaLabel = new Phrase("GPFSA", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(gpfsaLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(gpfsa, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}
					if (!gpfl.equals("No")) {
						Phrase gpflLabel = new Phrase("GPFL", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(gpflLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(gpfl, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!apglis.equals("No")) {
						Phrase apglisLabel = new Phrase("APGLIS", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(apglisLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(apglis, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!apglil.equals("No")) {
						Phrase apglilLabel = new Phrase("APGLIL", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(apglilLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(apglil, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!gis.equals("No")) {
						Phrase gisLabel = new Phrase("GIS", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(gisLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(gis, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}
					if (!lic.equals("No")) {
						Phrase licLabel = new Phrase("LIC", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(licLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(lic, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!license_ded.equals("No")) {
						Phrase licenseDedLabel = new Phrase("License Ded", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(licenseDedLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(license_ded, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!con_decd.equals("No")) {
						Phrase conDecdLabel = new Phrase("Con Decd", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(conDecdLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(con_decd, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!epf.equals("No")) {
						Phrase epfLabel = new Phrase("EPF", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(epfLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(epf, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}
					if (!epf_l.equals("No")) {
						Phrase epfLLabel = new Phrase("EPF_L", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(epfLLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(epf_l, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!vpf.equals("No")) {
						Phrase vpfLabel = new Phrase("VPF", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(vpfLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(vpf, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}
					if (!ppf.equals("No")) {
						Phrase ppfLabel = new Phrase("PPF", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(ppfLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(ppf, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!rcs_cont.equals("No")) {
						Phrase rcsContLabel = new Phrase("RCS Cont", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(rcsContLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(rcs_cont, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}
					if (!cmrf.equals("No")) {
						Phrase cmrfLabel = new Phrase("CMRF", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(cmrfLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(cmrf, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!fcf.equals("No")) {
						Phrase fcfLabel = new Phrase("FCF", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(fcfLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(fcf, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!house_rent.equals("No")) {
						Phrase houseRentLabel = new Phrase("House Rent", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(houseRentLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(house_rent, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!sal_rec.equals("No")) {
						Phrase salRecLabel = new Phrase("Sal Rec", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(salRecLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(sal_rec, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}
					// Repeat this block for other data fields

					if (!pt.equals("No")) {
						Phrase ptLabel = new Phrase("PT", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(ptLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(pt, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!it.equals("No")) {
						Phrase itLabel = new Phrase("IT", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(itLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(it, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}
					// Repeat this block for other data fields

					if (!postal_rd.equals("No")) {
						Phrase postalRdLabel = new Phrase("Postal RD", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(postalRdLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(postal_rd, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					if (!other_deductions.equals("No")) {
						Phrase otherDeductionsLabel = new Phrase("Other Deductions", subTableHeadingFonts);
						Paragraph paragraph = new Paragraph();
						paragraph.add(otherDeductionsLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(other_deductions, Element.ALIGN_RIGHT));
						cell1.addElement(paragraph);
					}

					table2.addCell(cell1);
				}

				for (Map<String, Object> data2 : loan) {
					PdfPCell cell3 = new PdfPCell();

					String CarA = String.valueOf(data2.get("car_a"));
					String CarI = String.valueOf(data2.get("car_i"));
					String CycI = String.valueOf(data2.get("cyc_i"));
					String CycA = String.valueOf(data2.get("cyc_a"));
					String McaI = String.valueOf(data2.get("mca_i"));
					String McaA = String.valueOf(data2.get("mca_a"));
					String MarA = String.valueOf(data2.get("mar_a"));
					String MedA = String.valueOf(data2.get("med_a"));
					String Hba = String.valueOf(data2.get("hba"));
					String Hba1 = String.valueOf(data2.get("hbal"));
					String CompA = String.valueOf(data2.get("comp_a"));
					String Fa = String.valueOf(data2.get("fa"));
					String Ea = String.valueOf(data2.get("ea"));
					String Cell = String.valueOf(data2.get("cell"));
					String AddHba = String.valueOf(data2.get("add_hba"));
					String AddHba1 = String.valueOf(data2.get("add_hba1"));
					String SalAdv = String.valueOf(data2.get("sal_adv"));
					String Sfa = String.valueOf(data2.get("sfa"));
					String MedAI = String.valueOf(data2.get("med_a_i"));
					String Hcesa = String.valueOf(data2.get("hcesa"));
					String HcesaI = String.valueOf(data2.get("hcesa_i"));
					String StaffPl = String.valueOf(data2.get("staff_pl"));
					String Court = String.valueOf(data2.get("court"));
					String VijBank = String.valueOf(data2.get("personal_bank"));
					String MarI = String.valueOf(data2.get("mar_i"));
					String HrArrear = String.valueOf(data2.get("hr_arrear"));
					String Hbao = String.valueOf(data2.get("hbao"));
					String Comp1 = String.valueOf(data2.get("comp1"));
					String CarIPi = String.valueOf(data2.get("car_ipi"));
					String CarAPi = String.valueOf(data2.get("car_api"));
					String CycIPi = String.valueOf(data2.get("cyc_ipi"));
					String CycAPi = String.valueOf(data2.get("cyc_api"));
					String McaIPi = String.valueOf(data2.get("mca_ipi"));
					String McaAPi = String.valueOf(data2.get("mca_api"));
					String MarAPi = String.valueOf(data2.get("mar_api"));
					String MedAPi = String.valueOf(data2.get("med_api"));
					String HbaPi = String.valueOf(data2.get("hbapi"));
					String HbalPi = String.valueOf(data2.get("hba1pi"));
					String carIpi = String.valueOf(data2.get("car_ipi"));
					String CarApi = String.valueOf(data2.get("car_api"));
					String CycIpi = String.valueOf(data2.get("cyc_ipi"));
					String CycApi = String.valueOf(data2.get("cyc_api"));
					String McaIpi = String.valueOf(data2.get("mca_ipi"));
					String McaApi = String.valueOf(data2.get("mca_api"));
					String MarApi = String.valueOf(data2.get("mar_api"));
					String MedApi = String.valueOf(data2.get("med_api"));
					String CompPi = String.valueOf(data2.get("comppi"));
					String FaPi = String.valueOf(data2.get("fapi"));
					String EaPi = String.valueOf(data2.get("eapi"));
					String CellPi = String.valueOf(data2.get("cellpi"));
					String AddHbaPi = String.valueOf(data2.get("add_hbapi"));
					String AddHba1Pi = String.valueOf(data2.get("add_hba1pi"));
					String SalAdvPi = String.valueOf(data2.get("sal_adv_pi"));
					String SfaPi = String.valueOf(data2.get("sfapi"));
					String MedAIPi = String.valueOf(data2.get("med_a_ipi"));
					String HcesaPi = String.valueOf(data2.get("hcesapi"));
					String HcesaIPi = String.valueOf(data2.get("hcesa_ipi"));
					String StaffPlPi = String.valueOf(data2.get("staff_plpi"));
					String CourtPi = String.valueOf(data2.get("courtpi"));

					String Bank = String.valueOf(data2.get("vij_bankpi"));
					String MarIPi = String.valueOf(data2.get("mar_ipi"));
					String HrArrearPi = String.valueOf(data2.get("hr_arrearpi"));
					String HbaoPi = String.valueOf(data2.get("hbaopi"));
					String ComplPi = String.valueOf(data2.get("comp1pi"));
					String CarIti = String.valueOf(data2.get("car_iti"));
					String CarAti = String.valueOf(data2.get("car_ati"));
					String CycIti = String.valueOf(data2.get("cyc_iti"));
					String CycAti = String.valueOf(data2.get("cyc_ati"));
					String McaIti = String.valueOf(data2.get("mca_iti"));
					String McaAti = String.valueOf(data2.get("mca_ati"));
					String MarAti = String.valueOf(data2.get("mar_ati"));
					String MedAti = String.valueOf(data2.get("med_ati"));
					String HbaTi = String.valueOf(data2.get("hbati"));
					String Hba1Ti = String.valueOf(data2.get("hba1ti"));
					String CompTi = String.valueOf(data2.get("compti"));
					String Fati = String.valueOf(data2.get("fati"));
					String Eati = String.valueOf(data2.get("eati"));
					String Cellti = String.valueOf(data2.get("cellti"));
					String AddHbaTi = String.valueOf(data2.get("add_hbati"));
					String AddHablTi = String.valueOf(data2.get("add_hba1ti"));
					String SalAdvti = String.valueOf(data2.get("sal_advti"));
					String SfaTi = String.valueOf(data2.get("sfati"));
					String MedAiti = String.valueOf(data2.get("med_a_iti"));
					String Hcesati = String.valueOf(data2.get("hcesati"));
					String Hcesaiti = String.valueOf(data2.get("hcesaiti"));
					String Staffplti = String.valueOf(data2.get("staff_plti"));
					String Courtti = String.valueOf(data2.get("courtti"));
					String Bankti = String.valueOf(data2.get("vij_bankti"));
					String Mariti = String.valueOf(data2.get("mar_iti"));
					String HrArrearTi = String.valueOf(data2.get("hr_arrearti"));
					String HbaoTi = String.valueOf(data2.get("hbaoti"));
					String Comp1ti = String.valueOf(data2.get("comp1ti"));
					if (!CarA.equals("No")) {
						Phrase loanLabel = new Phrase("Car A", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CarA, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CarI.equals("No")) {
						Phrase loanLabel = new Phrase("Car I", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CarI, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!CycA.equals("No")) {
						Phrase loanLabel = new Phrase("Cyc A", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CycA, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CycI.equals("No")) {
						Phrase loanLabel = new Phrase("Cyc I", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CycI, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!McaA.equals("No")) {
						Phrase loanLabel = new Phrase("Mca A", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(McaA, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!McaI.equals("No")) {
						Phrase loanLabel = new Phrase("Mca I", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(McaI, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!MarA.equals("No")) {
						Phrase loanLabel = new Phrase("Mar A", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MarA, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!MedA.equals("No")) {
						Phrase loanLabel = new Phrase("Med A", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MedA, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Hba.equals("No")) {
						Phrase loanLabel = new Phrase("Hba", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Hba, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Hba1.equals("No")) {
						Phrase loanLabel = new Phrase("Hba1", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Hba1, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!MedA.equals("No")) {
						Phrase loanLabel = new Phrase("Med A", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MedA, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CompA.equals("No")) {
						Phrase loanLabel = new Phrase("CompA", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CompA, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Fa.equals("No")) {
						Phrase loanLabel = new Phrase("Fa", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Fa, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Fa.equals("No")) {
						Phrase loanLabel = new Phrase("Fa", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Fa, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!Ea.equals("No")) {
						Phrase loanLabel = new Phrase("Ea", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Ea, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Cell.equals("No")) {
						Phrase loanLabel = new Phrase("Cell", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Cell, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!AddHba.equals("No")) {
						Phrase loanLabel = new Phrase("AddHba", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(AddHba, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!AddHba1.equals("No")) {
						Phrase loanLabel = new Phrase("AddHba1", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(AddHba1, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!SalAdv.equals("No")) {
						Phrase loanLabel = new Phrase("SalAdv", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(SalAdv, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Sfa.equals("No")) {
						Phrase loanLabel = new Phrase("Sfa", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Sfa, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!MedAI.equals("No")) {
						Phrase loanLabel = new Phrase("MedAI", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MedAI, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Hcesa.equals("No")) {
						Phrase loanLabel = new Phrase("Hcesa", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Hcesa, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!HcesaI.equals("No")) {
						Phrase loanLabel = new Phrase("HcesaI", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(HcesaI, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!StaffPl.equals("No")) {
						Phrase loanLabel = new Phrase("StaffPl", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(StaffPl, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Court.equals("No")) {
						Phrase loanLabel = new Phrase("Court", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Court, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!VijBank.equals("No")) {
						Phrase loanLabel = new Phrase("VijBank", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(VijBank, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!MarI.equals("No")) {
						Phrase loanLabel = new Phrase("Sfa", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MarI, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!HrArrear.equals("No")) {
						Phrase loanLabel = new Phrase("HrArrear", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(HrArrear, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Hbao.equals("No")) {
						Phrase loanLabel = new Phrase("Hbao", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Hbao, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Comp1.equals("No")) {
						Phrase loanLabel = new Phrase("Comp1", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Comp1, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!CarIPi.equals("No")) {
						Phrase loanLabel = new Phrase("CarIPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CarIPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CarAPi.equals("No")) {
						Phrase loanLabel = new Phrase("CarAPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CarAPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!CycIPi.equals("No")) {
						Phrase loanLabel = new Phrase("CycIPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CycIPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CycAPi.equals("No")) {
						Phrase loanLabel = new Phrase("CycAPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CycAPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!McaIPi.equals("No")) {
						Phrase loanLabel = new Phrase("McaIPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(McaIPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!McaAPi.equals("No")) {
						Phrase loanLabel = new Phrase("McaAPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(McaAPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!MarAPi.equals("No")) {
						Phrase loanLabel = new Phrase("MarAPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MarAPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!MedAPi.equals("No")) {
						Phrase loanLabel = new Phrase("MedAPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MedAPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!HbaPi.equals("No")) {
						Phrase loanLabel = new Phrase("HbaPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(HbaPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!HbalPi.equals("No")) {
						Phrase loanLabel = new Phrase("HbalPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(HbalPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!carIpi.equals("No")) {
						Phrase loanLabel = new Phrase("carIpi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(carIpi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CarApi.equals("No")) {
						Phrase loanLabel = new Phrase("CarApi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CarApi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CycIpi.equals("No")) {
						Phrase loanLabel = new Phrase("CycIpi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CycIpi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CycApi.equals("No")) {
						Phrase loanLabel = new Phrase("CycApi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CycApi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!McaIpi.equals("No")) {
						Phrase loanLabel = new Phrase("McaIpi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(McaIpi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!McaApi.equals("No")) {
						Phrase loanLabel = new Phrase("McaApi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(McaApi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!MarApi.equals("No")) {
						Phrase loanLabel = new Phrase("MarApi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MarApi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!MedApi.equals("No")) {
						Phrase loanLabel = new Phrase("MedApi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MedApi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!CompPi.equals("No")) {
						Phrase loanLabel = new Phrase("CompPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CompPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!FaPi.equals("No")) {
						Phrase loanLabel = new Phrase("FaPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(FaPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!EaPi.equals("No")) {
						Phrase loanLabel = new Phrase("EaPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(EaPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!CellPi.equals("No")) {
						Phrase loanLabel = new Phrase("CellPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CellPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!AddHbaPi.equals("No")) {
						Phrase loanLabel = new Phrase("AddHbaPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(AddHbaPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!AddHba1Pi.equals("No")) {
						Phrase loanLabel = new Phrase("AddHba1Pi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(AddHba1Pi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!SalAdvPi.equals("No")) {
						Phrase loanLabel = new Phrase("SalAdvPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(SalAdvPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!SfaPi.equals("No")) {
						Phrase loanLabel = new Phrase("SfaPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(SfaPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!MedAIPi.equals("No")) {
						Phrase loanLabel = new Phrase("MedAIPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MedAIPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!HcesaPi.equals("No")) {
						Phrase loanLabel = new Phrase("HcesaPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(HcesaPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!HcesaIPi.equals("No")) {
						Phrase loanLabel = new Phrase("HcesaIPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(HcesaIPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!StaffPlPi.equals("No")) {
						Phrase loanLabel = new Phrase("StaffPlPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(StaffPlPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CourtPi.equals("No")) {
						Phrase loanLabel = new Phrase("CourtPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CourtPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!Bank.equals("No")) {
						Phrase loanLabel = new Phrase("Bank", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Bank, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!MarIPi.equals("No")) {
						Phrase loanLabel = new Phrase("MarIPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MarIPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!HrArrearPi.equals("No")) {
						Phrase loanLabel = new Phrase("HrArrearPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(HrArrearPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!HbaoPi.equals("No")) {
						Phrase loanLabel = new Phrase("HbaoPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(HbaoPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!ComplPi.equals("No")) {
						Phrase loanLabel = new Phrase("ComplPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(ComplPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!CarIti.equals("No")) {
						Phrase loanLabel = new Phrase("CarIti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CarIti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CarAti.equals("No")) {
						Phrase loanLabel = new Phrase("CarAti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CarAti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CycIti.equals("No")) {
						Phrase loanLabel = new Phrase("CycIti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CycIti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CycAti.equals("No")) {
						Phrase loanLabel = new Phrase("CycAti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CycAti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CourtPi.equals("No")) {
						Phrase loanLabel = new Phrase("CourtPi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CourtPi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!CycAti.equals("No")) {
						Phrase loanLabel = new Phrase("McaIti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(McaIti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!McaIti.equals("No")) {
						Phrase loanLabel = new Phrase("McaIti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(McaIti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!McaAti.equals("No")) {
						Phrase loanLabel = new Phrase("McaAti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(McaAti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!MarAti.equals("No")) {
						Phrase loanLabel = new Phrase("MarAti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MarAti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!MedAti.equals("No")) {
						Phrase loanLabel = new Phrase("MedAti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MedAti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!HbaTi.equals("No")) {
						Phrase loanLabel = new Phrase("HbaTi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(HbaTi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!Hba1Ti.equals("No")) {
						Phrase loanLabel = new Phrase("Hba1Ti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Hba1Ti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!CompTi.equals("No")) {
						Phrase loanLabel = new Phrase("CompTi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(CompTi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!Fati.equals("No")) {
						Phrase loanLabel = new Phrase("Fati", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Fati, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Eati.equals("No")) {
						Phrase loanLabel = new Phrase("Eati", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Eati, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Cellti.equals("No")) {
						Phrase loanLabel = new Phrase("Cellti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Cellti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!AddHbaTi.equals("No")) {
						Phrase loanLabel = new Phrase("AddHbaTi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(AddHbaTi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!AddHablTi.equals("No")) {
						Phrase loanLabel = new Phrase("AddHablTi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(AddHablTi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!SalAdvti.equals("No")) {
						Phrase loanLabel = new Phrase("SalAdvti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(SalAdvti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!SfaTi.equals("No")) {
						Phrase loanLabel = new Phrase("SfaTi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(SfaTi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!MedAiti.equals("No")) {
						Phrase loanLabel = new Phrase("MedAiti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(MedAiti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Hcesati.equals("No")) {
						Phrase loanLabel = new Phrase("Hcesati", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Hcesati, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Hcesaiti.equals("No")) {
						Phrase loanLabel = new Phrase("Hcesaiti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Hcesaiti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Staffplti.equals("No")) {
						Phrase loanLabel = new Phrase("Staffplti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Staffplti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!Courtti.equals("No")) {
						Phrase loanLabel = new Phrase("Courtti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Courtti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Bankti.equals("No")) {
						Phrase loanLabel = new Phrase("Bankti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Bankti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Mariti.equals("No")) {
						Phrase loanLabel = new Phrase("Mariti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Mariti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}

					if (!HrArrearTi.equals("No")) {
						Phrase loanLabel = new Phrase("HrArrearTi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(HrArrearTi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!HbaoTi.equals("No")) {
						Phrase loanLabel = new Phrase("HbaoTi", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(HbaoTi, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					if (!Comp1ti.equals("No")) {
						Phrase loanLabel = new Phrase("Comp1ti", subTableHeadingFonts);

						Paragraph paragraph = new Paragraph();
						paragraph.add(loanLabel);
						paragraph.add(new Chunk(new VerticalPositionMark()));
						paragraph.add(createAlignedParagraph(Comp1ti, Element.ALIGN_RIGHT));

						cell3.addElement(paragraph);
					}
					table2.addCell(cell3);
				}

				document.add(table2);
				// Total
				String basic_earnings_sum = payrollConfirmEmpDetails.basic_earnings_sum(emp_id, payment_type,
						confirm_month, year);
				String deductions_sum = payrollConfirmEmpDetails.deductions_sum(emp_id, payment_type, confirm_month,
						year);
				String loan_sum = payrollConfirmEmpDetails.loan_sum(emp_id, payment_type, confirm_month, year);
				PdfPTable table3 = new PdfPTable(6);
				table3.setWidths(new int[] { 20, 20, 20, 20, 25, 20 });
				table3.setWidthPercentage(100);

				PdfPCell c2;

				c2 = new PdfPCell(new Phrase("Total", MainHeadingFonts));
				c2.setHorizontalAlignment(Element.ALIGN_LEFT);
				c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c2.setPadding(5f);
				table3.addCell(c2);

				c2 = new PdfPCell(new Phrase(basic_earnings_sum, MainHeadingFonts));
				c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c2.setPadding(5f);
				table3.addCell(c2);

				c2 = new PdfPCell(new Phrase("Total", MainHeadingFonts));
				c2.setHorizontalAlignment(Element.ALIGN_LEFT);
				c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c2.setPadding(5f);
				table3.addCell(c2);

				c2 = new PdfPCell(new Phrase(deductions_sum, MainHeadingFonts));
				c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c2.setPadding(5f);
				table3.addCell(c2);

				c2 = new PdfPCell(new Phrase("Total", MainHeadingFonts));
				c2.setHorizontalAlignment(Element.ALIGN_LEFT);
				c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c2.setPadding(5f);
				table3.addCell(c2);

				c2 = new PdfPCell(new Phrase(loan_sum, MainHeadingFonts));
				c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c2.setPadding(5f);
				table3.addCell(c2);

				document.add(table3);

				float float_earning = Float.parseFloat(basic_earnings_sum);
				float float_deductions = Float.parseFloat(deductions_sum);
				float float_loan = Float.parseFloat(loan_sum);
				float net_salary = float_earning - (float_deductions + float_loan);
				long net_salary_long = (long) net_salary;
				BigDecimal net_salary_decimal = new BigDecimal(Float.toString(net_salary));
				String netSalaryInWords = numberToWord(net_salary_decimal);
				System.out.println("net_salary_long" + net_salary_long);
				System.out.println("netSalaryInWords" + netSalaryInWords);
				// table4
				PdfPTable table4 = new PdfPTable(1);
				table4.setWidthPercentage(100);

				// +(basic_earnings_sum-(deductions_sum+loan_sum))
				PdfPCell table4_c1;
				table4_c1 = new PdfPCell(
						new Phrase("Net Salary : " + net_salary + "(" + netSalaryInWords + ")", MainHeadingFonts));
				table4_c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table4_c1.setPadding(5f);
				table4_c1.setBorderColor(baseGreenColor);
				table4.addCell(table4_c1);
				
				document.add(table4);

//				heading = new Paragraph("\n\n", subTableHeadingFonts);
//				document.add(heading);
				PdfPTable tablenote = new PdfPTable(1);
				tablenote.setWidths(new int[] { 50 });
				tablenote.setWidthPercentage(100);
				PdfPCell notecell;

			
				Phrase phrase = new Phrase();

				Chunk noteChunk = new Chunk("NOTE:", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, baseGreenColor));
				Chunk restOfTextChunk = new Chunk(" : This is a computer-generated statement and signature is not required",
						new Font(Font.FontFamily.TIMES_ROMAN, 12));

				phrase.add(noteChunk);
				phrase.add(restOfTextChunk);

				notecell = new PdfPCell(phrase);

				notecell.setBorder(0);
				notecell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				tablenote.addCell(notecell);

				notecell = new PdfPCell(new Phrase("\n", subTableHeadingFonts));
				notecell.setBorder(0);
				notecell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				tablenote.addCell(notecell);


				PdfPCell dotscell;

				DottedLineCellEvent dottedLineCellEvent = new DottedLineCellEvent();
				dotscell = new PdfPCell(new Phrase("", subTableHeadingFonts));
				dotscell.setBorder(0);
				dotscell.setCellEvent(dottedLineCellEvent);
				notecell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				tablenote.addCell(dotscell);
				document.add(tablenote);

			}
		} catch (DocumentException e) {
			logger.error("Error during PDF document creation:", e);
		} finally {
			document.close();
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	class DottedLineCellEvent implements PdfPCellEvent {
		@Override
		public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
			PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
			float y = position.getTop(); // Change to get the top position of the cell
			canvas.setLineDash(3f, 3f);
			canvas.moveTo(position.getLeft(), y);
			canvas.lineTo(position.getRight(), y); // Change to position.getRight() to draw along the top
			canvas.stroke();
		}
	}

}
