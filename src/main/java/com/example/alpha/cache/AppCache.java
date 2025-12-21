package com.example.alpha.cache;

import com.example.alpha.Entity.ConfigJournalAppEntity;
import com.example.alpha.Repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    // Frequently use or changing frequency ko config database mei insert kara denege and load kar lenege spring boot mei


    public enum keys{
        WEATHER_API;
    }


    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    //What we do as app run appCache load all the details then after this we can commun with Map
    // In memory Cache
    public Map<String, String> appCache;

    // Jese hi bean load humme kuch kaam karana ho
    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all){
            appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
