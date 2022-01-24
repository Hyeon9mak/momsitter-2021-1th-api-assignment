package com.momsitter.assignment.domain;

import com.momsitter.assignment.exception.InvalidGenderException;

public enum Gender {
    남,
    여;

    public static Gender findByName(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            throw new InvalidGenderException(String.format("%s 이름의 성별은 존재하지 않습니다.", name));
        }
    }
}
