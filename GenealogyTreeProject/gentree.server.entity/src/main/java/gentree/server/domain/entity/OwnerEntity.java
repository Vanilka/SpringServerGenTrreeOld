package gentree.server.domain.entity;

import gentree.common.configuration.enums.RoleEnum;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
@Entity
@Table(name = "owner")
@Setter
public class OwnerEntity implements Serializable {

    private static final long serialVersionUID = -4043965269781743087L;

    private Long version;
    private Long id;
    private String login;
    private String password;
    private RoleEnum role;
    private List<FamilyEntity> familyList = new ArrayList<>();


    public OwnerEntity() {
    }

    public OwnerEntity(String login, String password, RoleEnum role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
    /*
        GETTERS AND SETTERS
     */

    @Version
    public Long getVersion() {
        return version;
    }

    @Id
    @GeneratedValue(generator = "InvSeqOwner")
    @SequenceGenerator(name = "InvSeqOwner", sequenceName = "INV_SEQ_OWNER", allocationSize = 5)
    public Long getId() {
        return id;
    }


    @Column(nullable = false, unique = true)
    public String getLogin() {
        return login;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public RoleEnum getRole() {
        return role;
    }


    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<FamilyEntity> getFamilyList() {
        return familyList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OwnerEntity{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
