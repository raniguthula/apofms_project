package in_Apcfss.Apofms.api.ofmsapi.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
//import java.util.concurrent.CompletionService;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;

import in_Apcfss.Apofms.api.ofmsapi.Execptions.ResourceNotFoundException;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.PayrollConfirmEmpDetails;
import in_Apcfss.Apofms.api.ofmsapi.request.MonthlyPaybillRequest;
import in_Apcfss.Apofms.api.ofmsapi.request.Payment_receipts_Req;
import in_Apcfss.Apofms.api.ofmsapi.services.Pdf_View_Service;
import in_Apcfss.Apofms.api.ofmsapi.servicesimpl.Emp_pay_Service;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class Pdf_View_Controller {

	@Autowired
	Pdf_View_Service pdf_View_Service;

	@GetMapping(value = "/ReceiptEntryPdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> get_receiptvoucher_PDF(@RequestParam String payment_receipt_id)
			throws IOException, ResourceNotFoundException, WriterException {

		ByteArrayInputStream bis = pdf_View_Service.get_receiptvoucher_PDF(payment_receipt_id);
		System.out.println("hello");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=otprView.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@GetMapping(value = "/PaymentEntryPdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> get_paymentvoucher_PDF(@RequestParam String payment_receipt_id)
			throws IOException, ResourceNotFoundException, WriterException {
		ByteArrayInputStream bis = pdf_View_Service.get_paymentvoucher_PDF(payment_receipt_id);
		System.out.println("hello");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=otprView.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@GetMapping(value = "/journalvoucherpdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> get_journalvoucher_PDF(@RequestParam String payment_receipt_id)
			throws IOException, ResourceNotFoundException, WriterException {
		ByteArrayInputStream bis = pdf_View_Service.get_journalvoucher_PDF(payment_receipt_id);
		System.out.println("hello");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=otprView.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@GetMapping(value = "/interbanktransferpdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> interbanktransferpdf(@RequestParam String payment_receipt_id,
			@RequestParam String receipt_id) throws IOException, ResourceNotFoundException, WriterException {
		ByteArrayInputStream bis = pdf_View_Service.interbanktransferpdf(payment_receipt_id, receipt_id);
		System.out.println("hello");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=otprView.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@GetMapping(value = "/trailBalacePdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> trailBalacePdf(@RequestParam String payment_receipt_id)
			throws IOException, ResourceNotFoundException, WriterException {
		ByteArrayInputStream bis = null;
		String subhead = "sub";
		try {
			subhead = pdf_View_Service.getInterBankSubhead(payment_receipt_id);
			if (subhead == null) {
				subhead = "sub";
			}
		} catch (Exception e) {
			// TODO: handle exception
			subhead = "sub"; 
		}
		System.out.println("subhead" + subhead);

		if (subhead.equals("IBT")) {
			if (payment_receipt_id.startsWith("P")) {
				String receipt_id = pdf_View_Service.getReceipt_id(payment_receipt_id);
				System.out.println("receipt_id-p"+receipt_id);
				System.out.println("payment_receipt_id-p"+payment_receipt_id);
				bis = pdf_View_Service.interbanktransferpdf(payment_receipt_id, receipt_id);
				
			} else {
				String payment_id = pdf_View_Service.getPayment_Receipt_id(payment_receipt_id);
				System.out.println("receipt_id-r"+payment_id);
				System.out.println("payment_receipt_id-r"+payment_receipt_id);
				bis = pdf_View_Service.interbanktransferpdf(payment_receipt_id,payment_id );
			}


		} else {
			if (payment_receipt_id.startsWith("R")) {
				System.out.println("called R");
				bis = pdf_View_Service.get_receiptvoucher_PDF(payment_receipt_id);
			} else if (payment_receipt_id.startsWith("P")) {
				System.out.println("called P");
				bis = pdf_View_Service.get_paymentvoucher_PDF(payment_receipt_id);
			} else if (payment_receipt_id.startsWith("J")) {
				String paymentreceiptid = payment_receipt_id;
				System.out.println("called J");
				bis = pdf_View_Service.get_journalvoucher_PDF(paymentreceiptid);
			}

		}

		System.out.println("hello");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=otprView.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	// Reports ::Cash and Bank Rceipts and Cash and Bank Payments (by clicking on
	// HeadId)
	@GetMapping(value = "/HeadsPdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> get_Heads_PDF(@RequestParam String payment_receipt_id)
			throws IOException, ResourceNotFoundException {

		ByteArrayInputStream bis = pdf_View_Service.get_Heads_PDF(payment_receipt_id);
		System.out.println("hello");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=otprView.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	// Reports ::Receipts and Payments(Cash && Bank) (by clicking on HeadId)

	@GetMapping(value = "/Cash_and_Bankpdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> get_Cash_and_Bankpdf(@RequestParam String headid,
			@RequestParam String fromDate, @RequestParam String toDate) throws IOException, ResourceNotFoundException {

		ByteArrayInputStream bis = pdf_View_Service.get_Cash_and_Bankpdf(headid, fromDate, toDate);
		System.out.println("hello");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=otprView.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@Autowired
	Emp_pay_Service emp_pay_Service;
	@Autowired
	PayrollConfirmEmpDetails payrollConfirmEmpDetails;

	// *************Same API for Monthly paybill confirmation and Generated monthly
	// paybill
//	http://172.17.205.53:8082/ofmsapi/pdf/getMonthly_Paybill_Pdf?emp_id=0119040933&payment_type=reg&confirm_month=02&year=2022
	@PostMapping(value = "/getMonthly_Paybill_Pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> getMonthly_Paybill_Confirmation_Pdf(
			@RequestBody MonthlyPaybillRequest monthlyPaybillRequest)

			throws IOException, ResourceNotFoundException {
		String emp_id = monthlyPaybillRequest.getEmp_id();
		String payment_type = monthlyPaybillRequest.getPayment_type();
		String confirm_month = monthlyPaybillRequest.getConfirm_month();
		String year = monthlyPaybillRequest.getYear();
		ByteArrayInputStream bis = emp_pay_Service.getMonthly_Paybill_Pdf(emp_id, payment_type, confirm_month, year);
		System.out.println("hello");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=otprView.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

//	@GetMapping(value = "/getGenerated_Monthly_Paybill_Pdf", produces = MediaType.APPLICATION_PDF_VALUE)
//    public ResponseEntity<InputStreamResource> getGenerated_Monthly_Paybill_Pdf(@RequestParam String emp_id, @RequestParam String payment_type,
//			@RequestParam String confirm_month, @RequestParam String year) throws IOException, ResourceNotFoundException {
//        ByteArrayInputStream bis = emp_pay_Service.getGenerated_Monthly_Paybill_Pdf(emp_id, payment_type, confirm_month, year);
//        System.out.println("hello");
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline; filename=otprView.pdf");
//        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
//                .body(new InputStreamResource(bis));
//    }
	@PostMapping(value = "/getEmp_Pay_Particulars_Pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> getEmp_Pay_Particulars_Pdf(
			@RequestBody MonthlyPaybillRequest monthlyPaybillRequest) throws IOException, ResourceNotFoundException {
		String emp_type = monthlyPaybillRequest.getEmp_type();
		String emp_id = monthlyPaybillRequest.getEmp_id();
		String payment_type = monthlyPaybillRequest.getPayment_type();
		String fromMonth = monthlyPaybillRequest.getFromMonth();
		String toMonth = monthlyPaybillRequest.getToMonth();
		String fromYear = monthlyPaybillRequest.getFromYear();
		String toYear = monthlyPaybillRequest.getToYear();
		ByteArrayInputStream bis = emp_pay_Service.getEmp_Pay_Particulars_Pdf(emp_id, emp_type, payment_type, fromMonth,
				toMonth, fromYear, toYear);
		System.out.println("hello");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=otprView.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}


//
//	@PostMapping(value = "/getEmpConfirmationPdf")
//	public List<Map<String, String>> getEmpConfirmationPdf(@RequestBody MonthlyPaybillRequest monthlyPaybillRequest)
//			throws IOException, ResourceNotFoundException {
//
//		String emp_id = monthlyPaybillRequest.getEmp_id();
//
//		return emp_pay_Service.getEmpConfirmationPdf(emp_id);
//
//	}
	@PostMapping(value = "/generatePayslipForEmployees", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> generatePayslipForEmployees(
			@RequestBody List<MonthlyPaybillRequest> monthlyPaybillRequest)

			throws IOException, ResourceNotFoundException {
		
		ByteArrayInputStream bis = emp_pay_Service.generatePayslipForEmployees(monthlyPaybillRequest);
		System.out.println("hello");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=otprView.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}
}
