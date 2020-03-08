package t5750.rest.jersey.storage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import t5750.rest.jersey.model.Address;
import t5750.rest.jersey.model.Contact;
import t5750.rest.jersey.util.Globals;

public class ContactStore {
	private static Map<String, Contact> store;
	private static ContactStore instance = null;

	private ContactStore() {
		store = new HashMap<String, Contact>();
		initOneContact();
	}

	public static Map<String, Contact> getStore() {
		if (instance == null) {
			instance = new ContactStore();
		}
		return store;
	}

	private static void initOneContact() {
		Address[] addrs = { new Address("Shanghai", "Long Hua Street"),
				new Address("Shanghai", "Dong Quan Street") };
		Contact contact = new Contact(Globals.T5750,
				Globals.T5750.toUpperCase(), Arrays.asList(addrs));
		store.put(contact.getId(), contact);
	}
}
