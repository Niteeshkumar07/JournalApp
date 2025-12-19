package com.example.alpha.Service;

import com.example.alpha.Entity.JournalEntry;
import com.example.alpha.Entity.User;
import com.example.alpha.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    // As we know run it after create bean in main file but if we run it give error because
    // mongodb ke andar replication is needed for transactional management show we move our local database to atlas a cloud server like
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try{
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved =  journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            // if we some error occur then journal entry save but kisi user se asscociate nahi rahegi
            userService.saveUser(user);
        } catch (Exception e){
            throw new RuntimeException("An error occured while saving the entry",e);
        }

    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try{
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x->x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }

        } catch (Exception e){
            throw new RuntimeException("An error occurence while saving the entry",e);
        }
        return removed;


    }

    public JournalEntry updateEntry(ObjectId id, JournalEntry updatedEntry) {
        JournalEntry existing = getById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        existing.setTitle(updatedEntry.getTitle());
        existing.setContent(updatedEntry.getContent());
        existing.setDate(updatedEntry.getDate());

        return journalEntryRepository.save(existing);
    }

//    public List<JournalEntry> findByUserName(String userName){
//
//    }
}
