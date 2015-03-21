import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by nicolas on 21/03/2015.
 */
public class HttpClient {
    static String call(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = null;
        try {
            request = new Request.Builder().url(url).build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
