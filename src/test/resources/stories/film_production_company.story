Feature: Film Production Company API Services

Scenario: 1 film production company create operation
Meta: @id film_production_company-scenario1
Given film production company POSTED and SAVED as filmProductionCompany using role admin and permissions create:film_production_companies,read:film_production_companies
and data {
           "name": "Test Film Production Company"
         }
on endpoint /film-production-companies
Then SAVED object as filmProductionCompany should contain the following values: {
                                                                                  "name": "Test Film Production Company",
                                                                                  "id": 1,
                                                                                  "_links": {
                                                                                    "self": {
                                                                                      "href": "http://localhost/film-production-companies/1"
                                                                                    }
                                                                                  },
                                                                                  "_embedded": {
                                                                                    "filmCrewMembers": []
                                                                                  }
                                                                                }

Scenario: 2 film production company get-one operation
GivenStories: stories/film_production_company.story#{id:film_production_company-scenario1}
When GET on endpoint /film-production-companies/1 SAVED as filmProductionCompanyGetOneResult using role admin and permissions read:film_production_companies
Then SAVED object as filmProductionCompanyGetOneResult should contain the following values: {
                                                                                              "name": "Test Film Production Company",
                                                                                              "id": 1,
                                                                                              "_links": {
                                                                                                "self": {
                                                                                                  "href": "http://localhost/film-production-companies/1"
                                                                                                }
                                                                                              },
                                                                                              "_embedded": {
                                                                                                "filmCrewMembers": []
                                                                                              }
                                                                                            }

Scenario: 3 film production company get-all operation
GivenStories: stories/film_production_company.story#{id:film_production_company-scenario1}
When GET on endpoint /film-production-companies?page=1&size=35 SAVED as filmProductionCompanyGetAllResult using role admin and permissions read:film_production_companies
Then SAVED object as filmProductionCompanyGetAllResult should contain the following values: {
                                                                                              "_links": {
                                                                                                "first": {
                                                                                                  "href": "http://localhost/film-production-companies?page=1&size=35"
                                                                                                },
                                                                                                "last": {
                                                                                                  "href": "http://localhost/film-production-companies?page=1&size=35"
                                                                                                },
                                                                                                "self": {
                                                                                                  "href": "http://localhost/film-production-companies?page=0&size=35"
                                                                                                }
                                                                                              },
                                                                                              "totalElements": 1,
                                                                                              "totalPages": 1,
                                                                                              "page": null,
                                                                                              "_embedded": {
                                                                                                "content": [
                                                                                                  {
                                                                                                    "name": "Test Film Production Company",
                                                                                                    "id": 1,
                                                                                                    "_links": {
                                                                                                      "self": {
                                                                                                        "href": "http://localhost/film-production-companies/1"
                                                                                                      }
                                                                                                    },
                                                                                                    "_embedded": {
                                                                                                      "filmCrewMembers": []
                                                                                                    }
                                                                                                  }
                                                                                                ]
                                                                                              }
                                                                                            }

Scenario: 5 film production company patch operation
GivenStories: stories/film_production_company.story#{id:film_production_company-scenario1}
When film production company PATCHED and SAVED as patchResult using role admin and permissions update:film_production_companies
and data [
           {
             "op": "replace",
             "path": "/name",
             "value": "New Film Production Company Name"
           }
         ]
on endpoint /film-production-companies/1
Then SAVED object as patchResult should contain the following values: {
                                                                        "name": "New Film Production Company Name",
                                                                        "id": 1,
                                                                        "_links": {
                                                                          "self": {
                                                                            "href": "http://localhost/film-production-companies/1"
                                                                          }
                                                                        },
                                                                        "_embedded": {
                                                                          "filmCrewMembers": []
                                                                        }
                                                                      }

Scenario: 5 - film production company add crew member operation
Meta: @id film_production_company-scenario5
GivenStories: stories/film_production_company.story#{id:film_production_company-scenario1}
When film crew member PUT executed and SAVED as filmCrewMemberResult using role admin and permissions update:film_production_companies
and data {
           "name": "Test Name",
           "lastName": "Test Last Name",
           "filmCrewJob": { "id": 2 }
         }
on endpoint /film-production-companies/1/film-crew-members
Then SAVED object as filmCrewMemberResult should contain the following values: {
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

Scenario: 6 - film production company remove and get-all crew members operations
GivenStories: stories/film_production_company.story#{id:film_production_company-scenario5}
When DELETE film crew member using role admin and permissions delete:film_production_companies
on endpoint /film-production-companies/1/film-crew-members/1 should return the following values: {
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
Then GET on endpoint /film-production-companies/1/film-crew-members?page=1&size=35 using role admin
and permissions read:film_production_companies should contain the following values: {
                                                                                      "_links": {
                                                                                        "first": {
                                                                                          "href": "http://localhost/film-production-companies/1/film-crew-members?page=1&size=35"
                                                                                        },
                                                                                        "last": {
                                                                                          "href": "http://localhost/film-production-companies/1/film-crew-members?page=1&size=35"
                                                                                        },
                                                                                        "self": {
                                                                                          "href": "http://localhost/film-production-companies/1/film-crew-members?page=0&size=35"
                                                                                        }
                                                                                      },
                                                                                      "totalElements": 0,
                                                                                      "totalPages": 0,
                                                                                      "page": null,
                                                                                      "_embedded": {
                                                                                        "content": []
                                                                                      }
                                                                                    }