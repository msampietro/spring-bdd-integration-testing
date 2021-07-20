package com.msampietro.springbddintegrationtesting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBddIntegrationTestingApplication.class, AuthUtils.class})
@AutoConfigureMockMvc
@Getter
public abstract class AbstractStory extends JUnitStories {

    private static final String TYPE = "TYPE";
    private static final String OBJECT = "OBJECT";
    private static final String ARRAY = "ARRAY";
    private static final String WITH_KEY = "WITH_KEY";
    private static final String SAVED_AS = "SAVED_AS";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthUtils authUtils;

    private Map<String, JsonNode> savedObjects = new HashMap<>();

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(this.getClass()))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withCodeLocation(codeLocationFromClass(this.getClass()))
                        .withFormats(CONSOLE));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), this);
    }

    @Given("$element POSTED and SAVED as $objectName using role $role and permissions $permissions " +
            "and data $rawJsonData on endpoint $endpoint")
    public void elementPostedAndSavedUsingRoleAndPermissionsStep(@Named("objectName") String objectName,
                                                                 @Named("role") String role,
                                                                 @Named("permissions") String permissions,
                                                                 @Named("rawJsonData") String rawJsonData,
                                                                 @Named("endpoint") String endpoint) throws Exception {
        var jwt = this.getAuthUtils().obtainAccessToken(buildRolePermissionClaims(role.toLowerCase(), permissions));
        var jsonData = this.getObjectMapper().readTree(rawJsonData);
        var result = TestUtils.createAndReturnResultUsingLocationHeader(this.getMvc(), endpoint, jsonData.toString(), jwt);
        var jsonResult = this.getObjectMapper().readTree(result);
        this.getSavedObjects().put(objectName, jsonResult);
    }

    @When("$element POSTED and SAVED as $objectName using role $role and permissions $permissions and data $rawJsonData " +
            "on endpoint $endpoint including previously created objects: $objectsTable")
    public void elementPostedAndSavedUsingRolePermissionsAndPreviouslyCreatedObjectsStep(@Named("objectName") String objectName,
                                                                                         @Named("role") String role,
                                                                                         @Named("permissions") String permissions,
                                                                                         @Named("rawJsonData") String rawJsonData,
                                                                                         @Named("endpoint") String endpoint,
                                                                                         @Named("objectsTable") ExamplesTable objectsTable) throws Exception {
        var jwt = this.getAuthUtils().obtainAccessToken(buildRolePermissionClaims(role.toLowerCase(), permissions));
        var jsonData = (ObjectNode) this.getObjectMapper().readTree(rawJsonData);
        resolveElementTableObjectNodeAttachments(jsonData, objectsTable);
        var result = TestUtils.createAndReturnResultUsingLocationHeader(this.getMvc(), endpoint, jsonData.toString(), jwt);
        var jsonResult = this.getObjectMapper().readTree(result);
        this.getSavedObjects().put(objectName, jsonResult);
    }

    @When("$element PUT executed and SAVED as $objectName using role $role and permissions $permissions " +
            "and data $rawJsonData on endpoint $endpoint")
    public void elementPutAndSavedUsingRoleAndPermissionsStep(@Named("objectName") String objectName,
                                                              @Named("role") String role,
                                                              @Named("permissions") String permissions,
                                                              @Named("rawJsonData") String rawJsonData,
                                                              @Named("endpoint") String endpoint) throws Exception {
        var jwt = this.getAuthUtils().obtainAccessToken(buildRolePermissionClaims(role.toLowerCase(), permissions));
        var jsonData = this.getObjectMapper().readTree(rawJsonData);
        var result = TestUtils.putObjectAndReturnResult(this.getMvc(), endpoint, jsonData.toString(), jwt);
        var jsonResult = this.getObjectMapper().readTree(result);
        this.getSavedObjects().put(objectName, jsonResult);
    }

    @When("GET on endpoint $endpoint SAVED as $objectName using role $role and permissions $permissions")
    public void getAllAndSaveUsingRolePermissionsStep(@Named("objectName") String objectName,
                                                      @Named("endpoint") String endpoint,
                                                      @Named("role") String role,
                                                      @Named("permissions") String permissions) throws Exception {
        var jwt = this.getAuthUtils().obtainAccessToken(buildRolePermissionClaims(role.toLowerCase(), permissions));
        var result = TestUtils.getAndReturnResult(this.getMvc(), endpoint, jwt);
        var jsonResult = this.getObjectMapper().readTree(result);
        this.getSavedObjects().put(objectName, jsonResult);
    }

    @When("$element PATCHED and SAVED as $objectName using role $role and permissions $permissions and data $rawJsonData on endpoint $endpoint")
    public void getAllAndSaveUsingRolePermissionsStep(@Named("objectName") String objectName,
                                                      @Named("endpoint") String endpoint,
                                                      @Named("role") String role,
                                                      @Named("permissions") String permissions,
                                                      @Named("rawJsonData") String rawJsonData) throws Exception {
        var jwt = this.getAuthUtils().obtainAccessToken(buildRolePermissionClaims(role.toLowerCase(), permissions));
        var jsonData = (ArrayNode) this.getObjectMapper().readTree(rawJsonData);
        var result = TestUtils.patchObjectAndReturnResult(this.getMvc(), endpoint, jsonData.toString(), jwt);
        var jsonResult = this.getObjectMapper().readTree(result);
        this.getSavedObjects().put(objectName, jsonResult);
    }

    @When("DELETE $element using role $role and permissions $permissions on endpoint $endpoint should return the following values: $rawJsonData")
    public void deleteUsingRolePermissionsStep(@Named("endpoint") String endpoint,
                                               @Named("role") String role,
                                               @Named("permissions") String permissions,
                                               @Named("rawJsonData") String rawJsonData) throws Exception {
        var jwt = this.getAuthUtils().obtainAccessToken(buildRolePermissionClaims(role.toLowerCase(), permissions));
        var result = TestUtils.deleteAndReturnResult(this.getMvc(), endpoint, jwt);
        var jsonResult = this.getObjectMapper().readTree(result);
        var jsonData = this.getObjectMapper().readTree(rawJsonData);
        assertThat(jsonData).isEqualTo(jsonResult);
    }

    @Then("SAVED object as $objectName should contain the following values: $rawJsonData")
    public void thenAssertSavedObjectStep(@Named("objectName") String objectName,
                                          @Named("rawJsonData") String rawJsonData) throws JsonProcessingException {

        var jsonObject = this.getObjectMapper().readTree(rawJsonData);
        assertThat(jsonObject).isEqualTo(this.getSavedObjects().get(objectName));
    }

    @Then("GET on endpoint $endpoint using role $role and permissions $permissions should contain the following values: $rawJsonData")
    public void getAndAssertUsingRolePermissionsStep(@Named("endpoint") String endpoint,
                                                     @Named("role") String role,
                                                     @Named("permissions") String permissions,
                                                     @Named("rawJsonData") String rawJsonData) throws Exception {
        var jwt = this.getAuthUtils().obtainAccessToken(buildRolePermissionClaims(role.toLowerCase(), permissions));
        var jsonObject = this.getObjectMapper().readTree(rawJsonData);
        var result = TestUtils.getAndReturnResult(this.getMvc(), endpoint, jwt);
        var jsonResult = this.getObjectMapper().readTree(result);
        assertThat(jsonObject).isEqualTo(jsonResult);
    }

    @Then("GET on endpoint $endpoint using role $role and permissions $permissions should respond $statusCode")
    public void getUsingRolePermissionsExceptionExpectedStep(@Named("endpoint") String endpoint,
                                                             @Named("role") String role,
                                                             @Named("permissions") String permissions,
                                                             @Named("statusCode") int statusCode) throws Exception {
        var jwt = this.getAuthUtils().obtainAccessToken(buildRolePermissionClaims(role.toLowerCase(), permissions));
        TestUtils.getAndExpectStatusCode(this.getMvc(), endpoint, jwt, statusCode);
    }

    @Then("POST on endpoint $endpoint using role $role and permissions $permissions and data $rawJsonData should respond $statusCode")
    public void postUsingRolePermissionsExceptionExpectedStep(@Named("endpoint") String endpoint,
                                                              @Named("role") String role,
                                                              @Named("permissions") String permissions,
                                                              @Named("rawJsonData") String rawJsonData,
                                                              @Named("statusCode") int statusCode) throws Exception {
        var jwt = this.getAuthUtils().obtainAccessToken(buildRolePermissionClaims(role.toLowerCase(), permissions));
        var jsonObject = this.getObjectMapper().readTree(rawJsonData);
        TestUtils.createAndExpectStatusCode(this.getMvc(), endpoint, jsonObject.toString(), jwt, statusCode);
    }

    @Then("DELETE on endpoint $endpoint using role $role and permissions $permissions should respond $statusCode")
    public void deleteUsingRolePermissionsStep(@Named("endpoint") String endpoint,
                                               @Named("role") String role,
                                               @Named("permissions") String permissions,
                                               @Named("status") int statusCode) throws Exception {
        var jwt = this.getAuthUtils().obtainAccessToken(buildRolePermissionClaims(role.toLowerCase(), permissions));
        TestUtils.deleteAndExpectStatusCode(this.getMvc(), endpoint, jwt, statusCode);
    }

    private Map<String, String> buildRolePermissionClaims(String role, String permissions) {
        return Map.of("role", role,
                "scope", permissions,
                "username", "test_username");
    }

    private void resolveElementTableObjectNodeAttachments(ObjectNode objectNode, ExamplesTable examplesTable) {
        examplesTable.getRows().forEach(r -> {
            if (r.get(TYPE).equals(OBJECT)) {
                objectNode.set(r.get(WITH_KEY), this.getSavedObjects().get(r.get(SAVED_AS)));
            }
            if (r.get(TYPE).equals(ARRAY)) {
                ArrayNode arrayNode = this.getObjectMapper().createArrayNode();
                arrayNode.add(this.getSavedObjects().get(r.get(SAVED_AS)));
                objectNode.set(r.get(WITH_KEY), arrayNode);
            }
        });
    }

    public void truncateTables(Collection<String> tableNames) throws SQLException {
        TestUtils.truncateDatabaseTables(applicationContext, tableNames.toArray(String[]::new));
    }

    public void truncateSequences(Collection<String> sequenceNames) throws SQLException {
        TestUtils.resetSequences(applicationContext, sequenceNames.toArray(String[]::new));
    }

    public void resetSavedObjects() {
        savedObjects = new HashMap<>();
    }


}
