package org.likelion.zagabi.Domain.Category.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Value.Entity.Value;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String categoryName;

    public void updateCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @OneToMany(mappedBy = "valueCategory", cascade = CascadeType.ALL)
    private List<Value> values;

}
