package com.neetry.library.user.repository.model;

import com.neetry.library.user.auth.model.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean verified;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "phone", column = @Column(name = "phone", nullable = false, unique = true)),
            @AttributeOverride(name = "email", column = @Column(name = "email", nullable = false, unique = true))
    })
    private ContactInfo contactInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "country", column = @Column(name = "country", nullable = false)),
            @AttributeOverride(name = "address", column = @Column(name = "address", nullable = false)),
            @AttributeOverride(name = "postalZip", column = @Column(name = "postalZip", nullable = false))
    })
    private AddressInfo addressInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "cardNumber", column = @Column(name = "cardNumber")),
            @AttributeOverride(name = "expDate", column = @Column(name = "expDate")),
            @AttributeOverride(name = "cvv", column = @Column(name = "cvv"))
    })
    private PaymentInfo paymentInfo;

    @Embeddable
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContactInfo {
        private Long phone;
        private String email;
    }

    @Embeddable
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddressInfo {
        private String country;
        private String address;
        private String postalZip;
    }

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentInfo {
        private String cardNumber;
        private LocalDate expDate;
        private Integer cvv;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return contactInfo.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
