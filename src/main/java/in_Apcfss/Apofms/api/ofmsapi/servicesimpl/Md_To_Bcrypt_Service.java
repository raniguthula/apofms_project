package in_Apcfss.Apofms.api.ofmsapi.servicesimpl;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.Repositories.UserRepository;
import in_Apcfss.Apofms.api.ofmsapi.models.User;

@Service
public class Md_To_Bcrypt_Service {

	UserRepository userRepository;

	public Md_To_Bcrypt_Service(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public ResponseEntity<String> Md_To_Bcrypt(String user_id, String password, String newPassword) {
		// Verify the original password before proceeding
//		boolean isPasswordCorrect = verifyPassword(user_id, password);
//		if (!isPasswordCorrect) {
//			// Handle incorrect password scenario
//			return ResponseEntity.badRequest().body("Incorrect password");
//		}

		// Generate a new bcrypt hash for the new password
		String bcryptHash = generateBcryptHash(newPassword);
		System.out.println("bcryptHash" + bcryptHash);
		// Update the user's password in the repository
		int update = userRepository.updatePassword(user_id, bcryptHash);
		System.out.println(update + "update");
		return ResponseEntity.ok("Password successfully converted to bcrypt");
	}

//	    private boolean verifyPassword(String user_id, String password) {
//	        // Implement your own logic to verify the password (e.g., querying the repository)
//	        // Return true if the password is correct, false otherwise
//	        // Replace this with your actual implementation
//	        return userRepository.verifyPassword(user_id, password);
//	    }
//	private boolean verifyPassword(String user_id, String password) {
//		// Retrieve the user from the repository based on the user_id
//
//		if (user_id == null) {
//			// User not found
//			return false;
//		}
//
//		// Assuming the user entity has a property called "password" that stores the
//		// hashed password
//		String hashedPassword = user_id.getPassword();
//
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		return encoder.matches(password, hashedPassword);
//	}

	private String generateBcryptHash(String newPassword) {
		// Implement the logic to generate a new bcrypt hash for the new password
		// Use a library or function that supports bcrypt hashing with a random salt
		// Replace this with your actual implementation
		return generate(newPassword);
	}

	private static final int STRENGTH = 12;

	public static String generate(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(STRENGTH);
		return encoder.encode(password);
	}
}
