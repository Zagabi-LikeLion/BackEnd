package org.likelion.zagabi.Domain.ValueChangeLog.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChangeType {
    Add_Category(0),Add_Value(1),Change_Rank(2), Delete_Value(3);

    int whatChangeType;
}