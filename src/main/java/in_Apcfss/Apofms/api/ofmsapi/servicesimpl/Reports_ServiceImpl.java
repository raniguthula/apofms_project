package in_Apcfss.Apofms.api.ofmsapi.servicesimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.Repositories.EntryForms_Repo;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.Pdf_View_Repo;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.Reports_Repo;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.UsersSecurityRepo;
import in_Apcfss.Apofms.api.ofmsapi.request.ReportsRequest;
import in_Apcfss.Apofms.api.ofmsapi.services.Reports_Service;
import java.util.Map.Entry;

@Service
public class Reports_ServiceImpl implements Reports_Service {
	@Autowired
	Reports_Repo reports_Repo;
	@Autowired
	EntryForms_Repo entryForms_Repo;
	@Autowired
	UsersSecurityRepo usersSecurityRepo;

	@Autowired
	Pdf_View_Repo pdf_View_Repo;

	@Override
	public List<Map<String, String>> GetBankBook_Report(String fromDate, String toDate) {

		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		return reports_Repo.GetBankBook_Report(fromDate, toDate, user_id);
	}

	@Override
	public List<Map<String, String>> DesignationDrop_Down() {

		return reports_Repo.DesignationDrop_Down();

	}

	@Override
	public List<Map<String, String>> GetSearchEmployee_Report(int dist_code, int designation_id, String empname,
			String empid) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		List<Map<String, String>> search = null;
		if (dist_code != 0 && designation_id == 0 && empname.equals("0") && empid.equals("0")) {
			search = reports_Repo.GetSearchEmployee_Report_dist(dist_code);
		} else if (dist_code != 0 && designation_id == 0 && !empname.equals("0") && empid.equals("0")) {
			search = reports_Repo.GetSearchEmployee_Report_dist_empname(dist_code, empname);
		} else if (dist_code != 0 && designation_id != 0 && empname.equals("0") && empid.equals("0")) {
			search = reports_Repo.GetSearchEmployee_Report_design(dist_code, designation_id);
		} else if (dist_code != 0 && designation_id != 0 && !empname.equals("0") && empid.equals("0")) {
			search = reports_Repo.GetSearchEmployee_Report_empname(dist_code, designation_id, empname);
		}

