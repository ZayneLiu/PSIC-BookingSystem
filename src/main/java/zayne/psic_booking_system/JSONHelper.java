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

    /**
     * Get all physicians from data storage.
     *
     * @return A list of all physicians retrieved and deserialized from `data.json`.
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws JSONException
     * @throws FileNotFoundException
     * @throws NoSuchFieldException
     * @throws SecurityException
     */
    public static ArrayList<Physician> getPhysicians() throws IllegalArgumentException, IllegalAccessException,
            JSONException, FileNotFoundException, NoSuchFieldException, SecurityException {

        var result = new ArrayList<Physician>();

        var physicians = getDataObject().getJSONArray("physician");
        for (var element : physicians) {
            var obj = new JSONObject(element.toString());

            var physician = new Physician();
            for (var item : Physician.class.getFields()) {
                var fieldName = item.getName();
                // Skip fields that don't exist in json file.
                if (!obj.has(fieldName)) {
                    continue;
                }

                var field = physician.getClass().getField(fieldName);

                // Handle JSON arrays for expertise and treatments
                if (JSONArray.class == obj.get(fieldName).getClass()) {
                    JSONArray jsonArray = (JSONArray) obj.get(fieldName);

                    switch (fieldName) {
                        case "expertise":
                            var expertiseList = new ArrayList<Expertise>();
                            for (var i = 0; i < jsonArray.length(); i++) {
                                var expertiseItem = Expertise.valueOf(jsonArray.get(i).toString().toUpperCase());
                                expertiseList.add(expertiseItem);
                            }
                            field.set(physician, expertiseList);
                            break;
                        case "treatment":
                            var treatmentList = new ArrayList<Treatment>();
                            for (var i = 0; i < jsonArray.length(); i++) {
                                var treatmentItem = Treatment.valueOf(jsonArray.get(i).toString().toUpperCase());
                                treatmentList.add(treatmentItem);
                            }
                            field.set(physician, treatmentList);
                            break;
                        default:
                            break;
                    }

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
