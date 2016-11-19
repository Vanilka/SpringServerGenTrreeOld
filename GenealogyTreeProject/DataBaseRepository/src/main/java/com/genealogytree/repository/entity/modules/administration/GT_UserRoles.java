package com.genealogytree.repository.entity.modules.administration;

import javax.persistence.*;

@Entity
@Table(name = "USER_ROLE")
public class GT_UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "name")
    private String roleName;
}
