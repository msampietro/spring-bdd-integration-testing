Feature: Film API Services

Scenario: 1 - film create operation
Meta: @id film-scenario1
Given single 'film production company' POSTED and SAVED as filmProductionCompany using role admin and permissions create:film_production_companies,read:film_production_companies
and data {
           "name": "Test Production Company"
         }
on endpoint /film-production-companies
And single 'actor' POSTED and SAVED as actor using role admin and permissions create:actors,read:actors
and data {
           "name": "Robert",
           "lastName": "DeNiro"
         }
on endpoint /actors
When film POSTED and SAVED as filmCreationResult using role admin and permissions create:films,read:films
and data {
           "name": "Film Test",
           "ranking": 9,
           "filmGenre": {
             "id": 1
           }
         }
on endpoint /films including previously created objects:
|TYPE|SAVED_AS|WITH_KEY|
|ARRAY|actor|actors|
|OBJECT|filmProductionCompany|filmProductionCompany|
Then SAVED object as filmCreationResult should contain the following values: {
                                                                        "ranking": 9,
                                                                        "name": "Film Test",
                                                                        "id": 1,
                                                                        "_links": {
                                                                          "self": {
                                                                            "href": "http://localhost/films/1"
                                                                          }
                                                                        },
                                                                        "filmProductionCompany": {
                                                                          "name": "Test Production Company",
                                                                          "id": 1,
                                                                          "_links": {
                                                                            "self": {
                                                                              "href": "http://localhost/film-production-companies/1"
                                                                            }
                                                                          }
                                                                        },
                                                                        "_embedded": {
                                                                          "actors": [
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
                                                                          ],
                                                                          "reviews": []
                                                                        }
                                                                      }

Scenario: 2 - film get-one operation
GivenStories: stories/film.story#{id:film-scenario1}
When GET on endpoint /films/1 SAVED as filmGetOneResult using role admin and permissions read:films
Then SAVED object as filmGetOneResult should contain the following values: {
                                                                             "name": "Film Test",
                                                                             "id": 1,
                                                                             "ranking": 9,
                                                                             "_links": {
                                                                               "self": {
                                                                                 "href": "http://localhost/films/1"
                                                                               }
                                                                             },
                                                                             "filmProductionCompany": {
                                                                               "name": "Test Production Company",
                                                                               "id": 1,
                                                                               "_links": {
                                                                                 "self": {
                                                                                   "href": "http://localhost/film-production-companies/1"
                                                                                 }
                                                                               }
                                                                             },
                                                                             "_embedded": {
                                                                               "actors": [
                                                                                 {
                                                                                   "lastName": "DeNiro",
                                                                                   "name": "Robert",
                                                                                   "id": 1,
                                                                                   "_links": {
                                                                                     "self": {
                                                                                       "href": "http://localhost/actors/1"
                                                                                     }
                                                                                   }
                                                                                 }
                                                                               ],
                                                                               "reviews": []
                                                                             }
                                                                           }

Scenario: 3 - film get-all operation
GivenStories: stories/film.story#{id:film-scenario1}
When GET on endpoint /films?page=1&size=35 SAVED as filmGetAllResult using role admin and permissions read:films
Then SAVED object as filmGetAllResult should contain the following values: {
                                                                   "_links": {
                                                                     "first": {
                                                                       "href": "http://localhost/films?page=1&size=35"
                                                                     },
                                                                     "last": {
                                                                       "href": "http://localhost/films?page=1&size=35"
                                                                     },
                                                                     "self": {
                                                                       "href": "http://localhost/films?page=0&size=35"
                                                                     }
                                                                   },
                                                                   "totalElements": 1,
                                                                   "totalPages": 1,
                                                                   "page": null,
                                                                   "_embedded": {
                                                                     "content": [
                                                                       {
                                                                         "name": "Film Test",
                                                                         "id": 1,
                                                                         "ranking": 9,
                                                                         "_links": {
                                                                           "self": {
                                                                             "href": "http://localhost/films/1"
                                                                           }
                                                                         },
                                                                         "filmProductionCompany": {
                                                                           "name": "Test Production Company",
                                                                           "id": 1,
                                                                           "_links": {
                                                                             "self": {
                                                                               "href": "http://localhost/film-production-companies/1"
                                                                             }
                                                                           }
                                                                         },
                                                                         "_embedded": {
                                                                           "actors": [
                                                                             {
                                                                               "lastName": "DeNiro",
                                                                               "name": "Robert",
                                                                               "id": 1,
                                                                               "_links": {
                                                                                 "self": {
                                                                                   "href": "http://localhost/actors/1"
                                                                                 }
                                                                               }
                                                                             }
                                                                           ],
                                                                           "reviews": []
                                                                         }
                                                                       }
                                                                     ]
                                                                   }
                                                                 }