		return search;
	}

	@Override
	public List<Map<String, String>> GetSubsidiary_Report(String fromDate, String toDate, String banknameaccountno) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		String openignbal = reports_Repo.getOpeningBal(fromDate, banknameaccountno, security_id);

		System.out.println("openignbal" + openignbal);

		return reports_Repo.GetSubsidiary_Report(fromDate, toDate, banknameaccountno, security_id);

	}
	// Cash Bank Receipts Report

	@Override
	public List<Map<String, String>> GetCashBank_Receipts_Report(String fromDate, String toDate, String cash_bank,
			String transaction_type) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> BANK = null;

		if (cash_bank.equals("All") && (transaction_type.equals("R") || transaction_type.equals("P"))) {
			System.out.println("fromDate: " + fromDate);
			System.out.println("toDate: " + toDate);
			System.out.println("cash_bank: " + cash_bank);
			System.out.println("transaction_type: " + transaction_type);

			BANK = reports_Repo.GetCashBank_All_Receipts_Report(fromDate, toDate, transaction_type, security_id);
			System.out.println("BANK: " + BANK);
		} else if (transaction_type.equals("R") || transaction_type.equals("P")) {
			System.out.println("transaction_type..cash_bank: " + transaction_type);

			BANK = reports_Repo.GetCashBank_Receipts_Report(fromDate, toDate, cash_bank, transaction_type, security_id);
		}

		return BANK;
	}

	@Override
	public Map<String, Object> Get_ByPaymentReceiptId(String payment_receipt_id) {

		return pdf_View_Repo.getdata_by_transection_id(payment_receipt_id);
	}

	@Override
	public List<Map<String, Object>> Get_PaymentReceiptId_Heads(String payment_receipt_id) {

		return pdf_View_Repo.getheads(payment_receipt_id);
	}

	@Override
	public List<Map<String, String>> GetJournalVochersReport(String fromDate, String toDate) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		return reports_Repo.GetJournalVochersReport(fromDate, toDate, security_id);
	}

	@Override
	public List<Map<String, String>> GetJournalVochersReport_DataById(String payment_receipt_id) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		return reports_Repo.GetJournalVochersReport_ById(payment_receipt_id);
	}

	@Override
	public List<Map<String, String>> GetJournalVochersReport_HeadsById(String payment_receipt_id) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		return reports_Repo.GetJournalVochersReport_HeadsById(payment_receipt_id);
	}

	@Override
	public List<Map<String, String>> GetHeadWise_Details_Report(String fromDate, String toDate, String headid) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> HEADS = null;
		if (headid.equals("All")) {
			HEADS = reports_Repo.GetHeadWiseAll_Details_Report(fromDate, toDate, security_id);
		} else {
			HEADS = reports_Repo.GetHeadWise_Details_Report(fromDate, toDate, headid, security_id);
		}
		return HEADS;
	}

	@Override
	public List<Map<String, String>> GetSubLeadger_Report(String fromDate, String toDate, String headid,
			String subheadseqid, String district) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> subledger = null;

		if (district.equals("0")) {
			System.out.println("called if");
			if (headid.equals("All") && subheadseqid.equals("All")) {
				subledger = reports_Repo.getSubLedgerReportAll(fromDate, toDate, security_id);

			} else if (!headid.equals("All") && subheadseqid.equals("All")) {
				subledger = reports_Repo.getSubLedgerReportByheadids(fromDate, toDate, security_id, headid);
			} else if (!headid.equals("All") && !subheadseqid.equals("All")) {
				subledger = reports_Repo.getSubLedgerReportByIDs(fromDate, toDate, security_id, headid, subheadseqid);
			}
		} else {
			System.out.println("called else");
			security_id = district;
			System.out.println("security_id" + security_id);
			System.out.println("district" + district);
			if (headid.equals("All") && subheadseqid.equals("All")) {
				System.out.println(" all");
				subledger = reports_Repo.getSubLedgerReportAll(fromDate, toDate, security_id);

			} else if (!headid.equals("All") && subheadseqid.equals("All")) {
				System.out.println(" not  all");
				subledger = reports_Repo.getSubLedgerReportByheadids(fromDate, toDate, security_id, headid);
			} else if (!headid.equals("All") && !subheadseqid.equals("All")) {
				System.out.println("not not");
				subledger = reports_Repo.getSubLedgerReportByIDs(fromDate, toDate, security_id, headid, subheadseqid);
			}
		}

		return subledger;
	}

	@Override
	public List<Map<String, String>> GetJournalRegister_Report(String fromDate, String toDate, String district) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> journalData = null;
		if (district.equals("All")) {
			journalData = reports_Repo.GetJournalRegister_Report(fromDate, toDate, security_id);
		} else {
			security_id = district;
			journalData = reports_Repo.GetJournalRegister_Report(fromDate, toDate, security_id);
		}

		return journalData;
	}

