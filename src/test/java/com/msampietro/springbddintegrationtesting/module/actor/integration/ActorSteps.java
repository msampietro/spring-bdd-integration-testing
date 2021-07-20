package com.msampietro.springbddintegrationtesting.module.actor.integration;

import com.msampietro.springbddintegrationtesting.AbstractStory;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.io.StoryFinder;

import java.util.Collections;
import java.util.List;

public class ActorSteps extends AbstractStory {

    @AfterStory
    @BeforeScenario
    public void init() throws Exception {
        this.resetSavedObjects();
        this.truncateTables(Collections.singletonList("actor"));
        this.truncateSequences(Collections.singletonList("actor_seq"));
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths("src/test/resources",
                "**/actor.story",
                "**/excluded_actor.story");
    }

}