Scenario: 4 - film patch operation
GivenStories: stories/film.story#{id:film-scenario1}
Given single 'actor' POSTED and SAVED as actor using role admin and permissions create:actors,read:actors
and data {
           "name": "Matthew",
           "lastName": "Mcconaughey"
         }
on endpoint /actors
And single 'film production company' POSTED and SAVED as filmProductionCompany using role admin and permissions create:film_production_companies,read:film_production_companies
and data {
           "name": "Another Production Company"
         }
on endpoint /film-production-companies
When film PATCHED and SAVED as patchResult using role admin and permissions update:films
and data [
           {
             "op": "replace",
             "path": "/name",
             "value": "New Movie Name"
           },
           {
             "op": "replace",
             "path": "/ranking",
             "value": 7
           },
           {
             "op": "replace",
             "path": "/filmProductionCompany",
             "value": {
               "id": 2
             }
           },
           {
             "op": "remove",
             "path": "/actors/0"
           },
           {
             "op": "add",
             "path": "/actors/-",
             "value": {
               "id": 2
             }
           }
         ]
on endpoint /films/1
Then SAVED object as patchResult should contain the following values: {
                                                                         "name": "New Movie Name",
                                                                         "id": 1,
                                                                         "ranking": 7,
                                                                         "_links": {
                                                                           "self": {
                                                                             "href": "http://localhost/films/1"
                                                                           }
                                                                         },
                                                                         "filmProductionCompany": {
                                                                           "name": "Another Production Company",
                                                                           "id": 2,
                                                                           "_links": {
                                                                             "self": {
                                                                               "href": "http://localhost/film-production-companies/2"
                                                                             }
                                                                           }
                                                                         },
                                                                         "_embedded": {
                                                                           "actors": [
                                                                             {
                                                                               "name": "Matthew",
                                                                               "id": 2,
                                                                               "lastName": "Mcconaughey",
                                                                               "_links": {
                                                                                 "self": {
                                                                                   "href": "http://localhost/actors/2"
                                                                                 }
                                                                               }
                                                                             }
                                                                           ],
                                                                           "reviews": []
                                                                         }
                                                                       }

Scenario: 5 - film add review operation
Meta: @id film-scenario5
GivenStories: stories/film.story#{id:film-scenario1}
When review PUT executed and SAVED as reviewResult using role admin and permissions update:films
and data {
           "comment": "My review",
           "username": "usertest"
         }
on endpoint /films/1/reviews
Then SAVED object as reviewResult should contain the following values: {
                                                                         "username": "usertest",
                                                                         "comment": "My review",
                                                                         "id": 1,
                                                                         "_links": {
                                                                           "self": {
                                                                             "href": "http://localhost/reviews/1"
                                                                           }
                                                                         }
                                                                       }
