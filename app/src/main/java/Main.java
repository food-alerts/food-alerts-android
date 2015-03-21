import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by nicolas on 21/03/2015.
 */
public class Main {

    public static void main(String[] args) throws IOException, JAXBException {
        AtomParser atomParser = new AtomParser();

        Optional<String> productUrl =
               atomParser.productUrl(
                       HttpClient.call(
                               "http://apirest-dev.vidal.fr/rest/api/search?code=3400938200577&app_id=9fd557d3&app_key=b5ab4790e151b24971f250200643effd"));
        System.out.println("product URL: " + productUrl);

        if (productUrl.isPresent()) {
            List<String> molecules =
                    AtomParser.molecules(
                            HttpClient.call(
                                    "http://apirest-dev.vidal.fr" + productUrl.get() + "/molecules?app_id=9fd557d3&app_key=b5ab4790e151b24971f250200643effd"));
            System.out.println("molecules: " + molecules);
        }


    }


}
