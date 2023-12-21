package br.edu.ifpb.pweb2.venus.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
@Id
// @GeneratedValue(strategy= GenerationType.IDENTITY)
//     private Integer id;

private String username;
private String password;
private Boolean enabled;

@OneToMany(mappedBy = "username", cascade = CascadeType.ALL)
@ToString.Exclude
List<Authority> authorities;

public User (String username, String password) {
    this.username = username;
    this.password = password;
}

public void addAuthority(String string) {
}
}
