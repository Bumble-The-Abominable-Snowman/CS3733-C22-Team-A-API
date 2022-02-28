package teamA_API.entities;

import java.util.HashMap;
import lombok.Data;

@Data
public class SR {

  protected HashMap<String, String> fields_string = new HashMap<>();

  // default constructor
  public SR() {
    this.fields_string.put("request_id", "SANITATION_API_ID");
  }

  public void setFieldByString(String key, String value) {
    this.fields_string.put(key, value);
  }
}
