package com.swe573.swe573.service.search;

import com.swe573.swe573.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.Query;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final EntityManager em;

    public List<User> searchForUser(String word){
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(em);
        QueryBuilder qb = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(User.class)
                .get();
        Query query = qb.keyword()
                .onFields("username")
                .matching(word)
                .createQuery();
        FullTextQuery fullTextQuery = (FullTextQuery) fullTextEntityManager.createFullTextQuery(query,User.class);
        List<User> userList=(List<User>) fullTextQuery.getResultList();
        return userList;
    }
}
