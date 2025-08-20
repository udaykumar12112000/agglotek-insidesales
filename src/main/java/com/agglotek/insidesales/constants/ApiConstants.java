package com.agglotek.insidesales.constants;

public class ApiConstants {


    //clients
    public static final String CLIENT_APIS = "/api/clients";

    public static final String ADD_CLIENTS = "/addClient";

    public static final String GET_CLIENTS = "/getClients";

    public static final String ADD_CLIENT_CONVO = "/addClientConvo";

    public static final String GET_CLIENT_CONVO = "/getClientConvoData";

    //users
    public static final String USER_APIS = "/api/users";

    public static final String CREATE_USER = "/createUser";

    public static final String GET_USER = "/getUser";

    public static final String EDIT_USER = "/editUser";

    public static final String USER_LOGIN = "/login";

    public static final String GET_USER_SUMMARY = "/user_summary";

    public static final String GET_USER_NOTIFICATIONS = "/user_notifications";

    public static final String UPDATE_BID_STATUS_FROM_CLIENT = "/update_bid_status";

    public static final String GET_USER_REPORTEES = "/reportees";

    //roles
    public static final String ROLES_APIS = "/api/roles";

    public static final String ADD_ROLE = "/addRole";

    public static final String GET_ALL_ROLES = "/getAllRoles";

    public static final String EDIT_ROLE = "/editRole";

    public static final String DELETE_ROLE = "/deleteRole/{roleId}";

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

    //email send
    public static final String EMAIL_SENDING = "/email";

    public static final String SEND_EMAIL = "/sendEmail";
}
