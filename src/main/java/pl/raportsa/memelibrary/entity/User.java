/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.raportsa.memelibrary.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.raportsa.memelibrary.model.enums.Role;

import javax.persistence.*;

/**
 * @author pojdana
 */
@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GenericGenerator(name = "userIdGen", strategy = "increment")
    @GeneratedValue(generator = "userIdGen")
    private Long id;
    @Column(name = "USERNAME", unique = true)
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ACTIVE")
    private int active;
    @Column(name = "ROLES")
    @Enumerated(EnumType.STRING)
    private Role roles;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.active = 1;
        this.roles = Role.ROLE_USER;
    }

    public User(String username, String password, int active, Role roles) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }
}