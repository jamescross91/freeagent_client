package com.karlnosworthy.freeagent;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.karlnosworthy.freeagent.model.*;
import com.karlnosworthy.freeagent.model.wrapper.*;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Response;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * A simple, retrofit based client to allow CRUD access to a FreeAgentService account. Utilises OAuth2 to provide
 * suitable authentication before an instance can be supplied for use.
 *
 * @author Karl Nosworthy
 */
public class FreeAgentClient {

    public static final String SANDBOX_URL = "https://api.sandbox.freeagent.com";
    public static final String LIVE_URL = "https://api.freeagent.com/v2";

    private static final String LOCALHOST = "127.0.0.1"; // NOPMD

    private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"), ".store/oauth2");

    private SimpleDateFormat dateFormat;
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static FreeAgentService freeAgentServiceInstance;
    private Credential credential;



    /**
     * Authorises, initialises and returns a new FreeAgentClient instance.
     *
     * @param identifier The identifier to use for OAuth authentication and FreeAgentService recognition.
     * @param secret The secret to use for OAuth authentication.
     * @return An authenticated instance which is ready to use or null
     * @throws IOException Thrown a problem is encountered during the OAuth process.
     */
    public static FreeAgentClient authorise(String identifier, String secret) throws IOException {
        return authorise(identifier, secret, LIVE_URL, false);
    }

    /**
     * Authorises, initialises and returns a new FreeAgentClient instance.
     *
     * @param identifier The identifier to use for OAuth authentication and FreeAgentService recognition.
     * @param secret The secret to use for OAuth authentication.
     * @param apiURL The URL of the API to target {@link #LIVE_URL}, {@link #SANDBOX_URL} etc.
     * @param loggingEnabled Should logging be enabled.
     * @return An authenticated instance which is ready to use or null
     * @throws IOException Thrown a problem is encountered during the OAuth process.
     */
    public static FreeAgentClient authorise(String identifier, String secret, String apiURL, boolean loggingEnabled) throws IOException {

        if (DATA_STORE_FACTORY == null) {
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        }

        AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken
                .authorizationHeaderAccessMethod(),
                HTTP_TRANSPORT,
                JSON_FACTORY,
                new GenericUrl(apiURL + "/token_endpoint"),
                new ClientParametersAuthentication(identifier, secret),
                identifier,
                apiURL + "/approve_app")
                .setDataStoreFactory(DATA_STORE_FACTORY).build();

        // authorize
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().build();


