package com.project.Permission.of.lead.util;

import java.security.SecureRandom;

public class PasswordGenerator {

    public static String generatePassword(){

        String letters="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz";
        String numbers="0123456789";

        SecureRandom random=new SecureRandom();

        StringBuilder password=new StringBuilder();

        for(int i=0; i<5; i++){

            password.append(letters.charAt(random.nextInt(letters.length())));

        }

        password.append("@");

        for(int i=0; i<5; i++){
            password.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return password.toString();
    }
}
