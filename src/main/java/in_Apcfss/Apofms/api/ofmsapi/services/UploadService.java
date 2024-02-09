package in_Apcfss.Apofms.api.ofmsapi.services;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UploadService {
	
	@Value("${file.upload-dir}")
    private String uploadPath;

	@Autowired
    private ServletContext servletContext;
    
    public boolean uploadDocument(MultipartFile file, String filePath, String fileName) {
        boolean isUploaded = false;
        try {
        	System.out.println("----File"+file);
            if (file.isEmpty()) {
                return isUploaded;
            }
            Path path = Paths.get(uploadPath+filePath).toAbsolutePath().normalize();
            if (!Files.exists(path)) {
                init(path);
            }
            @SuppressWarnings("unused")
			String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            Path targetLocation = path.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            isUploaded = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUploaded;
    }
    
    public void init(Path path) {
        System.out.println("*** documentsLocation: " + uploadPath);
        try {
            Files.createDirectories(path);
        } catch (Exception ex) {
            // throw new DocumentStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    public boolean uploadVerificationDoc(String docPath, MultipartFile uploadFile, String imageName) {
        boolean isUploaded = false;
        try {
        	String path = "";
            /*
             * if (envType.equalsIgnoreCase("DEV")) { path = "/" + uploadPath; } else if
             * (envType.equalsIgnoreCase("PROD")) { path = servletContext.getRealPath("") +
             * uploadPath; }
             */
        	path = servletContext.getRealPath("") + uploadPath+docPath;
            //path = "/" + uploadPath+docPath;
            /*
             * Path path = Paths.get(rootPath); if (!Files.exists(path)) { init(); }
             */
            //System.out.println("*********** rootPath" + path);
            File folder = new File(path);
            if (!folder.exists()) {
                boolean isGenerated = folder.mkdirs();
                System.out.println("**** isGenerated: " + isGenerated);
            }
            FileOutputStream fs = new FileOutputStream(path + "/" + imageName);
            fs.write(uploadFile.getBytes());
            fs.flush();
            fs.close();

           isUploaded = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("** exception");
            isUploaded = false;
        }
        return isUploaded;
    }

	
	
	
}
