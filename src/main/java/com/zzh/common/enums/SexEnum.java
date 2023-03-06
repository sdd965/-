package com.zzh.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexEnum {

    MAN("MAN","男"),
    WOMAN("WOMAN","女");

    private final String code;
    private final String name;

    public static String getName(String sex) {
        SexEnum [] enums = values();
        for (SexEnum anEnum : enums) {
            if(anEnum.toString().equals(sex))
                return anEnum.getName();
        }
        return null;
    }

    public static String getCode(String name) {
        SexEnum [] enums = values();
        for (SexEnum anEnum : enums) {
            if(anEnum.toString().equals(name))
            {
                return anEnum.getCode();
            }
        }
        return null;
    }
}
