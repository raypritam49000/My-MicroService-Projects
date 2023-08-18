package com.common.security.utility;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean matchPassword(String password, String passwordHash) {
        return BCrypt.checkpw(password, passwordHash);
    }
}
