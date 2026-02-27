package com.example.Library.Resource.Checkout.System.model;

import com.example.Library.Resource.Checkout.System.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import tools.jackson.databind.node.StringNode;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}
