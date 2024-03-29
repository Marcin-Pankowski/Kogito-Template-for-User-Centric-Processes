package de.piu.templates.kogito.test.process;

import de.piu.templates.kogito.dto.Person;
import de.piu.templates.kogito.test.listener.TestNodeTriggeredListener;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.kie.kogito.process.Processes;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTaskExampleProcessTest {

    @Inject
    Processes processes;

    @Inject
    Instance<TestNodeTriggeredListener> testNodeTriggeredListenerInstance;

    @BeforeEach
    void resetListener() {
        testNodeTriggeredListenerInstance.get().setProcessNameToObserve("UserTaskExample");
        testNodeTriggeredListenerInstance.get().resetObserver();
    }

    @Test
    public void goThroughAllUserTasksIT() {

        var process = processes.processById("UserTaskExample");
        var model = process.createModel();
        var processInstance = process.createInstance(model);
        processInstance.start();


        assertThat(processInstance.status()).isEqualTo(org.kie.api.runtime.process.ProcessInstance.STATE_ACTIVE);

        var triggeredNodesNames = testNodeTriggeredListenerInstance.get().getTriggeredNodesNames();
        // Is the end node reached?
        assertThat(triggeredNodesNames.stream().reduce((first, second) -> second).get()).isEqualTo("Task for an individual User");
    
    }



}
