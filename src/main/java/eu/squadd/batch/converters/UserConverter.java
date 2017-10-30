/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.converters;

import eu.squadd.batch.domain.mongo.MongoUser;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author smoczyna
 */
public class UserConverter implements Converter<MongoUser, Document> {

    @Override
    public Document convert(MongoUser usr) {
        Document doc = new Document();
        doc.put("name", usr.getName());
        return doc;
    }
    
    public MongoUser convert(Document document) {
        MongoUser user = new MongoUser(document.getString("name"));
        return user;
    }
}
