package daggerok.admin.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Document
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUser implements Serializable {

    private static final long serialVersionUID = -6273188457340745533L;

    @Id String id;
    String username;
    String password;
    boolean enabled;
    List<String> roles;
}