//	public List<Map<String, Object>> GetJournalRegister_Report(String fromDate, String toDate) {
//	    String user_id = CommonServiceImpl.getLoggedInUserId();
//	    System.out.println("user_id:::::" + user_id);
//	    String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
//	    System.out.println(security_id);
//
//	    List<Map<String, Object>> result = new ArrayList<>();
//
//	    List<Map<String, String>> journalData = reports_Repo.GetJournalRegister_Report(fromDate, toDate, security_id);
//	    System.out.println("Journal Data Size: " + journalData.size());
//
//	    for (Map<String, String> entry : journalData) {
//	        Map<String, Object> formattedEntry = new HashMap<>();
//	        formattedEntry.put("upload_copy", entry.get("upload_copy"));
//	        formattedEntry.put("subheadname", entry.get("subheadname"));
//	        formattedEntry.put("description", entry.get("description"));
//	        formattedEntry.put("heads", entry.get("heads"));
//	        formattedEntry.put("to_char", entry.get("to_char"));
//	        formattedEntry.put("vid", entry.get("vid"));
//	        formattedEntry.put("headscredit", entry.get("headscredit"));
//	        formattedEntry.put("payment_receipt_id", entry.get("payment_receipt_id"));
//	        formattedEntry.put("debit", entry.get("debit"));
//	        formattedEntry.put("credit", entry.get("credit"));
//	        formattedEntry.put("headsdebit", entry.get("headsdebit"));
//
//	        List<Map<String, String>> paymentDataForReceiptHeads = getPaymentDataForReceiptHeads(entry.get("payment_receipt_id"));
//	        List<Map<String, Object>> formattedPaymentData = new ArrayList<>();
//
//	        for (Map<String, String> paymentEntry : paymentDataForReceiptHeads) {
//	            Map<String, Object> formattedPaymentEntry = new HashMap<>();
//	            formattedPaymentEntry.put("headname", paymentEntry.get("headname"));
//	            formattedPaymentEntry.put("subheadname", paymentEntry.get("subheadname"));
//	            formattedPaymentEntry.put("payment_receipt_id", paymentEntry.get("payment_receipt_id"));
//	            formattedPaymentEntry.put("debit", paymentEntry.get("debit"));
//	            formattedPaymentEntry.put("credit", paymentEntry.get("credit"));
//	            formattedPaymentData.add(formattedPaymentEntry);
//	        }
//
//	        formattedEntry.put("paymentDataForReceiptHeads", formattedPaymentData);
//	        result.add(formattedEntry);
//	    }
//
//	    // Print or use the result list as needed
//	    System.out.println("Formatted Result: " + result);
//
//	    return result;
//	}

	@Override
	public List<Map<String, String>> getPaymentDataForReceiptHeads(String payment_receipt_id) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);

		List<Map<String, String>> journalHeadsData = reports_Repo.getPaymentDataForReceiptHeads(payment_receipt_id);

		return journalHeadsData;
	}

	public List<Map<String, String>> GetGeneralLeadger_Report(String fromDate, String toDate,String district, String headid) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> General_Ledger = null;
		if (headid.equals("All")) {
			General_Ledger = reports_Repo.getGeneralLedgerReportAll(fromDate, toDate, security_id);
		} else if (!headid.equals("All")) {
			General_Ledger = reports_Repo.getGeneralLedgerReportByHeadID(fromDate, toDate, security_id, headid);
		}
		return General_Ledger;
	}

