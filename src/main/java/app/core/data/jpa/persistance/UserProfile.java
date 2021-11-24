package app.core.data.jpa.persistance;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile extends Entity {

    private String fullName;

    @Column(nullable = false, unique = true)
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfile", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<ItemOwnership> ownerships = new ArrayList<>();

    public UserProfile(String fullName, String phone) {
        this.fullName = fullName;
        this.phone = phone;
    }
}
