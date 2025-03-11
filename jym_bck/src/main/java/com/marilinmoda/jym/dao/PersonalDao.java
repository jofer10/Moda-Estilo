package com.marilinmoda.jym.dao;

import com.marilinmoda.jym.models.PersonalListDTO;

public interface PersonalDao {
    Object addOrupdatePersonal(PersonalListDTO per);
    Object deletePersonal(Integer per_id);
    Object personalList(Integer cia_id, String dni, String nombres);
}