//EXPENDITURE ANAYASIS
	@Override
	public List<Map<String, String>> GetExpenditureAnalaysis_Report(String fromDate, String toDate) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> data = null;
		if (!security_id.equals("00")) {
			data = reports_Repo.GetExpenditureAnalaysis_Report(fromDate, toDate, security_id);
		} else {
			data = reports_Repo.GetExpenditureAnalaysis_Report_Apshcl(fromDate, toDate);

		}
		return data;
	}

	@Override
	public List<Map<String, String>> GetExpenditureAnalaysis_OnClick_Exp(String fromDate, String toDate,
			String classification, String code) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> onclickdata = null;
		if (!security_id.equals("00") && !classification.equals("08")) {

			System.out.println("!00-!08");
			onclickdata = reports_Repo.GetExpenditureAnalaysis_ReportDistrictOnclick(fromDate, toDate, classification,
					security_id);
		} else if (!security_id.equals("00") && classification.equals("08")) {

			System.out.println("!00-08");
			onclickdata = reports_Repo.GetExpenditureAnalaysis_ReportDistrictOnclick08(fromDate, toDate, security_id);
		} else if (security_id.equals("00") && !classification.equals("08")) {
			security_id = code;
			onclickdata = reports_Repo.GetExpenditureAnalaysis_Report_ApshclOnclick(fromDate, toDate, classification,
					security_id);

		} else if (security_id.equals("00") && classification.equals("08")) {
			System.out.println("00-08");
			security_id = code;
			onclickdata = reports_Repo.GetExpenditureAnalaysis_Report_ApshclOnclick08(fromDate, toDate, security_id);

		}

		return onclickdata;
	}

	public List<Map<String, String>> GetExpenditureAnalaysis_OnClick_Headid(String fromDate, String toDate,
			String classification, String headid) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> onclickHead = null;
		return reports_Repo.GetExpenditureAnalaysis_OnClick_Headid(fromDate, toDate, classification, security_id,
				headid);

	}

	@Override
	public List<Map<String, String>> GetReviewReport(String fromDate, String toDate, String review_flag,
			String district) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> category = null;

		if (review_flag != null) {
			System.out.println("district" + district);
			if (review_flag.equals("DistBankWise")) {
				// APSHCL state
				if (district.equals("state")) {
					System.out.println("called if");
					category = reports_Repo.getstatewisedata(fromDate, toDate);
				}
				// DH User
				else if (district.equals("0")) {
					System.out.println("called else if"+security_id);
					category = reports_Repo.getDistBankWise(fromDate, toDate, security_id);

				}
//				// APSHCL DISTRICT
				else {
					security_id = district;
					category = reports_Repo.getDistBankWise(fromDate, toDate, security_id);
				}

			}
			// APSHCL state
			else if (review_flag.equals("TrailBalance")) {
				if (district.equals("state")) {
					System.out.println("calling state in trail");
					category = reports_Repo.getTrailBlance_state(fromDate, toDate);
					if (district.equals("0")) {
						System.out.println("called else if"+security_id);
						System.out.println("..district" + district);
						category = reports_Repo.getTrailBlance(fromDate, toDate, security_id);

					}
				}
				// DH User
				else if (district.equals("0")) {
					System.out.println("called else if"+security_id);
					System.out.println("..district" + district);
					category = reports_Repo.getTrailBlance(fromDate, toDate, security_id);

				}
				// APSHCL DISTRICT
				else {
					security_id = district;

					category = reports_Repo.getTrailBlance(fromDate, toDate, security_id);
				}
			}
		} else {
			System.out.println("Review_flag is null.");
		}

		return category;
	}

	public List<Map<String, String>> GetReviewReport_OnClick(String fromDate, String toDate, String review_flag,
			String district, String headid) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> category = null;

		if (review_flag.equals("DistBankWise")) {

		} else if (review_flag.equals("TrailBalance")) {
			if (district.equals("state")) {
				System.out.println("calling state in trail");
				category = reports_Repo.getTrail_statewiseonclick(fromDate, toDate, headid);
			}
			// DH User
			else if (district.equals("0")) {
				System.out.println("called else if");
				category = reports_Repo.getTrail_DistrictWiseonclick(fromDate, toDate, headid, security_id);
			}
			// APSHCL DISTRICT
			else {
				security_id = district;

				category = reports_Repo.getTrail_DistrictWiseonclick(fromDate, toDate, headid, security_id);
			}
		}
		return category;

	}

//
	public List<Map<String, String>> GetReviewReport_In_Exp(String fromDate, String toDate, String review_flag,
			String district, String in_exp) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> category = null;

		if (review_flag != null) {
			System.out.println("review_flag" + review_flag);
			if (review_flag.equals("Income_Exp")) {
				if (district.equals("state")) {
					if (in_exp.equals("in_exp")) {
						category = reports_Repo.getstatewisedata_inexp(fromDate, toDate);
					}

				} else {
					if (in_exp.equals("in")) {
						System.out.println("called else in income/exp");
						category = reports_Repo.getstatewisedata_receipts_dist(fromDate, toDate, security_id);
					}
					if (in_exp.equals("exp")) {
						category = reports_Repo.getstatewisedata_payments_dist(fromDate, toDate, security_id);
					}

				}

			}
		} else {
			System.out.println("Review_flag is null.");
		}

		return category;
	}

