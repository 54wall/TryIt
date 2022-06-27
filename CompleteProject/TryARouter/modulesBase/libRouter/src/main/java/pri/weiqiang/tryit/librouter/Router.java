package pri.weiqiang.tryit.librouter;

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

public class Router {

    final Map<String, Map<String, Class<?>>> groupMap = new HashMap<>();
    final Map<String, Class<?>> routeMap = new HashMap<>();

    private Router() {
    }

    public final static class Holder {
        static Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * @param path /main/MainActivity
     * @param clz
     */
    public void register(String path, Class<?> clz) {
        String[] strArry = path.split("/");
        if (strArry.length > 2) {
            String groupName = strArry[1];
            String routeName = path;
            Map<String, Class<?>> group = null;
            if (groupMap.containsKey(groupName)) {
                group = groupMap.get(groupName);
            }
            if (group == null) {
                group = new HashMap<>();
                groupMap.put(groupName, group);
            }
            if (group != null) {
                group.put(routeName, clz);
            }
        }
    }

    public void startActivity(Activity activity, String path) {
        String[] strArry = path.split("/");
        if (strArry.length > 2) {
            String groupName = strArry[1];
            String routeName = path;
            Map<String, Class<?>> group = null;
            if (groupMap.containsKey(groupName)) {
                group = groupMap.get(groupName);
            }
            if (group != null && group.containsKey(routeName)) {
                Class<?> clz = group.get(routeName);
                activity.startActivity(new Intent(activity, clz));
            }

        }
    }
}
