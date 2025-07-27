package com.agglotek.insidesales.constants;

public class ApiConstants {


    //clients
    public static final String CLIENT_APIS = "/api/clients";

    public static final String ADD_CLIENTS = "/addClient";

    public static final String GET_CLIENTS = "/getClients";

    public static final String ADD_CLIENT_CONVO = "/addClientConvo";

    public static final String GET_CLIENT_CONVO = "/getClientConvoData/{clientId}";

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

    public static final String FILTER_QUOTATIONS = "/filterByStatus";

    public static final String UPDATE_QUOTATION = "/update";

    //projects
    public static final String PROJECT_APIS = "/api/projects";

    public static final String PROJECT_DETAILS = "/projectInfo/{salesPersonId}";

    public static final String UPDATE_PO_NUM = "/update/ClientProjectNumber";

    //Files
    public static final String PROJECT_PO_UPLOAD = "/upload/PO";

    public static final String PROJECT_PO_DOWNLOAD = "/download/PO";

    //work status entry
    public static final String WORK_STATUS_APIS = "/api/workStatus";

    public static final String ADD_WORK_STATUS = "/addWorkStatus";

    public static final String GET_WORK_STATUS = "/getWorkStatus/{userId}";
}
