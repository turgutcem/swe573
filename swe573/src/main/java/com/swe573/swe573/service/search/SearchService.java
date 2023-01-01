package com.swe573.swe573.service.search;

import com.swe573.swe573.model.Gibi;
import com.swe573.swe573.model.Topic;
import com.swe573.swe573.model.User;
import com.swe573.swe573.model.enums.GibiAccessLevel;
import com.swe573.swe573.service.GibiService;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.Query;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final EntityManager em;

    @Autowired
    private GibiService gibiService;

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
        if(userList.isEmpty()){
            Query query2=qb.keyword().fuzzy().withEditDistanceUpTo(2).onField("username").matching(word).createQuery();
            FullTextQuery fullTextQuery2 = (FullTextQuery) fullTextEntityManager.createFullTextQuery(query2,User.class);
            List<User> userList2=(List<User>) fullTextQuery2.getResultList();
            return userList2;
        }
        return userList;
    }

    public List<Topic> searchForTopic(String word){
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(em);
        QueryBuilder qb = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Topic.class)
                .get();
        Query query = qb.keyword()
                .onFields("topicName")
                .matching(word)
                .createQuery();
        FullTextQuery fullTextQuery = (FullTextQuery) fullTextEntityManager.createFullTextQuery(query,Topic.class);
        List<Topic> topicList=(List<Topic>) fullTextQuery.getResultList();
        if(topicList.isEmpty()){
            Query query2=qb.keyword().fuzzy().withEditDistanceUpTo(2).onField("topicName").matching(word).createQuery();
            FullTextQuery fullTextQuery2 = (FullTextQuery) fullTextEntityManager.createFullTextQuery(query2,Topic.class);
            List<Topic> topicList2=(List<Topic>) fullTextQuery2.getResultList();
            return topicList2;
        }
        return topicList;
    }

    public Set<Gibi> searchForGibi(User user,
                                   String word){
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(em);
        QueryBuilder qb = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Gibi.class)
                .get();
        Query query = qb.phrase()
                .withSlop(3)
                .onField("onComment")
                .sentence(word)
                .createQuery();

        Query query2 = qb.phrase()
                .withSlop(3).onField("URL").sentence(word).createQuery();

        FullTextQuery fullTextQuery = (FullTextQuery) fullTextEntityManager.createFullTextQuery(query,Gibi.class);
        FullTextQuery fullTextQuery2 = (FullTextQuery) fullTextEntityManager.createFullTextQuery(query2,Gibi.class);

        List<Gibi> gibiList2=(List<Gibi>) fullTextQuery2.getResultList();
        List<Gibi> gibiList=(List<Gibi>) fullTextQuery.getResultList();

        Set<Gibi> mergedList= Stream.concat(gibiList.stream(),gibiList2.stream()).filter(g -> (g.getAccessLevel().equals(GibiAccessLevel.PUBLIC)) || (gibiService.isPermittedToView(user,g))).collect(Collectors.toSet());

        return mergedList;
    }
}
