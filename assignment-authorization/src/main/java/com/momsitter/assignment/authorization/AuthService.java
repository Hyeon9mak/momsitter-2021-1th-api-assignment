package com.momsitter.assignment.authorization;

public interface AuthService {

    Object findAuthMemberByToken(String token);
}
