package com.marilinmoda.jym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marilinmoda.jym.dao.PersonalDao;
import com.marilinmoda.jym.models.PersonalListDTO;

@Service
public class PersonalService {
    @Autowired
    private PersonalDao pd;
    private Object resPersonal;

    public Object addOrupdatePersonal(PersonalListDTO per){
        this.resPersonal = pd.addOrupdatePersonal(per);
        
        // if (resPersonal instanceof String) {
        //     return resPersonal;
        // } else if (resPersonal != null) {
        //     return resPersonal;
        // }

        return resPersonal;
    }

    public Object deletePersonal(Integer per_id) {
        this.resPersonal = pd.deletePersonal(per_id);

        return resPersonal;
    }

    public Object getLisPersonal(Integer cia_id, String dni, String nombres){
        this.resPersonal = pd.personalList(cia_id,dni,nombres);
        
        return resPersonal;
    }
}
