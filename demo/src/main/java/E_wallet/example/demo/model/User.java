package E_wallet.example.demo.model;

import E_wallet.example.demo.enums.UserIdentificationType;
import E_wallet.example.demo.enums.UserStatus;
import E_wallet.example.demo.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int id;

    @Column(length=50)
    String name;

    @Column(unique=true,  length=50 )
    String email;

    @Column(unique=true, nullable=false, length=50)
    String phoneNo;

    String password;

    String authorities;

    @Enumerated(value=EnumType.STRING)
    UserIdentificationType userIdentificationType;


    @Enumerated(value=EnumType.STRING)
    UserType userType;

    @Enumerated(value=EnumType.STRING)
    UserStatus userStatus;




    String userIdentificationValue;

    @CreationTimestamp
    Date createdOn;

    @UpdateTimestamp
    Date updateOn;


    @Override
    public String getUsername() {
        return phoneNo;
    }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities (){
            return Arrays.stream(authorities.split(","))
                    .map(authority -> new SimpleGrantedAuthority(authority))
                    .collect(Collectors.toList());
        }

}