        final Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        if (credential != null) {
            return new FreeAgentClient(credential, apiURL, loggingEnabled);
        } else {
            return null;
        }
    }

    /**
     * Returns the basic information about the company contained within FreeAgent
     * for the authorised account.
     *
     * @return A populated FreeAgentCompany instance or null.
     */
    public FreeAgentCompany getCompany() {
        FreeAgentCompanyWrapper companyWrapper = freeAgentServiceInstance.getCompany();

        if (companyWrapper != null) {
            return companyWrapper.getCompany();
        }
        return null;
    }

    /**
     * Returns a list of all the known contacts contained within FreeAgent
     * for the authorised account.
     *
     * @return A list of Contact instances.
     */
    public List<FreeAgentContact> getContacts() {
        FreeAgentContactWrapper contactsWrapper = freeAgentServiceInstance.getContacts();
        if (contactsWrapper != null) {
            return contactsWrapper.getContacts();
        }
        return null;
    }

    /**
     * Returns a list of all the known contacts contained within FreeAgentService
     * for the authorised account.
     *
     * @param viewType The view type {@link com.karlnosworthy.freeagent.FreeAgentClient.ContactViewType} filter to apply to the contacts
     * @return A list of Contact instances.
     */
    public List<FreeAgentContact> getContacts(ContactViewType viewType) {
        FreeAgentContactWrapper contactsWrapper = freeAgentServiceInstance.getContacts(viewType.identifier);
        if (contactsWrapper != null) {
            return contactsWrapper.getContacts();
        }
        return null;
    }

    /**
     * Returns a list of all the known contacts contained within FreeAgentService
     * for the authorised account.
     *
     * @param viewType The view type {@link com.karlnosworthy.freeagent.FreeAgentClient.ContactViewType} filter to apply to the contacts.
     * @param sortOrderType The sort order {@link com.karlnosworthy.freeagent.FreeAgentClient.ContactSortOrderType} to apply to the contacts.
     * @return A list of Contact instances.
     */
    public List<FreeAgentContact> getContacts(ContactViewType viewType, ContactSortOrderType sortOrderType) {
        FreeAgentContactWrapper contactsWrapper = freeAgentServiceInstance.getContacts(viewType.identifier, sortOrderType.identifier);
        if (contactsWrapper != null) {
            return contactsWrapper.getContacts();
        }
        return null;
    }

    /**
     * Retrieves the contact that matches the specified id.
     *
     * @param contactId The id to match.
     * @return A Contact instance or null if the id supplied was invalid or could not be matched.
     */
    public FreeAgentContact getContact(String contactId) {
        if (contactId != null && !contactId.isEmpty()) {
            FreeAgentContactWrapper contactWrapper = freeAgentServiceInstance.getContact(contactId);
            if (contactWrapper != null) {
                return contactWrapper.getContact();
            }
        }
        return null;
    }

    /**
     * Attempts to create a new contact entry in the associated FreeAgent account.
     *
     * Will return null if the contact instance provided is null or cannot be saved into the account.
     *
     * @param contact The populated contact instance.
     * @return The updated contact instance or null.
     */
    public FreeAgentContact createContact(FreeAgentContact contact) {
        if (contact != null) {
            FreeAgentContactWrapper contactWrapper = freeAgentServiceInstance.createContact(new FreeAgentContactWrapper(contact));
            if (contactWrapper != null) {
                return contactWrapper.getContact();
            }
        }
        return null;
    }

    /**
     * Builds a new contact instance from the specified JSON string. The instance has not been
     * sent to FreeAgent.
     *
     * @param contactJSON A string containing contact information in FreeAgent friendly format.
     * @return A populated contact instance or null if the contactJSON is empty.
     * @throws JsonSyntaxException If the format does not match the FreeAgent V2 Contact format.
     */
    public FreeAgentContact buildContact(String contactJSON) throws JsonSyntaxException {
        if (contactJSON == null || contactJSON.isEmpty()) {
            return null;
        }
        return new GsonBuilder().create().fromJson(contactJSON, FreeAgentContact.class);
    }

    /**
     * Attempts to import a new contact into the associated FreeAgent account by deserialising the specified
     * JSON contact information and requesting a new contact be created.
     *
     * NOTE: The import (creation within FreeAgent) will only be actioned if no URL property is present or if the
     *       URL property is not populated. Otherwise null will be returned.
     *
     * @param contactJSON A string containing contact information in FreeAgent friendly format.
     * @return The newly populated contact instance that has been imported into FreeAgent or null.
     * @throws JsonSyntaxException If the format does not match the FreeAgent V2 Contact format.
     */
    public FreeAgentContact importContact(String contactJSON) throws JsonSyntaxException {
        if (contactJSON == null || contactJSON.isEmpty()) {
            return null;
        }

        FreeAgentContact contact = buildContact(contactJSON);

        if (contact != null && (contact.getUrl() == null || contact.getUrl().isEmpty())) {
            FreeAgentContactWrapper contactWrapper = freeAgentServiceInstance.createContact(new FreeAgentContactWrapper(contact));

            if (contactWrapper != null) {
                return contactWrapper.getContact();
            }
        }

        return null;
    }

    /**
     * Attempts to update the specified contact entry in the associated FreeAgent account.
     *
     * @param contact The populated contact instance.
     * @return True if the contact has been updated successfully, otherwise false.
     */
    public boolean updateContact(FreeAgentContact contact) {
        if (contact != null) {
            String contactId = extractIdentifier(contact.getUrl());

            if (contactId != null && !contactId.isEmpty()) {
                Response response = freeAgentServiceInstance.updateContact(new FreeAgentContactWrapper(contact), contactId);
                if (response.getStatus() == 200) {
                    contact.setUpdatedAt(dateFormat.format(new Date()));
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Attempts to delete the specified contact entry in the associated FreeAgent account.
     *
     * @param contact The populated contact instance.
     * @return True if the contact has been deleted successfully, otherwise false.
     */
    public boolean deleteContact(FreeAgentContact contact) {
        if (contact != null) {
            String contactId = extractIdentifier(contact.getUrl());

            if (contactId != null && !contactId.isEmpty()) {
                Response response = freeAgentServiceInstance.deleteContact(contactId);

                if (response.getStatus() == 200) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Retrieves the Contact associated with the specified project.
     *
     * @param project The populated project instance to retrieve the contact for.
     * @return A populated FreeAgentContact instance or null if the contact could not be found.
     */
    public FreeAgentContact getContactForProject(FreeAgentProject project) {
        if (project != null) {
            String contactId = extractIdentifier(project.getContact());

            if (contactId != null && !contactId.isEmpty()) {
                FreeAgentContactWrapper contactWrapper = freeAgentServiceInstance.getContact(contactId);

                if (contactWrapper != null) {
                    return contactWrapper.getContact();
                }
            }
        }
        return null;
    }

    /**
     * Returns a list of all the known projects contained within FreeAgent
     * for the authorised account.
     *
     * @return A list of Project instances.
     */
    public List<FreeAgentProject> getProjects() {
        FreeAgentProjectWrapper projectsWrapper = freeAgentServiceInstance.getProjects();

        if (projectsWrapper != null) {
            return projectsWrapper.getProjects();
        }
        return null;
    }

    /**
     * Returns a list of all the known projects contained within FreeAgent
     * for the authorised account.
     *
     * @param statusType The view type {@link com.karlnosworthy.freeagent.FreeAgentClient.ProjectStatusType} filter to apply to the projects.
     * @return A list of Project instances.
     */
    public List<FreeAgentProject> getProjects(ProjectStatusType statusType) {
        FreeAgentProjectWrapper projectsWrapper = freeAgentServiceInstance.getProjects(statusType.identifier);

        if (projectsWrapper != null) {
            return projectsWrapper.getProjects();
        }
        return null;
    }

    /**
     * Returns a list of projects that are associated with the specified contact.
     *
     * @param contact The contact instance to look up projects for.
     * @return A list of the appropriate projects or null.
     */
    public List<FreeAgentProject> getProjects(FreeAgentContact contact) {
        if (contact != null && contact.getUrl() != null && !contact.getUrl().isEmpty()) {

            FreeAgentProjectWrapper projectsWrapper = freeAgentServiceInstance.getProjectsForContact(contact.getUrl());
            if (projectsWrapper != null) {
                return projectsWrapper.getProjects();
            }
        }
        return null;
    }

    /**
     * Retrieves the project that matches the specified id.
     *
     * @param projectId The id to match.
     * @return A Project instance or null if the id supplied was invalid or could not be matched.
     */
    public FreeAgentProject getProject(String projectId) {
        if (projectId != null && !projectId.isEmpty()) {
            FreeAgentProjectWrapper projectWrapper = freeAgentServiceInstance.getProject(projectId);

            if (projectWrapper != null) {
                return projectWrapper.getProject();
            }
        }
        return null;
    }

    /**
     * Attempts to create a new project entry in the associated FreeAgent account.
     *
     * Will return null if the project instance provided is null or cannot be saved into the account.
     *
     * @param project The populated project instance.
     * @return The updated project instance or null.
     */
    public FreeAgentProject createProject(FreeAgentProject project) {
        if (project != null) {
            FreeAgentProjectWrapper projectWrapper = freeAgentServiceInstance.createProject(new FreeAgentProjectWrapper(project));
            if (projectWrapper != null) {
                return projectWrapper.getProject();
            }
        }
        return null;
    }

    /**
     * Builds a new project instance from the specified JSON string. The instance has not been
     * sent to FreeAgent.
     *
     * @param projectJSON A string containing project information in FreeAgent friendly format.
     * @return A populated project instance or null if the projectJSON is empty.
     * @throws JsonSyntaxException If the format does not match the FreeAgent V2 Project format.
     */
    public FreeAgentProject buildProject(String projectJSON) throws JsonSyntaxException {
        if (projectJSON == null || projectJSON.isEmpty()) {
            return null;
        }
        return new GsonBuilder().create().fromJson(projectJSON, FreeAgentProject.class);
    }

    /**
     * Attempts to import a new project into the associated FreeAgent account by deserialising the specified
     * JSON project information and requesting a new project be created.
     *
     * NOTE: The import (creation within FreeAgent) will only be actioned if no URL property is present or if the
     *       URL property is not populated. Otherwise null will be returned.
     *
     * @param projectJSON A string containing project information in FreeAgent friendly format.
     * @return The newly populated project instance that has been imported into FreeAgent or null.
     * @throws JsonSyntaxException If the format does not match the FreeAgent V2 Project format.
     */
    public FreeAgentProject importProject(String projectJSON) throws JsonSyntaxException {
        if (projectJSON == null || projectJSON.isEmpty()) {
            return null;
        }

        FreeAgentProject project = buildProject(projectJSON);

        if (project != null && (project.getUrl() == null || project.getUrl().isEmpty())) {
            FreeAgentProjectWrapper projectWrapper = freeAgentServiceInstance.createProject(new FreeAgentProjectWrapper(project));

            if (projectWrapper != null) {
                return projectWrapper.getProject();
            }
        }

        return null;
    }

    /**
     * Attempts to update the specified project entry in the associated FreeAgent account.
     *
     * @param project The populated project instance.
     * @return True if the project has been updated successfully, otherwise false.
     */
    public boolean updateProject(FreeAgentProject project) {
        if (project != null) {
            String projectId = extractIdentifier(project.getUrl());

            if (projectId != null && !projectId.isEmpty()) {
                Response response = freeAgentServiceInstance.updateProject(new FreeAgentProjectWrapper(project), projectId);
                if (response.getStatus() == 200) {
                    project.setUpdatedAt(dateFormat.format(new Date()));
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Attempts to delete the specified project entry in the associated FreeAgent account.
     *
     * @param project The populated project instance.
     * @return True if the project has been deleted successfully, otherwise false.
     */
    public boolean deleteProject(FreeAgentProject project) {
        if (project != null) {
            String projectId = extractIdentifier(project.getUrl());

            if (projectId != null && !projectId.isEmpty()) {
                Response response = freeAgentServiceInstance.deleteProject(projectId);

                if (response.getStatus() == 200) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Returns a list of the invoices contained within FreeAgent
     * for the authorised account.
     *
     * @return A list of FreeAgentInvoice instances.
     */
    public List<FreeAgentInvoice> getInvoices() {
        return getInvoices(false);
    }

    /**
     * Returns a list of the invoices contained within FreeAgent
     * for the authorised account.
     *
     * @param nestInvoiceItems Should the invoice items also be included with the invoice
     * @return A list of FreeAgentInvoice instances.
     */
    public List<FreeAgentInvoice> getInvoices(boolean nestInvoiceItems) {
        FreeAgentInvoiceWrapper invoicesWrapper = freeAgentServiceInstance.getInvoices(nestInvoiceItems);

        if (invoicesWrapper != null && invoicesWrapper.hasInvoices()) {
            return invoicesWrapper.getInvoices();
        } else {
            return null;
        }
    }

    /**
     * Retrieves the invoice that matches the specified id.
     *
     * @param invoiceId The id to match.
     * @return An Invoice instance or null if the id supplied was invalid or could not be matched.
     */
    public FreeAgentInvoice getInvoice(String invoiceId) {
        if (invoiceId != null && !invoiceId.isEmpty()) {
            FreeAgentInvoiceWrapper invoiceWrapper = freeAgentServiceInstance.getInvoice(invoiceId);
            if (invoiceWrapper != null) {
                return invoiceWrapper.getInvoice();
            }
        }
        return null;
    }

    /**
     * Attempts to create a new invoice entry in the associated FreeAgent account.
     *
     * Will return null if the invoice instance provided is null or cannot be saved into the account.
     *
     * @param invoice The populated invoice instance.
     * @return The updated invoice instance or null.
     */
    public FreeAgentInvoice createInvoice(FreeAgentInvoice invoice) {
        if (invoice != null) {
            FreeAgentInvoiceWrapper invoiceWrapper = freeAgentServiceInstance.createInvoice(new FreeAgentInvoiceWrapper(invoice));
            if (invoiceWrapper != null) {
                return invoiceWrapper.getInvoice();
            }
        }
        return null;
    }

    /**
     * Builds a new invoice instance from the specified JSON string. The instance has not been
     * sent to FreeAgent.
     *
     * @param invoiceJSON A string containing invoice information in FreeAgent friendly format.
     * @return A populated invoice instance or null if the invoiceJSON is empty.
     * @throws JsonSyntaxException If the format does not match the FreeAgent V2 Invoice format.
     */
    public FreeAgentInvoice buildInvoice(String invoiceJSON) throws JsonSyntaxException {
        if (invoiceJSON == null || invoiceJSON.isEmpty()) {
            return null;
        }
        return new GsonBuilder().create().fromJson(invoiceJSON, FreeAgentInvoice.class);
    }

    /**
     * Builds a list of new invoice instances from the specified JSON string. The instance has not been
     * sent to FreeAgent.
     *
     * @param invoicesJSON A string containing the collection of invoice information in FreeAgent friendly format.
     * @return A list of populated invoice instances or null if the invoicesJSON is empty.
     * @throws JsonSyntaxException If the format does not match the FreeAgent V2 Invoice format.
     */
    public List<FreeAgentInvoice> buildInvoices(String invoicesJSON) throws JsonSyntaxException {
        if (invoicesJSON == null || invoicesJSON.isEmpty()) {
            return null;
        }

        Type collectionType = new TypeToken<List<FreeAgentInvoice>>(){}.getType();
        return new GsonBuilder().create().fromJson(invoicesJSON, collectionType);
    }

    /**
     * Attempts to import a new invoice into the associated FreeAgent account by deserialising the specified
     * JSON invoice information and requesting a new invoice be created.
     *
     * NOTE: The import (creation within FreeAgent) will only be actioned if no URL property is present or if the
     *       URL property is not populated. Otherwise null will be returned.
     *
     * @param invoiceJSON A string containing invoice information in FreeAgent friendly format.
     * @return The newly populated invoice instance that has been imported into FreeAgent or null.
     * @throws JsonSyntaxException If the format does not match the FreeAgent V2 Invoice format.
     */
    public FreeAgentInvoice importInvoice(String invoiceJSON) throws JsonSyntaxException {
        if (invoiceJSON == null || invoiceJSON.isEmpty()) {
            return null;
        }

        FreeAgentInvoice invoice = buildInvoice(invoiceJSON);

        if (invoice != null && (invoice.getUrl() == null || invoice.getUrl().isEmpty())) {
            FreeAgentInvoiceWrapper invoiceWrapper = freeAgentServiceInstance.createInvoice(new FreeAgentInvoiceWrapper(invoice));

            if (invoiceWrapper != null) {
                return invoiceWrapper.getInvoice();
            }
        }

        return null;
    }

    /**
     * Attempts to update the specified invoice entry in the associated FreeAgent account.
     *
     * @param invoice The populated Invoice instance.
     * @return True if the invoice has been updated successfully, otherwise false.
     */
    public boolean updateInvoice(FreeAgentInvoice invoice) {
        if (invoice != null) {
            String invoiceId = extractIdentifier(invoice.getUrl());

            if (invoiceId != null && !invoiceId.isEmpty()) {
                Response response = freeAgentServiceInstance.updateInvoice(new FreeAgentInvoiceWrapper(invoice), invoiceId);
                if (response.getStatus() == 200) {
                    invoice.setUpdatedAt(dateFormat.format(new Date()));
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Attempts to delete the specified invoice entry in the associated FreeAgent account.
     *
     * @param invoice The populated invoice instance.
     * @return True if the invoice has been deleted successfully, otherwise false.
     */
    public boolean deleteInvoice(FreeAgentInvoice invoice) {
        if (invoice != null) {
            String invoiceId = extractIdentifier(invoice.getUrl());

            if (invoiceId != null && !invoiceId.isEmpty()) {
                Response response = freeAgentServiceInstance.deleteInvoice(invoiceId);

                if (response.getStatus() == 200) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Returns a list of all the known users contained within FreeAgent
     * for the authorised account.
     *
     * @return A list of User instances.
     */
    public List<FreeAgentUser> getUsers() {
        FreeAgentUserWrapper usersWrapper = freeAgentServiceInstance.getUsers();

        if (usersWrapper != null) {
            return usersWrapper.getUsers();
        }
        return null;
    }

    /**
     * Retrieves the users that matches the specified id.
     *
     * @param userId The id to match.
     * @return A User instance or null if the id supplied was invalid or could not be matched.
     */
    public FreeAgentUser getUser(String userId) {
        if (userId != null && !userId.isEmpty()) {
            FreeAgentUserWrapper userWrapper = freeAgentServiceInstance.getUser(userId);
            if (userWrapper != null) {
                return userWrapper.getUser();
            }
        }
        return null;
    }

    /**
     * Retrieves the information about the current user/personal profile contained within
     * the FreeAgent authorised account.
     *
     * @return The current user/personal profile or null if the profile could not be obtained.
     */
    public FreeAgentUser getPersonalProfile() {
        FreeAgentUserWrapper userWrapper = freeAgentServiceInstance.getCurrentUser();

        if (userWrapper != null) {
            return userWrapper.getUser();
        }
        return null;
    }

    /**
     * Attempts to create a new user entry in the associated FreeAgent account.
     *
     * Will return null if the user instance provided is null or cannot be saved into the account.
     *
     * @param user The populated user instance.
     * @return The updated user instance or null.
     */
    public FreeAgentUser createUser(FreeAgentUser user) {
        if (user != null) {
            FreeAgentUserWrapper userWrapper = freeAgentServiceInstance.createUser(new FreeAgentUserWrapper(user));
            if (userWrapper != null) {
                return userWrapper.getUser();
            }
        }
        return null;
    }

    /**
     * Attempts to update the specified user entry in the associated FreeAgent account.
     *
     * @param user The populated user instance.
     * @return True if the user has been updated successfully, otherwise false.
     */
    public boolean updateUser(FreeAgentUser user) {
        if (user != null) {
            String userId = extractIdentifier(user.getUrl());

            if (userId != null && !userId.isEmpty()) {
                Response response = freeAgentServiceInstance.updateUser(new FreeAgentUserWrapper(user), userId);
                if (response.getStatus() == 200) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Attempts to delete the specified user entry in the associated FreeAgent account.
     *
     * @param user The populated user instance.
     * @return True if the user has been deleted successfully, otherwise false.
     */
    public boolean deleteUser(FreeAgentUser user) {
        if (user != null) {
            String userId = extractIdentifier(user.getUrl());

            if (userId != null && !userId.isEmpty()) {
                Response response = freeAgentServiceInstance.deleteUser(userId);

                if (response.getStatus() == 200) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Builds a new user instance from the specified JSON string. The instance has not been
     * sent to FreeAgent.
     *
     * @param userJSON A string containing user information in FreeAgent friendly format.
     * @return A populated user instance or null if the userJSON is empty.
     * @throws JsonSyntaxException If the format does not match the FreeAgent V2 User format.
     */
    public FreeAgentUser buildUser(String userJSON) throws JsonSyntaxException {
        if (userJSON == null || userJSON.isEmpty()) {
            return null;
        }
        return new GsonBuilder().create().fromJson(userJSON, FreeAgentUser.class);
    }

    /**
     * Attempts to import a new user into the associated FreeAgent account by deserialising the specified
     * JSON user information and requesting a new user be created.
     *
     * NOTE: The import (creation within FreeAgent) will only be actioned if no URL property is present or if the
     *       URL property is not populated. Otherwise null will be returned.
     *
     * @param userJSON A string containing user information in FreeAgent friendly format.
     * @return The newly populated user instance that has been imported into FreeAgent or null.
     * @throws JsonSyntaxException If the format does not match the FreeAgent V2 User format.
     */
    public FreeAgentUser importUser(String userJSON) throws JsonSyntaxException {
        if (userJSON == null || userJSON.isEmpty()) {
            return null;
        }

        FreeAgentUser user = buildUser(userJSON);

        if (user != null && (user.getUrl() == null || user.getUrl().isEmpty())) {
            FreeAgentUserWrapper userWrapper = freeAgentServiceInstance.createUser(new FreeAgentUserWrapper(user));

            if (userWrapper != null) {
                return userWrapper.getUser();
            }
        }

        return null;
    }

    public List<FreeAgentExpense> getExpenses() {
        FreeAgentExpenseWrapper expenses = freeAgentServiceInstance.getExpenses();
        if(expenses != null) {
            return expenses.getExpenses();
        }
        return null;
    }

    public FreeAgentExpense createExpense(FreeAgentExpense expense) {
        if (expense != null) {
            FreeAgentExpenseWrapper expenseWrapper = freeAgentServiceInstance.createExpense(new FreeAgentExpenseWrapper(expense));
            if (expenseWrapper != null) {
                return expenseWrapper.getExpense();
            }
        }
        return null;
    }

    private FreeAgentClient(Credential oauthCredential, String apiURL, boolean loggingEnabled) {
        super();
        this.credential = oauthCredential;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(apiURL);

        if (loggingEnabled) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        builder.setRequestInterceptor(new RequestInterceptor() {

            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("Authorization", "Bearer " + credential.getAccessToken());
            }
        });

        RestAdapter restAdapter = builder.build();

        freeAgentServiceInstance = restAdapter.create(FreeAgentService.class);
    }

    public String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public String extractIdentifier(String url) {
        if (url != null && !url.isEmpty()) {
            return url.substring(1 + url.lastIndexOf("/"));
        }
        return "";
    }

    public enum ContactViewType {
        All("all"),
        Active("active"),
        Clients("clients"),
        Suppliers("suppliers"),
        ActiveProjects("active_projects"),
        CompletedProjects("completed_projects"),
        OpenClients("open_clients"),
        OpenSuppliers("open_suppliers"),
        Hidden("hidden");

        private String identifier;

        ContactViewType(String identifier) {
            this.identifier = identifier;
        }
    }

    public enum ContactSortOrderType {

        Name(""),
        CreatedAtAscending("created_at"),
        CreatedAtDescending("-created_at"),
        UpdatedAtAscending("updated_at"),
        UpdatedAtDescending("-updated_at");

        private String identifier;

        ContactSortOrderType(String identifier) {
            this.identifier = identifier;
        }
    }

    public enum ProjectStatusType {

        Active("active"),
        Completed("completed"),
        Cancelled("cancelled"),
        Hidden("hidden");

        private String identifier;

        ProjectStatusType(String identifier) {
            this.identifier = identifier;
        }
    }

    public enum UserPermissionType {
        NoAccess,
        Time,
        MyMoney,
        ContactsAndProjects,
        InvoicesEstimatesAndFiles,
        Bills,
        Banking,
        TaxAccountingAndUsers,
        Full
    }
}
