package org.likelion.zagabi.Domain.ValueTrashCan.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.zagabi.Domain.Account.Entity.User;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "valuetrashcan")
public class ValueTrashCan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String value_title;


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String categoryName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public void setUser(User user){
        this.user = user;
    }
    public void setValue_title(String valueTitle){
        value_title = valueTitle;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
