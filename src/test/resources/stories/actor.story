Feature: Actor API Services

Scenario: 1 actor create operation
Meta: @id actor-scenario1
Given actor POSTED and SAVED as actor using role admin and permissions create:actors,read:actors
and data {
           "name": "Robert",
           "lastName": "DeNiro"
         }
on endpoint /actors
Then SAVED object as actor should contain the following values: {
                                                                  "lastName": "DeNiro",
                                                                  "name": "Robert",
                                                                  "id": 1,
                                                                  "_links": {
                                                                    "self": {
                                                                      "href": "http://localhost/actors/1"
                                                                    }
                                                                  }
                                                                }

Scenario: 2 actor get-one operation
GivenStories: stories/actor.story#{id:actor-scenario1}
When GET on endpoint /actors/1 SAVED as actorGetOneResult using role admin and permissions read:actors
Then SAVED object as actorGetOneResult should contain the following values: {
                                                                              "lastName": "DeNiro",
                                                                              "name": "Robert",
                                                                              "id": 1,
                                                                              "_links": {
                                                                                "self": {
                                                                                  "href": "http://localhost/actors/1"
                                                                                }
                                                                              }
                                                                            }

Scenario: 3 actor get-all operation
GivenStories: stories/actor.story#{id:actor-scenario1}
When GET on endpoint /actors?page=1&size=35 SAVED as actorGetAllResult using role admin and permissions read:actors
Then SAVED object as actorGetAllResult should contain the following values: {
                                                                              "_links": {
                                                                                "first": {
                                                                                  "href": "http://localhost/actors?page=1&size=35"
                                                                                },
                                                                                "last": {
                                                                                  "href": "http://localhost/actors?page=1&size=35"
                                                                                },
                                                                                "self": {
                                                                                  "href": "http://localhost/actors?page=0&size=35"
                                                                                }
                                                                              },
                                                                              "totalElements": 1,
                                                                              "totalPages": 1,
                                                                              "page": null,
                                                                              "_embedded": {
                                                                                "content": [
                                                                                  {
                                                                                    "name": "Robert",
                                                                                    "id": 1,
                                                                                    "lastName": "DeNiro",
                                                                                    "_links": {
                                                                                      "self": {
                                                                                        "href": "http://localhost/actors/1"
                                                                                      }
                                                                                    }
                                                                                  }
                                                                                ]
                                                                              }
                                                                            }

Scenario: 4 actor patch operation
GivenStories: stories/actor.story#{id:actor-scenario1}
When actor PATCHED and SAVED as patchResult using role admin and permissions update:actors
and data [
           {
             "op": "replace",
             "path": "/name",
             "value": "New Actor Name"
           },
           {
             "op": "replace",
             "path": "/lastName",
             "value": "New Actor LastName"
           }
         ]
on endpoint /actors/1
Then SAVED object as patchResult should contain the following values: {
                                                                        "name": "New Actor Name",
                                                                        "id": 1,
                                                                        "lastName": "New Actor LastName",
                                                                        "_links": {
                                                                          "self": {
                                                                            "href": "http://localhost/actors/1"
                                                                          }
                                                                        }
                                                                      }

Scenario: 5 actor delete operation
GivenStories: stories/actor.story#{id:actor-scenario1}
When DELETE actor using role admin and permissions delete:actors on endpoint /actors/1 should return the following values: {"id": "1"}
Then GET on endpoint /actors/1 using role admin and permissions read:actors should respond 404