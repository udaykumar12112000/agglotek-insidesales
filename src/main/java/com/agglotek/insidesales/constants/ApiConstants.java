package com.agglotek.insidesales.constants;

import org.springframework.core.StandardReflectionParameterNameDiscoverer;

import java.security.PublicKey;

public class ApiConstants {


    //clients
    public static final String CLIENT_APIS = "/api/clients";

    public static final String ADD_CLIENTS = "/addClient";

    public static final String GET_CLIENTS = "/getClients";

    //users
    public static final String USER_APIS = "/api/users";

    public static final String CREATE_USER = "/createUser";

    public static final String GET_USER = "/getUser";

    public static final String EDIT_USER = "/editUser";

    public static final String USER_LOGIN = "/login";

    //roles
    public static final String ROLES_APIS = "/api/roles";

    public static final String ADD_ROLE = "/addRole";

    public static final String GET_ALL_ROLES = "/getAllRoles";

    //quotations
    public static final String QUOTATION_APIS = "/api/quotations";

    public static final String ADD_QUOTATION = "/addQuotation";
}
