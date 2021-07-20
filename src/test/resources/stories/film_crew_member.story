Feature: Film Crew Member Reduced API Services (Related to Film Production Company)

Scenario: 1 film crew member get-one operation
GivenStories: stories/film_production_company.story#{id:film_production_company-scenario5}
Then GET on endpoint /film-crew-members/1 using role admin
and permissions read:film_crew_members should contain the following values: {
                                                                              "name": "Test Name",
                                                                              "id": 1,
                                                                              "filmCrewJob": {
                                                                                "name": "Producer",
                                                                                "id": 2
                                                                              },
                                                                              "lastName": "Test Last Name",
                                                                              "_links": {
                                                                                "self": {
                                                                                  "href": "http://localhost/film-crew-members/1"
                                                                                }
                                                                              }
                                                                            }

Scenario: 2 film crew member patch operation
GivenStories: stories/film_production_company.story#{id:film_production_company-scenario5}
When film crew member PATCHED and SAVED as patchResult using role admin and permissions update:film_crew_members
and data [
           {
             "op": "replace",
             "path": "/name",
             "value": "New Crew Member Name"
           },
           {
            "op": "replace",
            "path": "/lastName",
            "value": "New Crew Member LastName"
           },
           {
           "op": "replace",
           "path": "/filmCrewJob",
           "value": { "id": 3 }
           }
         ]
on endpoint /film-crew-members/1
Then SAVED object as patchResult should contain the following values: {
                                                                        "filmCrewJob": {
                                                                          "name": "Executive Producer",
                                                                          "id": 3
                                                                        },
                                                                        "lastName": "New Crew Member LastName",
                                                                        "name": "New Crew Member Name",
                                                                        "id": 1,
                                                                        "_links": {
                                                                          "self": {
                                                                            "href": "http://localhost/film-crew-members/1"
                                                                          }
                                                                        }
                                                                      }

Scenario: 3 film crew member create operation disabled
GivenStories: stories/film_production_company.story#{id:film_production_company-scenario5}
Then POST on endpoint /film-crew-members using role admin and permissions create:film_crew_members
and data {
            "name": "Test Name",
            "lastName": "Test Last Name",
            "filmCrewJob": { "id": 2 }
         }
should respond 405

Scenario: 4 film crew member delete operation disabled
GivenStories: stories/film_production_company.story#{id:film_production_company-scenario5}
Then DELETE on endpoint /film-crew-members/1 using role admin and permissions delete:film_crew_members should respond 405

Scenario: 5 film crew member get-all operation disabled
GivenStories: stories/film_production_company.story#{id:film_production_company-scenario5}
Then GET on endpoint /film-crew-members?page=1&size=35 using role admin and permissions read:film_crew_members should respond 405