package com.kbalazsworks.stackjudge.state.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Getter(onMethod = @__(@JsonIgnore))
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long    id;
    @Getter
    private Boolean isEmailUser;
    @Getter
    private Boolean isFacebookUser;
    @Getter
    private String  profilePictureUrl;
    @Getter
    private String  username;
    @ToString.Exclude
    private String  password;
    @ToString.Exclude
    private String  facebookAccessToken;
    @Getter
    private Long    facebookId;
}
