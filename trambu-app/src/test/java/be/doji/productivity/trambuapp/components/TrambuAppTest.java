package be.doji.productivity.trambuapp.components;

import be.doji.productivity.trambuapp.components.presenter.ActivityManagerContainer;
import be.doji.productivity.trambuapp.components.presenter.ActivityPagePresenter;
import be.doji.productivity.trambucore.managers.ActivityManager;
import be.doji.productivity.trambucore.managers.TimeTrackingManager;
import be.doji.productivity.trambucore.testutil.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class TrambuAppTest extends ApplicationTest {

    private static final Logger LOG = LoggerFactory.getLogger(TrambuAppTest.class);

    @Mock private ActivityPagePresenter mockPagePresenter;
    @Mock private ActivityManagerContainer mockActController;

    private ActivityManager activityManager;

    private TimeTrackingManager timeTrackingManager;

    private Path activityTestFile;
    private Path timeTrackingTestFile;

    @Before public void setUp() throws Exception {
        activityTestFile = createTempFile();
        timeTrackingTestFile = createTempFile();
        this.activityManager = new ActivityManager(activityTestFile.toString());
        this.timeTrackingManager = new TimeTrackingManager(timeTrackingTestFile.toString());
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockPagePresenter.getActivityController()).thenReturn(mockActController);
        Mockito.when(mockActController.getActivityManager()).thenReturn(activityManager);
        Mockito.when(mockActController.getTimeTrackingManager()).thenReturn(timeTrackingManager);
    }

    @After public void cleanUp() throws IOException {
        if (Files.exists(activityTestFile)) {
            Files.delete(activityTestFile);
        }
        if (Files.exists(timeTrackingTestFile)) {
            Files.delete(timeTrackingTestFile);
        }
    }

    public Path createTempFile() throws IOException {
        Path directoryPath = Paths
                .get(FileUtils.getTestPath("tempDirectory/maynotbeempty.txt", this.getClass().getClassLoader()))
                .getParent();
        return Files.createTempFile(directoryPath, "temp", "txt");
    }

    public ActivityManager getActivityManager() {
        return activityManager;
    }

    public void setActivityManager(ActivityManager activityManager) {
        this.activityManager = activityManager;
    }

    public TimeTrackingManager getTimeTrackingManager() {
        return timeTrackingManager;
    }

    public void setTimeTrackingManager(TimeTrackingManager timeTrackingManager) {
        this.timeTrackingManager = timeTrackingManager;
    }

    public ActivityPagePresenter getMockPagePresenter() {
        return mockPagePresenter;
    }

    public void setMockPagePresenter(ActivityPagePresenter mockPagePresenter) {
        this.mockPagePresenter = mockPagePresenter;
    }

    public ActivityManagerContainer getMockActController() {
        return mockActController;
    }

    public void setMockActController(ActivityManagerContainer mockActController) {
        this.mockActController = mockActController;
    }
}