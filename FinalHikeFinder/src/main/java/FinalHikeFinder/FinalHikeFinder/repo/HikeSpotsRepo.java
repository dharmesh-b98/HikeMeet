package FinalHikeFinder.FinalHikeFinder.repo;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import FinalHikeFinder.FinalHikeFinder.repo.constants.MongoConstants;

@Repository
public class HikeSpotsRepo {
    
    @Autowired MongoTemplate mongoTemplate;
    
    public Long checkCount(){
        Criteria criteria = new Criteria();
        Query query = Query.query(criteria);
        return mongoTemplate.count(query, Document.class, MongoConstants.mongoCollectionName);
    }


    public void batchInsert(List<Document> hikeSpotsDocumentList){
        mongoTemplate.insert(hikeSpotsDocumentList, MongoConstants.mongoCollectionName);
    }


    public List<Document> getHikeSpotDocumentList(){
        Criteria criteria = new Criteria();
        Query query = Query.query(criteria);
        List<Document> documentList = mongoTemplate.find(query, Document.class, MongoConstants.mongoCollectionName);
        return documentList;
    }

    public Document getHikeSpot(Integer hikeSpotId){
        Criteria criteria = Criteria.where("_id").is(hikeSpotId);
        Query query = Query.query(criteria);
        Document document = mongoTemplate.find(query, Document.class, MongoConstants.mongoCollectionName).get(0);
        return document;
    }

    public Document getHikeSpotTele(String hikeSpotName){
        Criteria criteria = Criteria.where("name").is(hikeSpotName);
        Query query = Query.query(criteria);
        Document document = mongoTemplate.find(query, Document.class, MongoConstants.mongoCollectionName).get(0);
        return document;
    }
    

    
}
