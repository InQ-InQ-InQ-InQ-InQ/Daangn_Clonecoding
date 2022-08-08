package team1.Daangn_Clonecoding.domain.member;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String town;

    public Address(String city, String town) {
        this.city = city;
        this.town = town;
    }
}
