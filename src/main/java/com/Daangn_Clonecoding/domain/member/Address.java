package com.Daangn_Clonecoding.domain.member;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String city;

    private String county;

    private String district;

    public Address(String city, String county, String district) {
        this.city = city;
        this.county = county;
        this.district = district;
    }
}
