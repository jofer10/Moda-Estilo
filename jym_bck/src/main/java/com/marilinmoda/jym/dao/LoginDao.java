package com.marilinmoda.jym.dao;

public interface LoginDao<T, ID> {
    // List<T> getList();

    // void eliminar(ID id);

    // void registrar(T obj);

    T getLogin(String email, String pass);
}
