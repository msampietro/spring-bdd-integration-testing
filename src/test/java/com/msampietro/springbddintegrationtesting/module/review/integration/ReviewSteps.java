package com.msampietro.springbddintegrationtesting.module.review.integration;

import com.msampietro.springbddintegrationtesting.AbstractStory;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.io.StoryFinder;

import java.util.Arrays;
import java.util.List;

public class ReviewSteps extends AbstractStory {

    @AfterStory
    @BeforeScenario
    public void init() throws Exception {
        this.resetSavedObjects();
        this.truncateTables(Arrays.asList("film_actor",
                "actor",
                "film",
                "film_production_company",
                "review"));
        this.truncateSequences(Arrays.asList("actor_seq",
                "film_seq",
                "film_production_company_seq",
                "review_seq"));
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths("src/test/resources",
                "**/review.story",
                "**/excluded_review.story");
    }

}
