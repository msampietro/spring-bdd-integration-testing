package com.msampietro.springbddintegrationtesting.module.film.integration;

import com.msampietro.springbddintegrationtesting.AbstractStory;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.io.StoryFinder;

import java.util.Arrays;
import java.util.List;

public class FilmCrewMemberSteps extends AbstractStory {

    @AfterStory
    @BeforeScenario
    public void init() throws Exception {
        this.resetSavedObjects();
        this.truncateTables(Arrays.asList("film_production_company",
                "film_crew_member"));
        this.truncateSequences(Arrays.asList("film_production_company_seq",
                "film_crew_member_seq"));
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths("src/test/resources",
                "**/film_crew_member.story",
                "**/excluded_film_crew_member.story");
    }

}
