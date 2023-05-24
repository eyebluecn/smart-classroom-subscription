package com.smart.classroom.subscription.utility.enums;

/**
 * @author fusu
 * @date 2023-04-14
 */

import lombok.Getter;

public enum SortDirection {
    ASC("升序"),
    DESC("降序");;

    @Getter
    private final String description;

    SortDirection(String description) {
        this.description = description;
    }


    public static String toString(SortDirection orderStatus) {
        if (orderStatus == null) {
            return null;
        }
        return orderStatus.name();
    }

    public static SortDirection toEnum(String s) {
        if (s == null) {
            return null;
        }
        return SortDirection.valueOf(s);
    }

}