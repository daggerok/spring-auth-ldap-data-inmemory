package daggerok.admin.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends MongoRepository<AdminUser, String> {

    PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    Optional<AdminUser> findByUsername(final String username);

    default AdminUser encodePasswordAndSave(AdminUser adminUser) {
        return save(adminUser.setPassword(PASSWORD_ENCODER.encode(adminUser.getPassword())));
    }
}
