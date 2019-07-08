package ua.kiev.prog;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class CustomUser {
    @Id
    @GeneratedValue
    private Long id;

    private String login;
    private String password;
    private String salt;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "customusers_roles",
            joinColumns = @JoinColumn(name = "customuser_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    private String email;
    private String phone;

    public CustomUser(String login, String password, String salt, Collection<Role> roles, String email, String phone) {
        this.login = login;
        this.password = password;
        this.salt = salt;
        this.roles = roles;
        this.email = email;
        this.phone = phone;
    }

    public String getPassword() { // так как пароль хранится с солю то убираем ее при получении
        return password.replace(salt, "");
    }
}
