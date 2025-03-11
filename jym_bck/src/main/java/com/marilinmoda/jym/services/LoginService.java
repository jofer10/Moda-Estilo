package com.marilinmoda.jym.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marilinmoda.jym.dao.LoginDao;
import com.marilinmoda.jym.models.LoginValidacion;

@Service
public class LoginService {
    @Autowired
    private LoginDao<Object, Integer> login;

    public Object validateLogin(String email, String password) {
        Object loginResult = login.getLogin(email, password);

        if (loginResult instanceof String) {
            return loginResult;
        } else if (loginResult != null) {
            LoginValidacion logval = (LoginValidacion) loginResult;
            System.out.println("Password encriptado: " + logval.getUsu_key_access());
            
            if (logval.getUsu_key_access().isEmpty()) {
                return null;
            }

            System.out.println("Proceso de encriptación");
            System.out.println(BCrypt.checkpw(password, logval.getUsu_key_access()));

            if (BCrypt.checkpw(password, logval.getUsu_key_access())) {
                return loginResult;
            }
        }

        return null;
    }
}
