package org.likelion.zagabi.Domain.ValueChangeLog.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.likelion.zagabi.Domain.Value.Entity.Value;
import org.likelion.zagabi.Global.Common.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "valuechangelog")
public class ValueChangeLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChangeType changeType;

    @Column(nullable = false)
    private String changeReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "value_id")
    private Value value;

    @Column
    private Integer Ranking;

    @Column
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private String valueTitle;


    public void setUser(User user){
        this.user = user;
    }

    public void setValue(Value whatValue) {
        value = whatValue;
    }

    public void setRanking(int whatRanking) {
        Ranking = whatRanking;
    }

    public void setCategoryName(String categoryName){
        this.categoryName = categoryName;
    }

    public void setValueTitle(String valueTitle) {
        this.valueTitle = valueTitle;
    }
}
