package com.kbalazsworks.stackjudge.state.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
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
    @Getter
    private String idsUserId;
}
