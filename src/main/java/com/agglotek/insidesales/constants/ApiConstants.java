package com.agglotek.insidesales.constants;

import jakarta.servlet.http.PushBuilder;

import javax.swing.plaf.PanelUI;

public class ApiConstants {


    //clients
    public static final String CLIENT_APIS = "/api/clients";

    public static final String ADD_CLIENTS = "/addClient";

    public static final String GET_CLIENTS = "/getClients";

    public static final String GET_FABRICATORS = "/getFabricators";

    public static final String ADD_CLIENT_CONVO = "/addClientConvo";

    public static final String GET_CLIENT_CONVO = "/getClientConvoData";

    public static final String EDIT_CLIENT = "/editClient";

    public static final String EDIT_CLIENT_CONVO = "editClientConvo";

    public static final String GET_CLIENT_BY_PROJECT_ID = "/getClient/{projectId}";

    public static final String ASSIGN_CLEINTS_TO_SALES = "/assignClients";

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

    public static final String CHANGE_PASSWORD = "/changePassword";

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

    public static final String GET_FILES_IN_PATH = "/getFilesAndPaths";

    //projects
    public static final String PROJECT_APIS = "/api/projects";

    public static final String PROJECT_DETAILS = "/projectInfo";

    public static final String UPDATE_PROJECT = "/updateProjectDetails";

    //Files
    public static final String FILE_UPLOAD = "/upload";

    public static final String FILE_DOWNLOAD = "/download";

    public static final String UPLOAD_FILES_IN_ZIP = "/uploadFilesZip";

    public static final String LIST_FILES = "/listFiles";


    public static final String DOWNLOAD_FROM_PATH = "/downloadFromPath";

    //work status entry
    public static final String WORK_STATUS_APIS = "/api/workStatus";

    public static final String ADD_WORK_STATUS = "/addWorkStatus";

    public static final String GET_WORK_STATUS = "/getWorkStatusForUser";

    public static final String EDIT_WORK_STATUS = "/editWorkStatus";

    //email send
    public static final String EMAIL_SENDING = "/email";

    public static final String SEND_EMAIL = "/sendEmail";

    public static final String MANAGER_DATA = "/api/manager";

    //Project Manager
    public static final String GET_PROJECTS_FOR_PROJECT_MANAGER = "/userProjects";

    public static final String UPDATE_PROJECT_DETAILS = "/updateProjectDetails";

    public static final String FILTER_BY_STATUS = "/projectFilterByStatus";


    public static final String PAYMENTS = "/api/payments";

    public static final String PAYMENT_CREATION = "/createPayment";

    public static final String INVOICE = "/api/invoice";

    public static final String CREATE_OR_UPDATE_INVOICE = "/createOrUpdateInvoice";

    public static final String FETCH_INVOICE_BY_USERID = "/invoiceByUserId";

    public static final String DELETE_INVOICE_BY_ID = "delete/{invoiceId}";

    public static final String ASSIGN_SALES_TARGETS = "/assignSalesTargets";

    public static final String GET_SALES_TARGETS = "/getSalesTargets/{year}";

    public static final String NOTIFY_UNASSIGNED_PROJECTS_TO_ADMIN = "/unassignedProjects";

    public static final String FILTER_WORK_STATUS_FOR_USER = "/filterWorkStatusByDates";

    public static final String GET_CLIENT_CONVO_BY_USERS = "/getClientConvoForUsers";
}
