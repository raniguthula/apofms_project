package in_Apcfss.Apofms.api.ofmsapi.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.google.zxing.WriterException;

import in_Apcfss.Apofms.api.ofmsapi.Execptions.ResourceNotFoundException;

public interface Pdf_View_Service {

	ByteArrayInputStream get_receiptvoucher_PDF(String payment_receipt_id) throws IOException, ResourceNotFoundException,WriterException ;

	ByteArrayInputStream get_paymentvoucher_PDF(String payment_receipt_id)  throws IOException, ResourceNotFoundException,WriterException ;

	ByteArrayInputStream get_journalvoucher_PDF(String payment_receipt_id)  throws IOException, ResourceNotFoundException,WriterException ;

	ByteArrayInputStream interbanktransferpdf(String payment_receipt_id,String receipt_id) throws IOException, ResourceNotFoundException,WriterException;

	
	// Reports ::Cash and Bank Rceipts and Cash and Bank Payments (by clicking on 
		// HeadId)
	
	ByteArrayInputStream get_Heads_PDF(String payment_receipt_id) throws IOException, ResourceNotFoundException ;
	// Reports ::Receipts and Payments(Cash && Bank) (by clicking on HeadId)
	
	ByteArrayInputStream get_Cash_and_Bankpdf(String headid, String fromDate, String toDate) throws IOException, ResourceNotFoundException ;

	String getInterBankSubhead(String payment_receipt_id);

	String getReceipt_id(String payment_receipt_id);

	String getPayment_Receipt_id(String payment_receipt_id);


}
