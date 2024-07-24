package org.likelion.zagabi.Domain.Value.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "value")
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String value_title;

    @Column
    private Integer ranking;

    @Column
    private String category_name;

    @Column
    private Long categoryId;

    public void setFirstRank(int firstRank){
        ranking = firstRank;
    }

    // 랭킹을 바꾸는 메서드, setFirstRank와 똑같지만 헷갈릴까봐 하나 더 만들어놓음
    public void updateRanking(int changeRanking){
        ranking=changeRanking;
    }
    public void setCategory_name(String categoryName){
        category_name = categoryName;
    }


}
