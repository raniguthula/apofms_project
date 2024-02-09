package in_Apcfss.Apofms.api.ofmsapi.servicesimpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



@Service
public class CommonServiceImpl {
//
//    @Autowired
//    MstHeadsRepository mstHeadsRepository;
//
//    @Autowired
//    GeneralReceiptRepo generalReceiptRepo;
//
//    @Value("${file.upload-dir}")
//    private String fileStorageLocation;
//    @Autowired
//    UploadService uploadService;

    public static String getLoggedInUserId() {
        String principal = "";
        UserDetailsImpl userPrincipal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        System.out.println("@@@@@@@@@ userPrincipal: " + userPrincipal);
        if (userPrincipal != null) {
            principal = userPrincipal.getUsername();
        }
//      else if(userPrincipal != null) {
//          principal = userPrincipal.get
//        } 
        System.out.println("@@@@@@@@@ principal: " + principal);
        return principal;
    }
	public List<Map<String, Object>> getMap(List<Tuple> tupleList) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		tupleList.stream().forEach((tuple) -> list.add(tuple.getElements().stream()
				.collect(Collectors.toMap((e) -> e.getAlias(), (e) -> tuple.get(e) == null ? "" : tuple.get(e)))));
		
		return list;
	}
	
   
}