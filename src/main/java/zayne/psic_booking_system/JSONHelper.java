package zayne.psic_booking_system;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import zayne.psic_booking_system.Physician.Expertise;
import zayne.psic_booking_system.Physician.Treatment;

public class JSONHelper {

    public static final String JSON_URL = "data.json";

    private static JSONObject getDataObject() throws JSONException, FileNotFoundException {
        return new JSONObject(new JSONTokener(new FileReader(JSON_URL)));
    }

    public static ArrayList<Physician> getPhysicians() throws IllegalArgumentException, IllegalAccessException,
            JSONException, FileNotFoundException, NoSuchFieldException, SecurityException {

        var result = new ArrayList<Physician>();

        var physicians = getDataObject().getJSONArray("physician");
        for (var element : physicians) {
            var obj = new JSONObject(element.toString());

            var physician = new Physician();
            for (var item : Physician.class.getFields()) {
                var fieldName = item.getName();
                if (!obj.has(fieldName)) {
                    continue;
                }

                var field = physician.getClass().getField(fieldName);

                // Handle JSON arrays for expertise and treatments
                if (JSONArray.class == obj.get(fieldName).getClass()) {
                    JSONArray jsonArray = (JSONArray) obj.get(fieldName);
                    var arr = fieldName == "expertise" ? new Expertise[jsonArray.length()]
                            : new Treatment[jsonArray.length()];
                    for (var i = 0; i < arr.length; i++) {
                        arr[i] = fieldName == "expertise" ? Expertise.valueOf(jsonArray.get(i).toString().toUpperCase())
                                : Treatment.valueOf(jsonArray.get(i).toString().toUpperCase());
                    }

                    field.set(physician, arr);
                    continue;
                }

                // Handle primitive types
                field.set(physician, obj.get(fieldName));
            }

            result.add(physician);
        }

        return result;
    }
}
