package be.doji.productivity.TrackMeUp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Doji on 22/10/2017.
 */
public final class TrackerUtils {

    private TrackerUtils() {
    }

    public static List<String> findAllMatches(String regex, String lineToSearch) {
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile(regex).matcher(lineToSearch);
        while (m.find()) {
            allMatches.add(m.group());
        }

        return allMatches;
    }
}
