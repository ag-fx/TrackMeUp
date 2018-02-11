package be.doji.productivity.trambuapp.components.presenter;

import be.doji.productivity.trambuapp.components.view.ActivityPageView;
import be.doji.productivity.trambuapp.components.view.ActivityView;
import be.doji.productivity.trambuapp.utils.DisplayConstants;
import be.doji.productivity.trambuapp.utils.DisplayUtils;
import be.doji.productivity.trambucore.model.tasks.Activity;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityPagePresenter extends Presenter {

  private static final Logger LOG = LoggerFactory.getLogger(ActivityPagePresenter.class);

  private ManagerContainer model;
  private ActivityPageView view;

  private String tagFilter;
  private String projectFilter;
  private boolean filterDone = false;

  public ActivityPagePresenter(ActivityPageView view) {
    this.view = view;
    this.model = ManagerContainer.Factory.getInstance();
  }

  public void refresh() {
    populateActivities();
    view.refreshAccordion();
  }

  public void populate() {
    refresh();
  }

  public void populateActivities() {
    view.getPanes().clear();
    Map<Date, List<Activity>> groupedActivities = model.getActivityManager()
        .getActivitiesWithDateHeader();
    for (Map.Entry<Date, List<Activity>> activityGroupEntry : groupedActivities.entrySet()) {
      List<Activity> activityGroup = applyFilters(activityGroupEntry.getValue());
      if (!activityGroup.isEmpty()) {
        view.addPane(DisplayUtils
            .createSeperatorPane(DisplayUtils.getDateSeperatorText(activityGroupEntry.getKey())));
        for (Activity activity : activityGroup) {
          view.addPane(new ActivityView(activity, this));
        }
      }
    }
  }

  private List<Activity> applyFilters(List<Activity> activityPanes) {

    List<Activity> filteredActivities = new ArrayList<>();
    for (Activity activity : activityPanes) {
      if (!shouldBeFiltered(activity)) {
        filteredActivities.add(activity);
      }
    }
    return filteredActivities;
  }

  private boolean shouldBeFiltered(Activity activity) {
    boolean shouldFilterBasedOnProject = shouldBeFilteredOnProject(activity);
    boolean shouldFilterBasedOnTag = shouldBeFilteredOnTag(activity);
    boolean shouldFilterBasedOnCompletion = filterDone && activity.isCompleted();
    return shouldFilterBasedOnProject || shouldFilterBasedOnTag || shouldFilterBasedOnCompletion;
  }

  private boolean shouldBeFilteredOnProject(Activity activity) {
    return StringUtils.isNotBlank(projectFilter) && !activity.getProjects().parallelStream()
        .anyMatch(project -> StringUtils.equalsIgnoreCase(project, getProjectFilter()));
  }

  private boolean shouldBeFilteredOnTag(Activity activity) {
    return StringUtils.isNotBlank(tagFilter) && !activity.getTags().isEmpty() && !activity.getTags()
        .parallelStream()
        .anyMatch(tag -> StringUtils.equalsIgnoreCase(tag, getTagFilter()));
  }


  private String getTagFilter() {
    return tagFilter;
  }

  void setTagFilter(String tagFilter) {
    this.resetFilter();
    this.tagFilter = tagFilter;
    view.getControlAccordion().updateFilterLabel();
  }

  private String getProjectFilter() {
    return projectFilter;
  }

  public void setProjectFilter(String projectFilter) {
    this.resetFilter();
    this.projectFilter = projectFilter;
    view.getControlAccordion().updateFilterLabel();
  }

  public void resetFilter() {
    this.tagFilter = "";
    this.projectFilter = "";
    this.filterDone = false;
    view.getControlAccordion().updateFilterLabel();
  }

  public String getActiveFilter() {
    if (StringUtils.isNotBlank(tagFilter)) {
      return tagFilter;
    } else if (StringUtils.isNotBlank(projectFilter)) {
      return projectFilter;
    } else if (this.filterDone) {
      return DisplayConstants.LABEL_TEXT_FILTER_COMPLETED;
    } else {
      return DisplayConstants.LABEL_TEXT_FILTER_NONE;
    }
  }

  public void setFilterDone(boolean filterDone) {
    this.filterDone = filterDone;
  }

  public ActivityPageView getView() {
    return view;
  }

  public void setView(ActivityPageView view) {
    this.view = view;
  }

  public void onViewClose() {
    this.model.getTimeTrackingManager().stopAll();

  }

  public void onViewLoad() {
    this.refresh();
  }

  public void saveActivity(Activity newActivity) {
    try {
      this.model.getActivityManager().save(newActivity);

    } catch (IOException | ParseException exception) {
      LOG.error("Error creation new activity", exception);
    }

  }

  public ManagerContainer getActivityController() {
    return this.model;
  }
}
