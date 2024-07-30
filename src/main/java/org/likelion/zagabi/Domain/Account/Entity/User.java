package org.likelion.zagabi.Domain.Account.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.likelion.zagabi.Global.Common.BaseEntity;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickName;


    @Column(name="security_answer", nullable = false)
    private String securityAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "security_question_id")
    private SecurityQuestion securityQuestion;


    public void updatePassword(String pw) {
        password = pw;
    }
    public void updateNickname(String newNickname) {
        this.nickName = newNickname;
    }


}
