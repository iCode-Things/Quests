package me.dan.quests.mongo;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.dan.quests.config.Config;
import me.dan.quests.user.QuestProgress;
import me.dan.quests.user.QuestUser;
import org.bson.Document;

@Getter
public class MongoDatabaseImpl {

    private final MongoClient mongoClient;
    private final DB db;
    private final DBCollection collection;
    private final Gson gson;

    public MongoDatabaseImpl() throws IllegalArgumentException {
        this.gson = new Gson();
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("mongodb://");
        uriBuilder.append(Config.MONGO_HOST.getString());
        uriBuilder.append(":");
        uriBuilder.append(Config.MONGO_PORT.getInt());
        String uri = uriBuilder.toString();
        mongoClient = new MongoClient(new MongoClientURI(uri));
        this.db = mongoClient.getDB(Config.MONGO_DATABASE.getString());
        this.collection = db.getCollection("players");
    }

    private void insertUser(QuestUser questUser) {
        DBObject dbObject = new BasicDBObject("uuid", questUser.getUuid().toString());
        insertQuestData(dbObject, questUser);
        collection.insert(dbObject);
    }

    public void saveUser(QuestUser questUser) {
        DBObject r = new BasicDBObject("uuid", questUser.getUuid().toString());
        //Use findOne to only get one object!
        DBObject found = collection.findOne(r);
        if (found == null) {
            insertUser(questUser);
            return;
        }


        DBObject obj = new BasicDBObject("uuid", questUser.getUuid().toString());
        insertQuestData(obj, questUser);
        collection.update(found, obj);
    }

    public void insertQuestData(DBObject dbObject, QuestUser questUser) {
        for (String key : questUser.getQuestProgressMap().keySet()) {
            QuestProgress questProgress = questUser.getQuestProgressMap().get(key);
            dbObject.put(key, gson.toJson(questProgress));
        }
    }

}
