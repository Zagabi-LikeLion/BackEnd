package org.likelion.zagabi.Domain.Category.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion.zagabi.Domain.Account.Entity.User;

@Entity
@Table(name = "value_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ValueCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(nullable = false)
    private String categoryName;

    public void updateCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