And GET on endpoint /films/1 using role admin and permissions read:films should contain the following values: {
                                                                                                                "name": "Film Test",
                                                                                                                "id": 1,
                                                                                                                "ranking": 9,
                                                                                                                "_links": {
                                                                                                                  "self": {
                                                                                                                    "href": "http://localhost/films/1"
                                                                                                                  }
                                                                                                                },
                                                                                                                "filmProductionCompany": {
                                                                                                                  "name": "Test Production Company",
                                                                                                                  "id": 1,
                                                                                                                  "_links": {
                                                                                                                    "self": {
                                                                                                                      "href": "http://localhost/film-production-companies/1"
                                                                                                                    }
                                                                                                                  }
                                                                                                                },
                                                                                                                "_embedded": {
                                                                                                                  "actors": [
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
                                                                                                                  ],
                                                                                                                  "reviews": [
                                                                                                                    {
                                                                                                                      "comment": "My review",
                                                                                                                      "id": 1,
                                                                                                                      "username": "usertest",
                                                                                                                      "_links": {
                                                                                                                        "self": {
                                                                                                                          "href": "http://localhost/reviews/1"
                                                                                                                        }
                                                                                                                      }
                                                                                                                    }
                                                                                                                  ]
                                                                                                                }
                                                                                                              }

Scenario: 6 - film delete operation
GivenStories: stories/film.story#{id:film-scenario1}
When DELETE film using role admin and permissions delete:films on endpoint /films/1 should return the following values: {"id": "1"}
Then GET on endpoint /films/1 using role admin and permissions read:films should respond 404

Scenario: 7 - get film reviews operation
GivenStories: stories/film.story#{id:film-scenario5}
Then GET on endpoint /films/1/reviews?page=1&size=35 using role admin and permissions read:films should contain the following values: {
                                                                                                                                        "_links": {
                                                                                                                                          "first": {
                                                                                                                                            "href": "http://localhost/films/1/reviews?page=1&size=35"
                                                                                                                                          },
                                                                                                                                          "last": {
                                                                                                                                            "href": "http://localhost/films/1/reviews?page=1&size=35"
                                                                                                                                          },
                                                                                                                                          "self": {
                                                                                                                                            "href": "http://localhost/films/1/reviews?page=0&size=35"
                                                                                                                                          }
                                                                                                                                        },
                                                                                                                                        "totalElements": 1,
                                                                                                                                        "totalPages": 1,
                                                                                                                                        "page": null,
                                                                                                                                        "_embedded": {
                                                                                                                                          "content": [
                                                                                                                                            {
                                                                                                                                              "id": 1,
                                                                                                                                              "comment": "My review",
                                                                                                                                              "username": "usertest",
                                                                                                                                              "_links": {
                                                                                                                                                "self": {
                                                                                                                                                  "href": "http://localhost/reviews/1"
                                                                                                                                                }
                                                                                                                                              }
                                                                                                                                            }
                                                                                                                                          ]
                                                                                                                                        }
                                                                                                                                      }

Scenario: 8 - film review remove and get-all operations
GivenStories: stories/film.story#{id:film-scenario5}
When DELETE review using role admin and permissions delete:films
on endpoint /films/1/reviews/1 should return the following values: {
                                                                     "username": "usertest",
                                                                     "id": 1,
                                                                     "comment": "My review",
                                                                     "_links": {
                                                                       "self": {
                                                                         "href": "http://localhost/reviews/1"
                                                                       }
                                                                     }
                                                                   }
Then GET on endpoint /films/1/reviews?page=1&size=35 using role admin
and permissions read:films should contain the following values: {
                                                                  "_links": {
                                                                    "first": {
                                                                      "href": "http://localhost/films/1/reviews?page=1&size=35"
                                                                    },
                                                                    "last": {
                                                                      "href": "http://localhost/films/1/reviews?page=1&size=35"
                                                                    },
                                                                    "self": {
                                                                      "href": "http://localhost/films/1/reviews?page=0&size=35"
                                                                    }
                                                                  },
                                                                  "totalElements": 0,
                                                                  "totalPages": 0,
                                                                  "page": null,
                                                                  "_embedded": {
                                                                    "content": []
                                                                  }
                                                                }