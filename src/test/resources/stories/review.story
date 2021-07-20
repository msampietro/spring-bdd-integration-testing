Feature: Review Reduced API Services (Related to Film)

Scenario: 1 review get-one operation
GivenStories: stories/film.story#{id:film-scenario5}
Then GET on endpoint /reviews/1 using role admin
and permissions read:reviews should contain the following values: {
                                                                    "comment": "My review",
                                                                    "id": 1,
                                                                    "username": "usertest",
                                                                    "_links": {
                                                                      "self": {
                                                                        "href": "http://localhost/reviews/1"
                                                                      }
                                                                    }
                                                                  }

Scenario: 2 review patch operation
GivenStories: stories/film.story#{id:film-scenario5}
When review PATCHED and SAVED as patchResult using role admin and permissions update:reviews
and data [
           {
             "op": "replace",
             "path": "/comment",
             "value": "New Comment"
           },
           {
            "op": "replace",
            "path": "/username",
            "value": "New Username"
           }
         ]
on endpoint /reviews/1
Then SAVED object as patchResult should contain the following values: {
                                                                        "id": 1,
                                                                        "username": "New Username",
                                                                        "comment": "New Comment",
                                                                        "_links": {
                                                                          "self": {
                                                                            "href": "http://localhost/reviews/1"
                                                                          }
                                                                        }
                                                                      }

Scenario: 3 review create operation disabled
GivenStories: stories/film.story#{id:film-scenario5}
Then POST on endpoint /reviews using role admin and permissions create:reviews
and data {
            "comment": "My review",
            "username": "usertest"
          }
should respond 405

Scenario: 4 review delete operation disabled
GivenStories: stories/film.story#{id:film-scenario5}
Then DELETE on endpoint /reviews/1 using role admin and permissions delete:reviews should respond 405

Scenario: 5 review get-all operation disabled
GivenStories: stories/film.story#{id:film-scenario5}
Then GET on endpoint /reviews?page=1&size=35 using role admin and permissions read:reviews should respond 405