//	public List<Map<String, String>> GetReviewReport_In_Exp_State(String fromDate, String toDate, String review_flag,
//			String district) {
//		String user_id = CommonServiceImpl.getLoggedInUserId();
//		System.out.println("user_id:::::" + user_id);
//		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
//		System.out.println(security_id);
//		List<Map<String, String>> category = null;
//		if (review_flag != null) {
//
//			if (review_flag.equals("Income_Exp")) {
//				if (district.equals("state")) {
//					List<Map<String, String>> response_in = reports_Repo.getstatewisedata_receipts(fromDate, toDate);
//					List<Map<String, String>> response_exp = reports_Repo.getstatewisedata_payments(fromDate, toDate);
//					if (response_in != null && response_exp != null) {
//						// Combine response_in and response_exp and assign to category
//						category = new ArrayList<>();
//						category.addAll(response_in);
//
//						category.addAll(response_exp);
//					} else {
//						// Handle the case where one or both responses are null
//						System.out.println("One or both responses are null.");
//					}
//				}
//
//			}
//		}
//
//		return category;
	// }

	@Override
	public List<Map<String, String>> GetPayments_Receips_CashBank_Report(String fromDate, String toDate,
			String cash_bank, String transaction_type, String district) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> pr_cb = null;
		if (security_id.equals("00") || security_id.equals("GM")) {
			security_id = district;
			if (cash_bank.equals("All")) {
				pr_cb = reports_Repo.GetPayments_Receips_CashBank_Report_All(fromDate, toDate, transaction_type,
						security_id);
			} else {
				pr_cb = reports_Repo.GetPayments_Receips_CashBank_Report(fromDate, toDate, cash_bank, transaction_type,
						security_id);
			}
		} else {
			if (cash_bank.equals("All")) {
				pr_cb = reports_Repo.GetPayments_Receips_CashBank_Report_All(fromDate, toDate, transaction_type,
						security_id);
			} else {
				pr_cb = reports_Repo.GetPayments_Receips_CashBank_Report(fromDate, toDate, cash_bank, transaction_type,
						security_id);
			}
		}

		return pr_cb;
	}

	public List<Map<String, String>> Months_Drop_Down() {
		return reports_Repo.Months_Drop_Down();

	}

	public List<Map<String, String>> years_Drop_Down() {
		return reports_Repo.years_Drop_Down();

	}

	@Override
	public List<Map<String, String>> GetSalary_OnlineJV_Report_Columns(String year, String month, String jv_type) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> Salary_Online = null;
		if (jv_type.equals("ojv")) {
			System.out.println("IN OJV");
			Salary_Online = reports_Repo.GetSalary_OnlineJV_Report_Columns_ojv(year, month);
		} else if (jv_type.equals("sjv")) {
			Salary_Online = reports_Repo.GetSalary_OnlineJV_Report_Columns_sjv(year, month, security_id);
		}
		return Salary_Online;
	}

	@Override
	public List<Map<String, String>> GetSalary_OnlineJV_Report_Values(String year, String month, String jv_type) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, String>> Salary_Online = null;
		if (jv_type.equals("ojv")) {
			if (security_id.equals("00")|| security_id.equals("GM")) {
				System.out.println("called GetSalary_OnlineJV_Report_Values_ojvApshcl");
				Salary_Online = reports_Repo.GetSalary_OnlineJV_Report_Values_ojvApshcl(year, month);
			} else {
				Salary_Online = reports_Repo.GetSalary_OnlineJV_Report_Values_ojv(year, month, security_id);

			}

		} else if (jv_type.equals("sjv")) {
			if (security_id.equals("00")|| security_id.equals("GM")) {
				Salary_Online = reports_Repo.GetSalary_OnlineJV_Report_Values_sjvApshcl(year, month);
			} else {
				Salary_Online = reports_Repo.GetSalary_OnlineJV_Report_Values_sjv(year, month, security_id);
			}

		}
		return Salary_Online;
	}

	// Bank Balances Report************APSHCL LOGIN**************

	@Override
	public List<Map<String, String>> BankBalancesReport() {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		return reports_Repo.BankBalancesReport(security_id);
	}

	@Override
	public Map<String, Object> ClickingOnYes(ReportsRequest reportsRequest) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		String banknameaccountno = reportsRequest.getBanknameaccountno();
		int yes = reports_Repo.updatemandal_account(banknameaccountno, security_id);
		if (yes != 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Updated Sucessfully");
		} else {
			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an Error");
		}

		return responseMap;
	}
	// Bank Book Report ****Apshcl Login ******************

	@Override
	public List<Map<String, String>> BankBookReport_Apshcl(ReportsRequest reportsRequest) {

		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);

		List<Map<String, String>> bankbook_apshcl = null;
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		String security_id = reportsRequest.getSecurity_id();
		String banknameaccountno = reportsRequest.getBanknameaccountno();
		String banks = reportsRequest.getBanks();
		String transaction_type = reportsRequest.getTransaction_type();

		if (!fromDate.equals("0") && !toDate.equals("0") && security_id.equals("0") && banknameaccountno.equals("0")
				&& banks.equals("0") && transaction_type.equals("0")) {
			bankbook_apshcl = reports_Repo.bankbook_fromdate_todate(fromDate, toDate);
		}
		if (!fromDate.equals("0") && !toDate.equals("0") && !security_id.equals("0") && banknameaccountno.equals("0")
				&& banks.equals("0") && transaction_type.equals("0")) {
			bankbook_apshcl = reports_Repo.bankbook_fromdate_todate_ByDistrict(fromDate, toDate, security_id);
		}
		if (!fromDate.equals("0") && !toDate.equals("0") && !security_id.equals("0") && banknameaccountno.equals("0")
				&& !banks.equals("0") && transaction_type.equals("0")) {
			bankbook_apshcl = reports_Repo.bankbook_fromdate_todate_ByBanks(fromDate, toDate, security_id, banks);
		}
		if (!fromDate.equals("0") && !toDate.equals("0") && !security_id.equals("0") && !banks.equals("0")
				&& !banknameaccountno.equals("0") && transaction_type.equals("0")) {

			bankbook_apshcl = reports_Repo.bankbook_fromdate_todate_ByDistrict_account(fromDate, toDate, security_id,
					banks, banknameaccountno);
		}
		if (!fromDate.equals("0") && !toDate.equals("0") && !security_id.equals("0") && !banks.equals("0")
				&& !banknameaccountno.equals("0") && !transaction_type.equals("0")) {

			bankbook_apshcl = reports_Repo.bankbook_fromdate_todate_ByDistrict_account_Mode(fromDate, toDate,
					security_id, banks, banknameaccountno, transaction_type);
		}
		return bankbook_apshcl;
	}

	@Override
	public List<Map<String, String>> AccountNoDropDown(String bankshortname) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);

		return reports_Repo.AccountNoDropDown(bankshortname);
	}

//	@Override
//	public List<Map<String, String>> getOpeningBal(String fromDate, String banknameaccountno) {
//		String user_id = CommonServiceImpl.getLoggedInUserId();
//		System.out.println("user_id:::::" + user_id);
//		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
//		System.out.println(security_id);
//		return reports_Repo.getOpeningBal(fromDate,banknameaccountno,security_id);
//	}
	// DeletePRJ report from GMF1 USER
	@Override
	public List<Map<String, String>> GetDeletePRJ(String fromDate, String toDate, String category, String district) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id:::::" + user_id);
		List<Map<String, String>> PRJ = null;
		System.out.println("category out" + category);
		if (category.equals("P")) {

			PRJ = reports_Repo.GetPgetfordelete(fromDate, toDate, district);
		}
		if (category.equals("R")) {
			System.out.println("category" + category);
			PRJ = reports_Repo.GetRgetfordelete(fromDate, toDate, district);
		} else if (category.equals("J")) {
			System.out.println("category" + category);
			PRJ = reports_Repo.GetJvgetfordelete(fromDate, toDate, district);
		}
		return PRJ;

	}

}
