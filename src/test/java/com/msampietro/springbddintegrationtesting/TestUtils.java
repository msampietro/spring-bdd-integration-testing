package com.msampietro.springbddintegrationtesting;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
public final class TestUtils {

    public static final String AUTHORIZATION_BEARER_PREFIX = "Bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String LOCATION_HEADER = "Location";
    public static final String APPLICATION_JSON_PATCH_CONTENT_TYPE = "application/json-patch+json";

    private TestUtils() {
    }

    public static void truncateDatabaseTables(ApplicationContext applicationContext,
                                              String... tableNames) throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        String resetSqlTemplate = "truncate table %s cascade";
        String resetSql = "";
        try (Connection dbConnection = dataSource.getConnection()) {
            for (String resetSqlArgument : tableNames) {
                try (Statement statement = dbConnection.createStatement()) {
                    resetSql = String.format(resetSqlTemplate, resetSqlArgument);
                    statement.execute(resetSql);
                } catch (Exception ex) {
                    log.debug("Truncate Error: {}, Query: {}", ex.getMessage(), resetSql);
                }
            }
        }
    }

    public static void resetSequences(ApplicationContext applicationContext,
                                      String... sequenceNames) throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        String resetSqlTemplate = "alter sequence %s restart with 1";
        String resetSql = "";
        try (Connection dbConnection = dataSource.getConnection()) {
            for (String resetSqlArgument : sequenceNames) {
                try (Statement statement = dbConnection.createStatement()) {
                    resetSql = String.format(resetSqlTemplate, resetSqlArgument);
                    statement.execute(resetSql);
                } catch (Exception ex) {
                    log.debug("Truncate Error: {}, Query: {}", ex.getMessage(), resetSql);
                }
            }
        }
    }

    public static String createAndReturnResultUsingLocationHeader(MockMvc mockMvc, String endpoint, String content, String token) throws Exception {
        MvcResult postResult = mockMvc.perform(post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, StringUtils.join(AUTHORIZATION_BEARER_PREFIX, token))
                .content(content))
                .andExpect(status().isCreated())
                .andReturn();
        String locationHeader = postResult.getResponse().getHeader(LOCATION_HEADER);
        return getAndReturnResult(mockMvc, locationHeader, token);
    }

    public static void createAndExpectStatusCode(MockMvc mockMvc, String endpoint, String content, String token, int statusCode) throws Exception {
        mockMvc.perform(post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, StringUtils.join(AUTHORIZATION_BEARER_PREFIX, token))
                .content(content))
                .andExpect(status().is(statusCode));
    }

    public static String patchObjectAndReturnResult(MockMvc mockMvc, String endpoint, String content, String token) throws Exception {
        MvcResult patchResult = mockMvc.perform(patch(endpoint)
                .contentType(MediaType.parseMediaType(APPLICATION_JSON_PATCH_CONTENT_TYPE))
                .header(AUTHORIZATION_HEADER, StringUtils.join(AUTHORIZATION_BEARER_PREFIX, token))
                .content(content))
                .andExpect(status().isOk())
                .andReturn();
        return patchResult.getResponse().getContentAsString();
    }

    public static String putObjectAndReturnResult(MockMvc mockMvc, String endpoint, String content, String token) throws Exception {
        MvcResult patchResult = mockMvc.perform(put(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, StringUtils.join(AUTHORIZATION_BEARER_PREFIX, token))
                .content(content))
                .andExpect(status().isOk())
                .andReturn();
        return patchResult.getResponse().getContentAsString();
    }

    public static String deleteAndReturnResult(MockMvc mockMvc, String endpoint, String token) throws Exception {
        MvcResult putResult = mockMvc.perform(delete(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, StringUtils.join(AUTHORIZATION_BEARER_PREFIX, token)))
                .andExpect(status().isOk())
                .andReturn();
        return putResult.getResponse().getContentAsString();
    }

    public static void deleteAndExpectStatusCode(MockMvc mockMvc, String endpoint, String token, int statusCode) throws Exception {
        mockMvc.perform(delete(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, StringUtils.join(AUTHORIZATION_BEARER_PREFIX, token)))
                .andExpect(status().is(statusCode));
    }

    public static void getAndExpectStatusCode(MockMvc mockMvc, String endpoint, String token, int statusCode) throws Exception {
        mockMvc.perform(get(endpoint)
                .header(AUTHORIZATION_HEADER, StringUtils.join(AUTHORIZATION_BEARER_PREFIX, token)))
                .andExpect(status().is(statusCode));
    }

    public static String getAndReturnResult(MockMvc mockMvc, String endpoint, String token) throws Exception {
        MvcResult getResult = mockMvc.perform(get(endpoint)
                .header(AUTHORIZATION_HEADER, StringUtils.join(AUTHORIZATION_BEARER_PREFIX, token)))
                .andExpect(status().isOk())
                .andReturn();
        return getResult.getResponse().getContentAsString();
    }

}