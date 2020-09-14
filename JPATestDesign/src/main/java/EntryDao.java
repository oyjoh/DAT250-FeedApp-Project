import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EntryDao {
    private EntityManager entityManager;

    public EntryDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<Entry> getAllEntries() {
        Query query = entityManager.createNamedQuery("Entry.findAll", Entry.class);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Entry> getAllEntriesFromPoll(Long pollId) {
        Query query = entityManager.createNamedQuery("Entry.findByPoll", Entry.class);
        query.setParameter("poll", pollId);
        return query.getResultList();
    }

    public void updateEntry(Long entryId, EntryUpdateRequest eur ) {
        String updatedTimestamp = TimeStamp.getTimeStamp();
        Entry entry = getEntryById(entryId);

        // Iterate over the input to see what we want to update
        if(eur.getValue() != null) entry.setValue(eur.getValue());
        if(eur.getNumber() != null) entry.setNumber(eur.getNumber());
        entry.setTime_submitted(updatedTimestamp);

        // Update the database
        entityManager.getTransaction().begin();
        entityManager.persist(entry);
        entityManager.getTransaction().commit();

    }

    public void deleteEntry(Long entryId) {
        Entry e = getEntryById(entryId);
        entityManager.getTransaction().begin();
        entityManager.remove(e);
        entityManager.getTransaction().commit();
    }

    public Entry addEntry(Value value, Integer number, Brukar brukar, Poll poll) {
        String timestamp = TimeStamp.getTimeStamp();
        Entry entry = Entry.builder()
                .setNumber(number)
                .setValue(value)
                .setTime_submitted(timestamp)
                .setBrukar(brukar)
                .setPoll(poll)
                .build();

        // Add the new entry to the database
        entityManager.getTransaction().begin();
        entityManager.persist(entry);
        entityManager.getTransaction().commit();

        return entry;
    }


    private Entry getEntryById(Long entryId) {
        return entityManager.find(Entry.class, entryId);
    }





}
