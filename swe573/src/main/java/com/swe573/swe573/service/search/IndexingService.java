package com.swe573.swe573.service.search;

import lombok.RequiredArgsConstructor;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class IndexingService  {
    private final EntityManager em;
    @Transactional
    public void initiateIndexing() throws InterruptedException {
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(em);
        fullTextEntityManager.createIndexer().startAndWait();
    }
}
