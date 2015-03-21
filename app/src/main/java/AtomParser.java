import fr.vidal.oss.jaxb.atom.core.Entry;
import fr.vidal.oss.jaxb.atom.core.Feed;
import fr.vidal.oss.jaxb.atom.core.Link;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.*;

/**
 * Created by nicolas on 21/03/2015.
 */
public class AtomParser {

    private Unmarshaller unmarshaller;

    AtomParser() throws JAXBException {
        unmarshaller = JAXBContext.newInstance(Feed.class).createUnmarshaller();
    }

    public Optional<String> productUrl(String atom) throws JAXBException {
        Feed result = (Feed) unmarshaller.unmarshal(new InputSource(new StringReader(atom)));

        Collection<Entry> entries = result.getEntries();
        if (entries.size() > 0) {
            Entry entry = entries.iterator().next();
            for (Link link : entry.getLinks()) {
                if (link.getType().equals(""))
            }
            whil (links.hasNext())
                Link next = links.next();
                return Optional.of(next.getHref());
            }
        }
        return Optional.empty();
    }

    public static List<String> molecules(String atom) {
        return new ArrayList<String>();
    }
}
