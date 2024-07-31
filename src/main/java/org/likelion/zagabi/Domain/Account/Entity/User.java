package org.likelion.zagabi.Domain.Account.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.likelion.zagabi.Domain.Category.Entity.ValueCategory;
import org.likelion.zagabi.Domain.Diary.Entity.Diary;
import org.likelion.zagabi.Domain.ValueChangeLog.Entity.ValueChangeLog;
import org.likelion.zagabi.Domain.ValueTrashCan.Entity.ValueTrashCan;
import org.likelion.zagabi.Global.Common.BaseEntity;

import java.util.List;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Diary> diaries;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ValueCategory> valueCategories;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ValueChangeLog> valueChangeLogs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ValueTrashCan> valueTrashCans;



    public void updatePassword(String pw) {
        password = pw;
    }
    public void updateNickname(String newNickname) {
        this.nickName = newNickname;
    }


}
