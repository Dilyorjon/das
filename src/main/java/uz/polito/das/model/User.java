package uz.polito.das.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends AuditModel implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "username", length = 60, unique = true, nullable = false)
  private String username;

  @Column(name = "password", length = 60, nullable = false)
  protected String password;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "role")
  private String role;


  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "user_roles", joinColumns = {
    @JoinColumn(name = "user_id")}, inverseJoinColumns = {
    @JoinColumn(name = "role_id")})
  private Set<Role> roles;


//
//    public User( Integer id, String username ) {
//        super( );
//        this.id = id;
//        this.username = username;
//    }
//
//
//
//    public User(){}

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }


}
